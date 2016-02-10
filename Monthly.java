public class Monthly extends Appointment {
		
	public Monthly(String title, String description, int day, int hour, int minute, boolean isAM) {
		super(title, description, day, hour, minute, isAM);
	}

	//TODO: add a new constructor to the Appointment class

	@Override
	public boolean occursOn(int year, int month, int day) {
		if (getDay() == day) return true;
		return false;
	}
}
