package vendingmachine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

  private VendingMachine machine;

  @BeforeEach
  void setUp() {
    machine = new VendingMachine();
  }

  @DisplayName("Accept 1, 5, 20, 50, 100 pence coins")
  @Test
  void machineTakesOneFiveTwentyAndFiftyPenceCoins() {
    assertAll("valid pence coins",
      () -> assertTrue(machine.takeCoin(1)),
      () -> assertTrue(machine.takeCoin(5)),
      () -> assertTrue(machine.takeCoin(20)),
      () -> assertTrue(machine.takeCoin(50)),
      () -> assertTrue(machine.takeCoin(100))
    );
  }

  @Test
  void machineRejectsInvalidCoins() {
    assertAll("invalid pence coins",
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

  @Test
  void getCandyWhenTotalValueIsLessThanTenPenceWillFail() {
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(5);
    assertFalse(machine.popItem("Candy"));
  }

  @Test
  void getCandyWhenTotalValueIsNotLessThanTenPenceWillSucceed() {
    machine.takeCoin(5);
    machine.takeCoin(5);
    assertTrue(machine.popItem("Candy"));
  }

  @Test
  void subtractTenPenceFromTotalValueAfterGettingCandy() {
    machine.takeCoin(20);
    machine.popItem("Candy");
    assertEquals(10, machine.getTotalValue());
  }

  @Test
  void getSnackWhenTotalValueIsLessThanFiftyPenceWillFail() {
    machine.takeCoin(20);
    machine.takeCoin(20);
    machine.takeCoin(5);
    assertFalse(machine.popItem("Snack"));
  }

  @Test
  void getSnackWhenTotalValueIsNotLessThanFiftyPenceWillSucceed() {
    machine.takeCoin(20);
    machine.takeCoin(20);
    machine.takeCoin(20);
    assertTrue(machine.popItem("Snack"));
  }

  @Test
  void subtractFiftyPenceFromTotalValueAfterGettingSnack() {
    machine.takeCoin(50);
    machine.popItem("Snack");
    assertEquals(0, machine.getTotalValue());
  }

  @Test
  void getNutsWhenTotalValueIsLessThanSeventyfivePenceWillFail() {
    machine.takeCoin(20);
    machine.takeCoin(50);
    assertFalse(machine.popItem("Nuts"));
  }

  @Test
  void getNutsWhenTotalValueIsNotLessThanSeventyfivePenceWillSucceed() {
    machine.takeCoin(20);
    machine.takeCoin(20);
    machine.takeCoin(50);
    assertTrue(machine.popItem("Nuts"));
  }

  @Test
  void subtractSeventyfivePenceFromTotalValueAfterGettingNuts() {
    machine.takeCoin(50);
    machine.takeCoin(50);
    machine.popItem("Nuts");
    assertEquals(25, machine.getTotalValue());
  }

  @Test
  void getCokeWhenTotalValueIsLessThanHundredAndFiftyPenceWillFail() {
    machine.takeCoin(20);
    machine.takeCoin(100);
    assertFalse(machine.popItem("Coke"));
  }

  @Test
  void getCokeWhenTotalValueIsNotLessThanHundredAndFiftyPenceWillSucceed() {
    machine.takeCoin(100);
    machine.takeCoin(50);
    assertTrue(machine.popItem("Coke"));
  }

  @Test
  void subtractHundredAndFiftyPenceFromTotalValueAfterGettingCoke() {
    machine.takeCoin(100);
    machine.takeCoin(100);
    machine.popItem("Coke");
    assertEquals(50, machine.getTotalValue());
  }

  @Test
  void getBottleWaterWhenTotalValueIsLessThanHundredPenceWillFail() {
    machine.takeCoin(20);
    assertFalse(machine.popItem("BottleWater"));
  }

  @Test
  void getBottleWaterWhenTotalValueIsNotLessThanHundredPenceWillSucceed() {
    machine.takeCoin(100);
    machine.takeCoin(50);
    assertTrue(machine.popItem("BottleWater"));
  }

  @Test
  void subtractHundredPenceFromTotalValueAfterGettingBottleWater() {
    machine.takeCoin(100);
    machine.takeCoin(100);
    machine.popItem("BottleWater");
    assertEquals(100, machine.getTotalValue());
  }

  @Test
  void resetVendingMachineStockToZeroWillPreventFromGettingAnyItems() {
    machine.takeCoin(100);
    machine.takeCoin(100);
    machine.reset(0);
    assertAll("No Stock in Vending Machine",
        () -> assertFalse(machine.popItem("Candy")),
        () -> assertFalse(machine.popItem("Snack")),
        () -> assertFalse(machine.popItem("Nuts")),
        () -> assertFalse(machine.popItem("Coke")),
        () -> assertFalse(machine.popItem("BottleWater"))
    );
  }

}
