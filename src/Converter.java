import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Converter {
	Gson g = new Gson();
	
	void ConvertTo(LinkedList k, int r)
	{
		String out = "";
		out = g.toJson(k);
		try{
		PrintWriter file = new PrintWriter("run" + r + ".txt", "UTF-8");
		} 
		catch (IOException e) 
		{
			   System.out.println("convert to json failed");
		}
	}
	Object ConvertFrom()
	{
		
		return null;
	}
}
