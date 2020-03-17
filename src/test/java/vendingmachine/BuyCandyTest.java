package vendingmachine;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import vendingmachine.machine.*;
import vendingmachine.products.ItemInfo;
import vendingmachine.main.MyVendingMachine;

public class BuyCandyTest {

  private VendingMachine machine;
  private Coins mockedCoins;
  private Products mockedProducts;
  private Item item;

  @BeforeEach
  void setUp() {
    item = new ItemInfo("Candy", 10, 10);
    mockedProducts = mock(Products.class);
    when(mockedProducts.getItem("Candy")).thenReturn(item);

    mockedCoins = mock(Coins.class);
    when(
      mockedCoins.hasValue(
        intThat(coin -> CoinsTest.isValid(coin)))).thenReturn(true);
    when(
      mockedCoins.hasValue(
        intThat(coin -> CoinsTest.isNotValid(coin)))).thenReturn(false);
    
    machine = new MyVendingMachine(mockedCoins, mockedProducts);
  }

  @DisplayName("Not Enough Money to buy Candy")
  @Test
  void buyCandyWithoutEnoughMoneyWillThrowAnException() {
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(5);
    assertThrows(NotEnoughMoney.class, () -> machine.popItem("Candy"));
  }

  @DisplayName("Candy costs 10 pence")
  @Test
  void returnChangesIfAnyAfterGettingCandyAndSetRemainingChangeToZero() 
    throws NotEnoughMoney, StockEmpty, NoItemException {
    machine.takeCoin(20);
    assertEquals(20-10, machine.popItem("Candy"));
    assertEquals(0, machine.getRemainingChange());
  }

  @DisplayName("Cannot buy when Candy sold out")
  @Test
  void buyCandyWhenStockIsEmptyWillThrowException() {
    machine.takeCoin(100);
    machine.resetItemStock("Candy", 0);
    assertThrows(StockEmpty.class, () -> machine.popItem("Candy"));
  }

  @Test
  void resetCandyStockToFive() {
    machine.resetItemStock("Candy", 5);
    assertEquals(5, machine.getItemStock("Candy"));
  }

  @Test
  void stockIsEmptyWhenSoldOut() throws NotEnoughMoney, StockEmpty, NoItemException {
    machine.resetItemStock("Candy", 2);
    for (int i = 0; i < 2; i++) {
      machine.takeCoin(20);
      machine.popItem("Candy");
    }
    assertEquals(0, machine.getItemStock("Candy"));
  }

}
