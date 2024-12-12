package com.crud.service;

import java.time.LocalDateTime;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.crud.config.AppProperties;
import com.crud.model.email.Email;
import com.crud.model.email.EmailStatus;
import com.crud.model.user.User;
import com.crud.repository.EmailRepository;
import com.crud.util.TokenService;

@Service
public class EmailService {
  private final EmailRepository emailRepository;
  private final TokenService tokenService;
  private final JavaMailSender javaMailSender;
  private final AppProperties appProperties; 

  public EmailService(EmailRepository emailRepository, 
      TokenService tokenService,
      JavaMailSender javaMailSender,
      AppProperties appProperties) {
    this.emailRepository = emailRepository;
    this.tokenService = tokenService;
    this.javaMailSender = javaMailSender;
    this.appProperties = appProperties;
  }

  public void create(User user){
    String token = tokenService.createToken(user.getId());
    Email email = new Email();
    SimpleMailMessage msg = new SimpleMailMessage();

    try{
      email.setSendDateEmail(LocalDateTime.now());
      email.setStatus(EmailStatus.PROCESSING);
      email.setToken(token);
      email.setUser(user);

      msg.setTo(user.getEmail());
      msg.setSubject("OAuth2LoginExampleCaio account confirmation");
      msg.setText(appProperties.getApp().getApiUrl() + "/verify?token=" + token);

    } catch(MailException exception){
      email.setStatus(EmailStatus.ERROR);
    } finally{
      emailRepository.save(email);
      javaMailSender.send(msg);
    }
    
  }
}
