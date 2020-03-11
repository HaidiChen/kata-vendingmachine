package vendingmachine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class BuyCokeTest {

  private VendingMachine machine;
  private Coins coins;
  private Products products;

  @BeforeEach
  void setUp() {
    products = new OnSaleProducts();
    products.addItem(new Item("Coke", 150, 10));
    
    coins = PenceCoins.getInstance();
    machine = new VendingMachine(coins, products);
  }

  @DisplayName("Not enough money to buy Coke")
  @Test
  void buyCokeWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    machine.takeCoin(100);
    assertThrows(
        VendingMachine.NotEnoughMoney.class, () -> machine.popItem("Coke"));
  }

  @DisplayName("Coke costs 150 pence")
  @Test
  void returnChangesIfAnyAfterGettingCokeAndSetRemainingChangeToZero() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(100);
    machine.takeCoin(100);
    assertEquals(100+100-150, machine.popItem("Coke"));
    assertEquals(0, machine.getRemainingChange());
  }

  @DisplayName("Cannot buy when Coke sold out")
  @Test
  void buyCokeWhenStockIsEmptyWillThrowException() {
    machine.takeCoin(100);
    machine.takeCoin(100);
    machine.resetItemStock("Coke", 0);
    assertThrows(
      VendingMachine.StockEmpty.class, () -> machine.popItem("Coke"));
  }

  @Test
  void resetCokeStockToFive() {
    machine.resetItemStock("Coke", 5);
    assertEquals(5, machine.getItemStock("Coke"));
  }

  @Test
  void stockIsEmptyWhenSoldOut() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.resetItemStock("Coke", 2);
    for (int i = 0; i < 2; i++) {
      machine.takeCoin(100);
      machine.takeCoin(100);
      machine.popItem("Coke");
    }
    assertEquals(0, machine.getItemStock("Coke"));
  }

}
