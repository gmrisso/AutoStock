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

import java.util.Date;


public class Quote {
	private static int decimalPrecision		= 4;
	
	private String codeStock;
	private String stockName;

	private Date date;
	private float time;
	private float high;
	private float low;
	private float open;
	private float close;
	private long openInterest;
	private long volume;

	public Quote(String codeStock, String stockName, Date date, float time, float high, float low,
			float open, float close, long openInterest, long volume) {
		int power = decimalPrecision;
		float f = (float)Math.pow(10.0, power);
		this.codeStock = codeStock;
		this.stockName = stockName;
		this.date = date;
		this.time = time;
		this.high = Math.round(high*f)/f;
		this.low = Math.round(low*f)/f;
		this.open = Math.round(open*f)/f;
		this.close = Math.round(close*f)/f;
		this.openInterest = openInterest;
		this.volume = volume;
	}

	public Quote(String codeStock, String stockName, Date date, float high, float low,
			float open, float close, long openInterest, long volume) {
		int power = decimalPrecision;
		float f = (float)Math.pow(10.0, power);
		this.codeStock = codeStock;
		this.stockName = stockName;
		this.date = date;
		this.high = Math.round(high*f)/f;
		this.low = Math.round(low*f)/f;
		this.open = Math.round(open*f)/f;
		this.close = Math.round(close*f)/f;
		this.openInterest = openInterest;
		this.volume = volume;
	}	

	public String getCodeStock() {
		return codeStock;
	}

	public void setCodeStock(String codeStock) {
		this.codeStock = codeStock;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	/**
	 * @return the quote date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the quote date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the time
	 */
	public float getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(float time) {
		this.time = time;
	}

	/**
	 * @return the high price
	 */
	public float getHigh() {
		return high;
	}

	/**
	 * @param high the high price to set
	 */
	public void setHigh(float high) {
		this.high = high;
	}

	/**
	 * @return the low price
	 */
	public float getLow() {
		return low;
	}

	/**
	 * @param low the low price to set
	 */
	public void setLow(float low) {
		this.low = low;
	}

	/**
	 * @return the open price
	 */
	public float getOpen() {
		return open;
	}

	/**
	 * @param open the open price to set
	 */
	public void setOpen(float open) {
		this.open = open;
	}

	/**
	 * @return the close price
	 */
	public float getClose() {
		return close;
	}

	/**
	 * @param close the close price to set
	 */
	public void setClose(float close) {
		this.close = close;
	}

	/**
	 * @return the open interest price
	 */
	public long getOpenInterest() {
		return openInterest;
	}

	/**
	 * @param openInterest the open interest price to set
	 */
	public void setOpenInterest(long openInterest) {
		this.openInterest = openInterest;
	}

	/**
	 * @return the volume
	 */
	public long getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(long volume) {
		this.volume = volume;
	}

	/**
	 * @return the decimalPrecision
	 */
	public static int getDecimalPrecision() {
		return decimalPrecision;
	}

	/**
	 * @param decimalPrecision the decimalPrecision to set
	 */
	public static void setDecimalPrecision(int decimalPrecision) {
		Quote.decimalPrecision = decimalPrecision;
	}
}
