MailClient
===========
MailClient is a GUI based email client written entirely in java for desktop computers. Currently it supports only Gmail. It uses JavaMail API for connecting, retrieving and sending mails. 

Login Screen

![Login Screen](/screenshots/login.png)

Login screen's JTextField uses TextPrompt class to put placeholder text.

Inbox Screen 

![Inbox Screen](/screenshots/inboxui.png)

Inbox screen uses JTabbedPane with three different tabs
- Inbox
- Sentmail
- Compose

Inbox and Sentmail uses JTable with two columns, one showing sender's email and other showing email subject. Clicking More retrieves 5 more emails. 

Compose

![Compose Screen](/screenshots/composeui.png)

Has separate field for _to_ address, _subject_, _body_. Clicking on Send button sends the mail

