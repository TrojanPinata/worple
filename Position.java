import java.util.*;
import java.util.stream.*;

public class Position {
   
   //private Letter guess;
   //private Position next, previous;

   private List<Letter> possible, remaining;
   private int index;
   private boolean correct = false;

   public Position(int index) {
      this.index = index;
      possible = Arrays.stream(Chance.getBaseLetterArray()).collect(Collectors.toList());
      remaining = new ArrayList<Letter>();
   }

   public List<Letter> getPossible() {
      return possible;
   }

   public int getIndex() {
      return index;
   }

   public void setPossible(List<Letter> possible) {
      this.possible = possible;
   }

   public void removeFromPossible(String s) {
      ArrayList<Letter> v = new ArrayList<Letter>();
      for (Letter p : possible) {
         if (p.getValue().equals(s)) {
            v.add(p);
         }
      }
      for (Letter g : v) {
         possible.remove(g);
      }
   }

   public void addToPossible(Letter s) {
      possible.add(s);
   }

   public void setFinal(Letter s) {
      possible.clear();
      possible.add(s);
   }
   
   public void refreshRemaining() {
      for (Letter p : possible) {
         remaining.add(p.clone());
      }
   }

   public List<Letter> getRemaining() {
      return remaining;
   }

   public ArrayList<String> getRemainingString() {
      ArrayList<String> arr = new ArrayList<String>();
      for (Letter l : remaining) {
         arr.add(l.getValue());
      }
      return arr;
   }

   public void setRemaining(List<Letter> remaining) {
      this.remaining = remaining;
   }

   public void removeFromRemaining(String s) {
      for (Letter p : remaining) {
         if (p.getValue().equals(s)) {
            remaining.remove(p);
         }
      }
   }

   public void addToRemaining(Letter s) {
      remaining.add(s);
   }

   public boolean getCorrect() {
      return correct;
   }

   public void setCorrect(boolean correct) {
      this.correct = correct;
   }
}
