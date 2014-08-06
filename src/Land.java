/**
 * @(#)Land.java
 *
 *
 * @author
 * @version 1.00 2014/6/25
 */
import jss2.ArrayList;
public class Land implements Comparable<Object>{

	//ivars
	private String name;
	private Colour colour;
	private Colour[] domain = null;
	private ArrayList<Land> neighbours;

    public Land() {
    	//assign NONE colour
    	colour = Colour.NONE;
    	name = "";
    	neighbours = new ArrayList<Land>();
    }

    public Land(String n, Colour c){
    	name = n;
    	colour = c;
    }

    /**
     * mutators
     */

    /**
     * @param String
     * n - new name
     */
    public void setName(String n){
    	name = n;
    }

    /**
     * @param Colour
     * c - new colour
     */
    public void setColour(Colour c){
    	colour = c;
    }

    /*
     * Accessors
     */

    /**
     * @return String
     * name - name of Land
     */
    public String getName(){
    	return name;
    }

    /**
     * @return Colour
     * colour - current land colouring
     */
    public Colour getColour(){
    	return colour;
    }

    public void setDomain(Colour[] d){
    	domain = new Colour[d.length];
    	domain = d;
    }

    public Colour[] getDomain(){
    	return domain;
    }

    public void setNeighbours(ArrayList<Land> n){
    	neighbours = n;
    }

    public ArrayList<Land> getNeighbuors(){
    	return neighbours;

    }

    /**
     * Implementing Comparable
     */

	/**
	 * @param Object
	 * o - other Object to be compared
	 *
	 * @return int
	 * 0 if equal, -1 if less than, 1 if greater than
	 *
	 * Compares two objects (in this case, Land to another Land
	 */
    public int compareTo(Object o){
		Land other = (Land)o;

    	if(other.getName().equals(this.getName()) && other.getColour() == this.getColour())
    		return 0;
    	else if (other.getName().compareTo(this.getName()) < 0 && other.getColour() != this.getColour())
    		return -1;
    	else
    		return 1;
    }

	/**
	 * @param Object
	 * other - object to check for equality
	 *
	 * @return boolean
	 * true if equal, false if not
	 *
	 * Checks equality between two pieces of Land
	 */
    public boolean equals(Object other){
    	return (this.compareTo(other)==0);
    }

	/**
	 * @return String
	 * toString method for Land
	 */
    public String toString(){
    	String result=name;

    	switch(colour){
    		case NONE: result+=":NONE";
    			break;
    		case YELLOW: result+=":YELLOW";
    			break;
    		case RED: result+=":RED";
    			break;
    		case GREEN: result+=":GREEN";
    			break;
    		case BLUE: result+=":BLUE";
    			break;
    		default: result+=":null";
    			break;
    	}

    	return result;
    }
}