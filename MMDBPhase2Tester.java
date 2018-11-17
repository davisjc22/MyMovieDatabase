
import java.io.FileWriter;
import java.io.PrintWriter;

//import Complete.CounterMap;
//import Complete.MyTreeMap;


public class MMDBPhase2Tester {

	public static void main(String[] args) {
		try{
			String FILENAME = "H:/MMDBPhase2.txt";
			FileWriter file = new FileWriter(FILENAME);
			PrintWriter out = new PrintWriter(file);
			
			out.println(output());
			out.println( "~~~~~~~~~~~ My Tree Map~~~~~~~~~~~~~~~~");
			MyTreeMap mappy = new MyTreeMap();
			mappy.put("keyA","A1");
			mappy.put("keyA","A2");
			mappy.put("keyA","A3");
			mappy.put("keyA","A4");
			mappy.put("keyB","B1");
			mappy.put("keyB","B2");
			mappy.put("keyC","C1");
			mappy.put("keyC","C2");
			mappy.put("keyC","C3");
			mappy.put("keyA","A5");
			out.println(mappy);
			out.println("Looping through");
			for(Comparable word: mappy.keyList()){
				out.println(word+"-->"+mappy.get(word));
			}
			
			out.println( "~~~~~~~~~~~ Counter Map~~~~~~~~~~~~~~~~");
			String[] words = {"here","are","some","random","words","i","hope","your","code","works","words","are","fun","when","your","code","works","i","hope","you","don't","fail","i","am","tired","of","making","up","random","words"};
			CounterMap mappy2 = new CounterMap();
			for(String w: words)
				mappy2.put(w);
			
			for(Comparable key: mappy2.keyList())
				out.println(key+"-->"+mappy2.get(key));
			
			out.println("INVERSION");
			out.println(mappy2.inversion());
			
			out.close();
			System.out.println("Done generating file");
			System.out.println("Now go to iSchool and upload it");
			System.out.println("You can find the file @ "+FILENAME);
		}
		catch(Exception ex){
			System.out.println("ERROR!");
			ex.printStackTrace();
		}

	}
	
	public static String output(){
		char[] a = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		long tm = System.currentTimeMillis();
		String n =System.getProperty("user.name"); 
		int x = (int)(tm%10);
		for(int i=x-1; i>0; i--)
			x*=i;
		int x2 = 0;
		for(int i=0; i<a.length; i++)
			if(n.toLowerCase().charAt(0)==a[i] || n.toLowerCase().charAt(n.length()-1)==a[i]){
				x2+=i;		
			}
		
	//	try{Thread.sleep(x2);}catch(Exception ex){ex.printStackTrace();}
				
		return "~~~~~~~~~~~~~~~~~~~"+n+":"+x2+" : "+tm+":"+x+"~~~~~~~~~~~~~~~~~~~~~";
	}

}
