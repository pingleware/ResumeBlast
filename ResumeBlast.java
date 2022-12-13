import java.util.Properties;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class ResumeBlast {
	public static void main(String[] args) {
		String smtpHost = "smtp.com"; // a paid service which forwards email to a recipient
		String to; // the recipient, read from email.txt file
		String from = "<your email address>"; // your email being tracked by smtp.com
		String fileName = "<your resume filename>"; // file name to be attached, current directory

		Properties props = new Properties();
		props.put("mail.smtp.host",smtpHost);
		props.put("mail.smtp.port","2525");  // ISP blocks port 25, so use the alternate port

		try {
	  		FileInputStream fi = new FileInputStream("email.txt"); // file which holds emails of recruiters
	  		BufferedReader br = new BufferedReader(new InputStreamReader(fi));

			while ((to = br.readLine()) != null) {
				Session session = Session.getDefaultInstance(props,null);
				MimeMessage message = new MimeMessage(session);
				message.setSubject("<A subject like, A Position on the Internet");
				message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText("A short note about rate and availability");

				BodyPart fileBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(fileName);
				fileBodyPart.setDataHandler(new DataHandler(source));
				File file = new File(fileName);
				fileBodyPart.setFileName(file.getName());

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				multipart.addBodyPart(fileBodyPart);
				message.setContent(multipart);

				Transport.send(message);
			}
  	  		br.close();
  	  		fi.close();
		} catch (MessagingException me) {
			System.err.println(me);
		} catch (IllegalStateException ise) {
			System.err.println(ise);
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}