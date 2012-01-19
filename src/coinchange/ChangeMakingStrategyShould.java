package coinchange;

import org.junit.Test;
import static org.junit.Assert.*;

abstract public class ChangeMakingStrategyShould {
	
	protected ChangeMakingStrategy strategy;
	
	protected CoinSet coinSetFromArray(int[] array) {
		CoinSet result = new CoinSet();
		result.addCoins(array);
		return result;
	}
	
	@Test
	public void returnEmptyCoinSetWhenChangeIsZero() throws Exception {
		CoinSet cs = new CoinSet();
		cs.addCoins(5, 7);
		assertEquals(0, strategy.makeChange(cs, 0).getTotalCoins());
	}
	
	@Test
	public void returnCorrectTotalMoneyOrFail() throws Exception {
		CoinSet cs = coinSetFromArray(new int[]{3, 2, 2});
		try {
			assertEquals(5, strategy.makeChange(cs, 5).getTotalMoney());
		}
		catch (ChangeImpossibleException e) {
		}
	}
	
	@Test (expected = ChangeImpossibleException.class)
	public void throwWhenTheresTooLittleMoneyToMakeChange() throws Exception {
		CoinSet cs = new CoinSet();
		cs.addCoins(4, 24);
		cs.addCoin(3);
		strategy.makeChange(cs, 100);
	}
}	
