// Name: Jeel Vekaria
// Student ID: 501170561
import java.util.ArrayList;

/*
 * A Playlist contains an array list of AudioContent (i.e. Song, AudioBooks, Podcasts) from the library
 */
public class Playlist
{
	private String title;
	private ArrayList<AudioContent> contents; // songs, books, or podcasts or even mixture
	
	public Playlist(String title)
	{
		this.title = title;
		contents = new ArrayList<AudioContent>();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void addContent(AudioContent content)
	{
		contents.add(content);
	}
	
	public ArrayList<AudioContent> getContent()
	{
		return contents;
	}

	public void setContent(ArrayList<AudioContent> contents)
	{
		this.contents = contents;
	}
	
	/*
	 * Print the information of each audio content object (song, audiobook, podcast)
	 * in the contents array list. Print the index of the audio content object first
	 * followed by ". " then make use of the printInfo() method of each audio content object
	 * Make sure the index starts at 1
	 */
	public void printContents()
	{
		int index = 1;
		// goes through each content and prints it out with its index number
		for(AudioContent content : contents){
			System.out.print(index+". ");
			index++;
			content.printInfo();
			System.out.println();
		}
	}
	
	// Play all the AudioContent in the contents list
	public void playAll(){
		// goes through each content and plays all of its audio content
		for(AudioContent content : contents){
			content.printInfo();
			content.play();
			System.out.println();
		}
	}
	
	// Play the specific AudioContent from the contents array list.
	// First make sure the index is in the correct range. 
	public void play(int index)
	{
		// plays audio content if it is within bounds
		if(this.contains(index)){
			contents.get(index).printInfo();
			contents.get(index).play();
		}
	}
	
	public boolean contains(int index)
	{
		// checks if given index is within bounds
		return index >= 1 && index <= contents.size();
	}
	
	// Two Playlists are equal if their titles are equal
	public boolean equals(Object other)
	{
		// makes a playlist object
		Playlist pl = (Playlist)other;
		// returns true if thier titles are equal
		if(this.title.equals(pl.title)){
			return true;
		}
		// else returns false
		return false;
	}
	
	// Given an index of an audio content object in contents array list,
	// remove the audio content object from the array list
	// Hint: use the contains() method above to check if the index is valid
	// The given index is 1-indexed so convert to 0-indexing before removing
	public void deleteContent(int index)
	{
		// checks if index is not within bounds
		if (!contains(index)) {
			return;
		}
		// else removes content at given index
		contents.remove(index-1);
	}
	
	
}
