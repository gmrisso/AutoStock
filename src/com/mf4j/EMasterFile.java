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

import br.com.autoStock.util.I18n;

import com.mf4j.util.ConvertUtils;
import com.mf4j.util.MFUtils;


public class EMasterFile {

	static Logger logger		= Logger.getLogger(EMasterFile.class.getName());

	/**
	 * size of a EMASTER record
	 */
	public static final int EMASTER_RECORD_LENGTH	= 192;

	/**
	 * id code
	 */
	public static final int ID_CODE_OFFSET			= 0;

	/**
	 * offset of file's name number
	 * file # ie F#
	 */
	public static final int FILE_NAME_NUMBER_OFFSET	= 2;

	/**
	 * number of 4 bytes fields per record
	 */
	public static final int RECORD_COUNT_OFFSET	= 6;

	/**
	 * Tells which fields are visible eg. OI, OP, Time etc.
	 */
	public static final int FIELDS_OFFSET		= 7;

	public static final int AUTORUN_OFFSET		= 9;

	/**
	 * symbol
	 * 13 bytes
	 */
	public static final int SYMBOL_OFFSET			= 11;

	/**
	 * length of symbol
	 */
	public static final int SYMBOL_LENGTH			= 13;

	/**
	 * stock name1
	 * 16 bytes
	 */
	public static final int ISSUE_NAME_OFFSET1		= 32;

	/**
	 * length of stock's name
	 */
	public static final int ISSUE_NAME_LENGTH1		= 16;

	/**
	 * stock name2
	 * 32 bytes
	 */
	public static final int ISSUE_NAME_OFFSET2		= 139;

	/**
	 * length of stock's name
	 */
	public static final int ISSUE_NAME_LENGTH2		= 45;

	/**
	 * date format: 'I'(IDA), 'W', 'Q', 'D', 'M' or 'Y'
	 */
	public static final int TIME_PERIOD_OFFSET		= 60;

	/**
	 * first date
	 * 4 bytes
	 */
	public static final int FIRST_DATE_OFFSET		= 64;

	/**
	 * last date
	 * 4 bytes
	 */
	public static final int LAST_DATE_OFFSET		= 72;

	private static final String FILE_PREFIX			= "F";
	private static final String FILE_EXT			= ".DAT";

	private int idCode;
	private int fileNumber;
	private String fileType;
	private int recordCount;
	private Date firstDate;
	private Date lastDate;
	private String symbol;
	private String issueName;
	private String timePeriod;
	private boolean autoRun;
	private Fields emFields;

	public EMasterFile(byte[] data, int recordNumber) {
		int recordBase = (recordNumber + 1) * EMASTER_RECORD_LENGTH;
		try {
			idCode = data[recordBase+ID_CODE_OFFSET];
			fileNumber = data[recordBase+FILE_NAME_NUMBER_OFFSET];
			
			//TODO xunxo do capeta
			if(fileNumber < 0){
				fileNumber = 256 + fileNumber;
			}

			recordCount = data[recordBase+RECORD_COUNT_OFFSET];
			emFields = new Fields(data[recordBase+FIELDS_OFFSET]);

			if (data[recordBase+AUTORUN_OFFSET] == 42) {	// 42 = 'Y'
				autoRun = true;
			} else {
				autoRun = false;
			}

			symbol = new String(data,recordBase+SYMBOL_OFFSET, SYMBOL_LENGTH).trim();
			issueName = new String(data, recordBase+ISSUE_NAME_OFFSET1, ISSUE_NAME_LENGTH1).trim();
			String tmp = new String(data, recordBase+ISSUE_NAME_OFFSET2, ISSUE_NAME_LENGTH2).trim();
			if (!tmp.equals("")) {
				// case issue name > 16 characters
				issueName = tmp;
			}

			timePeriod = new String(data, recordBase+TIME_PERIOD_OFFSET, 1);

			firstDate = ConvertUtils.byte2Date(data, recordBase+FIRST_DATE_OFFSET);
			lastDate = ConvertUtils.byte2Date(data, recordBase+LAST_DATE_OFFSET);
			
			//logger.debug(symbol+" "+recordCount+" "+FILE_PREFIX+fileNumber+FILE_EXT );
		}
		catch (ConvertException e) {
			logger.error(I18n.getMessage("bj") + recordNumber);
			logger.error(MFUtils.stackTrace2String(e));
			// TODO call DataDump
		}
	}

	/**
	 * @return the idCode
	 */
	public int getIdCode() {
		return idCode;
	}

	/**
	 * @param idCode the idCode to set
	 */
	public void setIdCode(int idCode) {
		this.idCode = idCode;
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
	 * @return the fileType
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
	 * @return the timePeriod
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

	/**
	 * @return the emFields
	 */
	public Fields getEmFields() {
		return emFields;
	}

	/**
	 * @param emFields the emFields to set
	 */
	public void setEmFields(Fields emFields) {
		this.emFields = emFields;
	}

	/**
	 * @return fileName, "F" + fileNumber + ".DAT"
	 */
	public String getFileName() {
		return FILE_PREFIX+fileNumber+FILE_EXT;
	}
}
