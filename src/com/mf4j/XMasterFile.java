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


public class XMasterFile {

	static Logger logger		= Logger.getLogger(XMasterFile.class.getName());

	/**
	 * size of a XMASTER record
	 */
	public static final int XMASTER_RECORD_LENGTH	= 150;

	/**
	 * offset of file's name number
	 * file # ie F#
	 */
	public static final int FILE_NAME_NUMBER_OFFSET	= 65;

	/**
	 * stock name
	 * 16 bytes
	 */
	public static final int ISSUE_NAME_OFFSET		= 16;

	/**
	 * length of stock's name
	 */
	public static final int ISSUER_NAME_LENGTH		= 23;

	/**
	 * first date
	 * 4 bytes
	 */
	public static final int FIRST_DATE_OFFSET		= 104;

	/**
	 * last date
	 * 4 bytes
	 */
	public static final int LAST_DATE_OFFSET		= 108;

	/**
	 * date format: 'I'(IDA), 'W', 'Q', 'D', 'M' or 'Y'
	 */
	public static final int TIME_PERIOD_OFFSET		= 62;

	/**
	 * symbol
	 * 14 bytes
	 */
	public static final int SYMBOL_OFFSET			= 1;

	/**
	 * Tells which fields are visible eg. OI, OP, Time etc.
	 */
	public static final int FIELDS_OFFSET		= 70;

	/**
	 * length of symbol
	 */
	public static final int SYMBOL_LENGTH			= 14;

	private static final String FILE_PREFIX			= "F";
	private static final String FILE_EXT			= ".MWD";

	private int fileNumber;
	private int recordLength;
	private int recordCount;
	private Date firstDate;
	private Date lastDate;
	private String symbol;
	private String issueName;
	private String timePeriod;
	private Fields xmFields;

	public XMasterFile(byte[] data, int recordNumber) {
		int recordBase = (recordNumber+1) * XMASTER_RECORD_LENGTH;
		try {
			fileNumber = ConvertUtils.byte2Int(data, recordBase+FILE_NAME_NUMBER_OFFSET, 2);
			xmFields = new Fields(data[recordBase+FIELDS_OFFSET]);

			recordLength = 28;
			recordCount = 7;

			issueName = new String(data, recordBase+ISSUE_NAME_OFFSET, ISSUER_NAME_LENGTH).trim();

			firstDate = ConvertUtils.byte2DateX(data, recordBase+FIRST_DATE_OFFSET);
			lastDate = ConvertUtils.byte2DateX(data, recordBase+LAST_DATE_OFFSET);

			timePeriod = new String(data, recordBase+TIME_PERIOD_OFFSET, 1);

			symbol = ConvertUtils.byte2StringX(data,recordBase+SYMBOL_OFFSET, SYMBOL_LENGTH);
			
			//logger.debug(symbol+" "+recordCount+" "+FILE_PREFIX+fileNumber+FILE_EXT );
		}
		catch (ConvertException e) {
			logger.error(I18n.getMessage("bj") + recordNumber);
			logger.error(MFUtils.stackTrace2String(e));
			// TODO call DataDump
		}
	}

	/**
	 * @return fileName, "F" + fileNumber + ".MWD"
	 */
	public String getFileName() {
		return FILE_PREFIX+fileNumber+FILE_EXT;
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
	 * @return the recordLength
	 */
	public int getRecordLength() {
		return recordLength;
	}

	/**
	 * @param recordLength the recordLength to set
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
	 * @return the xmFields
	 */
	public Fields getXmFields() {
		return xmFields;
	}

	/**
	 * @param xmFields the xmFields to set
	 */
	public void setXmFields(Fields xmFields) {
		this.xmFields = xmFields;
	}
}
