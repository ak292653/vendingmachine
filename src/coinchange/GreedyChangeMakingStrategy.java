package coinchange;

import java.util.Map;

public class GreedyChangeMakingStrategy implements ChangeMakingStrategy {

	@Override
	public CoinSet makeChange(CoinSet coins, int amount) throws ChangeImpossibleException {
		// TODO Auto-generated method stub
		CoinSet change = new CoinSet();
		for (Map.Entry<Integer, Integer> e : coins.reversedCoinList()) {
			if (e.getKey() <= amount) {
				int take = Math.min(e.getValue(), amount / e.getKey());
				change.addCoins(e.getKey(), take);
				amount -= take * e.getKey();
			}
		}
		if (amount == 0)
			return change;
		else
			throw new ChangeImpossibleException();
	}

}
