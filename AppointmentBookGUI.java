import java.util.Calendar;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.YearMonth;
import java.util.GregorianCalendar;
import java.util.regex.*;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.event.*;
import javax.swing.*;



public class AppointmentBookGUI extends JFrame implements ItemListener, ActionListener {	

	private File workingFile;
	private String fileName;
	private String mainFilePath;
	private AppointmentBook appBook; 
	private ButtonGroup actions;
	private JPanel lower;
	private JTabbedPane navigation;
	private JComboBox[] entryBoxes;
	private JTextArea schedule;
	private JButton showSchedule;
	
	private boolean writing;
	private FileOutputStream out;
	private FileInputStream in;
	private File myFile;

	private AppointmentBook myAppBook;


	private static final int STARTING_MONTHS = 0;
	private static final int ENDING_MONTHS = 1;
	private static final int STARTING_DAYS = 2;
	private static final int ENDING_DAYS = 3;
	private static final int STARTING_YEARS = 4;
	private static final int ENDING_YEARS = 5;
	private String startDay, endDay, startYear, endYear, startMonth, endMonth;

	public AppointmentBookGUI(AppointmentBook... ab) {	
	//this.appBook = ab[0];
		navigation = new JTabbedPane();
		this.getContentPane().add(navigation);		
		this.setTitle("Appointment Book");
		this.setSize(600, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		
		JPanel initial = new JPanel(new GridLayout(2, 1));
		JPanel show = new JPanel(new GridLayout(3, 1));
		JPanel add = new JPanel(new GridLayout(1,1));
		//JPanel remove = new JPanel(new FlowLayout());

		navigation.addTab("Start Here", initial);
		navigation.addTab("Show Appointments", show);
		navigation.addTab("Add Appointments", add);
		//navigation.addTab("Remove Appointments", remove);

		// INITIAL
		//
		//
		// INITIAL

		JButton createNewFile = new JButton("Create new file.");
		createNewFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					fileName = JOptionPane.showInputDialog("What will the file name be?") + ".txt";
					File myFile = new File(fileName);
					boolean fileCreationSuccessful = myFile.createNewFile();
					//TODO: check if the file creation was successful.
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(initial, "Error. Please try again.");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(initial, "Error. Please try again.");
				}
			}
		});

		JButton openOldFile = new JButton("Open previous file.");
		openOldFile.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent ae) {	
				fileName = JOptionPane.showInputDialog("What will the file name be?") + ".txt";
				/*try {
					
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(initial,"File not found");
				}*/	
			//fileName = JOptionPane.showInputDialog("What is the name of this file? Please enter the absolute path of the file.") + ".txt";
			}
		});

		initial.add(createNewFile);
		initial.add(openOldFile);


		// SHOW
		//
		//
		// SHOW
		
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

		// add everything in the text panel
		
		JPanel textPanel = new JPanel(new BorderLayout());
		schedule = new JTextArea("Test");
		textPanel.add(schedule);
		show.add(textPanel);

		// add the schedule button
		
		JPanel showScheduleButton = new JPanel(new BorderLayout());
		showSchedule = new JButton("Show schedule");
		showSchedule.addActionListener(this);

		showScheduleButton.add(showSchedule);
		show.add(showScheduleButton);
		
		// ADD TAB
		//
		//
		// ADD TAB
		
		JPanel entryFields = new JPanel(new GridLayout(10, 2));
		

		entryFields.add(new JLabel("Title: "));
		JTextField titleField = new JTextField();
		entryFields.add(titleField);

		entryFields.add(new JLabel("Description: "));
		JTextField descriptionArea = new JTextField();
		entryFields.add(descriptionArea);
	//	entryFields.add(new JLabel("test"));
	//	entryFields.add(new JLabel("test2"));	
		
		entryFields.add(new JLabel("Event Type: "));
		JComboBox eventTypeOptions = new JComboBox(new String[] {"Select an event type", "", "Onetime", "Monthly", "Daily"});
		entryFields.add(eventTypeOptions);

		entryFields.add(new JLabel("Day: "));
		JTextField dayField = new JTextField();
		dayField.setEditable(true);
		entryFields.add(dayField);

		entryFields.add(new JLabel("Month: "));
		JTextField monthField = new JTextField();
		monthField.setEditable(true);
		entryFields.add(monthField);

		entryFields.add(new JLabel("Year: "));
		JTextField yearField = new JTextField(Calendar.getInstance().get(Calendar.YEAR) + "");
		yearField.setEditable(true);
		entryFields.add(yearField);

		entryFields.add(new JLabel("Hour: "));
		JTextField hourField = new JTextField();
		entryFields.add(hourField);

		entryFields.add(new JLabel("Minute: "));
		JTextField minuteField = new JTextField(); 
		entryFields.add(minuteField);

		entryFields.add(new JLabel("AM or PM: "));
		JComboBox amOrPm = new JComboBox(new String[] {"Select AM or PM", "", "AM", "PM"});
		entryFields.add(amOrPm);
		
		JButton clearButton = new JButton("Clear");
		JButton submitButton = new JButton("Submit");
		entryFields.add(submitButton);
		entryFields.add(clearButton);

		add.add(entryFields);
		setVisible(true);


	}
	
	public void initializeShowTab() {

	}

	public static void main(String[] args) {
		new AppointmentBookGUI();
	}

	public void updateStartEndData(String s, JComboBox j) {
		switch ((String) j.getItemAt(0)) {
			case "Select a starting month": 
				System.out.println("Starting month == " + s);
				if (s != "Select a starting month" && s!= "" ) startMonth = s;
				break;
			case "Select an ending month": 
				if (s != "Select an ending month" && s!= "" ) endMonth = s;
				break;
			case "Select a starting day":
				if (s != "Select an starting day" && s!= "" ) startDay = s;
				break;
			case "Select an ending day":
				if (s != "Select an ending day" && s!= "" ) endDay = s;
				break;
			case "Select a starting year":
				if (s != "Select a starting year" && s!= "" ) startYear = s;
				break;
			case "Select an ending year":
				if (s != "Select an ending year" && s!= "" ) endYear = s;
				break;
			default:
				break;
		}
	}

	@Override 
	public void itemStateChanged(ItemEvent e) throws NullPointerException {
		String s = (String) e.getItem();
		JComboBox currentComboBox = (JComboBox) e.getItemSelectable();
		for (JComboBox j : entryBoxes) {
			if (e.getStateChange() == ItemEvent.SELECTED 
			    &&((String) j.getItemAt(0)).equals((String) currentComboBox.getItemAt(0))) {
				// this means that the same comboboxes are being accessed right now
				updateStartEndData(s, j);
			}
		}	
		System.out.println(dataIsVerified());
		System.out.println(s);
	}

	private int monthToNumber(String s) {
		int rn = 0; 
		switch (s.toLowerCase()) {
			case "january":
				return 1;
			case "february":
				return 2;
			case "march": 
				return 3;
			case "april": 
				return 4;
			case "may":
				return 5;
			case "june":
				return 6;
			case "july":
				return 7;
			case "august":
				return 8;
			case "september": 
				return 9;
			case "october":
				return 10;
			case "november":
				return 11;
			case "december":
				return 12;
			default: 
				throw new NumberFormatException();
		}
	}

	public boolean dataIsVerified() {
		try { 
			int startYearInt = Integer.parseInt(startYear);
			int startMonthInt = monthToNumber(startMonth);
			int startDayInt = Integer.parseInt(startDay);
			int endYearInt = Integer.parseInt(endYear);
			int endMonthInt = monthToNumber(endMonth);
			int endDayInt = Integer.parseInt(endDay);
			System.out.println(startYearInt);
			System.out.println(startMonthInt);
			System.out.println(startDayInt);
			System.out.println(endYearInt);
			System.out.println(endMonthInt);
			System.out.println(endDayInt);
			YearMonth startYM = YearMonth.of(startYearInt, startMonthInt);
			YearMonth endYM = YearMonth.of(endYearInt, endMonthInt);
			if (!(startYM.isValidDay(startDayInt) && endYM.isValidDay(endDayInt))) {System.out.println("here!");return false;}
			System.out.println("Here4");
			return (startYM.isBefore(endYM));
		} catch (NumberFormatException e) {
			System.out.println("here 2");
			return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (dataIsVerified()) {
			// show the schedule in the text area
			schedule.setText("test");	

		} else {
			// give a warning pop-up box.
			JOptionPane.showMessageDialog(this, "Sorry, invalid year! Please fix.");
		}
	}
}
