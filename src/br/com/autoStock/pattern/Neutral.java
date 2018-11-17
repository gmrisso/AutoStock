package br.com.autoStock.pattern;

import br.com.autoStock.util.entity.PatternCandle;

import com.mf4j.Quote;

public class Neutral { //12
	
	private Quote[] quotes;
	private float body;
	private float upperShadow;
	private float lowerShadow;
	
	protected void init(Quote[] quotes){
		this.quotes = quotes;
	}
	
	protected PatternCandle[] compare(int index){
		
		/*High Wave
Long Legged Doji
Umbrella
Inverted Umbrella
Doji
four price doji
White Spinning Top
Black Spinning Top
Short White Candlestick
Short Black Candlestick
White Candlestick
Black Candlestick
*/
			
		
		return null;
	}

}
