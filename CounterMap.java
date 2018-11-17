
import java.util.*;
public class CounterMap {
	private NumNode root;
	
	public CounterMap(){root = null;}
	
	public void put(Comparable key){
		Boolean movedRight = false;
		if(isEmpty()){
			root = new NumNode(key);
			return;
		}
		NumNode p = root, c = root;
		while(c!=null){
			if(c.getKey().compareTo(key)>0){
				p=c;
				c = c.getLeft();
				movedRight = false;
			}
			else if(c.getKey().compareTo(key)<0){
				p = c;
				c = c.getRight();
				movedRight = true;
			}
			else{
					c.increment();//if the NumNode has this key, add 1 to the count
					return;
			}
		}
		
		if(movedRight)//if the key isn't in the map, add it to the map
			p.setRight(new NumNode(key));
		else
			p.setLeft(new NumNode(key));
		
		
	}
	
	public NumNode recSearch(NumNode n, Comparable k){
		return null;
	}	
	
	public Integer get(Comparable k){
		NumNode c = root;
		while(c!=null){
			if(c.getKey().compareTo(k) == 0)//checks if it matches
				return c.getCount();
			else if(c.getKey().compareTo(k)>0)
				c = c.getLeft();//goes left if it's to big
			else
				c = c.getRight();
				
		}
		return null;//returns null if it isn't there
	}
	
	public ArrayList<Comparable> keyList(){
		ArrayList<Comparable> list = new ArrayList();
		keyHelper(root, list);
		return list;
	}
	public void keyHelper( NumNode r, ArrayList l){
		if(r==null)
			return;
		l.add(r.getKey());
		keyHelper(r.getLeft(), l);
		keyHelper(r.getRight(), l);
	}
	
	public boolean isEmpty(){return root == null;}
	
	public MyTreeMap inversion(){
		MyTreeMap mtm = new MyTreeMap();//makes a new map for inverted keys
		inversionRecursion( root, mtm);//puts inverted keys in the map
		return mtm;
	}
	public void inversionRecursion( NumNode r, MyTreeMap m){
		
		for(Comparable k: keyList()){
			m.put(get(k), (String)k );//uses the counts as the new keys and the keys as the new values (flip flops them)
		}
	}
	
	public String toString(){
		return toStringHelp(root);
	}
	public String toStringHelp(NumNode r){
		if(r == null)
			return " ";
		return toStringHelp(r.getLeft())+ r + toStringHelp(r.getRight());
	}
	
	
	
}
