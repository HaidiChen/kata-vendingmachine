package vendingmachine;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import vendingmachine.main.MyVendingMachine;
import vendingmachine.machine.*;

public class CoinsTest {

  private VendingMachine machine;
  private Coins mockedCoins;
  private Products mockedProducts;

  @BeforeEach
  void setUp() {
    mockedProducts = mock(Products.class);
    mockedCoins = mock(Coins.class);
    machine = new MyVendingMachine(mockedCoins, mockedProducts);
    when(mockedCoins.hasValue(intThat(coin -> isValid(coin)))).thenReturn(true);
    when(
      mockedCoins.hasValue(intThat(coin -> isNotValid(coin)))).thenReturn(false);
  }

  @Test
  void machineTakesOneFiveTwentyFiftyAndHundredPenceCoins() {
    assertAll(
      () -> assertTrue(machine.takeCoin(1)),
      () -> assertTrue(machine.takeCoin(5)),
      () -> assertTrue(machine.takeCoin(20)),
      () -> assertTrue(machine.takeCoin(50)),
      () -> assertTrue(machine.takeCoin(100))
    );
  }

  public static boolean isValid(int coin) {
    return coin == 1 || coin == 5 || coin == 20 || coin == 50 || coin == 100;
  }

  public static boolean isNotValid(int coin) {
    return !isValid(coin);
  }

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


