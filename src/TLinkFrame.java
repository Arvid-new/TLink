import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JPanel routePanel;
	private JPanel stopPanel;
	private JPanel customerPanel;
	private JPanel driverPanel;
	private JPanel operatorPanel;
	private JPanel menuPanel;
	private JTabbedPane tabPane;
	
	private JTable customerTable;
	private JScrollPane customerScrollPane;
	
	private JButton updateBtn;
	private JButton clearBtn;
	
	private JTextField routeField;

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
		mainPanel.setBackground(Color.WHITE);
		
		JLabel title = new JLabel("TLink Database App");
		title.setFont(new Font("Dialog", Font.PLAIN, 18));		
		
		updateBtn = new JButton("Update");
		clearBtn = new JButton("Clear");
		routeField = new JTextField("Route");
		
		
		// Route Tab
		JButton routeSearchBtn = new JButton("Search");
		JButton stopsBtn = new JButton("Stops");
		
		JPanel routeMenu = new JPanel();
		routeMenu.setLayout(new GridLayout(1, 2));
		routeMenu.add(routeSearchBtn);
		routeMenu.add(stopsBtn);
		
		routePanel = new JPanel();
		routePanel.setLayout(new BorderLayout());
		routePanel.setBackground(Color.WHITE);
		routePanel.add(routeMenu, BorderLayout.NORTH);
	
		
		// Stop Tab
		JButton stopSearchBtn = new JButton("Search");
		JButton routesBtn = new JButton("Route(s)");
		
		JPanel stopMenu = new JPanel();
		stopMenu.setLayout(new GridLayout(1, 2));
		stopMenu.add(stopSearchBtn);
		stopMenu.add(routesBtn);
		
		stopPanel = new JPanel();
		stopPanel.setLayout(new BorderLayout());
		stopPanel.setBackground(Color.WHITE);
		stopPanel.add(stopMenu, BorderLayout.NORTH);

		
		// Customer Tab
		customerPanel = new JPanel();
		customerPanel.setLayout(new BorderLayout());
		customerPanel.setBackground(Color.WHITE);

		Object[][] data = {
				{"Kathy", "Smith", "111-111-1111"},
				{"Bob", "Doe", "555-555-5555"}
		};
		
		String[] colNames = {"First Name", "Last Name", "Phone Number"};

		customerTable = new JTable(data, colNames);		
		customerScrollPane = new JScrollPane(customerTable);
		customerPanel.add(customerScrollPane, BorderLayout.CENTER);
		
		
		// Driver Tab
		driverPanel = new JPanel();
		driverPanel.setBackground(Color.WHITE);
		
		
		// Operator Tab
		operatorPanel = new JPanel();
		operatorPanel.setBackground(Color.WHITE);
		
		
		// Tabs pane
		tabPane = new JTabbedPane();
		tabPane.addTab("Routes", routePanel);
		tabPane.addTab("Stops", stopPanel);
		tabPane.addTab("Customer", customerPanel);
		tabPane.addTab("Driver", driverPanel);
		tabPane.addTab("Operator", operatorPanel);
		tabPane.setBackground(Color.WHITE);
		tabPane.setFocusable(false);
		
		mainPanel.add(title, BorderLayout.PAGE_START);
		mainPanel.add(tabPane, BorderLayout.CENTER);

		setContentPane(mainPanel);		
	}
}
