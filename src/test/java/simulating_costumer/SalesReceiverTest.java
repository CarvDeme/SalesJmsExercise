package simulating_costumer;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import simplesolution.AdjustmentOperation;
import simplesolution.AppConfig;
import simplesolution.MessageTypes;
import simplesolution.MultipleSales;
import simplesolution.Operations;
import simplesolution.Sale;
import simplesolution.SaleMessage;

public class SalesReceiverTest {

	JmsTemplate jmsTemplate;
	ConfigurableApplicationContext context;
	private static JmsListenerEndpointRegistry jmsRegistry;

	@Before
	public void setUpBeforeClass() throws Exception {

		context = SpringApplication.run(AppConfig.class);

		jmsTemplate = context.getBean(JmsTemplate.class);
	}

	@Test
	public void sendType1() {

		SaleMessage s = new MultipleSales(new Sale("product code 001", 1L), 1);

		jmsTemplate.convertAndSend("salequeue", s);

	}

	@Test
	public void sendType2() {

		SaleMessage s = new MultipleSales(new Sale("product code 001", 1L), 2);

		s.setSaleMessageType(MessageTypes.TYPE_2);
		jmsTemplate.convertAndSend("salequeue", s);

	}

	@Test
	public void sendType3Add() {

		SaleMessage s = new AdjustmentOperation(new Sale("product code 001", 1L), Operations.TYPE_ADD);

		s.setSaleMessageType(MessageTypes.TYPE_3);

		jmsTemplate.convertAndSend("salequeue", s);

	}

	@Test
	public void sendType1x100() {

		IntStream.range(0, 100).parallel().forEach(i -> {
			jmsTemplate.convertAndSend("salequeue", new Sale("product code " + i, 10L));
		});

	}

	@AfterClass
	public static void ListenerShutdowner() {
		jmsRegistry.getListenerContainers().stream().map(e -> (DefaultMessageListenerContainer) e)
				.forEach(DefaultMessageListenerContainer::shutdown);
	}
}
