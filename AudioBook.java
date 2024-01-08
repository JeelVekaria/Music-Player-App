// Name: Jeel Vekaria
// Student ID: 501170561
import java.util.ArrayList;

/*
 * An AudioBook is a type of AudioContent.
 * It is a recording made available on the internet of a book being read aloud by a narrator
 * 
 */
public class AudioBook extends AudioContent
{
	public static final String TYPENAME =	"AUDIOBOOK";
	
	private String author; 
	private String narrator;
	private ArrayList<String> chapterTitles;
	private ArrayList<String> chapters;
	private int currentChapter = 0;

	
	public AudioBook(String title, int year, String id, String type, String audioFile, int length,
									String author, String narrator, ArrayList<String> chapterTitles, ArrayList<String> chapters)
	{
		// Make use of the constructor in the super class AudioContent. 
		// Initialize additional AudioBook instance variables. 
		super(title, year, id, type, audioFile, length);
		this.author = author;
		this.narrator = narrator;
		this.chapterTitles = chapterTitles;
		this.chapters = chapters;
	}
	
	public String getType()
	{
		return TYPENAME;
	}

  // Print information about the audiobook. First print the basic information of the AudioContent 
	// by making use of the printInfo() method in superclass AudioContent and then print author and narrator
	// see the video
	public void printInfo()
	{
		super.printInfo();
		System.out.println("Author: " + author+" Narrated by: " + narrator);
	}
	
  // Play the audiobook by setting the audioFile to the current chapter title (from chapterTitles array list) 
	// followed by the current chapter (from chapters array list)
	// Then make use of the the play() method of the superclass
	public void play()
	{
		// sets audiofile to chapter title and current chapter from current chapter
		setAudioFile(chapterTitles.get(currentChapter) + ".\n" + chapters.get(currentChapter));
		System.out.println();
		// calls super method for play
		super.play();
	}
	
	// Print the table of contents of the book - i.e. the list of chapter titles
	// See the video
	public void printTOC()
	{
		int index = 1;
		// goes through each chaptertitles and prints it with its index
		for(String chapterTitle : chapterTitles){
			System.out.println("Chapter " + index + ". "+chapterTitle+"\n");
			index++;
		}
	}

	// Select a specific chapter to play - nothing to do here
	public void selectChapter(int chapter)
	{
		if (chapter >= 1 && chapter <= chapters.size())
		{
			currentChapter = chapter - 1;
		}
	}
	
	//Two AudioBooks are equal if their AudioContent information is equal and both the author and narrators are equal
	public boolean equals(Object other)
	{
		AudioBook book = (AudioBook)other;
		if(super.equals(book) && this.author.equals(book.getAuthor()) && this.narrator.equals(book.getNarrator()) && this.chapters == book.getChapters() && this.chapterTitles == book.getChapterTitles()){
			return true;
		}
		return false;
	}
	
	public int getNumberOfChapters()
	{
		return chapters.size();
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getNarrator()
	{
		return narrator;
	}

	public void setNarrator(String narrator)
	{
		this.narrator = narrator;
	}

	public ArrayList<String> getChapterTitles()
	{
		return chapterTitles;
	}

	public void setChapterTitles(ArrayList<String> chapterTitles)
	{
		this.chapterTitles = chapterTitles;
	}

	public ArrayList<String> getChapters()
	{
		return chapters;
	}

	public void setChapters(ArrayList<String> chapters)
	{
		this.chapters = chapters;
	}


	// Method for Bonus Mark
	public String toString() {
		String result = super.getTitle()+" "+super.getYear()+" "+super.getId()+" "+"SONG"+" "+super.getAudioFile()+" "+super.getLength()+" "+author+" "+narrator+" "+currentChapter;
		for(String chaptertitle : chapterTitles){
			result+= " "+chaptertitle;
		}
		for(String chapter: chapters){
			result+= " "+chapter;
		}
		return result;
	}
}
