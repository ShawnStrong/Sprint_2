public class Racer {
	public int racerNum;
	public int totalRacers;
	public int start;
	public int fin;
	public int index;
	public int x;
	public int y;
	
	public Racer (int rNum, int tRacers) {
		
		this.racerNum = rNum;
		
		// stores the channel to be turned on
		this.x = totalRacers * 2 - 1;
		this.y = totalRacers * 2;
		this.start = 0;
		this.fin = 0;
		this.index = tRacers;
	}

}