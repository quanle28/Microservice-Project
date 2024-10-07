package com.quanle28.employeesservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thoughtworks.xstream.XStream;

@Configuration
public class AxonConfig {

	@Bean
	public XStream xStream() {
		XStream xStream = new XStream();

		xStream.allowTypesByWildcard(new String[] { "com.aql.**", "com.quanle28.**" });
		return xStream;
	}
}
