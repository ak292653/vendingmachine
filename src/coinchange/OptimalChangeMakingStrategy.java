package coinchange;

import java.util.ArrayList;
import java.util.Map;

public class OptimalChangeMakingStrategy implements ChangeMakingStrategy {
	
	private ArrayList<Map.Entry<Integer, Integer> > coins;
	private int change;
	private int[][] opt;
	
	private static int INFINITY = 1000000000;
	
	private void computeDPArray() throws ChangeImpossibleException {
		if (coins.size() == 0)
			throw new ChangeImpossibleException();
		
		// dynamic programming
		int[][] dp = new int[coins.size()][change + 1];
		// array for reconstructing optimal change
		opt = new int[coins.size()][change + 1];
		
		for (int i = 0; i < coins.size(); ++i) {
			for (int value = 0; value <= change; ++value) {
				dp[i][value] = INFINITY;
				for (int howMany = 0; howMany <= coins.get(i).getValue(); ++howMany) {
					int sum = coins.get(i).getKey() * howMany;
					if (sum > value)
						break;
					if (i == 0) {
						if (sum == value && howMany < dp[i][value]) {
							dp[i][value] = howMany;
							opt[i][value] = howMany;
						}
					}
					else {
						if (howMany + dp[i - 1][value - sum] < dp[i][value]) {
							dp[i][value] = howMany + dp[i - 1][value - sum];
							opt[i][value] = howMany;
						}
					}
				}
			}
		}
		if (dp[coins.size() - 1][change] >= INFINITY)
			throw new ChangeImpossibleException();
		
	}
	
	private CoinSet reconstruct() throws ChangeImpossibleException {
		
		CoinSet result = new CoinSet();
		for (int i = coins.size() - 1, current = change; i >= 0; --i) {
			result.addCoins(coins.get(i).getKey(), opt[i][current]);
			current -= coins.get(i).getKey() * opt[i][current];
		}
		return result;
	}
	
	@Override
	public CoinSet makeChange(CoinSet coins, int change)
			throws ChangeImpossibleException {
		this.coins = new ArrayList<Map.Entry<Integer, Integer> >(coins.reversedCoinList());
		this.change = change;
		computeDPArray();
		return reconstruct();
	}

}
