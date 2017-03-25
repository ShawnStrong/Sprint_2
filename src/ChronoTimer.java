import java.util.*;
import java.time.*;

public class ChronoTimer {
	// system
	static boolean power;
	static boolean run;
	static boolean event;
	static boolean out;
	static Scanner console = new Scanner(System.in);
	static Channel[] channels = new Channel[4];
	// race
	static int totRacers;
	//static Queue<Racer> racers = new LinkedList<Racer>();
	static LinkedList<Racer> racers = new LinkedList<Racer>();
	static LinkedList<Racer> toFinish = new LinkedList<Racer>();
	static LinkedList<Racer> completed = new LinkedList<Racer>();
	// time
	static Clock time;
	static LocalTime time2;
	static double start;
	static Time stopWatch;

	public ChronoTimer() {

		ChronoTimer.power = false;
		ChronoTimer.run = false;
		ChronoTimer.event = false;
		
		for(int i = 0; i < 4; i++) {
			
			channels[i] = new Channel();
		}
	}

	public static void main(String args[]) {
		
		for (int i = 0; i < 4; i++) {
			
			channels[i] = new Channel();
		}
		
		stopWatch = new Time();
		Simulator Chrono = new Simulator();
		time = Clock.systemUTC();
		start = time.millis();
		Chrono.textReader();
	}

	static void getInput() {

		boolean runConsole = true;

		while (runConsole) {

			System.out.println("Enter a new command \n");
			
			if(!power) {
				System.out.println("POWER to turn on");
			}
			
			String input;
			input = console.nextLine();
			String[] splitted = input.split("\\s+");

			if (splitted[0].equalsIgnoreCase("POWER")) {
				/////// adding power call, terminates if power called ????
				/////// change???
				if (power == false) {
					
					power = true;
					System.out.println("Welcome to the Chronotimer\n");
					System.out.println("Waiting for Event command: Type 'Event'\n");
				} 
				
				else if (power == true) {
					
					power = false;
					System.out.println("Shutting off ChronoTimer");
				}
			}

			else if (splitted[0].equalsIgnoreCase("EXIT")) {
				
				System.exit(0);
				System.out.println("Exiting program, goodbye \n");
			}
			
			else if (splitted[0].equalsIgnoreCase("EVENT") && power) {
				
				event = true;
				System.out.println("Individual Race has been Selected\n");
				System.out.println("Waiting for Newrun: Type Newrun\n");
			}

			else if (splitted[0].equalsIgnoreCase("RESET") && power) {
				
				reset();
				System.out.println("Run has been reset\n");
			}
			
			else if (splitted[0].equalsIgnoreCase("NEWRUN") && power && event && !run) {
				
				run = true;
				System.out.println("New run initiated \n");
				System.out.println("'list' for lists of commands\n");
			}
			
			else if (splitted[0].equalsIgnoreCase("NUM") && power && event && run) {

				
				racers.add(new Racer(Integer.parseInt(splitted[1]), totRacers));
				totRacers++;
				System.out.println("Racer " + splitted[1] + " has been added");
			}
			
			else if (splitted[0].equalsIgnoreCase("START") && power && event && run && !racers.isEmpty()) {
				
				trigChannel(1);
				System.out.println("Race started \n");
			}

			else if (splitted[0].equalsIgnoreCase("FINISH") && power && event && run && !racers.isEmpty()) {
				
				trigChannel(2);
				System.out.println("Race finish \n");
			}
			
			else if (splitted[0].equalsIgnoreCase("TRIG") && power && event && run) {
				
				trigChannel(Integer.parseInt(splitted[1]));
				System.out.println("You have triggered " + splitted[1] + "\n");
			}
			
			else if (splitted[0].equalsIgnoreCase("TOG") && power && event && run) {
				
				togChannel(Integer.parseInt(splitted[1]));
				System.out.println("You have toggled " + splitted[1] + "\n");
			}
			
			else if (splitted[0].equalsIgnoreCase("PRINT") && power && event && run) {
				
				receipt();
			}
			else if (splitted[0].equalsIgnoreCase("PRINTlists")) {
				
				printlists();
			}
			
			else if (splitted[0].equalsIgnoreCase("ENDRUN")&& power && event && run) {
				
				endrun();
				System.out.println("Run ended \n");
			}
			
			else if (splitted[0].equalsIgnoreCase("TIME")) {

				stopWatch.setTime(splitted[1]);
				System.out.println("You set the time at: " + splitted[1] + "\n");
			}
		
			else if (splitted[0].equalsIgnoreCase("DNF") && !toFinish.isEmpty()) {
				// Remove first racer from toFinish, set his finish time to -1, then add him to the completed list
				Racer temp = toFinish.removeFirst();
				System.out.println("Racer "+temp.racerNum+" did not finish.");
				temp.fin = -1;
				completed.add(temp);
				System.out.println("Run disqualified \n");
			}

			else if (splitted[0].equalsIgnoreCase("CANCEL") && !toFinish.isEmpty()) {
				
				// Remove first racer from toFinish, set his start time to 0, then add him to racers
				Racer temp = toFinish.removeFirst();
				System.out.println("Racer "+temp.racerNum+" moved back to start.");
				temp.start = 0;
				racers.addFirst(temp); 
				System.out.println("Run cancel \n");
			}
			
			else if (splitted[0].equalsIgnoreCase("LIST")) {

					System.out.println("POWER: on/off \n" 
						+ "EXIT: exits program \n" 
						+ "RESET: reset all run times and settings \n"
						+ "TIME: sets current time\n" 
						+ "DNF: end that racers run, with no finish time\n" 
						+ "NUM + (ID): to create racer\n"
						+ "EVENT: to choose event\n"
						+ "NEWRUN: start a new run\n"
						+ "ENDRUN: end current run\n"
						+ "START: activate trigger\n"
						+ "FINISH: deactivate trigger\n"
						+ "TRIG + (#): trigger to activate\n"
						+ "TOG + (#): toggle to activate\n"
						+ "PRINT: print results\n"
						+ "CANCEL: discard current race\n"
						+ "...................... \n");
			}

			else {
				System.out.println("Not a valid command, type 'LIST' to list commands: \n");
			}
		}
	} // end while

	static void receipt() {
		// Cycles through the completed linkedlist and prints out the racer's number and time
		for(int i = 0; i < completed.size(); i++) {
			
			Racer temp = completed.get(i);
			System.out.println("\nRacer " + temp.racerNum + " time: " + stopWatch.formatTime((temp.fin - temp.start))+ "\n");
		}
	}
	
	static void startTime() {
		
		start = time.millis();
	}

	static double finishTime() {
		
		return (time.millis() - start) / 100;
		// System.out.println(LocalTime.now());
	}
	// returns the current value/state of power 
	static boolean getPower() {

		return power;
	}
	// sets the power to be its opposite value
	static void setPower() {

		power = !power;	
		
		if (power == true) {
			
			System.out.println("\nThe power is on\n");
		}
		
		else {
			System.out.println("\nThe power is off\n");
		}
	}
	
	static void endrun(){
		run = false;
		racers.clear();
		toFinish.clear();
		completed.clear();
		totRacers = 0;
	}
	
	static void reset() {
		
		channels[0].top = false;
		channels[0].bottom = false;
		channels[1].top = false;
		channels[1].bottom = false;
		channels[2].top = false;
		channels[2].bottom = false;
		channels[3].top = false;
		channels[3].bottom = false;
		run = false;
		event = false;
		racers.clear();
		toFinish.clear();
		completed.clear();
		totRacers = 0;
	}
	
	static void togChannel(int input) {
		
		int channel = (int) Math.ceil((double) input / 2) - 1;
		
		if (input % 2 == 0){
			
			if (!channels[channel].bottom) {
				channels[channel].bottom = true;
			}
			
			else {
				channels[channel].bottom = false;
			}
		}
		
		else {
			
			if (!channels[channel].top) {
				
				channels[channel].top = true;
			}
			
			else {
				
				channels[channel].top = false;
			}
		}
	}
	
	static void trigChannel(int parseInt) {
		// convert parseInt (0-3) to channel (1-8)
		int channel = (int) Math.ceil((double) parseInt / 2) - 1;
		
		// used for determining which list a racer is in
		boolean found = false;
		
		// list of elements to remove from list
		LinkedList<Racer> temp = new LinkedList<>();
		
		if (channel < 3 && channel >= 0) {
			// determine if start or finish channel
			if (parseInt % 2 == 1) {
				
				if (channels[channel].top == true) {
					// first, check if racer already started. If so, update his start time
					if(!toFinish.isEmpty()){
						for(Racer s : toFinish){
							if(s.index == channel){
								s.start = time.millis();
								found = true;
							}
						}
					}
					// If not, start the racer
					if (!racers.isEmpty() && !found) {
						for(Racer s : racers){
							if(s.index == channel){
								temp.add(s);
								s.start = time.millis();
								toFinish.add(s);
								found = true;
							}
						}
						racers.removeAll(temp);
					} 
					// if no racer found attached to channel
					if(!found){
						System.out.println("No racer linked to that channel.");
					}
				}
				else {
					System.out.println("channel was not toggled");
				}

			}
			// determine if finish channel
			if (parseInt % 2 == 0) {
				if (channels[channel].bottom == true) {
					// First, check if racer has already finished. If so, update his finish time
					// If not, finish him
					if (!completed.isEmpty()) {
						
						for(Racer s : completed){
							if(s.index == channel){
								s.fin = time.millis();
								found = true;
							}
						}
					}
					if(!toFinish.isEmpty() && !found){
						for(Racer s : toFinish){
							if(s.index == channel){
								temp.add(s);
								s.fin = time.millis();
								completed.add(s);
								found = true;
							}
						}
						toFinish.removeAll(temp);
					}
					// if no racer found attached to channel
					if(!found){
						System.out.println("No racer linked to that channel.");
					}
			} 
					
			else {
					System.out.println("channel was not toggled");
				}
				
			}
		}
		
		else {
			System.out.println("invlaid channel number");
		}
	}	
	static void printlists(){
		
		System.out.println("racers: ");
		for(Racer s : racers){
			System.out.print("      ");
			System.out.println("Racer " + s.racerNum + " start: " + stopWatch.formatTime(s.start) + " finish: " + stopWatch.formatTime(s.fin));
		}
		
		System.out.println("toFinish: ");
		for(Racer s : toFinish){
			System.out.print("      ");
			System.out.println("Racer " + s.racerNum + " start: " + stopWatch.formatTime(s.start) + " finish: " + stopWatch.formatTime(s.fin));
		}
		
		System.out.println("completed: ");
		for(Racer s : completed){
			System.out.print("      ");
			System.out.println("Racer " + s.racerNum + " start: " + stopWatch.formatTime(s.start) + " finish: " + stopWatch.formatTime(s.fin));
		}
	}
}
