package vendingmachine.gui;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class MoneySession extends JPanel {

  JLabel paid;
  private GUIInteractor interactor;
  private JTextField moneyField;

  public MoneySession(GUIInteractor interactor) {
    this.interactor = interactor;
    JLabel payHereLabel = new JLabel("Pay here:");
    moneyField = new JTextField(5);
    moneyField.addKeyListener(
      new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
          passMoneyAfterPressingEnterKey(e.getKeyCode());
        }
      }
    );
    paid = new JLabel("Paid:");
    this.setLayout(new GridLayout(2, 1));
    this.add(payHereLabel);
    this.add(moneyField);
    this.add(paid);
  }

  private void passMoneyAfterPressingEnterKey(int keyCode) {
    if (keyCode == KeyEvent.VK_ENTER) {
      String payOrRefund = moneyField.getText();
      moneyField.setText("");
      interactor.pay(payOrRefund);
    }
  }

  public void setText(String text) {
    paid.setText(text);
  }
}
