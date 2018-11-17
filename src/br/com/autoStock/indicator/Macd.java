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

public class Macd {
	
	/*
	 * MACD = mme[12] - mme[26]
	 * sinal = mme[9]macd
	 * 	
	*/	
	public DataIndicator[][] calculateMACD (DataIndicator[] source, int qtmacdlongo, int qtmacdcurto, int qtmacdsinal){
		
		MediaMovel media = new MediaMovel();
		
		DataIndicator[] macdLongo = media.calcularMME(source, qtmacdlongo);
		DataIndicator[] macdCurto = media.calcularMME(source, qtmacdcurto);
		DataIndicator[] macd = new DataIndicator[source.length];
		DataIndicator[] histograma = new DataIndicator[source.length];
		
		for (int i = 0; i < source.length; i++) {
			macd[i] = new DataIndicator();
			macd[i].setIndex(source[i].getIndex());			
			macd[i].setClose(macdCurto[i].getClose() - macdLongo[i].getClose());
		}
		
		DataIndicator[] sinal = media.calcularMME(macd, qtmacdsinal);
		
		for (int i = 0; i < macd.length; i++) {
			
			histograma[i] = new DataIndicator();
			histograma[i].setIndex(macd[i].getIndex());
			histograma[i].setClose(macd[i].getClose() - sinal[i].getClose());
			
		}
		
		DataIndicator[][] resultado = new DataIndicator[3][];	
		
		resultado[0] = macd;
		resultado[1] = sinal;
		resultado[2] = histograma;
							
		return resultado;

	}

}