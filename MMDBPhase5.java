import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class MMDBPhase5 extends JFrame implements ActionListener {
	
	final static String TAGFILE = "./src/top250Tags.txt";
	static String MovieTitle = "The Godfather (1972)";//<--just change this to check for different movies
	private JPanel panel;
	private static Object[][] tableData;
	private JTable myTable;
	private JTextField searchy;
	private static JComboBox combo;
	private String[] columnNames = {"Percent", "Movie"}; 
	static MyTreeMap titleTree;
	static MyTreeMap tagTree;
	private JButton searchbutton;
	
	
	public MMDBPhase5(){
		
		titleTree = new MyTreeMap();
		tagTree = new MyTreeMap();
		combo = new JComboBox();
		
		readTags();
		
		panel = new JPanel( new GridLayout(3,1));//panel that all elements will be on
		searchy = new JTextField();
		
		searchbutton = new JButton("Search");
		searchbutton.addActionListener(this);
		
		combo.addActionListener(this);
		searchy.addActionListener(this);
		tableData = new Object[250][2];
		myTable = new JTable(tableData, columnNames);
		
		
		for(int i=titleTree.keyList().size()-1; i >=0 ; i--){
			combo.addItem(titleTree.keyList().get(i));				/**LEFT OFF HERE*/
		}
		
		//JPanel
		panel.add(searchy);
		panel.add(searchbutton);
		panel.add(combo);
		
		this.add(panel, BorderLayout.NORTH);
		this.add(new JScrollPane(myTable), BorderLayout.CENTER);
		
		
		
		
		
		
		
		this.setSize(400,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		
		
			
		
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
	
	public static void main(String[] args) {new MMDBPhase5();}

	
	
	public void clear(){//clears all the spaces in the table
		for(int r=0; r<tableData.length; r++){
			for(int c=0; c< tableData[0].length; c++){
				tableData[r][c] = null;//clears all windows in table
			}
		}
	}
	
	
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == null)//if nothing was selected, don't do anything
			return;
		
		
		if(arg0.getSource() == combo){
			if(combo.getSelectedItem()==null)//if they click on something other then combo
				return;
			int row = 0;//starts on first row
			
			MovieTitle = (String) combo.getSelectedItem();//saves the selected item from the box
			clear();//clears all windows in table
			displayRecResults(getRecommendations(MovieTitle));
			repaint();
		}
		else if(arg0.getSource() == searchbutton || arg0.getSource() == searchy){//if they use the search box, it'll call getTitleMatches
			clear();//clears the table of the previous results
			combo.removeAllItems();
			getTitleMatches(searchy.getText());
			repaint();
		}
		
		
		
	}
		
	
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
	
	public static void displayRecResults( CounterMap hits ){	
		int r =0;
		int percentMatch;											
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
				tableData[r][0] = (percentMatch + "%\t");
				tableData[r++][1] = s;
				System.out.println(percentMatch+" %\t" + s);	// prints the percentage match along with the movie title
			}
			
		}
		
	}
	
	public static void getTitleMatches(String userInput){
		CounterMap results=new CounterMap();             /**COUNTERMAP TO MYTREEMAP**/
		userInput = userInput.toLowerCase();//makes the user input all lowercase so it can be searched for in the titles
		String[] input = userInput.split(" ");//splits the input into individual key words to search for
		for(String in: input){
			for(Comparable titles: titleTree.keyList()){
				if(((String) titles).toLowerCase().contains(in.toLowerCase())){//makes the movie titles lowercase and checks if they contain the key words
					//System.out.println("ADDING "+in);
					results.put(titles); //adds the title if it contains the key words
				}						//adds 1 hit each time it comes across the same title
			}							//the title with the most hits is probably what they're searching for
		}
		
		int selfhits = input.length;
		int r = 0;
		int percentMatch;
		MyTreeMap re = results.inversion();// puts the results in a TreeMap with the number of hits as the key
		for(Comparable hits : re.keyList()){
			for(String match : re.get(hits)){
				int h = (Integer)hits;
				combo.addItem(match);
//				percentMatch = (int) (100*((double)h/match.split(" ").length)); // calculated by the number of words that were entered out of the total number of words in that match
//				tableData[r][0] = ( percentMatch + "%\t");							//Ex: if searched for "Back", the percent match for "Back to the Future" would be 25%
//				tableData[r++][1] = match;
			}
		}
			
		
	}
}
