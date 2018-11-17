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
import java.util.Date;

import org.apache.log4j.Logger;

public class Trade implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(Trade.class.getName());
	
	private float vlBuy;
	private float vlSale;
	private float pcProfit;
	private Date dtBuy;
	private Date dtSale;
	private int indexBuy;
	private int indexSale;
	
	public double getVlBuy() {
		return vlBuy;
	}

	public void setVlBuy(int indexBuy, Date dtBuy, float vlBuy) {
		this.indexBuy = indexBuy;
		this.vlBuy = vlBuy;		
		this.dtBuy = dtBuy;
		
		if(vlSale != 0 && vlBuy != 0 ){
			this.pcProfit = (vlSale / vlBuy) - 1;
		}
	}

	public double getVlSale() {
		return vlSale;
	}
	
	public int getIndexBuy(){
		return indexBuy;
	}

	public void setVlSale(int indexSale, Date dtSale, float vlSale) {
		this.indexSale = indexSale;
		this.vlSale = vlSale;
		this.dtSale = dtSale;
		
		if(vlSale != 0 && vlBuy != 0 ){
			this.pcProfit = (vlSale / vlBuy) - 1;
		}
	}
	
	public int getIndexSale(){
		return indexSale;
	}

	public double getPcProfit() {
		return pcProfit;
	}

	public Date getDtBuy() {
		return dtBuy;
	}

	public Date getDtSale() {
		return dtSale;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dtBuy == null) ? 0 : dtBuy.hashCode());
		result = prime * result + ((dtSale == null) ? 0 : dtSale.hashCode());
		result = prime * result + Float.floatToIntBits(vlBuy);
		result = prime * result + Float.floatToIntBits(vlSale);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (dtBuy == null) {
			if (other.dtBuy != null)
				return false;
		} else if (!dtBuy.equals(other.dtBuy))
			return false;
		if (dtSale == null) {
			if (other.dtSale != null)
				return false;
		} else if (!dtSale.equals(other.dtSale))
			return false;
		if (Float.floatToIntBits(vlBuy) != Float.floatToIntBits(other.vlBuy))
			return false;
		if (Float.floatToIntBits(vlSale) != Float.floatToIntBits(other.vlSale))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trade [dtBuy=" + dtBuy + ", dtSale=" + dtSale + ", pcProfit="
				+ pcProfit + ", vlBuy=" + vlBuy + ", vlSale=" + vlSale + "]";
	}

}
