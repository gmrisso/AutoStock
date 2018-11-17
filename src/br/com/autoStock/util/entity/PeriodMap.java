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

package br.com.autoStock.util.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.autoStock.util.exception.PeriodException;

import com.mf4j.Quote;

public class PeriodMap {
	
	static Logger logger = Logger.getLogger(PeriodMap.class.getName());
	
	List<Quote> listPriceDay, listPriceWeek, listPriceMonth;
	String codeStock, stockName;
	
	private HashMap<String,String> mapaDay; //[15/07/2011;01/01/1900]
	private HashMap<String,String> mapaWeek; //[15/07/2011;01/01/1900]
	private HashMap<String,String> mapaMonth; //[15/07/2011;01/01/1900]
		
	@SuppressWarnings("deprecation")
	public PeriodMap(List<Quote> listPrice) throws PeriodException{
		
		mapaDay = new HashMap<String,String>();
		mapaWeek = new HashMap<String,String>();
		mapaMonth = new HashMap<String,String>();
		
		if(listPrice == null || listPrice.size() == 0)
			throw new PeriodException();
		
		this.listPriceDay = listPrice;
		this.codeStock = listPrice.get(0).getCodeStock();
		this.stockName = listPrice.get(0).getStockName();
		
		//Carregar mapaDay
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		Calendar gambiarra = Calendar.getInstance();
        gambiarra.set(1900, 0, 1); 
                
        for (int i = 0; i < listPriceDay.size(); i++) {
        	
        	date = gambiarra.getTime();        	
        	gambiarra.add(Calendar.DATE, 1);        	
        	date.setHours(12);        	
        	mapaDay.put(simpleDateformat.format(listPriceDay.get(i).getDate()),simpleDateformat.format(date));

        }
						
		//Week Data
		listPriceWeek =  new ArrayList<Quote>();
		
		Quote priceW = new Quote(listPrice.get(0).getCodeStock(), 
				listPrice.get(0).getStockName(), 
				listPrice.get(0).getDate(), 
				listPrice.get(0).getTime(), 
				listPrice.get(0).getHigh(), 
				listPrice.get(0).getLow(), 
				listPrice.get(0).getOpen(), 
				listPrice.get(0).getClose(), 
				listPrice.get(0).getOpenInterest(), 
				listPrice.get(0).getVolume());
		
		gambiarra.set(1900, 0, 1);
		
		for(int i = 1; i < listPrice.size();i++ ){
			
			if(listPrice.get(i).getDate().getDay() > listPrice.get(i - 1).getDate().getDay()){
				
				if(listPrice.get(i).getHigh() > priceW.getHigh())
					priceW.setHigh(listPrice.get(i).getHigh());
				
				if(listPrice.get(i).getLow() < priceW.getLow())
					priceW.setLow(listPrice.get(i).getLow());	
				
				priceW.setClose(listPrice.get(i).getClose());					
				priceW.setOpenInterest(priceW.getOpenInterest()+ listPrice.get(i).getOpenInterest());
				priceW.setVolume(priceW.getVolume() + listPrice.get(i).getVolume());
				
				date = gambiarra.getTime();        	
	        	mapaWeek.put(simpleDateformat.format(listPrice.get(i).getDate()),simpleDateformat.format(date));
	        	
			}
			else{
				listPriceWeek.add(priceW);
				
				date = gambiarra.getTime();        	
	        	gambiarra.add(Calendar.DATE, 1);        	
	        	date.setHours(12);        	
	        	mapaWeek.put(simpleDateformat.format(priceW.getDate()),simpleDateformat.format(date));
	        	
				priceW = new Quote(listPrice.get(i).getCodeStock(), 
						listPrice.get(i).getStockName(), 
						listPrice.get(i).getDate(), 
						listPrice.get(i).getTime(), 
						listPrice.get(i).getHigh(), 
						listPrice.get(i).getLow(), 
						listPrice.get(i).getOpen(), 
						listPrice.get(i).getClose(), 
						listPrice.get(i).getOpenInterest(), 
						listPrice.get(i).getVolume());					
			}	
		}
		
		listPriceWeek.add(priceW);
		
		date = gambiarra.getTime();        	
    	gambiarra.add(Calendar.DATE, 1);        	
    	date.setHours(12);        	
    	mapaWeek.put(simpleDateformat.format(priceW.getDate()),simpleDateformat.format(date));
    	
		listPriceMonth =  new ArrayList<Quote>();
		
		Quote priceM = new Quote(listPrice.get(0).getCodeStock(), 
				listPrice.get(0).getStockName(), 
				listPrice.get(0).getDate(), 
				listPrice.get(0).getTime(), 
				listPrice.get(0).getHigh(), 
				listPrice.get(0).getLow(), 
				listPrice.get(0).getOpen(), 
				listPrice.get(0).getClose(), 
				listPrice.get(0).getOpenInterest(), 
				listPrice.get(0).getVolume());
		
		gambiarra.set(1900, 0, 1);
		
		for(int i = 1; i < listPrice.size();i++ ){
			
			if(listPrice.get(i).getDate().getDate() > listPrice.get(i - 1).getDate().getDate()){
				
				if(listPrice.get(i).getHigh() > priceM.getHigh())
					priceM.setHigh(listPrice.get(i).getHigh());
				
				if(listPrice.get(i).getLow() < priceM.getLow())
					priceM.setLow(listPrice.get(i).getLow());	
				
				priceM.setClose(listPrice.get(i).getClose());					
				priceM.setOpenInterest(priceM.getOpenInterest()+ listPrice.get(i).getOpenInterest());
				priceM.setVolume(priceM.getVolume() + listPrice.get(i).getVolume());
				
				date = gambiarra.getTime();        	
	        	mapaMonth.put(simpleDateformat.format(listPrice.get(i).getDate()),simpleDateformat.format(date));
	        	
			}
			else{
				listPriceMonth.add(priceM);
				
				date = gambiarra.getTime();        	
	        	gambiarra.add(Calendar.DATE, 1);        	
	        	date.setHours(12);        	
	        	mapaMonth.put(simpleDateformat.format(priceM.getDate()),simpleDateformat.format(date));
	        	
				priceM = new Quote(listPrice.get(i).getCodeStock(), 
						listPrice.get(i).getStockName(), 
						listPrice.get(i).getDate(), 
						listPrice.get(i).getTime(), 
						listPrice.get(i).getHigh(), 
						listPrice.get(i).getLow(), 
						listPrice.get(i).getOpen(), 
						listPrice.get(i).getClose(), 
						listPrice.get(i).getOpenInterest(), 
						listPrice.get(i).getVolume());					
			}
		}
		
		listPriceMonth.add(priceM);		
		
		date = gambiarra.getTime();        	
    	gambiarra.add(Calendar.DATE, 1);        	
    	date.setHours(12);        	
    	mapaMonth.put(simpleDateformat.format(priceM.getDate()),simpleDateformat.format(date));
    			
	}
	
	public Quote[] getQuoteDay() throws PeriodException{
		
		if(listPriceDay == null || listPriceDay.size() == 0)
		throw new PeriodException();
			
		return listPriceDay.toArray(new Quote[0]);
	}
	
	public Quote[] getQuoteWeek() throws PeriodException{
		
		if(listPriceWeek == null || listPriceWeek.size() == 0)
		throw new PeriodException();
		
		return listPriceWeek.toArray(new Quote[0]);
	}
	
	public Quote[] getQuoteMonth() throws PeriodException{
		
		if(listPriceMonth == null || listPriceMonth.size() == 0)
		throw new PeriodException();
		
		return listPriceMonth.toArray(new Quote[0]);
	}
	
	public String convertDate( char period, String date){
		
		switch(period){				
		case 'D':
			return mapaDay.get(date);
		case 'W':
			return mapaWeek.get(date);
		case 'M':
			return mapaMonth.get(date);
		}
		return "";
	}
	
	public String getCodeStock(){
		return codeStock;
	}
	
	public String getStockName(){
		return stockName;
	}
}
