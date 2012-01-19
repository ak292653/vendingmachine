package coinchange;

import static org.junit.Assert.*;

import org.junit.Test;

public class OptimalChangeMakingStrategyShould extends
		ChangeMakingStrategyShould {
	
	public OptimalChangeMakingStrategyShould() {
		strategy = new OptimalChangeMakingStrategy();
	}
	
	@Test
	public void workWhenGreedyFails() throws Exception {
		CoinSet cs = coinSetFromArray(new int[]{2, 2, 3});
		assertArrayEquals(new int[]{2, 2}, strategy.makeChange(cs, 4).toSortedArray());
	}
	
	@Test
	public void returnMinimumChangeWhenGreedyFailsToDoThat() throws Exception {
		CoinSet cs = coinSetFromArray(new int[]{1, 1, 3, 3, 4, 4});
		assertArrayEquals(new int[]{3, 3}, strategy.makeChange(cs, 6).toSortedArray());
	}
	
	@Test
	public void workAsWellAsGreedyWithPolishCoins() throws Exception {
		CoinSet cs = coinSetFromArray(new int[]{10, 10, 10, 20, 20, 20, 50, 50, 50});
		cs.addCoins(1, 100);
		assertEquals(strategy.makeChange(cs, 80).getTotalCoins(),
				(new GreedyChangeMakingStrategy()).makeChange(cs, 80).getTotalCoins());
	}
	
	@Test (expected = ChangeImpossibleException.class)
	public void throwIfItsReallyImpossibleToMakeChange() throws Exception {
		CoinSet cs = coinSetFromArray(new int[]{6, 8, 6, 9, 3, 2, 3, 6, 100});
		strategy.makeChange(cs, 7);
	}
	
}
