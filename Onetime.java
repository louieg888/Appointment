public class Onetime extends Appointment {
		
	public Onetime(String title, String description, int day, int month, int year, int hour, int minute, boolean isAM) {
		super(title, description, day, month, year, hour, minute, isAM);
	}

	@Override
	public boolean occursOn(int year, int month, int day) {
		if (getYear() == year && getMonth() == month && getDay() == day) return true;
		return false;
	}
}
