package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public final class  SMTPMail {
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
	public static final String  HOST    = "smtp.gmail.com";
	public static final String  FROM    = "pennfitness@gmail.com";
	

//	public static final void  main ( String [ ]  args )
//	  throws AddressException, MessagingException
//	//////////////////////////////////////////////////////////////////////
//	{
//	  String  host    = ( args.length < 1 ? TEST_HOST    : args [ 0 ] );
//	  String  from    = ( args.length < 2 ? TEST_FROM    : args [ 1 ] );
//	  String  to      = ( args.length < 3 ? TEST_TO      : args [ 2 ] );
//	  String  subject = ( args.length < 4 ? TEST_SUBJECT : args [ 3 ] );
//	  String  message = ( args.length < 5 ? TEST_MESSAGE : args [ 4 ] );
//	
//	  send ( host, from, to, subject, message );
//	}
	
	public static void  send (
	  List<String>  email_to,
	  String  subject,
	  String  message )
	  throws AddressException, MessagingException
	//////////////////////////////////////////////////////////////////////
	{
	  send (
	    ( Authenticator ) null,
	    ( InternetAddress [ ] ) null, //to
	    ( InternetAddress [ ] ) null, //cc
	    email_to, //bcc
	    subject,
	    message );
	}
		
	public static void  send (
	  Authenticator        authenticator,
	  InternetAddress [ ]  addresses_to,
	  InternetAddress [ ]  addresses_cc,
	  List<String> bccList,
	  String               subject,
	  String               message )
	  throws MessagingException
	//////////////////////////////////////////////////////////////////////
	{
	  Properties  properties = new Properties ( );
	  properties.put("mail.transport.protocol", "smtp");
	  properties.put("mail.smtp.starttls.enable","true");
	  properties.put ( "mail.smtp.host", HOST );
	  properties.put("mail.smtp.auth", "true");
	
	  Authenticator auth = new SMTPAuthenticator();
	  Session mailSession = Session.getDefaultInstance(properties, auth);
	  
	  Transport transport = mailSession.getTransport();
	  
	  InternetAddress [] addresses_bcc = new InternetAddress[ bccList.size() ];
	  Iterator<String> iterator = bccList.iterator();
	  
	  int count = 0;
	  while( iterator.hasNext() ){
		  addresses_bcc[ count++ ] = new InternetAddress( iterator.next() );
	  }
	  
	  MimeMessage  msg = new MimeMessage ( mailSession );
	
	  msg.addFrom ( new InternetAddress [ ] { new InternetAddress ( FROM ) } );
	  msg.setRecipients ( Message.RecipientType.BCC, addresses_bcc );
	  msg.setSubject ( subject );
	  msg.setText ( message );
	  
	  transport.connect();
	  transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.BCC));
	  
	  transport.close();
  
	}
	
	private  SMTPMail ( ) {
		
	}
	
	public static void main( String[]args ){
		List<String> list = new ArrayList<String>();
		list.add("kerem@seas.upenn.edu");
		list.add("kerembasol@gmail.com");
		try {
			send(list, "multiple_test", "test message");
		} catch (AddressException e) {
			System.out.println("SMTPMail.main() : Invalid address" );
			e.printStackTrace();
		} catch (MessagingException e) {
			System.out.println("SMTPMail.main() : Error sending email");
			e.printStackTrace();
		}
	}
	
}

class SMTPAuthenticator extends Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
       String username = "pennfitness@gmail.com";
       String password = "appdev123";
       return new PasswordAuthentication(username, password);
    }
}


