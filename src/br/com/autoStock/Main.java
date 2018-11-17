/**
*
* Copyright (C) 2011  Gilnei Marcos Risso All Rights Reserved.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>
* 
* You can contact the software author by e-mail gilnei.risso@gmail.com
*
*/

package br.com.autoStock;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import br.com.autoStock.util.I18n;
import br.com.autoStock.util.Property;

public class Main {
	
	static Logger logger = Logger.getLogger(Main.class.getName());

	public Main()
	{			
		Property.loadProperties();
		
		logger.debug(I18n.getMessage("aa"));
		
		PrintStream fileStream 	= null;
		try
		{			
			fileStream = new PrintStream(new FileOutputStream(Property.getProperty('G'),true));			
			//System.setOut(fileStream);			
			//System.setErr(fileStream); 
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		
		new Principal();		
				
	}
	
	public static void main(String args[]) throws Exception
	{		   
		new Main();
	}
}
