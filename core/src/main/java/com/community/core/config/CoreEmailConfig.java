package com.community.core.config;

import java.util.Properties;

import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.email.service.message.MessageCreator;
import org.broadleafcommerce.common.email.service.message.NullMessageCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Shared email configuration
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
@Configuration
public class CoreEmailConfig {

    /**
     * A dummy mail sender has been set to send emails for testing purposes only
     * To view the emails sent use "DevNull SMTP" (download separately) with the following setting: 
     *   Port: 30000
     */
   @Bean
   public JavaMailSender blMailSender() {
       JavaMailSenderImpl sender = new JavaMailSenderImpl();
       sender.setHost("localhost");
       sender.setPort(30000);
       sender.setProtocol("smtp");
       Properties javaMailProps = new Properties();
       javaMailProps.setProperty("mail.smtp.starttls.enable", ""+true);
       javaMailProps.setProperty("mail.smtp.timeout", "25000");
       sender.setJavaMailProperties(javaMailProps);
       return sender;
   }
    
    /**
     * Uncomment this bean to send real emails
     */
//    @Bean
//    @Autowired
//    public MessageCreator blMessageCreator(@Qualifier("blEmailTemplateEngine") TemplateEngine tlTemplateEngine, @Qualifier("blMailSender") MailSender mailSender) {
//        return new ThymeleafMessageCreator(tlTemplateEngine, mailSender);
//    }
    
    @Bean
    @Autowired
    public MessageCreator blMessageCreator(@Qualifier("blMailSender") JavaMailSender mailSender) {
        return new NullMessageCreator(mailSender);
    }
    
    @Bean
    public EmailInfo blEmailInfo() {
        EmailInfo info = getEmailInfo();
        return info;
    }

    private EmailInfo getEmailInfo() {
        EmailInfo info = new EmailInfo();
        info.setFromAddress("crook.lilith@gmail.com");
        info.setSendAsyncPriority("2");
        info.setSendEmailReliableAsync("false");
        return info;
    }
    
    @Bean
    public EmailInfo blRegistrationEmailInfo() {
        EmailInfo info = getEmailInfo();
        info.setSubject("You have successfully registered!");
        info.setEmailTemplate("register-email");
        return info;
    }
    
    @Bean
    public EmailInfo blForgotPasswordEmailInfo() {
        EmailInfo info = getEmailInfo();
        info.setSubject("Reset password request");
        info.setEmailTemplate("resetPassword-email");
        return info;
    }
    
    @Bean
    public EmailInfo blOrderConfirmationEmailInfo() {
        EmailInfo info = getEmailInfo();
        info.setSubject("Your order with Mountain View Arms");
        info.setEmailTemplate("orderConfirmation-email");
        return info;
    }
    
    @Bean
    public EmailInfo blFulfillmentOrderTrackingEmailInfo() {
        EmailInfo info = getEmailInfo();
        info.setSubject("Your order with Mountain View Arms Has Shipped");
        info.setEmailTemplate("fulfillmentOrderTracking-email");
        return info;
    }
    
    @Bean
    public EmailInfo blReturnAuthorizationEmailInfo() {
        EmailInfo info = getEmailInfo();
        info.setSubject("Your return with Mountain View Arms");
        info.setEmailTemplate("returnAuthorization-email");
        return info;
    }
    
    @Bean
    public EmailInfo blReturnConfirmationEmailInfo() {
        EmailInfo info = getEmailInfo();
        info.setSubject("Your return with Mountain View Arms");
        info.setEmailTemplate("returnConfirmation-email");
        return info;
    }
}
