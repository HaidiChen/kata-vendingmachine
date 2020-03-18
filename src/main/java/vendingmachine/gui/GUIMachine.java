package vendingmachine.gui;

import java.util.Iterator;
import vendingmachine.machine.Item;
import java.awt.*;
import javax.swing.*;
import vendingmachine.interactor.OutputPlatform;
import vendingmachine.machine.Products;
import vendingmachine.machine.VendingMachine;

public class GUIMachine extends JFrame implements OutputPlatform {

  JPanel topPanel;
  JLabel title;
  JPanel bottomPanel;
  JPanel leftPanel;
  JPanel rightPanel;
  ProductsTable productsTable;

  public GUIMachine(VendingMachine machine) {
    productsTable.setData(machine.getProducts());
    productsTable = new ProductsTable();
    productsTable.setVisible(false);
    setUpTitle();
    setUpContent();
    setUpFrameProperties();
  }

  public void printAskingForSelection() {
  }

  public void printExceptionMessage(Exception e) {
  }

  public void printSalesTitle() {
    title.setVisible(true);
  }

  public void printItemsNamePriceAndStock(Products products) {
    productsTable.setVisible(true);
  }

  public void printNameOfItemPurchasedAndChange(String itemName, int change) {
  }

  public void printMoneyPaid(int moneyPaid) {
  }

  public void printInstruction() {
  }

  public void printRefundInfo(int refundAmount) {
  }

  private void setUpFrameProperties() {
    this.setSize(600, 800);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  private void setUpTitle() {
    topPanel = new JPanel();
    title = new JLabel("My Vendign Machine");
    title.setVisible(false);
    topPanel.add(title);
  }
  
  private void setUpContent() {
    bottomPanel = new JPanel();
    bottomPanel.setBackground(Color.yellow);
    leftPanel = new JPanel();
    leftPanel.add(productsTable, BorderLayout.CENTER);

    rightPanel = new JPanel();
    rightPanel.setBackground(Color.gray);

    bottomPanel.add(leftPanel);
    bottomPanel.add(rightPanel);
    bottomPanel.setLayout(new FlowLayout());

    this.add(topPanel, BorderLayout.NORTH);
    this.add(bottomPanel, BorderLayout.CENTER);
  }
}
