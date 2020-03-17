package vendingmachine.machine;

public class NoItemException extends Exception {

  public NoItemException(String message) {
    super(message);
  }
}
