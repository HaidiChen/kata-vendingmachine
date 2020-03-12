package vendingmachine;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import vendingmachine.machine.*;
import vendingmachine.products.ItemInfo;
import vendingmachine.main.MyVendingMachine;

public class BuyNutsTest {

  private VendingMachine machine;
  private Coins mockedCoins;
  private Products mockedProducts;
  private Item item;

  @BeforeEach
  void setUp() {
    item = new ItemInfo("Nuts", 75, 10);
    mockedProducts = mock(Products.class);
    when(mockedProducts.getItem("Nuts")).thenReturn(item);

    mockedCoins = mock(Coins.class);
    when(
      mockedCoins.hasValue(
        intThat(coin -> CoinsTest.isValid(coin)))).thenReturn(true);
    when(
      mockedCoins.hasValue(
        intThat(coin -> CoinsTest.isNotValid(coin)))).thenReturn(false);
    
    machine = new MyVendingMachine(mockedCoins, mockedProducts);
  }

  private void testNotEnoughMoneyException(String itemName) {
  }

  @DisplayName("Not enough money to buy Nuts")
  @Test
  void buyNutsWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    machine.takeCoin(50);
    assertThrows(NotEnoughMoney.class, () -> machine.popItem("Nuts"));
  }

  @DisplayName("Nuts costs 75 pence")
  @Test
  void returnChangesIfAnyAfterGettingNutsAndSetRemainingChangeToZero() 
    throws NotEnoughMoney, StockEmpty {
    machine.takeCoin(50);
    machine.takeCoin(50);
    assertEquals(50+50-75, machine.popItem("Nuts"));
    assertEquals(0, machine.getRemainingChange());
  }

  @DisplayName("Cannot buy when Nuts sold out")
  @Test
  void buyNutsWhenStockIsEmptyWillThrowException() {
    machine.takeCoin(100);
    machine.resetItemStock("Nuts", 0);
    assertThrows(StockEmpty.class, () -> machine.popItem("Nuts"));
  }

  @Test
  void resetNutsStockToFive() {
    machine.resetItemStock("Nuts", 5);
    assertEquals(5, machine.getItemStock("Nuts"));
  }

  @Test
  void stockIsEmptyWhenSoldOut() throws NotEnoughMoney, StockEmpty {
    machine.resetItemStock("Nuts", 2);
    for (int i = 0; i < 2; i++) {
      machine.takeCoin(100);
      machine.popItem("Nuts");
    }
    assertEquals(0, machine.getItemStock("Nuts"));
  }

}
