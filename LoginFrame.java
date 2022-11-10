
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
public class LoginFrame extends JFrame implements ActionListener  {
	
	
	Connection connection = null;
    Container container = getContentPane();
    JLabel userLabel = new JLabel("USERNAME");
   
    JLabel passwordLabel = new JLabel("PASSWORD");
    JLabel validlabel = new JLabel("wrong password try again !!!!!!!");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton signupButton = new JButton("SIGNUP");
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("Show Password");
    
    
    
    public void CloseFrame() throws SQLException{
        super.dispose();
        super.setVisible(false);
        connection.close();
    }
    public static Boolean isvisible() {
    	return LoginFrame.isvisible();
    	
    	
    	
    }
    
    LoginFrame() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        
    	try {
    		
    		Class.forName("org.sqlite.JDBC");
    		connection = DriverManager.getConnection("jdbc:sqlite:./gamedata.db");
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	} catch (SQLException e1) {
    		e1.printStackTrace();
    	}
    	
 
    }
 
    public void setLayoutManager() {
        container.setLayout(null);
        container.setBackground(Color.BLACK);
        userLabel.setForeground(Color.white);
        passwordLabel.setForeground(Color.white);
        validlabel.setForeground(Color.RED);
        validlabel.setBackground(Color.white);

    }
 
    public void setLocationAndSize() {
        userLabel.setBounds(170, 150, 100, 30);
        passwordLabel.setBounds(170, 220, 100, 30);
        userTextField.setBounds(270, 150, 300, 50);
        passwordField.setBounds(270, 220, 300, 50);
        showPassword.setBounds(500, 300, 120, 30);
        loginButton.setBounds(200, 300, 100, 30);
        signupButton.setBounds(350, 350, 100, 30);
        resetButton.setBounds(350, 300, 100, 30);
        validlabel.setBounds(300, 400, 200, 30);
 
 
    }
 
    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
        container.add(signupButton);
        container.add(validlabel);
    }
 
    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
        signupButton.addActionListener(this);
        validlabel.setVisible(false);
    }
 
 
    @SuppressWarnings("deprecation")
	@Override
    public void actionPerformed (ActionEvent e) {
        //Coding Part of LOGIN button
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = passwordField.getText();
            
            
          try {
    	  String auth = "SELECT (count(*) > 0) as found FROM user WHERE  username = ? AND password = ?";
    	  PreparedStatement authstat = connection.prepareStatement(auth);
			authstat.setString(1, userText);
			authstat.setString(2, pwdText);
	          ResultSet check = authstat.executeQuery();
	          
	          if (check.next()) {
	                boolean found = check.getBoolean(1); 
	                
	                if (found) {
	                	Game.user= userText;
	                    CloseFrame();
	                    
	                  

	                } else {
	                	validlabel.setVisible(true);
	     

	          
	                }
	          
	          }
		} catch (SQLException e1) {e1.printStackTrace();}
          
          
           
    }
        //Coding Part of RESET button
        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }
        
        if (e.getSource() == signupButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = passwordField.getText();
//            JOptionPane.showMessageDialog(this, "signup Successful");
            
          try {
        	  
  	      String insertuser = "INSERT INTO user(username,password,score) VALUES(?,?,?)";
  	      PreparedStatement insertuserstat = connection.prepareStatement(insertuser);
  	      insertuserstat.setString(1, userText);
  	      insertuserstat.setString(2, pwdText);
  	      insertuserstat.executeUpdate();
		} catch (Exception e2) {
				System.err.println(e2);
		}

       
        }
        
        
        
       //Coding Part of showPassword JCheckBox
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
 
 
        }
    }
 
}
 
