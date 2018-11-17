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

public class Ifr {
	
	/* Método Wilder*/	
	public DataIndicator[] calcularIFR(DataIndicator[] source, int period){
		
		DataIndicator[] ifr = new DataIndicator[source.length];
				
		float medAlta = 0;
		float medBaixa = 0;
		
		ifr[0] = new DataIndicator();
		ifr[0].setIndex(source[0].getIndex());
		ifr[0].setClose(100);
		
		for (int i = 1; i < source.length; i++) {
			
			ifr[i] = new DataIndicator();
			ifr[i].setIndex(source[i].getIndex());
						
			if(i > period){
				
				if(source[i].getClose() > source[i-1].getClose()){					
					medBaixa *= (period - 1);
					medBaixa /= period;
					medAlta *= (period - 1);					
					medAlta += (source[i].getClose() - source[i-1].getClose());					
					medAlta /= period;					
				}
				else{
					medAlta *= (period - 1);	
					medAlta /= period;
					medBaixa *= (period - 1);					
					medBaixa += (source[i - 1].getClose() - source[i].getClose());					
					medBaixa /= period;
				}
				
				ifr[i].setClose(100 - (100/(1+ (medAlta / medBaixa))));
				
			}
			else {
				
				if(source[i].getClose() > source[i-1].getClose()){
					
					medAlta += (source[i].getClose() - source[i-1].getClose());
					
				}
				else{
					medBaixa += (source[i - 1].getClose() - source[i].getClose());
				}
				
				if(i == period){
					medAlta /= period;
					medBaixa /= period;
					
					ifr[i].setClose(100 - (100/(1+ (medAlta / medBaixa))));
				}
			}			
		}							
		return ifr;		
	}

}
