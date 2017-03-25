import java.util.*;
import java.time.*;

public class ChronoTimer {
	// system
	static final int MAX_CHANNELS = 1000;
	static boolean power;
	static boolean run;
	static String event = "";
	static boolean out;
	static Scanner console = new Scanner(System.in);
	static boolean[] channels = new boolean[MAX_CHANNELS];
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
	static String input;
	static String[] splitted;
	
	static int runCounter = 0;

	public ChronoTimer() {

		ChronoTimer.power = false;
		ChronoTimer.run = false;
		ChronoTimer.event = "";
		
		ChronoTimer.channels = new boolean[MAX_CHANNELS];
	}

	public static void main(String args[]) {
		
		channels = new boolean[MAX_CHANNELS];
		
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
			
			
			input = console.nextLine();
			splitted = input.split("\\s+");

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
			
			else if (splitted[0].equalsIgnoreCase("EVENT")) {
				
				event();
				
			}

			else if (splitted[0].equalsIgnoreCase("RESET") && power) {
				
				reset();
				System.out.println("Run has been reset\n");
			}
			
			else if (splitted[0].equalsIgnoreCase("NEWRUN") && power && !event.isEmpty() && !run) {
				
				run = true;
				runCounter++;
				System.out.println("New run initiated \n");
				System.out.println("'list' for lists of commands\n");
			}
			
			else if (splitted[0].equalsIgnoreCase("NUM") && power && !event.isEmpty() && run) {

				
				num();
			}
			
			else if (splitted[0].equalsIgnoreCase("START") && power && !event.isEmpty() && run && !racers.isEmpty()) {
				
				trigChannel(1);
				System.out.println("Race started \n");
			}

			else if (splitted[0].equalsIgnoreCase("FINISH") && power && !event.isEmpty() && run && !racers.isEmpty()) {
				
				trigChannel(2);
				System.out.println("Race finish \n");
			}
			
			else if (splitted[0].equalsIgnoreCase("TRIG") && power && !event.isEmpty() && run) {
				
				trigChannel(Integer.parseInt(splitted[1]));
				System.out.println("You have triggered " + splitted[1] + "\n");
			}
			
			else if (splitted[0].equalsIgnoreCase("TOG") && power && !event.isEmpty() && run) {
				
				togChannel(Integer.parseInt(splitted[1]));
				System.out.println("You have toggled " + splitted[1] + "\n");
			}
			
			else if (splitted[0].equalsIgnoreCase("PRINT") && power && !event.isEmpty() && run) {
				
				receipt();
			}
			else if (splitted[0].equalsIgnoreCase("PRINTlists")) {
				
				printlists();
			}
			
			else if (splitted[0].equalsIgnoreCase("ENDRUN")&& power && !event.isEmpty() && run) {
				
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
	
	static void num() {
		totRacers++;
		racers.add(new Racer(Integer.parseInt(splitted[1]), totRacers));
		System.out.println("Racer " + splitted[1] + " has been added");
	}
	
	static void event() {
		if(power){
			if(event.isEmpty()){
				if(splitted[1].equalsIgnoreCase("IND")){
					event = splitted[1];
					System.out.println("Individual Race has been Selected\n");
					System.out.println("Waiting for Newrun: Type Newrun\n");
				}else if(splitted[1].equalsIgnoreCase("PARIND")){
					event = splitted[1];
					System.out.println("Parallel Individual Race has been Selected\n");
					System.out.println("Waiting for Newrun: Type Newrun\n");
				}else{
					System.out.println("That is not a valid event type");
				}
			}else{
				System.out.println("There is already an event. Please reset before starting a new event.");
			}
		}else{
			System.out.println("Must turn on power");
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
		Converter.ConvertTo(completed, runCounter);
		run = false;
		racers.clear();
		toFinish.clear();
		completed.clear();
		totRacers = 0;
		runCounter = 0;
	}
	
	static void reset() {
		
		for (int i = 0; i < channels.length; i++) {
			channels[i] = false;
		}
		run = false;
		event = "";
		racers.clear();
		toFinish.clear();
		completed.clear();
		totRacers = 0;
		runCounter = 0;
	}
	
	static void togChannel(int input) {
		
		if (!channels[channel]) {
			channels[channel] = true;
		} else {
			channels[channel] = false;
		}
	}
	
	static void trigChannel(int channel) {

		// used for determining which list a racer is in
		boolean found = false;

		// list of elements to remove from list
		LinkedList<Racer> temp = new LinkedList<>();
		if (channel < MAX_CHANNELS && channel > 0) {
			if (event.equalsIgnoreCase("IND")) {
				// determine if start or finish channel
				// if start channel:
				if (channel % 2 == 1) {
					// if channel toggled on
					if (channels[channel] == true) {
						// first, check if racer already started. If so, update
						// his start time
						if (!toFinish.isEmpty()) {
							for (Racer s : toFinish) {
								if (s.index == Math.ceil((double) channel / 2)) {
									s.start = time.millis();
									found = true;
								}
							}
						}
						// If not, start the racer
						if (!racers.isEmpty() && !found) {
							for (Racer s : racers) {
								if (s.index == Math.ceil((double) channel / 2)) {
									temp.add(s);
									s.start = time.millis();
									toFinish.add(s);
									found = true;
								}
							}
							racers.removeAll(temp);
						}
						// if no racer found attached to channel
						if (!found) {
							System.out.println("No racer linked to that channel.");
						}
					} else {
						System.out.println("channel was not toggled");
					}

				}
				// if finish channel
				if (channel % 2 == 0) {
					if (channels[channel] == true) {
						// First, check if racer has already finished. If so,
						// update his finish time
						// If not, finish him
						if (!completed.isEmpty()) {

							for (Racer s : completed) {
								if (s.index == Math.ceil((double) channel / 2)) {
									s.fin = time.millis();
									found = true;
								}
							}
						}
						if (!toFinish.isEmpty() && !found) {
							for (Racer s : toFinish) {
								if (s.index == Math.ceil((double) channel / 2)) {
									temp.add(s);
									s.fin = time.millis();
									completed.add(s);
									found = true;
								}
							}
							toFinish.removeAll(temp);
						}
						// if no racer found attached to channel
						if (!found) {
							System.out.println("No racer linked to that channel.");
						}
					}

					else {
						System.out.println("channel was not toggled");
					}

				}
			} else if (event.equalsIgnoreCase("PARIND")) {
				// determine if start or finish channel
				// if start channel:
				if (channel % 2 == 1) {
					// if channel toggled on
					if (channels[channel] == true) {
						// first, check if racer already started. If so, update
						// his start time
						if (!toFinish.isEmpty()) {
							for (Racer s : toFinish) {
								// if racer is found
								if (s.index == Math.ceil((double) channel / 4)) {
									if (s.fin == 0) {
										s.start = time.millis();
									} else {
										s.start2 = time.millis();
									}
									found = true;
								}
							}
						}
						// If not, start the racer
						if (!racers.isEmpty() && !found) {
							for (Racer s : racers) {
								if (s.index == Math.ceil((double) channel / 4)) {
									temp.add(s);
									if (s.start == 0) {
										s.start = time.millis();
									} else {
										s.start2 = time.millis();
									}
									toFinish.add(s);
									found = true;
								}
							}
							racers.removeAll(temp);
						}
						// if no racer found attached to channel
						if (!found) {
							System.out.println("No racer linked to that channel.");
						}
					} else {
						System.out.println("channel was not toggled");
					}

				}
				// if finish channel
				if (channel % 2 == 0) {
					if (channels[channel] == true) {
						// First, check if racer has already finished. If so,
						// update his finish time
						// If not, finish him
						if (!completed.isEmpty()) {

							for (Racer s : completed) {
								if (s.index == Math.ceil((double) channel / 4)) {
									s.fin2 = time.millis();
									found = true;
								}
							}
						}
						if (!toFinish.isEmpty() && !found) {
							for (Racer s : toFinish) {
								if (s.index == Math.ceil((double) channel / 4)) {
									temp.add(s);
									if (s.fin == 0) {
										s.fin = time.millis();
										racers.add(s);
									} else {
										s.fin2 = time.millis();
										completed.add(s);
									}
									found = true;
								}
							}
							toFinish.removeAll(temp);
						}
						// if no racer found attached to channel
						if (!found) {
							System.out.println("No racer linked to that channel.");
						}
					}

					else {
						System.out.println("channel was not toggled");
					}

				}
			} else {
				System.out.println("Shouldn't be here, but invalid event");
			}
		}

		else {
			System.out.println("invalid channel number");
		}
	}	
	static void printlists() {
		if (event.equalsIgnoreCase("IND")) {
			System.out.println("racers: ");
			for (Racer s : racers) {
				System.out.print("      ");
				System.out.println("Racer " + s.racerNum + " start: " + stopWatch.formatTime(s.start) + " finish: "
						+ stopWatch.formatTime(s.fin));
			}

			System.out.println("toFinish: ");
			for (Racer s : toFinish) {
				System.out.print("      ");
				System.out.println("Racer " + s.racerNum + " start: " + stopWatch.formatTime(s.start) + " finish: "
						+ stopWatch.formatTime(s.fin));
			}

			System.out.println("completed: ");
			for (Racer s : completed) {
				System.out.print("      ");
				System.out.println("Racer " + s.racerNum + " start: " + stopWatch.formatTime(s.start) + " finish: "
						+ stopWatch.formatTime(s.fin));
			}
		}
		if (event.equalsIgnoreCase("PARIND")) {
			System.out.println("racers: ");
			for (Racer s : racers) {
				System.out.print("      ");
				System.out.println("Racer " + s.racerNum + " start: " + stopWatch.formatTime(s.start) + " finish: "
						+ stopWatch.formatTime(s.fin));
				System.out.println("		start: " + stopWatch.formatTime(s.start2) + " finish: "
						+ stopWatch.formatTime(s.fin2));
			}

			System.out.println("toFinish: ");
			for (Racer s : toFinish) {
				System.out.print("      ");
				System.out.println("Racer " + s.racerNum + " start: " + stopWatch.formatTime(s.start) + " finish: "
						+ stopWatch.formatTime(s.fin));
				System.out.println("		start: " + stopWatch.formatTime(s.start2) + " finish: "
						+ stopWatch.formatTime(s.fin2));
			}

			System.out.println("completed: ");
			for (Racer s : completed) {
				System.out.print("      ");
				System.out.println("Racer " + s.racerNum + " start: " + stopWatch.formatTime(s.start) + " finish: "
						+ stopWatch.formatTime(s.fin));
				System.out.println("		start: " + stopWatch.formatTime(s.start2) + " finish: "
						+ stopWatch.formatTime(s.fin2));
			}
		}
	}
}
