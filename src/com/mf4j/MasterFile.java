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

import org.apache.log4j.Logger;

import com.mf4j.util.ConvertUtils;
import com.mf4j.util.MFUtils;


public class MasterFile {

	static Logger logger		= Logger.getLogger(MetaFile.class.getName());

	/**
	 * size of a MASTER record
	 */
	public static final int MASTER_RECORD_LENGTH			= 53;

	/**
	 * offset of file's name number
	 * file # ie F#
	 */
	public static final int FILE_NAME_NUMBER_OFFSET	= 0;

	/**
	 * CT file type = 0'e' (5 or 7 fields)
	 * 2 bytes
	 */
	public static final int FILE_TYPE_OFFEST		= 1;

	/**
	 * record length in bytes
	 */
	public static final int RECORD_LENGTH_OFFSET	= 3;

	/**
	 * number of 4 bytes fields per record
	 */
	public static final int RECORD_COUNT_OFFSET		= 4;

	/**
	 * reserve1
	 * 2 bytes
	 */
	public static final int RESERVE_1_OFFSET		= 5;

	/**
	 * stock name
	 * 16 bytes
	 */
	public static final int ISSUE_NAME_OFFSET		= 7;

	/**
	 * length of stock's name
	 */
	public static final int ISSUER_NAME_LENGTH		= 16;

	/**
	 * reserve2
	 * 1 byte
	 */
	public static final int RESERVE_2_OFFSET		= 23;

	/**
	 * 'Y" if CT ver 2.8, anything else otherwise
	 */
	public static final int V28_FLAG_OFFSET			= 24;

	/**
	 * first date
	 * 4 bytes
	 */
	public static final int FIRST_DATE_OFFSET		= 25;

	/**
	 * last date
	 * 4 bytes
	 */
	public static final int LAST_DATE_OFFSET		= 29;

	/**
	 * date format: 'I'(IDA), 'W', 'Q', 'D', 'M' or 'Y'
	 */
	public static final int TIME_PERIOD_OFFSET		= 33;

	/**
	 * intraday time base
	 * 2 byte
	 */
	public static final int IDA_TIME_OFFSET			= 34;

	/**
	 * symbol
	 * 14 bytes
	 */
	public static final int SYMBOL_OFFSET			= 36;

	/**
	 * length of symbol
	 */
	public static final int SYMBOL_LENGTH			= 14;


	private static final String FILE_PREFIX			= "F";
	private static final String FILE_EXT			= ".DAT";

	private int fileNumber;
	private String fileType;
	private int recordLength;
	private int recordCount;
	private Date firstDate;
	private Date lastDate;
	private String symbol;
	private String issueName;
	private boolean v28;
	private String timePeriod;
	private int idaTime;
	private boolean autoRun;

	public MasterFile(byte[] data, int recordNumber) {
		int recordBase = (recordNumber+1) * MASTER_RECORD_LENGTH;
		try {
			fileNumber = data[recordBase+FILE_NAME_NUMBER_OFFSET];
			
			//TODO xunxo do capeta
			if(fileNumber < 0){
				fileNumber = 256 + fileNumber;
			}
			
			fileType = String.valueOf(ConvertUtils.byte2Int(data, recordBase+FILE_TYPE_OFFEST, 2));

			recordLength = data[recordBase+RECORD_LENGTH_OFFSET];
			recordCount = data[recordBase+RECORD_COUNT_OFFSET];

			issueName = new String(data, recordBase+ISSUE_NAME_OFFSET, ISSUER_NAME_LENGTH).trim();

			if (data[recordBase+V28_FLAG_OFFSET] == 89) {	// 89 = 'Y'
				v28 = true;
			} else {
				v28 = false;
			}
			if (v28) {
				autoRun = true;
			} else {
				autoRun = false;
			}

			firstDate = ConvertUtils.msbByte2Date(data, recordBase+FIRST_DATE_OFFSET);
			lastDate = ConvertUtils.msbByte2Date(data, recordBase+LAST_DATE_OFFSET);

			timePeriod = new String(data, recordBase+TIME_PERIOD_OFFSET, 1);
			idaTime = ConvertUtils.byte2Int(data, recordBase+IDA_TIME_OFFSET, 2);

			symbol = new String(data,recordBase+SYMBOL_OFFSET, SYMBOL_LENGTH).trim();
			//logger.debug(symbol+" "+recordCount+" "+FILE_PREFIX+fileNumber+FILE_EXT );
		}
		catch (ConvertException e) {
			logger.error("can't load MASTER at #"+recordNumber);
			logger.error(MFUtils.stackTrace2String(e));
			// TODO call DataDump
		}
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
	 * @return fileName, "F" + fileNumber + ".DAT"
	 */
	public String getFileName() {
		return FILE_PREFIX+fileNumber+FILE_EXT;
	}

	/**
	 * @return CT file type = 0'e' (5 or 7 fields)
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return record length in bytes
	 */
	public int getRecordLength() {
		return recordLength;
	}

	/**
	 * @param recordLength the recordLength to set in bytes
	 */
	public void setRecordLength(int recordLength) {
		this.recordLength = recordLength;
	}

	/**
	 * @return the recordCount
	 */
	public int getRecordCount() {
		return recordCount;
	}

	/**
	 * @param recordCount the recordCount to set
	 */
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
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
	 * @return the issueName
	 */
	public String getIssueName() {
		return issueName;
	}

	/**
	 * @param issueName the issueName to set
	 */
	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	/**
	 * @return the v28
	 */
	public boolean isV28() {
		return v28;
	}

	/**
	 * @param v28 the v28 to set
	 */
	public void setV28(boolean v28) {
		this.v28 = v28;
	}

	/**
	 * @return time period data format: 'I'(IDA), 'W', 'Q', 'D', 'M' or 'Y'
	 */
	public String getTimePeriod() {
		return timePeriod;
	}

	/**
	 * @param timePeriod the timePeriod to set
	 */
	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	/**
	 * @return the idaTime (intraday time base)
	 */
	public int getIdaTime() {
		return idaTime;
	}

	/**
	 * @param idaTime the idaTime to set
	 */
	public void setIdaTime(int idaTime) {
		this.idaTime = idaTime;
	}

	/**
	 * @return the autoRun
	 */
	public boolean isAutoRun() {
		return autoRun;
	}

	/**
	 * @param autoRun the autoRun to set
	 */
	public void setAutoRun(boolean autoRun) {
		this.autoRun = autoRun;
	}
}
