// Name: Jeel Vekaria
// Student ID: 501170561

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{

		// checks if content is a Song type
		if(content == null){
			throw new AudioContentNotFoundException();
		}
		if(content.getType().equals(Song.TYPENAME)){
			Song song = (Song)content;
			// if song already in songs array list, returns false
			if(songs.contains(content)){
				throw new AudioContentAlreadyDownloadedException(Song.TYPENAME, song.getTitle());
			}
			// else it will add content to the songs arraylist
			songs.add(song);
		}
		
		// checks if content is a AudioBook type
		else if(content.getType().equals(AudioBook.TYPENAME)){
			AudioBook book = (AudioBook)content;
			if(audiobooks.contains(content)){
				// if audiobook already in audiobooks array list, returns false
				// errorMsg = "AudioBook already downloaded";
				throw new AudioContentAlreadyDownloadedException(AudioBook.TYPENAME, book.getTitle());

			}
			// else it will add content to the audiobooks arraylist
			audiobooks.add(book);
		}
		System.out.println(content.getType()+" "+content.getTitle()+" Added to Library");
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
		
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	// public void listAllPodcasts()
	// {
	// 	int counter = 1;
	// 	for(Podcast podcast: podcasts){
	// 		System.out.print(counter+". ");
	// 		podcast.printInfo();
	// 		counter++;
	// 	}
	// }
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		int counter = 1;
		for(Playlist playl: playlists){
			System.out.println(counter+". "+playl.getTitle());
			counter++;
		}
		
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList <String> artists = new ArrayList<String>();

		// goes through each song downloaded
		for(Song song : songs){
			// if artist of the song isn't in the playlist, they will be added
			if(!artists.contains(song.getArtist())){
				artists.add(song.getArtist());
			}
		}
		// prints out artist names
		int counter = 1;
		for(String artist : artists){
			System.out.println(counter+". "+artist);
			counter++;
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{
		// checks if index isn't within bounds
		if(index<1 || index> songs.size()){
			throw new AudioContentNotFoundException(index);
		}

		int songIndex = 1;
		ArrayList<AudioContent> content = new ArrayList<AudioContent>();

		// saves song to be deleted
		Song songGone = songs.get(index-1);
	
		// goes through every playlist
		for(Playlist playlist: playlists){
			// saves audio contents of playlist into content
			 content = playlist.getContent();
			//  goes through each content to see if its equal to the song to be deleted
			 for(AudioContent s: content){
				 if(s.equals(songGone)){
					// removes song from playlist
					 playlist.deleteContent(songIndex);
				}
				songIndex++;
			 }
			 songIndex = 0;
		}
		// removes song from downloads
		songs.remove(index-1);
		return;
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song s1, Song s2) {
			// returns -1 if s1 year is less than s2 year
			if (s1.getYear() < s2.getYear()){
				return -1;
			}
			// returns 1 if s1 year is greater than s2 year
			else if (s1.getYear() > s2.getYear())
				return 1;
			// else return 0
			return 0;
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song s1, Song s2) {
			// returns -1 if s1 length is less than s2 length
			if (s1.getLength() < s2.getLength()){
				return -1;
			}
			// returns 1 if s1 length is greater than s2 length
			else if (s1.getLength() > s2.getLength())
				return 1;
			// else return 0
			return 0;
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		// Checks if index is out of bounds
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFoundException(index);
		}
		// else it plays song
		songs.get(index-1).printInfo();
		songs.get(index-1).play();
		return;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		// checks if index is out of bounds
		if (index < 1 || index > audiobooks.size()){
			throw new AudioContentNotFoundException(index);
		}
		// checks if chapter is out of bounds
		else if(chapter<1 || chapter>audiobooks.get(index-1).getChapterTitles().size()){
			throw new AudioContentNotFoundException(index);
		}

		// else plays audiobook at given chapter
		AudioBook book = audiobooks.get(index-1);
		book.selectChapter(chapter);
		book.printInfo();
		book.play();
		return;
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		// checks if index is out of bounds
		if (index < 1 || index > audiobooks.size()){
			throw new AudioContentNotFoundException(index);
		}
		
		// else prints chapter titles of an audiobook
		AudioBook book = audiobooks.get(index-1);
		book.printTOC();
		return;
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		// checks if playlist with given title already exists in playlists array
		for(Playlist playlist :playlists){
			if(playlist.getTitle().equals(title)){
			throw new AudioContentAlreadyExistsException(title);
			}
		}

		// else adds playlist with given title to playlists array
		playlists.add(new Playlist(title));
		return;
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		// loops thorugh each playlist
		for(Playlist playlist : playlists){
			// if playlist with given title exists, ir prints its content information
			if(playlist.getTitle().equals(title)){
				playlist.printContents();
				return;
			}
		}
		// else sets an error msg and returns false
		throw new AudioContentNotFoundException(title);
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		// loops through each playlist
		for(Playlist playlist : playlists){
			// if playlist with given title exsits in playlists array, it prints all of its content
			if(playlist.getTitle().equals(playlistTitle)){
				playlist.playAll();
				return;
			}
		}
		throw new AudioContentNotFoundException(playlistTitle);
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL){
		// goes through each playlist
		for(Playlist playlist : playlists){
			// if playlist with given title exists in playlists array, moves to nested if statement
			if(playlist.getTitle().equals(playlistTitle)){
				// if index is out of bounds, sets errorMsg and return false 
				if(indexInPL < 1 || indexInPL > playlist.getContent().size()){
					throw new AudioContentNotFoundException(indexInPL);
				}
				// else it prints playlist title and play its specific content with given indexInPL
				System.out.println(playlist.getTitle());
				playlist.play(indexInPL-1);
				return;
			}
		}
		// sets errorMsg and returns false
		throw new AudioContentNotFoundException(playlistTitle);
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		// checks if given type is a song
		if(type.equalsIgnoreCase(Song.TYPENAME)){
			// checks if given index is out of bounds
			if(index <1 || index > songs.size()){
				throw new AudioContentNotFoundException(index);
			}
			// else goes through each playlist
			for(int i=0; i<playlistTitle.length();i++){
				// if a playlist with given playlistTitle exists, adds song to that playlist
				if(playlists.get(i).getTitle().equals(playlistTitle)){
					playlists.get(i).addContent(songs.get(index-1));
					return;
				}
			}
			// if playlist with given playlistTitle does not exist, sets erorMsg and returns false
			throw new AudioContentNotFoundException(playlistTitle);
		}
		// checks if given type is an audiobook
		else if(type.equalsIgnoreCase(AudioBook.TYPENAME)){
			// checks if given index is out of bounds
			if(index <1 || index > audiobooks.size()){
				throw new AudioContentNotFoundException(playlistTitle);
			}
			// else goes through each playlist
			for(int i=0; i<playlistTitle.length();i++){
				// if a playlist with given playlistTitle exists, adds song to that playlist
				if(playlists.get(i).getTitle().equals(playlistTitle)){
					playlists.get(i).addContent(audiobooks.get(index-1));
					return;
				}
			}
			// if playlist with given playlistTitle does not exist, sets erorMsg and returns false
			throw new AudioContentNotFoundException(playlistTitle);
		}
		// checks if given type is a podcast
		// else if(type.equalsIgnoreCase(Podcast.TYPENAME)){
		// 	// checks if given index is out of bounds
		// 	if(index <1 || index > podcasts.size()){
		// 		errorMsg = "Podcast not found.";
		// 		return false;
		// 	}
		// 	// else goes through each playlist
		// 	for(int i=0; i<playlistTitle.length();i++){
		// 		// if a playlist with given playlistTitle exists, adds song to that playlist
		// 		if(playlists.get(i).getTitle().equals(playlistTitle)){
		// 			playlists.get(i).addContent(podcasts.get(index-1));
		// 			return true;
		// 		}
		// 	}
		// 	// if playlist with given playlistTitle does not exist, sets erorMsg and returns false
		// 	errorMsg = "Playlist not found.";
		// 	return false;
		// }
		throw new AudioContentNotFoundException(playlistTitle);

	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		// goes through each playlist and comapres title
		for(int i=0; i<playlists.size();i++){
			// if playlist title matches given title, it deletes content as given index
			if(playlists.get(i).getTitle().equals(title)){
				playlists.get(i).deleteContent(index);
				return;
			}
		}
		throw new AudioContentNotFoundException(index);
	}
	
}

// error for when audio content already downloaded
class AudioContentAlreadyDownloadedException extends RuntimeException {
	public AudioContentAlreadyDownloadedException(){}
	public AudioContentAlreadyDownloadedException(String type, String content){
		super(type+" "+content+" already downloaded");
	}
}

// error for when content could not be found
class AudioContentNotFoundException extends RuntimeException {
	public AudioContentNotFoundException(){}
	public AudioContentNotFoundException(int index){
		super("Audio Content at Index "+index+" Could Not Be Found.");
	}
	public AudioContentNotFoundException(String title){
		super("Audio Content "+title+" Could Not Be Found.");
	}
}

// error if playlist already exists with same name
class AudioContentAlreadyExistsException extends RuntimeException {
	public AudioContentAlreadyExistsException(){}
	public AudioContentAlreadyExistsException(String title){
		super("Audio Content Playlist "+title+" Already Exists");
	}
}

class TextNotFoundException extends RuntimeException {
	public TextNotFoundException(){}
	public TextNotFoundException(String text){
		super("Could not find "+text+" in any of the contents");
	}
}