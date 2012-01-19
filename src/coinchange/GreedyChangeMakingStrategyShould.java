package coinchange;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class GreedyChangeMakingStrategyShould extends ChangeMakingStrategyShould {
	
	public GreedyChangeMakingStrategyShould() {
		strategy = new GreedyChangeMakingStrategy();
	}
	
	@Test
	public void returnSingleCoinSetWhenItIsPossible() throws Exception {
		CoinSet cs = new CoinSet();
		cs.addCoins(1, 5);
		cs.addCoin(5);
		CoinSet res = strategy.makeChange(cs, 5);
		assertEquals(1, res.getTotalCoins());
		assertEquals(1, res.getCount(5));
	}
	
	@Test (expected = ChangeImpossibleException.class)
	public void failSometimesEvenIfItsPossibleToMakeChange() throws Exception {
		CoinSet cs = new CoinSet();
		cs.addCoins(new int[]{2, 2, 3});
		strategy.makeChange(cs, 4);
	}
	
	@Test
	public void workForHighDenominations() throws Exception {
		CoinSet cs = new CoinSet();
		cs.addCoins(10000, 20000);
		cs.addCoins(1024, 100);
		cs.addCoins(100, 1000000);
		cs.addCoins(1, 100);
		CoinSet result = strategy.makeChange(cs, 11111111);
		assertEquals(1111, result.getCount(10000));
		assertEquals(1, result.getCount(1024));
		assertEquals(87, result.getCount(1));
	}
	
	@Test (timeout=10)
	public void workFastForHighDenominations() throws Exception {
		CoinSet cs = new CoinSet();
		cs.addCoins(3, 1000000000);
		assertEquals(1000000002, strategy.makeChange(cs, 1000000002).getTotalMoney());
	}
	
}
