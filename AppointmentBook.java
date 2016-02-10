import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Comparator;
import java.util.Collections;

public class AppointmentBook {
	public ArrayList<Appointment> appointments;

	public AppointmentBook(Appointment... theAppointments) {
		this.appointments = new ArrayList<Appointment>();
		for (Appointment a : theAppointments) {
			appointments.add(a);
		}
	}

	public AppointmentBook(ArrayList<Appointment> theAppts){
		appointments = theAppts;
	}

	public void displayAppointments() {
		for (Appointment a : appointments) {
			a.displayAppointment();
		}
	}

	public void writeToFile(){
	
	}

	public void add(Appointment a){ 
		appointments.add(a);	
	}

	//TODO: only goes to day before endday. fix this. 
	public void displayAppointmentsFromTo(int startYear, int endYear, int startDay, int endDay, int startMonth, int endMonth) {
		Collections.sort(appointments);
		ArrayList<Appointment> apptsLeft = new ArrayList<Appointment>();
		ArrayList<GregorianCalendar> appointmentDates = new ArrayList<GregorianCalendar>();
		for (Appointment a : appointments) apptsLeft.add(a);
		//for (Appointment a : appointments) a.displayAppointment();
		
		GregorianCalendar startDate = new GregorianCalendar(startYear, startMonth, startDay);
		GregorianCalendar endDate = new GregorianCalendar(endYear, endMonth, endDay);	

		while (startDate.getTimeInMillis() < endDate.getTimeInMillis()) {
			for (Appointment a : appointments) {
				if (a.occursOn(startDate.get(Calendar.YEAR), 
					       startDate.get(Calendar.MONTH),
					       startDate.get(Calendar.DAY_OF_MONTH))) {
					appointmentDates.add((GregorianCalendar) startDate.clone());
					startDate.add(Calendar.DAY_OF_MONTH, 1);
					// System.out.println(startDate.get(Calendar.MONTH) + "-" + startDate.get(Calendar.DAY_OF_MONTH) + "-" + startDate.get(Calendar.YEAR));
					break;
				}
			}
		}

		for (GregorianCalendar g : appointmentDates) {
			System.out.println(g.get(Calendar.MONTH) + "-" + g.get(Calendar.DAY_OF_MONTH) + "-" + g.get(Calendar.YEAR));
			ArrayList<Appointment> theseAppointments = new ArrayList<Appointment>();
			for (Appointment a : appointments) {	
				if (a.occursOn(g.get(Calendar.YEAR), 
					       g.get(Calendar.MONTH),
					       g.get(Calendar.DAY_OF_MONTH))) {
					theseAppointments.add(a);
				}
			}
			
			AppointmentBook tempAB = new AppointmentBook(theseAppointments);
			tempAB.sortAppointments();
			tempAB.displayAppointments();
		}
	}

	public void remove() {
	
	}

	public ArrayList<Appointment> findAppointmentsOn(int year, int month, int day) {
		ArrayList<Appointment> theseAppointments = new ArrayList<Appointment>();
		for (Appointment a : appointments) {
			if (a.occursOn(year, month, day)) theseAppointments.add(a);
		}
		return theseAppointments;
	}

	public void modify() {

	}

	public void sortAppointments() {
		Collections.sort(appointments);
	}

	//temp method for debugging
	public ArrayList<Appointment> getApptList() {
		return appointments;
	}

	public String toString() {
		return "implement later"; 	
	}

	public static void main(String... args) {
		AppointmentBook myAppointmentBook = new AppointmentBook();
		
		myAppointmentBook.add(new Onetime(
			"Dentist's Appointment, #2", 
			"Check out potential cavity at dentist's office", 
			15, 3, 2016, 7, 30, false)
		);

		myAppointmentBook.add(new Onetime(
			"Doctor's Appointment, #3", 
			"Check out potential tumor at doctor's office", 
			20, 3, 2016, 7, 30, false)
		);
	

		myAppointmentBook.add(new Monthly(
			"Have a good first of the month, #2", 
			"Check out potential tumor at doctor's office", 
			1, 1, 00, false)
		);

		myAppointmentBook.add(new Daily(
			"Wake Up, #1", 
			"I have to wake up for the day ahead of me", 
			7, 30, true)
		);

		myAppointmentBook.add(new Onetime(
			"Mini Golf, #4", 
			"Check out potential tumor at doctor's office", 
			20, 4, 2016, 6, 30, false)
		);

		myAppointmentBook.sortAppointments();
		for (Appointment a:myAppointmentBook.getApptList()) a.displayAppointment();

		//myAppointmentBook.findAppointmentsOn(2016, 3, 20).get(0).displayAppointment();
		myAppointmentBook.displayAppointmentsFromTo(2016, 2016, 10, 10, 3, 4);
		
		
	}
}
