package vendingmachine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class BuyNutsTest {

  private VendingMachine machine;
  private Coins coins;
  private Products products;

  @BeforeEach
  void setUp() {
    products = new OnSaleProducts();
    products.addItem(new Item("Nuts", 75, 10));
    
    coins = PenceCoins.getInstance();
    machine = new VendingMachine(coins, products);
  }

  private void testNotEnoughMoneyException(String itemName) {
  }

  @DisplayName("Not enough money to buy Nuts")
  @Test
  void buyNutsWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    machine.takeCoin(50);
    assertThrows(
        VendingMachine.NotEnoughMoney.class, () -> machine.popItem("Nuts"));
  }

  @DisplayName("Nuts costs 75 pence")
  @Test
  void returnChangesIfAnyAfterGettingNutsAndSetRemainingChangeToZero() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(50);
    machine.takeCoin(50);
    assertEquals(50+50-75, machine.popItem("Nuts"));
    assertEquals(0, machine.getRemainingChange());
  }

  @DisplayName("Cannot buy when Nuts sold out")
  @Test
  void buyNutsWhenStockIsEmptyWillThrowException() {
    machine.takeCoin(100);
    machine.resetItemStock("Nuts", 0);
    assertThrows(
      VendingMachine.StockEmpty.class, () -> machine.popItem("Nuts"));
  }

  @Test
  void resetNutsStockToFive() {
    machine.resetItemStock("Nuts", 5);
    assertEquals(5, machine.getItemStock("Nuts"));
  }

  @Test
  void stockIsEmptyWhenSoldOut() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.resetItemStock("Nuts", 2);
    for (int i = 0; i < 2; i++) {
      machine.takeCoin(100);
      machine.popItem("Nuts");
    }
    assertEquals(0, machine.getItemStock("Nuts"));
  }

}
