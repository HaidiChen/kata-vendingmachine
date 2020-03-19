package vendingmachine.gui;

import java.util.HashMap;
import java.awt.event.*;
import javax.swing.*;

public class ChoiceSession extends JPanel {

  static HashMap<String, String> items = new HashMap<>();
  JTextField choice;
  private String itemIndex;

  public ChoiceSession(GUIInteractor interactor) {
    items.put("0", "Nuts");
    items.put("1", "Coke");
    items.put("2", "Candy");
    items.put("3", "BottleWater");
    items.put("4", "Snack");
    JLabel instr = new JLabel("I want item #:");
    choice = new JTextField(5);
    choice.addKeyListener(
      new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
          int key = e.getKeyCode();
          if (key == KeyEvent.VK_ENTER) {
            itemIndex = choice.getText();
            interactor.initialPurchasing(items.get(itemIndex));
          }
        }
      }
    );
    this.add(instr);
    this.add(choice);
  }

}
