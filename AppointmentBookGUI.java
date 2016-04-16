import java.util.Calendar;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.YearMonth;
import java.util.GregorianCalendar;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.event.*;
import javax.swing.*;

public class AppointmentBookGUI extends JFrame implements ItemListener, ActionListener {	
	
	
	private JPanel initial, show, add;
	private JTextField titleField, descriptionField;
	private JTextField minuteField, hourField, dayField, monthField, yearField;
	private JComboBox amOrPm, eventTypeOptions;
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
		//navigation.addTab("Remove Appointments", remove);
		initializeFrame();
		
		// INITIAL
		//
		//
		// INITIAL
		
		JButton createNewFile = new JButton("Create new file.");	
		
		createNewFile.addActionListener(new CreateFileListener());		

		JButton openOldFile = new JButton("Open previous file.");

		//TODO: Add implementation for this method.
		openOldFile.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent ae) {	
				ObjectInputStream in = null;
				FileInputStream fin = null;

				try {
					fileName = JOptionPane.showInputDialog("What will the file name be?") + ".ser";
					myFile = new File(fileName);
					myAppBook = new AppointmentBook();
					
					fin = new FileInputStream(myFile);
					in = new ObjectInputStream(fin);
					
					myAppBook = (AppointmentBook) in.readObject();
					
					//TODO: verify successful connection 
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(initial, "Error. Please try again.");
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(initial, "Error. Please try again.");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(initial, "Error. Please try again.");
				} finally {
					try {
						if (in != null) in.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(initial, "Error. Please try again.");
					}
				}
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
		JScrollPane tempScrollPane = new JScrollPane(schedule);
		textPanel.add(tempScrollPane);
		show.add(textPanel);

		// add the schedule button
		
		JPanel showScheduleButton = new JPanel(new BorderLayout());
		showSchedule = new JButton("Show schedule");
		showSchedule.addActionListener(new ActionListener() {	
			//TODO: Add verification mechanism. 
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				FileInputStream fis = null;
				ObjectInputStream in = null;
				System.out.println("File connection: " + myFile.exists());

				try {
					fis = new FileInputStream(myFile);
					in = new ObjectInputStream(fis);
					myAppBook = (AppointmentBook) in.readObject();
					System.out.println("here 1");
					int sYear = Integer.parseInt((String)entryBoxes[STARTING_YEARS].getSelectedItem());
					int eYear = Integer.parseInt((String)entryBoxes[ENDING_YEARS].getSelectedItem());
					int sDay = Integer.parseInt((String)entryBoxes[STARTING_DAYS].getSelectedItem());
					int eDay = Integer.parseInt((String)entryBoxes[ENDING_DAYS].getSelectedItem());
					int sMonth = AppointmentBookGUI.monthToNumber((String)entryBoxes[STARTING_MONTHS].getSelectedItem());
					int eMonth = AppointmentBookGUI.monthToNumber((String)entryBoxes[ENDING_MONTHS].getSelectedItem());
					System.out.println(sYear);
					System.out.println(eYear);
					System.out.println(sDay);
					System.out.println(eDay);
					System.out.println(sMonth);
					System.out.println(eMonth);
					//String str = myAppBook.toString(sYear, eYear, sDay, eDay, sMonth, eMonth);
					//System.out.println(str);
					//myAppBook.displayAppointmentsFromTo(sYear, eYear, sDay, eDay, sMonth, eMonth);
					schedule.setText(myAppBook.toString(
						Integer.parseInt((String)entryBoxes[STARTING_YEARS].getSelectedItem()),
						Integer.parseInt((String)entryBoxes[ENDING_YEARS].getSelectedItem()),
						Integer.parseInt((String)entryBoxes[STARTING_DAYS].getSelectedItem()),
						Integer.parseInt((String)entryBoxes[ENDING_DAYS].getSelectedItem()),
						AppointmentBookGUI.monthToNumber((String)entryBoxes[STARTING_MONTHS].getSelectedItem()),
						AppointmentBookGUI.monthToNumber((String)entryBoxes[ENDING_MONTHS].getSelectedItem())
					));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
				
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});

		showScheduleButton.add(showSchedule);
		show.add(showScheduleButton);
		
		// ADD TAB
		//
		//
		// ADD TAB
		
		JPanel entryFields = new JPanel(new GridLayout(10, 2));

		entryFields.add(new JLabel("Title: "));
		titleField = new JTextField();
		entryFields.add(titleField);

		entryFields.add(new JLabel("Description: "));
		JTextField descriptionField = new JTextField();
		entryFields.add(descriptionField);
	//	entryFields.add(new JLabel("test"));
	//	entryFields.add(new JLabel("test2"));	
		
		entryFields.add(new JLabel("Event Type: "));
		eventTypeOptions = new JComboBox(new String[] {"Select an event type", "", "Onetime", "Monthly", "Daily"});
		entryFields.add(eventTypeOptions);

		entryFields.add(new JLabel("Day: "));
		dayField = new JTextField();
		dayField.setEditable(true);
		entryFields.add(dayField);

		entryFields.add(new JLabel("Month: "));
		monthField = new JTextField();
		monthField.setEditable(true);
		entryFields.add(monthField);

		entryFields.add(new JLabel("Year: "));
		yearField = new JTextField(Calendar.getInstance().get(Calendar.YEAR) + "");
		yearField.setEditable(true);
		entryFields.add(yearField);

		entryFields.add(new JLabel("Hour: "));
		hourField = new JTextField();
		entryFields.add(hourField);

		entryFields.add(new JLabel("Minute: "));
		minuteField = new JTextField(); 
		entryFields.add(minuteField);

		entryFields.add(new JLabel("AM or PM: "));
		amOrPm = new JComboBox(new String[] {"Select AM or PM", "", "AM", "PM"});
		entryFields.add(amOrPm);
		
		JButton clearButton = new JButton("Clear");
		JButton submitButton = new JButton("Submit");
		entryFields.add(submitButton);
		entryFields.add(clearButton);
		clearButton.addActionListener(new ClearButtonListener());/*{
			@Override public void actionPerformed(ActionEvent ae) {
				titleField.setText("");
				descriptionField.setText("");
				yearField.setText(Calendar.getInstance().get(Calendar.YEAR) + "");
				monthField.setText("");
				dayField.setText("");
				hourField.setText("");
				minuteField.setText("");
				amOrPm.setSelectedIndex(0);
				eventTypeOptions.setSelectedIndex(0);
			}
		});*/

		submitButton.addActionListener(new SubmitButtonListener()); /*{
			@Override 
			public void actionPerformed(ActionEvent ae) {
				boolean dataVerified = allFieldsVerified();
				System.out.println(dataVerified);
				if (dataVerified) {
					String title = titleField.getText();
					String description = descriptionField.getText();
					int year = Integer.parseInt(yearField.getText());
					int month = AppointmentBookGUI.monthToNumber(monthField.getText());
					int day = Integer.parseInt(dayField.getText());
					int hour = Integer.parseInt(hourField.getText());
					int minute = Integer.parseInt(minuteField.getText());
					boolean isAM = (amOrPm.getSelectedItem().equals("AM"))? true : false;

					if (eventTypeOptions.getSelectedItem().equals("Onetime")) {
						
					} else if ( eventTypeOptions.getSelectedItem().equals("Monthly")) {
						FileOutputStream fos = null;
						ObjectOutputStream out = null;
						FileInputStream fis = null;
						ObjectInputStream in = null;
						System.out.println("File connection: " + myFile.exists());

						try {
							fis = new FileInputStream(myFile);
							in = new ObjectInputStream(fis);
							myAppBook = (AppointmentBook) in.readObject();
							myAppBook.add(new Monthly(title, description, day, hour, minute, isAM));
							
							fos = new FileOutputStream(myFile);
							out = new ObjectOutputStream(fos);
							out.writeObject(myAppBook);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
						
						} finally {
							if (out != null) {
								try {
									out.close();
								} catch (IOException e) {
								
								}
							}

							if (in != null) {
								try {
									in.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}	
					} else if (eventTypeOptions.getSelectedItem().equals("Daily")) {
							
					} 
					
					titleField.setText("");
					descriptionField.setText("");
					yearField.setText(Calendar.getInstance().get(Calendar.YEAR) + "");
					monthField.setText("");
					dayField.setText("");
					hourField.setText("");
					minuteField.setText("");
					amOrPm.setSelectedIndex(0);
					eventTypeOptions.setSelectedIndex(0);

					JOptionPane.showMessageDialog(add, "Successful submission!");
				}

			}

			public boolean minuteFieldIsVerified() {
				try {
					int minutes = Integer.parseInt(minuteField.getText());
					return minutes < 60 && minutes >= 0;
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(add, "Invalid minute field.");
					return false;
				}
			}

			public boolean hourFieldIsVerified() {
				try {
					int hours = Integer.parseInt(hourField.getText());
					return hours <= 12 && hours > 0;
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(add, "Invalid hour field.");
					return false;
				}
			}	
		
			public boolean dateIsVerified() {
				try { 
					int year = Integer.parseInt(yearField.getText());
					int month = AppointmentBookGUI.monthToNumber(monthField.getText());
					int day = Integer.parseInt(dayField.getText());
					YearMonth ym = YearMonth.of(year, month);
					return ym.isValidDay(day);
				} catch (NumberFormatException e) {
					return false;
				}
			}

			public boolean titleIsVerified() {
				return (titleField.getText() != null) && (titleField.getText() != "");
			}

			public boolean descriptionIsVerified() {
				return (descriptionField.getText() != null) && (descriptionField.getText() != "");
			}

			public boolean amPmIsVerified() {
				return amOrPm.getSelectedIndex() != 0 && amOrPm.getSelectedIndex() != 1;
			}

			public boolean eventTypeOptionsIsVerified() {
				return eventTypeOptions.getSelectedIndex() != 0 && eventTypeOptions.getSelectedIndex() != 1; 
			}

			public boolean allFieldsVerified() {
				boolean debug = true;
				if (debug) {
					System.out.println(minuteFieldIsVerified());
					System.out.println(hourFieldIsVerified());
					System.out.println(dateIsVerified());
					System.out.println(titleIsVerified());
					System.out.println(descriptionIsVerified());
					System.out.println(amPmIsVerified());
					System.out.println(eventTypeOptionsIsVerified());
				}
				return minuteFieldIsVerified() 
					&& hourFieldIsVerified() 
					&& dateIsVerified()
					&& titleIsVerified() 
					&& descriptionIsVerified() 
					&& amPmIsVerified() 
					&& eventTypeOptionsIsVerified();
			}
		});*/

		add.add(entryFields);
		setVisible(true);
	}
	
	public void initializeFrame() {
		navigation = new JTabbedPane();
		this.getContentPane().add(navigation);		
		this.setTitle("Appointment Book");
		this.setSize(600, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		
		initial = new JPanel(new GridLayout(2, 1));
		show = new JPanel(new GridLayout(3, 1));
		add = new JPanel(new GridLayout(1,1));

		navigation.addTab("Start Here", initial);
		navigation.addTab("Show Appointments", show);
		navigation.addTab("Add Appointments", add);
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

	/**
	 * Clears all entries under the "add" tab.
	 * To be used after submission or when the clear button is pressed.
	 */

	public static void clearEntries() {
		titleField.setText("");
		descriptionField.setText("");
		yearField.setText(Calendar.getInstance().get(Calendar.YEAR) + "");
		monthField.setText("");
		dayField.setText("");
		hourField.setText("");
		minuteField.setText("");
		amOrPm.setSelectedIndex(0);
		eventTypeOptions.setSelectedIndex(0);
	}

	/**
	 * Action listener for the "clear" button.
	 */

	class ClearButtonListener implements ActionListener {	
		@Override 
		public void actionPerformed(ActionEvent ae) {
			titleField.setText("");
			descriptionField.setText("");
			yearField.setText(Calendar.getInstance().get(Calendar.YEAR) + "");
			monthField.setText("");
			dayField.setText("");
			hourField.setText("");
			minuteField.setText("");
			amOrPm.setSelectedIndex(0);
			eventTypeOptions.setSelectedIndex(0);
		}
	}

	/**
	 * Checks for a click on the submit button, and then opens the object located in myFile
	 * to add the new event to. Also verifies the data of the submit boxes. 
	 */

	class SubmitButtonListener implements ActionListener {	
		@Override 
		public void actionPerformed(ActionEvent ae) {
			boolean dataVerified = allFieldsVerified();
			System.out.println(dataVerified);
			if (dataVerified) {
				String title = titleField.getText();
				String description = descriptionField.getText();
				int year = Integer.parseInt(yearField.getText());
				int month = AppointmentBookGUI.monthToNumber(monthField.getText());
				int day = Integer.parseInt(dayField.getText());
				int hour = Integer.parseInt(hourField.getText());
				int minute = Integer.parseInt(minuteField.getText());
				boolean isAM = (amOrPm.getSelectedItem().equals("AM"))? true : false;

				if (eventTypeOptions.getSelectedItem().equals("Onetime")) {
					
				} else if (eventTypeOptions.getSelectedItem().equals("Monthly")) {
					FileOutputStream fos = null;
					ObjectOutputStream out = null;
					FileInputStream fis = null;
					ObjectInputStream in = null;
					System.out.println("File connection: " + myFile.exists());

					try {
						fis = new FileInputStream(myFile);
						in = new ObjectInputStream(fis);
						myAppBook = (AppointmentBook) in.readObject();
						myAppBook.add(new Monthly(title, description, day, hour, minute, isAM));
						
						fos = new FileOutputStream(myFile);
						out = new ObjectOutputStream(fos);
						out.writeObject(myAppBook);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
					
					} finally {
						if (out != null) {
							try {
								out.close();
							} catch (IOException e) {
							
							}
						}

						if (in != null) {
							try {
								in.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}	
				} else if (eventTypeOptions.getSelectedItem().equals("Daily")) {
						
				} 
				
				titleField.setText("");
				descriptionField.setText("");
				yearField.setText(Calendar.getInstance().get(Calendar.YEAR) + "");
				monthField.setText("");
				dayField.setText("");
				hourField.setText("");
				minuteField.setText("");
				amOrPm.setSelectedIndex(0);
				eventTypeOptions.setSelectedIndex(0);

				JOptionPane.showMessageDialog(add, "Successful submission!");
			}

		}

		public boolean minuteFieldIsVerified() {
			try {
				int minutes = Integer.parseInt(minuteField.getText());
				return minutes < 60 && minutes >= 0;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(add, "Invalid minute field.");
				return false;
			}
		}

		public boolean hourFieldIsVerified() {
			try {
				int hours = Integer.parseInt(hourField.getText());
				return hours <= 12 && hours > 0;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(add, "Invalid hour field.");
				return false;
			}
		}	
	
		public boolean dateIsVerified() {
			try { 
				int year = Integer.parseInt(yearField.getText());
				int month = AppointmentBookGUI.monthToNumber(monthField.getText());
				int day = Integer.parseInt(dayField.getText());
				YearMonth ym = YearMonth.of(year, month);
				return ym.isValidDay(day);
			} catch (NumberFormatException e) {
				return false;
			}
		}

		public boolean titleIsVerified() {
			return (titleField.getText() != null) && (titleField.getText() != "");
		}

		public boolean descriptionIsVerified() {
			return (descriptionField.getText() != null) && (descriptionField.getText() != "");
		}

		public boolean amPmIsVerified() {
			return amOrPm.getSelectedIndex() != 0 && amOrPm.getSelectedIndex() != 1;
		}

		public boolean eventTypeOptionsIsVerified() {
			return eventTypeOptions.getSelectedIndex() != 0 && eventTypeOptions.getSelectedIndex() != 1; 
		}

		public boolean allFieldsVerified() {
			boolean debug = true;
			if (debug) {
				System.out.println(minuteFieldIsVerified());
				System.out.println(hourFieldIsVerified());
				System.out.println(dateIsVerified());
				System.out.println(titleIsVerified());
				System.out.println(descriptionIsVerified());
				System.out.println(amPmIsVerified());
				System.out.println(eventTypeOptionsIsVerified());
			}
			return minuteFieldIsVerified() 
				&& hourFieldIsVerified() 
				&& dateIsVerified()
				&& titleIsVerified() 
				&& descriptionIsVerified() 
				&& amPmIsVerified() 
				&& eventTypeOptionsIsVerified();
		}
	}
	
	class CreateFileListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			ObjectOutputStream out = null;
			FileOutputStream fos = null;

			try {
				fileName = JOptionPane.showInputDialog("What will the file name be?") + ".ser";
				myFile = new File(fileName);
				boolean fileCreationSuccessful = myFile.createNewFile();
				myAppBook = new AppointmentBook();
				
				fos = new FileOutputStream(myFile);
				out = new ObjectOutputStream(fos);
				
				out.writeObject(myAppBook);

				//TODO: check if the file creation was successful.
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(initial, "Error. Please try again.");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(initial, "Error. Please try again.");
			} finally {
				try {
					if (out != null) out.close();
				} catch (IOException e) {

				}
			}
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

	public static int monthToNumber(String s) {
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
			return (startYM.isBefore(endYM));
		} catch (NumberFormatException e) {
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
