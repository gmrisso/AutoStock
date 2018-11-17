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

public class Bollinger {
	
	MediaMovel media;
	
	public Bollinger(){
		media = new MediaMovel();
	}
	
	public DataIndicator[][] calcBollingerBands(DataIndicator[] source, int periodo, int qtDesvio){
		
		DataIndicator[] bandaCentral = media.calcularMMS(source, periodo);
		
		DataIndicator[] bandaInferior = new DataIndicator[source.length];
		DataIndicator[] bandaSuperior = new DataIndicator[source.length];
				
		for (int i = periodo; i < source.length; i++){
			
			bandaInferior[i] = new DataIndicator();
			bandaSuperior[i] = new DataIndicator();
			
			float desvioP = calculaDesvioPadrao(i,source,periodo,bandaCentral[i].getClose());
			              
			bandaInferior[i].setClose((float) (bandaCentral[i].getClose() - qtDesvio * desvioP));
			bandaSuperior[i].setClose((float) (bandaCentral[i].getClose() + qtDesvio * desvioP));
						
		}
		
		for (int i = 0; i < periodo; i++){
			
			if(i < bandaCentral.length){
				bandaInferior[i] = bandaCentral[i];
				bandaSuperior[i] = bandaCentral[i];
			}									
		}
		
		DataIndicator[][] resultado = new DataIndicator[3][];	
		
		resultado[0] = bandaCentral;
		resultado[1] = bandaInferior;
		resultado[2] = bandaSuperior;

		return resultado;		
	}
	
	private float calculaDesvioPadrao(int ponteiro, DataIndicator[] source,int periodo, float media){
		
		float somatorio = 0;
		int contAux = periodo;
		
		while(contAux > 0){
			
			somatorio += Math.pow(source[ponteiro].getClose() - media, 2);
			
			ponteiro--;
			contAux --;
		}
		
		somatorio /=(periodo - 1);
				
		return (float) Math.sqrt(somatorio);
	}
}
