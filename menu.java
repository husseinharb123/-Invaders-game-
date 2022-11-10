
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import java.awt.Button;
import javax.swing.event.ChangeListener;
public class menu extends JFrame {
	 public void CloseFrame() throws SQLException{
	        super.dispose();
	        super.setVisible(false);
	        connection.close();
	    }

	private JPanel contentPane;
Connection connection = null;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */




	public menu() {
		
		
    	try {
    		
    		Class.forName("org.sqlite.JDBC");
    		connection = DriverManager.getConnection("jdbc:sqlite:./gamedata.db");
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	} catch (SQLException e1) {
    		e1.printStackTrace();
    	}
    	
    
        String updateenemy = "UPDATE Gamesetting SET shots = ?, enemy = ? , speed = ? ,timebtw = ? , enemies = ? WHERE id = ?";
        PreparedStatement updatescorestat;
		try {
			updatescorestat = connection.prepareStatement(updateenemy);
			updatescorestat.setLong(1, 2);
			updatescorestat.setLong(2, 0);
			updatescorestat.setLong(3, 0);
			updatescorestat.setLong(4, 0);
			updatescorestat.setLong(5, 1);
			updatescorestat.setLong(6, 123);
			updatescorestat.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
    	
    	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,800,600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("alien");
		rdbtnNewRadioButton.setBounds(230, 48, 111, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("husssein");
		rdbtnNewRadioButton_1.setBounds(369, 48, 111, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("islam(lion)");
		rdbtnNewRadioButton_2.setBounds(504, 48, 111, 23);
		contentPane.add(rdbtnNewRadioButton_2);
		
		ButtonGroup editableGroup = new ButtonGroup();	
		editableGroup.add(rdbtnNewRadioButton);
	    editableGroup.add(rdbtnNewRadioButton_1);
	    editableGroup.add(rdbtnNewRadioButton_2);

	    
	    rdbtnNewRadioButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	          String updateenemy = "UPDATE Gamesetting SET enemy = ? WHERE id = ?";
	          PreparedStatement updatescorestat;
			try {
				updatescorestat = connection.prepareStatement(updateenemy);
				updatescorestat.setLong(1, 0);
				updatescorestat.setLong(2, 123);
				updatescorestat.executeUpdate();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        }
	    });
  
	    rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	          String updateenemy = "UPDATE Gamesetting SET enemy = ? WHERE id = ?";
	          PreparedStatement updatescorestat;
			try {
				updatescorestat = connection.prepareStatement(updateenemy);
				updatescorestat.setLong(1, 1);
				updatescorestat.setLong(2, 123);
				updatescorestat.executeUpdate();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        }
	    });
	    rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	          String updateenemy = "UPDATE Gamesetting SET enemy = ? WHERE id = ?";
	          PreparedStatement updatescorestat;
			try {
				updatescorestat = connection.prepareStatement(updateenemy);
				updatescorestat.setLong(1, 2);
				updatescorestat.setLong(2, 123);
				updatescorestat.executeUpdate();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        }
	    });
	    

	    
		JLabel lblNewLabel = new JLabel("chose the enemy");
		lblNewLabel.setBounds(80, 52, 108, 14);
		contentPane.add(lblNewLabel);
		lblNewLabel.setForeground(Color.white);
		
		JLabel lblNewLabel_1 = new JLabel("speed of shot");
		lblNewLabel_1.setBounds(80, 150, 85, 14);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(Color.white);
		
		JLabel lblNewLabel_2 = new JLabel("time between enemies");
		lblNewLabel_2.setBounds(80, 175, 128, 14);
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setForeground(Color.white);
		

		

	

		
		
		JButton startgamebtn = new JButton("start the game");
		startgamebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.counter+=1;
				try {
					CloseFrame();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		startgamebtn.setBounds(274, 239, 184, 90);
		contentPane.add(startgamebtn);
		
		JLabel lblNewLabel_4 = new JLabel("number of enemies");
		lblNewLabel_4.setBounds(80, 122, 120, 14);
		contentPane.add(lblNewLabel_4);
		lblNewLabel_4.setForeground(Color.white);
		
		
		JSlider slidershotspeed = new JSlider();
		slidershotspeed.setBounds(230, 145, 200, 19);
		contentPane.add(slidershotspeed);
		
		slidershotspeed.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent ce) {
	        	
	  	          String updateenemy = "UPDATE Gamesetting SET speed = ? WHERE id = ?";
	  	          PreparedStatement updatescorestat;
	  			try {
	  				updatescorestat = connection.prepareStatement(updateenemy);
	  				updatescorestat.setLong(1, ((JSlider) ce.getSource()).getValue());
	  				updatescorestat.setLong(2, 123);
	  				updatescorestat.executeUpdate();
	  			} catch (SQLException e1) {
	  				e1.printStackTrace();
	  			}
	        	
	        	
	        }
	    });
		
		
		
		
		
		
		JSeparator separator = new JSeparator();
		separator.setBounds(207, 154, 1, 2);
		contentPane.add(separator);
		
		JSpinner spinnereneimies = new JSpinner();
		spinnereneimies.setBounds(230, 119, 30, 20);
		contentPane.add(spinnereneimies);
		
		spinnereneimies.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner spinner = (JSpinner) e.getSource();
  	          String updateenemy = "UPDATE Gamesetting SET enemies = ? WHERE id = ?";
  	          PreparedStatement updatescorestat;
  			try {
  				updatescorestat = connection.prepareStatement(updateenemy);
  				updatescorestat.setLong(1, (int)spinner.getValue());
  				updatescorestat.setLong(2, 123);
  				updatescorestat.executeUpdate();
  			} catch (SQLException e1) {
  				// TODO Auto-generated catch block
  				e1.printStackTrace();
  			}
                  
                
                
            }
        });
		
		
		
		
	
		JSlider slidertimebtw = new JSlider();
		slidertimebtw.setBounds(230, 170, 200, 19);
		contentPane.add(slidertimebtw);
		
		slidertimebtw.addChangeListener(new ChangeListener() {
		        @Override
		        public void stateChanged(ChangeEvent ce) {
		        	
		  	          String updateenemy = "UPDATE Gamesetting SET timebtw = ? WHERE id = ?";
		  	          PreparedStatement updatescorestat;
		  			try {
		  				updatescorestat = connection.prepareStatement(updateenemy);
		  				updatescorestat.setLong(1, ((JSlider) ce.getSource()).getValue());
		  				updatescorestat.setLong(2, 123);
		  				updatescorestat.executeUpdate();
		  			} catch (SQLException e1) {
		  				// TODO Auto-generated catch block
		  				e1.printStackTrace();
		  			}
		        	
		        	
		        }
		    });
	}
	

	
}
