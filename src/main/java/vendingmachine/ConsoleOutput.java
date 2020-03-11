package vendingmachine;

public class ConsoleOutput implements OutputPlatform {

  public void print(String info) {
    System.out.println(info);
  }
}
