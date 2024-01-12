package com.example.workflow;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
public class EmailNotification {
	
	
    private final TemplateEngine templateEngine;
    private String templateName = "email-template";
    
 
	    private static final String HOST = "smtp.gmail.com";
	    private static final String USER = "camunda2024@gmail.com";
	    private static final String PWD = "andw yqta utgm wtcg";
	    private final static Logger LOGGER = Logger.getLogger(EmailNotification.class.getName());
	    
	    @Autowired
	    public EmailNotification(TemplateEngine templateEngine) {
	        this.templateEngine = templateEngine;
	    }

		/*
		 * @Override public void notify(DelegateExecution delegateTask) {
		 * 
		 * // String assignee = delegateTask.getAssignee(); String taskId =
		 * delegateTask.getId();
		 * 
		 * if (true) { // IdentityService identityService =
		 * Context.getProcessEngineConfiguration().getIdentityService(); // User user =
		 * identityService.createUserQuery().userId(assignee).singleResult();
		 * 
		 * if (true) { String recipient = "dwivedivivekanand92@gmail.com"; // Email
		 * email = new SimpleEmail(); // email.setCharset("utf-8"); //
		 * email.setHostName(HOST); // email.setSmtpPort(587); //
		 * email.setSSLCheckServerIdentity(true); // email.setStartTLSEnabled(false); //
		 * email.setAuthentication(USER, PWD);
		 * 
		 * Properties props = new Properties();
		 * props.setProperty("mail.transport.protocol", "smtp");
		 * props.setProperty("mail.host", "smtp.gmail.com"); props.put("mail.smtp.auth",
		 * "true"); props.put("mail.smtp.port", "465"); props.put("mail.debug", "true");
		 * props.put("mail.smtp.socketFactory.port", "465");
		 * props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		 * props.put("mail.smtp.socketFactory.fallback", "false"); Session session =
		 * Session.getDefaultInstance(props, new javax.mail.Authenticator() { protected
		 * PasswordAuthentication getPasswordAuthentication() { return new
		 * PasswordAuthentication(USER, PWD); } });
		 * 
		 * 
		 * try { // email.setFrom("noreply@camunda.org"); //
		 * email.setSubject("Task assigned: " + delegateTask.getName()); // email.
		 * setMsg("Please complete: http://localhost:8085/camunda/app/tasklist/default/#/task="
		 * + taskId); // email.addTo(recipient); // email.send();
		 * 
		 * Transport transport = session.getTransport(); InternetAddress addressFrom =
		 * new InternetAddress(USER);
		 * 
		 * MimeMessage message = new MimeMessage(session);
		 * message.setSender(addressFrom); message.setSubject("Task assigned: " ); //
		 * message.
		 * setContent("Please complete: http://localhost:8085/camunda/app/tasklist/default/#/task="
		 * + taskId, ,); message.setContent("Hello","text/plain");
		 * message.addRecipient(Message.RecipientType.TO, new
		 * InternetAddress(recipient));
		 * 
		 * transport.connect(); Transport.send(message); transport.close();
		 * 
		 * LOGGER.info("Task Assignment Email successfully sent to user '" +
		 * "' with address '" + recipient + "'.");
		 * 
		 * } catch (Exception e) { LOGGER.log(Level.WARNING,
		 * "Could not send email to assignee", e); } } else {
		 * LOGGER.info("Not sending email to user " + "', user has no email address.");
		 * 
		 * } } else { LOGGER.info("Not sending email to user " +
		 * "', user is not enrolled with identity service."); } }
		 */

		//@Override
	//	public void execute(DelegateExecution execution) throws Exception {
			
			// String assignee = delegateTask.getAssignee();
	      //  String taskId = delegateTask.getId();
	    public void execute(String status){
	        if (true) {
	           // IdentityService identityService = Context.getProcessEngineConfiguration().getIdentityService();
	          //  User user = identityService.createUserQuery().userId(assignee).singleResult();

	            if (true) {
	                String recipient = "dwivedivivekanand92@gmail.com";
//	                Email email = new SimpleEmail();
//	                email.setCharset("utf-8");
//	                email.setHostName(HOST);
//	                email.setSmtpPort(587);
//	                email.setSSLCheckServerIdentity(true);
//	                email.setStartTLSEnabled(false);
//	                email.setAuthentication(USER, PWD);
	                
	                Context context = new Context();
	                
	                context.setVariable("message", "Hi, The status has been " +status);
	                
	                Properties props = new Properties();
	                props.setProperty("mail.transport.protocol", "smtp");
	                props.setProperty("mail.host", "smtp.gmail.com");
	                props.put("mail.smtp.auth", "true");
	                props.put("mail.smtp.port", "465");
	                props.put("mail.debug", "true");
	                props.put("mail.smtp.socketFactory.port", "465");
	                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	                props.put("mail.smtp.socketFactory.fallback", "false");
	                Session session = Session.getDefaultInstance(props,
	                        new javax.mail.Authenticator() {
	                            protected PasswordAuthentication getPasswordAuthentication() {
	                                return new PasswordAuthentication(USER, PWD);
	                            }
	                        });


	                try {
//	                    email.setFrom("noreply@camunda.org");
//	                    email.setSubject("Task assigned: " + delegateTask.getName());
//	                    email.setMsg("Please complete: http://localhost:8085/camunda/app/tasklist/default/#/task=" + taskId);
//	                    email.addTo(recipient);
//	                    email.send();

	                    Transport transport = session.getTransport();
	                    InternetAddress addressFrom = new InternetAddress(USER);

	                    MimeMessage message = new MimeMessage(session);
	                //    MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
	                    message.setSender(addressFrom);
	                    message.setSubject("Task assigned: " );
	                    String htmlContent = templateEngine.process(templateName, context);
			           // helper.setText(htmlContent, true);
	                  //  message.setContent("Please complete: http://localhost:8085/camunda/app/tasklist/default/#/task=" + taskId, ,);
	                   // message.setContent("Hello","text/plain");	
	                  //  message.setContent(htmlContent,"text/html");
	                    
	                    
	                    
	                    //Add code for attachement
	                    
	                    Multipart multipart = new MimeMultipart();
	                    MimeBodyPart textBodyPart = new MimeBodyPart(); 
	                  // textBodyPart.setContent(multipart);
	                    textBodyPart.setContent( htmlContent, "text/html; charset=utf-8" );
	                    MimeBodyPart attachmentBodyPart= new MimeBodyPart();
	                    DataSource source = new FileDataSource("D:\\abcd.pdf"); // ex : "C:\\test.pdf" 
	                   attachmentBodyPart.setDataHandler(new DataHandler(source)); 
	                   attachmentBodyPart.setFileName("abcd.pdf"); // ex : "test.pdf" 
	                   multipart.addBodyPart(textBodyPart); // add the text part 
	                   multipart.addBodyPart(attachmentBodyPart); // add the attachement part 
	                   message.setContent(multipart);
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

	                    transport.connect();
	                    Transport.send(message);
	                    transport.close();

	                    LOGGER.info("Task Assignment Email successfully sent to user '"  + "' with address '" + recipient + "'.");

	                } catch (Exception e) {
	                    LOGGER.log(Level.WARNING, "Could not send email to assignee", e);
	                }
	            } else {
	               LOGGER.info("Not sending email to user "  + "', user has no email address.");
	            	
	            }
	        } else {
	            LOGGER.info("Not sending email to user " + "', user is not enrolled with identity service.");
	        }
			// TODO Auto-generated method stub
			
		}
		
		 

		
}


