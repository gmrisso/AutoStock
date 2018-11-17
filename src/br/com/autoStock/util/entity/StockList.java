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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StockList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Stock[] stock;
	
	public StockList(Stock[] stock){
		this.stock = stock;
	}
	
	@SuppressWarnings("unchecked")
	public boolean add(Stock s){
		
		List<Stock> newStock = new ArrayList<Stock>();
		
		if(!contains(s)){
			
			if(stock != null){
				for(int i = 0; i < stock.length;i++){
					
					newStock.add(stock[i]);			
				}
			}			
			
			boolean result = newStock.add(s);
			
			Collections.sort (newStock, new Comparator() {  
	            public int compare(Object obj1, Object obj2) {  
	            	Stock a1 = (Stock)obj1;  
	            	Stock a2 = (Stock) obj2;  
	                return a1.getStockCode().compareTo(a2.getStockCode());  
	            }  
	        });
			
			stock = newStock.toArray(new Stock[0] );
			
			return result;
		}
		
		return false;		
	}
	
	public boolean remove(Stock s){
		
		List<Stock> newStock = new ArrayList<Stock>();
		
		if(contains(s)){
			
			for(int i = 0; i < stock.length;i++){
				
				newStock.add(stock[i]);			
			}
			
			boolean result = newStock.remove(s);
			
			stock = newStock.toArray(new Stock[0] );
			
			return result;
			
		}
		
		return false;
	}
	
	public boolean removeAll(){
		
		stock = null;		
		return true;
	}
	
	public boolean contains(Stock s){
		
		if(stock == null)
			return false;
		
		for(int i = 0; i < stock.length;i++){
			
			if(stock[i].equals(s))
				return true;			
		}
		
		return false;		
	}
	
	public Stock[] getAllStock(){
		return stock;
	}

}
