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
package com.mf4j;

import org.apache.log4j.Logger;

import com.mf4j.util.MFUtils;

@SuppressWarnings("serial")
public class ConvertException extends Exception {

	static Logger logger		= Logger.getLogger(ConvertException.class.getName());

	public ConvertException(String message) {
		super(message);
		logger.debug(MFUtils.stackTrace2String(this));
	}
}
