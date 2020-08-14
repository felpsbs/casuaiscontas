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
public class PasswordResetListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetListener.class);
	
	@Autowired
	private MailService mailService; 
	
	@Async
	@EventListener
	public void recoverPassword(PasswordResetEvent event) {
		try {
			mailService.send(event); 
		} catch (MessagingException e) {
			LOGGER.error("Erro ao tentar enviar e-mail", e);
		}
	}

}
