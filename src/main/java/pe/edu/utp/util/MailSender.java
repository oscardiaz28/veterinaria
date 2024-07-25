package pe.edu.utp.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class MailSender {
    private final static String username = "diazvargasod@gmail.com";
    private final static String appPassword = "lukl jnll phxj boav";

    public static void sendMail(String message, String destinatario){

        try {
            SimpleEmail mail = new SimpleEmail();
            mail.setHostName("smtp.gmail.com");
            mail.setSmtpPort(587);
            mail.setAuthenticator(new DefaultAuthenticator(username, appPassword ));
            mail.setSSLOnConnect(true);
            mail.setFrom(username);
            mail.setSubject("Equipo de Soporte");
            mail.setMsg(message);
            mail.addTo(destinatario);
            mail.send();

        } catch (EmailException e) {
            throw new RuntimeException(e);
        }

    }

}
