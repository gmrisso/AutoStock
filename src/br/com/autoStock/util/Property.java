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

package br.com.autoStock.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import com.mf4j.util.MFUtils;

/* OK */
public class Property {
	
	static Logger logger		= Logger.getLogger(Property.class.getName());
	
	private static Properties config;	
	
    public static void loadProperties() {
    	
    	try {
			FileInputStream fis = new FileInputStream("properties.lst");
			ObjectInputStream ois;
			ois = new ObjectInputStream(fis);
			config = (Properties) ois.readObject();			
	        ois.close();
	        
	        I18n.setLocale(config.getLanguage());
	        
		} catch (Exception e) {
			
			config = new Properties();
			
			saveProperties();
			
			JOptionPane.showMessageDialog(null, I18n.getMessage("ba"));
		} 	
    }
   
    public static String getProperty(char property) {
    	
    	switch(property){
    	case 'S':
    		return config.getStrategy();
		case 'I':
    		return new Float(config.getInterval()).toString();
    	case 'D':
			return config.getDatabaseDir();
    	case 'T':
    		return config.isSoldTrade()?"true":"false";
    	case 'G':
    		return config.getLogDir();
    	case 'L':
    		return config.getLanguage();
    	}
    	
    	return "";
    }
   
    public static void setProperties(Properties conf) { 
    	config = conf;
    	saveProperties();
    }
    
    public static Properties getProperties() { 
    	return config;
    }
    
	public static void saveProperties() {
		
		logger.debug(I18n.getMessage("bb"));
        
        try {
            FileOutputStream fos = new FileOutputStream("properties.lst");
        
            ObjectOutputStream oos = new ObjectOutputStream(fos);
        
            oos.writeObject(config);
        
            oos.close();
            
        }
        catch(FileNotFoundException e) {
        	logger.error(I18n.getMessage("bc"));
        	logger.error(MFUtils.stackTrace2String(e));
        }
        catch(IOException e) {
        	logger.error(I18n.getMessage("bd"));
        	logger.error(MFUtils.stackTrace2String(e));
        }
    }	

}
