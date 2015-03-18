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
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import net.miginfocom.swing.MigLayout;


@SuppressWarnings("serial")
public class TLinkFrame extends JFrame {

	private JPanel mainPanel;
	private JPanel customerPanel;
	private JPanel driverPanel;
	private JPanel operatorPanel;
	private JPanel menuPanel;
	private JTabbedPane tabPane;
	
	private JButton searchBtn;
	private JButton updateBtn;
	private JButton clearBtn;

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
		
		searchBtn = new JButton("Search");
		updateBtn = new JButton("Update");
		clearBtn = new JButton("Clear");
		
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(5, 1));
		menuPanel.add(searchBtn);
		menuPanel.add(updateBtn);
		menuPanel.add(clearBtn);
		
		customerPanel = new JPanel();
		customerPanel.setLayout(new BorderLayout());
		customerPanel.setBackground(Color.WHITE);
		customerPanel.add(menuPanel, BorderLayout.LINE_START);

		driverPanel = new JPanel();
		driverPanel.setBackground(Color.WHITE);
		operatorPanel = new JPanel();
		operatorPanel.setBackground(Color.WHITE);
		
		tabPane = new JTabbedPane();
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
