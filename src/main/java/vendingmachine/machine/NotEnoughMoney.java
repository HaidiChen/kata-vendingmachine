package vendingmachine.machine;

public class NotEnoughMoney extends Exception {
  
  public NotEnoughMoney(String s) {
    super(s);
  }

}
