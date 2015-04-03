
/**
 * Provides a factory method for creating word search games. 
 */
public class WordSearchGameFactory {
   public static WordSearchGame createGame() {
      return new JaysWordSearch();
   }
}