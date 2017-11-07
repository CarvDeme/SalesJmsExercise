package simplesolution;

public class AdjustmentOperation implements SaleMessage {

	String messageTypes;
	Sale sale;
	String operation;

	public AdjustmentOperation() {
	}

	public AdjustmentOperation(Sale sale, String operation) {
		super();
		this.sale = sale;
		this.operation = operation;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Override
	public String getSaleMessageType() {
		return this.messageTypes;
	}

	@Override
	public void setSaleMessageType(String m) {
		this.messageTypes = m;

	}
}
