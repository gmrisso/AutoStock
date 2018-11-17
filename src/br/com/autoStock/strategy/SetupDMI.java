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
import br.com.autoStock.indicator.Dmi;
import br.com.autoStock.util.entity.Configuration;

import com.mf4j.Quote;

/*
 * Setup IFR2 + MMA13
 * 1) Procurar papéis que o ifr2 esteja abaixo da mma13 do ifr;
 * 2) Quando ifr2 rompe a mma13, marcar máxima do candle pra compra e stop na mínima;
 * 3) Comprar no rompimento da máxima marcada;
 * 4) Ajustar stop para a mínima do candle quando a mma5 dos preços virar para baixo
 */

public class SetupDMI  extends Regras {
	
	private Quote[] quotes;
	
	private DataIndicator[][] dmi;
	
	private float pcCompra;
	private float pcStop;
	private int dataStop;
	
	@Override
	public void init(Configuration conf, Quote[] quotes) {
		
		this.quotes = quotes;
		
		Dmi d = new Dmi();
		dmi = d.calcularDMI(getDado(), 8, 8);		
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

	public Configuration getConfiguration(){
		
		Configuration conf = new Configuration();
		conf.setDmi(8, 8);
		conf.setDidi(3, 8,20);
		
		return conf;		
	}

	@Override
	public float regraCompra(Quote historico, int dataAtual) {
		
		//Abaixo de 32 a tendencia é fraca
		// Adx < D+ e D- não tem tendencia
		if((dmi[0][dataAtual].getClose() < 32) 
		|| ((dmi[0][dataAtual].getClose() < dmi[1][dataAtual].getClose() && dmi[0][dataAtual].getClose() < dmi[2][dataAtual].getClose()))){
			return 0F;
		}
		
		// Quique de compra
		if ((dmi[0][dataAtual].getClose() < dmi[0][dataAtual - 1].getClose())
		&& (dmi[0][dataAtual - 1].getClose() > dmi[0][dataAtual - 2].getClose())
		&& (dmi[0][dataAtual - 2].getClose() > dmi[0][dataAtual - 3].getClose())
		&& (dmi[2][dataAtual].getClose() > dmi[1][dataAtual].getClose()))
		{
			pcCompra = quotes[dataAtual].getClose();
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

	@Override
	public float regraVenda(Quote historico, int dataAtual) {
				
		if ((dmi[0][dataAtual].getClose() < dmi[0][dataAtual - 1].getClose())
		&& (dmi[0][dataAtual - 1].getClose() > dmi[0][dataAtual - 2].getClose())
		&& (dmi[2][dataAtual].getClose() < dmi[1][dataAtual].getClose())){
			pcStop = quotes[dataAtual].getLow();
		}
		
		if(pcStop >= historico.getLow()
		&& pcStop <= historico.getHigh()
		&& (dataStop < dataAtual || historico.getClose() < historico.getOpen())){
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
		return 10;
	}

}
