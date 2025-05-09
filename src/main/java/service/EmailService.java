package service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;

public class EmailService {
    private static final SecureRandom random = new SecureRandom();
        private static final String EMAIL_SENDER = "votre-email@gmail.com";
        private static final String PASSWORD = "votre-mot-de-passe";

        public static void envoyerEmail(String destinataire, String sujet, String messageTexte) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_SENDER, PASSWORD);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_SENDER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(messageTexte);
            Transport.send(message);
            System.out.println("E-mail envoyé avec succès !");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static String genererCode() {
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}