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

public class Estocastico {
	
	private MediaMovel m;
	
	public Estocastico(){
		m = new MediaMovel();
	}	
		
	public DataIndicator[][] calcularEstocastico(DataIndicator[] source, int periodoK, int periodoD, int periodoLento, boolean isLento){

		DataIndicator[] estocasticoK = new DataIndicator[source.length];

		for (int i = 0; i < source.length; i++){

			estocasticoK[i] = new DataIndicator();			
			estocasticoK[i].setIndex(source[i].getIndex());
			
			float maxima = getMaximo(source, i,periodoK);
			float minima = getMinimo(source, i,periodoK);
			
			estocasticoK[i].setClose((source[i].getClose() - minima)/(maxima - minima) * 100F);
			
		}
				
		DataIndicator[] estocasticoD = m.calcularMMS(estocasticoK, periodoD);
		
		/* Estocastico Lento*/
		if(isLento){
			estocasticoK = estocasticoD.clone();
			estocasticoD = m.calcularMMS(estocasticoK, periodoLento);
		}
		
		DataIndicator[] histograma = new DataIndicator[source.length];
		
		for (int i = 0; i < estocasticoK.length; i++) {
			
			histograma[i] = new DataIndicator();
			histograma[i].setIndex(estocasticoK[i].getIndex());
			histograma[i].setClose(estocasticoK[i].getClose() - estocasticoD[i].getClose());
			
		}
		
		DataIndicator[][] resultado = new DataIndicator[3][];	
		
		resultado[0] = estocasticoK;
		resultado[1] = estocasticoD;
		resultado[2] = histograma;

		return resultado;		
	}

	private float getMinimo(DataIndicator[] source, int per, int cont){
    	
		if(per == 0 || cont == 1)
			return source[per].getLow(); 
		
		float valor = getMinimo(source, --per,--cont);
		
		if(valor > source[per+1].getLow()){
			return source[per+1].getLow();
		}
		else{
			return valor;
		}
	}
	
	private float getMaximo(DataIndicator[] source, int per, int cont){
    	
		if(per == 0 || cont == 1)
			return source[per].getHigh(); 
		
		float valor = getMaximo(source, --per,--cont);
		
		if(valor < source[per+1].getHigh()){
			return source[per+1].getHigh();
		}
		else{
			return valor;
		}
	}

}