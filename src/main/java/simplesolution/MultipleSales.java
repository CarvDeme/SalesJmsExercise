package simplesolution;

public class MultipleSales implements SaleMessage {

	String messageTypes;
	Sale sale;
	int occurrences;

	public MultipleSales() {

	}

	public MultipleSales(Sale sale, int occurrences) {

		this.sale = sale;
		this.occurrences = occurrences;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public int getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
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
