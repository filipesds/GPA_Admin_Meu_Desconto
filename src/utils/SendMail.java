package utils;

import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

 public class SendMail { 
	private static final Logger logger = LogManager.getLogger();
	
    public static boolean execute(String subject, String corpoEmail, List<String> anexos) throws Exception {	
    	String[] to={"automacaocwi.meudescontogpa@gmail.com"}; //senha: gpaMDautomacao2018
    	String[] cc={"filipe.silva@cwi.com.br"};
        String[] bcc={};

        return SendMail.sendMail("automacaocwi.meudescontogpa@gmail.com","gpaMDautomacao2018","smtp.gmail.com","465","true","true", false,"javax.net.ssl.SSLSocketFactory",
        "false",to, cc, bcc, subject, corpoEmail,anexos);
    }

    public static boolean sendMail(String userName,String passWord, String host, String port,String starttls, String auth,
           boolean debug,String socketFactoryClass, String fallback, String[] to,String[] cc, String[] bcc, String subject,String corpoEmail,
           List<String> anexos){
	    Properties props = new Properties();
	    props.put("mail.smtp.user", userName);
	    props.put("mail.smtp.host", host);
	    if (!"".equals(port)) {
	    	props.put("mail.smtp.port", port);
	    }
	    if (!"".equals(starttls)) {
	        props.put("mail.smtp.starttls.enable",starttls);
	        props.put("mail.smtp.auth", auth);
	    }
	    if (debug) {
	    	props.put("mail.smtp.debug", "true");
	    } 
	    else {
	    	props.put("mail.smtp.debug", "false");
	    }
	    if (!"".equals(port)) {
	        props.put("mail.smtp.socketFactory.port", port);
	    }
	    if (!"".equals(socketFactoryClass)) {
	        props.put("mail.smtp.socketFactory.class",socketFactoryClass);
	    }
	    if (!"".equals(fallback)) {
	        props.put("mail.smtp.socketFactory.fallback", fallback);
	    }
	    try{
	        Session session = Session.getDefaultInstance(props, null);
	        session.setDebug(debug);
	        
	        MimeMessage msg = new MimeMessage(session);
	        msg.setSubject(subject);
	        Multipart multipart = new MimeMultipart();
	
	        MimeBodyPart imgPart = new MimeBodyPart();
	        DataSource ds = new FileDataSource("mailheader/mailheader.png");
	        imgPart.setDataHandler(new DataHandler(ds));
	        imgPart.setHeader("Content-ID","<imageheader>");
	        imgPart.setDisposition(MimeBodyPart.INLINE);
	        multipart.addBodyPart(imgPart);
	        
	        MimeBodyPart conteudoDeMensagem = new MimeBodyPart();
	        conteudoDeMensagem.setContent(corpoEmail, "text/html");
	        multipart.addBodyPart(conteudoDeMensagem);
	        
	        MimeBodyPart imgPartLegenda = new MimeBodyPart();
	        DataSource dsLegenda = new FileDataSource("mailheader/legenda.png");
	        imgPartLegenda.setDataHandler(new DataHandler(dsLegenda));
	        imgPartLegenda.setHeader("Content-ID","<legenda>");
	        imgPartLegenda.setDisposition(MimeBodyPart.INLINE);
	        multipart.addBodyPart(imgPartLegenda);
	        
	        MimeBodyPart imgPartFooter = new MimeBodyPart();
	        DataSource dsFooter = new FileDataSource("mailheader/footer.png");
	        imgPartFooter.setDataHandler(new DataHandler(dsFooter));
	        imgPartFooter.setHeader("Content-ID","<footer>");
	        imgPartFooter.setDisposition(MimeBodyPart.INLINE);
	        multipart.addBodyPart(imgPartFooter);
	        
	        //add anexos
        	for(String attachmentName : anexos){
        		MimeBodyPart messageBodyPart = new MimeBodyPart();
        		DataSource source = new FileDataSource(attachmentName);
        		messageBodyPart.setDataHandler(new DataHandler(source));
        		messageBodyPart.setFileName(source.getName());
        		multipart.addBodyPart(messageBodyPart);
        	}
	        msg.setContent(multipart);
	        msg.setFrom(new InternetAddress(userName));
	        for(int i=0;i<to.length;i++){
	            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
	        }
	        for(int i=0;i<cc.length;i++){
	            msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));
	        }
	        for(int i=0;i<bcc.length;i++){
	            msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));
	        }
	        msg.saveChanges();
	        Transport transport = session.getTransport("smtp");
	        transport.connect(host, userName, passWord);
	        transport.sendMessage(msg, msg.getAllRecipients());
	        transport.close();
	
	        return true;
	        
	    } 
	    catch (Exception mex){
	        mex.printStackTrace();
	        logger.error("Erro ao enviar email.");
	       
	        return false;
	    }
	}
}