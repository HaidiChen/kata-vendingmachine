package vendingmachine;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import vendingmachine.machine.*;
import vendingmachine.products.ItemInfo;
import vendingmachine.main.MyVendingMachine;

public class BuyBottleWaterTest {

    private VendingMachine machine;
    private Coins mockedCoins;
    private Products mockedProducts;
    private Item item;

    @BeforeEach
    void setUp() {
        item = new ItemInfo("BottleWater", 100, 10);
        mockedProducts = mock(Products.class);
        when(mockedProducts.getItem("BottleWater")).thenReturn(item);

        mockedCoins = mock(Coins.class);
        when(
                mockedCoins.hasValue(
                    intThat(coin -> CoinsTest.isValid(coin)))).thenReturn(true);
        when(
                mockedCoins.hasValue(
                    intThat(coin -> CoinsTest.isNotValid(coin)))).thenReturn(false);

        machine = new MyVendingMachine(mockedCoins, mockedProducts);
    }

    @DisplayName("Not enough money to buy Water")
    @Test
    void buyBottleWaterWithoutEnoughMoneyWillThrowException() {
        machine.takeCoin(20);
        assertThrows(
                NotEnoughMoney.class, () -> machine.popItem("BottleWater"));
    }

    @DisplayName("Bottle Water costs 100 pence")
    @Test
    void returnChangesIfAnyAfterGettingBottleWaterAndSetRemainingChangeToZero() 
        throws NotEnoughMoney, StockEmpty, NoItemException {
        machine.takeCoin(100);
        machine.takeCoin(100);
        assertEquals(100+100-100, machine.popItem("BottleWater"));
        assertEquals(0, machine.getRemainingChange());
    }

    @DisplayName("Cannot buy when water sold out")
    @Test
    void buyWaterWhenStockIsEmptyWillThrowException() {
        machine.takeCoin(100);
        machine.takeCoin(100);
        machine.resetItemStock("BottleWater", 0);
        assertThrows(
                StockEmpty.class, () -> machine.popItem("BottleWater"));
    }

    @Test
    void resetBottleWaterStockToFive() {
        machine.resetItemStock("BottleWater", 5);
        assertEquals(5, machine.getItemStock("BottleWater"));
    }

    @Test
    void stockIsEmptyWhenSoldOut() throws NotEnoughMoney, StockEmpty, NoItemException {
        machine.resetItemStock("BottleWater", 2);
        machine.takeCoin(100);
        machine.popItem("BottleWater");
        machine.takeCoin(100);
        machine.popItem("BottleWater");
        assertEquals(0, machine.getItemStock("BottleWater"));
    }

    @Test
    void buyItemWithWrongName_WillThrowNoItemException() {
        machine.takeCoin(20);
        assertThrows(
                NoItemException.class, () -> machine.popItem("nonsense"));
    }

}
