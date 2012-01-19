package coinchange;


import org.junit.Test;
import static org.junit.Assert.*;

public class CoinSetShould {
	
	@Test (expected = IllegalArgumentException.class)
	public void notAllowNegativeDenominations() throws Exception {
		(new CoinSet()).addCoin(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void notAllowZeroDenomination() throws Exception {
		(new CoinSet()).addCoin(0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void notAllowNegativeCoinCountAtInsertion() throws Exception {
		(new CoinSet()).addCoins(583, -56);
	}
	
	@Test
	public void containCorrectAmountOf1sWhenFirstInserted() throws Exception {
		CoinSet cs = new CoinSet();
		cs.addCoins(1, 56);
		assertEquals(56, cs.getCount(1));
	}
	
	@Test
	public void initiallyContainZero1s() throws Exception {
		assertEquals(0, (new CoinSet()).getCount(1));
	}
	
	@Test
	public void returnCorrectSumOfInsertedCoins() throws Exception {
		CoinSet cs = new CoinSet();
		cs.addCoins(1, 22);
		cs.addCoins(2, 0);
		cs.addCoin(7);
		assertEquals(cs.getTotalCoins(), 23);		
	}
	
	@Test
	public void returnCorrectTotalCash() throws Exception {
		CoinSet cs = new CoinSet();
		cs.addCoins(30, 50);
		cs.addCoins(1, 1000);
		assertEquals(cs.getTotalMoney(), 2500);
	}
	
	@Test
	public void returnCorrectArrayRepresentation() throws Exception {
		CoinSet cs = new CoinSet();
		cs.addCoins(2, 3);
		cs.addCoins(5, 2);
		cs.addCoins(new int[]{7, 100, 1});
		assertArrayEquals(new int[]{1, 2, 2, 2, 5, 5, 7, 100}, cs.toSortedArray());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void throwWhenSubtractingGreaterFromSmaller() throws Exception {
		CoinSet cs = new CoinSet(), sub = new CoinSet();
		cs.addCoins(5, 7);
		sub.addCoins(5, 8);
		cs.subtract(sub);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void throwWhenSubstractingFromNotPresentCoinValue() throws Exception {
		CoinSet sub = new CoinSet();
		sub.addCoins(1, 1);
		(new CoinSet()).subtract(sub);
	}
	
	@Test
	public void deleteSingleLargestCoin() throws Exception {
		CoinSet cs = new CoinSet();
		cs.addCoin(100);
		assertEquals(100, cs.dispenseLargest());
		assertEquals(0, cs.getTotalMoney());
	}

	@Test (expected = ChangeDispenserEmptyException.class)
	public void throwWhenDeletingFromEmptySet() throws Exception {
		CoinSet cs = new CoinSet();
		cs.addCoin(1);
		cs.dispenseLargest();
		cs.dispenseLargest();
	}
}
