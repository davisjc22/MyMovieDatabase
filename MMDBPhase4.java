
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import javax.swing.JOptionPane;

public class MMDBPhase4 {
	final static String TAGFILE = "./src/top250Tags.txt";
	static String MovieTitle = "The Godfather (1972)";//<--just change this to check for different movies
	
	//***********HERE YOU NEED TO Declare TWO data structures
	//  1) key = title  ;  value = set of tags for that movie
	//  2) key = tag    ;  value = set of movies with those tags
	/** make them both static variables for now **/
	static MyTreeMap titleTree;
	static MyTreeMap tagTree;
	
	public static void main(String[] args) {
		//0) instantiate your data structures
		
		titleTree = new MyTreeMap();
		tagTree = new MyTreeMap();
		
		//1) read the file & fill your two data structures
		

		readTags();
		
		//2) generate a data structure that will hold # of hits for diff movies
		

		
		CounterMap hits = getRecommendations(JOptionPane.showInputDialog("Enter a Movie Title"));
		//3) display the results:
		//		* descending order by percent
		//      * each movie on its own line
		displayRecResults(hits);
		

	}
	
	public static void readTags(){
		//this function will need to fill up BOTH of your MyTreeMaps!
		try{
			
			FileReader reeder = new FileReader(new File(TAGFILE));
			BufferedReader br = new BufferedReader(reeder);
			String line = br.readLine();			
			while(line!=null){
				System.out.println("Just read:"+line);
				/** ~~~~~~~~~~~~ DO SOMETHING HERE ~~~~~~~~~~~~~~~~~**/
				
				String title = line.substring(0, line.indexOf("("));	//splits the line between the title and the tag
				String tag = line.substring(line.indexOf(")")+1);
				title = title.trim();
				tag = tag.trim();
				titleTree.put(title, tag);	//adds it to the map with titles as the key
				tagTree.put(tag, title);	//adds it to the map with tags as the key
				
				/** ~~~~~~~~~~~~ ~~~~~~~~~~~~~~~~~ ~~~~~~~~~~~~~~~~~**/
				//last line of loop
				line = br.readLine();
			}//done reading
			br.close();
			
			
		}
		catch(Exception ex){ex.printStackTrace();}
		
		
	}
	
	//returns a data structure where:
	//  key=movie title  ; value = number of tags this movie shares with the requested movie
	public static CounterMap getRecommendations(String requestedMovie){
		CounterMap results=new CounterMap();
		
		requestedMovie = requestedMovie.trim();
		MovieTitle = requestedMovie;
		
		
		System.out.println("SEARCHED FOR: " + requestedMovie);
		
		Set<String> tags = titleTree.get(requestedMovie);//makes a set of tags based off of a search for titles containing the 1 word
		Set<String> titles;
		for(String s: tags){//loops through the set of tags
			titles = tagTree.get(s);//takes in a set of titles with that tag
			for(String t : titles){
				results.put(t);//adds the title to the countermap. If it's already there, adds 1 to it's count
			}
		}
		
		
		
		return results;
	}
	
	//prints recommendations: 1 movie per line & in descending order by percentage
	public static void displayRecResults( CounterMap hits ){			/*************LEFT OFF HERE*************/
		int percentMatch;											/**FIND MATCHING TAGS****/
		int tagmatch = 0;
		MyTreeMap toDisplay = hits.inversion();
	//	System.out.println(toDisplay);
		System.out.println("Reccomendations");
		ArrayList<Comparable> listMatches = toDisplay.keyList();//a bunch of numbers
		for(int i = 0; i< listMatches.size(); i++){//loops though matches
			Integer number=(Integer)listMatches.get(i);
			for(String s: toDisplay.get(number)){//loops through tags of a match
				//System.out.println( number+":"+s);						
				percentMatch = (int) (100*((double)number/titleTree.get(MovieTitle).size())); //calculates how many tags are the same as the entry, makes it a percent
				System.out.println(percentMatch+" %\t" + s);	// prints the percentage match along with the movie title
			}
			
		}
		
	}

}
