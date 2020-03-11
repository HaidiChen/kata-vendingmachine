package vendingmachine;

import java.util.Scanner;

public class MachineInteractor {

  private VendingMachine machine;
  private OutputPlatform output;

  private boolean refundRequested = false;
  private Scanner scanner;

  public MachineInteractor(VendingMachine machine) {
    this.machine = machine;
    scanner = new Scanner(System.in);
  }

  public void setOutputPlatform(OutputPlatform output) {
    this.output = output;
  }

  private int insertCoins(int moneyInMachine) {
    output.printInstruction();
    String coinInserted = scanner.nextLine();
    if (coinInserted.equals("r")) {
      output.printRefundInfo(machine.refund());
      refundRequested = true;
    }
    else {
      machine.takeCoin(Integer.parseInt(coinInserted));
    }
    return machine.getRemainingChange();
  }

  private void buyItem(String itemName, int totalMoneyInMachine) {
    try {
      int change = machine.popItem(itemName);
      output.printNameOfItemPurchasedAndChange(itemName, change);
    }
    catch (VendingMachine.NotEnoughMoney e) {
      output.printExceptionMessage(e);
      output.printMoneyPaid(machine.getRemainingChange());
      int currentMoneyInMachine = insertCoins(totalMoneyInMachine);
      if (!refundRequested) {
        buyItem(itemName, currentMoneyInMachine);
      }
    }
    catch (VendingMachine.StockEmpty ex) {
      output.printExceptionMessage(ex);
    }
  }

  public void start() {
    displayItemsInMachine();
    chooseOneItemToBuy();
  }

  private void displayItemsInMachine() {
    output.printSalesTitle();
    output.printItemsNamePriceAndStock(machine.getProducts());
  }

  private void chooseOneItemToBuy() {
    output.printAskingForSelection();
    String itemName = scanner.nextLine();
    buyItem(itemName, 0);
  }

}
