package vendingmachine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import vendingmachine.machine.*;
import vendingmachine.products.*;
import vendingmachine.coins.*;

public class CoinsTest {

  private VendingMachine machine;
  private Coins coins;
  private Products products;

  @BeforeEach
  void setUp() {
    products = new OnSaleProducts();
    coins = PenceCoins.getInstance();
    machine = new VendingMachine(coins, products);
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
  void refundReturnsRemainingChangeBackAndSetRemainingChangeToZero() {
    machine.takeCoin(50);
    assertEquals(50, machine.refund());
    assertEquals(0, machine.getRemainingChange());
  }

}
