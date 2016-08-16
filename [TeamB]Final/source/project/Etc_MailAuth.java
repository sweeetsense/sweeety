package project;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Etc_MailAuth extends Authenticator {
   private String fromEmail, password;

   public Etc_MailAuth(String fromEmail, String password) { // 인증처리용
      this.fromEmail = fromEmail;
      this.password = password;
   }

   @Override
   protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(fromEmail, password);
   }
}