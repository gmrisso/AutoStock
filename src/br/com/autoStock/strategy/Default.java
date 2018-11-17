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

package br.com.autoStock.strategy;

import com.mf4j.Quote;

import br.com.autoStock.indicator.Estocastico;
import br.com.autoStock.indicator.Ifr;
import br.com.autoStock.indicator.DataIndicator;
import br.com.autoStock.indicator.Macd;
import br.com.autoStock.indicator.MediaMovel;
import br.com.autoStock.indicator.Obv;
import br.com.autoStock.util.entity.Configuration;

public class Default extends Regras{
	
	private Configuration conf;
	private Quote[] quotes;	
	private DataIndicator[] qtmmesimples;
	private DataIndicator[] qtmmecruzLongo;
	private DataIndicator[] qtmmecruzCurto;	
	private DataIndicator[] qtmacd;
	private DataIndicator[] qtmacdHistograma;
	private DataIndicator[] qtmacdsinal;	
	private DataIndicator[] qtifr;
	private DataIndicator[] estocK;
	private DataIndicator[] estocD;
	private String[] tendenciaObv;
	
	private MediaMovel media;
	private Macd macd;
	private Ifr ifr;
	private Obv obv;
	private Estocastico estocastico;	
	
	int candleInicial;
	
	public void init(Configuration conf, Quote[] quotes) {
		
		this.conf = conf;
		this.quotes = quotes;
		
		media = new MediaMovel();
		macd = new Macd();
		ifr = new Ifr();
		obv = new Obv();
		estocastico = new Estocastico();
		
		if(conf.getQtSMA1() > 0){
			qtmmesimples = media.calcularMME(getDado(), conf.getQtSMA1());
			
			if(candleInicial < conf.getQtSMA1() + 2){
				candleInicial = conf.getQtSMA1() + 2;
			}
		}

		if(conf.getQtSMA2() > 0){
			qtmmecruzLongo = media.calcularMME(getDado(), conf.getQtSMA3());
			qtmmecruzCurto = media.calcularMME(getDado(), conf.getQtSMA2());
			
			if(candleInicial < conf.getQtSMA3() + 1){
				candleInicial = conf.getQtSMA3() + 1;
			}
		}

		if(conf.getQtShortMacd() > 0){
			DataIndicator[][] m = macd.calculateMACD(getDado(), conf.getQtLongMacd(), conf.getQtShortMacd(), conf.getQtSignalMacd());
			qtmacd = m[0];
			qtmacdHistograma = m[2];
			qtmacdsinal = m[1];
			
			if(candleInicial < conf.getQtLongMacd() + 2){
				candleInicial = conf.getQtLongMacd() + 2;
			}
		}

		if(conf.getQtRSI() > 0){
			qtifr = ifr.calcularIFR(getDado(),conf.getQtRSI());
			
			if(candleInicial < conf.getQtRSI() + 2){
				candleInicial = conf.getQtRSI() + 2;
			}
		}

		if(conf.getQtKStochastic() > 0){
			DataIndicator[][] ind = estocastico.calcularEstocastico(getDado(), conf.getQtKStochastic(), conf.getQtDStochastic(), conf.getQtLowStoch(), conf.isLowStoch());
			estocK = ind[0];
			estocD = ind[1];
			
			if(candleInicial < conf.getQtKStochastic() + 1){
				candleInicial = conf.getQtKStochastic() + 1;
			}
		}

		if(conf.isObv()){
			tendenciaObv = obv.calcularTendenciaOBV(getDado());
		}
			
	}
	
	private DataIndicator[] getDado(){
		
		DataIndicator[] dado = new DataIndicator[quotes.length];
		
		for (int i = 0; i < quotes.length; i++) {
			dado[i] = new DataIndicator();
			dado[i].setIndex(quotes[i].getDate().getTime());			
			dado[i].setClose(quotes[i].getClose());
			dado[i].setOpen(quotes[i].getOpen());
			dado[i].setVolume(quotes[i].getVolume());
			dado[i].setHigh(quotes[i].getHigh());
			dado[i].setLow(quotes[i].getLow());
		}
		
		return dado;
	}
	
	
	public Configuration getConfiguration(){
		return this.conf;
	}

	public int getCandleInicial(){
		
		return candleInicial;
	}
	
	public float regraCompra(Quote historico, int dataAtual) {

		boolean compra = false;
		boolean venda = false;
		boolean compraOBV = false;
		boolean vendaOBV = false;
		boolean compraEstocastico = false;
		boolean vendaEstocastico = false;
		boolean compraIFR = false;
		boolean vendaIFR = false;
		boolean compraMACD = false;
		boolean vendaMACD = false;
		boolean compraMME = false;
		boolean vendaMME = false;

		if(conf.isObv()){

			if(tendenciaObv[dataAtual].equals("A")){
				compraOBV = true;
			}
			else if(tendenciaObv[dataAtual].equals("B")){
				vendaOBV = true;
			}
		}
		else{
			compraOBV = true;
			vendaOBV = true;
		}

		if(conf.getQtKStochastic() > 0){

			if(conf.isCrossStoch()){
				//Compra e venda no cruzamente de %k com %d
				if (estocK[dataAtual].getClose() <= estocD[dataAtual].getClose()
				&& estocK[dataAtual - 1].getClose() >= estocD[dataAtual - 1].getClose()){

					vendaEstocastico = true;					
				}

				if (estocK[dataAtual].getClose() >= estocD[dataAtual].getClose()
				&& estocK[dataAtual - 1].getClose() <= estocD[dataAtual - 1].getClose()){

					compraEstocastico = true;					
				}						
			}
			else{
				//Compra estocastico sobrevendido e vira pra cima
				if (estocK[dataAtual - 1].getClose() < conf.getOversoldStoch()
				&&  estocK[dataAtual].getClose() > estocK[dataAtual - 1].getClose()
				&&  estocK[dataAtual - 1].getClose() < estocK[dataAtual - 2].getClose()){

					compraEstocastico = true;					
				}

				//Venda estocastico sobrecomprado e vira pra baixo
				if (estocK[dataAtual - 1].getClose() > conf.getOverboughtStoch()
				&&  estocK[dataAtual].getClose() < estocK[dataAtual - 1].getClose()
				&&  estocK[dataAtual - 1].getClose() > estocK[dataAtual - 2].getClose()){

					vendaEstocastico = true;					
				}
			}
		}
		else{
			compraEstocastico = true;
			vendaEstocastico = true;
		}

		if(conf.getQtRSI() > 0){

			if (qtifr[dataAtual - 1].getClose() < conf.getOversoldRsi()
			&& (qtifr[dataAtual - 1].getClose() <= qtifr[dataAtual].getClose()
			&& qtifr[dataAtual - 2].getClose() >= qtifr[dataAtual - 1].getClose())){

				compraIFR = true;					
			}

			if (qtifr[dataAtual - 1].getClose() > conf.getOverboughtRsi()
			&& (qtifr[dataAtual - 1].getClose() >= qtifr[dataAtual].getClose()
			&& qtifr[dataAtual - 2].getClose() <= qtifr[dataAtual - 1].getClose())){

				vendaIFR = true;					
			}
		}
		else{
			compraIFR = true;
			vendaIFR = true;
		}

		if(conf.getQtShortMacd() > 0){

			if(conf.isCrossMacd()){
				if (qtmacd[dataAtual].getClose() < qtmacdsinal[dataAtual].getClose() 
				&&  qtmacd[dataAtual - 1].getClose() > qtmacdsinal[dataAtual - 1].getClose()){
					vendaMACD = true;
				} 
				else if (qtmacd[dataAtual].getClose() > qtmacdsinal[dataAtual].getClose()
					 &&  qtmacd[dataAtual - 1].getClose() < qtmacdsinal[dataAtual - 1].getClose()){
					compraMACD = true;
				}	
			}
			else{

				if (qtmacd[dataAtual].getClose() > qtmacdsinal[dataAtual].getClose() //acima
				&& (qtmacdHistograma[dataAtual - 1].getClose() >= qtmacdHistograma[dataAtual].getClose()
				&& qtmacdHistograma[dataAtual - 2].getClose() <= qtmacdHistograma[dataAtual - 1].getClose())){
					vendaMACD = true;
				} else if (qtmacd[dataAtual].getClose() < qtmacdsinal[dataAtual].getClose() //abaixo
					   &&(qtmacdHistograma[dataAtual - 1].getClose() <= qtmacdHistograma[dataAtual].getClose()
					   && qtmacdHistograma[dataAtual - 2].getClose() >= qtmacdHistograma[dataAtual - 1].getClose())){
					compraMACD = true;
				}						
			}
		}
		else{
			compraMACD = true;
			vendaMACD = true;
		}

		if(conf.getQtSMA1() > 0){

			if(qtmmesimples[dataAtual].getClose() 	  <= qtmmesimples[dataAtual -1].getClose()
			&& qtmmesimples[dataAtual - 1].getClose() >= qtmmesimples[dataAtual -2].getClose()){

				vendaMME = true;

			} else if(qtmmesimples[dataAtual].getClose() 	 >= qtmmesimples[dataAtual -1].getClose()
				   && qtmmesimples[dataAtual - 1].getClose() <= qtmmesimples[dataAtual -2].getClose()){

				compraMME = true;
			}
		}
		else if(conf.getQtSMA2() > 0){

			if(qtmmecruzCurto[dataAtual - 1].getClose() <= qtmmecruzLongo[dataAtual - 1].getClose()
			&& qtmmecruzCurto[dataAtual].getClose() >= qtmmecruzLongo[dataAtual].getClose()){

				compraMME = true;

			}else if(qtmmecruzCurto[dataAtual - 1].getClose() >= qtmmecruzLongo[dataAtual - 1].getClose()
				  && qtmmecruzCurto[dataAtual].getClose() <= qtmmecruzLongo[dataAtual].getClose()){

				vendaMME = true;
			}
		}
		else{
			compraMME = true;
			vendaMME = true;
		}

		compra = (compraOBV && compraEstocastico && compraMACD && compraIFR && compraMME);
		venda  = (vendaOBV && vendaEstocastico && vendaMACD && vendaIFR && vendaMME);
		
		if(compra && !venda){
			return historico.getClose();
		}
		else{
			return 0F;
		}
	}
	
	public float regraVenda(Quote historico, int dataAtual) {

		boolean compra = false;
		boolean venda = false;
		boolean compraOBV = false;
		boolean vendaOBV = false;
		boolean compraEstocastico = false;
		boolean vendaEstocastico = false;
		boolean compraIFR = false;
		boolean vendaIFR = false;
		boolean compraMACD = false;
		boolean vendaMACD = false;
		boolean compraMME = false;
		boolean vendaMME = false;

		if(conf.isObv()){

			if(tendenciaObv[dataAtual].equals("A")){
				compraOBV = true;
			}
			else if(tendenciaObv[dataAtual].equals("B")){
				vendaOBV = true;
			}
		}
		else{
			compraOBV = true;
			vendaOBV = true;
		}

		if(conf.getQtKStochastic() > 0){

			if(conf.isCrossStoch()){
				//Compra e venda no cruzamente de %k com %d
				if (estocK[dataAtual].getClose() <= estocD[dataAtual].getClose()
				&& estocK[dataAtual - 1].getClose() >= estocD[dataAtual - 1].getClose()){

					vendaEstocastico = true;					
				}

				if (estocK[dataAtual].getClose() >= estocD[dataAtual].getClose()
				&& estocK[dataAtual - 1].getClose() <= estocD[dataAtual - 1].getClose()){

					compraEstocastico = true;					
				}						
			}
			else{
				//Compra estocastico sobrevendido e vira pra cima
				if (estocK[dataAtual - 1].getClose() < conf.getOversoldStoch()
				&&  estocK[dataAtual].getClose() > estocK[dataAtual - 1].getClose()
				&&  estocK[dataAtual - 1].getClose() < estocK[dataAtual - 2].getClose()){

					compraEstocastico = true;					
				}

				//Venda estocastico sobrecomprado e vira pra baixo
				if (estocK[dataAtual - 1].getClose() > conf.getOverboughtStoch()
						&&  estocK[dataAtual].getClose() < estocK[dataAtual - 1].getClose()
						&&  estocK[dataAtual - 1].getClose() > estocK[dataAtual - 2].getClose()){

					vendaEstocastico = true;					
				}
			}
		}
		else{
			compraEstocastico = true;
			vendaEstocastico = true;
		}

		if(conf.getQtRSI() > 0){

			if (qtifr[dataAtual - 1].getClose() < conf.getOversoldRsi()
			&& (qtifr[dataAtual - 1].getClose() <= qtifr[dataAtual].getClose()
			&& qtifr[dataAtual - 2].getClose() >= qtifr[dataAtual - 1].getClose())){

				compraIFR = true;					
			}

			if (qtifr[dataAtual - 1].getClose() > conf.getOverboughtRsi()
			&& (qtifr[dataAtual - 1].getClose() >= qtifr[dataAtual].getClose()
			&& qtifr[dataAtual - 2].getClose() <= qtifr[dataAtual - 1].getClose())){

				vendaIFR = true;					
			}
		}
		else{
			compraIFR = true;
			vendaIFR = true;
		}

		if(conf.getQtShortMacd() > 0){

			if(conf.isCrossMacd()){
				if (qtmacd[dataAtual].getClose() < qtmacdsinal[dataAtual].getClose() 
				&&  qtmacd[dataAtual - 1].getClose() > qtmacdsinal[dataAtual - 1].getClose()){
					vendaMACD = true;
				} 
				else if (qtmacd[dataAtual].getClose() > qtmacdsinal[dataAtual].getClose()
					 &&  qtmacd[dataAtual - 1].getClose() < qtmacdsinal[dataAtual - 1].getClose()){
					compraMACD = true;
				}	
			}
			else{

				if (qtmacd[dataAtual].getClose() > qtmacdsinal[dataAtual].getClose() //acima
				&& (qtmacdHistograma[dataAtual - 1].getClose() >= qtmacdHistograma[dataAtual].getClose()
				&& qtmacdHistograma[dataAtual - 2].getClose() <= qtmacdHistograma[dataAtual - 1].getClose())){
					vendaMACD = true;
				} else if (qtmacd[dataAtual].getClose() < qtmacdsinal[dataAtual].getClose() //abaixo
					   &&(qtmacdHistograma[dataAtual - 1].getClose() <= qtmacdHistograma[dataAtual].getClose()
					   && qtmacdHistograma[dataAtual - 2].getClose() >= qtmacdHistograma[dataAtual - 1].getClose())){
					compraMACD = true;
				}						
			}
		}
		else{
			compraMACD = true;
			vendaMACD = true;
		}

		if(conf.getQtSMA1() > 0){

			if(qtmmesimples[dataAtual].getClose() 	  <= qtmmesimples[dataAtual -1].getClose()
			&& qtmmesimples[dataAtual - 1].getClose() >= qtmmesimples[dataAtual -2].getClose()){

				vendaMME = true;

			} else if(qtmmesimples[dataAtual].getClose() 	 >= qtmmesimples[dataAtual -1].getClose()
				   && qtmmesimples[dataAtual - 1].getClose() <= qtmmesimples[dataAtual -2].getClose()){

				compraMME = true;
			}
		}
		else if(conf.getQtSMA2() > 0){

			if(qtmmecruzCurto[dataAtual - 1].getClose() <= qtmmecruzLongo[dataAtual - 1].getClose()
			&& qtmmecruzCurto[dataAtual].getClose() >= qtmmecruzLongo[dataAtual].getClose()){

				compraMME = true;

			}else if(qtmmecruzCurto[dataAtual - 1].getClose() >= qtmmecruzLongo[dataAtual - 1].getClose()
				  && qtmmecruzCurto[dataAtual].getClose() <= qtmmecruzLongo[dataAtual].getClose()){

				vendaMME = true;
			}
		}
		else{
			compraMME = true;
			vendaMME = true;
		}

		compra = (compraOBV && compraEstocastico && compraMACD && compraIFR && compraMME);
		venda  = (vendaOBV && vendaEstocastico && vendaMACD && vendaIFR && vendaMME);
		
		if(!compra && venda){
			return historico.getClose();
		}
		else{
			return 0F;
		}
	}
}
