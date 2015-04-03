   /**
    * Path class for storing path information for a given word.
    * 
    * @author John Cook
    *
    */
public class Path {
    String word;
    int[] path;
    
    /*
     * Constructor
     * @param wordIn takes in the current word fragment
     * @param pathIn takes in the current path of word
     */
    public Path(String wordIn, int[] pathIn) {
       word = wordIn;
       path = pathIn;
    }
    
    /*
     * Constructor
     * @param pathIn takes in the current path of word
     */
    public Path(int[] pathIn) {
       word = null;
       path = pathIn;
    }
}
