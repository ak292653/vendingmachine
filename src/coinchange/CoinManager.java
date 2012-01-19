package coinchange;

public class CoinManager {

	private int currentBalance;
	private CoinSet coins;
	private ChangeMakingStrategy strategy;
	private CoinSet change;
	
	public CoinManager(ChangeMakingStrategy strategy) {
		this.strategy = strategy;
		currentBalance = 0;
		coins = new CoinSet();
		change = new CoinSet();
	}
	
	public void addCoin(int coinValue) {
		coins.addCoin(coinValue);
		currentBalance += coinValue;
	}
	
	public void addSpareCoins(int[] spare) {
		for (int i = 0; i < spare.length; ++i) {
			coins.addCoin(spare[i]);
		}
	}
	
	public void purchase(int value) throws Exception {
		if (currentBalance < value)
			throw new TooLittleFundsException();
		int delta = currentBalance - value;
		try {
			change = strategy.makeChange(coins, delta);
			subtractAndReset();
		}
		catch (ChangeImpossibleException e) {
			change = strategy.makeChange(coins, currentBalance);
			subtractAndReset();
			throw e;
		}
		
	}
	
	public int getCurrentBalance() {
		return currentBalance;
	}
	
	private void subtractAndReset() {
		coins.subtract(change);
		currentBalance = 0;
	}
	
	public int nextChangeCoin() throws ChangeDispenserEmptyException {
		return change.dispenseLargest();
	}
}
