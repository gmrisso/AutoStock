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

package br.com.autoStock.indicator;

public class MediaMovel {

	/*
	 * Media Movel Exponencial
	 * MMEx = ME(x-1) + K x {Fech(x) – ME(x-1)}
	 * */	
	public DataIndicator[] calcularMME(DataIndicator[] source, int period){
		
		DataIndicator[] media = new DataIndicator[source.length];
		
		float mult = (2f / ((float)period + 1f));
		
		media[0] = new DataIndicator();
		media[0].setIndex(source[0].getIndex());
		media[0].setClose(source[0].getClose());
	
		for (int i = 1; i < media.length; i++) {
			media[i] = new DataIndicator();
			media[i].setIndex(source[i].getIndex());
			
			media[i].setClose(media[i-1].getClose() + (mult * (source[i].getClose() - media[i-1].getClose())));
		}
							
		return media;
		
	}
		
	/*
	 * Média Movel simples
	 * SMA = [Fech(x) + Fech(x-1) + Fech(x-2) + … + Fech(x-9)] ÷ 10 
	 * */     
	  public DataIndicator[] calcularMMS(DataIndicator[] source, int period) {
		  
		  DataIndicator[] media = new DataIndicator[source.length];
		  
		  for (int i = 0; i < period; i++){
			  if(i < source.length){
				  media[i] = new DataIndicator();
				  media[i].setIndex(source[i].getIndex());
		  		  media[i].setClose(mediaMovelSimples(source, i,period)/(i+1));
			  }
		  }
		  		          
          for (int i = period; i < source.length; i++){
        	  
        	media[i] = new DataIndicator();
        	media[i].setIndex(source[i].getIndex());
  			media[i].setClose(mediaMovelSimples(source, i,period)/period);  			
          } 
          
          return media;
  
      }
	 
    private float mediaMovelSimples(DataIndicator[] source, int per, int cont){
    	
		if(per == 0 || cont == 1)
			return source[per].getClose();    	
		
		return source[per].getClose() + mediaMovelSimples(source, --per,--cont);
	}
		
}
