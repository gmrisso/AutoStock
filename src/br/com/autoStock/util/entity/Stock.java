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
import org.apache.log4j.Logger;

import br.com.autoStock.util.I18n;

public class Stock  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(Stock.class.getName());
	
	private String stockName;	
	private String stockCode;
	
	public Stock(){
		logger.debug(I18n.getMessage("ay"));
	}

	public Stock(String stockCode){

		this.stockCode = stockCode;

	}

	public Stock(String stockName, String stockCode){

		this.stockName = stockName;
		this.stockCode = stockCode;

	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((stockCode == null) ? 0 : stockCode.hashCode());
		result = prime * result
				+ ((stockName == null) ? 0 : stockName.hashCode());
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
		Stock other = (Stock) obj;
		if (stockCode == null) {
			if (other.stockCode != null)
				return false;
		} else if (!stockCode.equals(other.stockCode))
			return false;
		if (stockName == null) {
			if (other.stockName != null)
				return false;
		} else if (!stockName.equals(other.stockName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return I18n.getMessage("az") + stockCode + "]";
	}	
}