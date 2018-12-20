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

@RestController
public class SalesResource {
	@Autowired
	CustomerSosRepository customerSosRepository;

	@Autowired
	SalesOrderRepository salesOrderRepository;

	@Autowired
	OrderLineItemRepository orderLineItemRepository;

	private Logger logger = LoggerFactory.getLogger(getClass());
	private EurekaClient discoveryClient1;
	private RestTemplate restTemplate;
	private LoadBalancerClient loadBalancer;

	@Autowired
	public SalesResource(LoadBalancerClient loadBalancer) {
		//this.discoveryClient1 = discoveryClient1;
		this.loadBalancer = loadBalancer;
		this.restTemplate = new RestTemplate();
	}

	@PostMapping("/sales")
	public String createItem(@RequestBody SalesRequest req) throws RestClientException, IOException {
		Sales_order sales_order=null;
		Customer_SOS customer = customerSosRepository.findOne(req.getCustomer_id());
		if(customer!=null) {

			//ResponseEntity<ItemList> res=null;
			
			/*@Autowired
			private LoadBalancerClient loadBalancer;
			
			public void getEmployee() throws RestClientException, IOException {
				
				ServiceInstance serviceInstance=loadBalancer.choose("employee-producer");
				
				System.out.println(serviceInstance.getUri());
				
				String baseUrl=serviceInstance.getUri().toString()*/;

			List<String> itemNames=req.getItemNames();
			/*res=restTemplate.exchange("http://localhost:8084/items",
					HttpMethod.GET, getHeaders(),ItemList.class);*/
			ServiceInstance instance = loadBalancer.choose("475947-item-service");
			//InstanceInfo instance = discoveryClient1.getNextServerFromEureka("475947-item-service", false);
			//List<ServiceInstance> instances=discoveryClient.getInstances("item-service");
			//ServiceInstance serviceInstance=instances.get(0);
			String baseUrl=instance.getUri().toString();

			ItemList itemList =restTemplate.getForObject( baseUrl+"/itemList", ItemList.class);
			Long totalprice=0l;
			int itemQuantity=0;
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

				return "sucessfully ordered";}

			else {
				return "Item is not available. So your order declined Please do again";}

		}

		return "Cutomer Id is not available. so Order did not happen.";
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
