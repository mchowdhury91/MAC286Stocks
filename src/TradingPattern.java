
public abstract class TradingPattern {

	DataArray dataArray;
	TradeArray tradeArray;
	float stopLoss;
	float target;
	
	public abstract TradeArray match();
	
	public void setStopLoss(float stopLoss){
		this.stopLoss = stopLoss;
	}
	
	public void setTarget(float target){
		this.target = target;
	}
	
	public void setDataArray(DataArray dataArray){
		this.dataArray = dataArray;
	}
	
	public float getStopLoss(){ return stopLoss; }
	public float getTarget(){ return target; }
	public DataArray getDataArray(){ return dataArray; }
	public TradeArray getTradeArray(){ return tradeArray; }	
	
}
