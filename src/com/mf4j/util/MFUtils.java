/**
*
* Copyright (C) 2010  Jarungwit J. All Rights Reserved.
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
*/
package com.mf4j.util;

import java.util.Calendar;
import java.util.Date;


public class MFUtils {
	/**
	 * transform ex.getStackTrace() to string for using in log4j
	 * @param ex
	 * @return
	 */
	public static String stackTrace2String(Exception ex) {
		StackTraceElement[] stackTraceElements = ex.getStackTrace();
		StringBuffer buffer = new StringBuffer();
		buffer.append(ex.getMessage());
		buffer.append(System.getProperty("line.separator"));
		for (int i=0; i<stackTraceElements.length; i++) {
			buffer.append("\tat ");
			buffer.append(stackTraceElements[i]);
			buffer.append(System.getProperty("line.separator"));
		}
		return buffer.toString();
	}

	/**
	 *
	 * @param year
	 * @param month 0=Jan, ... , 11=Dec
	 * @param day
	 * @return
	 */
	public static Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
}
