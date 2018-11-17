

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import javax.swing.JOptionPane;

public class MMDBPhase3 {
	final static String INPUTFILE = "./src/top250.txt";
	
	public static void main(String[] args) {
		//1) read the file
		ArrayList<String> movies = readTitles();//
		
		//2) ask for user input
		String search = JOptionPane.showInputDialog("Enter a movie title");
		
		//3) generate a data structure that will have their matches
		MyTreeMap mapMatches = getTitleMatches(search, movies);//generates a MyTreeMap of titles that 
															//matched the search entry by the number of 
															//hits that each title had. ex: (4, The Lord of the Rings: The Return of the King (2003))
		
		
		//4) print the title matches
		
		//System.out.println("Matches: \n" + mapMatches); //prints sets of matches all in one line
		
		ArrayList<Comparable> listMatches = mapMatches.keyList();//arraylist of keys (numbers) for mapMatches
		for(int i =0; i< listMatches.size(); i++){//loops through the sets of hits
			System.out.println( listMatches.get(i) + " Hits");//Prints the number of hits that it is looping through
			for(String title  : mapMatches.get(listMatches.get(i))){//loops through each title in the current set of hits
				System.out.println(title);//prints the title itself
			}											/**LEFT OFF HERE**/
		}
		
		
		//		*each on their own line
		//		*in descending order of relevance

	}
	
	public static ArrayList<String> readTitles(){
		ArrayList<String> titles = new ArrayList();
		try{
			
			FileReader reeder = new FileReader(new File(INPUTFILE));
			BufferedReader br = new BufferedReader(reeder);
			String line = br.readLine();
			while(line!=null){
				//System.out.println("Just read:"+line);   
				
				//last line of loop
				line = line.substring(line.indexOf(")")+1);
				line = line.trim();
				//System.out.println(line);
				titles.add(line);//adds the trimmed down title to the ArrayList															/**LEFT OFF HERE**/
				line = br.readLine();
			}//done reading
			br.close();
			
			
		}
		catch(Exception ex){ex.printStackTrace();}
		
		return titles;
	}
	
	   
	public static MyTreeMap getTitleMatches(String userInput, ArrayList<String> actualTitles){
		CounterMap results=new CounterMap();             /**COUNTERMAP TO MYTREEMAP**/
		userInput = userInput.toLowerCase();//makes the user input all lowercase so it can be searched for in the titles
		String[] input = userInput.split(" ");//splits the input into individual key words to search for
		for(String in: input){
			for(String titles: actualTitles){
				if(titles.toLowerCase().contains(in.toLowerCase())){//makes the movie titles lowercase and checks if they contain the key words
					//System.out.println("ADDING "+in);
					results.put(titles); //adds the title if it contians the key words
				}						//adds 1 hit each time it comes across the same title
			}							//the title with the most hits is probably what they're searching for
		}
		
		MyTreeMap re = results.inversion();// puts the results in a TreeMap with the number of hits as the key
			
			
		
		return re;
	}

}
