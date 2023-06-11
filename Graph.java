import java.util.*;
import java.io.*;

public class Graph {
   
   private Position[] positions;
   private Letter[] solution;
   private String filename = "possible.txt";
   private ArrayList<String> check;
   private String solutionString;
   private ArrayList<Letter> yellow;

   public Graph(Position[] positions, ArrayList<Letter> yellow) {
      this.positions = positions;
      this.yellow = yellow;
      loadPossible(filename);
   }

   public String getSolution() {
      // initialize remaining and solution
      for (int c = 0; c < 5; c++) {
         positions[c].refreshRemaining();
      }
      solution = new Letter[5];

      // first run
      chooseFirst();
      for (int t = 1; t < 5; t++) {
         Letter last = solution[0];
         int ind = last.getIndex();
         String[] freq = Chance.getLetter(ind);
         int x = 0;
         for (String s : freq) {
            if (positions[t].getRemainingString().contains(s)) {
               x = positions[t].getRemainingString().indexOf(s);
               solution[t] = positions[t].getRemaining().get(x); 
               break;
            }
         }
      }

      // core loop
      int ind = 0;
      boolean breaker = false;
      while (true) {

         for (Letter r : positions[0].getRemaining()) {

            if (!positions[0].getCorrect()) {
               ind = positions[0].getRemainingString().indexOf(r.getValue());
               solution[0] = positions[0].getRemaining().get(ind);
            }
            
            if (breaker) {break;}

            for (Letter t : positions[1].getRemaining()) {

               if (!positions[1].getCorrect()) {
                  ind = positions[1].getRemainingString().indexOf(t.getValue());
                  solution[1] = positions[1].getRemaining().get(ind);
               }
               
               if (breaker) {break;}

               for (Letter y : positions[2].getRemaining()) {

                  if (!positions[2].getCorrect()) {
                     ind = positions[2].getRemainingString().indexOf(y.getValue());
                     solution[2] = positions[2].getRemaining().get(ind);
                  }

                  if (breaker) {break;}

                  for (Letter u : positions[3].getRemaining()) {

                     if (!positions[3].getCorrect()) {
                        ind = positions[3].getRemainingString().indexOf(u.getValue());
                        solution[3] = positions[3].getRemaining().get(ind);
                     }

                     if (breaker) {break;}

                     for (Letter w : positions[4].getRemaining()) {
                        if (!positions[4].getCorrect()) {
                           ind = positions[4].getRemainingString().indexOf(w.getValue());
                           solution[4] = positions[4].getRemaining().get(ind);
                        }

                        generateSolutionString();
                        if (validateYellow() && validateSolution()) {
                           breaker = true;
                           break;
                        }
                     }
                  }
               }
            }
         }

         if (breaker) {break;}
      }
      return solutionString;
   }

   private void chooseFirst() {
      int h = 0;
      while (true) {
         if (positions[0].getRemainingString().contains(Chance.getStartingLetter(h).getValue())) {
            solution[0] = Chance.getStartingLetter(h);
            break;
         }
         else {
            h++;
         }
      }
   }

   private boolean validateSolution() {
      if (solutionString.length() != 5) {
         System.out.println("Validity check attempted with insufficient characters.");
      }
      return check.contains(solutionString);
   }

   private void generateSolutionString() {
      solutionString = "";
      for (Letter p : solution) {
         solutionString += p.getValue();
      }
   }

   private boolean validateYellow() {
      for (int i = 0; i < yellow.size(); i++) {
         if (!solutionString.contains(yellow.get(i).getValue())) {
            return false;
         }
      }
      return true;
   }

   private void loadPossible(String f) {
      try {
         check = new ArrayList<String>();
         Scanner scnr = new Scanner(new File(f));
         while (scnr.hasNext()) {
            check.add(scnr.next());
         }
      }
      catch (FileNotFoundException e) {
         System.out.println(f + " not found.");
      }
   }
}