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

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import br.com.autoStock.ui.MainScreen;
import br.com.autoStock.util.I18n;
import br.com.autoStock.util.entity.Configuration;
import br.com.autoStock.util.entity.PeriodMap;
import br.com.autoStock.util.entity.Stock;
import br.com.autoStock.util.entity.StockList;
import br.com.autoStock.util.exception.PeriodException;
import br.com.autoStock.util.exception.StockException;

public class Principal{
	
	static Logger logger = Logger.getLogger(Principal.class.getName());

	private static final long serialVersionUID = 1L;
	
	private static MainScreen screen;
	
	private Bovespa bovespa;
	
	private AgAnalista[] agenteSimul;
	private AgAnalista[] agenteAcomp;
		
	public Principal(){
		

		/*Splash splash = new Splash("image/splash.gif");
	    splash.splash();
	    try {
	      Thread.sleep(1000);
	    }
	    catch(InterruptedException ex) {
	    
	    } 
	    
	    splash.dispose();*/
		
		bovespa = new Bovespa();
					
		screen = new MainScreen(this);
		
		try {
			FileInputStream fis = new FileInputStream("stockList.lst");
			ObjectInputStream ois;
			ois = new ObjectInputStream(fis);
			StockList sl = (StockList) ois.readObject();			
	        ois.close();
	        
	        screen.setStock(sl.getAllStock());
	        
		} catch (Exception e) {
			
			Stock[] stock = bovespa.listStock();
			
			if(stock.length > 0){
				screen.setStock(stock);
			}	
		}
			
	}

	public void simulateConf(ArrayList<Configuration> conf) 
	{
		screen.setSimulationReport(I18n.getMessage("ab"), true);
		
		agenteSimul = new AgAnalista[conf.size()];

		for (int i = 0; i < conf.size(); i++) {

			agenteSimul[i] = new AgAnalista(conf.get(i).getStockCode()+"_"+i,
												 this,
												 conf.get(i),
												 true);
		}
	}
	
	public Configuration getAgenteSimulacao(int index){
		
		return agenteSimul[index].getConf();
	}

	public void acompanharAtivos(ArrayList<Configuration> confAcompanhamento) 
	{	
		screen.setSimulationReport(I18n.getMessage("ac"), true);
		
		agenteAcomp = new AgAnalista[confAcompanhamento.size()];
		
		for (int i = 0; i < confAcompanhamento.size(); i++) {
			
			agenteAcomp[i] = new AgAnalista(confAcompanhamento.get(i).getStockCode()+"_"+ i,
												 this,
												 confAcompanhamento.get(i),
												 false);

		}
	}
	
	public Configuration getAgenteAcompanhamento(int index){
		
		return agenteAcomp[index].getConf();
	}
	
	public void resetAcompanhamento(){
		
		for (int i = 0; i < agenteAcomp.length; i++) {
			
			agenteAcomp[i].reset();

		}
		
		agenteAcomp = null;
	}

	public PeriodMap getHistorico(Configuration conf)
	throws StockException, PeriodException{
				
		PeriodMap historico = bovespa.listPriceStock(conf.getStockCode().trim(), conf.getDtStart(), conf.getDtEnd());
		
		if(historico.getQuoteDay().length > 0){
			return historico;
			
		}
		
		throw new StockException(conf.getStockCode());
		
	}

	public void mostrarMensagem(boolean simulacao, String mensagem){		
		screen.setSimulationReport(mensagem, false);
	}
}