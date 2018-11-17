
import java.util.*;
 
public class SetNode{
	private Comparable key;
	private Set<String> setty;
	private SetNode left, right;
	
	public SetNode(Comparable k){
		key = k;
		setty = new TreeSet();
	}
	
	public SetNode(Comparable k, String setElement){
		super();
		key = k;
		setty = new TreeSet();
		setty.add(setElement);		
	}
	
	public Set getSet(){return setty;}
	public Comparable getKey(){return key;}
	
	public SetNode getLeft(){return left;}
	public SetNode getRight(){return right;}
	
	public void setLeft(SetNode l){left = l;}
	public void setRight(SetNode r){right =r;}
	
	public String toString(){
		return key + " --> "+ setty;
	}
}
