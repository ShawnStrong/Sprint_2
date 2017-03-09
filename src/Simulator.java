
import java.io.*;
import java.util.*;

public class Simulator {
Scanner console;
File actual;
	Simulator()
	{
		this.console = new Scanner(System.in);
		this.actual = null;
		
	}
	
	public void textReader()
	{
		boolean querySuccess = false;
		
		while(!(querySuccess))
		{
			System.out.println("type 'file' to read from file or 'input' for manual inputs: \n" );
			String selection = console.next();
			
			if(selection.equalsIgnoreCase("file"))
			{
				boolean checkRun = true;
				querySuccess = true;
				while(checkRun)
				{
					querySuccess = true;
					System.out.println("which file would you like to run?(enter 'run1' or 'run2')");
					String run = console.next();
					if(run.equalsIgnoreCase("run1"))
					{
						actual = new File("src/run1.txt");
						checkRun = false;
					}
					else if(run.equalsIgnoreCase("run2"))
					{
						actual = new File("src/run2.txt");
						checkRun = false;
					}
					else
					{
						System.out.println("not a valid file: enter either 'run1' or 'run2'");
					}
				}
				//a correct file has been selected and will now be read
				try (BufferedReader br = new BufferedReader(new FileReader(actual)))
				{

					String[] splited = { "", "" };
					int acc_num = 0;
					String line = br.readLine();
					
					while (line != null) 
					{
						
						
							
						splited = line.split("\\s+");
						System.out.println(line);
							
						if (splited[0].equalsIgnoreCase("POWER"))
						{
/////// adding power call, terminates if power called ???? change???
							System.exit(0);
						}
							
							
						else if(splited[0].equalsIgnoreCase("EXIT"))
						{
							
							
						}
						else if(splited[0].equalsIgnoreCase("TIME"))
						{
							
							
						}
						else if(splited[0].equalsIgnoreCase("RESET"))
						{
							
						}
						else if(splited[0].equalsIgnoreCase("DNF"))
						{
							
						}
						else if(splited[0].equalsIgnoreCase("CANCEL"))
						{
							
						}
						else if(splited[0].equalsIgnoreCase("TOG"))
						{
							
						}
						else if(splited[0].equalsIgnoreCase("TRIG"))
						{
							
						}
						else if(splited[0].equalsIgnoreCase("START"))
						{
							
						}
						else if(splited[0].equalsIgnoreCase("FINISH"))
						{
							
						}
						
					
					}//end while
					
				} //end try
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			else if(selection.equalsIgnoreCase("input"))
			{
				querySuccess = true;
				ChronoTimer.getInput();
			}
			else
			{
				System.out.println("not a valid input");
			}
			
		}
		
		
	}

}
