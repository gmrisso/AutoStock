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

public class Dmi {
	
	private MediaMovel m;
	
	public Dmi(){
		m = new MediaMovel();
	}
		
	public DataIndicator[][] calcularDMI(DataIndicator[] source, int periodo, int average){
		
		DataIndicator[] deltaHigh = new DataIndicator[source.length];
		DataIndicator[] deltaLow = new DataIndicator[source.length];
		
		deltaHigh[0] = new DataIndicator();
		deltaHigh[0].setIndex(source[0].getIndex());
		deltaHigh[0].setClose(0);
		
		deltaLow[0] = new DataIndicator();
		deltaLow[0].setIndex(source[0].getIndex());
		deltaLow[0].setClose(0);
		
		/* Calculando movimento direcional */
		for (int i = 1; i < source.length; i++){
			
			deltaHigh[i] = new DataIndicator();
			deltaHigh[i].setIndex(source[0].getIndex());
			
			deltaLow[i] = new DataIndicator();
			deltaLow[i].setIndex(source[0].getIndex());
			
			deltaHigh[i].setClose(source[i].getHigh() - source[i - 1].getHigh());
			deltaLow[i].setClose(source[i - 1].getLow() - source[i].getLow());
			
			if((deltaHigh[i].getClose() < 0 && deltaLow[i].getClose() < 0) || (deltaHigh[i].getClose() == deltaLow[i].getClose())){
				deltaHigh[i].setClose(0);
				deltaLow[i].setClose(0);
			}
			else if(deltaHigh[i].getClose() > deltaLow[i].getClose()){
				deltaLow[i].setClose(0);
			}
			else {
				deltaHigh[i].setClose(0);
			}			
		}
		
		/* Média do movimento direcional */
		DataIndicator[] admp = new DataIndicator[source.length];
		DataIndicator[] admn = new DataIndicator[source.length];
		
		admp = m.calcularMME(deltaHigh, average);
		admn = m.calcularMME(deltaLow, average);
				
		/* True Range */
		DataIndicator[] tr = new DataIndicator[source.length];
		
		tr[0] = new DataIndicator();
		tr[0].setIndex(source[0].getIndex());
		tr[0].setClose(source[0].getHigh() - source[0].getLow());
		
		for (int i = 1; i < source.length; i++){
			
			float tr1 = Math.abs(source[i].getHigh() - source[i].getLow());
			float tr2 = Math.abs(source[i].getHigh() - source[i - 1].getClose());
			float tr3 = Math.abs(source[i - 1].getClose() - source[i].getLow());
			tr[i] = new DataIndicator();
			tr[i].setClose(Math.max(tr1, tr2));
			tr[i].setClose(Math.max(tr[i].getClose(), tr3));
			
		}
		
		/* Média True Range */
		DataIndicator[] atr = new DataIndicator[source.length];
		
		atr = m.calcularMME(tr, average);
		
		/* Indice direcional */		
		DataIndicator[] dimais = new DataIndicator[source.length];
		DataIndicator[] dimenos = new DataIndicator[source.length];
				
		for (int i = 0; i < source.length; i++){
			
			dimais[i] = new DataIndicator();
			dimais[i].setIndex(source[i].getIndex());
			dimais[i].setClose((admp[i].getClose() / atr[i].getClose()) * 100);
			
			dimenos[i] = new DataIndicator();
			dimenos[i].setIndex(source[i].getIndex());
			dimenos[i].setClose((admn[i].getClose() / atr[i].getClose()) * 100);
		}	
		
		/* Indice de movimento direcional */
		DataIndicator[] dx = new DataIndicator[source.length];
				
		for (int i = 0; i < source.length; i++){
			dx[i] = new DataIndicator();
			dx[i].setIndex(source[i].getIndex());
			
			if(dimais[i].getClose() + dimenos[i].getClose() == 0.0){
				dx[i].setClose(0);
			}
			else{
				dx[i].setClose((Math.abs(dimais[i].getClose() - dimenos[i].getClose()) / (dimais[i].getClose() + dimenos[i].getClose())) * 100);
			}
		}
		
		/* Média do indice de movimento direcional */
		DataIndicator[] adx = new DataIndicator[source.length];		
		adx = m.calcularMME(dx, average);		
		
		DataIndicator[][] resultado = new DataIndicator[3][];	
		
		resultado[0] = adx;
		resultado[1] = dimais;
		resultado[2] = dimenos;

		return resultado;		
	}

}
