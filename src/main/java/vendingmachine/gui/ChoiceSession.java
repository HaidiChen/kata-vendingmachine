package vendingmachine.gui;

import java.util.HashMap;
import java.awt.event.*;
import javax.swing.*;

public class ChoiceSession extends JPanel {

  static HashMap<String, String> items = new HashMap<>();
  JTextField choice;
  private String itemIndex;
  private GUIInteractor interactor;

  private void setUpItems() {
    items.put("0", "Nuts");
    items.put("1", "Coke");
    items.put("2", "Candy");
    items.put("3", "BottleWater");
    items.put("4", "Snack");
  }

  public ChoiceSession(GUIInteractor interactor) {
    this.interactor = interactor;
    setUpItems();
    JLabel instr = new JLabel("I want item #:");
    choice = new JTextField(5);
    choice.addKeyListener(
      new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
          passSelectedItemNameAfterPressingEnterKey(e.getKeyCode());
        }
      }
    );
    this.add(instr);
    this.add(choice);
  }

  private void passSelectedItemNameAfterPressingEnterKey(int keyCode) {
    if (keyCode == KeyEvent.VK_ENTER) {
      itemIndex = choice.getText();
      interactor.setItemIndex(itemIndex);
      String itemName = items.get(itemIndex);
      itemName = itemName == null? "": itemName;
      interactor.confirmItemToPurchase(itemName);
    }
  }

  public void setText(String text) {
    choice.setText(text);
  }

}
