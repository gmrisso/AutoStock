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

import br.com.autoStock.indicator.DataIndicator;
import br.com.autoStock.indicator.MediaMovel;
import br.com.autoStock.util.entity.Configuration;

import com.mf4j.Quote;

/*
 * Setup MM9.1
 * 1) Procurar algum papel que a MM9 esteja apontando para baixo;
 * 2) Aguardar que a MM9 vire para cima;
 * 3) Marcar a máxima do candle que fez a MME9 virar;
 * 4) Efetuar a compra assim que for feito qualquer negócio acima do valor dessa máxima;
 * 5) O Stop Loss fica abaixo da mínima do candle que fez a MME9 virar.
 * 6) Objetivos de Venda: Realização Parcial: Position: 8% / Swing Trade: 3,5% / Daytrade: 1% - Efetividade: 78%. 
 * Vender a posição remanescente quando for feito qualquer negócio abaixo da mínima do canlde que fizer a MM9 virar 
 * outra vez.
 */

public class SetupMME91 extends Regras {
	
	private DataIndicator[] mme9;
	private MediaMovel media;
	private Quote[] quotes;
	private float pcCompra;
	private float pcStop;
	private int dataStop;

 	/*
 	 * Inicialização de variáveis
 	 * @see br.ucs.tcc.estrategia.Regras#init(br.ucs.tcc.indicador.Dado[])
 	 */
	public void init(Configuration conf, Quote[] quotes){
		this.quotes = quotes;
		this.media = new MediaMovel();		
		this.mme9 = this.media.calcularMME(getDado(),9);
		this.pcCompra = 0;
		this.pcStop = 0;		
	}
	
	public Configuration getConfiguration(){
		
		Configuration conf = new Configuration();
		
		conf.setQtSMA1(9, 'e');
		
		return conf;		
	}
	
	private DataIndicator[] getDado(){
		
		DataIndicator[] dado = new DataIndicator[quotes.length];
		
		for (int i = 0; i < quotes.length; i++) {
			dado[i] = new DataIndicator();
			dado[i].setIndex(quotes[i].getDate().getTime());			
			dado[i].setClose(quotes[i].getClose());
			dado[i].setOpen(quotes[i].getOpen());
			dado[i].setHigh(quotes[i].getHigh());
			dado[i].setLow(quotes[i].getLow());
			dado[i].setVolume(quotes[i].getVolume());
		}
		
		return dado;
	}
	
	/*
	 * Compra acontece quando a mme[9] vira pra cima e o preço rompe a máxima do candle que virou pra cima.
	 * @see br.ucs.tcc.estrategia.Regras#regraCompra(br.ucs.tcc.entity.HistoricoNegociacao, int)
	 */
	@Override
	public float regraCompra(Quote historico, int dataAtual) {
			
		if(mme9[dataAtual - 1].getClose() > mme9[dataAtual - 2].getClose()
		&& mme9[dataAtual - 2].getClose() < mme9[dataAtual - 3].getClose()
		&& pcStop == 0F){
			
			pcCompra = quotes[dataAtual - 1].getHigh() + 0.01F;
			pcStop = quotes[dataAtual - 1].getLow() - 0.01F;
			dataStop = dataAtual;
		}
		
		if(pcCompra >= historico.getLow()
		&& pcCompra <= historico.getHigh()
		&& pcCompra > 0F){
			
			float aux = pcCompra;
			pcCompra = 0F;
			
			return aux;
		}else if(pcCompra < historico.getLow()
				&& pcCompra > 0F){ //gap
			
			pcCompra = 0F;
			
			return historico.getOpen();
		}
		
		return 0F;
	}
	
	/*
	 * Stop se perder a mínima do candle que virou a mme[9] pra cima;
	 * Venda quando perder a mínima do candle que a média vira para baixo;
	 * @see br.ucs.tcc.estrategia.Regras#regraVenda(br.ucs.tcc.entity.HistoricoNegociacao, int)
	 */
	@Override
	public float regraVenda(Quote historico, int dataAtual) {
		
		if(mme9[dataAtual - 1].getClose() < mme9[dataAtual - 2].getClose()
		&& mme9[dataAtual - 2].getClose() > mme9[dataAtual - 3].getClose()){

			pcStop = quotes[dataAtual - 1].getLow() - 0.01F;

		}
		
		if(pcStop >= historico.getLow()
		&& pcStop <= historico.getHigh()
		&& (dataStop < dataAtual || historico.getClose() < historico.getOpen())
		&& mme9[dataAtual].getClose() < mme9[dataAtual - 1].getClose()){
			float aux = pcStop;
			pcStop = 0F;
			return aux;
		}else if(pcStop > historico.getHigh()
				&& (dataStop < dataAtual || historico.getClose() < historico.getOpen())){ //gap			
			pcStop = 0F;
			return historico.getHigh();
		}		
		
		return 0;
	}
	
	public int getCandleInicial() {		
		return 12;
	}
}
