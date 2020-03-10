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
    
    coins = new PenceCoins();
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
  void returnChangesIfAnyAfterGettingSnack() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(50);
    assertEquals(50-50, machine.popItem("Snack"));
  }

}
