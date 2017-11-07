package simplesolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.stereotype.Component;

@Component
public class SalesReceiver {

	int i;

	List<Sale> salesDao = new ArrayList<Sale>();
	List<AdjustmentOperation> logAdjustments = new ArrayList<AdjustmentOperation>();

	@Autowired
	JmsListenerEndpointRegistry jmsRegistry;

	@Autowired
	ConfigurableApplicationContext context;

	@JmsListener(destination = "salequeue", containerFactory = "saleListenerFactory")
	public void receiveMessage(SaleMessage sm) {

		// You can assume the code will only ever be executed in a single threaded
		// environment
		i = i + 1;

		if (sm.getSaleMessageType() == MessageTypes.TYPE_1) {
			salesDao.add(((MultipleSales) sm).getSale());
		} else if (sm.getSaleMessageType() == MessageTypes.TYPE_2) {
			Collections.nCopies(((MultipleSales) sm).getOccurrences(), 1).stream()
					.forEach(i -> salesDao.add(((MultipleSales) sm).getSale()));
		} else if (sm.getSaleMessageType() == MessageTypes.TYPE_3) {
			logAdjustments.add(((AdjustmentOperation) sm));
			if (((AdjustmentOperation) sm).operation == Operations.TYPE_SUBTRACT) {
				salesDao.stream()
						.filter(x -> ((AdjustmentOperation) sm).getSale().getProduct_type().equals(x.product_type))
						.forEach(y -> y.setSale_value(
								y.getSale_value() - ((AdjustmentOperation) sm).getSale().getSale_value()));

			} else if (((AdjustmentOperation) sm).operation == Operations.TYPE_ADD) {
				salesDao.stream()
						.filter(x -> ((AdjustmentOperation) sm).getSale().getProduct_type().equals(x.product_type))
						.forEach(y -> y.setSale_value(
								y.getSale_value() + ((AdjustmentOperation) sm).getSale().getSale_value()));

			} else if (((AdjustmentOperation) sm).operation == Operations.TYPE_MULTIPLY) {
				salesDao.stream()
						.filter(x -> ((AdjustmentOperation) sm).getSale().getProduct_type().equals(x.product_type))
						.forEach(y -> y.setSale_value(
								y.getSale_value() * ((AdjustmentOperation) sm).getSale().getSale_value()));

			}
		}

		// Output format to be plain text, printed out to the console.
		if (i % 50 == 0) {
			jmsRegistry.stop();
			System.out.println(
					"Report of the adjustments that have been made to each sale type while the application was running:");
			logAdjustments.forEach(System.out::println);
			jmsRegistry.start();
			i = 0;
			logAdjustments.clear();
		}

		else if (i % 10 == 0) {
			System.out.println("Report detailing the number of sales of each product and their total:");
			List<Sale> amount_by_product = salesDao.stream()
					.collect(Collectors.groupingBy(sale -> sale.getProduct_type())).entrySet().stream()
					.map(e -> e.getValue().stream().reduce(
							(s1, s2) -> new Sale(s1.getProduct_type(), s1.getSale_value() + s2.getSale_value())))
					.map(s -> s.get()).collect(Collectors.toList());
			System.out.println(amount_by_product);
		}

	}

}
