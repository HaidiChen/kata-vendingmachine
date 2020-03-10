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
  void calculateTotalValueOfValidCoinsTakenByMachine() {
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(20);
    assertEquals(22, machine.getTotalValue());
  }

  @Test
  void calculateTotalValueOfInvalidCoinsTakenByMachine() {
    machine.takeCoin(2);
    machine.takeCoin(10);
    machine.takeCoin(15);
    machine.takeCoin(30);
    assertEquals(0, machine.getTotalValue());
  }

  @Test
  void calculateTotalValueOfValidAndInvalidCoinsTakenByMachine() {
    machine.takeCoin(50);
    machine.takeCoin(90);
    machine.takeCoin(100);
    assertEquals(150, machine.getTotalValue());
  }

  @Test
  void refundMakesMachineHasZeroTotalValue() {
    machine.takeCoin(50);
    machine.refund();
    assertEquals(0, machine.getTotalValue());
  }

  @DisplayName("Failed to buy Candy when money is not enough")
  @Test
  void getCandyWhenTotalValueIsLessThanTenPenceWillFail() {
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(5);
    assertFalse(machine.popItem("Candy"));
  }

  @DisplayName("Successfully bought Candy when money is enough")
  @Test
  void getCandyWhenTotalValueIsNotLessThanTenPenceWillSucceed() {
    machine.takeCoin(5);
    machine.takeCoin(5);
    assertTrue(machine.popItem("Candy"));
  }

  @DisplayName("Candy costs 10 pence")
  @Test
  void subtractTenPenceFromTotalValueAfterGettingCandy() {
    machine.takeCoin(20);
    machine.popItem("Candy");
    assertEquals(10, machine.getTotalValue());
  }

  @DisplayName("Failed to buy Snack when money is not enough")
  @Test
  void getSnackWhenTotalValueIsLessThanFiftyPenceWillFail() {
    machine.takeCoin(20);
    machine.takeCoin(20);
    machine.takeCoin(5);
    assertFalse(machine.popItem("Snack"));
  }

  @DisplayName("Successfully bought Snack when money is enough")
  @Test
  void getSnackWhenTotalValueIsNotLessThanFiftyPenceWillSucceed() {
    machine.takeCoin(20);
    machine.takeCoin(20);
    machine.takeCoin(20);
    assertTrue(machine.popItem("Snack"));
  }

  @DisplayName("Snack costs 50 pence")
  @Test
  void subtractFiftyPenceFromTotalValueAfterGettingSnack() {
    machine.takeCoin(50);
    machine.popItem("Snack");
    assertEquals(0, machine.getTotalValue());
  }

  @DisplayName("Failed to buy Nuts when money is not enough")
  @Test
  void getNutsWhenTotalValueIsLessThanSeventyfivePenceWillFail() {
    machine.takeCoin(20);
    machine.takeCoin(50);
    assertFalse(machine.popItem("Nuts"));
  }

  @DisplayName("Successfully bought Nuts when money is enough")
  @Test
  void getNutsWhenTotalValueIsNotLessThanSeventyfivePenceWillSucceed() {
    machine.takeCoin(20);
    machine.takeCoin(20);
    machine.takeCoin(50);
    assertTrue(machine.popItem("Nuts"));
  }

  @DisplayName("Nuts costs 75 pence")
  @Test
  void subtractSeventyfivePenceFromTotalValueAfterGettingNuts() {
    machine.takeCoin(50);
    machine.takeCoin(50);
    machine.popItem("Nuts");
    assertEquals(25, machine.getTotalValue());
  }

  @DisplayName("Failed to buy Coke when money is not enough")
  @Test
  void getCokeWhenTotalValueIsLessThanHundredAndFiftyPenceWillFail() {
    machine.takeCoin(20);
    machine.takeCoin(100);
    assertFalse(machine.popItem("Coke"));
  }

  @DisplayName("Successfully bought Coke when money is enough")
  @Test
  void getCokeWhenTotalValueIsNotLessThanHundredAndFiftyPenceWillSucceed() {
    machine.takeCoin(100);
    machine.takeCoin(50);
    assertTrue(machine.popItem("Coke"));
  }

  @DisplayName("Coke costs 150 pence")
  @Test
  void subtractHundredAndFiftyPenceFromTotalValueAfterGettingCoke() {
    machine.takeCoin(100);
    machine.takeCoin(100);
    machine.popItem("Coke");
    assertEquals(50, machine.getTotalValue());
  }

  @DisplayName("Failed to buy Bottle Water when money is not enough")
  @Test
  void getBottleWaterWhenTotalValueIsLessThanHundredPenceWillFail() {
    machine.takeCoin(20);
    assertFalse(machine.popItem("BottleWater"));
  }

  @DisplayName("Successfully bought Bottle Water when money is enough")
  @Test
  void getBottleWaterWhenTotalValueIsNotLessThanHundredPenceWillSucceed() {
    machine.takeCoin(100);
    machine.takeCoin(50);
    assertTrue(machine.popItem("BottleWater"));
  }

  @DisplayName("Bottle Water costs 100 pence")
  @Test
  void subtractHundredPenceFromTotalValueAfterGettingBottleWater() {
    machine.takeCoin(100);
    machine.takeCoin(100);
    machine.popItem("BottleWater");
    assertEquals(100, machine.getTotalValue());
  }

  @DisplayName("Cannot buy when stock is empty")
  @Test
  void resetVendingMachineStockToZeroWillPreventFromGettingAnyItems() {
    machine.takeCoin(100);
    machine.takeCoin(100);
    machine.resetStock(0);
    assertAll(
        () -> assertFalse(machine.popItem("Candy")),
        () -> assertFalse(machine.popItem("Snack")),
        () -> assertFalse(machine.popItem("Nuts")),
        () -> assertFalse(machine.popItem("Coke")),
        () -> assertFalse(machine.popItem("BottleWater")),
        () -> assertTrue(machine.isEmpty())
    );
  }

  @Test
  void resetStockToFiveForEachItem() {
    machine.resetStock(5);
    assertFalse(machine.isEmpty());
  }

}
