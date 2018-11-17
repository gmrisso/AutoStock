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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.log4j.Logger;


public class MetaFileManager {

	static Logger logger		= Logger.getLogger(MetaFileManager.class.getName());

	/**
	 * hashtable of Class MetaFile
	 * key = symbol
	 * value = MetaFile
	 */
	private Hashtable<String, MetaFile[]> metaFiles = new Hashtable<String, MetaFile[]>();

	private String metaFilePath;

	private int stockCount;

	/**
	 * create a new MetaFileManager instance with metafilePath
	 * @param metafilePath point to directory of metastock data
	 */
	public MetaFileManager(String metafilePath) {
		if (!metafilePath.endsWith(File.separator)) {
			this.metaFilePath = metafilePath+File.separator;
		} else {
			this.metaFilePath = metafilePath;
		}
	}

	/**
	 * create a new master file
	 */
	public void create() {
		// TODO implement method create a new master file
	}

	/**
	 * initialize an existing metafile
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		stockCount = 0;

		// read file master
		String masterFileName = metaFilePath+"MASTER";
		byte[] buffer = new byte[(int) new File(masterFileName).length()];
		BufferedInputStream f = null;
		f = new BufferedInputStream(new FileInputStream(masterFileName));
		f.read(buffer);
		f.close();
		int recordMasterCount = (buffer.length/MasterFile.MASTER_RECORD_LENGTH)-1;
		//logger.debug("master has "+recordMasterCount+" records.");
		ArrayList<MasterFile> masterFileRecords = new ArrayList<MasterFile>();
		for (int i=0; i<recordMasterCount; i++) {
			MasterFile masterFileRecord = new MasterFile(buffer, i);
			masterFileRecords.add(masterFileRecord);
		}
		stockCount = recordMasterCount;

		// read file emaster
		String emasterFileName = metaFilePath+"EMASTER";
		buffer = new byte[(int) new File(emasterFileName).length()];
		f = new BufferedInputStream(new FileInputStream(emasterFileName));
		f.read(buffer);
		f.close();
		recordMasterCount = (buffer.length/EMasterFile.EMASTER_RECORD_LENGTH)-1;
		ArrayList<EMasterFile> emasterFileRecords = new ArrayList<EMasterFile>();
		for (int i=0; i<recordMasterCount; i++) {
			EMasterFile emasterFileRecord = new EMasterFile(buffer, i);
			emasterFileRecords.add(emasterFileRecord);
		}

		// read file xmaster
		String xmasterFileName = metaFilePath+"XMASTER";
		File xmasterFile = new File(xmasterFileName);
		ArrayList<XMasterFile> xmasterFileRecords = new ArrayList<XMasterFile>();;
		int recordXMasterCount = 0;
		if (xmasterFile.exists()) {
			buffer = new byte[(int) xmasterFile.length()];
			f = new BufferedInputStream(new FileInputStream(xmasterFileName));
			f.read(buffer);
			f.close();
			recordXMasterCount = (buffer.length/XMasterFile.XMASTER_RECORD_LENGTH)-1;
			for (int i=0; i<recordXMasterCount; i++) {
				XMasterFile xmasterFileRecord = new XMasterFile(buffer, i);
				xmasterFileRecords.add(xmasterFileRecord);
			}
		}
		//logger.debug("xmaster has "+recordXMasterCount+" records.");
		stockCount += recordXMasterCount;

		// read metafile of master and put to metaFiles
		for (int i=0; i<recordMasterCount; i++) {
			
			MetaFile[] metaFile = new MetaFile[1];
			metaFile[0] = new MetaFile(metaFilePath+masterFileRecords.get(i).getFileName(), masterFileRecords.get(i), emasterFileRecords.get(i));
			if (metaFiles.containsKey(metaFile[0].getSymbol())) {
				MetaFile[] oldMetaFile = metaFiles.get(metaFile[0].getSymbol());
				int oldLength = oldMetaFile.length;
				MetaFile[] newMetaFile = new MetaFile[oldMetaFile.length+1];
				System.arraycopy(oldMetaFile, 0, newMetaFile, 0, oldLength);
				newMetaFile[oldLength] = metaFile[0];
				metaFiles.put(metaFile[0].getSymbol(), newMetaFile);
			} else {
				metaFiles.put(metaFile[0].getSymbol(), metaFile);
			}
		}

		// read metafile of xmaster and put to metaFiles
		for (int i=0; i<recordXMasterCount; i++) {
			
			MetaFile[] metaFile = new MetaFile[1];
			metaFile[0] = new MetaFile(metaFilePath+xmasterFileRecords.get(i).getFileName(), xmasterFileRecords.get(i));
			if (metaFiles.containsKey(metaFile[0].getSymbol())) {
				MetaFile[] oldMetaFile = metaFiles.get(metaFile[0].getSymbol());
				int oldLength = oldMetaFile.length;
				MetaFile[] newMetaFile = new MetaFile[oldMetaFile.length+1];
				System.arraycopy(oldMetaFile, 0, newMetaFile, 0, oldLength);
				newMetaFile[oldLength] = metaFile[0];
				metaFiles.put(metaFile[0].getSymbol(), newMetaFile);
			} else {
				metaFiles.put(metaFile[0].getSymbol(), metaFile);
			}
		}
	}

	/**
	 * get metafile by specify symbol
	 * @param symbol
	 * @return array of MetaFile or null if not found
	 */
	public MetaFile[] getMetaFileBySymbol(String symbol) {
		if (metaFiles.containsKey(symbol)) {
			return metaFiles.get(symbol);
		} else {
			return null;
		}
	}

	/**
	 * get metafile by specify issue's name
	 * @param name
	 * @return
	 */
	public MetaFile[] getMetaFileByName(String name) {
		Enumeration<MetaFile[]> enums = metaFiles.elements();
		while (enums.hasMoreElements()) {
			MetaFile[] metaFile = enums.nextElement();
			if (metaFile[0].getName().equals(name)) {
				return metaFile;
			}
		}

		return null;
	}

	/**
	 *
	 * @return array of all stocks
	 */
	public MetaFile[] getAllMetaFiles() {
		if (stockCount <= 0) {
			return null;
		}

		MetaFile[] metaFile = new MetaFile[stockCount];
		int i=0;
		Enumeration<MetaFile[]> enums = metaFiles.elements();
		while (enums.hasMoreElements()) {
			MetaFile[] mf = enums.nextElement();
			for (int j=0; j<mf.length; j++) {
				metaFile[i++] = mf[j];
			}
		}
		return metaFile;
	}

	/**
	 *
	 * @return array of String contains all symbols
	 */
	public String[] getAllSymbols() {
		String[] symbols = new String[metaFiles.size()];
		Enumeration<String> enums = metaFiles.keys();
		int i=0;
		while (enums.hasMoreElements()) {
			symbols[i++] = enums.nextElement();
		}

		return symbols;
	}

	/**
	 *
	 * @return array of String contains all names
	 */
	public String[] getAllNames() {
		String[] names = new String[metaFiles.size()];
		Enumeration<MetaFile[]> enums = metaFiles.elements();
		int i=0;
		while (enums.hasMoreElements()) {
			MetaFile metaFile[] = enums.nextElement();
			names[i++] = metaFile[0].getName();
		}

		return names;
	}

	/**
	 *
	 * @return decimal precision
	 */
	public int getQuoteDecimalPrecision() {
		return Quote.getDecimalPrecision();
	}

	/**
	 * set decimal precision (default is 4 digit)
	 * @param decimalPrecision
	 */
	public void setQuoteDecimalPrecision(int decimalPrecision) {
		Quote.setDecimalPrecision(decimalPrecision);
	}

	/**
	 *
	 * @return number of stocks
	 */
	public int getStockCount() {
		return stockCount;
	}
	
	
}
