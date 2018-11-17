
import java.util.*;

public class MyTreeMap{
	private SetNode root;
	private int size = 0;
	
	public MyTreeMap(){
		root = null;
	}
	public SetNode find(Comparable key){
		return null;
	}
	
	public void put(Comparable key, String value) {
		Boolean movedRight = false;
		if(isEmpty()){
			root = new SetNode(key, value);
			return;
		}
		SetNode p = root, c = root;
		
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
					c.getSet().add(value);//if the SetNode has this key, add the new value to the set of values
					return;
			}
			
		}
		if(movedRight)//if the key isn't in the map, add it to the map
			p.setRight(new SetNode(key, value));
		else
			p.setLeft(new SetNode(key, value));
		size++;
		
	}
	
	public Set<String> get(Comparable key) {
		SetNode c= root;
		while(c!=null){
			if(c.getKey().compareTo(key) == 0)//checks if it matches
				return c.getSet();
			else if(c.getKey().compareTo(key)>0)//
				c = c.getLeft();
			else
				c = c.getRight();
				
		}
		return null;
	}
	
	public boolean containsKey(Comparable key) {
		return (find(key)!=null);		
	}	
	public boolean isEmpty() {
		return root == null;
	}
	public ArrayList<Comparable> keyList() {
		//Set<Comparable> setty = new
		ArrayList<Comparable> list = new ArrayList();
		keyListHelper(root, list);
		return list;
	}
	public void keyListHelper(SetNode r, ArrayList l){
		if(r==null)
			return;
		keyListHelper(r.getRight(), l);
		l.add(r.getKey());
		keyListHelper(r.getLeft(), l);
		
	}
	public int size() {
		return size;
	}
	public String toString(){
		return toStringHelp(root);
	}
	public String toStringHelp(SetNode r){
		if(r == null)
			return "\n";
		return toStringHelp(r.getLeft())+ r + toStringHelp(r.getRight());
	}


}
