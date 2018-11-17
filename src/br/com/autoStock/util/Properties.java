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

package br.com.autoStock.util;

import java.io.Serializable;

public class Properties implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String strategy;
	private float interval;
	private String databaseDir;
	private boolean soldTrade;
	private String logDir;
	private String language;
	
	public Properties() {
		super();
		this.strategy = "";
		this.interval = 900000; //15 minutos
		this.databaseDir = "E:/GrapherOC/BD";
		this.soldTrade = false;
		this.logDir = "AutoStock.log";
		this.language = "pt";
	}
		
	public Properties(String strategy, float interval, String databaseDir,
			boolean soldTrade, String logDir, String language) {
		super();
		this.strategy = strategy;
		this.interval = interval;
		this.databaseDir = databaseDir;
		this.soldTrade = soldTrade;
		this.logDir = logDir;
		this.language = language;
	}
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	public float getInterval() {
		return interval;
	}
	public void setInterval(float interval) {
		this.interval = interval;
	}
	public String getDatabaseDir() {
		return databaseDir;
	}
	public void setDatabaseDir(String databaseDir) {
		this.databaseDir = databaseDir;
	}
	public boolean isSoldTrade() {
		return soldTrade;
	}
	public void setSoldTrade(boolean soldTrade) {
		this.soldTrade = soldTrade;
	}
	public String getLogDir() {
		return logDir;
	}
	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
}
