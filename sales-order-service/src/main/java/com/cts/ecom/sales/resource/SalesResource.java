package com.cts.ecom.sales.resource;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.ecom.sales.model.Customer_SOS;
import com.cts.ecom.sales.model.Item;
import com.cts.ecom.sales.model.ItemList;
import com.cts.ecom.sales.model.Order_line_item;
import com.cts.ecom.sales.model.SalesRequest;
import com.cts.ecom.sales.model.Sales_order;
import com.cts.ecom.sales.repo.CustomerSosRepository;
import com.cts.ecom.sales.repo.OrderLineItemRepository;
import com.cts.ecom.sales.repo.SalesOrderRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class SalesResource {
	@Autowired
	CustomerSosRepository customerSosRepository;

	@Autowired
	SalesOrderRepository salesOrderRepository;

	@Autowired
	OrderLineItemRepository orderLineItemRepository;

	private RestTemplate restTemplate;
	private LoadBalancerClient loadBalancer;

	@Autowired
	public SalesResource(LoadBalancerClient loadBalancer) {
		//this.discoveryClient = discoveryClient;
		this.loadBalancer = loadBalancer;
		this.restTemplate = new RestTemplate();
	}

	@PostMapping("/sales")
	@HystrixCommand(fallbackMethod = "getDataFallBack")
	public String createItem(@RequestBody SalesRequest req) throws RestClientException, IOException {
		Sales_order sales_order=null;
		System.out.println("sales service call happened.");
		Customer_SOS customer = customerSosRepository.findOne(req.getCustomer_id());
		if(customer!=null) {
			List<String> itemNames=req.getItemNames();
			System.out.println("before call loadbalancer");
			ServiceInstance instance = loadBalancer.choose("475947-item-service");
			String baseUrl=instance.getUri().toString();
			System.out.println("baseUrl "+baseUrl);
			ItemList itemList =restTemplate.getForObject( baseUrl+"/itemList", ItemList.class);
			Long totalprice=0l;
			int itemQuantity=1;
			String itemsDesc="";
			String AllItemNames="";

			for (String itemName : itemNames) {

				for (Item item : itemList.getItems()) {
					if(item.getName().equals(itemName)) {
						totalprice=totalprice+item.getPrice();
						++itemQuantity;
						itemsDesc.concat(itemQuantity+" Item "+item.getDescription()+" ");
						AllItemNames.concat(itemQuantity+"->"+item.getName()+"");
					}
				}
			}
			if(itemQuantity>0) {
				sales_order = new Sales_order();
				sales_order.setCust_id(req.getCustomer_id());
				sales_order.setOrder_date(req.getOrder_date());
				sales_order.setOrder_desc(itemsDesc);
				sales_order.setId((long)Math.random());

				System.out.println(sales_order.toString());
				salesOrderRepository.save(sales_order);

				Order_line_item orderLineItem = new Order_line_item();
				orderLineItem.setItem_name(AllItemNames);
				orderLineItem.setItem_quantity(itemQuantity);
				orderLineItem.setOrder_id(sales_order.getId());
				String response="sucessfully ordered.  "+"\n"+"order Line Item details:"+
						orderLineItem.toString()+
						"sales order details:"+sales_order.toString();
				return response;}

			else {
				return "Item is not available. So your order is declined Please do again";}

		}

		return "Cutomer Id is not available. so Order did not happen.";
	}
	public String getDataFallBack(SalesRequest salesReq) {

		/*ItemList itemList = new ItemList();
		Item item = new Item();
		item.setId(0l);
		item.setName("fallBackNm");
		item.setDescription("fallback description");
		item.setPrice(0l);
		itemList.getItems().add(item);*/
		return "service is down. Please try after some time. thank you for your patiences";

	}
	@GetMapping("/customersos/{id}")
	public Customer_SOS retrieveStudent(@PathVariable long id) {
		return customerSosRepository.findOne(id);
	}

	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

	public static String getCurrentTimeUsingDate() {
		Date date = new Date();
		String strDateFormat = "hh:mm:ss a";
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		String formattedDate= dateFormat.format(date);
		return formattedDate;
	}

}
