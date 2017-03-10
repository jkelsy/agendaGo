/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package km.software.agendago;

import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author jkelsy
 */

@Stateless
public class EmailTareaService {
    private static final Logger logger = Logger.getLogger(EmailTareaService.class.getName());
   
    @Resource(mappedName="java:jboss/mail/Agenda")
    Session gmailSession;
    
    @Asynchronous
    public void sendEmail(String to, String from, String subject, String content) {     
        logger.info("Sending Email from " + from + " to " + to + " : " + subject);     
        try {
            Message message = new MimeMessage(gmailSession);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");
            //message.setText(content);
            Transport.send(message);

            logger.info("Email was sent");

        } catch (MessagingException e) {
            logger.info("Error while sending email : " + e.getMessage());
        }
    }
    
}
