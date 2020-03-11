package vendingmachine;

import java.util.Set;
import java.util.Scanner;
import java.util.Map;

public class MachineInteractor {

  private VendingMachine machine;

  private boolean refundRequested = false;
  private Scanner scanner;

  public MachineInteractor(VendingMachine machine) {
    this.machine = machine;
    scanner = new Scanner(System.in);
  }

  private int insertCoins(int moneyInMachine) {
    System.out.println("Insert coin (1, 5, 20, 50, 100) " + 
        "Or press r for requesting a refund:");
    String coinInserted = scanner.nextLine();
    if (coinInserted.equals("r")) {
      System.out.println("Here is your refund: " + machine.refund() + " Pence");
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
      System.out.println(
          "You got yourself a " + itemName + 
          ", and here is your change: " + changes);
    }
    catch (VendingMachine.NotEnoughMoney e) {
      System.out.println(e.getMessage());
      System.out.println("[Money Inserted]: " + machine.getRemainingChange());
      int currentMoneyInMachine = insertCoins(totalMoneyInMachine);
      if (!refundRequested) {
        buyItem(itemName, currentMoneyInMachine);
      }
    }
    catch (VendingMachine.StockEmpty ex) {
      System.out.println(ex.getMessage());
    }
  }

  public void start() {
    displayItemsInMachine();
    chooseOneItemToBuy();
  }

  private void displayItemsInMachine() {
    System.out.println("We have following items: ");
    Map<String, Item> items = machine.getItemsMap();
    for (String name: (Set<String>)items.keySet()) {
      Item item = items.get(name);
      System.out.println("--" + name + " (" + item.getPrice() + " Pence) [" + 
          machine.getItemStock(name) + " in stock]");
    }
  }

  private void chooseOneItemToBuy() {
    System.out.println("which one do you want?");
    String itemName = scanner.nextLine();
    buyItem(itemName, 0);
  }

}