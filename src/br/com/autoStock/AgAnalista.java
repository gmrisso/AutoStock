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

package br.com.autoStock;

import java.util.Date;

import javax.swing.JOptionPane;

import com.mf4j.Quote;
import br.com.autoStock.strategy.Regras;
import br.com.autoStock.util.I18n;
import br.com.autoStock.util.Property;
import br.com.autoStock.util.entity.Configuration;
import br.com.autoStock.util.entity.PeriodMap;
import br.com.autoStock.util.entity.Trade;
import br.com.autoStock.util.exception.PeriodException;
import br.com.autoStock.util.exception.StockException;

public class AgAnalista{

	private static final long serialVersionUID = 1L;
	
	private String nmAgente;

	private Quote[] quotes;
	private PeriodMap historico;
	private Principal sistema;
	private Configuration conf;
	private boolean simulacao;
	private boolean stop;

	public int confIdconfiguracao;
	
	public AgAnalista(String nome, 
			Principal sistema,
			Configuration conf ,
			boolean simulacao){
		
		this.nmAgente = nome;
		this.sistema = sistema;
		this.conf = conf;
		this.simulacao = simulacao;
		
		try {
			Regras estrategia = (Regras) Class.forName("br.com.autoStock.strategy." + Property.getProperty('S')).newInstance();
			
			this.conf.setQtSMA1(estrategia.getConfiguration().getQtSMA1(), estrategia.getConfiguration().getCatSMA1());
			this.conf.setQtSMA2(estrategia.getConfiguration().getQtSMA2(), estrategia.getConfiguration().getCatSMA2());
			this.conf.setQtSMA3(estrategia.getConfiguration().getQtSMA3(), estrategia.getConfiguration().getCatSMA3());
			this.conf.setQtSMA4(estrategia.getConfiguration().getQtSMA4(), estrategia.getConfiguration().getCatSMA4());
			this.conf.setQtSMA5(estrategia.getConfiguration().getQtSMA5(), estrategia.getConfiguration().getCatSMA5());
			this.conf.setQtBB(estrategia.getConfiguration().getQtBB());
			this.conf.setDvBB(estrategia.getConfiguration().getDvBB());
			this.conf.setQtLongMacd(estrategia.getConfiguration().getQtLongMacd());
			this.conf.setQtShortMacd(estrategia.getConfiguration().getQtShortMacd());
			this.conf.setQtSignalMacd(estrategia.getConfiguration().getQtSignalMacd());
			this.conf.setCrossMacd(estrategia.getConfiguration().isCrossMacd());
			this.conf.setQtRSI(estrategia.getConfiguration().getQtRSI());
			this.conf.setQtSMARSI1(estrategia.getConfiguration().getQtSMARSI1(), estrategia.getConfiguration().getCatSMARSI1());
			this.conf.setQtSMARSI2(estrategia.getConfiguration().getQtSMARSI2(), estrategia.getConfiguration().getCatSMARSI2());
			this.conf.setQtSMARSI3(estrategia.getConfiguration().getQtSMARSI3(), estrategia.getConfiguration().getCatSMARSI3());
			this.conf.setOverboughtRsi(estrategia.getConfiguration().getOverboughtRsi());
			this.conf.setOversoldRsi(estrategia.getConfiguration().getOversoldRsi());
			this.conf.setVolume(estrategia.getConfiguration().isVolume());
			this.conf.setQtSMAVol1(estrategia.getConfiguration().getQtSMAVol1(), estrategia.getConfiguration().getCatSMAVol1());
			this.conf.setQtSMAVol2(estrategia.getConfiguration().getQtSMAVol2(), estrategia.getConfiguration().getCatSMAVol2());
			this.conf.setQtSMAVol3(estrategia.getConfiguration().getQtSMAVol3(), estrategia.getConfiguration().getCatSMAVol3());
			this.conf.setObv(estrategia.getConfiguration().isObv());
			this.conf.setQtSMAOBV1(estrategia.getConfiguration().getQtSMAOBV1(), estrategia.getConfiguration().getCatSMAOBV1());
			this.conf.setQtSMAOBV2(estrategia.getConfiguration().getQtSMAOBV2(), estrategia.getConfiguration().getCatSMAOBV2());
			this.conf.setQtSMAOBV3(estrategia.getConfiguration().getQtSMAOBV3(), estrategia.getConfiguration().getCatSMAOBV3());
			this.conf.setQtKStochastic(estrategia.getConfiguration().getQtKStochastic());
			this.conf.setQtDStochastic(estrategia.getConfiguration().getQtDStochastic());
			this.conf.setQtLowStoch(estrategia.getConfiguration().getQtLowStoch());
			this.conf.setLowStoch(estrategia.getConfiguration().isLowStoch());
			this.conf.setCrossStoch(estrategia.getConfiguration().isCrossStoch());			
			this.conf.setDmi(estrategia.getConfiguration().getQtPeriodDmi(),estrategia.getConfiguration().getQtAvgDmi());
			
			this.conf.setDidi(estrategia.getConfiguration().getQtShortDidi(), estrategia.getConfiguration().getQtMediumDidi(), estrategia.getConfiguration().getQtLongDidi());
			this.conf.setOverboughtStoch(estrategia.getConfiguration().getOverboughtStoch());
			this.conf.setOversoldStoch(estrategia.getConfiguration().getOversoldStoch());
						
			Thread analista = new Thread(new Analista(estrategia));			
			analista.start();
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	private class Analista implements Runnable{
		
		Regras estrategia;
		
		public Analista(Regras estrategia){
			this.estrategia = estrategia;			
		}
		
		//@Override
		public void run() {	
			
			stop = false;
			
			while(!stop){
				
				try {
					historico = sistema.getHistorico(conf);
					
					switch (conf.getPeriod()) {
					case 'D':
						quotes = historico.getQuoteDay();
						break;
					case 'W':
						quotes = historico.getQuoteWeek();
						break;
					case 'M':
						quotes = historico.getQuoteMonth();
						break;
					}
					
				} catch (StockException e1) {
					e1.printStackTrace();
				} catch (PeriodException e1) {
					e1.printStackTrace();
				}
				
				estrategia. init(conf, quotes);

				analisarAcao();

				if(simulacao){
					//doDelete();
					break;
				}
				else{
					try {
						Thread.sleep(new Long(Property.getProperty('I')).longValue());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}				
			}						
		}
		
		private void analisarAcao(){

			int inicio;
			
			if(simulacao){
				inicio = estrategia.getCandleInicial();
			}
			else{
				inicio = quotes.length - 1;
			}
			
			for (int i = inicio; i < quotes.length; i++){
				
				float vlCompra = estrategia.regraCompra(quotes[i], i);
				float vlVenda = estrategia.regraVenda(quotes[i], i);
										
				if(vlCompra > 0){
					
					if(!simulacao){
						sistema.mostrarMensagem(false, I18n.getMessage("ai") +quotes[i].getCodeStock()
								+I18n.getMessage("aj") + I18n.getMessage("ak") + vlCompra);
					}
					
					informarAnalise(simulacao,
							i,
							quotes[i].getDate(),
							true,
							vlCompra);
				} 
				
				if(vlVenda > 0){					
					
					if(!simulacao){
						sistema.mostrarMensagem(false, I18n.getMessage("ai") +quotes[i].getCodeStock()
								+ I18n.getMessage("al") + I18n.getMessage("ak") + vlVenda);
					}
					
					informarAnalise(simulacao,
							i,
							quotes[i].getDate(),
							false,
							vlVenda);		
				}			
			}			
			
			if(simulacao){								
				listarTradesSimulacao();
				sistema.mostrarMensagem(true, I18n.getMessage("am") + nmAgente);
			}
		}
	}
	
	public void reset(){
		stop = true;
	}

	public void informarAnalise(boolean simulacao, int indexBS,  Date date, boolean isBuy, float value){
				
		conf.addTrade(isBuy, indexBS, date, value);

		if(!simulacao){
			
			if(receberAnalise(isBuy, value)){
				sistema.mostrarMensagem(false, I18n.getMessage("an") 
						+ I18n.getMessage("as") + conf.getStockCode() 
						+ " " + (isBuy?I18n.getMessage("aq"):I18n.getMessage("ar")) + value);
			}
			else{
				sistema.mostrarMensagem(false, I18n.getMessage("ar")
						+ I18n.getMessage("as") + conf.getStockCode() 
						+ " " + (isBuy?I18n.getMessage("aq"):I18n.getMessage("ar")) + value);
			}

		}			
	}	
	
	private void listarTradesSimulacao(){
		
		int qttradesucesso = 0;
		float percacerto = 0f;
		float vlCarteiraTeorica = 100f;
		
		Trade[] trades = conf.getTrades().toArray(new Trade[0]);

		for (int i = 0; i < trades.length; i++){
			
			if(trades[i].getDtBuy() == null){
				
				trades[i].setVlBuy(quotes.length - 1, quotes[quotes.length - 1].getDate(), quotes[quotes.length - 1].getClose());
				       
				informarAnalise(true,
						trades[i].getIndexBuy(),
						quotes[quotes.length - 1].getDate(), 
						true,
						quotes[quotes.length - 1].getClose());
			}

			if(trades[i].getDtSale() == null){
				
				trades[i].setVlSale(quotes.length - 1, quotes[quotes.length - 1].getDate(), quotes[quotes.length - 1].getClose());
				
				informarAnalise(true, 
						trades[i].getIndexSale(),
						quotes[quotes.length - 1].getDate(), 
						false,
						quotes[quotes.length - 1].getClose());
				
			}		

			vlCarteiraTeorica += vlCarteiraTeorica * trades[i].getPcProfit();

			if(trades[i].getPcProfit() > 0){
				qttradesucesso++;
			}

			percacerto += trades[i].getPcProfit();
		}
		
		conf.setPcSucessTrade(new Integer(qttradesucesso).floatValue() / trades.length);
		conf.setPercAccuracy(percacerto);	

	}
	
	public boolean receberAnalise(boolean isBuy, float value){

		String mensagem = "";

		if(isBuy){					
			mensagem = I18n.getMessage("ai") +conf.getStockCode() + I18n.getMessage("aj") + I18n.getMessage("ak") + value;
		}
		else{
			mensagem = I18n.getMessage("ai") +conf.getStockCode() + I18n.getMessage("al") + I18n.getMessage("ak") + value;
		}

		int resposta = JOptionPane.showConfirmDialog( null,mensagem + "\n" + I18n.getMessage("ap") + "?", "",JOptionPane.YES_NO_OPTION);

		if(resposta == JOptionPane.YES_OPTION){
			
			return true;
		}
		
		return false;
	}

	public Configuration getConf() {
		return conf;
	}	
}
