import java.util.List;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Stack;

/**
 * 
 * A class that defines a word search game object.
 * 
 * @author John Cook
 * @version 07/10/14
 *
 */
public class JaysWordSearch implements WordSearchGame {
	/** Size of one side of game board, such that board is n x n. **/
   private int n;
   /** 2D array to hold game board. **/
   private String [][] board;
   /** TreeSet for holding lexicon by String. **/
   private TreeSet<String> lexStr = new TreeSet<String>();
   /** Stack for holding first current starting point on board. **/
   private Stack<Integer> firstStack = new Stack<Integer>();
   /** Size of first stack. **/
   private int firstStackSize = 0;
   /** Number of times loadLexicon has been called. **/
   private int loadLexCalls;
   /** ArrayList that holds paths of valid words. **/
   private ArrayList<Path> paths = new ArrayList<Path>();
   /** Stack for holding alterable copy of direction stack. **/
   private Stack<Integer> copyStack = new Stack<Integer>();
   /** Size of copy stack. **/
   private int copyStackSize = 0;
   /** Tells isOnBoard to ignore valid word/prefix tests. **/
   private boolean ignoreValid = false;
	
	/**
	 * Parameterless constructor.
	 */
   public JaysWordSearch() {
      loadLexCalls = 0;
      n = 0;
      board = new String[n][n];
      lexStr = new TreeSet<String>();
      loadLexCalls = 0;
   }
	
   /**
    * Constructor for custom game.
    * @param nIn declares board size
    * @param lexNameIn contains name of lexicon
    * @param boardArrayIn contains String array for game board
    */
   public JaysWordSearch(int nIn, String lexNameIn, String [] boardArrayIn) {
      n = nIn;
      String lexName = lexNameIn;
      String[] boardArray = boardArrayIn;
      setBoard(boardArray);
      loadLexicon(lexName);
      loadLexCalls = 0;
   }
   
	/**
	 * Loads the lexicon into a data structure by word for later use. 
	 * 
	 * @param fileName A string containing the name of the file to be opened
	 * @throws IOException if FileNotFound 
	 * @throws IllegalArgumentException if fileName is null
	 * @throws IllegalArgumentException if fileName cannot be opened
	 */
  public void loadLexicon(String fileName) {
     if (fileName == null) {
        throw new IllegalArgumentException();
     }
     File file = new File(fileName);
     if (!(file.exists())) {
        throw new IllegalArgumentException();
     }
     // load lexicon into tree
     try {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        @SuppressWarnings("resource")
           Scanner inputFile = new Scanner(br);
        while (inputFile.hasNext() & inputFile.hasNextLine()) { 
           String cur = inputFile.next();
           cur = cur.toUpperCase();
           inputFile.nextLine();
           lexStr.add(cur);
        }
     } 
     catch (IOException e) {
        e.printStackTrace();
     }
     catch (Exception f) {
        throw new IllegalArgumentException();
     }
     loadLexCalls += 1;
  }
	
  /**
   * Stores the incoming array of Strings in a data structure that will
   *      make it convenient to find words.
   *
   * @param letterArray This array of length N^2 stores the contents of the    
   *      game board in row-major order. Thus, index 0 stores the contents
   *      of board position (0,0) and index length-1 stores the contents
   *      of board position (N-1,N-1). Note that the board must be square
   *      and that the strings inside may be longer than one character. 
   * @throws IllegalArgumentException if letterArray is null, or is 
   *      not square.
   */
   public void setBoard(String[] letterArray) {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
      else {
         int i = 0;   int j = 0;
         int k = 0;   int p = 0;
         double nDub = Math.sqrt(letterArray.length);
         int nInt = (int) nDub;
         if (nDub - nInt != 0) {
            throw new IllegalArgumentException();
         }
         this.n = (int) Math.sqrt(letterArray.length);
         this.board = new String[n][n];
         while (j < n) {
            while (i < n) {
               this.board[j][k - p] = letterArray[k];
               k++;
               i++;
            }
            p += this.n;
            j++;
            i = 0;
         }
      }
   }
   
  /**
   * Creates a String representation of the board, suitable for
   * printing to standard out. Note that this method can always be
   * called since implementing classes should have a default board.
   */
   public String getBoard() {
      String result = "";
      for (String[] s : board) {
         String[] temp = new String[s.length];
         temp = s;
         for (String u : temp) {
            result = result + u;
         }
         result += "\n";
      }
      String slashN = "\n";
      String end = result.substring((result.length() - 1), result.length());
      if (end.equals(slashN)) {
         result = result.substring(0, result.length() - 1);
      }
      return result;
   }
	
  /**
   * Retrieves all valid words on the game board, according to the
   * stated game rules. 
   * 
   * @param minimumWordLength The minimum allowed length (i.e., number
   *	of characters) for any word found on the board.
   * @return java.util.SortedSet which contains all the words of minimum
   *  length found on the game board and in the lexicon. If no words can
   *  be found, return null.
   * @throws IllegalArgumentException if minimumWordLength < 1
   * @throws IllegalStateException if loadLexicon has not been called.
   */
   public SortedSet<String> getAllValidWords(int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      else if (loadLexCalls < 1) {
         throw new IllegalStateException();
      }
      else {
         ArrayList<Let> excluded = new ArrayList<Let>();
         Let[][] letBoard = new Let[n][n];
         // create all nodes (Let objects)
         letBoard = createAllNodes();
       
         // check for/set neighbors in every let in letBoard 
         setNeighbors(letBoard, excluded);
          
         SortedSet<String> set = new TreeSet<String>();
         ArrayList<String> searchResult = new ArrayList<String>();
         @SuppressWarnings("unused")
            String tempResult = ""; 
         int y = 0;   int x = 0;
         while (y < n) {
            while (x < n) { // check across row
               String firstLetter = letBoard[y][x].letter;
               if (lexContains(firstLetter) && firstLetter.length() >= minimumWordLength) {
                  set.add(letBoard[y][x].letter);
               }
               letBoard[y][x].visited = true;
               searchResult = jaySearch(x, y, letBoard, excluded);
               for (String s : searchResult) {
                  if (s.length() >= minimumWordLength) {
                     set.add(s);
                  }
               }
               letBoard[y][x].visited = false;
               x++;
            }
            y++;  // go to second row
            x = 0;
         }
         return set;
      }
   }
   
   /**
    * Computes the cummulative score for the scorable words in the given set.
    * To be scorable, a word must (1) have at least the minimum number of characters,
    * (2) be in the lexicon, and (3) be on the board. Each scorable word is
    * awarded one point for the minimum number of characters, and one point for 
    * each character beyond the minimum number.
    *
    * @param words The set of words that are to be scored.
    * @param minimumWordLength The minimum number of characters required per word
    * @return the cummulative score of all scorable words in the set
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */  
    public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
       if (minimumWordLength < 1) {
          throw new IllegalArgumentException();
       }
       else if (loadLexCalls < 1) {
          throw new IllegalStateException();
       }
       int totalScore = 0;
       for (String s : words) {
          if ((s.length() >= minimumWordLength) && (lexStr.contains(s)) 
          	  && (isOnBoard(s) != null)) {
             totalScore += calculateScore(s, minimumWordLength);
          }
       }
       return totalScore;
    }
    
   /**
  	* Determines if the given word is in the lexicon.
  	* 
  	* @param wordToCheck The word to validate
  	* @return true if wordToCheck appears in lexicon, false otherwise.
  	* @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
  	*/
     public boolean isValidWord(String wordToCheck) {
        if (wordToCheck == null) {
           throw new IllegalArgumentException();
        }
        else if (loadLexCalls < 1) {
           throw new IllegalStateException();
        }
        // if wordToCheck is in lex, return true
        String cur = wordToCheck.toUpperCase();
        if (lexStr.contains(cur)) {
           return true;
        }
        return false;
     }
  	
   /**
  	* Determines if there is at least one word in the lexicon with the 
  	* given prefix.
  	* 
  	* @param prefixToCheck The prefix to validate
  	* @return true if prefixToCheck appears in lexicon, false otherwise.
  	* @throws IllegalArgumentException if prefixToCheck is null.
  	* @throws IllegalStateException if loadLexicon has not been called.
    */
     public boolean isValidPrefix(String prefixToCheck) {
        if (prefixToCheck == null) {
           throw new IllegalArgumentException();
        }
        else if (loadLexCalls < 1) {
           throw new IllegalStateException();
        }
        else {
        // if any word in lex beings with prefixToCheck, return true 
           String cur = prefixToCheck.toUpperCase();
           if (cur.length() == 1) {
              return true;
           }
           String flr = "";
           String floorWord = lexStr.floor(cur); 
           if (floorWord == "") {
              return false;
           }
           String ceil = "";
           String ceilingWord = lexStr.ceiling(cur);
           if (ceilingWord == null || prefixToCheck.length() > ceilingWord.length()) {
              return false;
           }
           else if (ceilingWord == "") {
              return false;
           }
           ceil = ceilingWord.substring(0, (cur.length()));
           if (cur.equals(flr) || cur.equals(ceil)) {
              return true;
           }
           return false;
        }
     }
     
   /**
  	* Determines if the given word is in on the game board. If so, 
  	*	it returns the path that makes up the word.
  	* 
  	* @param wordToCheck The word to validate
  	* @return java.util.List containing java.lang.Integer objects with 
  	*	the path that makes up the word on the game board. If word
  	*	is not on the game board, return null. Positions on the board are
    *   numbered from zero top to bottom, left to right (i.e., in row-major
    *   order). Thus, on an NxN board, the upper left position is numbered 
    *   0 and the lower right position is numbered N^2 - 1.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
  	*/
    public List<Integer> isOnBoard(String wordToCheck) {
       if (wordToCheck == null || loadLexCalls < 1) {
          throw new IllegalArgumentException();
       }
       String wordToCheckUpper = wordToCheck.toUpperCase();
       List<Integer> list = new ArrayList<Integer>();
       SortedSet<String> words = null;
       if (!(lexContains(wordToCheck))) {
          ignoreValid = true; 
       }
       words = getAllValidWords(wordToCheck.length());
    
       if (words.contains(wordToCheckUpper)) {
             // find Path object in paths whose .word field matches wordToCheck
          for (Path p : paths) {
             if ((p.word).equals(wordToCheckUpper)) {  // find correct Path object
                   //copy array here
                list = copyList(list, p.path);
             }
          }
       }
       else {
          list = null;
       }
       ignoreValid = false;
       return list;
    }
   
   /**
    * Performs recursive search for all possible words starting from given point.
    * 
    * @param x x coord of starting point
    * @param y y coord of starting point
    * @return ArrayList that holds all words found from starting point
    */
   private ArrayList<String> jaySearch(int x, int y, Let[][] letBoard, ArrayList<Let> excluded) {
      if (x < 0 || y < 0 || x > n || y > n) {
         throw new IllegalArgumentException();
      }
      ArrayList<String> result = new ArrayList<String>();
      copyStack = new Stack<Integer>();
      copyStackSize = 0;
      String resultString = "";
      resultString += letBoard[y][x].letter;
      copyStack.push(letBoard[y][x].place);
      copyStackSize++;
      ArrayList<Integer> exPlace = new ArrayList<Integer>();
      for (Let l : letBoard[y][x].neighborArr) {
         String temp = resultString;
         if (l != null) {
            resultString += l.letter;
            firstStack.push(l.place);
            firstStackSize++;
            l.visited = true;
            if (isValidPrefix(resultString) || ignoreValid) {
               result.addAll(recursiveSearch(resultString, letBoard, l,
                     l.x, l.y, exPlace, l.place, firstStack));
               firstStack.pop();
               firstStackSize--;
               l.visited = false;
            } 
            else {
               firstStack.pop();
               firstStackSize--; 
               l.visited = false;  
            }
         }
         resultString = temp;
         while (copyStackSize > (firstStackSize + 1)) {
            copyStack.pop();
            copyStackSize--;
         }
      }	   
      return result;
   }
   
   /**
    * Recursive search algorithm for finding complete words.
    * 
    * @param resultString is current result string
    * @param letBoard is the board filled with objects
    * @return ArrayList with complete words
    */
   public ArrayList<String> recursiveSearch (String resultString, Let[][] letBoard, Let l, 
      int x, int y, ArrayList<Integer> exPlace, int pPlace, Stack<Integer> st) {
      ArrayList<String> result = new ArrayList<String>();
      Stack<Integer> s = new Stack<Integer>();
      for (int i : st) {
         s.push(i);
         if (!(copyStack.contains(i))) {
            copyStack.push(i);
            copyStackSize++;
         }
      }
      if (lexContains(resultString) || ignoreValid) { // is valid word?
         result.add(resultString.toUpperCase());
                   // add current word path to path ArrayList paths
         int[] temp = new int[copyStack.size()];
         int j = 0;
         for (Integer i : copyStack) {   // copy stack to int[], then add to path object
            temp[j] = i;
            j++;
         }
         // reverse items in temp
         Path cur = new Path(resultString.toUpperCase(), temp);
         paths.add(cur);
      }
      if (isValidPrefix(resultString) || ignoreValid) {
         String tempStr = resultString;
         // result += every other possible letter, and all of it's possible neighbor letters
         for (Let p : letBoard[y][x].neighborArr) {
            String temp = resultString;
            if (lexContains(resultString) || ignoreValid) { // is valid word?
               result.add(resultString.toUpperCase());
            }
            if ((p != null) && (p.visited == false)) {
               if (!(s.contains(p.place))) {
                  resultString += p.letter;
                  int u = p.place;
                  s.push(u);
                  copyStack.push(u);
                  copyStackSize++;
                  p.visited = true;
                  try {
                     result.addAll(recursiveSearch(resultString, letBoard, l, p.x, p.y, exPlace, p.place, s));
                  }
                  catch (NullPointerException e) {
                     System.out.print("You suck.");
                  }
                  s.pop();
                  copyStack.pop();
                  copyStackSize--;
                  p.visited = false;
               }
            }
            else {
               resultString = tempStr;
            }
            resultString = temp;
         }
      }
      return result;
   }
   
   /**
    * Creates all nodes (Let objects) for jaySearch.
    * 
    * @return letBoard
    */
   public Let[][] createAllNodes() {
      int c = 0;   int d = 0;
      Let[][] letBoard = new Let[n][n];
      for (String[] a : board) {
         for (@SuppressWarnings("unused") String s : a) {
            letBoard[d][c] = new Let(d,c);
            c++;
         }
         c = 0;
         d++;
      }
      return letBoard;
   }
   
   /**
    * Sets neighbors for all Let objects in paramter array.
    * 
    * @param letBoard array to be set
    */
   public void setNeighbors(Let[][] letBoard, ArrayList<Let> excluded) {
      for (Let[] b : letBoard) {
         for (Let l : b) {
            // neighbor 0
            for (int i = 0; i < 8; i++) {
               l.neighborArr[i] = null;
            }
            if (l.x != 0 && l.y != 0 /*&& !(excludedCheck(excluded, letBoard[l.y - 1][l.x - 1]))*/) {
               l.neighborArr[0] = letBoard[l.y - 1][l.x - 1];
            }
            // neighbor 1
            if (l.y != 0 /*&& !(excludedCheck(excluded, letBoard[l.y - 1][l.x]))*/) {
               l.neighborArr[1] = letBoard[l.y - 1][l.x];
            }
            // neighbor 2
            if (l.x != (n - 1) && l.y != 0 /*&& !(excludedCheck(excluded, letBoard[l.y - 1][l.x + 1]))*/) {
               l.neighborArr[2] = letBoard[l.y - 1][l.x + 1];
            }
            // neighbor 3
            if (l.x != (n - 1) /*&& !(excludedCheck(excluded, letBoard[l.y][l.x + 1]))*/) {
               l.neighborArr[3] = letBoard[l.y][l.x + 1];
            }
            // neighbor 4
            if (l.x != (n - 1) && l.y != (n - 1) /*&& !(excludedCheck(excluded, letBoard[l.y + 1][l.x + 1]))*/) {
               l.neighborArr[4] = letBoard[l.y + 1][l.x + 1];
            }
            // neighbor 5
            if (l.y != (n - 1) /*&& !(excludedCheck(excluded, letBoard[l.y + 1][l.x]))*/) {
               l.neighborArr[5] = letBoard[l.y + 1][l.x];
            }
            // neighbor 6
            if (l.x != 0 && l.y != (n - 1) /*&& !(excludedCheck(excluded, letBoard[l.y + 1][l.x - 1]))*/) {
               l.neighborArr[6] = letBoard[l.y + 1][l.x - 1];
            }
            // neighbor 7
            if (l.x != 0 /*&& !(excludedCheck(excluded, letBoard[l.y][l.x - 1]))*/) {
               l.neighborArr[7] = letBoard[l.y][l.x - 1];
            }
            excluded.add(l);
         }
      }
   }
   
   /**
    * Calculates score for word passed into parameter.
    * 
    * @param s is score to be calculated
    * @return score for that word
    */
   private int calculateScore(String s, int min) {
      int score = s.length() - min + 1;
      return score;
   }

   /** 
    * Returns a copy of the specified list. 
    * 
    * @return  copy of given List
    **/
   public List<Integer> copyList(List<Integer> listIn, int[] pathIn) {
      List<Integer> result = new ArrayList<Integer>();
      for (int i : pathIn) {
         result.add(i);
      }
      return result;
   }
   
   /** 
    * Checks to see if a given word is contained in the lexicon.
    * 
    * @return true if word is present, otherwise return false
    **/
   public boolean lexContains(String input) {
      String temp = input.toUpperCase();
      return lexStr.contains(temp);
   }
   
   /**
    * Let class for holding letter information from board.
    * 
    * @author John Cook
    *
    */
   private class Let {
      private String letter;
      private int y;
      private int x;
      private int place;
      // neighbors:
      private Let[] neighborArr = new Let[8];
      private boolean visited = false;
      
      public Let(int yIn, int xIn) {
         y = yIn;
         x = xIn;
         letter = board[y][x];
         place = x + (y*n);
         visited = false;
      }
   }
   
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
}