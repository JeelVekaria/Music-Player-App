// Name: Jeel Vekaria
// Student ID: 501170561
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI {
	public static void main(String[] args) {
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your
		// mylibrary
		AudioContentStore store = new AudioContentStore();

		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine()) {
			try{
				String action = scanner.nextLine();

				if (action == null || action.equals("")) {
					System.out.print("\n>");
					continue;
				} else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;

				else if (action.equalsIgnoreCase("STORE")) // List all songs
				{
					store.listAll();
				} else if (action.equalsIgnoreCase("SONGS")) // List all songs
				{
					mylibrary.listAllSongs();
				} else if (action.equalsIgnoreCase("BOOKS")) // List all songs
				{
					mylibrary.listAllAudioBooks();
				} 
				else if (action.equalsIgnoreCase("ARTISTS")) // List all songs
				{
					mylibrary.listAllArtists();
				} else if (action.equalsIgnoreCase("PLAYLISTS")) // List all play lists
				{
					mylibrary.listAllPlaylists();
				}
				// Download audiocontent (song/audiobook/podcast) from the store
				// Specify the index of the content

				else if (action.equalsIgnoreCase("DOWNLOAD")) {
					int index = 0;
					int index2 = 0;

					// gets 2 inputs from user, starting and ending index
					System.out.print("From Store Content #: ");
					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						System.out.print("To Store Content #: ");
						if (scanner.hasNextInt()) {
							index2 = scanner.nextInt();
						}
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					// goes thorugh each content
					for(int i=index; i<index2+1; i++){
						try{
							AudioContent content = store.getContent(i);
							mylibrary.download(content);
						}
						// error when content already downloaded
						catch(AudioContentAlreadyDownloadedException e){
							System.out.println(e.getMessage());
						}
						// error when there is invalid input, eg. -1, String, 10000
						catch(AudioContentNotFoundException e){
							System.out.println("Error downloading audio content beyond this point.");
							break;
						}
					}
				}
				// downloads songs with given artist/author name
				else if(action.equalsIgnoreCase("DOWNLOADA")){
					String artist = "";
					System.out.print("Artist: ");
					if(scanner.hasNextLine()){
						artist = scanner.nextLine();
						// tries to download content with an artist/author name
						ArrayList<Integer> artistList = store.getArtistsList(artist);
						// gets position of each content with related artist/author
						for(Integer position : artistList){
							try{
								AudioContent content = store.getContent(position);
								mylibrary.download(content);
							}
							// error when content already downloaded
							catch(AudioContentAlreadyDownloadedException e){
								System.out.println(e.getMessage());
							}
							// if inputed artist/author doesn't exist, error occurs
							catch(AudioContentNotFoundException e){
								System.out.println("No matches for "+artist);
							}
						}
					}
				}
				
				else if(action.equalsIgnoreCase("DOWNLOADG")){
					String genre = "";
					System.out.print("Genre: ");
					if(scanner.hasNextLine()){
						genre = scanner.nextLine().toUpperCase();
						// tries to download content with given genre
						ArrayList<Integer> genreList = store.getGenresList(genre);
						// gets position of each content with related artist/author
						for(Integer position : genreList){
								try{
								AudioContent content = store.getContent(position);
								mylibrary.download(content);
								}
								catch(AudioContentAlreadyDownloadedException e){
									System.out.println(e.getMessage());
								}
								// if inputed genre is not in playlist, error occurs
								catch(AudioContentNotFoundException e){
									System.out.println("Couldn't find " + genre +" genre in the library.");
								}
							}
						// error when content already downloaded
					}
				}



				// Get the *library* index (index of a song based on the songs list)
				// of a song from the keyboard and play the song
				else if (action.equalsIgnoreCase("PLAYSONG")) {
					// Print error message if the song doesn't exist in the library
					int index = 0;

					System.out.print("Song Number: ");
					if (scanner.hasNextInt()) {
						// saves user input into index
						index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
						// calls method and either performs give task or returns an error
						mylibrary.playSong(index);
					}
				}
				// Print the table of contents (TOC) of an audiobook that
				// has been downloaded to the library. Get the desired book index
				// from the keyboard - the index is based on the list of books in the library
				else if (action.equalsIgnoreCase("BOOKTOC")) {
					// Print error message if the book doesn't exist in the library
					int index = 0;

					System.out.print("Audio Book Number: ");
					if (scanner.hasNextInt()) {
						// saves user input into index
						index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
						// calls method and either performs give task or returns an error
						mylibrary.printAudioBookTOC(index);
					}
				}
				// Similar to playsong above except for audio book
				// In addition to the book index, read the chapter
				// number from the keyboard - see class Library
				else if (action.equalsIgnoreCase("PLAYBOOK")) {
					// Print error message if the book doesn't exist in the library
					int index, chapter = 0;

					System.out.print("Audio Book Number: ");
					if (scanner.hasNextInt()) {
						// saves user input into index
						index = scanner.nextInt();
						System.out.print("Chapter: ");
						if (scanner.hasNextInt()) {
							// saves user input into chapter
							chapter = scanner.nextInt();
							scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
						}
						// calls method and either performs give task or returns an error
						mylibrary.playAudioBook(index, chapter);
					}
				}

				// Specify a playlist title (string)
				// Play all the audio content (songs, audiobooks, podcasts) of the playlist
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYALLPL")) {
					String plTitle = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						// saves user input into plTitle
						plTitle = scanner.nextLine();
						// calls method and either performs give task or returns an error
						mylibrary.playPlaylist(plTitle);
					}
				}
				// Specify a playlist title (string)
				// Read the index of a song/audiobook/podcast in the playist from the keyboard
				// Play all the audio content
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYPL")) {
					String plTitle = "";
					int contentIndex = 0;
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						// saves user input into plTitle
						plTitle = scanner.nextLine();
						System.out.print("Content Number: ");
						if (scanner.hasNextInt()) {
							// saves user input into contentIndex
							contentIndex = scanner.nextInt();
							scanner.nextLine();
						}
						// calls method and either performs give task or returns an error
						mylibrary.playPlaylist(plTitle, contentIndex);
					}

				}
				// Delete a song from the list of songs in mylibrary and any play lists it
				// belongs to
				// Read a song index from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELSONG")) {
					int index = 0;
					System.out.print("Library Song #: ");
					if (scanner.hasNextInt()) {
						// saves user input into index
						index = scanner.nextInt();
						scanner.nextLine();
						// calls method and either performs give task or returns an error
						mylibrary.deleteSong(index);
					}
				}
				// Read a title string from the keyboard and make a playlist
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("MAKEPL")) {
					String plTitle = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						// saves user input into plTitle
						plTitle = scanner.nextLine();
						// calls method and either performs give task or returns an error
						mylibrary.makePlaylist(plTitle);
					}
				}
				// Print the content information (songs, audiobooks, podcasts) in the playlist
				// Read a playlist title string from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PRINTPL")) // print playlist content
				{
					String plTitle = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						// saves user input into plTitle
						plTitle = scanner.next();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
						// calls method and either performs give task or returns an error
						mylibrary.printPlaylist(plTitle);
					}
				}
				// Add content (song, audiobook, podcast) from mylibrary (via index) to a
				// playlist
				// Read the playlist title, the type of content ("song" "audiobook" "podcast")
				// and the index of the content (based on song list, audiobook list etc) from
				// the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("ADDTOPL")) {
					System.out.print("Playlist Title: ");
					int index = 0;
					String plTitle, contentType = "";
					if (scanner.hasNext()) {
						// saves user input into plTitle
						plTitle = scanner.nextLine();
						System.out.print("Content Type [SONG, AUDIOBOOK]: ");
						// saves user input into contentType
						contentType = scanner.nextLine();
						System.out.print("Library Content #: ");
						if (scanner.hasNextInt()) {
							// saves user input into index
							index = scanner.nextInt();
							scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
						}
						// calls method and either performs give task or returns an error
						mylibrary.addContentToPlaylist(contentType, index, plTitle);
					}
				}
				// Delete content from play list based on index from the playlist
				// Read the playlist title string and the playlist index
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELFROMPL")) {
					String plTitle = "";
					int contentIndex = 0;
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						// saves user input into plTitle
						plTitle = scanner.nextLine();
						System.out.print("Content Number: ");
						if (scanner.hasNextInt()) {
							// saves user input into contentIndex
							contentIndex = scanner.nextInt();
							scanner.nextLine();
						}
						// calls method and either performs give task or returns an error
						mylibrary.delContentFromPlaylist(contentIndex, plTitle);
					}
				}

				else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
				{
					mylibrary.sortSongsByYear();
				} else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
				{
					mylibrary.sortSongsByName();
				} else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
				{
					mylibrary.sortSongsByLength();
				}


				// Updated methods for assignment 2 below


				// searches for content with given title
				else if (action.equalsIgnoreCase("SEARCH")){
					String title = "";
					System.out.print("Title: ");
					if(scanner.hasNext()){
						title = scanner.nextLine();
						try{
							store.printContentIndex(title);
						} catch (TextNotFoundException e){
							System.out.println("No matches for "+title);
						}
					}
				}
				
				else if(action.equalsIgnoreCase("SEARCHA")){
					String artist = "";
					System.out.print("Artist: ");
					if(scanner.hasNext()){
						artist = scanner.nextLine();
						try{
							store.printArtistIndex(artist);
						} catch(TextNotFoundException e){
							System.out.println("No matches for "+artist);
						}
					}
				}
				
				else if(action.equalsIgnoreCase("SEARCHG")){
					String genre = "";
					System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
					if(scanner.hasNext()){
						genre = scanner.nextLine().toUpperCase();
						try{
							store.printGenreIndex(genre);
						} catch(TextNotFoundException e){
							System.out.println("No matches for "+genre);
						}
					}
				}

				else if(action.equalsIgnoreCase("SEARCHP")){
					String text = "";
					System.out.print("Enter text: ");
					if(scanner.hasNext()){
						text = scanner.nextLine();
						try{
							store.printSimilarText(text);
						} 
						catch(TextNotFoundException e){
							System.out.println(e.getMessage());
						}
					}
				}




				System.out.print("\n>");
				
			}

			// unique exceptions
			// when song or audiobook already downloaded
			catch(AudioContentAlreadyDownloadedException exception){
				System.out.println(exception.getMessage()+"\n>");
			}
			
			// when song or audiobook does not exist
			catch(AudioContentNotFoundException exception){
				System.out.println(exception.getMessage()+"\n>");
			}
			
			// when playlist is already created with same name
			catch(AudioContentAlreadyExistsException exception){
				System.out.println(exception.getMessage()+"\n>");
			}
			
		}
		scanner.close();
	}
}