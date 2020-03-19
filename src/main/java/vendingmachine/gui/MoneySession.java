package vendingmachine.gui;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class MoneySession extends JPanel {

  JLabel paid;

  public MoneySession(GUIInteractor interactor) {
    JLabel label = new JLabel("Pay here:");
    JTextField money = new JTextField(5);
    money.addKeyListener(
      new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
          int key = e.getKeyCode();
          if (key == KeyEvent.VK_ENTER) {
            String payOrRefund = money.getText();
            money.setText("");
            interactor.pay(payOrRefund);
          }
        }
      }
    );
    paid = new JLabel("Paid:");
    this.setLayout(new GridLayout(2, 1));
    this.add(label);
    this.add(money);
    this.add(paid);
  }

  public void setPaidLabelText(String text) {
    paid.setText(text);
  }
}
