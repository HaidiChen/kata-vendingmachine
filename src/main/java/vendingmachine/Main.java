package vendingmachine;

public class Main {

  public static void main(String[] args) {
    Products products = prepareItemsForMachine();
    Coins penceCoins = PenceCoins.getInstance();
    VendingMachine machine = new VendingMachine(penceCoins, products);

    MachineInteractor interactor = new MachineInteractor(machine);
    OutputPlatform output = new ConsoleOutput();
    interactor.setOutputPlatform(output);
    interactor.start();
  }

  private static Products prepareItemsForMachine() {
    Products products = new OnSaleProducts();
    products.addItem(new Item("Candy", 10, 5));
    products.addItem(new Item("Snack", 50, 10));
    products.addItem(new Item("Nuts", 75, 10));
    products.addItem(new Item("Coke", 150, 10));
    products.addItem(new Item("BottleWater", 100, 10));
    return products;
  }

}
