   /**
    * Path class for storing path information for a given word.
    * 
    * @author John Cook
    *
    */
public class Path {
    String word;
    int[] path;
    
    public Path(String wordIn, int[] pathIn) {
       word = wordIn;
       path = pathIn;
    }
    
    public Path(int[] pathIn) {
       word = null;
       path = pathIn;
    }
}
