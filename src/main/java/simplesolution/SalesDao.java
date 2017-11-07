package simplesolution;

import java.util.List;

//All data to be in memory
//No database or UI is required
public class SalesDao {
	List<Sale> sales;

	public SalesDao() {
		
	}

	public SalesDao(List<Sale> sales) {
		super();
		this.sales = sales;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	@Override
	public String toString() {
		return "SalesDao [sales=" + sales + "]";
	}
	
}
