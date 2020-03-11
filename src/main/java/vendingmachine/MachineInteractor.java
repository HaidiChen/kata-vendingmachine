package vendingmachine;

import java.util.Iterator;
import java.util.Set;
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
    output.print("Insert coin (1, 5, 20, 50, 100) " + 
        "Or press r for requesting a refund:");
    String coinInserted = scanner.nextLine();
    if (coinInserted.equals("r")) {
      output.print("Here is your refund: " + machine.refund() + " Pence");
      refundRequested = true;
    }
    else {
      machine.takeCoin(Integer.parseInt(coinInserted));
    }
    return machine.getRemainingChange();
  }

  private void buyItem(String itemName, int totalMoneyInMachine) {
    try {
      int changes = machine.popItem(itemName);
      output.print(
          "You got yourself a " + itemName + 
          ", and here is your change: " + changes);
    }
    catch (VendingMachine.NotEnoughMoney e) {
      output.print(e.getMessage());
      output.print("[Money Inserted]: " + machine.getRemainingChange());
      int currentMoneyInMachine = insertCoins(totalMoneyInMachine);
      if (!refundRequested) {
        buyItem(itemName, currentMoneyInMachine);
      }
    }
    catch (VendingMachine.StockEmpty ex) {
      output.print(ex.getMessage());
    }
  }

  public void start() {
    displayItemsInMachine();
    chooseOneItemToBuy();
  }

  private void displayItemsInMachine() {
    output.print("We have following products for sale: ");
    Products products = machine.getProducts();
    Iterator<String> names= products.getAllItemNames();
    while (names.hasNext()) {
      Item item = products.getItem(names.next());
      output.print("--" + item.getName() + 
          " (" + item.getPrice() + " Pence) [" + item.getStock() + " in stock]");
    }
  }

  private void chooseOneItemToBuy() {
    output.print("which one do you want?");
    String itemName = scanner.nextLine();
    buyItem(itemName, 0);
  }

}
