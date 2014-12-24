//Created by Athul Santhosh & Anjith Sasindran (Group 6)

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;



public class MailClient {
	Message[] messages,sentMessages ;
	Folder emailFolder,emailFolderSent;
	JTabbedPane tabbedPane;
	Table inboxTable, sentMail;
	JFrame loginScreen;
	JScrollPane bar,barInbox,barSend, inboxPrintScroll, sentPrintScroll;
	JFrame emailInboxFrame;
	JTextField emailid,to,subject;
	JTextArea body, inboxPrint, sentPrint;
	JPasswordField password;
	JButton login,compose,sendMail,send,moreSend,moreInbox;
	Dimension loginScreenSize, msgDialogSize;
	JPanel entireLogin,inboxPanel,inboxpane,sendmailpane,composepane;
	BorderLayout mainLayout;
	TextPrompt logins, passwords,todup,subjectdup,bodydup;
	String email, passwordString, columnName[] = {"Sender", "Mail"}, temp[] = new String[2];
	DefaultTableModel tableManager, sentTableManager;
	int n=1,p=1;
	
	public static void main(String args[])
	{
		MailClient obj = new MailClient();
		obj.build();
	}
	public void build()
	{
		mainLayout = new BorderLayout(40, 40);
		
		loginScreenSize = new Dimension(300,400);
		msgDialogSize = new Dimension(800,500);
		
		loginScreen = new JFrame("MailClient");
		loginScreen.setSize(loginScreenSize);
		loginScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginScreen.setResizable(false);
		loginScreen.setLocationRelativeTo(null);
		loginScreen.setLayout(mainLayout);
		
		entireLogin = new JPanel();
		entireLogin.setBorder(new EmptyBorder(100,40,100,40));
		entireLogin.setLayout(new GridLayout(3,1));
		
		emailid = new JTextField();
		emailid.setFont(new Font("serif", Font.PLAIN, 18));
		
		password = new JPasswordField();
		password.setFont(new Font("serif", Font.PLAIN, 18));
		login = new JButton("LOGIN");
		
		logins = new TextPrompt("[Enter Username]", emailid);
		passwords = new TextPrompt("[Enter Password]", password);
		
		entireLogin.add(emailid);
		entireLogin.add(password);
		entireLogin.add(login);
		
		loginScreen.add(entireLogin, BorderLayout.CENTER);
		loginScreen.setVisible(true);
		
		
		login.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e)
			{
							
			      Properties properties;
			      properties = System.getProperties();
				  properties.setProperty("mail.store.protocol", "imaps");
			      
				  Session emailSession = Session.getDefaultInstance(properties, null);
			      email = emailid.getText();
			      passwordString = new String(password.getPassword());
			      try
			      {
			    	  Store store = emailSession.getStore("imaps");

			    	  store.connect("imap.gmail.com", emailid.getText(), password.getText());
			    	  loginScreen.dispose();
			    	  
			    	  emailInboxFrame = new JFrame("Inbox "+ email);
			    	  emailInboxFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			    	  emailInboxFrame.setMinimumSize(loginScreenSize);
			    	  emailInboxFrame.setVisible(true);
			    	  emailInboxFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                      tabbedPane=new JTabbedPane();
                      
                      composepane=new JPanel();
                      sendmailpane=new JPanel();
                      inboxpane=new JPanel();
                      
                      moreInbox=new JButton("MORE");
                      inboxpane.setLayout(new BorderLayout());
                      inboxpane.add(moreInbox,BorderLayout.SOUTH);
                      tableManager = new DefaultTableModel(columnName, 0);
                      
                      inboxTable = new Table();
                      inboxTable.setRowHeight(20);
                      inboxTable.setFont(new Font("serif", Font.PLAIN, 15));
                      barInbox=new JScrollPane(inboxTable);
                      inboxTable.setModel(tableManager);
                      
                      inboxTable.setFillsViewportHeight(true);
                      emailFolder = store.getFolder("INBOX");
                      emailFolder.open(Folder.READ_ONLY);
                                                                 
                      messages = emailFolder.getMessages(n,5);

                      for (int i = 0; i < 5; i++) {
                         Message message = messages[i];
                         temp[0] = message.getFrom()[0].toString();
                         temp[1]=message.getSubject();
                         tableManager.addRow(temp);
                      }
                      moreSend=new JButton("MORE");
                      sendmailpane.setLayout(new BorderLayout());
                      sendmailpane.add(moreSend,BorderLayout.SOUTH);
                      sentTableManager = new DefaultTableModel(columnName, 0);
                      
                      sentTableManager = new DefaultTableModel(columnName, 0);
                      sentMail = new Table();
                      
                      sentMail.setRowHeight(20);
                      sentMail.setFont(new Font("serif", Font.PLAIN, 15));
                      
                      barSend=new JScrollPane(sentMail);
                      sentMail.setModel(sentTableManager);
                      
                      sentMail.setFillsViewportHeight(true);
                      emailFolderSent = store.getFolder("[Gmail]/Sent Mail");
                      emailFolderSent.open(Folder.READ_ONLY);
                      
                      
                      
                      sentMessages = emailFolderSent.getMessages(p,5);

                      for (int i = 0; i < 5; i++) {
                         Message message = sentMessages[i];
                         temp[0] = message.getFrom()[0].toString();
                         temp[1]=message.getSubject();
                         sentTableManager.addRow(temp);
                      }
                      
                      
                      sentMail.setFillsViewportHeight(true);
                      sentMail.setModel(sentTableManager);                
                      
                      composepane.setLayout(new BorderLayout());
					  
                      tabbedPane.addTab("INBOX",inboxpane);
                      tabbedPane.addTab("SENTMAIL",sendmailpane);
                      tabbedPane.addTab("COMPOSE",composepane);
                      
                      to=new JTextField();
                      todup=new TextPrompt("[TO]",to);
                      
                      subject=new JTextField();
                      subjectdup=new TextPrompt("[SUBJECT]",subject);
                      
                      body=new JTextArea(40,10);
                      bodydup=new TextPrompt("[BODY]",body);
                      
                      bar=new JScrollPane(body);
                      bar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                      bar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                      
                      send=new JButton("SEND");
                      BoxLayout boxLayout = new BoxLayout(composepane, BoxLayout.Y_AXIS);
                      composepane.setLayout(boxLayout);
                      composepane.add(to);
                      composepane.add(subject);
                      composepane.add(bar);
                      composepane.add(send);
                      
                      send.addActionListener(new ActionListener() {
                    	  
                    	  public void actionPerformed(ActionEvent arg0) {
                    		  
                    		  
                    		  String host = "pop.gmail.com";
                    		  
                    		  Properties props = new Properties();
                    		  props.put("mail.smtp.auth", "true");
                    		  props.put("mail.smtp.starttls.enable", "true");
                    		  props.put("mail.smtp.host", host);
                    		  props.put("mail.smtp.port", "25");
                    		  
                    		  Session session = Session.getInstance(props,
                    				  new javax.mail.Authenticator() {
                    			  protected PasswordAuthentication getPasswordAuthentication() {
                    				  return new PasswordAuthentication(email, passwordString);
                    			  }
                    		  });
                    		  
                    		  try {
                    			  
                    			  Message message = new MimeMessage(session);
                    			  message.setFrom(new InternetAddress(email));
                    			  message.setRecipients(Message.RecipientType.TO,
                    					  InternetAddress.parse(to.getText()));
                    			  message.setSubject(subject.getText());
                    			  message.setText(body.getText());
                    			  
                    			  Transport.send(message);
                    			  to.setText("");
                    			  subject.setText("");
                    			  body.setText("");
                    			  
                    		  } catch (MessagingException e) {
                    			  throw new RuntimeException(e);
                    		  }
                    		  
                    	  }
                      });
                    
                      inboxpane.add(barInbox,BorderLayout.CENTER);
                      sendmailpane.add(barSend,BorderLayout.CENTER);
                      emailInboxFrame.add(tabbedPane);
                      
                      inboxTable.addKeyListener(new KeyListener() {
						
						public void keyTyped(KeyEvent arg0) {
							// TODO Auto-generated method stub
						}
						
						public void keyReleased(KeyEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						public void keyPressed(KeyEvent arg0) {
							// TODO Auto-generated method stub

							if (arg0.getKeyCode()==KeyEvent.VK_SPACE)
							{
								String s = (String)inboxTable.getValueAt(inboxTable.getSelectedRow(), 1);
								JDialog msgDialog = new JDialog(emailInboxFrame, s);
								msgDialog.setVisible(true);
								msgDialog.setLocationRelativeTo(null);
								msgDialog.setSize(msgDialogSize);
								
								inboxPrint = new JTextArea();
								inboxPrint.setEditable(false);
								inboxPrintScroll = new JScrollPane(inboxPrint);
								
								try
								{
									Message msg = emailFolder.getMessage(inboxTable.getSelectedRow()+1);
									Multipart mp = (Multipart) msg.getContent();
							        BodyPart bp = mp.getBodyPart(0);
									inboxPrint.setText((String)bp.getContent());
								}
								catch(Exception e){
									e.printStackTrace();
								}
								msgDialog.add(inboxPrintScroll);
							}
							
						}
					});
                      
                      sentMail.addKeyListener(new KeyListener() {
						
						public void keyTyped(KeyEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						public void keyReleased(KeyEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
						public void keyPressed(KeyEvent arg0) {
							// TODO Auto-generated method stub
							if (arg0.getKeyCode()==KeyEvent.VK_SPACE)
							{
								String s = (String)inboxTable.getValueAt(inboxTable.getSelectedRow(), 1);
								JDialog msgDialog = new JDialog(emailInboxFrame, s);
								msgDialog.setVisible(true);
								msgDialog.setLocationRelativeTo(null);
								msgDialog.setSize(msgDialogSize);
								
								sentPrint = new JTextArea();
								sentPrint.setEditable(false);
								sentPrintScroll = new JScrollPane(sentPrint);
								
								try
								{
									Message msg = emailFolderSent.getMessage(sentMail.getSelectedRow()+1);
									Multipart mp = (Multipart) msg.getContent();
							        BodyPart bp = mp.getBodyPart(0);
									sentPrint.setText((String)bp.getContent());
								}
								catch(Exception e){
									e.printStackTrace();
								}
								msgDialog.add(sentPrintScroll);
							}
							
						}
					});
                      
                      moreInbox.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent arg0) {
							n=n+5;
							try
							{
							Message[] messages = emailFolder.getMessages(n,n+4);

		                      for (int i = 0; i < 5; i++) {
		                         Message message = messages[i];
		                         temp[0] = message.getFrom()[0].toString();
		                         temp[1]=message.getSubject();
		                         tableManager.addRow(temp);
		                      }
							}
					     catch(Exception e)
					     {}	
						}
					});
                      moreSend.addActionListener(new ActionListener() {
  						
  						public void actionPerformed(ActionEvent arg0) {
  							p=p+5;
  							try
  							{
  							Message[] sentMessages = emailFolderSent.getMessages(p,p+4);

  		                      for (int i = 0; i < 5; i++) {
  		                         Message message = sentMessages[i];
  		                         temp[0] = message.getFrom()[0].toString();
  		                         temp[1]=message.getSubject();
  		                         sentTableManager.addRow(temp);
  		                      }
  		                      
  							}
  					     catch(Exception e)
  					     {}	
  						}
  					});
			      }
			      catch(AuthenticationFailedException e1)
			      {
			    	  JOptionPane.showMessageDialog(loginScreen, "Invalid Username and Password");
			    	  e1.printStackTrace();
			      }
			      catch(Exception e2)
			      {
			    	 
			      }
			}
		});
	}
	
}
