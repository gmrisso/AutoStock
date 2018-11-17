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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.mf4j.util.ConvertUtils;
import com.mf4j.util.MFUtils;


public class MetaFile {

	static Logger logger		= Logger.getLogger(MetaFile.class.getName());

	public static final int TIME_PERIOD_IDA		= 1;
	public static final int TIME_PERIOD_DAY		= 2;
	public static final int TIME_PERIOD_WEEK	= 3;
	public static final int TIME_PERIOD_MONTH	= 4;
	public static final int TIME_PERIOD_QUARTER	= 5;
	public static final int TIME_PERIOD_YEAR	= 6;

	private String fileName			= "";
	private String name				= "";
	private String symbol			= "";
	private int quoteCount			= -1;
	private Date firstDate;
	private Date lastDate;
	private int fileNumber;
	private ArrayList<Quote> quotes;

	private int recordLength		= 0;
	private int recordCount			= -1;
	private int timePeriod;
	private Fields fields;

	/**
	 * create a new instance from file master and emaster
	 * @param fileName
	 * @param masterFile
	 * @param emasterFile
	 */
	public MetaFile(String fileName, MasterFile masterFile, EMasterFile emasterFile) {
		this.fileName = fileName;
		this.name = emasterFile.getIssueName();
		this.symbol = masterFile.getSymbol();
		this.firstDate = masterFile.getFirstDate();
		this.lastDate = masterFile.getLastDate();
		this.recordLength = masterFile.getRecordLength();
		this.recordCount = masterFile.getRecordCount();
		this.fileNumber = masterFile.getFileNumber();
		switch (masterFile.getTimePeriod().charAt(0)) {
			case 'I':
				this.timePeriod = TIME_PERIOD_IDA;
				break;
			case 'W':
				this.timePeriod = TIME_PERIOD_WEEK;
				break;
			case 'Q':
				this.timePeriod = TIME_PERIOD_QUARTER;
				break;
			case 'D':
				this.timePeriod = TIME_PERIOD_DAY;
				break;
			case 'M':
				this.timePeriod = TIME_PERIOD_MONTH;
				break;
			case 'Y':
				this.timePeriod = TIME_PERIOD_YEAR;
				break;
		}
		this.fields = emasterFile.getEmFields();
	}

	/**
	 * create a new instance from file xmaster
	 * @param fileName
	 * @param xmasterFile
	 */
	public MetaFile(String fileName, XMasterFile xmasterFile) {
		this.fileName = fileName;
		this.name = xmasterFile.getIssueName();
		this.symbol = xmasterFile.getSymbol();
		this.firstDate = xmasterFile.getFirstDate();
		this.lastDate = xmasterFile.getLastDate();
		this.recordLength = xmasterFile.getRecordLength();
		this.recordCount = xmasterFile.getRecordCount();
		this.fileNumber = xmasterFile.getFileNumber();
		switch (xmasterFile.getTimePeriod().charAt(0)) {
			case 'I':
				this.timePeriod = TIME_PERIOD_IDA;
				break;
			case 'W':
				this.timePeriod = TIME_PERIOD_WEEK;
				break;
			case 'Q':
				this.timePeriod = TIME_PERIOD_QUARTER;
				break;
			case 'D':
				this.timePeriod = TIME_PERIOD_DAY;
				break;
			case 'M':
				this.timePeriod = TIME_PERIOD_MONTH;
				break;
			case 'Y':
				this.timePeriod = TIME_PERIOD_YEAR;
				break;
		}
		this.fields = xmasterFile.getXmFields();
	}

	/**
	 * load quotes
	 */
	public void loadQuotes() throws Exception {
		try {
			// read quote from file
			byte[] buffer = new byte[(int) new File(fileName).length()];
			BufferedInputStream f = new BufferedInputStream(new FileInputStream(fileName));
			f.read(buffer);
			f.close();

			quoteCount = (buffer.length/recordLength)-1;
			float[] data = new float[Math.min(recordCount, 8)];
			if (quoteCount > 0) {
				quotes = new ArrayList<Quote>();
				for (int i=recordLength; i<=buffer.length-1; i+=recordLength) {
					for (int c=0; c<=recordCount-1; c++) {
						data[c] = ConvertUtils.msbByte2Float(buffer, i+(c*4));
					}
					for (int c=recordCount; c<=data.length-1; c++) {
						data[c] = 0.0f;
					}

					if (this.timePeriod == TIME_PERIOD_DAY || this.timePeriod == TIME_PERIOD_WEEK) {
						if (this.fields.isHighField() && this.fields.isLowField() &&
								this.fields.isOpenInterest() && this.fields.isOpenPrice()) {
							quotes.add(new Quote(getSymbol(), getName(), ConvertUtils.float2Date(data[0]), data[2], data[3], data[1], data[4], (long)data[6], (long)data[5]));
						} else if (this.fields.isHighField() && this.fields.isLowField() &&
								!this.fields.isOpenInterest() && this.fields.isOpenPrice()) {
							quotes.add(new Quote(getSymbol(), getName(), ConvertUtils.float2Date(data[0]), data[2], data[3], data[1], data[4], 0, (long)data[5]));
						} else if (this.fields.isHighField() && this.fields.isLowField() &&
								!this.fields.isOpenInterest() && !this.fields.isOpenPrice()) {
							quotes.add(new Quote(getSymbol(), getName(), ConvertUtils.float2Date(data[0]), data[1], data[2], 0, data[3], 0, (long)data[4]));
						}
					} else if (this.timePeriod == TIME_PERIOD_IDA) {
						// TODO intraday
						throw new UnsupportedMetaFileException("NOT SUPPORT INTRADAY;[symbol="+symbol+", file="+fileName+", offset="+i+", recordLength="+recordLength);
					} else {
						throw new UnsupportedMetaFileException("NOT SUPPORT TIMEFRAME;[symbol="+symbol+", file="+fileName+", timePeriod="+timePeriod);
					}
				}
			}
		}
		catch (FileNotFoundException e) {
			logger.error(MFUtils.stackTrace2String(e));
		}
		catch (IOException e) {
			logger.error(MFUtils.stackTrace2String(e));
		}
		catch (ConvertException e) {
			logger.error(MFUtils.stackTrace2String(e));
		}
	}
	
	/**
	 * load quotes
	 */
	@SuppressWarnings("deprecation")
	public void loadQuotes(Date dtStart, Date dtEnd) throws Exception {
		try {	
			dtEnd.setHours(23);
			dtEnd.setMinutes(59);
			
			// read quote from file
			byte[] buffer = new byte[(int) new File(fileName).length()];
			BufferedInputStream f = new BufferedInputStream(new FileInputStream(fileName));
			f.read(buffer);
			f.close();

			quoteCount = (buffer.length/recordLength)-1;
			float[] data = new float[Math.min(recordCount, 8)];
			if (quoteCount > 0) {
				quotes = new ArrayList<Quote>();
				for (int i=recordLength; i<=buffer.length-1; i+=recordLength) {
					for (int c=0; c<=recordCount-1; c++) {
						data[c] = ConvertUtils.msbByte2Float(buffer, i+(c*4));
					}
					for (int c=recordCount; c<=data.length-1; c++) {
						data[c] = 0.0f;
					}

					if (this.timePeriod == TIME_PERIOD_DAY || this.timePeriod == TIME_PERIOD_WEEK) {
												
						if(ConvertUtils.float2Date(data[0]).compareTo(dtStart) >= 0
						&& ConvertUtils.float2Date(data[0]).compareTo(dtEnd) <= 0){		
							
							if (this.fields.isHighField() && this.fields.isLowField() &&
									this.fields.isOpenInterest() && this.fields.isOpenPrice()) {
								quotes.add(new Quote(getSymbol(), getName(), ConvertUtils.float2Date(data[0]), data[2], data[3], data[1], data[4], (long)data[6], (long)data[5]));
							} else if (this.fields.isHighField() && this.fields.isLowField() &&
									!this.fields.isOpenInterest() && this.fields.isOpenPrice()) {
								quotes.add(new Quote(getSymbol(), getName(), ConvertUtils.float2Date(data[0]), data[2], data[3], data[1], data[4], 0, (long)data[5]));
							} else if (this.fields.isHighField() && this.fields.isLowField() &&
									!this.fields.isOpenInterest() && !this.fields.isOpenPrice()) {
								quotes.add(new Quote(getSymbol(), getName(), ConvertUtils.float2Date(data[0]), data[1], data[2], 0, data[3], 0, (long)data[4]));
							}							
						}
						
					} else if (this.timePeriod == TIME_PERIOD_IDA) {
						// TODO intraday
						throw new UnsupportedMetaFileException("NOT SUPPORT INTRADAY;[symbol="+symbol+", file="+fileName+", offset="+i+", recordLength="+recordLength);
					} else {
						throw new UnsupportedMetaFileException("NOT SUPPORT TIMEFRAME;[symbol="+symbol+", file="+fileName+", timePeriod="+timePeriod);
					}
				}
			}
			
		}
		catch (FileNotFoundException e) {
			logger.error(MFUtils.stackTrace2String(e));
		}
		catch (IOException e) {
			logger.error(MFUtils.stackTrace2String(e));
		}
		catch (ConvertException e) {
			logger.error(MFUtils.stackTrace2String(e));
		}
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the issue name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the issue name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the quoteCount
	 */
	public int getQuoteCount() {
		return quoteCount;
	}

	/**
	 * @param quoteCount the quoteCount to set
	 */
	public void setQuoteCount(int quoteCount) {
		this.quoteCount = quoteCount;
	}

	/**
	 * @return the fileNumber
	 */
	public int getFileNumber() {
		return fileNumber;
	}

	/**
	 * @param fileNumber the fileNumber to set
	 */
	public void setFileNumber(int fileNumber) {
		this.fileNumber = fileNumber;
	}

	/**
	 * @return the quotes
	 */
	public ArrayList<Quote> getQuotes() {
		return quotes;
	}

	/**
	 * @param quotes the quotes to set
	 */
	public void setQuotes(ArrayList<Quote> quotes) {
		this.quotes = quotes;
	}

	/**
	 * @return the firstDate
	 */
	public Date getFirstDate() {
		return firstDate;
	}

	/**
	 * @param firstDate the firstDate to set
	 */
	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

	/**
	 * @return the lastDate
	 */
	public Date getLastDate() {
		return lastDate;
	}

	/**
	 * @param lastDate the lastDate to set
	 */
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * @return the timePeriod (MetaFile.TIME_PERIOD_*)
	 */
	public int getTimePeriod() {
		return timePeriod;
	}

	/**
	 * @param timeFrame the timePeriod to set (MetaFile.TIME_PERIOD_*)
	 */
	public void setTimeFrame(int timePeriod) {
		this.timePeriod = timePeriod;
	}

	/**
	 * this file has field Opening Price
	 * @return true or false
	 */
	public boolean hasOpeningPrice() {
		return fields.isOpenPrice();
	}

	/**
	 * this file has field Open Interest
	 * @return true or false
	 */
	public boolean hasOpenInterest() {
		return fields.isOpenInterest();
	}
	
}
