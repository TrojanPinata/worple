import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
   
   private static JFrame frame = new JFrame("worple");
   private static JPanel buttons, fields;
   private static JButton solve, reset, info;
   private static HashSet<Letter> invalid;
   private static ArrayList<Letter> correct, yellow, yellowMaster;
   private static ArrayList<Integer> indexes;
   private static JTextField[] inputs, modifiers;
   private static String[] inputsStrings, modifiersStrings;
   private static int numPop = 0;
   //private static Position[] positions;

   public static void main(String[] args) {
      Chance.load();
      frame = new GUI();
      frame.setTitle("w o r p l e");
      frame.pack();
      frame.setSize(800, 600);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLocationRelativeTo(null);
      frame.setBackground(Color.GRAY);
      frame.setVisible(true);
      System.out.println("GUI loaded");
   }

   private GUI() {
      try {
         // initialize panels
         buttons = new JPanel();
         fields = new JPanel();

         // UI Elements
         solve = new JButton("Solve");
         reset = new JButton("Reset");
         info = new JButton("Info");

         inputs = new JTextField[6];
         modifiers = new JTextField[6];
         inputsStrings = new String[6];
         modifiersStrings = new String[6];
         for (int i = 0; i < 6; i++) {
            inputs[i] = new JTextField("");
            modifiers[i] = new JTextField("");
            inputsStrings[i] = "";
            modifiersStrings[i] = "";
         }

         loadFonts();
      }
      catch (Exception e) {
         JOptionPane.showMessageDialog(null, "An error occured while initializing some elements.");
         e.printStackTrace();
         System.out.println(e);
      }

      try {
         // define button actions

         fields = new JPanel(new GridLayout(6,2));
         for (int i = 0; i < 6; i++) {
            fields.add(inputs[i]);
            fields.add(modifiers[i]);
         }

         // solve using given inputs
         solve = new JButton("Solve");
         buttons = new JPanel();
         buttons.add(solve);
         solve.addActionListener((ActionListener) new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               // clear text fields
               for (int i = 0; i < 6; i++) {
                  inputsStrings[i] = "";
                  modifiersStrings[i] = "";
               }
               numPop = 0;
               for (int i = 0; i < 6; i++) {
                  inputsStrings[i] = inputs[i].getText();
                  modifiersStrings[i] = modifiers[i].getText();
                  
                  // integrity checks
                  if (inputsStrings[i].length() > 5 || modifiersStrings[i].length() > 5) {
                     JOptionPane.showMessageDialog(null, "There are too many letters input to one of the fields");
                  }
                  if (inputsStrings[i].length() != modifiersStrings[i].length()) {
                     JOptionPane.showMessageDialog(null, "There is a problem relating fields. The number of characters must match in both modifier and letter fields.");
                  }
                  if (inputsStrings[i].length() == 0) {
                     break;
                  }
                  numPop++;
               }
               
               // determine invalid letters
               invalid = new HashSet<Letter>();
               for (int i = 0; i < numPop; i++) {
                  for (int j = 0; j < 5; j++) {
                     if (modifiersStrings[i].charAt(j) == 'x') {
                        invalid.add(new Letter("" + inputsStrings[i].charAt(j)));
                     }

                  }
               }

               // generate Position objects and define properties of each
               Position p;
               Position[] positions = new Position[5];
               for (int i = 0; i < 5; i++) {
                  p = new Position(i);
                  for (Letter g : invalid) {
                     p.removeFromPossible(g.getValue());
                  }
                  positions[i] = p;
               }

               // determine correct/final letters
               correct = new ArrayList<Letter>();
               indexes = new ArrayList<Integer>();
               for (int i = 0; i < numPop; i++) {
                  for (int j = 0; j < 5; j++) {
                     if (modifiersStrings[i].charAt(j) == 'o') {
                        correct.add(new Letter("" + inputsStrings[i].charAt(j)));
                        indexes.add(j);
                     }
                  }
               }
               for (int i = 0; i < 5; i++) {
                  if (indexes.contains(i)) {
                     positions[i].setFinal(correct.get(indexes.indexOf(i)));
                     positions[i].setCorrect(true);
                  }
                  /* else {
                     for (int h = 0; h < correct.size(); h++) {
                        positions[h].removeFromPossible(correct.get(h).getValue());
                     }
                  } */
               }

               // remove yellows
               yellowMaster = new ArrayList<Letter>();
               for (int i = 0; i < numPop; i++) {
                  yellow = new ArrayList<Letter>();
                  for (int j = 0; j < 5; j++) {
                     if (modifiersStrings[i].charAt(j) == 'y') {
                        Letter a = new Letter("" + inputsStrings[i].charAt(j));
                        yellow.add(a);
                        positions[j].removeFromPossible(a.getValue());
                     }
                  }
                  yellowMaster.addAll(yellow);
               }


               Graph graph = new Graph(positions, yellowMaster);

               // THIS NEEDS TO SOLVE THE WORDLE AND RETURN MOST LIKELY ANSWER
               JOptionPane.showMessageDialog(null, graph.getSolution());
            }

         });

         reset = new JButton("Reset");
         buttons.add(reset);
         reset.addActionListener((ActionListener) new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               for (int i = 0; i < 6; i++) {
                  inputs[i].setText("");
                  modifiers[i].setText("");
               }
               /* //answer: colon
               inputs[0].setText("based");
               inputs[1].setText("might");
               inputs[2].setText("pluck");
               inputs[3].setText("front");
               inputs[4].setText("local");
               modifiers[0].setText("xxxxx");
               modifiers[1].setText("xxxxx");
               modifiers[2].setText("xyxyx");
               modifiers[3].setText("xxyyx");
               modifiers[4].setText("yoyxy"); */
            }

         });

         info = new JButton("Info");
         buttons.add(info);
         info.addActionListener((ActionListener) new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               JOptionPane.showMessageDialog(null, "Type five letter guess in left column and cooresponding color codes in right column\nGreen letters are represented as o, Yellow as y, and Grey as x\nFor example:\nBASED - xoxyy\nA is correct, E and D are wrong places but are present.");
            }

         });

         Container cont = getContentPane();
         cont.setLayout(new BorderLayout());
         cont.add(fields, BorderLayout.CENTER);
         cont.add(buttons, BorderLayout.SOUTH);
         frame.setIconImage(Toolkit.getDefaultToolkit().getImage("favicon.png"));
      }
      catch (Exception e) {
         e.printStackTrace();
         System.out.println(e);
      }
   }

   private static void loadFonts() {
      // Fonts
      Font font = new Font(Font.MONOSPACED, Font.BOLD, 15);
      Font fontLarge = new Font(Font.MONOSPACED, Font.BOLD, 24);
      UIManager.put("OptionPane.messageFont", font);
      UIManager.put("OptionPane.buttonFont", font);
   
      solve.setFont(font);
      reset.setFont(font);
      info.setFont(font);

      for (int i = 0; i < 6; i++) {
         inputs[i].setFont(fontLarge);
         modifiers[i].setFont(fontLarge);
         inputs[i].setHorizontalAlignment(JTextField.CENTER);
         modifiers[i].setHorizontalAlignment(JTextField.CENTER);
      }

   }
}
