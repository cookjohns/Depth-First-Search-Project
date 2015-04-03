import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.SortedSet;
import java.util.ArrayList;


public class JaysWordSearchTest {

   String[] set = {"a", "b", "c", "d", "e", "f", "g", "h", 
            "i", "j", "k", "l", "m", "n", "o", "p"};
   String[] small = {"a", "b", "c", "d"};
   String [] big = {
           "O",	"Y",	"D",	"D",	"T",	"P",	"N",	"R",	"A",	"H",	"E",	"L",	"C",	"S",	"B",	"P",	"S",	"U",	"B",	"G",
           "U",	"P",	"Y",	"H",	"R",	"R",	"X",	"R",	"E",	"F",	"H",	"D",	"H",	"T",	"K",	"X",	"K",	"O",	"Z",	"F",
           "W",	"Y",	"H",	"Y",	"T",	"C",	"H",	"M",	"V",	"P",	"R",	"T",	"A",	"K",	"N",	"E",	"S",	"I",	"B",	"T",
           "M",	"V",	"Y",	"Q",	"E",	"U",	"O",	"E",	"F",	"A",	"K",	"J",	"C",	"W",	"I",	"K",	"I",	"U",	"K",	"T",
           "P",	"O",	"F",	"E",	"G",	"Z",	"T",	"X",	"O",	"Z",	"T",	"H",	"K",	"B",	"M",	"G",	"D",	"P",	"P",	"P",
           "G",	"U",	"E",	"S",	"C",	"J",	"C",	"B",	"Q",	"F",	"T",	"R",	"I",	"P",	"N",	"I",	"E",	"W",	"P",	"K",
           "H",	"K",	"G",	"B",	"B",	"L",	"Y",	"J",	"P",	"J",	"E",	"O",	"N",	"Q",	"V",	"N",	"B",	"S",	"H",	"R",
           "N",	"Z",	"R",	"G",	"A",	"E",	"W",	"P",	"L",	"L",	"Z",	"R",	"G",	"I",	"E",	"T",	"U",	"N",	"R",	"L",
           "I",  "K",	"T",	"J",	"K",	"J",	"F",	"C",	"I",	"T",	"M",	"R",	"D",	"T",	"R",	"E",	"G",	"L",	"J",	"G",
           "I",	"K",	"H",	"L",	"C",	"V",	"P",	"P",	"D",	"S",	"Q",	"E",	"W",	"O",	"C",	"R",	"L",	"V",	"L",	"P",
           "T",	"A",	"T",	"N",	"O",	"R",	"M",	"W",	"K",	"O",	"D",	"O",	"U",	"O",	"V",	"F",	"M",	"H",	"V",	"V",
           "S",	"I",	"X",	"Z",	"L",	"O",	"T",	"Z",	"L",	"B",	"R",	"G",	"F",	"Q",	"P",	"A",	"Y",	"P",	"D",	"L",
           "B",	"K",	"S",	"N",	"C",	"H",	"O",	"P",	"Y",	"K",	"H",	"C",	"R",	"R",	"I",	"C",	"S",	"B",	"J",	"X",
           "R",	"F",	"I",	"Y",	"R",	"H",	"B",	"Z",	"I",	"P",	"C",	"K",	"I",	"N",	"O",	"E",  "C",	"C",	"U",	"C",
           "P",	"I",	"J",	"R",	"E",	"Y",	"E",	"Z",	"U",	"R",	"R",  "M",	"F",  "S",	"M",  "R",	"N",	"J",	"I",	"B",
           "T",	"Q",	"O",	"C",	"V",	"R",	"O",	"T",	"X",	"H",	"C",	"R",	"W",	"S",	"A",	"V",	"T",  "N",	"U",	"I",
           "O",	"W",	"X",  "C",	"O",  "R",	"X",	"Q",	"A",	"S",	"A",	"S",	"S",	"E",	"M",	"B",	"L",	"Y",	"O",	"Z",
           "F",	"P", 	"L",	"S",	"C",	"I",	"T",	"L",	"U",	"M",	"O",	"N",	"I",	"T",	"O",	"R",	"J",	"W",	"I",	"N",
           "L",	"L",	"L",	"E",	"L",	"J",	"R",	"R",	"E",	"M",	"M",	"O",	"B",	"D",	"X",	"I",	"J",	"D",	"S",	"R",
           "L",	"C",	"H",	"S",	"H",	"Y",	"U",	"L",	"P",	"M",	"O",  "U",	"S",	"E",	"C",	"B",	"I",	"I",	"U",	"I",
           };
           
   String [] big2 = {"O","Y","D","D","T","P","N","R","A","H","E","L","C","S","B","P","S","U","B","G","U","P","Y","H","R","R","X",
                     "R","E","F","H","D","H","T","K","X","K","O","Z","F","W","Y","H","Y","T","C","H","M","V","P","R","T","A","K",
                     "N","E","S","I","B","T","M","V","Y","Q","E","U","O","E","F","A","K","J","C","W","I","K","I","U","K","T","P",
                     "O","F","E","G","Z","T","X","O","Z","T","H","K","B","M","G","D","P","P","P","G","U","E","S","C","J","C","B",
                     "Q","F","T","R","I","P","N","I","E","W","P","K","H","K","G","B","B","L","Y","J","P","J","E","O","N","Q","V",
                     "N","B","S","H","R","N","Z","R","G","A","E","W","P","L","L","Z","R","G","I","E","T","U","N","R","L","I","K",
                     "T","J","K","J","F","C","I","T","M","R","D","T","R","E","G","L","J","G","I","K","H","L","C","V","P","P","D",
                     "S","Q","E","W","O","C","R","L","V","L","P","T","A","T","N","O","R","M","W","K","O","D","O","U","O","V","F",
                     "M","H","V","V","S","I","X","Z","L","O","T","Z","L","B","R","G","F","Q","P","A","Y","P","D","L","B","K","S",
                     "N","C","H","O","P","Y","K","H","C","R","R","I","C","S","B","J","X","R","F","I","Y","R","H","B","Z","I","P",
                     "C","K","I","N","O","E","C","C","U","C","P","I","J","R","E","Y","E","Z","U","R","R","M","F","S","M","R","N",
                     "J","I","B","T","Q","O","C","V","R","O","T","X","H","C","R","W","S","A","V","T","N","U","I","O","W","X","C",
                     "O","R","X","Q","A","S","A","S","S","E","M","B","L","Y","O","Z","F","P","L","S","C","I","T","L","U","M","O",
                     "N","I","T","O","R","J","W","I","N","L","L","L","E","L","J","R","R","E","M","M","O","B","D","X","I","J","D",
                     "S","R","L","C","H","S","H","Y","U","L","P","M","O","U","S","E","C","B","I","I","U","I"};
                     
   JaysWordSearch game = new JaysWordSearch();

	
   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
      game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/csw12.txt");
   }
   
    @Test public void whatever1() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words_medium.txt");
       Assert.assertEquals(true, game.isValidPrefix("A"));
    }
   
    @Test public void whatever() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words_medium.txt");
       Assert.assertEquals(true, game.isValidPrefix("REOILE"));
    }
   
   @Test public void webcatTest4() {
      game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words_medium.txt");
      String[] test = {"P","X","D","E","O","T","L","J","E","I","E","A","C","O","R","N"};
      game.setBoard(test);
      SortedSet<String> temp = game.getAllValidWords(3);
      System.out.print("yo");
   }
    
    @Test public void webcatTest3() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words_medium.txt");
       String[] church = {"c", "h", "u", "r", "c", "f", "e", "o", "g", "h", "s", "d", "r", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"};
       game.setBoard(church);
       SortedSet<String> temp = game.getAllValidWords(10);
       System.out.print("soentowisn");
    }
    
    @Test public void webcatTest2() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words_medium.txt");
       game.setBoard(big2);
       SortedSet<String> temp = game.getAllValidWords(10);
       SortedSet<String> result = temp;
    }
    
    @Test public void webcatTest1() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words_medium.txt");
       String[] board = {"Z","E","Z","T","Q","I","O","X","U"};
       game.setBoard(board);
       SortedSet<String> temp = game.getAllValidWords(3);
       SortedSet<String> result = temp;
    }
    
    @Test public void bigTest() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words_medium.txt");
       game.setBoard(big);
       SortedSet<String> temp = game.getAllValidWords(3);
       SortedSet<String> result = temp;
    }
    @Test public void longTest() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words_medium.txt");
       String[] longBoard = randomBoard(400);
       game.setBoard(longBoard);
       SortedSet<String> temp = game.getAllValidWords(3);
       SortedSet<String> result = temp;
    }
    
    
    @Test (expected = IllegalArgumentException.class) 
    public void fileExistsTest() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/noFile.txt");
    }
    @Test (expected = IllegalArgumentException.class)
    public void notSquareBoardTest() {
       String[] three = {"a", "b", "c"};
       game.setBoard(three);
    }
    
    @SuppressWarnings("unused")
    @Test public void setBoardTest() {
       double dub = 25 % 1;   // for test
       double result = 25.5 % 1;   // for test
       game.setBoard(set);
       String[] small = {"c", "a", "t", "b"};
       game.setBoard(small);
       
    }
    
    @Test public void getBoardTest() {
       String temp = "abcd\nefgh\nijkl\nmnop";
       game.setBoard(set);
       Assert.assertEquals(temp, game.getBoard());
       Assert.assertEquals(temp, game.getBoard());
    }
    
    @Test public void isValidWordTest() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/csw12.txt");
       Assert.assertEquals(true, game.isValidWord("EA"));
       Assert.assertEquals(false, game.isValidWord("E"));
    }
    
    @Test public void isValidPrefixTest() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words.txt");
       Assert.assertEquals(true, game.isValidPrefix("AB"));
       Assert.assertEquals(true, game.isValidPrefix("AC"));
       Assert.assertEquals(true, game.isValidPrefix("ca"));
       
       String[] quiet = {"qu", "i", "e", "t"};
       game.setBoard(quiet);
       game.isValidPrefix("itqu");
    }
    
    @Test public void validWordsSmallTest() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words.txt");
       String[] small = {"c", "a", "t", "b"};
       game.setBoard(small);
       game.getAllValidWords(1);
       game.getAllValidWords(2);
       game.getAllValidWords(1);
    }
    @Test public void tigerTest() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words.txt");
       String[] tiger = {"tiger"};
       game.setBoard(tiger);
       game.getAllValidWords(3);
    }
    @Test public void multiWordTest() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words.txt");
       String[] multi = {"cat", "x", "fish", "xxxx"};
       game.setBoard(multi);
       game.getAllValidWords(7);
    }
       // long test goes here
    // big board test goes here
    public String[] randomBoard(int size) {
       String[] boardInput = new String[size];
       for (int i = 0; i < boardInput.length; i++) {
          char nextLetter = (char)((Math.random() * 26) + 65);
          boardInput[i] = Character.toString(nextLetter); 
       }
       return boardInput;
    
    }
    
    @Test public void containsTest() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/csw12.txt");
       Assert.assertEquals(true, game.lexContains("CAB"));
       Assert.assertEquals(true,  game.lexContains("cab"));
    }
    
    @Test public void isOnBoardTest() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words.txt");
       String[] small = {"c", "a", "t", "b"};
       game.setBoard(small);
       game.isOnBoard("tab");
       ArrayList<Integer> temp = new ArrayList<Integer>();
       temp.add(2);   temp.add(1);   temp.add(3);
       Assert.assertEquals(temp, game.isOnBoard("tab"));
       ArrayList<Integer> temp2 = new ArrayList<Integer>();
       temp2.add(0);   temp2.add(2);   temp2.add(3);
       Assert.assertEquals(temp2, game.isOnBoard("ctb"));
       Assert.assertEquals(null, game.isOnBoard("xyz"));
       Assert.assertEquals(null, game.isOnBoard("batcat"));
       
       String[] xyzBoard = {"x", "y", "z", "d"};
       game.setBoard(xyzBoard);
       ArrayList<Integer> temp3 = new ArrayList<Integer>();
       temp3.add(0);   temp3.add(1);   temp3.add(2);
       Assert.assertEquals(temp3, game.isOnBoard("xyz"));
       
       String[] quiet = {"qu", "i", "e", "t"};
       game.setBoard(quiet);
       ArrayList<Integer> temp4 = new ArrayList<Integer>();
       temp4.add(0);   temp4.add(1);   temp4.add(2);   temp4.add(3);
       Assert.assertEquals(temp4, game.isOnBoard("quiet"));
    }
    
    // method does not exist > retitled isOnBoard
    @Test public void isOnBoardNoCheckTest() {
       String[] test = {"x", "y", "z", "b"};
       game.setBoard(test);
       //game.isOnBoardNoCheck("xzy");   <-- does not exist
    }
    
    @Test public void prefixSmokeTest() {
       game.loadLexicon("/Users/johncook/Google Drive/COMP 2210/Assignment 4/wordfiles/words_medium.txt");
       String prefix = "XXX";
       Assert.assertEquals(false, game.isValidPrefix(prefix));
    }
   
}
