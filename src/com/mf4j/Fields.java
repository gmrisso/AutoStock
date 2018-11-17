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


public class Fields {
	private boolean timeField;
	private boolean openInterest;
	private boolean openPrice;
	private boolean highField;
	private boolean lowField;
	
	public Fields(byte field) {
		if ((field & 0x80) == 0x80) {
			timeField = true;
		} else {
			timeField = false;
		}
		
		if ((field & 0x40) == 0x40) {
			openInterest = true;
		} else {
			openInterest = false;
		}
		
		if ((field & 0x20) == 0x20) {
			openPrice = true;
		} else {
			openPrice = false;
		}
		
		if ((field & 0x10) == 0x10) {
			highField = true;
		} else {
			highField = false;
		}
		
		if ((field & 0x8) == 0x8) {
			lowField = true;
		} else {
			lowField = false;
		}
	}
	
	/**
	 * @return the timeField
	 */
	public boolean isTimeField() {
		return timeField;
	}
	
	/**
	 * @param timeField the timeField to set
	 */
	public void setTimeField(boolean timeField) {
		this.timeField = timeField;
	}
	
	/**
	 * @return the openInterest
	 */
	public boolean isOpenInterest() {
		return openInterest;
	}
	
	/**
	 * @param openInterest the openInterest to set
	 */
	public void setOpenInterest(boolean openInterest) {
		this.openInterest = openInterest;
	}
	
	/**
	 * @return the openPrice
	 */
	public boolean isOpenPrice() {
		return openPrice;
	}
	
	/**
	 * @param openPrice the openPrice to set
	 */
	public void setOpenPrice(boolean openPrice) {
		this.openPrice = openPrice;
	}
	
	/**
	 * @return the highField
	 */
	public boolean isHighField() {
		return highField;
	}
	
	/**
	 * @param highField the highField to set
	 */
	public void setHighField(boolean highField) {
		this.highField = highField;
	}
	
	/**
	 * @return the lowField
	 */
	public boolean isLowField() {
		return lowField;
	}
	
	/**
	 * @param lowField the lowField to set
	 */
	public void setLowField(boolean lowField) {
		this.lowField = lowField;
	}
}
