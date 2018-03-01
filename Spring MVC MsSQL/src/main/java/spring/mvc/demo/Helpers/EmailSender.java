package spring.mvc.demo.Helpers;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private boolean Sender(String htmlMsg, String to) {
        //Gmail
        final String username = "ACCOUNT"+"@gmail.com";
        final String password = "PASSWORD";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ACCOUNT@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Mail Activation");


            message.setContent(htmlMsg, "text/html; charset=utf-8");
            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            return false;
        }
    }

    public Boolean ActivationSender(String token, String to) throws UnsupportedEncodingException {
        String htmlMsg = "Dear User;"
                + "<br>"
                + "<a href='" + "http://localhost:8080/Register/Activation?token=" + java.net.URLEncoder.encode(token, "UTF-8") + "'>"
                + "For Activation"
                + "</a>";
        return Sender(htmlMsg, to);
    }

    public Boolean PasswordSender(String token, String to) throws UnsupportedEncodingException {
        String htmlMsg = "Dear User;"
                + "<br>"
                + "<a href='" + "http://localhost:8080/Register/ChangeForgotPassword?token=" + java.net.URLEncoder.encode(token, "UTF-8") + "'>"
                + "For Password Reset"
                + "</a>";
        return Sender(htmlMsg, to);
    }
}
