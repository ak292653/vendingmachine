package coinchange;

import org.junit.Test;
import static org.junit.Assert.*;

public class CoinManagerShould {

	private int collectChange(CoinManager cm) {
		int sum = 0;
		try {
			while (true)
				sum += cm.nextChangeCoin();
		}
		catch (ChangeDispenserEmptyException e) {}
		return sum;
	}
	
	@Test
	public void giveBackNoChangeWhenTheValueWasExact() throws Exception {
		CoinManager cm = new CoinManager(new GreedyChangeMakingStrategy());
		cm.addCoin(10);
		cm.addCoin(5);
		cm.purchase(15);
		assertEquals(0, collectChange(cm));
	}
	
	@Test (expected = TooLittleFundsException.class)
	public void throwWhenTryingToPurchaseWithoutEnoughFunds() throws Exception {
		CoinManager cm = new CoinManager(new GreedyChangeMakingStrategy());
		cm.purchase(99);
		cm.purchase(100);
	}
	
	@Test
	public void notGiveBackMoneyWhenNotEnoughFunds() throws Exception {
		CoinManager cm = new CoinManager(new GreedyChangeMakingStrategy());
		cm.addCoin(100);
		try {
			cm.purchase(200);
		}
		catch(TooLittleFundsException e) {
			assertEquals(0, collectChange(cm));
		}
	}
	
	@Test
	public void throwAndGiveBackMoneyWhenUnableToMakeChange() throws Exception {
		CoinManager cm = new CoinManager(new OptimalChangeMakingStrategy());
		cm.addCoin(100);
		try {
			cm.purchase(50);
			assertFalse(true);
		}
		catch (ChangeImpossibleException e) {
			assertEquals(100, collectChange(cm));
		}
	}
	
	@Test
	public void makeChangeWhenItsPossibleAndDoItOptimally() throws Exception {
		CoinManager cm = new CoinManager(new OptimalChangeMakingStrategy());
		cm.addSpareCoins(new int[]{1, 1, 3, 3});
		cm.addCoin(4);
		cm.addCoin(4);
		cm.purchase(2);
		assertEquals(3, cm.nextChangeCoin());
		assertEquals(3, cm.nextChangeCoin());
		assertEquals(0, collectChange(cm));
	}
	
	@Test
	public void displayCorrectBalanceAfterInsertingEachOf2Coins() throws Exception {
		CoinManager cm = new CoinManager(new GreedyChangeMakingStrategy());
		cm.addCoin(4);
		assertEquals(4, cm.getCurrentBalance());
		cm.addCoin(7);
		assertEquals(11, cm.getCurrentBalance());
	}
}
