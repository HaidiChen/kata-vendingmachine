package vendingmachine;

import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class BuySnackTest {

  private VendingMachine machine;
  private Map<String, Item> items;
  private Coins coins;

  @BeforeEach
  void setUp() {
    items = new HashMap<String, Item>();
    items.put("Snack", new Item("Snack", 50, 10));
    
    coins = PenceCoins.getInstance();
    machine = new VendingMachine(coins, items);
  }

  @DisplayName("Not enough money to buy Snack")
  @Test
  void buySnackWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    machine.takeCoin(20);
    machine.takeCoin(5);
    assertThrows(
        VendingMachine.NotEnoughMoney.class, () -> machine.popItem("Snack"));
  }

  @DisplayName("Snack costs 50 pence")
  @Test
  void returnChangesIfAnyAfterGettingSnackAndSetRemainingChangeToZero() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(50);
    assertEquals(50-50, machine.popItem("Snack"));
    assertEquals(0, machine.getRemainingChange());
  }

  @DisplayName("Cannot buy when Snack sold out")
  @Test
  void buySnackWhenStockIsEmptyWillThrowException() {
    machine.takeCoin(100);
    machine.resetItemStock("Snack", 0);
    assertThrows(
      VendingMachine.StockEmpty.class, () -> machine.popItem("Snack"));
  }

  @Test
  void resetSnackStockToFive() {
    machine.resetItemStock("Snack", 5);
    assertEquals(5, machine.getItemStock("Snack"));
  }

  @Test
  void stockIsEmptyWhenSoldOut() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.resetItemStock("Snack", 2);
    for (int i = 0; i < 2; i++) {
      machine.takeCoin(50);
      machine.popItem("Snack");
    }
    assertEquals(0, machine.getItemStock("Snack"));
  }

}
