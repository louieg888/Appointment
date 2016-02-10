public class Daily extends Appointment {
	
	public Daily(String title, String description, int hour, int minute, boolean isAM) {
		super(title, description, hour, minute, isAM);
	}

	@Override
	public boolean occursOn(int year, int month, int day) {
		return true;
	}
}
