package vendingmachine.ioplatform;

import vendingmachine.interactor.OutputPlatform;
import vendingmachine.machine.Products;
import vendingmachine.machine.Item;
import java.util.Iterator;

public class ConsoleOutput implements OutputPlatform {

    public void printAskingForSelection() {
        System.out.println("Come and pick one for your day.");
    }

    public void printExceptionMessage(Exception ex) {
        System.out.println(ex.getMessage());
    }

    public void printSalesTitle() {
        System.out.println("Today's Sale:");
    }

    public void printItemsNamePriceAndStock(Products products) {
        Iterator<String> names= products.getAllItemNames();
        while (names.hasNext()) {
            Item item = products.getItem(names.next());
            System.out.println("--" + item.getName() + 
                    " (" + item.getPrice() + " Pence) [" + item.getStock() + " in stock]");
        }
    }

    public void printNameOfItemPurchasedAndChange(String itemName, int change) {
        System.out.println( "You got yourself a " + itemName + 
                ", and here is your change: " + change);
    }

    public void printMoneyPaid(int moneyPaid) {
        System.out.println("[Money Inserted]: " + moneyPaid);
    }

    public void printInstruction() {
        System.out.println("Insert coin (1, 5, 20, 50, 100) " + 
                "Or press r for requesting a refund:");
    }

    public void printRefundInfo(int refundAmount) {
        System.out.println("Here is your refund: " + refundAmount + " Pence");
    }

}
