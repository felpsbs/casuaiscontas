package br.com.casuaiscontas.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@PropertySource(value = { "file://${HOME}/.casuaiscontas-mail.properties" }, ignoreResourceNotFound = true)
public class MailConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(env.getProperty("mail.host"));
		mailSender.setPort(587);
		mailSender.setUsername(env.getProperty("mail.username"));
		mailSender.setPassword(env.getProperty("mail.password"));

		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.debug", false);
		props.put("mail.smtp.connectiontimeout", 10000);

		mailSender.setJavaMailProperties(props);
		
		return mailSender;
	}
	
}
