import java.time.Clock;
import java.time.LocalTime;

public class Time {
	static Clock time;
	static LocalTime time2;
	
	public Time()
	{
		time = Clock.systemDefaultZone();
		//time = Clock.systemUTC();
		
	}
	
	public double getRacerTime()
	{
		return time.millis();
	}
	public LocalTime getSystemTime()
	{
		return LocalTime.now();
	}
	public double calcTime(long start, long fin)
	{
		return (fin-start) / 100;
		//System.out.println(LocalTime.now());
		
	}
}