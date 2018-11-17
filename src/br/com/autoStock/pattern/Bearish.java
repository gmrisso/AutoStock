package br.com.autoStock.pattern;

import br.com.autoStock.util.entity.PatternCandle;

import com.mf4j.Quote;

public class Bearish { //38
	
	private Quote[] quotes;
	
	protected void init(Quote[] quotes){
		this.quotes = quotes;
	}
	
	protected PatternCandle[] compare(int index){
		return null;
	}

}
