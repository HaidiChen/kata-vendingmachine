package vendingmachine;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import vendingmachine.machine.*;
import vendingmachine.products.ItemInfo;
import vendingmachine.main.MyVendingMachine;

public class BuyCokeTest {

  private VendingMachine machine;
  private Coins mockedCoins;
  private Products mockedProducts;
  private Item item;


  @BeforeEach
  void setUp() {
    item = new ItemInfo("Coke", 150, 10);
    mockedProducts = mock(Products.class);
    when(mockedProducts.getItem("Coke")).thenReturn(item);

    mockedCoins = mock(Coins.class);
    when(
      mockedCoins.hasValue(
        intThat(coin -> CoinsTest.isValid(coin)))).thenReturn(true);
    when(
      mockedCoins.hasValue(
        intThat(coin -> CoinsTest.isNotValid(coin)))).thenReturn(false);
    
    machine = new MyVendingMachine(mockedCoins, mockedProducts);
  }

  @DisplayName("Not enough money to buy Coke")
  @Test
  void buyCokeWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    machine.takeCoin(100);
    assertThrows(NotEnoughMoney.class, () -> machine.popItem("Coke"));
  }

  @DisplayName("Coke costs 150 pence")
  @Test
  void returnChangesIfAnyAfterGettingCokeAndSetRemainingChangeToZero() 
    throws NotEnoughMoney, StockEmpty, NoItemException {
    machine.takeCoin(100);
    machine.takeCoin(100);
    assertEquals(100+100-150, machine.popItem("Coke"));
    assertEquals(0, machine.getRemainingChange());
  }

  @DisplayName("Cannot buy when Coke sold out")
  @Test
  void buyCokeWhenStockIsEmptyWillThrowException() {
    machine.takeCoin(100);
    machine.takeCoin(100);
    machine.resetItemStock("Coke", 0);
    assertThrows(StockEmpty.class, () -> machine.popItem("Coke"));
  }

  @Test
  void resetCokeStockToFive() {
    machine.resetItemStock("Coke", 5);
    assertEquals(5, machine.getItemStock("Coke"));
  }

  @Test
  void stockIsEmptyWhenSoldOut()
    throws NotEnoughMoney, StockEmpty, NoItemException {
    machine.resetItemStock("Coke", 2);
    for (int i = 0; i < 2; i++) {
      machine.takeCoin(100);
      machine.takeCoin(100);
      machine.popItem("Coke");
    }
    assertEquals(0, machine.getItemStock("Coke"));
  }

}
