//import java.util.*;

public class Letter {

   private String value;
   private int index;

   public Letter(String value) {
      try {
         if (value.length() == 1) {
            if ((int) value.charAt(0) > 94) {
               index = (int) value.charAt(0) - 97;
            }
            else {
               index = (int) value.charAt(0) - 65;
            }
         }
         this.value = Chance.getBaseLetterString(index);
      }
      catch (Exception e) {
         System.out.println("Value of Letter object cannot be greater than 1");
         e.printStackTrace();
         System.out.println(e);
      }
   }

   public String getValue() {
      return value;
   }

   public int getIndex() {
      return index;
   }

   public Letter clone() {
      return new Letter(value);
   }
}