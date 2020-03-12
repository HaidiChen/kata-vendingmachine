package vendingmachine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import vendingmachine.machine.*;
import vendingmachine.products.*;
import vendingmachine.coins.*;

public class BuyBottleWaterTest {

  private VendingMachine machine;
  private Coins coins;
  private Products products;

  @BeforeEach
  void setUp() {
    products = new OnSaleProducts();
    products.addItem(new ItemInfo("BottleWater", 100, 10));
    
    coins = PenceCoins.getInstance();
    machine = new VendingMachine(coins, products);
  }

  @DisplayName("Not enough money to buy Water")
  @Test
  void buyBottleWaterWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    assertThrows(
        VendingMachine.NotEnoughMoney.class, () -> machine.popItem("BottleWater"));
  }

  @DisplayName("Bottle Water costs 100 pence")
  @Test
  void returnChangesIfAnyAfterGettingBottleWaterAndSetRemainingChangeToZero() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(100);
    machine.takeCoin(100);
    assertEquals(100+100-100, machine.popItem("BottleWater"));
    assertEquals(0, machine.getRemainingChange());
  }

  @DisplayName("Cannot buy when water sold out")
  @Test
  void buyWaterWhenStockIsEmptyWillThrowException() {
    machine.takeCoin(100);
    machine.takeCoin(100);
    machine.resetItemStock("BottleWater", 0);
    assertThrows(
      VendingMachine.StockEmpty.class, () -> machine.popItem("BottleWater"));
  }

  @Test
  void resetBottleWaterStockToFive() {
    machine.resetItemStock("BottleWater", 5);
    assertEquals(5, machine.getItemStock("BottleWater"));
  }

  @Test
  void stockIsEmptyWhenSoldOut() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.resetItemStock("BottleWater", 2);
    for (int i = 0; i < 2; i++) {
      machine.takeCoin(100);
      machine.popItem("BottleWater");
    }
    assertEquals(0, machine.getItemStock("BottleWater"));
  }

}
