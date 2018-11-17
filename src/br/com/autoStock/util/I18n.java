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

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class I18n
{
	static Logger logger		= Logger.getLogger(I18n.class.getName());
		
	private static Locale locale = new Locale("pt","BR");
	private static ResourceBundle rb = ResourceBundle.getBundle("language", locale);
		
	public static void setLocale(String language) {
		
		if(language.equals("pt")){
			locale = new Locale("pt","BR");			
		}
		else{
			locale = new Locale("en","US");
		}		
		
		rb = ResourceBundle.getBundle("language", locale);
	}
	
	public static String getMessage(String key){
		
		return rb.getString(key);
	}
	
	public static String getLanguage(){
		
		return locale.getLanguage();
	}
	
}