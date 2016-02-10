import java.util.regex.*;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.event.*;
import javax.swing.*;



public class AppointmentBookGUI extends JFrame implements ItemListener {	

	private JRadioButton show;
	private JRadioButton add;
	private JRadioButton remove;
	private ButtonGroup actions;
	private JPanel lower;
	private JTabbedPane navigation;
	//private JComboBox[] entryBoxes = new JComboBox[6];
	private JComboBox[] entryBoxes;
	private static final int STARTING_MONTHS = 0;
	private static final int ENDING_MONTHS = 1;
	private static final int STARTING_DAYS = 2;
	private static final int ENDING_DAYS = 3;
	private static final int STARTING_YEARS = 4;
	private static final int ENDING_YEARS = 5;
	private String startDay, endDay, startYear, endYear, startMonth, endMonth;

	public AppointmentBookGUI() {	
		navigation = new JTabbedPane();
		this.getContentPane().add(navigation);		
		this.setTitle("Appointment Book");
		this.setSize(600, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel show = new JPanel(new GridLayout(3, 1));
		JPanel add = new JPanel(new FlowLayout());
		JPanel remove = new JPanel(new FlowLayout());

		navigation.addTab("Show Appointments", show);
		navigation.addTab("Add Appointments", add);
		navigation.addTab("Remove Appointments", remove);

		// add everything under show tab
		
		String[] startingMonths = {"Select a starting month", "", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		String[] endingMonths = {"Select an ending month", "", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

		String[] startingYears = new String[87], endingYears = new String[87];
		startingYears[0] = "Select a starting year"; startingYears[1] = "";
		endingYears[0] = "Select an ending year"; endingYears[1] = "";

		for (int i = 2016; i <= 2100; i++) {
			startingYears[i - 2014] = i + "";
			endingYears[i - 2014] = i + "";
		}

		String[] startingDays = new String[33], endingDays = new String[33];
		startingDays[0]="Select a starting day"; startingDays[1]="";
		endingDays[0]="Select an ending day"; endingDays[1] = "";
		
		for (int i = 3; i <= 33; i++) {
			startingDays[i-1] = (i-2) + ""; 
			endingDays[i-1] = (i-2) + "";
		}
		
		JPanel innerTable = new JPanel(new GridLayout(3, 2));

		entryBoxes = new JComboBox[] {		
			new JComboBox(startingMonths),
			new JComboBox(endingMonths),
			new JComboBox(startingDays),
			new JComboBox(endingDays),
			new JComboBox(startingYears),
			new JComboBox(endingYears)
		};
		
		//innerTable.setBackground(Color.BLACK);
		for (JComboBox j : entryBoxes) {
			innerTable.add(j);
			j.addItemListener(this);
		}	

		show.add(innerTable);

		JPanel textBox = new JPanel(new BorderLayout());
		JTextArea tester = new JTextArea("Test");
		textBox.add(tester);
		show.add(textBox);

		setVisible(true);
	}
	
	public void initializeShowTab() {

	}

	public static void main(String[] args) {
		new AppointmentBookGUI();
	}

	@Override 
	public void itemStateChanged(ItemEvent e) throws NullPointerException {
		String s = (String) e.getItem();
		JComboBox currentComboBox = (JComboBox) e.getItemSelectable();
		breakloop:
		for (JComboBox j : entryBoxes) {
			if (((String) j.getItemAt(0)).equals((String) currentComboBox.getItemAt(0))) {
				// this means that the same comboboxes are being accessed right now
				switch ((String) j.getItemAt(0)) {
					case "Select a starting month": 
						System.out.println("Starting month == " + s);
						startMonth = s;
						break breakloop;
					case "Select an ending month": 
						endMonth = s;
						break breakloop;
					case "Select a starting day":
						startDay = s;
						break breakloop;
					case "Select an ending day":
						endDay = s;
						break breakloop;
					case "Select a starting year":
						startYear = s;
						break breakloop;
					case "Select an ending year":
						endYear = s;
						break breakloop;
					default:
						break breakloop;

				}
			}
		}
		
		System.out.println(s);
	//	System.out.println(e.getSelectedItem());
	//	if (e.getStateChange() == ItemEvent.SELECTED) {

	//	}
	}
}
