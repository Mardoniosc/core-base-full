package com.mardonio.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.mardonio.domain.Usuario;

public interface EmailService {

	void sendOrderConfirmationEmail(Usuario obj);

	void sendEmail(SimpleMailMessage msg);

	void sendOrderConfirmationHtmlEmail(Usuario obj);

	//Enviar email com arquivo HTML
	void sendHtmlEmail(MimeMessage msg);
	
	void sendNewPasswordEmail(Usuario usuario, String newPass);

}