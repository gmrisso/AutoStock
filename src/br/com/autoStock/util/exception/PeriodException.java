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

package br.com.autoStock.util.exception;

import javax.swing.JOptionPane;

import br.com.autoStock.util.I18n;

public class PeriodException extends Exception {
		
	private static final long serialVersionUID = 1L;
	   
	   public PeriodException() {
		   JOptionPane.showMessageDialog(null, I18n.getMessage("bf"),"",0);
	   }
	   
	   public String toString(){
	      return I18n.getMessage("bg");
	   }

}
