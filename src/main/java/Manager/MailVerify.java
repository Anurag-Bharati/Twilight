package Manager;

import com.sun.mail.smtp.SMTPAddressFailedException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h2>Mailing System</h2>
 * <p>This class is responsible of generating authentication code and sending it to provided gmail</p>
 * @author Anurag-Bharati
 * @since 2021
 * @version 1.0
 */

public class MailVerify {
    public static int OTP;

    /**
     * <h2>Main Method</h2>
     * <p>This method is responsible for setting up and sending the message</p>
     * @param name is the name of user provided in the TextField while registering
     * @param recepient is gmail account name of that user provided in the TextField while registering
     * @throws Exception when there is no internet connection while registering
     */
    public static void sendMail(String name, String recepient) throws Exception{

        OTP = (int) (Math.random()*90000)+10000;


        System.out.println("Now sending the mail to "+recepient);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        String myAccountEmail = PersonalDataField.MASTER_GMAIL;
        String myAccountPassword = PersonalDataField.MASTER_PASS;

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,myAccountPassword);
            }
        });

        Message message = prepareMsg(session, myAccountEmail, recepient, OTP, name);

        assert message != null;
        Transport.send(message);
        System.out.println("A message has been Sent and the code is " + OTP);

    }

    /**
     * <h2>Message Maker</h2>
     * <p>This method prepare message that is to be sent to the requested user's gmail</p>
     * @param session is the mailing details necessary to send a mail from
     * @param myAccountEmail is the sender's gmail
     * @param recepient is the receiver's gmail
     * @param OTP ain't exactly an otp but more of an 5-digit authentication code
     * @param name of recepient
     * @return Message which consist of Subject and Body. Inside body there is an AuthCode
     */
    private static Message prepareMsg(Session session, String myAccountEmail, String recepient, int OTP, String name){

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
            message.setSubject("Verification Code | Twilight - ByAnurag");
            message.setText("Hello "+name+ "!\n" +OTP+ " is your verification code.\n" + "Thanks for using our " +
                    "service.\n-Anurag");
            return message;

        } catch (SMTPAddressFailedException e){
            e.printStackTrace();
        } catch (MessagingException e) {
            Logger.getLogger(MailVerify.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}