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

package br.com.autoStock;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.autoStock.util.I18n;
import br.com.autoStock.util.Property;
import br.com.autoStock.util.SinalADVFN;
import br.com.autoStock.util.entity.PeriodMap;
import br.com.autoStock.util.entity.Stock;
import br.com.autoStock.util.exception.PeriodException;
import br.com.autoStock.util.exception.StockException;

import com.mf4j.MetaFile;
import com.mf4j.MetaFileManager;
import com.mf4j.Quote;
import com.mf4j.util.MFUtils;

public class Bovespa{

	static Logger logger = Logger.getLogger(Bovespa.class.getName());
	
	private static final long serialVersionUID = 1L;
	private Calendar today;
	
	public Bovespa(){
		
		this.today = Calendar.getInstance();
	}	

	public PeriodMap listPriceStock(String idStock, Date dtStart, Date dtEnd) throws PeriodException{
		
		List<Quote> listPrice =  new ArrayList<Quote>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		
		try {
			MetaFile selectedMetaFile = getSelectedMetaFile(idStock);
			
			selectedMetaFile.loadQuotes(dtStart,dtEnd);
			
			listPrice = selectedMetaFile.getQuotes();			
			
		} catch (Exception e) {
			logger.error(I18n.getMessage("ad"));
        	logger.error(MFUtils.stackTrace2String(e));
        	listPrice =  new ArrayList<Quote>();
		}
		
		//TODO colocar um parametro
		if((listPrice.size() > 0 && (formatter.format(today.getTime()).compareTo(formatter.format(listPrice.get(listPrice.size() - 1).getDate())) != 0))
		&& (formatter.format(today.getTime()).compareTo(formatter.format(dtEnd)) == 0)){
					
			try {
				
				SinalADVFN advfn = new SinalADVFN();				
				Quote online = advfn.priceOnline(idStock);
				
				if(online != null){
					
					if(listPrice.size() == 0)
						throw new StockException(idStock);
					
					if((formatter.format(today.getTime()).compareTo(formatter.format(online.getDate())) == 0)
							&& (formatter.format(today.getTime()).compareTo(formatter.format(listPrice.get((listPrice.size() -1)).getDate())) != 0)){
						
						listPrice.add(online);
					}
				}			
				
			}  catch (Exception e) {
				logger.error(idStock + I18n.getMessage("ae"));
				logger.error(MFUtils.stackTrace2String(e));
			}
		}
		
		return new PeriodMap(listPrice);
	
	}
	
 	private MetaFile getSelectedMetaFile(String idStock) {
		
		File path = new File(Property.getProperty('D'));
		
		// check MASTER
		String pathName = path.getAbsolutePath();
		String masterFileName = pathName+File.separator+"MASTER";
		File masterFile = new File(masterFileName);
		if (!masterFile.exists()) {
			logger.error(I18n.getMessage("af"));
			return null;
		}

		MetaFileManager manager = new MetaFileManager(pathName);
		try {
			manager.initialize();
		}
		catch (IOException e) {
			logger.error(I18n.getMessage("ag"));
			logger.debug(MFUtils.stackTrace2String(e));
			e.printStackTrace();
			return null;
		}
		
		MetaFile[] metaFile = manager.getMetaFileBySymbol(idStock);
				
		return metaFile[0];
	}
	
	@SuppressWarnings("unchecked")
	public Stock[] listStock(){
		
		File path = new File(Property.getProperty('D'));
		
		logger.debug(I18n.getMessage("ah") + path.getAbsolutePath());

		// check MASTER
		String pathName = path.getAbsolutePath();
		String masterFileName = pathName+File.separator+"MASTER";
		File masterFile = new File(masterFileName);
		if (!masterFile.exists()) {
			logger.error(I18n.getMessage("af"));
			return null;
		}

		MetaFileManager manager = new MetaFileManager(pathName);
		try {
			manager.initialize();
		}
		catch (IOException e) {
			logger.error(I18n.getMessage("ag"));
			logger.error(MFUtils.stackTrace2String(e));
			e.printStackTrace();
			return null;
		}

		MetaFile[] metaFile = manager.getAllMetaFiles();
		
		List<Stock> stockData = new ArrayList<Stock>();
		
		for (int i=0; i<metaFile.length; i++) {
			
			stockData.add(new Stock(metaFile[i].getName(),metaFile[i].getSymbol()));
				
		}
		
		Collections.sort (stockData, new Comparator() {  
            public int compare(Object obj1, Object obj2) {  
            	Stock a1 = (Stock)obj1;  
            	Stock a2 = (Stock) obj2;  
                return a1.getStockCode().compareTo(a2.getStockCode());  
            }  
        });  

		return stockData.toArray(new Stock[0] );
	}
}
