/**
 * @(#)Colour.java
 *
 *
 * @author
 * @version 1.00 2014/6/25
 */


public enum Colour {
	    NONE, YELLOW, RED, GREEN, BLUE;

	    public static Colour getColourValue(int c){
	    	Colour colour;
	    	switch(c){
	    		case 0: colour = NONE;
	    			break;
	    		case 1: colour = YELLOW;
	    			break;
	    		case 2: colour = RED;
	    			break;
	    		case 3: colour = GREEN;
	    			break;
	    		case 4: colour = BLUE;
	    			break;
	    		default: colour = NONE;
	    			break;
	    	}

	    	return colour;
	    }
}
