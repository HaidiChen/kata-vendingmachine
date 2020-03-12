package vendingmachine.interactor;

import vendingmachine.machine.Products;

public interface OutputPlatform {
  public void printAskingForSelection();
  public void printExceptionMessage(Exception ex);
  public void printSalesTitle();
  public void printItemsNamePriceAndStock(Products products);
  public void printNameOfItemPurchasedAndChange(String itemName, int change);
  public void printMoneyPaid(int moneyPaid);
  public void printInstruction();
  public void printRefundInfo(int refundAmount);
}
