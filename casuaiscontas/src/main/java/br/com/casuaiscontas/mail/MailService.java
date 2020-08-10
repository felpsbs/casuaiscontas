package br.com.casuaiscontas.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import br.com.casuaiscontas.model.User;

@Service
@PropertySource(value = { "file://${HOME}/.casuaiscontas-mail.properties" }, ignoreResourceNotFound = true)
public class MailService {

	@Autowired
	private Environment env;

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private SpringTemplateEngine template;
	
	public void send(User user) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
		
		Context context = new Context();
		context.setVariable("link", "http://localhost:8080/usuarios/cadastro/confirmacao?id=" + user.getId());		
		context.setVariable("logo", "logo");
		
		String html = template.process("mail/Confirmation.html", context);
		
		helper.setFrom(env.getProperty("mail.email"));
		helper.setTo(user.getEmail());
		helper.setSubject("Confirmação de cadastro CasuaisContas");
		helper.setText(html, true);
		
		helper.addInline("logo", new ClassPathResource("static/img/logo.png"));
		
		mailSender.send(message);
	}
}
