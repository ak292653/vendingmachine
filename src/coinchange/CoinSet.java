package coinchange;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CoinSet {
	
	private TreeMap<Integer, Integer> amounts;
	
	public CoinSet() {
		amounts = new TreeMap<Integer, Integer>();
	}
	
	public void addCoin(int coinValue) {
		addCoins(coinValue, 1);
	}
	
	public void addCoins(int coinValue, int count) {
		if (coinValue <= 0)
			throw new IllegalArgumentException("Coin value negative");
		if (count < 0)
			throw new IllegalArgumentException("Coin count negative");
		int currentCoinCount = 0;
		if (amounts.containsKey(coinValue))
			currentCoinCount = amounts.get(coinValue);
		amounts.put(coinValue, currentCoinCount + count);
	}
	
	public void addCoins(int[] coins) {
		for (int i = 0; i < coins.length; ++i)
			addCoin(coins[i]);
	}
	
	public int getCount(int coinValue) {
		if (amounts.containsKey(coinValue))
			return amounts.get(coinValue);
		return 0;
	}
	
	public int getTotalCoins() {
		int sum = 0;
		for (Map.Entry<Integer, Integer> e : amounts.entrySet()) {
			sum += e.getValue();
		}
		return sum;
	}
	
	public int getTotalMoney() {
		int sum = 0;
		for (Map.Entry<Integer, Integer> e : amounts.entrySet()) {
			sum += e.getKey() * e.getValue();
		}
		return sum;
	}
	
	public Set<Map.Entry<Integer, Integer> > reversedCoinList() {
		return amounts.descendingMap().entrySet();
	}
	
	public int[] toSortedArray() {
		int[] result = new int[getTotalCoins()];
		int index = 0;
		for (Map.Entry<Integer, Integer> e : amounts.entrySet()) {
			for (int j = 0; j < e.getValue(); ++j)
				result[index++] = e.getKey();
		}
		return result;	
	}

	public void subtract(CoinSet change) {
		for (Map.Entry<Integer, Integer> e : change.amounts.entrySet()) {
			if (e.getValue() > 0) {
				if (!amounts.containsKey(e.getKey()) || amounts.get(e.getKey()) < e.getValue())
					throw new IllegalArgumentException("Cannot subtract set.");
				amounts.put(e.getKey(), amounts.get(e.getKey()) - e.getValue());
			}
		}
	}

	public int dispenseLargest() throws ChangeDispenserEmptyException {
		while (!amounts.isEmpty()) {
			int coin = amounts.lastKey();
			if (amounts.get(coin) == 0)
				amounts.remove(coin);
			else {
				amounts.put(coin, amounts.get(coin) - 1);
				return coin;
			}
		}
		throw new ChangeDispenserEmptyException();
	}
	
}
