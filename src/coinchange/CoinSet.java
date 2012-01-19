package coinchange;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CoinSet {
	
	private TreeMap<Integer, Integer> amounts;
	private int totalCoins;
	private int totalMoney;
	
	public CoinSet() {
		amounts = new TreeMap<Integer, Integer>();
		totalCoins = 0;
		totalMoney = 0;
	}
	
	public void addCoin(int coinValue) {
		addCoins(coinValue, 1);
	}
	
	public void addCoins(int coinValue, int count) {
		if (coinValue <= 0)
			throw new IllegalArgumentException("Coin value not positive");
		if (count < 0)
			throw new IllegalArgumentException("Coin count negative");
		int currentCoinCount = 0;
		if (amounts.containsKey(coinValue))
			currentCoinCount = amounts.get(coinValue);
		amounts.put(coinValue, currentCoinCount + count);
		totalCoins += count;
		totalMoney += count * coinValue;
	}
	
	private void removeCoins(int coinValue, int count) {
		if (coinValue <= 0)
			throw new IllegalArgumentException("Coin value not positive");
		if (count < 0)
			throw new IllegalArgumentException("Coin count negative");
		int currentCoinCount = 0;
		if (amounts.containsKey(coinValue))
			currentCoinCount = amounts.get(coinValue);
		if (currentCoinCount < count)
			throw new IllegalArgumentException("Too much coins to remove");
		amounts.put(coinValue, currentCoinCount - count);
		totalCoins -= count;
		totalMoney -= count * coinValue;
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
		return totalCoins;
	}
	
	public int getTotalMoney() {
		return totalMoney;
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
				removeCoins(e.getKey(), e.getValue());
			}
		}
	}

	public int dispenseLargest() throws ChangeDispenserEmptyException {
		while (!amounts.isEmpty()) {
			int coin = amounts.lastKey();
			if (amounts.get(coin) == 0)
				amounts.remove(coin);
			else {
				removeCoins(coin, 1);
				return coin;
			}
		}
		throw new ChangeDispenserEmptyException();
	}
	
}
