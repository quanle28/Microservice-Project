package com.aql.notificationservice;

import org.apache.tomcat.util.net.WriteBuffer.Sink;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;

@SpringBootApplication
public class NotificationserviceApplication {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(NotificationserviceApplication.class);

	@Bean
	public Consumer<String> aql() {
		return message -> System.out.println("Message: " + message);

	}

	public static void main(String[] args) {
		SpringApplication.run(NotificationserviceApplication.class, args);
	}

}
