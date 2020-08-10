package br.com.casuaiscontas.service.event.user;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.casuaiscontas.mail.MailService;

@Component
public class SaveUserListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SaveUserListener.class);
	
	@Autowired
	private MailService mailService; 
	
	@Async
	@EventListener
	public void saveUser(SaveUserEvent event) {
		try {
			mailService.send(event.getUser()); 
		} catch (MessagingException e) {
			LOGGER.error("Erro ao tentar enviar e-mail", e);
		}
	}

}
