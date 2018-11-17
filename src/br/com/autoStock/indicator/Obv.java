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

public class Obv {
	
	/* Método Wilder*/	
	public DataIndicator[] calcularOBV(DataIndicator[] source){
		
		float aux = 0;
		
		DataIndicator[] obv = new DataIndicator[source.length];
			
		obv[0] = new DataIndicator();
		obv[0].setIndex(source[0].getIndex());
		
		aux = source[0].getVolume();
						
		for (int i = 1; i < source.length; i++) {
			
			obv[i] = new DataIndicator();
			obv[i].setIndex(source[i].getIndex());
			
			if(source[i].getClose() > source[i - 1].getClose()){
				
				aux = aux + source[i].getVolume();
			}
			else if(source[i].getClose() < source[i - 1].getClose()){
				aux = aux - source[i].getVolume();
			}
			
			obv[i].setVolume(aux);	
			
		}
		
		for (int i = 1; i < source.length; i++) {
			obv[i].setClose(obv[i].getVolume());
		}
							
		return obv;
		
	}
	
	public String[] calcularTendenciaOBV(DataIndicator[] source){
		
		double topo = 0D;
		double fundo = 0D;
		double topoAnt = 0D;
		double fundoAnt = 0D;
		
		DataIndicator[] obv = calcularOBV(source);
		
		String[] resultado = new String[obv.length];
		
		resultado[0] = "I";
		resultado[1] = "I";
		for (int i = 2; i < obv.length; i++) {
			
			if(obv[i - 1].getClose() >= obv[i].getClose()
			&& obv[i - 2].getClose() <= obv[i - 1].getClose()){
								
				topoAnt = topo;
				topo = obv[i - 1].getClose();
			}

			if(obv[i - 1].getClose() <= obv[i].getClose()
			&& obv[i - 2].getClose() >= obv[i - 1].getClose()){
				fundoAnt = fundo;
				fundo = obv[i - 1].getClose();
			}
			
			if(topo > topoAnt && fundo > fundoAnt){
				resultado[i] = "A";				
			} else if(topo < topoAnt && fundo < fundoAnt){
				resultado[i] = "B";
			}
			else{
				resultado[i] = "I";
			}
		}
		
		return resultado;		
	}

}