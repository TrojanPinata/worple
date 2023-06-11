import java.io.*;
import java.util.*;

public class Chance {
   
   private static String[] lettersStrings = new String[26];
   private static Letter[] letters = new Letter[26];
   private static String[][] bestString = new String[26][26];
   private static double[][] bestFloat = new double[26][26];
   private static String filename = "data.txt";
   private static Letter[] starting = new Letter[26];


   public static String getLetterElement(String letter, int element) {
      return bestString[element][atoi(letter)];
   }

   public static String[] getLetter(int element) {
      return bestString[element];
   }

   public static double[] getChance(int element) {
      return bestFloat[element];
   }
   
   public static double getChanceElement(String letter, int element) {
      return bestFloat[element][atoi(letter)];
   }

   public static Letter getBaseLetter(int element) {
      return letters[element];
   }

   public static String getBaseLetterString(int element) {
      return lettersStrings[element];
   }

   public static Letter[] getBaseLetterArray() {
      return letters;
   }

   public static Letter[] getStartingArray() {
      return starting;
   }

   public static Letter getStartingLetter(int element) {
      return starting[element];
   }

   public static int atoi(String letter) {
      int ind = (int) letter.charAt(0);
      if (ind > 94) {
         return ind - 97;
      }
      return ind - 65;
   }

   public static void load() {
      load(filename);
   }

   private static void load(String f) {
      try {
         Scanner scnr = new Scanner(new File(f));
         for (int i = 0; i < 26; i++) {
            String g = scnr.next();
            lettersStrings[i] = g;
            letters[i] = new Letter(g);
         }
         for (int i = 0; i < 26; i++) {
            starting[i] = new Letter(scnr.next());
         }
         for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
               bestString[i][j] = scnr.next();
            }
            for (int j = 0; j < 26; j++) {
               bestFloat[i][j] = scnr.nextFloat();
            }
         }
      }
      catch (FileNotFoundException e) {
         System.out.println(f + " not found.");
      }
   }
}
