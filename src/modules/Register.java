package modules;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JPanel implements ActionListener {
	JLabel l1=new JLabel("Choose your username : ");
	JTextField userTF=new JTextField();
	JLabel l2=new JLabel("Choose your password : ");
	JPasswordField passTF=new JPasswordField();
	JLabel l3=new JLabel("Confirm your password : ");
	JPasswordField passC=new JPasswordField();
	JButton register=new JButton("Register");
	JButton back=new JButton("Go Back");
            Register(){
            	JPanel loginP=new JPanel();
            	loginP.setLayout(new GridLayout(4,2));
            	loginP.add(l1);
            	loginP.add(userTF);
            	loginP.add(l2);
            	loginP.add(passTF);
            	loginP.add(l3);
            	loginP.add(passC);
            	loginP.add(register);
            	loginP.add(back);
            	register.addActionListener(this);
            	back.addActionListener(this);            	
            	add(loginP);
            	
            	
            }
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==register && passTF.getPassword().length>0 && userTF.getText().length()>0) {
					String pass=new String(passTF.getPassword());
					String confirm=new String(passC.getPassword());
					if(pass.equals(confirm)) {
						try {
							BufferedReader br=new BufferedReader(new FileReader("password.txt"));
							String line=br.readLine();
							while(line!=null) {
								StringTokenizer st=new StringTokenizer(line);
								if(userTF.getText().contentEquals(st.nextToken())) {
									System.out.println("User already exists");
								    return;
								}
								line=br.readLine();
							}
							br.close();
								MessageDigest md=MessageDigest.getInstance("SHA-256");
								md.update(pass.getBytes());
								byte byteData[]=md.digest();
								StringBuffer sb=new StringBuffer();
								for(int i=0;i<byteData.length;i++) {
									sb.append(Integer.toString((byteData[i] & 0xFF)+0x100,16).substring(1));
								}
								BufferedWriter bw=new BufferedWriter(new FileWriter("password.txt",true));
								bw.write(userTF.getText()+" "+sb.toString()+"\n");
								bw.close();
								Login login=(Login) getParent();
								login.c1.show(login,"login");
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
				}
				if(e.getSource()==back) {
					Login login=(Login) getParent();
					login.c1.show(login,"login");
					
				}
			}
}
