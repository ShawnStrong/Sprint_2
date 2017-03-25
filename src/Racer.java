public class Racer {
	public int racerNum;
	public int totalRacers;
	public long start;
	public long fin;
	public long start2;
	public long fin2;
	public int index;
	public int x;
	public int y;
	
	public Racer (int rNum, int channel) {
		
		this.racerNum = rNum;
		
		// stores the channel to be turned on
		this.x = totalRacers * 2 - 1;
		this.y = totalRacers * 2;
		this.start = 0;
		this.fin = 0;
		this.start2 = 0;
		this.fin2 = 0;
		this.index = channel;
	}

}
