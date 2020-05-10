package vendingmachine.coins;

import vendingmachine.machine.Coins;

import java.util.Set;
import java.util.HashSet;

public class PenceCoins implements Coins {

    private Set<Integer> values;
    private static PenceCoins instance = new PenceCoins();

    private PenceCoins() {
        values = new HashSet<Integer>();
        values.add(1);
        values.add(5);
        values.add(20);
        values.add(50);
        values.add(100);
    }

    public static Coins getInstance() {
        return instance;
    }

    public boolean hasValue(int coinValue) {
        return values.contains(coinValue);
    }
}
