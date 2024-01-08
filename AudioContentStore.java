// Name: Jeel Vekaria
// Student ID: 501170561
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.w3c.dom.Text;


// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents; 
		private HashMap<String, Integer> contentMap;
		private HashMap<String, ArrayList<Integer>> artistMap;
		private HashMap<String, ArrayList<Integer>> genreMap;
		
		public AudioContentStore()
		{
			contents = getAudioContentList("store.txt");
			contentMap = getContentIndexes(contents);
			artistMap = getArtistIndexes(contents);
			genreMap = getGenreIndexes(contents);
					
		}
		
		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		
		// prints content with given song/book title
		public void printContentIndex(String title){
			if(!contentMap.containsKey(title)){
				throw new TextNotFoundException(title);
			}
			// gets index of title in hashmap
			int pos = contentMap.get(title);

			// prints its index and content info
			System.out.print(pos+". ");
			contents.get(pos-1).printInfo();
		}
		
		// prints content with given artist name
		public void printArtistIndex(String artist){
			// gets arraylist of indexes with content related to artist
			if(!artistMap.containsKey(artist)){
				throw new TextNotFoundException(artist);
			}
			ArrayList<Integer> pos = artistMap.get(artist);
			// prints out index with each songs artist is related to
			for(int position: pos){
				System.out.print(position+". ");
				contents.get(position-1).printInfo();
				System.out.println();
			}
		}
		
		
		// prints content with given genre
		public void printGenreIndex(String genre){
			// gets arraylist of indexes with content with given genre
			if(!genreMap.containsKey(genre)){
				throw new TextNotFoundException(genre);
			}
			ArrayList<Integer> pos = genreMap.get(genre);
			// prints out index with each songs with given genre
			for(int position: pos){
				System.out.print(position+". ");
				contents.get(position-1).printInfo();
				System.out.println();
			}
		}

		// checks if input is partially similar to text in contents
		public void printSimilarText(String text){
			int counter = 1;
			// compare string holds ALL information about each content
			String compare = "";
			boolean exist = false;
			// goes through each content
			for(AudioContent content: contents){
				// if content is a song, grabs all its information from toString and puts into compare
				if(content.getType().equalsIgnoreCase("SONG")){
					Song song = (Song) content;
					compare= song.toString();
				}
				// if content is an audiobook, grabs all its information from toString and puts into compare
				else if(content.getType().equalsIgnoreCase("AUDIOBOOK")){
					AudioBook book = (AudioBook) content;
					compare= book.toString();
				}
				// checks if given input is contained in any contents
				if(compare.contains(text)){
					// if its contained in any contents, that content gets printed out
					System.out.print(counter+". ");
					content.printInfo();
					exist = true;
				}
				// index for the content
				counter++;
			}
			// throws error if text is not contained in any contents
			if(!exist){
				throw new TextNotFoundException(text);
			}
		}

		public HashMap<String, ArrayList<Integer>> getGenreIndexes(ArrayList<AudioContent> media){
			// initializing hashmap, arraylist of integers, and name of genre
			HashMap<String, ArrayList<Integer>> genreIndexes = new HashMap<String, ArrayList<Integer>>();
			String name = "";
			int counter = 0;
			// goes through each content
			for(AudioContent mediaCon : media){
				counter++;
				if(mediaCon.getType().equalsIgnoreCase("SONG")){
					// if content is a song, save genre name and add their index+1 into arraylist
					Song song = (Song) mediaCon;
					if(song.getGenre() == Song.Genre.POP) name = "POP";
					else if(song.getGenre() == Song.Genre.ROCK) name = "ROCK";
					else if(song.getGenre() == Song.Genre.JAZZ) name = "JAZZ";
					else if(song.getGenre() == Song.Genre.CLASSICAL) name = "CLASSICAL";
					else if(song.getGenre() == Song.Genre.HIPHOP) name = "HIPHOP";
					else if(song.getGenre() == Song.Genre.RAP) name = "RAP";

					// if genre is not in hashmap, add that artist and index into it
					if(!genreIndexes.containsKey(name)){
						genreIndexes.put(name, new ArrayList<Integer>());
					}
					genreIndexes.get(name).add(counter);
				}
			}
			return genreIndexes;
		}

		private HashMap<String, ArrayList<Integer>> getArtistIndexes(ArrayList<AudioContent> media){
			// initializing hashmap, arraylist of integers, and name of artist
			HashMap<String, ArrayList<Integer>> artistIndexes = new HashMap<String, ArrayList<Integer>>();
			String name = "";
			int counter = 0;
			for(AudioContent con: media){
					counter++;
					// gets name of artist if content type is song
					if(con.getType().equalsIgnoreCase("SONG")){
						Song s = (Song) con;
						name = s.getArtist();
					}
					// gets name of author if content type is audiobook
					else if(con.getType().equalsIgnoreCase("AUDIOBOOK")){
						AudioBook a = (AudioBook) con;
						name = a.getAuthor();
					}
					if(!artistIndexes.containsKey(name)){
						artistIndexes.put(name, new ArrayList<Integer>());
					}
					artistIndexes.get(name).add(counter);
			}
			return artistIndexes;
		}




		// gets each index of the content saved into a hashmap
		private HashMap<String, Integer> getContentIndexes(ArrayList<AudioContent> media){
			// initializiing a hasmap with String and Integer in its parameters
			HashMap<String, Integer> contentIndexes = new HashMap<String, Integer>();

			// goes through each content and puts the tite as a key and its index as its value
			for(int i=0; i<media.size(); i++){
				String title = media.get(i).getTitle();
				contentIndexes.put(title,i+1);
			}
			
			return contentIndexes;
		}


		// returns a list of the AudioContents
		private ArrayList<AudioContent> getAudioContentList(String filename){
			// ArrayList holding the contents
			ArrayList<AudioContent> mediaContents = new ArrayList<AudioContent>();
			// try/catch
			try{
				// opens the file "store.txt"
				File storeContent = new File(filename);
				Scanner in = new Scanner(storeContent);
				// Scans through each line
				while(in.hasNextLine()) {
					// Stores information based on the audio content
					String type = in.nextLine();
					String id = in.nextLine();
					String title = in.nextLine();
					int year = in.nextInt();
					int length = in.nextInt();
					in.nextLine();
					String artistOrAuthor = in.nextLine();
					String composerOrNarrator = in.nextLine();

					// if type is song, add only song components (eg. genre)
					if(type.equals("SONG")){
						System.out.println("Loading SONG");
						String gen = in.nextLine();
						Song.Genre genre = Song.Genre.POP;

						// gets genre of song
						if(gen.equalsIgnoreCase("POP")) genre = Song.Genre.POP;
						else if(gen.equalsIgnoreCase("ROCK")) genre = Song.Genre.ROCK;
						else if(gen.equalsIgnoreCase("JAZZ")) genre = Song.Genre.JAZZ;
						else if(gen.equalsIgnoreCase("CLASSICAL")) genre = Song.Genre.CLASSICAL;
						else if(gen.equalsIgnoreCase("HIPHOP")) genre = Song.Genre.HIPHOP;
						else if(gen.equalsIgnoreCase("RAP")) genre = Song.Genre.RAP;
						int lines = in.nextInt();

						// saves song contents into lyrics
						String lyrics = in.nextLine()+"\n";
						for(int i=0; i<lines; i++){
							lyrics += in.nextLine()+"\n";
						}
						// creates a song object and adds it to contents
						Song song = new Song(title, year, id, type, lyrics, length, artistOrAuthor, composerOrNarrator, genre, lyrics);
						mediaContents.add(song);
					}
					// checks if type is audiobook, add only audiobook components (eg. chapters)
					else if(type.equals("AUDIOBOOK")){
						System.out.println("Loading AUDIOBOOK");
						int titlesAmount = in.nextInt();
						in.nextLine();
						// arraylists of chapter titles and thier contents
						ArrayList<String> titles = new ArrayList<String>();
						ArrayList<String> text = new ArrayList<String>();
						// textfile will hold the chapters to be added into text
						String textfile = "";

						// adds each title into titles arraylist
						for(int i = 0; i < titlesAmount; i++){
							titles.add(in.nextLine());
						}

						// adds each chapter into text
						for(int i = 0; i < titlesAmount; i++){
							int textLines = in.nextInt();
							in.nextLine();
							for(int j = 0; j < textLines; j++){
								textfile += in.nextLine()+"\n";
							}
							text.add(textfile);
							textfile = "";
						}
						// creates an audiobook object and adds it to contents
						AudioBook book = new AudioBook(title, year, id, type, "", length, artistOrAuthor, composerOrNarrator, titles, text);
						mediaContents.add(book);
					}
				}
				in.close();
			}
			// catches any IO Exceptions and exits program
			catch (FileNotFoundException e) {
				System.out.println("Error processing file: "+e.getMessage());
				System.exit(1);
			}

			// returns arraylist of audiocontents
			return mediaContents;
		}


			public ArrayList<Integer> getArtistsList(String name){
				ArrayList<Integer> list = new ArrayList<Integer>();
				// appends list with indexes of artist if it exists
				if(artistMap.containsKey(name)){
					list = artistMap.get(name);
				}
				// adds -1 if artist name given by user input does not exist
				else{
					list.add(-1);
				}
				return list;
			}
			
			public ArrayList<Integer> getGenresList(String name){
				ArrayList<Integer> list = new ArrayList<Integer>();
				// appends list with indexes of genres if it exists
				if(genreMap.containsKey(name)){
					list = (genreMap.get(name));
				}
				// adds -1 if genre name given by user input does not exist
				else{
					list.add(-1);
				}
				return list;
			}
			// getters and setters
	
			public HashMap<String, Integer> getContentMap() {
				return contentMap;
			}
	
	
			public void setContentMap(HashMap<String, Integer> contentMap) {
				this.contentMap = contentMap;
			}
	
	
			public HashMap<String, ArrayList<Integer>> getArtistMap() {
				return artistMap;
			}
	
	
			public void setArtistMap(HashMap<String, ArrayList<Integer>> artistMap) {
				this.artistMap = artistMap;
			}
	
	
			public HashMap<String, ArrayList<Integer>> getGenreMap() {
				return genreMap;
			}
	
	
			public void setGenreMap(HashMap<String, ArrayList<Integer>> genreMap) {
				this.genreMap = genreMap;
			}
}
