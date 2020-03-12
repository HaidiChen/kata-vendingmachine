package vendingmachine.ioplatform;

import vendingmachine.interactor.InputPlatform;
import java.util.Scanner;

public class ConsoleInput implements InputPlatform {

  private Scanner scanner;

  public ConsoleInput() {
    scanner = new Scanner(System.in);
  }

  public String getStringInput() {
    String input = scanner.nextLine();
    return input;
  }
}
