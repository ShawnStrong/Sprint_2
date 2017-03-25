

import java.io.*;
import java.util.*;

public class Simulator {
	
Scanner console;
File actual;

Simulator() {
		
	this.console = new Scanner(System.in);
	this.actual = null;
}
	
	public void textReader() {
		
		boolean querySuccess = false;
		
		while (!(querySuccess)) {
			// Select either file or input
			System.out.println("\ntype 'file' to read from file or 'input' for manual inputs: " );
			String selection = console.next();
			
			if (selection.equalsIgnoreCase("file")) {
				
				boolean checkRun = true;
				querySuccess = true;
				
				while (checkRun) {
					
					//querySuccess = true;
					System.out.println("\nwhich file would you like to run?(enter 'run1' or 'run2') ");
					String input = console.next();
					
					if (input.equalsIgnoreCase("run1")) {
						
						actual = new File("src/CTS1RUN1.txt");
						checkRun = false;
					}
					
					else if(input.equalsIgnoreCase("run2")) {
						
						actual = new File("src/CTS1RUN2.txt");
						checkRun = false;
					}
					
					else {
						
						System.out.println("\nnot a valid file: enter either 'run1' or 'run2'");
					}
				}
				//a correct file has been selected and will now be read
				try (BufferedReader br = new BufferedReader(new FileReader(actual))){

					String[] split = { "", "" };
					String line = br.readLine();
					
					while (line != null) {
							
						split = line.split("\\s+");
						System.out.print(split[1]);
						
						if(split.length > 2){
							System.out.println(" "+split[2]);
						}
							
						if (split[1].equalsIgnoreCase("POWER")) {
							
							ChronoTimer.setPower();
						}
							
						else if (split[1].equalsIgnoreCase("EXIT")) {
							
							System.out.println("\nExiting program, goodbye ");
							System.exit(0);
						}
						
						else if (split[1].equalsIgnoreCase("EVENT")) {
							
							if (split[2].equalsIgnoreCase("IND")) {
								System.out.println("Event is set to Individual Race \n");
							}
						}
						
						else if (split[1].equalsIgnoreCase("NEWRUN")) {
							
							ChronoTimer.run = true;
							System.out.println("\nNew run initiated\n");
						}
						
						else if (split[1].equalsIgnoreCase("ENDRUN")) {
							
							ChronoTimer.endrun();
							System.out.println("\nRun ended\n");
						}
						
						else if (split[1].equalsIgnoreCase("TIME")) {
							
							ChronoTimer.stopWatch.setTime(split[2]);	
							System.out.println("You set the time at: " + split[2] + "\n");
						}
						
						else if (split[1].equalsIgnoreCase("NUM")) {
							
							
							ChronoTimer.racers.add(new Racer(Integer.parseInt(split[2]), ChronoTimer.totRacers));
							ChronoTimer.totRacers++;
							System.out.println("Racer: " + split[2] + " has been entered!\n");
						}
						
						else if (split[1].equalsIgnoreCase("TOG")) {
							
							ChronoTimer.togChannel(Integer.parseInt(split[2]));
							System.out.println("You just toggled: " + split[2] + "\n");
						}
						
						else if (split[1].equalsIgnoreCase("TRIG")) {
							
							System.out.println("You just triggered: " + split[2] + "\n");
							ChronoTimer.trigChannel(Integer.parseInt(split[2]));
						}
						
						else if (split[1].equalsIgnoreCase("PRINT")){
							ChronoTimer.receipt();
						}
						// Text files never call these buttons. 
						
						//else if (split[1].equalsIgnoreCase("RESET")) {Time.reset();}
						//else if (split[1].equalsIgnoreCase("DNF")) {}
						//else if (split[1].equalsIgnoreCase("CANCEL")) {}
						//else if (split[1].equalsIgnoreCase("START")) {}
						//else if (split[1].equalsIgnoreCase("FINISH")) {}
						
						line = br.readLine();
						split = line.split("\\s+");
						
					}//end while
				} //end try
				catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			
			else if(selection.equalsIgnoreCase("input")) {
				
				querySuccess = true;
				ChronoTimer.getInput();
			}
			
			else {
				
				System.out.println("not a valid input");
			}	
		}	
	}
}
