package coinchange;

public interface ChangeMakingStrategy {
	
	public CoinSet makeChange(CoinSet coins, int change) throws ChangeImpossibleException;

}


