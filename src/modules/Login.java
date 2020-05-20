package modules;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel implements ActionListener {
     JLabel l1=new JLabel("User Name : ");
     JTextField userTF=new JTextField();
     JLabel l2=new JLabel("Password : ");
     JPasswordField passTF=new JPasswordField();
     JPanel loginP=new JPanel(new GridLayout(3,2));
     JButton login=new JButton("Login");
     JButton register=new JButton("Register");
     JPanel panel=new JPanel();
     CardLayout c1;
     
     
	Login(){
		setLayout(new CardLayout());
		loginP.add(l1);
		loginP.add(userTF);
		loginP.add(l2);
		loginP.add(passTF);
		
		login.addActionListener(this);
		register.addActionListener(this);
		loginP.add(login);
		loginP.add(register);
		panel.add(loginP);
		add(panel,"login");
		c1=(CardLayout) getLayout();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub	
		if(e.getSource()==login) {
			try {
				BufferedReader br=new BufferedReader(new FileReader("password.txt"));
				String pass=null;
				String line=br.readLine();
				while(line!=null) {
					StringTokenizer st=new StringTokenizer(line);
					if(userTF.getText().equals(st.nextToken()))
						pass=st.nextToken();
					line=br.readLine();
				}
				br.close();
				MessageDigest md=MessageDigest.getInstance("SHA-256");
				md.update(new String(passTF.getPassword()).getBytes());
				byte byteData[]=md.digest();
				StringBuffer sb=new StringBuffer();
				for(int i=0;i<byteData.length;i++) {
					sb.append(Integer.toString((byteData[i] & 0xFF)+0x100,16).substring(1));
				}
				if(pass.equals(sb.toString())) {
					//System.out.println("You have logged in successfully");
					add(new FileBrowser(userTF.getText()),"fb");
					c1.show(this, "fb");
			}
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(e.getSource()==register) {
		add(new Register(),"register");
		c1.show(this, "register");
		}
		
	}
	
	public static void main(String[] args) {
		JFrame frame=new JFrame("Text Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		Login login=new Login();
		frame.add(login);
		frame.setVisible(true);
	}

}
