package vendingmachine;

import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

  private VendingMachine machine;

  @BeforeEach
  void setUp() {
    Map<String, Item> items = new HashMap<String, Item>();
    items.put("Candy", new Item("Candy", 10, 10));
    items.put("Snack", new Item("Snack", 50, 10));
    items.put("Nuts", new Item("Nuts", 75, 10));
    items.put("Coke", new Item("Coke", 150, 10));
    items.put("BottleWater", new Item("BottleWater", 100, 10));

    Coins coins = new PenceCoins();
    machine = new VendingMachine(coins, items);
  }

  @DisplayName("Accept 1, 5, 20, 50, 100 pence coins")
  @Test
  void machineTakesOneFiveTwentyAndFiftyPenceCoins() {
    assertAll(
      () -> assertTrue(machine.takeCoin(1)),
      () -> assertTrue(machine.takeCoin(5)),
      () -> assertTrue(machine.takeCoin(20)),
      () -> assertTrue(machine.takeCoin(50)),
      () -> assertTrue(machine.takeCoin(100))
    );
  }

  @DisplayName("Machine does not accept invalid coins")
  @Test
  void machineRejectsInvalidCoins() {
    assertAll(
        () -> assertFalse(machine.takeCoin(0)),
        () -> assertFalse(machine.takeCoin(2)),
        () -> assertFalse(machine.takeCoin(6)),
        () -> assertFalse(machine.takeCoin(10)),
        () -> assertFalse(machine.takeCoin(25))
        );
  }

  @Test
  void calculateRemainingChangeOfValidCoinsTakenByMachine() {
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(20);
    assertEquals(22, machine.getRemainingChange());
  }

  @Test
  void calculateRemainingChangeOfInvalidCoinsTakenByMachine() {
    machine.takeCoin(2);
    machine.takeCoin(10);
    machine.takeCoin(15);
    machine.takeCoin(30);
    assertEquals(0, machine.getRemainingChange());
  }

  @Test
  void calculateRemainingChangeOfValidAndInvalidCoinsTakenByMachine() {
    machine.takeCoin(50);
    machine.takeCoin(90);
    machine.takeCoin(100);
    assertEquals(150, machine.getRemainingChange());
  }

  @Test
  void refundClearsRemainingChange() {
    machine.takeCoin(50);
    machine.refund();
    assertEquals(0, machine.getRemainingChange());
  }

  @DisplayName("Not Enough Money to buy Candy")
  @Test
  void buyCandyWithoutEnoughMoneyWillThrowAnException() {
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(5);
    testNotEnoughMoneyException("Candy");
  }

  private void testNotEnoughMoneyException(String itemName) {
    assertThrows(
        VendingMachine.NotEnoughMoney.class, () -> machine.popItem(itemName));
  }

  @DisplayName("Candy costs 10 pence")
  @Test
  void returnChangesIfAnyAfterGettingCandy() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(20);
    assertEquals(20-10, machine.popItem("Candy"));
  }

  @DisplayName("Not enough money to buy Snack")
  @Test
  void buySnackWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    machine.takeCoin(20);
    machine.takeCoin(5);
    testNotEnoughMoneyException("Snack");
  }

  @DisplayName("Snack costs 50 pence")
  @Test
  void returnChangesIfAnyAfterGettingSnack() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(50);
    assertEquals(50-50, machine.popItem("Snack"));
  }

  @DisplayName("Not enough money to buy Nuts")
  @Test
  void buyNutsWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    machine.takeCoin(50);
    testNotEnoughMoneyException("Nuts");
  }

  @DisplayName("Nuts costs 75 pence")
  @Test
  void returnChangesIfAnyAfterGettingNuts() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(50);
    machine.takeCoin(50);
    assertEquals(50+50-75, machine.popItem("Nuts"));
  }

  @DisplayName("Not enough money to buy Coke")
  @Test
  void buyCokeWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    machine.takeCoin(100);
    testNotEnoughMoneyException("Coke");
  }

  @DisplayName("Coke costs 150 pence")
  @Test
  void returnChangesIfAnyAfterGettingCoke() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(100);
    machine.takeCoin(100);
    assertEquals(100+100-150, machine.popItem("Coke"));
  }

  @DisplayName("Not enough money to buy Water")
  @Test
  void buyBottleWaterWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    testNotEnoughMoneyException("BottleWater");
  }

  @DisplayName("Bottle Water costs 100 pence")
  @Test
  void subtractHundredPenceFromRemainingChangeAfterGettingBottleWater() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(100);
    machine.takeCoin(100);
    assertEquals(100+100-100, machine.popItem("BottleWater"));
  }

  @DisplayName("Cannot buy when stock is empty")
  @Test
  void buyItemsWhenStockIsEmptyWillThrowException() {
    machine.takeCoin(100);
    machine.takeCoin(100);
    machine.resetStock(0);
    assertAll(
      () -> testStockEmptyException("Candy"),
      () -> testStockEmptyException("Snack"),
      () -> testStockEmptyException("Nuts"),
      () -> testStockEmptyException("Coke"),
      () -> testStockEmptyException("BottleWater")
    );
  }

  private void testStockEmptyException(String itemName) {
    assertThrows(
      VendingMachine.StockEmpty.class, () -> machine.popItem(itemName));
  }

  @Test
  void resetStockToFiveForEachItem() {
    machine.resetStock(5);
    assertFalse(machine.isItemEmpty("Candy"));
    assertFalse(machine.isItemEmpty("Snack"));
    assertFalse(machine.isItemEmpty("Nuts"));
    assertFalse(machine.isItemEmpty("Coke"));
    assertFalse(machine.isItemEmpty("BottleWater"));
  }

}
