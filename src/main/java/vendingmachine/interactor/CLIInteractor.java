package vendingmachine.interactor;

import vendingmachine.machine.*;
import java.util.Scanner;

public class CLIInteractor {

    private static final String REFUND_OPTION = "r";
    private VendingMachine machine;
    private OutputPlatform output;
    private InputPlatform input;
    private boolean refundRequested = false;

    public CLIInteractor(
            VendingMachine machine, InputPlatform input, OutputPlatform output) {
        this.machine = machine;
        this.input = input;
        this.output = output;
            }

    private int moneyInMachineSoFar() {
        return machine.getRemainingChange();
    }

    private String askUserToPayOrQuitWithRefund() {
        output.printInstruction();
        return input.inputMoney();
    }

    private void takeTheCoinOrQuitWithRefund(String payOrRefund) {
        if (selectedRefundOption(payOrRefund)) {
            processRefund();
        }
        else if (paidMore(payOrRefund)){
            machineTakesTheCoin(payOrRefund);
        }
    }

    private boolean paidMore(String payOrRefund) {
        int paid;
        try {
            paid = Integer.parseInt(payOrRefund);
            return true;
        }
        catch (Exception e) { // User puts invalid string otherthan "r" or numbers
            output.printExceptionMessage(e);
            return false;
        }
    }

    private void processRefund() {
        output.printRefundInfo(machine.refund());
        refundRequested = true;
    }

    private boolean selectedRefundOption(String payOrRefund) {
        return payOrRefund.equalsIgnoreCase(REFUND_OPTION);
    }

    private void machineTakesTheCoin(String payOrRefund) {
        machine.takeCoin(Integer.parseInt(payOrRefund));
    }

    public void start() {
        displayItemsInMachine();
        letUserChooseOneItemToBuy();
    }

    private void displayItemsInMachine() {
        output.printSalesTitle();
        output.printItemsNamePriceAndStock(machine.getProducts());
    }

    private void letUserChooseOneItemToBuy() {
        output.printAskingForSelection();
        askUserToSelectOneItemToBuy();
    }

    private void askUserToSelectOneItemToBuy() {
        String selectedItemName = input.inputItemName();
        buyItem(selectedItemName, 0);
    }

    private void buyItem(String itemName, int moneyPaidSoFar) {
        try {
            returnTheSelectedItemAndChangeIfAny(itemName);
        }
        catch (NotEnoughMoney e) {
            informUserOfNextSteps(e, itemName, moneyPaidSoFar);
        }
        catch (StockEmpty ex) {
            informUserOfItemSoldOutAndQuit(ex);
        }
        catch (NoItemException ne) {
            informUserOfNoSuchItemAndQuit(ne);
        }
    }

    private void informUserOfNoSuchItemAndQuit(Exception e) {
        output.printExceptionMessage(e);
    }

    private void informUserOfNextSteps(
            Exception e, String itemName, int moneyPaidSoFar) {
        tellUserThatMoneyWasNotEnoughToBuyTheItem(e);
        int currentMoneyPaidSoFar = afterInsertCoinsOrAskRefund(moneyPaidSoFar);
        continuePurchaseOrQuit(itemName, currentMoneyPaidSoFar);
            }

    private void informUserOfItemSoldOutAndQuit(Exception e) {
        output.printExceptionMessage(e);
    }

    private int afterInsertCoinsOrAskRefund(int moneyInMachine) {
        String payOrRefund = askUserToPayOrQuitWithRefund();
        takeTheCoinOrQuitWithRefund(payOrRefund);
        return moneyInMachineSoFar();
    }

    private void continuePurchaseOrQuit(String itemName, int moneyPaidSoFar) {
        if (refundRequested) { 
            refundRequested = false;  // next purchasing has no requested refund
            return; // quit the current purchasing
        } 
        else {
            buyItem(itemName, moneyPaidSoFar);
        }
    }

    private void tellUserThatMoneyWasNotEnoughToBuyTheItem(Exception e) {
        output.printExceptionMessage(e);
        output.printMoneyPaid(machine.getRemainingChange());
    }

    private void returnTheSelectedItemAndChangeIfAny(String itemName) 
            throws NotEnoughMoney, StockEmpty, NoItemException {
            int change = machine.popItem(itemName);
            output.printNameOfItemPurchasedAndChange(itemName, change);
    }

}
