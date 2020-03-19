package vendingmachine.gui;

import java.util.Iterator;
import vendingmachine.machine.Item;
import java.awt.*;
import javax.swing.*;
import vendingmachine.interactor.OutputPlatform;
import vendingmachine.interactor.InputPlatform;
import vendingmachine.machine.*;

public class GUIInteractor extends JFrame {

  private final String REFUND_OPTION = "r";
  private boolean refundRequested = false;
  private String payOrRefund;
  JPanel topPanel;
  JLabel title;
  JPanel bottomPanel;
  ProductsTable productsTable;
  ChoiceSession choice;
  MoneySession money;
  JLabel message;
  VendingMachine machine;
  private String itemSelected;

  public GUIInteractor(VendingMachine machine) {
    this.machine = machine;
    productsTable.setData(this.machine.getProducts());
    productsTable = new ProductsTable();
    choice = new ChoiceSession(this);
    money = new MoneySession(this);
    money.setVisible(false);
    message = new JLabel("message goes here");
    setUpTitle();
    setUpContent();
    setUpFrameProperties();
  }

  private void setUpFrameProperties() {
    this.setSize(500, 600);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  private void setUpTitle() {
    topPanel = new JPanel();
    title = new JLabel("My Vendign Machine");
    topPanel.add(title);
  }
  
  private void setUpContent() {
    bottomPanel = new JPanel();
    bottomPanel.setBackground(Color.yellow);
    JScrollPane scrollPane = new JScrollPane(productsTable);
    productsTable.setFillsViewportHeight(true);
    bottomPanel.add(scrollPane, BorderLayout.CENTER);

    bottomPanel.add(choice);
    bottomPanel.add(money);
    bottomPanel.add(message);

    bottomPanel.setLayout(new FlowLayout());

    this.add(topPanel, BorderLayout.NORTH);
    this.add(bottomPanel, BorderLayout.CENTER);
  }

  public void initialPurchasing(String itemName) {
    if (itemName == null) {
      return;
    }
    itemSelected = itemName;
    money.setVisible(true);
    choice.setVisible(false);
    printInstruction();
  }

  public void buyItem(String itemName, int moneyPaidSoFar) {
    try {
      machine.takeCoin(moneyPaidSoFar);
      int change = machine.popItem(itemName);
      message.setText("You got yourself a " + itemName + 
          ", and Here's your change: " + change);
    }
    catch (NotEnoughMoney e) {
      message.setText(e.getMessage());
      money.setVisible(true);
      money.setPaidLabelText("Paid: " + machine.getRemainingChange());
    }
    catch (StockEmpty ex) {
      message.setText(ex.getMessage());
    }
    catch (NoItemException ne) {
      message.setText(ne.getMessage());
    }
  }

  public void pay(String money) {
    this.money.setVisible(false);
    int paying = 0;
    try {
      paying = Integer.parseInt(money);
    }
    catch (Exception e) {
      if (money.equalsIgnoreCase(REFUND_OPTION)) {
        message.setText("Here is your refund: " + machine.refund());
        this.money.setPaidLabelText("");
        choice.setVisible(true);
        return;
      }
    }
    buyItem(itemSelected, paying);
  }

  private void printInstruction() {
    message.setText("only accept 1,5,20,50 and 100 pence or 'r' to refund");
  }
}
