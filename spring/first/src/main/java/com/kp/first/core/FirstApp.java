package com.kp.first.core;

import com.kp.first.bean.GlobalBeanRepository;
import com.kp.first.model.Customer;
import com.kp.first.model.Order;
import com.kp.first.model.ProductPurchase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootApplication @EnableMongoRepositories(basePackages = "com.kp.first")
@ComponentScan(basePackages = "com.kp.first") public class FirstApp
    extends SpringBootServletInitializer {

    static ApplicationContext ctx;
    static GlobalBeanRepository globalBeanRepository;

    @Override protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FirstApp.class);
    }

    public static void main(String[] args) {
        System.out.println("Starting FirstApp");

        ctx = SpringApplication.run(FirstApp.class, args);

        //printBeans();

        globalBeanRepository = (GlobalBeanRepository) ctx.getBean("globalBeanRepository");

        //testNewOrder();

        testGetOrders();
    }

    public static void testNewOrder() {
        Order order = new Order();

        Customer customer = globalBeanRepository.customerRepository.findByUsername("kolgepratik");

        Collection<ProductPurchase> ppList = new ArrayList<>();
        ProductPurchase pp = globalBeanRepository.beanMaker._newProductPurchase();
        pp.setDetails(globalBeanRepository.productRepository.findByName("Mobile"), 2);
        ProductPurchase pp2 = globalBeanRepository.beanMaker._newProductPurchase();
        pp2.setDetails(globalBeanRepository.productRepository.findByName("Tablet"), 5);
        ppList.add(pp);
        ppList.add(pp2);

        order.setDetails(ppList, customer);

        System.out.println("order: " + order);

        globalBeanRepository.orderRepository.save(order);
    }

    public static void testGetOrders() {
        List<Order> orderList = globalBeanRepository.orderRepository.findAll();

        for (Order o : orderList) {
            System.out.println("Order: " + o);
        }
    }

    public static void printBeans() {
        System.out.println("---------- Beans ------------");
        for (String bean : ctx.getBeanDefinitionNames())
            System.out.println(bean);

        System.out.println("---------- Beans ------------");
    }
}

