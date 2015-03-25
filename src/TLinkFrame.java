import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.UIManager;
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
	private JPanel operatorMenu;
	private JTable routeTable;
	private JTable stopTable;

	private JScrollPane customerScrollPane;
	private JScrollPane driverScrollPane;

	private JLabel title;
	private JLabel welcome;

	private int empId = -1;

	public static void main(String[] args) {
		try {
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());


			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
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


	// ROUTE SECTION

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

	// STOP SECTION

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

	// CUSTOMER SECTION

	private JPanel createCustomerPanel() {
		customerTable = new JTable();
		customerScrollPane = new JScrollPane(customerTable);
		final int[] custID = {-1};
		final JButton logoutBtn = new JButton("Logout");
		final JButton loginBtn = new JButton("Login");
		final JButton updateBalanceBtn = new JButton("Update Balance");
		loginBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				int cid = -1;
				try {
					cid = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Customer ID")); 
				} catch (NumberFormatException nfe) {
					//ignore (this gets thrown only if user hits cancel before entering anything)
				};
				Customer customer = new Customer();
				ResultTableModel passResults = customer.login(cid);
				if (passResults.empty) {
					JOptionPane.showMessageDialog(null, "Login failed");
				}
				else {
					String name = passResults.getValueAt(0, 0).toString();
					welcome.setText("Welcome, " + name);
					custID[0] = cid;
					customerMenu.setLayout(new GridLayout(2, 2));
					customerMenu.add(updateBalanceBtn);
					customerMenu.add(logoutBtn);
					customerMenu.remove(loginBtn);
					customerPanel.revalidate();
					customerPanel.repaint();
					customerTable.removeAll();
					customerTable.setModel(passResults);
				}
			}
		});


		logoutBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				Customer customer = new Customer();
				welcome.setText("Welcome, Guest");
				customerTable.setModel(customer.login(-1));
				customerMenu.add(loginBtn);
				customerMenu.remove(logoutBtn);
				customerMenu.remove(updateBalanceBtn);
				customerMenu.setLayout(new GridLayout(1, 2));
				customerMenu.revalidate();
				customerMenu.repaint();
			}
		});


		updateBalanceBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel updatePanel = new JPanel();
				updatePanel.setLayout(new GridLayout(0, 1));
				JLabel amtLabel = new JLabel("Enter new amount to add");
				JTextField amtField = new JTextField();
				updatePanel.add(amtLabel);
				updatePanel.add(amtField);	

				String title = "Update Address";
				int option = JOptionPane.OK_CANCEL_OPTION;

				int input = JOptionPane.showConfirmDialog(null, updatePanel, title, option);
				if(input == JOptionPane.OK_OPTION) {				
					int cid = -1;
					int amtToAdd = 0;
					try {
						cid = custID[0];
						amtToAdd = Integer.parseInt(amtField.getText());

						Customer customer = new Customer();
						customer.updateBalance(cid, amtToAdd);
						ResultTableModel updateBalanceResults = customer.login(cid);
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
				customerTable.revalidate();
				customerTable.repaint();
			}
		});

		customerMenu = new JPanel();
		customerMenu.setLayout(new GridLayout(1, 2));
		customerMenu.add(loginBtn);
		//customerMenu.add(logoutBtn);
		//customerMenu.add(updateBalanceBtn);

		customerPanel = new JPanel();
		customerPanel.setLayout(new BorderLayout());
		customerPanel.add(customerScrollPane);
		customerPanel.add(resetBtn, BorderLayout.SOUTH);
		customerPanel.add(customerMenu, BorderLayout.NORTH);
		return customerPanel;

	}


	// DRIVER SECTION

	private JPanel createDriverPanel() {
		driverTable = new JTable();
		driverScrollPane = new JScrollPane(driverTable);

		JButton driverUpdateInfoBtn = new JButton("Update Information");
		JButton driverViewAllShiftsBtn = new JButton("View All Shifts");
		JButton driverGetShiftsBtn = new JButton("Get Shifts");
		final JButton logoutBtn = new JButton("Logout");
		final JButton loginBtn = new JButton("Login");

		final JPanel loginMenu = new JPanel();
		loginMenu.setLayout(new GridLayout(1,2));
		loginMenu.add(loginBtn);


		// Add Buttons to driverMenu
		driverMenu = new JPanel();
		driverMenu.setLayout(new GridLayout(1, 3));
		driverMenu.add(driverGetShiftsBtn);
		driverMenu.add(driverViewAllShiftsBtn);
		driverMenu.add(driverUpdateInfoBtn);
		driverMenu.setVisible(false);

		loginBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel loginPanel = new JPanel();
				loginPanel.setLayout(new GridLayout(0, 1));
				JLabel loginLabel = new JLabel("Enter Employee ID");
				JTextField loginField = new JTextField();
				loginPanel.add(loginLabel);
				loginPanel.add(loginField);

				String title = "Login";
				int option = JOptionPane.OK_CANCEL_OPTION;
				boolean validInput = false;

				do {
					int input = JOptionPane.showConfirmDialog(null, loginPanel, title, option);
					if (input == JOptionPane.OK_OPTION) {
						try {
							int did = Integer.parseInt(loginField.getText());
							Driver driver= new Driver();
							ResultTableModel passResults = driver.login(did);
							if (passResults.empty) {
								JOptionPane.showMessageDialog(null, "Login failed");
							}
							else {
								empId = did;
								String name = passResults.getValueAt(0, 1).toString();
								welcome.setText("Welcome, " + name);
								driverMenu.setVisible(true);
								loginMenu.add(logoutBtn);
								loginMenu.remove(loginBtn);
								/*driverPanel.revalidate();
								driverPanel.repaint();*/
								driverTable.removeAll();
								driverTable.setModel(passResults);
								validInput = true;
							}
						} catch (NumberFormatException nfe) {
							JOptionPane.showMessageDialog(null, "Login failed");
						};
					} else {
						validInput = true;
					}
				} while (!validInput);
			}
		});

		logoutBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				Driver driver = new Driver();
				welcome.setText("Welcome, Guest");
				empId = -1;
				loginMenu.add(loginBtn);
				loginMenu.remove(logoutBtn);
				driverTable.removeAll();
				driverTable.setModel(driver.login(-1));
				driverMenu.setVisible(false);
			}
		});

		driverGetShiftsBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				String dateStr = JOptionPane.showInputDialog(null, "Enter Date as: YYYY-MM-DD");
				Driver driver = new Driver();
				ResultTableModel getShiftsResults = driver.viewShifts(empId, dateStr);
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

		driverViewAllShiftsBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				Driver driver = new Driver();
				ResultTableModel viewAllShiftsResults = driver.viewAllShifts(empId);
				if (viewAllShiftsResults.empty) {
					JOptionPane.showMessageDialog(null, "No shifts found for given DriverID");
				}
				else {
					driverTable.removeAll();
					driverTable.setModel(viewAllShiftsResults);
				}
			}
		});

		driverUpdateInfoBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel updatePanel = new JPanel();
				updatePanel.setLayout(new GridLayout(0, 1));
				JLabel phoneLabel = new JLabel("Enter new phone number - no spaces or dashes");
				JLabel addressLabel = new JLabel("Enter new address");
				JTextField phoneField = new JTextField();
				JTextField addressField = new JTextField();
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
							String newPhone = phoneField.getText().trim();
							String newAddress = addressField.getText().trim();
							Driver driver = new Driver();

							if (newPhone.equals("") && newAddress.equals("")) {
								JOptionPane.showMessageDialog(null, "Please fill in at least one field");
							} else {							
								if (!newPhone.equals("")) {
									driver.updatePhoneNum(empId, newPhone);
								}
								if (!newAddress.equals("")) {
									driver.updateAddress(empId, newAddress);	
								}
								driverTable.removeAll();							
								ResultTableModel viewDriverInfo = driver.viewDriverInfo(empId);
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



		driverPanel = new JPanel();
		driverPanel.setLayout(new BorderLayout());
		driverPanel.add(driverScrollPane);
		driverPanel.add(loginMenu, BorderLayout.SOUTH);
		//driverPanel.add(resetBtn, BorderLayout.SOUTH);
		driverPanel.add(driverMenu, BorderLayout.NORTH);
		return driverPanel;
	}

	private JPanel createOperatorPanel() {
		JPanel addPanel = createAddTabPanel();
		JPanel removePanel = createRemoveTabPanel();
		JPanel updatePanel = createUpdateTabPanel();
		JTabbedPane operatorTabs = new JTabbedPane();
		operatorTabs.add("Add", addPanel);
		operatorTabs.add("Remove", removePanel);
		operatorTabs.addTab("Update", updatePanel);
		operatorTabs.setTabPlacement(JTabbedPane.LEFT);

		operatorPanel = new JPanel();
		operatorPanel.setLayout(new BorderLayout());
		operatorPanel.add(operatorTabs, BorderLayout.CENTER);
		return operatorPanel;
	}

	private JPanel createAddTabPanel() {
		final JTable operatorTable = new JTable();
		JScrollPane operatorScrollPane = new JScrollPane(operatorTable);
		JPanel addPanel = new JPanel();
		JPanel addPane = new JPanel();
		final JButton addCustomerBtn = new JButton("Add Customer");
		final JButton addDriverBtn = new JButton("Add Driver");
		final JButton addRouteBtn = new JButton("Add Route");
		final JButton addStopBtn = new JButton("Add Stop");
		final JButton addDriverVehicleBtn = new JButton("Add Driver Vehicle");
		final JButton addDriverlessVehicleBtn = new JButton("Add Driverless Vehicle");
		addCustomerBtn.setVisible(false);
		addDriverBtn.setVisible(false);
		addRouteBtn.setVisible(false);
		addStopBtn.setVisible(false);
		addDriverVehicleBtn.setVisible(false);
		addDriverlessVehicleBtn.setVisible(false);

		String[] addOptions = {"Select where to add to...", "Customer", "Driver", "Route", "Stop", "Driver Vehicle", "Driverless Vehicle"};
		JComboBox<String> addList = new JComboBox<String>(addOptions);
		addPanel.setLayout(new BorderLayout());

		addPanel.add(addList, BorderLayout.NORTH);
		addPanel.add(operatorScrollPane);
		addPanel.add(addPane, BorderLayout.SOUTH);
		OverlayLayout overlay = new OverlayLayout(addPane);
		addPane.setLayout(overlay);
		addPane.add(addCustomerBtn);
		addPane.add(addDriverBtn);
		addPane.add(addRouteBtn);
		addPane.add(addStopBtn);
		addPane.add(addDriverVehicleBtn);
		addPane.add(addDriverlessVehicleBtn);

		addList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JComboBox<?> cb = (JComboBox<?>) arg0.getSource();
				String addOption = (String)cb.getSelectedItem();

				if (addOption.equals("Select where to add to...")) {
					addCustomerBtn.setVisible(false);
					addDriverBtn.setVisible(false);
					addRouteBtn.setVisible(false);
					addStopBtn.setVisible(false);
					addDriverVehicleBtn.setVisible(false);
					addDriverlessVehicleBtn.setVisible(false);
					Customer customer = new Customer();
					operatorTable.setModel(customer.displayBalance(-1));
				}

				else if (addOption.equals("Customer")) {
					addCustomerBtn.setVisible(true);
					addDriverBtn.setVisible(false);
					addRouteBtn.setVisible(false);
					addStopBtn.setVisible(false);
					addDriverVehicleBtn.setVisible(false);
					addDriverlessVehicleBtn.setVisible(false);
					OwnsPass ownsPass = new OwnsPass();
					operatorTable.setModel(ownsPass.displayOwnsPass());
				}

				else if (addOption.equals("Driver")) {
					addDriverBtn.setVisible(true);
					addCustomerBtn.setVisible(false);
					addRouteBtn.setVisible(false);
					addStopBtn.setVisible(false);
					addDriverVehicleBtn.setVisible(false);
					addDriverlessVehicleBtn.setVisible(false);
					Driver driver = new Driver();
					operatorTable.setModel(driver.displayDrivers());
				}

				else if (addOption.equals("Route")) {
					addDriverBtn.setVisible(false);
					addCustomerBtn.setVisible(false);
					addRouteBtn.setVisible(true);
					addStopBtn.setVisible(false);
					addDriverVehicleBtn.setVisible(false);
					addDriverlessVehicleBtn.setVisible(false);
					Route route = new Route();
					operatorTable.setModel(route.displayRoutes());
				}

				else if (addOption.equals("Stop")) {
					addDriverBtn.setVisible(false);
					addCustomerBtn.setVisible(false);
					addRouteBtn.setVisible(false);
					addStopBtn.setVisible(true);
					addDriverVehicleBtn.setVisible(false);
					addDriverlessVehicleBtn.setVisible(false);
					Stop stop = new Stop();
					operatorTable.setModel(stop.displayStops());
				}
				
				else if (addOption.equals("Driver Vehicle")) {
					addDriverBtn.setVisible(false);
					addCustomerBtn.setVisible(false);
					addRouteBtn.setVisible(false);
					addStopBtn.setVisible(false);
					addDriverVehicleBtn.setVisible(true);
					addDriverlessVehicleBtn.setVisible(false);
					Driveable driveable = new Driveable();
					operatorTable.setModel(driveable.displayVehicles());
				}
				
				else if (addOption.equals("Driverless Vehicle")) {
					addDriverBtn.setVisible(false);
					addCustomerBtn.setVisible(false);
					addRouteBtn.setVisible(false);
					addStopBtn.setVisible(false);
					addDriverVehicleBtn.setVisible(false);
					addDriverlessVehicleBtn.setVisible(true);
					Driverless driverless = new Driverless();
					operatorTable.setModel(driverless.displayVehicles());
				}
			}
		});

		addCustomerBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel addPanel = new JPanel();
				addPanel.setLayout(new GridLayout(0, 1));
				JLabel cidLabel = new JLabel("Enter CustomerID");
				JLabel nameLabel = new JLabel("Enter Customer name");
				JLabel pidLabel = new JLabel("Enter Pass ID");
				JTextField cidField = new JTextField();
				JTextField nameField = new JTextField();
				JTextField pidField = new JTextField();
				addPanel.add(cidLabel);
				addPanel.add(cidField);
				addPanel.add(nameLabel);
				addPanel.add(nameField);
				addPanel.add(pidLabel);
				addPanel.add(pidField);

				String title = "Add Customer";
				int option = JOptionPane.OK_CANCEL_OPTION;
				boolean validInput = false;

				do {
					int input = JOptionPane.showConfirmDialog(null, addPanel, title, option);
					if(input == JOptionPane.OK_OPTION) {
						try {
							int newCid = Integer.parseInt(cidField.getText().trim());			
							String newName = nameField.getText().trim();
							int newPid = Integer.parseInt(pidField.getText().trim());
							Customer customer = new Customer();
							OwnsPass ownsPass = new OwnsPass();

							if (newName.equals("")) {
								JOptionPane.showMessageDialog(null, "Please fill in every field");
							} else {							
								customer.insertCustomer(newCid, newName);
								ownsPass.insertOwnsPass(newPid, 0, newCid);
								operatorTable.removeAll();							
								ResultTableModel viewCustomerInfo = ownsPass.displayOwnsPass();
								operatorTable.setModel(viewCustomerInfo);
								JOptionPane.showMessageDialog(null, "Customer added");
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

		addDriverBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel addPanel = new JPanel();
				addPanel.setLayout(new GridLayout(0, 1));
				JLabel didLabel = new JLabel("Enter DriverID:");
				JLabel nameLabel = new JLabel("Enter driver name");
				JLabel phoneLabel = new JLabel("Enter phone number");
				JLabel addressLabel = new JLabel("Enter address");
				JTextField didField = new JTextField();
				JTextField nameField = new JTextField();
				JTextField phoneField = new JTextField();
				JTextField addressField = new JTextField();
				addPanel.add(didLabel);
				addPanel.add(didField);
				addPanel.add(nameLabel);
				addPanel.add(nameField);
				addPanel.add(phoneLabel);
				addPanel.add(phoneField);
				addPanel.add(addressLabel);
				addPanel.add(addressField);

				String title = "Add Driver";
				int option = JOptionPane.OK_CANCEL_OPTION;
				boolean validInput = false;

				do {
					int input = JOptionPane.showConfirmDialog(null, addPanel, title, option);
					if(input == JOptionPane.OK_OPTION) {
						try {
							int newDid = Integer.parseInt(didField.getText().trim());			
							String newName = nameField.getText().trim();
							String newPhone = phoneField.getText().trim();
							String newAddress = addressField.getText().trim();
							Driver driver = new Driver();

							if (newName.equals("") || newPhone.equals("") || newAddress.equals("")) {
								JOptionPane.showMessageDialog(null, "Please fill in every field");
							} else {							
								driver.insertDriver(newDid, newName, newPhone, newAddress);
								operatorTable.removeAll();							
								ResultTableModel viewDriverInfo = driver.viewDriverInfo(newDid);
								operatorTable.setModel(viewDriverInfo);
								JOptionPane.showMessageDialog(null, "Driver added");
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

		addRouteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel addPanel = new JPanel();
				addPanel.setLayout(new GridLayout(0, 1));
				JLabel ridLabel = new JLabel("Enter route number:");
				JLabel nameLabel = new JLabel("Enter route name:");
				JLabel startLabel = new JLabel("Enter start time HH:MM:SS");
				JLabel endLabel = new JLabel("Enter end time HH:MM:SS");
				JTextField ridField = new JTextField();
				JTextField nameField = new JTextField();
				JTextField startField = new JTextField();
				JTextField endField = new JTextField();
				addPanel.add(ridLabel);
				addPanel.add(ridField);
				addPanel.add(nameLabel);
				addPanel.add(nameField);
				addPanel.add(startLabel);
				addPanel.add(startField);
				addPanel.add(endLabel);
				addPanel.add(endField);

				String title = "Add Route";
				int option = JOptionPane.OK_CANCEL_OPTION;
				boolean validInput = false;

				do {
					int input = JOptionPane.showConfirmDialog(null, addPanel, title, option);
					if(input == JOptionPane.OK_OPTION) {
						try {
							int newRid = Integer.parseInt(ridField.getText().trim());			
							String newName = nameField.getText().trim();

							Time newStart = Time.valueOf(startField.getText().trim());
							Time newEnd = Time.valueOf(endField.getText().trim());
							Route route = new Route();

							if (newName.equals("") || newStart.equals("") || newEnd.equals("")) {
								JOptionPane.showMessageDialog(null, "Please fill in every field");
							} else {							
								route.insertRoute(newRid, newName, newStart, newEnd);
								operatorTable.removeAll();							
								ResultTableModel viewRouteInfo = route.displayRoutes();
								operatorTable.setModel(viewRouteInfo);
								JOptionPane.showMessageDialog(null, "Route added");
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

		addStopBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel addPanel = new JPanel();
				addPanel.setLayout(new GridLayout(0, 1));
				JLabel sidLabel = new JLabel("Enter stop number:");
				JLabel nameLabel = new JLabel("Enter stop name:");
				JLabel locationLabel = new JLabel("Enter stop location");
				JTextField sidField = new JTextField();
				JTextField nameField = new JTextField();
				JTextField locationField = new JTextField();
				addPanel.add(sidLabel);
				addPanel.add(sidField);
				addPanel.add(nameLabel);
				addPanel.add(nameField);
				addPanel.add(locationLabel);
				addPanel.add(locationField);

				String title = "Add Stop";
				int option = JOptionPane.OK_CANCEL_OPTION;
				boolean validInput = false;

				do {
					int input = JOptionPane.showConfirmDialog(null, addPanel, title, option);
					if(input == JOptionPane.OK_OPTION) {
						try {
							int newSid = Integer.parseInt(sidField.getText().trim());			
							String newName = nameField.getText().trim();
							String newLocation = locationField.getText().trim();
							Stop stop = new Stop();

							if (newName.equals("") || newLocation.equals("")) {
								JOptionPane.showMessageDialog(null, "Please fill in every field");
							} else {							
								stop.insertStop(newSid, newName, newLocation);
								operatorTable.removeAll();							
								ResultTableModel viewStopInfo = stop.displayStops();
								operatorTable.setModel(viewStopInfo);
								JOptionPane.showMessageDialog(null, "Stop added");
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

		addDriverlessVehicleBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel addPanel = new JPanel();
				addPanel.setLayout(new GridLayout(0, 1));
				JLabel vidLabel = new JLabel("Enter vehicle number:");
				JLabel ageLabel = new JLabel("Enter vehicle age:");
				JLabel capLabel = new JLabel("Enter vehicle capacity:");
				JTextField vidField = new JTextField();
				JTextField ageField = new JTextField();				
				JTextField capField = new JTextField();
				addPanel.add(vidLabel);
				addPanel.add(vidField);
				addPanel.add(ageLabel);
				addPanel.add(ageField);
				addPanel.add(capLabel);
				addPanel.add(capField);
				
				String title = "Add Driverless Vehicle";
				int option = JOptionPane.OK_CANCEL_OPTION;
				boolean validInput = false;

				do {
					int input = JOptionPane.showConfirmDialog(null, addPanel, title, option);
					if(input == JOptionPane.OK_OPTION) {
						String vidStr = vidField.getText().trim();
						String ageStr = ageField.getText().trim();
						String capStr = capField.getText().trim();

						if (vidStr.equals("") || ageStr.equals("") || capStr.equals("")) {
							JOptionPane.showMessageDialog(null, "Please fill in every field");
						} else {		
							try {
								int newVid = Integer.parseInt(vidField.getText().trim());
								int newAge = Integer.parseInt(ageField.getText().trim());
								int newCap = Integer.parseInt(capField.getText().trim());								
								Driverless driverless = new Driverless();			
								driverless.insertVehicle(newVid, newAge, newCap);
								operatorTable.removeAll();							
								ResultTableModel viewDriverlessInfo = driverless.displayVehicles();
								operatorTable.setModel(viewDriverlessInfo);
								JOptionPane.showMessageDialog(null, "Driverless vehicle added");
								validInput = true;								
							} catch (NumberFormatException nfe) {
								JOptionPane.showMessageDialog(null, "Invalid format - please try again");								
							}
						}
					} else {
						validInput = true;
					}
				} while (!validInput);
			}				
		});		
		
		return addPanel;
	}

	private JPanel createRemoveTabPanel() {
		final JTable operatorTable = new JTable();
		JScrollPane operatorScrollPane = new JScrollPane(operatorTable);
		JPanel removePanel = new JPanel();
		JPanel removePane = new JPanel();
		final JButton removeCustomerBtn = new JButton("Remove Customer");
		final JButton removeDriverBtn = new JButton("Remove Driver");
		final JButton removeRouteBtn = new JButton("Remove Route");
		final JButton removeStopBtn = new JButton("Remove Stop");
		removeCustomerBtn.setVisible(false);
		removeDriverBtn.setVisible(false);
		removeRouteBtn.setVisible(false);
		removeStopBtn.setVisible(false);

		String[] removeOptions = {"Select where to remove from...", "Customer", "Driver", "Route", "Stop", "Driver Vehicle", "Driverless Vehicle"};
		JComboBox<String> removeList = new JComboBox<String>(removeOptions);
		removePanel.setLayout(new BorderLayout());

		removePanel.add(removeList, BorderLayout.NORTH);
		removePanel.add(operatorScrollPane);
		removePanel.add(removePane, BorderLayout.SOUTH);
		OverlayLayout overlay = new OverlayLayout(removePane);
		removePane.setLayout(overlay);
		removePane.add(removeCustomerBtn);
		removePane.add(removeDriverBtn);
		removePane.add(removeRouteBtn);
		removePane.add(removeStopBtn);

		removeList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JComboBox<?> cb = (JComboBox<?>) arg0.getSource();
				String removeOption = (String)cb.getSelectedItem();

				if (removeOption.equals("Select where to remove from...")) {
					removeCustomerBtn.setVisible(false);
					removeDriverBtn.setVisible(false);
					removeRouteBtn.setVisible(false);
					removeStopBtn.setVisible(false);
					Customer customer = new Customer();
					operatorTable.setModel(customer.displayBalance(-1));
				}

				else if (removeOption.equals("Customer")) {
					removeCustomerBtn.setVisible(true);
					removeDriverBtn.setVisible(false);
					removeRouteBtn.setVisible(false);
					removeStopBtn.setVisible(false);
					Customer customer = new Customer();
					operatorTable.setModel(customer.displayCustomers());
				}

				else if (removeOption.equals("Driver")) {
					removeDriverBtn.setVisible(true);
					removeCustomerBtn.setVisible(false);
					removeRouteBtn.setVisible(false);
					removeStopBtn.setVisible(false);
					Driver driver = new Driver();
					operatorTable.setModel(driver.displayDrivers());
				}

				else if (removeOption.equals("Route")) {
					removeDriverBtn.setVisible(false);
					removeCustomerBtn.setVisible(false);
					removeRouteBtn.setVisible(true);
					removeStopBtn.setVisible(false);
					Route route = new Route();
					operatorTable.setModel(route.displayRoutes());
				}

				else if (removeOption.equals("Stop")) {
					removeDriverBtn.setVisible(false);
					removeCustomerBtn.setVisible(false);
					removeRouteBtn.setVisible(false);
					removeStopBtn.setVisible(true);
					Stop stop = new Stop();
					operatorTable.setModel(stop.displayStops());
				}
			}
		});

		removeCustomerBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel removePanel = new JPanel();
				removePanel.setLayout(new GridLayout(0, 1));
				JLabel cidLabel = new JLabel("Enter CustomerID");
				JTextField cidField = new JTextField();
				removePanel.add(cidLabel);
				removePanel.add(cidField);

				String title = "Remove Customer";
				int option = JOptionPane.OK_CANCEL_OPTION;
				boolean validInput = false;

				do {
					int input = JOptionPane.showConfirmDialog(null, removePanel, title, option);
					if(input == JOptionPane.OK_OPTION) {
						try {
							int cid = Integer.parseInt(cidField.getText().trim());			
							Customer customer = new Customer();				
							customer.deleteCustomer(cid);
							operatorTable.removeAll();							
							ResultTableModel viewCustomerInfo = customer.displayCustomers();
							operatorTable.setModel(viewCustomerInfo);
							JOptionPane.showMessageDialog(null, "Customer " + cid + " removed");
							validInput = true;						
						} catch (NumberFormatException nfe) {
							JOptionPane.showMessageDialog(null, "Invalid format - please try again");
						};
					} else {
						validInput = true;
					}
				} while (!validInput);
			}				
		});

		removeDriverBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel removePanel = new JPanel();
				removePanel.setLayout(new GridLayout(0, 1));
				JLabel didLabel = new JLabel("Enter Driver ID:");
				JTextField didField = new JTextField();
				removePanel.add(didLabel);
				removePanel.add(didField);

				String title = "Remove Driver";
				int option = JOptionPane.OK_CANCEL_OPTION;
				boolean validInput = false;

				do {
					int input = JOptionPane.showConfirmDialog(null, removePanel, title, option);
					if(input == JOptionPane.OK_OPTION) {
						try {
							int did = Integer.parseInt(didField.getText().trim());			
							Driver driver = new Driver();

							driver.deleteDriver(did);
							operatorTable.removeAll();							
							ResultTableModel viewDriverInfo = driver.displayDrivers();
							operatorTable.setModel(viewDriverInfo);
							JOptionPane.showMessageDialog(null, "Driver " + did + " removed");
							validInput = true;							
						} catch (NumberFormatException nfe) {
							JOptionPane.showMessageDialog(null, "Invalid format - please try again");
						};
					} else {
						validInput = true;
					}
				} while (!validInput);
			}				
		});

		removeRouteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel removePanel = new JPanel();
				removePanel.setLayout(new GridLayout(0, 1));
				JLabel ridLabel = new JLabel("Enter route number:");
				JTextField ridField = new JTextField();
				removePanel.add(ridLabel);
				removePanel.add(ridField);

				String title = "Remove Route";
				int option = JOptionPane.OK_CANCEL_OPTION;
				boolean validInput = false;

				do {
					int input = JOptionPane.showConfirmDialog(null, removePanel, title, option);
					if(input == JOptionPane.OK_OPTION) {
						try {
							int rid = Integer.parseInt(ridField.getText().trim());			
							Route route = new Route();							
							route.deleteRoute(rid);
							operatorTable.removeAll();							
							ResultTableModel viewRouteInfo = route.displayRoutes();
							operatorTable.setModel(viewRouteInfo);
							JOptionPane.showMessageDialog(null, "Route removed");
							validInput = true;						
						} catch (NumberFormatException nfe) {
							JOptionPane.showMessageDialog(null, "Invalid format - please try again");
						};
					} else {
						validInput = true;
					}
				} while (!validInput);
			}				
		});

		removeStopBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel removePanel = new JPanel();
				removePanel.setLayout(new GridLayout(0, 1));
				JLabel sidLabel = new JLabel("Enter stop number:");
				JTextField sidField = new JTextField();
				removePanel.add(sidLabel);
				removePanel.add(sidField);

				String title = "Remove Stop";
				int option = JOptionPane.OK_CANCEL_OPTION;
				boolean validInput = false;

				do {
					int input = JOptionPane.showConfirmDialog(null, removePanel, title, option);
					if(input == JOptionPane.OK_OPTION) {
						try {
							int sid = Integer.parseInt(sidField.getText().trim());			
							Stop stop = new Stop();						
							stop.deleteStop(sid);
							operatorTable.removeAll();							
							ResultTableModel viewStopInfo = stop.displayStops();
							operatorTable.setModel(viewStopInfo);
							JOptionPane.showMessageDialog(null, "Stop removed");
							validInput = true;					
						} catch (NumberFormatException nfe) {
							JOptionPane.showMessageDialog(null, "Invalid format - please try again");
						};
					} else {
						validInput = true;
					}
				} while (!validInput);
			}				
		});

		return removePanel;
	}

	private JPanel createUpdateTabPanel() {
		final JTable operatorTable = new JTable();
		JScrollPane operatorScrollPane = new JScrollPane(operatorTable);
		JPanel updatePanel = new JPanel();
		JPanel updatePane = new JPanel();
		final JButton updateCustomerNameBtn = new JButton("Update Customer name");
		final JButton updateCustomerBalanceBtn = new JButton("Update Customer balance");
		updateCustomerNameBtn.setVisible(false);
		updateCustomerBalanceBtn.setVisible(false);

		String[] addOptions = {"Select where to update from...", "Customer"};
		JComboBox<String> updateList = new JComboBox<String>(addOptions);
		updatePanel.setLayout(new BorderLayout());

		updatePanel.add(updateList, BorderLayout.NORTH);
		updatePanel.add(operatorScrollPane);
		updatePanel.add(updatePane, BorderLayout.SOUTH);
		updatePane.setLayout(new GridLayout(0, 2));
		updatePane.add(updateCustomerNameBtn);
		updatePane.add(updateCustomerBalanceBtn);

		updateList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JComboBox<?> cb = (JComboBox<?>) arg0.getSource();
				String addOption = (String)cb.getSelectedItem();

				if (addOption.equals("Select where to update from...")) {
					Customer customer = new Customer();
					updateCustomerNameBtn.setVisible(false);
					updateCustomerBalanceBtn.setVisible(false);
					operatorTable.setModel(customer.displayBalance(-1));
				}

				else if (addOption.equals("Customer")) {
					updateCustomerNameBtn.setVisible(true);
					updateCustomerBalanceBtn.setVisible(true);
					OwnsPass ownsPass = new OwnsPass();
					operatorTable.setModel(ownsPass.displayOwnsPass());
				}
			}
		});

		updateCustomerNameBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel addPanel = new JPanel();
				addPanel.setLayout(new GridLayout(0, 1));
				JLabel cidLabel = new JLabel("Enter Customer ID:");
				JLabel nameLabel = new JLabel("Enter new name:");
				JTextField cidField = new JTextField();
				JTextField nameField = new JTextField();
				addPanel.add(cidLabel);
				addPanel.add(cidField);
				addPanel.add(nameLabel);
				addPanel.add(nameField);

				String title = "Update Customer name";
				int option = JOptionPane.OK_CANCEL_OPTION;
				boolean validInput = false;

				do {
					int input = JOptionPane.showConfirmDialog(null, addPanel, title, option);
					if(input == JOptionPane.OK_OPTION) {
						try {
							int newCid = Integer.parseInt(cidField.getText().trim());			
							String newName = nameField.getText().trim();
							Customer customer = new Customer();

							if (newName.equals("")) {
								JOptionPane.showMessageDialog(null, "Please fill in every field.");
							} else {							
								customer.updateName(newCid, newName);
								operatorTable.removeAll();							
								ResultTableModel viewCustomerInfo = customer.displayCustomers();
								operatorTable.setModel(viewCustomerInfo);
								JOptionPane.showMessageDialog(null, "Customer name updated.");
								validInput = true;
							}							
						} catch (NumberFormatException nfe) {
							JOptionPane.showMessageDialog(null, "Invalid format - please try again.");
						};
					} else {
						validInput = true;
					}
				} while (!validInput);
			}				
		});

		updateCustomerBalanceBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel addPanel = new JPanel();
				addPanel.setLayout(new GridLayout(0, 1));
				JLabel cidLabel = new JLabel("Enter Customer ID:");
				JLabel pidLabel = new JLabel("Enter Pass ID:");
				JLabel balanceLabel = new JLabel("Enter amount to add to balance:");
				JTextField cidField = new JTextField();
				JTextField pidField = new JTextField();
				JTextField balanceField = new JTextField();
				addPanel.add(cidLabel);
				addPanel.add(cidField);
				addPanel.add(balanceLabel);
				addPanel.add(balanceField);
				addPanel.add(pidLabel);
				addPanel.add(pidField);

				String title = "Update Customer balance";
				int option = JOptionPane.OK_CANCEL_OPTION;
				boolean validInput = false;

				do {
					int input = JOptionPane.showConfirmDialog(null, addPanel, title, option);
					if(input == JOptionPane.OK_OPTION) {
						try {
							int newCid = Integer.parseInt(cidField.getText().trim());	
							int newPid = Integer.parseInt(pidField.getText().trim());		
							int newBalance = Integer.parseInt(balanceField.getText().trim());
							Customer customer = new Customer();

							customer.updateBalance(newCid, newBalance);
							operatorTable.removeAll();							
							ResultTableModel customerModel = customer.displayCustomers();
							operatorTable.setModel(customerModel);
							JOptionPane.showMessageDialog(null, "Balance updated");
							validInput = true;

						} catch (NumberFormatException nfe) {
							JOptionPane.showMessageDialog(null, "Invalid format - please try again");
						};
					} else {
						validInput = true;
					}
				} while (!validInput);
			}				
		});

		return updatePanel;
	}
}
