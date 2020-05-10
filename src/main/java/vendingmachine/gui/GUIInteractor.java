package vendingmachine.gui;

import java.util.Iterator;
import vendingmachine.machine.Item;
import java.awt.*;
import javax.swing.*;
import vendingmachine.machine.*;

public class GUIInteractor extends JFrame {

    private final String REFUND_OPTION = "r";
    private boolean refundRequested = false;
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
        title = new JLabel("My Vending Machine");
        topPanel.add(title);
    }

    private void setUpContent() {
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.yellow);

        productsTable.setData(this.machine.getProducts());
        productsTable = new ProductsTable();
        JScrollPane scrollPane = new JScrollPane(productsTable);
        productsTable.setFillsViewportHeight(true);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        choice = new ChoiceSession(this);
        bottomPanel.add(choice);

        money = new MoneySession(this);
        money.setVisible(false);
        bottomPanel.add(money);

        message = new JLabel();
        bottomPanel.add(message);

        bottomPanel.setLayout(new FlowLayout());

        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);
    }

    public void confirmItemToPurchase(String itemName) {
        itemSelected = itemName;
        if (haveSuchItem(itemSelected)) {
            goToPaymentStep();
        }
    }

    private void goToPaymentStep() {
        money.setVisible(true);
        choice.setVisible(false);
        message.setText("only accept 1,5,20,50 and 100 pence or 'r' to refund");
    }

    private boolean haveSuchItem(String itemName) {
        try {
            machine.popItem(itemName);
        }
        catch (NoItemException ne) {
            informUserOfNoSuchItem(ne);
            return false;
        }
        catch (NotEnoughMoney e) {
        }
        catch (StockEmpty se) {
        }
        return true;
    }

    private void informUserOfNoSuchItem(Exception ne) {
        message.setText(ne.getMessage());
        choice.setText("");
    }

    public void buySelectedItem(int moneyPaidSoFar) {
        try {
            popPurchasedItemAndReturnChangeIfAny(moneyPaidSoFar);
        }
        catch (NotEnoughMoney e) {
            informUserOfNotEnoughMoneyToBuyThisItem(e);
            continuePurchasingOrRefund();
        }
        catch (StockEmpty ex) {
        }
        catch (NoItemException ne) {
        }
    }

    private void continuePurchasingOrRefund() {
        money.setVisible(true);
        money.setText("Paid: " + machine.getRemainingChange());
    }

    private void informUserOfNotEnoughMoneyToBuyThisItem(Exception e) {
        message.setText(e.getMessage());
    }

    private void popPurchasedItemAndReturnChangeIfAny(int moneyPaidSoFar) 
            throws NotEnoughMoney, StockEmpty, NoItemException {
            machine.takeCoin(moneyPaidSoFar);
            int change = machine.popItem(itemSelected);
            informUserOfASuccessfulPurchase(change);
            resetChoiceAndMoneySession();
    }

    private void informUserOfASuccessfulPurchase(int change) {
        message.setText("You got yourself a " + itemSelected + 
                ", and Here's your change: " + change);
    }

    public void pay(String moneyPaid) {
        money.setVisible(false);
        int paying = 0;
        try {
            paying = Integer.parseInt(moneyPaid);
        }
        catch (Exception e) {
            if (moneyPaid.equalsIgnoreCase(REFUND_OPTION)) {
                processRefundAndQuitPurchasing();
                return;
            }
        }
        buySelectedItem(paying);
    }

    private void processRefundAndQuitPurchasing() {
        informUserOfRefundHasBeenDone();
        resetChoiceAndMoneySession();
    }

    private void informUserOfRefundHasBeenDone() {
        message.setText("Here is your refund: " + machine.refund());
    }

    private void resetChoiceAndMoneySession() {
        money.setText("");
        choice.setVisible(true);
        choice.setText("");
    }

}
