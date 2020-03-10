package vendingmachine;

import java.util.Set;
import java.util.HashSet;

public class PenceCoins implements Coins {

  private Set<Integer> values = new HashSet<Integer>();

  public PenceCoins() {
    values.add(1);
    values.add(5);
    values.add(20);
    values.add(50);
    values.add(100);
  }

  public boolean hasValue(int coinValue) {
    return values.contains(coinValue);
  }
}
