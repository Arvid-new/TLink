import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;


@SuppressWarnings("serial")
public class TLinkFrame extends JFrame {

	private JPanel mainPanel;
	private JPanel headerPanel;
	private JPanel routePanel;
	private JPanel stopPanel;
	private JPanel customerPanel;
	private JPanel driverPanel;
	private JPanel operatorPanel;
	
	private JPanel routeMenu;
	private JTabbedPane tabPane;

	private JTable customerTable;
	private JTable driverTable;
	private JPanel customerMenu;
	private JPanel driverMenu;
	private JTable routeTable;
	private JTable stopTable;
	private JScrollPane customerScrollPane;
	private JScrollPane driverScrollPane;

	private JLabel title;
	private JLabel welcome;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

			/*
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}*/
		} catch (Exception e) {

		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TLinkFrame frame = new TLinkFrame("TLink Database App");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TLinkFrame(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		init();
	}

	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		headerPanel = new JPanel();
		headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		headerPanel.setLayout(new BorderLayout());
		title = new JLabel("TLink Database");	
		welcome = new JLabel("Welcome, Guest");
		headerPanel.add(title, BorderLayout.WEST);
		headerPanel.add(welcome, BorderLayout.EAST);

		routePanel = createRoutePanel();
		stopPanel = createStopPanel();
		customerPanel = createCustomerPanel();
		driverPanel = createDriverPanel();
		operatorPanel = createOperatorPanel();

		// Tabs pane
		tabPane = new JTabbedPane();
		tabPane.addTab("Routes", routePanel);
		tabPane.addTab("Stops", stopPanel);
		tabPane.addTab("Customer", customerPanel);
		tabPane.addTab("Driver", driverPanel);
		tabPane.addTab("Operator", operatorPanel);
		tabPane.setFocusable(false);

		mainPanel.add(headerPanel, BorderLayout.PAGE_START);
		mainPanel.add(tabPane, BorderLayout.CENTER);

		setContentPane(mainPanel);
	}

	private JPanel createRoutePanel() {		
		routeTable = new JTable();
		JScrollPane routeScrollPanel = new JScrollPane(routeTable);
		Route route = new Route();
		routeTable.setModel(route.displayRoutes());

		JButton routeSearchBtn = new JButton("Search");
		routeSearchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				String routeName = JOptionPane.showInputDialog(null, "Enter route name");
				if(routeName != null) {
					Route route = new Route();
					ResultTableModel search = route.searchRoutes(routeName);
					if (search.empty) {
						JOptionPane.showMessageDialog(null, "No routes found");
					}
					else {
						routeTable.removeAll();
						routeTable.setModel(search);
					}
				}
			}
		});

		JButton stopsBtn = new JButton("Stops");
		stopsBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				int rid = -1;
				try {
					rid = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter route ID"));
				} catch (NumberFormatException nfe) {
					//ignore (this gets thrown only if user hits cancel before entering anything)
				}
				Route route = new Route();
				ResultTableModel search = route.getAllStops(rid);
				if (search.empty) {
					JOptionPane.showMessageDialog(null, "No stops found");
				}
				else {
					routeTable.removeAll();
					routeTable.setModel(search);
				}
			}
		});
		
		JButton resetBtn = new JButton("Show All");
		resetBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Route route = new Route();
				routeTable.removeAll();
				routeTable.setModel(route.displayRoutes());
			}
		});

		routeMenu = new JPanel();
		routeMenu.setLayout(new GridLayout(1, 2));
		routeMenu.add(routeSearchBtn);
		routeMenu.add(stopsBtn);
		
		routePanel = new JPanel();
		routePanel.setLayout(new BorderLayout());
		routePanel.add(routeScrollPanel);
		routePanel.add(resetBtn, BorderLayout.SOUTH);
		routePanel.add(routeMenu, BorderLayout.NORTH);
		return routePanel;
	}

	private JPanel createStopPanel() {
		stopTable = new JTable();
		JScrollPane stopScrollPanel = new JScrollPane(stopTable);
		Stop stop = new Stop();
		stopTable.setModel(stop.displayStops());

		JButton stopSearchBtn = new JButton("Search");
		stopSearchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				String stopName = JOptionPane.showInputDialog(null, "Enter stop name");
				if (stopName != null) {
					Stop stop = new Stop();
					ResultTableModel search = stop.searchStops(stopName);
					if (search.empty) {
						JOptionPane.showMessageDialog(null, "No stops found");
					}
					else {
						stopTable.removeAll();
						stopTable.setModel(search);
					}
				}
			}
		});

		JButton routesBtn = new JButton("Find route(s)");
		routesBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				int sid = -1;
				try {
					sid = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter stop ID"));
				} catch (NumberFormatException nfe) {
					//ignore (this gets thrown only if user hits cancel before entering anything)
				}
				Stop stop = new Stop();
				ResultTableModel search = stop.findAllRoutes(sid);
				if (search.empty) {
					JOptionPane.showMessageDialog(null, "No routes found");
				}
				else {
					stopTable.removeAll();
					stopTable.setModel(search);
				}
			}
		});
		
		JButton resetBtn = new JButton("Show All");
		resetBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Stop stop = new Stop();
				stopTable.removeAll();
				stopTable.setModel(stop.displayStops());
			}
		});

		JPanel stopMenu = new JPanel();
		stopMenu.setLayout(new GridLayout(1, 2));
		stopMenu.add(stopSearchBtn);
		stopMenu.add(routesBtn);

		stopPanel = new JPanel();
		stopPanel.setLayout(new BorderLayout());
		stopPanel.add(stopScrollPanel);
		stopPanel.add(stopMenu, BorderLayout.NORTH);
		stopPanel.add(resetBtn, BorderLayout.SOUTH);
		return stopPanel;
	}

	private JPanel createCustomerPanel() {
		customerTable = new JTable();
		customerScrollPane = new JScrollPane(customerTable);
		
		JButton passIdBtn = new JButton("Pass Id");
		passIdBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				int cid = -1;
				try {
					cid = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Customer ID")); 
				} catch (NumberFormatException nfe) {
					//ignore (this gets thrown only if user hits cancel before entering anything)
				};
				Customer customer = new Customer();
				ResultTableModel passResults = customer.displayPassId(cid);
				if (passResults.empty) {
					JOptionPane.showMessageDialog(null, "No pass found for that Customer ID");
				}
				else {
					customerTable.removeAll();
					customerTable.setModel(passResults);
				}
			}
		});
		
		JButton displayBalanceBtn = new JButton("Get Balance");
		displayBalanceBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				String cidStr = JOptionPane.showInputDialog(null, "Enter Customer ID");
				int cid = -1;
				if (cidStr != null) {
					try {
						cid = Integer.parseInt(cidStr); 
						Customer customer = new Customer();
						ResultTableModel displayBalanceResults = customer.displayBalance(cid);
						if (displayBalanceResults.empty) {
							JOptionPane.showMessageDialog(null, "No result found for that Customer ID");
						}
						else {
							customerTable.removeAll();
							customerTable.setModel(displayBalanceResults);
						}						
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null, "Invalid input - please try again");
					};
				}
			}
		});
		
		JButton updateBalanceBtn = new JButton("Update Balance");
		updateBalanceBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel updatePanel = new JPanel();
				updatePanel.setLayout(new GridLayout(0, 1));
				JLabel cidLabel = new JLabel("Enter CustomerID:");
				JLabel amtLabel = new JLabel("Enter new amount to add");
				JTextField cidField = new JTextField();
				JTextField amtField = new JTextField();
				updatePanel.add(cidLabel);
				updatePanel.add(cidField);
				updatePanel.add(amtLabel);
				updatePanel.add(amtField);	
				
				String title = "Update Address";
				int option = JOptionPane.OK_CANCEL_OPTION;
				
				int input = JOptionPane.showConfirmDialog(null, updatePanel, title, option);
				if(input == JOptionPane.OK_OPTION) {				
					int cid = -1;
					int amtToAdd = 0;
					try {
						cid = Integer.parseInt(cidField.getText()); 
						amtToAdd = Integer.parseInt(amtField.getText());
						
						Customer customer = new Customer();
						customer.updateBalance(cid, amtToAdd);
						ResultTableModel updateBalanceResults = customer.displayBalance(cid);
						if (updateBalanceResults.empty) {
							JOptionPane.showMessageDialog(null, "CustomerID not found - please try again");
						}
						else {
							customerTable.removeAll();
							customerTable.setModel(updateBalanceResults);
							JOptionPane.showMessageDialog(null, "Update successful");
						}						
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null, "Invalid input - please try again");
					}
				}				
			}
		});
		
		JButton resetBtn = new JButton("Show All");
		resetBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				customerTable.removeAll();
			}
		});
		
		customerMenu = new JPanel();
		customerMenu.setLayout(new GridLayout(1, 2));
		customerMenu.add(passIdBtn);
		customerMenu.add(displayBalanceBtn);
		customerMenu.add(updateBalanceBtn);
		
		customerPanel = new JPanel();
		customerPanel.setLayout(new BorderLayout());
		customerPanel.add(customerScrollPane);
		customerPanel.add(resetBtn, BorderLayout.SOUTH);
		customerPanel.add(customerMenu, BorderLayout.NORTH);
		return customerPanel;
		
	}

	private JPanel createDriverPanel() {
		driverTable = new JTable();
		driverScrollPane = new JScrollPane(driverTable);
		JButton driverGetShiftsBtn = new JButton("Get Shifts");
		driverGetShiftsBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				int did = -1;
				try {
					did = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Driver ID")); 
				} catch (NumberFormatException nfe) {
					//ignore (this gets thrown only if user hits cancel before entering anything)
				};
				String dateStr = JOptionPane.showInputDialog(null, "Enter Date as: YYYY-MM-DD");
				Driver driver = new Driver();
				ResultTableModel getShiftsResults = driver.viewShifts(did, dateStr);
				if (getShiftsResults.empty) {
					JOptionPane.showMessageDialog(null, "No shifts found for given DriverID and date. "
							+ "Ensure date format is YYYY-MM-DD");
				}
				else {
					driverTable.removeAll();
					driverTable.setModel(getShiftsResults);
				}
			}
		});
		
		JButton driverViewAllShiftsBtn = new JButton("View All Shifts");
		driverViewAllShiftsBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				int did = -1;
				try {
					did = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Driver ID")); 
				} catch (NumberFormatException nfe) {
					//ignore (this gets thrown only if user hits cancel before entering anything)
				};
				Driver driver = new Driver();
				ResultTableModel viewAllShiftsResults = driver.viewAllShifts(did);
				if (viewAllShiftsResults.empty) {
					JOptionPane.showMessageDialog(null, "No shifts found for given DriverID");
				}
				else {
					driverTable.removeAll();
					driverTable.setModel(viewAllShiftsResults);
				}
			}
		});

		JButton driverUpdateInfoBtn = new JButton("Update Information");
		driverUpdateInfoBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel updatePanel = new JPanel();
				updatePanel.setLayout(new GridLayout(0, 1));
				JLabel didLabel = new JLabel("Enter DriverID:");
				JLabel phoneLabel = new JLabel("Enter new phone number - no spaces or dashes");
				JLabel addressLabel = new JLabel("Enter new address");
				JTextField didField = new JTextField();
				JTextField phoneField = new JTextField();
				JTextField addressField = new JTextField();
				updatePanel.add(didLabel);
				updatePanel.add(didField);
				updatePanel.add(phoneLabel);
				updatePanel.add(phoneField);
				updatePanel.add(addressLabel);
				updatePanel.add(addressField);
				
				String title = "Update Information";
				int option = JOptionPane.OK_CANCEL_OPTION;
				boolean validInput = false;
				
				do {
					int input = JOptionPane.showConfirmDialog(null, updatePanel, title, option);
					if(input == JOptionPane.OK_OPTION) {
						try {
							int did = Integer.parseInt(didField.getText().trim());			
							String newPhone = phoneField.getText().trim();
							String newAddress = addressField.getText().trim();
							Driver driver = new Driver();
							
							if (newPhone.equals("") && newAddress.equals("")) {
								JOptionPane.showMessageDialog(null, "Please fill in at least one field");
							} else if (driver.viewDriverInfo(did).empty) {
								JOptionPane.showMessageDialog(null, "DriverID not found - please try again");
							} else {							
								if (!newPhone.equals("")) {
									driver.updatePhoneNum(did, newPhone);
								}
								if (!newAddress.equals("")) {
									driver.updateAddress(did, newAddress);	
								}
								driverTable.removeAll();							
								ResultTableModel viewDriverInfo = driver.viewDriverInfo(did);
								driverTable.setModel(viewDriverInfo);
								JOptionPane.showMessageDialog(null, "Update Successful");
								validInput = true;
							}							
						} catch (NumberFormatException nfe) {
							JOptionPane.showMessageDialog(null, "Invalid format - please try again");
						};
					} else {
						validInput = true;
					}
				} while (!validInput);
			}
		});		
		
		// Add Buttons to driverMenu
		driverMenu = new JPanel();
		driverMenu.setLayout(new GridLayout(1, 2));
		driverMenu.add(driverGetShiftsBtn);
		driverMenu.add(driverViewAllShiftsBtn);
		driverMenu.add(driverUpdateInfoBtn);
		
		driverPanel = new JPanel();
		driverPanel.setLayout(new BorderLayout());
		driverPanel.add(driverScrollPane);
		//driverPanel.add(resetBtn, BorderLayout.SOUTH);
		driverPanel.add(driverMenu, BorderLayout.NORTH);
		return driverPanel;
	}

	private JPanel createOperatorPanel() {
		operatorPanel = new JPanel();
		return operatorPanel;
	}
}
