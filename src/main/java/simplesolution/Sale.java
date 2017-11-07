package simplesolution;

import java.util.List;

/**
 * A sale has a product type field and a value. Any number of different product
 * types can be expected. There is no fixed set.
 */
public class Sale{


	String product_type = MessageTypes.TYPE_1;
	Long sale_value;

	public Sale() {
	}

	public Sale(String product_type, Long sale_value) {
		this.product_type = product_type;
		this.sale_value = sale_value;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public Long getSale_value() {
		return sale_value;
	}

	public void setSale_value(Long sale_value) {
		this.sale_value = sale_value;
	}



}
