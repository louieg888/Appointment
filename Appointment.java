import java.util.Calendar;

public abstract class Appointment implements Comparable<Appointment>, java.io.Serializable {
	private String title;
	private String description; 
	private Calendar appointmentDate;

	
	public Appointment(String title, String description, int day, int month, int year, int hour, int minute, boolean isAM) {
		this.description = description;
		this.title = title;
		appointmentDate = Calendar.getInstance();
		appointmentDate.set(year, month, day, hour, minute);
		appointmentDate.set(Calendar.HOUR, hour);
		appointmentDate.set(Calendar.AM_PM, (isAM)? Calendar.AM : Calendar.PM);
	}	

	public Appointment(String title, String description, int day, int hour, int minute, boolean isAM) {
		this.description = description;
		this.title = title;
		appointmentDate = Calendar.getInstance(); 
		appointmentDate.set(Calendar.DAY_OF_MONTH, day);
		appointmentDate.set(Calendar.HOUR, hour);
		appointmentDate.set(Calendar.MINUTE, minute);
		appointmentDate.set(Calendar.AM_PM, (isAM)? Calendar.AM : Calendar.PM);
	}
	
	public Appointment(String title, String description, int hour, int minute, boolean isAM) {
		this.description = description;
		this.title = title;
		appointmentDate = Calendar.getInstance(); 
		appointmentDate.set(Calendar.HOUR, hour);
		appointmentDate.set(Calendar.MINUTE, minute);
		appointmentDate.set(Calendar.AM_PM, (isAM)? Calendar.AM : Calendar.PM);
	}

	public abstract boolean occursOn(int year, int month, int day); 
	
	@Override
	public int compareTo(Appointment other) {

		if (other instanceof Daily || this instanceof Daily) {
			if(this.isAM() != other.isAM()) return -1*Boolean.compare(this.isAM(),other.isAM());
			else if(this.getHour() != other.getHour()) return Integer.compare(this.getHour(), other.getHour());	
			else if(this.getMinute() != other.getMinute()) return Integer.compare(this.getMinute(), other.getMinute());
			else return 0;
		} else if (other instanceof Monthly || this instanceof Monthly) {	
			if(this.getDay() != other.getDay()) return Integer.compare(this.getDay(), other.getDay());	
			else if(this.isAM() != other.isAM()) return Boolean.compare(this.isAM(), other.isAM());
			else if(this.getHour() != other.getHour()) return Integer.compare(this.getHour(), other.getHour());	
			else if(this.getMinute() != other.getMinute()) return Integer.compare(this.getMinute(), other.getMinute());
			else return 0;
		} else if (other instanceof Onetime || this instanceof Onetime) {
			if (this.getYear() != other.getYear()) Integer.compare(this.getYear(), other.getYear());
			else if(this.getMonth() != other.getMonth()) return Integer.compare(this.getMonth(), other.getMonth());
			else if(this.isAM() != other.isAM()) return Boolean.compare(this.isAM(), other.isAM());
			else if(this.getDay() != other.getDay()) return Integer.compare(this.getDay(), other.getDay());	
			else if(this.getHour() != other.getHour()) return Integer.compare(this.getHour(), other.getHour());	
			else if(this.getMinute() != other.getMinute()) return Integer.compare(this.getMinute(),other.getMinute());
			else return 0;
		}
		return -5;
	}

	public String getTitle() {
		return title; 
	}

	public String getDescription() {
		return description;
	}

	public Calendar getAppointmentDate() {
		return appointmentDate;
	}

	public int getYear() {
		return appointmentDate.get(Calendar.YEAR);
	}

	public int getMonth() {
		return appointmentDate.get(Calendar.MONTH);
	}

	public int getDay() {
		return appointmentDate.get(Calendar.DAY_OF_MONTH);
	}

	public boolean isAM() {
		return appointmentDate.get(Calendar.AM_PM) == Calendar.AM;
	}

	public int getHour() {
		return appointmentDate.get(Calendar.HOUR);
	}

	public int getMinute() {
		return appointmentDate.get(Calendar.MINUTE);
	}

	public void setTitle(String newTitle) {
		title = newTitle;
	}

	public void setDescription(String newDescription) {
		description = newDescription;
	}

	public void displayAppointment() {
		System.out.format("%5tl:%tM %tp%n", appointmentDate, appointmentDate, appointmentDate);
		System.out.printf("        %s%n", title);        
		System.out.printf("        %s%n", description);

	}

	@Override
	public String toString() {
		String s1 = String.format("%5tl:%tM %tp%n", appointmentDate, appointmentDate, appointmentDate);
		String s2 = String.format("        %s%n", title);
		String s3 = String.format("        %s%n", description);
		return s1 + s2 + s3;
	}

	public static void main (String... args) {
		Appointment myApp = new Onetime("title", "description", 1, 1, 2017, 8, 0, true);
		System.out.println(myApp);
	}

	//TODO: Add setters for the year, month, day, hour, minute, AM/PM
}

