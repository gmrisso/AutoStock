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

package br.com.autoStock.util.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jfree.chart.annotations.AbstractXYAnnotation;

import br.com.autoStock.util.I18n;
import br.com.autoStock.util.Property;

public class Configuration implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(Configuration.class.getName());
	
	private ArrayList<Trade> trades;
	
	private String stockCode;
	private Date dtStart;
	private Date dtEnd;
	private char period;
	
	private int qtSMA1;
	private char catSMA1;
	private int qtSMA2;
	private char catSMA2;
	private int qtSMA3;
	private char catSMA3;
	private int qtSMA4;
	private char catSMA4;
	private int qtSMA5;
	private char catSMA5;
	
	private int qtBB;
	private int dvBB;
		
	private int qtLongMacd;
	private int qtShortMacd;
	private int qtSignalMacd;
	private boolean isCrossMacd;
	
	private int qtRSI;
	private int qtSMARSI1;
	private char catSMARSI1;
	private int qtSMARSI2;
	private char catSMARSI2;
	private int qtSMARSI3;
	private char catSMARSI3;
	private int overboughtRsi;
	private int oversoldRsi;
	
	private boolean isVolume;
	private int qtSMAVol1;
	private char catSMAVol1;
	private int qtSMAVol2;
	private char catSMAVol2;
	private int qtSMAVol3;
	private char catSMAVol3;
	
	private boolean isObv;	
	private int qtSMAOBV1;
	private char catSMAOBV1;
	private int qtSMAOBV2;
	private char catSMAOBV2;
	private int qtSMAOBV3;
	private char catSMAOBV3;
	
	private int qtKStochastic;
	private int qtDStochastic;
	private int qtLowStoch;
	private boolean isLowStoch;
	private boolean isCrossStoch;
	private int overboughtStoch;
	private int oversoldStoch;
	
	private int qtPeriodDmi;
	private int qtAvgDmi;
	
	private int qtShortDidi;
	private int qtMediumDidi;
	private int qtLongDidi;
	
	private float percAccuracy;
	private float pcSucessTrade;
	
	private ArrayList<AbstractXYAnnotation> annotations;
			
	public Configuration(String stockCode){
		
		logger.debug(I18n.getMessage("bk"));
		
		this.stockCode = stockCode;
		
		trades = new ArrayList<Trade>();
	}

	/*public ConfigurationS(String stockCode,
			Date dtStart,
			Date dtEnd,
			char period,
			int qtSMA1,
			char catSMA1,
			int qtSMA2,
			char catSMA2,
			int qtSMA3,
			char catSMA3,
			int qtSMA4,
			char catSMA4,
			int qtSMA5,
			char catSMA5,
			int qtBB,
			int dvBB,
			int qtLongMacd,
			int qtShortMacd,
			int qtSignalMacd,
			boolean isCrossMacd,
			int qtRSI,
			int qtSMARSI1,
			char catSMARSI1,
			int qtSMARSI2,
			char catSMARSI2,
			int qtSMARSI3,
			char catSMARSI3,
			int overboughtRsi,
			int oversoldRsi,
			boolean isVolume,
			int qtSMAVol1,
			char catSMAVol1,
			int qtSMAVol2,
			char catSMAVol2,
			int qtSMAVol3,
			char catSMAVol3,
			boolean isObv,
			int qtSMAOBV1,
			char catSMAOBV1,
			int qtSMAOBV2,
			char catSMAOBV2,
			int qtSMAOBV3,
			char catSMAOBV3,
			int qtKStochastic,
			int qtDStochastic,
			int qtLowStoch,
			boolean isLowStoch,
			boolean isCrossStoch,
			int overboughtStoch,
			int oversoldStoch,
			int qtPeriodDmi,
			int qtAvgDmi,			
			int qtShortDidi,
			int qtMediumDidi,
			int qtLongDidi,
			float percAccuracy,
			float pcSucessTrade){
		
		this.stockCode = stockCode;
		this.dtStart = dtStart;
		this.dtEnd = dtEnd;
		this.period = period;
		this.qtSMA1 = qtSMA1;
		this.catSMA1 = catSMA1;
		this.qtSMA2 = qtSMA2;
		this.catSMA2 = catSMA2;
		this.qtSMA3 = qtSMA3;
		this.catSMA3 = catSMA3;
		this.qtSMA4 = qtSMA4;
		this.catSMA4 = catSMA4;
		this.qtSMA5 = qtSMA5;
		this.catSMA5 = catSMA5;
		this.qtBB = qtBB;
		this.dvBB = dvBB;
		this.qtLongMacd = qtLongMacd;
		this.qtShortMacd = qtShortMacd;
		this.qtSignalMacd = qtSignalMacd;
		this.isCrossMacd = isCrossMacd;		
		this.qtRSI = qtRSI;
		this.qtSMARSI1 = qtSMARSI1;
		this.catSMARSI1 = catSMARSI1;
		this.qtSMARSI2 = qtSMARSI2;
		this.catSMARSI2 = catSMARSI2;
		this.qtSMARSI3 = qtSMARSI3;
		this.catSMARSI3 = catSMARSI3;
		this.overboughtRsi = overboughtRsi;
		this.oversoldRsi = oversoldRsi;
		this.isVolume = isVolume;
		this.qtSMAVol1 = qtSMAVol1;
		this.catSMAVol1 = catSMAVol1;
		this.qtSMAVol2 = qtSMAVol2;
		this.catSMAVol2 = catSMAVol2;
		this.qtSMAVol3 = qtSMAVol3;
		this.catSMAVol3 = catSMAVol3;
		this.isObv = isObv;
		this.qtSMAOBV1 = qtSMAOBV1;
		this.catSMAOBV1 = catSMAOBV1;
		this.qtSMAOBV2 = qtSMAOBV2;
		this.catSMAOBV2 = catSMAOBV2;
		this.qtSMAOBV3 = qtSMAOBV3;
		this.catSMAOBV3 = catSMAOBV3;
		this.qtKStochastic = qtKStochastic;
		this.qtDStochastic = qtDStochastic;
		this.qtLowStoch = qtLowStoch;
		this.isLowStoch = isLowStoch;
		this.isCrossStoch = isCrossStoch;
		this.overboughtStoch = overboughtStoch;
		this.oversoldStoch = oversoldStoch;
		this.qtPeriodDmi = qtPeriodDmi;
		this.qtAvgDmi = qtAvgDmi;		
		this.qtShortDidi = qtShortDidi;
		this.qtMediumDidi = qtMediumDidi;
		this.qtLongDidi = qtLongDidi;
		this.percAccuracy = percAccuracy;
		this.pcSucessTrade = pcSucessTrade;
		
		trades = new ArrayList<Trade>();

	}*/
	
	public Configuration(){
		trades = new ArrayList<Trade>();
	}
	
	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public int getQtSMA1() {
		return qtSMA1;
	}
	
	public char getCatSMA1(){
		return this.catSMA1;
	}

	public void setQtSMA1(int qtSMA1, char catSMA1) {
		this.qtSMA1 = qtSMA1;
		this.catSMA1 = catSMA1;
	}
	
	public int getQtSMA2() {
		return qtSMA2;
	}
	
	public char getCatSMA2(){
		return this.catSMA2;
	}

	public void setQtSMA2(int qtSMA2, char catSMA2) {
		this.qtSMA2 = qtSMA2;
		this.catSMA2 = catSMA2;
	}
	
	public int getQtSMA3() {
		return qtSMA3;
	}
	
	public char getCatSMA3(){
		return this.catSMA3;
	}

	public void setQtSMA3(int qtSMA3, char catSMA3) {
		this.qtSMA3 = qtSMA3;
		this.catSMA3 = catSMA3;
	}
	
	public int getQtSMA4() {
		return qtSMA4;
	}
	
	public char getCatSMA4(){
		return this.catSMA4;
	}

	public void setQtSMA4(int qtSMA4, char catSMA4) {
		this.qtSMA4 = qtSMA4;
		this.catSMA4 = catSMA4;
	}
	
	public int getQtSMA5() {
		return qtSMA5;
	}
	
	public char getCatSMA5(){
		return this.catSMA5;
	}

	public void setQtSMA5(int qtSMA5, char catSMA5) {
		this.qtSMA5 = qtSMA5;
		this.catSMA5 = catSMA5;
	}

	public int getQtBB() {
		return qtBB;
	}

	public void setQtBB(int qtBB) {
		this.qtBB = qtBB;
	}

	public int getDvBB() {
		return dvBB;
	}

	public void setDvBB(int dvBB) {
		this.dvBB = dvBB;
	}

	public int getQtLongMacd() {
		return qtLongMacd;
	}

	public void setQtLongMacd(int qtLongMacd) {
		this.qtLongMacd = qtLongMacd;
	}

	public int getQtShortMacd() {
		return qtShortMacd;
	}

	public void setQtShortMacd(int qtShortMacd) {
		this.qtShortMacd = qtShortMacd;
	}

	public int getQtSignalMacd() {
		return qtSignalMacd;
	}

	public void setQtSignalMacd(int qtSignalMacd) {
		this.qtSignalMacd = qtSignalMacd;
	}

	public boolean isCrossMacd() {
		return isCrossMacd;
	}

	public void setCrossMacd(boolean isCrossMacd) {
		this.isCrossMacd = isCrossMacd;
	}

	public int getQtRSI() {
		return qtRSI;
	}

	public void setQtRSI(int qtRSI) {
		this.qtRSI = qtRSI;
	}
	
	public int getQtSMARSI1() {
		return qtSMARSI1;
	}

	public void setQtSMARSI1(int qtSMARSI1, char catSMARSI1) {
		this.qtSMARSI1 = qtSMARSI1;
		this.catSMARSI1 = catSMARSI1;
	}

	public char getCatSMARSI1() {
		return catSMARSI1;
	}
	
	public int getQtSMARSI2() {
		return qtSMARSI2;
	}

	public void setQtSMARSI2(int qtSMARSI2, char catSMARSI2) {
		this.qtSMARSI2 = qtSMARSI2;
		this.catSMARSI2 = catSMARSI2;
	}

	public char getCatSMARSI2() {
		return catSMARSI2;
	}
	
	public int getQtSMARSI3() {
		return qtSMARSI3;
	}

	public void setQtSMARSI3(int qtSMARSI3, char catSMARSI3) {
		this.qtSMARSI3 = qtSMARSI3;
		this.catSMARSI3 = catSMARSI3;
	}

	public char getCatSMARSI3() {
		return catSMARSI3;
	}

	public int getOverboughtRsi() {
		return overboughtRsi;
	}

	public void setOverboughtRsi(int overboughtRsi) {
		this.overboughtRsi = overboughtRsi;
	}

	public int getOversoldRsi() {
		return oversoldRsi;
	}

	public void setOversoldRsi(int oversoldRsi) {
		this.oversoldRsi = oversoldRsi;
	}
		
	public boolean isVolume() {
		return isVolume;
	}

	public void setVolume(boolean isVolume) {
		this.isVolume = isVolume;
	}

	public int getQtSMAVol1() {
		return qtSMAVol1;
	}

	public void setQtSMAVol1(int qtSMAVol1,char catSMAVol1) {
		this.qtSMAVol1 = qtSMAVol1;
		this.catSMAVol1 = catSMAVol1;
	}

	public char getCatSMAVol1() {
		return catSMAVol1;
	}

	public int getQtSMAVol2() {
		return qtSMAVol2;
	}

	public void setQtSMAVol2(int qtSMAVol2,char catSMAVol2) {
		this.qtSMAVol2 = qtSMAVol2;
		this.catSMAVol2 = catSMAVol2;
	}

	public char getCatSMAVol2() {
		return catSMAVol2;
	}

	public int getQtSMAVol3() {
		return qtSMAVol3;
	}

	public void setQtSMAVol3(int qtSMAVol3, char catSMAVol3) {
		this.qtSMAVol3 = qtSMAVol3;
		this.catSMAVol3 = catSMAVol3;
	}

	public char getCatSMAVol3() {
		return catSMAVol3;
	}

	public boolean isObv() {
		return isObv;
	}

	public void setObv(boolean isObv) {
		this.isObv = isObv;
	}
	
	public int getQtSMAOBV1() {
		return qtSMAOBV1;
	}

	public void setQtSMAOBV1(int qtSMAOBV1, char catSMAOBV1) {
		this.qtSMAOBV1 = qtSMAOBV1;
		this.catSMAOBV1 = catSMAOBV1;
	}

	public char getCatSMAOBV1() {
		return catSMAOBV1;
	}
	
	public int getQtSMAOBV2() {
		return qtSMAOBV2;
	}

	public void setQtSMAOBV2(int qtSMAOBV2, char catSMAOBV2) {
		this.qtSMAOBV2 = qtSMAOBV2;
		this.catSMAOBV2 = catSMAOBV2;
	}

	public char getCatSMAOBV2() {
		return catSMAOBV2;
	}
	
	public int getQtSMAOBV3() {
		return qtSMAOBV3;
	}

	public void setQtSMAOBV3(int qtSMAOBV3, char catSMAOBV3) {
		this.qtSMAOBV3 = qtSMAOBV3;
		this.catSMAOBV3 = catSMAOBV3;
	}

	public char getCatSMAOBV3() {
		return catSMAOBV3;
	}

	public int getQtKStochastic() {
		return qtKStochastic;
	}

	public void setQtKStochastic(int qtKStochastic) {
		this.qtKStochastic = qtKStochastic;
	}

	public int getQtDStochastic() {
		return qtDStochastic;
	}

	public void setQtDStochastic(int qtDStochastic) {
		this.qtDStochastic = qtDStochastic;
	}

	public int getQtLowStoch() {
		return qtLowStoch;
	}

	public void setQtLowStoch(int qtLowStoch) {
		this.qtLowStoch = qtLowStoch;
	}

	public boolean isLowStoch() {
		return isLowStoch;
	}

	public void setLowStoch(boolean isLowStoch) {
		this.isLowStoch = isLowStoch;
	}

	public boolean isCrossStoch() {
		return isCrossStoch;
	}

	public void setCrossStoch(boolean isCrossStoch) {
		this.isCrossStoch = isCrossStoch;
	}

	public int getOverboughtStoch() {
		return overboughtStoch;
	}

	public void setOverboughtStoch(int overboughtStoch) {
		this.overboughtStoch = overboughtStoch;
	}

	public int getOversoldStoch() {
		return oversoldStoch;
	}

	public void setOversoldStoch(int oversoldStoch) {
		this.oversoldStoch = oversoldStoch;
	}
	
	public void setDmi(int qtPeriodDmi, int qtAvgDmi) {
		this.qtPeriodDmi = qtPeriodDmi;
		this.qtAvgDmi = qtAvgDmi;	
	}
	
	public int getQtPeriodDmi() {
		return this.qtPeriodDmi;
	}

	public int getQtAvgDmi() {
		return this.qtAvgDmi;
	}
	
	public void setDidi(int qtShortDidi, int qtMediumDidi, int qtLongDidi) {
		this.qtShortDidi = qtShortDidi;
		this.qtMediumDidi = qtMediumDidi;
		this.qtLongDidi = qtLongDidi;
	}
	
	public int getQtShortDidi() {
		return qtShortDidi;
	}

	public int getQtMediumDidi() {
		return qtMediumDidi;
	}

	public int getQtLongDidi() {
		return qtLongDidi;
	}

	public float getPercAccuracy() {
		return percAccuracy;
	}

	public void setPercAccuracy(float percAccuracy) {
		this.percAccuracy = percAccuracy;
	}

	public float getPcSucessTrade() {
		return pcSucessTrade;
	}

	public void setPcSucessTrade(float pcSucessTrade) {
		this.pcSucessTrade = pcSucessTrade;
	}

	public Date getDtStart() {
		return dtStart;
	}

	public void setDtStart(Date dtStart) {
		this.dtStart = dtStart;
	}

	public Date getDtEnd() {
		return dtEnd;
	}

	public void setDtEnd(Date dtEnd) {
		this.dtEnd = dtEnd;
	}
	
	public char getPeriod(){
		return this.period;
	}
	
	public void setPeriod(char period){
		this.period = period;
	}
	
	public ArrayList<AbstractXYAnnotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(ArrayList<AbstractXYAnnotation> annotations) {
		this.annotations = annotations;
	}

	public void addTrade(boolean buySell,int indexBS, Date date, float value) 
	{
		
		try {
			if(buySell){ //Registra Compra
				
				if(Property.getProperty('T').equals("true")){
					
					for(int index = 0; index< trades.size();index++){
						
						Trade trade = trades.get(index);
						
						if(trade.getDtBuy() == null){
							trade.setVlBuy(indexBS, date, value);
						}
						
					}					
				}			
				
				Trade trade = new Trade();
					
				trade.setVlBuy(indexBS, date, value);
					
				trades.add(trade);
			}
			else { //Registra Venda
				
				for(int index = 0; index< trades.size();index++){
					
					Trade trade = trades.get(index);
					
					if(trade.getDtSale() == null){
						trade.setVlSale(indexBS, date, value);
					}
					
				}
				
				if(Property.getProperty('T').equals("true")){
					
					Trade trade = new Trade();
						
					trade.setVlSale(indexBS, date, value);
						
					trades.add(trade);
				}
				
					
			}		

		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

	public ArrayList<Trade> getTrades(){
		return trades;
	}
	
	@Override
	public String toString(){
		
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");
		
		String descricao = "----------\n"+stockCode+"\n";
		
		if(getQtSMA1() > 0){
			descricao += "\n " + I18n.getMessage("bo") + getCatSMA1()+": " + getQtSMA1();
		}
		if(getQtSMA2() > 0){
			descricao += "\n " + I18n.getMessage("bo") + getCatSMA2()+": " + getQtSMA2();
		}
		if(getQtSMA3() > 0){
			descricao += "\n " + I18n.getMessage("bo") + getCatSMA3()+": " + getQtSMA3();
		}
		if(getQtSMA4() > 0){
			descricao += "\n " + I18n.getMessage("bo") + getCatSMA4()+": " + getQtSMA4();
		}
		if(getQtSMA5() > 0){
			descricao += "\n " + I18n.getMessage("bo") + getCatSMA5()+": " + getQtSMA5();
		}	
		
		if(getQtBB() > 0){
			descricao += "\n " + I18n.getMessage("bp") +": "+ getQtBB()+" "+ I18n.getMessage("bq") + ": " + getDvBB();
		}
		
		if(getQtLongMacd() > 0){
			descricao += "\n "+I18n.getMessage("br")+": " + getQtShortMacd() + "/"
			+ getQtLongMacd() + "/" + getQtSignalMacd();
			
			if(isCrossMacd()){
				descricao += I18n.getMessage("bl");
			}
			else{
				descricao += I18n.getMessage("bm");
			}				
		}
		
		if(getQtKStochastic() > 0){
			descricao += "\n " + I18n.getMessage("bn") + getQtKStochastic() + "/"
			+ getQtDStochastic();
			
			if(isLowStoch())
			descricao += "/"+I18n.getMessage("bs")+" " + getQtLowStoch();
			
			if(isCrossStoch())
				descricao += I18n.getMessage("bl");
			
			descricao += " "+I18n.getMessage("bt")+" " + getOverboughtStoch()
			+ " SV " + getOversoldStoch();
			
		}
		
		if(getQtRSI() > 0){
			descricao += "\n "+I18n.getMessage("bx")+": " + getQtRSI() + " "+I18n.getMessage("bt")+" " +  getOverboughtRsi()
			+ " "+I18n.getMessage("bu")+" " + getOversoldRsi();
			
			if(getQtSMARSI1() > 0){
				descricao += " "+I18n.getMessage("bo")+ getCatSMARSI1()+": " + getQtSMARSI1();
			}
			
			if(getQtSMARSI2() > 0){
				descricao += " "+I18n.getMessage("bo")+ getCatSMARSI2()+": " + getQtSMARSI2();
			}
			
			if(getQtSMARSI3() > 0){
				descricao += " "+I18n.getMessage("bo")+ getCatSMARSI3()+": " + getQtSMARSI3();
			}
			
			if(getOverboughtRsi() > 0){
				descricao += " "+I18n.getMessage("bt")+": " + getOverboughtRsi();
			}
			
			if(getOversoldRsi() > 0){
				descricao += " "+I18n.getMessage("bu")+": " + getOversoldRsi();
			}
		}
		
		if(isVolume()){
			descricao += "\n "+I18n.getMessage("bu");
			
			if(getQtSMAVol1() > 0){
				descricao += " "+I18n.getMessage("bo")+ getCatSMAVol1()+": " + getQtSMAVol1();
			}
			
			if(getQtSMAVol2() > 0){
				descricao += " "+I18n.getMessage("bo")+ getCatSMAVol2()+": " + getQtSMAVol2();
			}

			if(getQtSMAVol3() > 0){
				descricao += " "+I18n.getMessage("bo")+ getCatSMAVol3()+": " + getQtSMAVol3();
			}
		}
		
		if(isObv()){
			descricao += "\n "+I18n.getMessage("bz");
			
			if(getQtSMAOBV1() > 0){
				descricao += " "+I18n.getMessage("bo")+ getCatSMAOBV1()+": " + getQtSMAOBV1();
			}
			
			if(getQtSMAOBV2() > 0){
				descricao += " "+I18n.getMessage("bo")+ getCatSMAOBV2()+": " + getQtSMAOBV2();
			}

			if(getQtSMAOBV3() > 0){
				descricao += " "+I18n.getMessage("bo")+ getCatSMAOBV3()+": " + getQtSMAOBV3();
			}
		}
		
		if(getQtPeriodDmi() > 0){
			
			descricao += "\n "+I18n.getMessage("ca")+" "+ getQtPeriodDmi() + "/" + getQtAvgDmi();
		}
		
		if(getQtShortDidi() > 0){
			descricao += "\n "+I18n.getMessage("cb")+" "+ getQtShortDidi() + "/" + getQtMediumDidi() + "/" + getQtLongDidi();
		}
		
		descricao +="\n"+I18n.getMessage("cc")+": "+ simpleDateformat.format(getDtStart()) +" "+I18n.getMessage("cd")+": "+ simpleDateformat.format(getDtEnd())
		+" \n----------";
		
		return descricao;
		
	}
}
