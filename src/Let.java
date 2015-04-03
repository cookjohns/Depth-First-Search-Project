   /**
    * Let class for holding letter information from board.
    * 
    * @author John Cook
    *
    */
public class Let {
    private String letter;
    private int y;
    private int x;
    private int place;
    // neighbors:
    private Let[] neighborArr = new Let[8];
    private boolean visited = false;
    
    /**
     * Constructor
     * @param yIn takes in y coordinate
     * @param xIn takes in x coordinate
     */
    public Let(int yIn, int xIn) {
       y = yIn;
       x = xIn;
       letter = board[y][x];
       place = x + (y*n);
       visited = false;
    }
}
