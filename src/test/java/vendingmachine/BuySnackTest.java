package vendingmachine;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import vendingmachine.machine.*;
import vendingmachine.products.ItemInfo;
import vendingmachine.main.MyVendingMachine;

public class BuySnackTest {

  private VendingMachine machine;
  private Coins mockedCoins;
  private Products mockedProducts;
  private Item item;

  @BeforeEach
  void setUp() {
    item = new ItemInfo("Snack", 50, 10);
    mockedProducts = mock(Products.class);
    when(mockedProducts.getItem("Snack")).thenReturn(item);

    mockedCoins = mock(Coins.class);
    when(
      mockedCoins.hasValue(
        intThat(coin -> CoinsTest.isValid(coin)))).thenReturn(true);
    when(
      mockedCoins.hasValue(
        intThat(coin -> CoinsTest.isNotValid(coin)))).thenReturn(false);
    
    machine = new MyVendingMachine(mockedCoins, mockedProducts);
  }

  @DisplayName("Not enough money to buy Snack")
  @Test
  void buySnackWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    machine.takeCoin(20);
    machine.takeCoin(5);
    assertThrows(NotEnoughMoney.class, () -> machine.popItem("Snack"));
  }

  @DisplayName("Snack costs 50 pence")
  @Test
  void returnChangesIfAnyAfterGettingSnackAndSetRemainingChangeToZero() 
    throws NotEnoughMoney, StockEmpty, NoItemException {
    machine.takeCoin(50);
    assertEquals(50-50, machine.popItem("Snack"));
    assertEquals(0, machine.getRemainingChange());
  }

  @DisplayName("Cannot buy when Snack sold out")
  @Test
  void buySnackWhenStockIsEmptyWillThrowException() {
    machine.takeCoin(100);
    machine.resetItemStock("Snack", 0);
    assertThrows(StockEmpty.class, () -> machine.popItem("Snack"));
  }

  @Test
  void resetSnackStockToFive() {
    machine.resetItemStock("Snack", 5);
    assertEquals(5, machine.getItemStock("Snack"));
  }

  @Test
  void stockIsEmptyWhenSoldOut()
    throws NotEnoughMoney, StockEmpty, NoItemException {
    machine.resetItemStock("Snack", 2);
    for (int i = 0; i < 2; i++) {
      machine.takeCoin(50);
      machine.popItem("Snack");
    }
    assertEquals(0, machine.getItemStock("Snack"));
  }

}
