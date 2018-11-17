package br.com.autoStock.pattern;

import java.util.ArrayList;

import br.com.autoStock.util.entity.PatternCandle;

import com.mf4j.Quote;

public class Bullish {
	
	private Quote[] quotes;		
	private PatternFactory factory;
	
	public Bullish(){
		factory = new PatternFactory(); 
	}
	
	protected void init(Quote[] quotes){
		this.quotes = quotes;
	}
	
	protected ArrayList<PatternCandle> compare(int index){
		
		int body, upperShadow, lowerShadow;
		int body1, upperShadow1, lowerShadow1;
		int body2, upperShadow2, lowerShadow2;
		int body3, upperShadow3, lowerShadow3;
		
		ArrayList<PatternCandle> array = new ArrayList<PatternCandle>();
		
		int open 	= Float.valueOf(quotes[index].getOpen() * 100).intValue();
		int open1 	= Float.valueOf(quotes[index - 1].getOpen() * 100).intValue();
		int open2 	= Float.valueOf(quotes[index - 2].getOpen() * 100).intValue();
		int open3 	= Float.valueOf(quotes[index - 3].getOpen() * 100).intValue();
		
		int close 	= Float.valueOf(quotes[index].getClose() * 100).intValue();
		int close1 	= Float.valueOf(quotes[index - 1].getClose() * 100).intValue();
		int close2 	= Float.valueOf(quotes[index - 2].getClose() * 100).intValue();
		int close3 	= Float.valueOf(quotes[index - 3].getClose() * 100).intValue();
		
		int high 	= Float.valueOf(quotes[index].getHigh() * 100).intValue();
		int high1 	= Float.valueOf(quotes[index - 1].getHigh() * 100).intValue();
		int high2 	= Float.valueOf(quotes[index - 2].getHigh() * 100).intValue();
		int high3 	= Float.valueOf(quotes[index - 3].getHigh() * 100).intValue();
		
		int low 	= Float.valueOf(quotes[index].getLow() * 100).intValue();	
		int low1	= Float.valueOf(quotes[index - 1].getLow() * 100).intValue();
		int low2	= Float.valueOf(quotes[index - 2].getLow() * 100).intValue();
		int low3	= Float.valueOf(quotes[index - 3].getLow() * 100).intValue();
		int low4	= Float.valueOf(quotes[index - 4].getLow() * 100).intValue();
		
		int avg 	= Float.valueOf(getAverage(quotes)* 100).intValue();
			
		if(open > close){			
			body = open - close;	
			upperShadow = high - open;
			lowerShadow = close - low;			
		}
		else{
			body = close - open;
			upperShadow = high - close;
			lowerShadow = open - low;
		}
		
		if(open1 > close1){			
			body1 = open1 - close1;	
			upperShadow1 = high1 - open1;
			lowerShadow1 = close1 - low1;			
		}
		else{
			body1 = close1 - open1;
			upperShadow1 = high1 - close1;
			lowerShadow1 = open1 - low1;
		}
		
		if(open2 > close2){			
			body2 = open2 - close2;	
			upperShadow2 = high2 - open2;
			lowerShadow2 = close2 - low2;			
		}
		else{
			body2 = close2 - open2;
			upperShadow2 = high2 - close2;
			lowerShadow2 = open2 - low2;
		}
		
		if(open3 > close3){			
			body3 = open3 - close3;	
			upperShadow3 = high3 - open3;
			lowerShadow3 = close3 - low3;			
		}
		else{
			body3 = close3 - open3;
			upperShadow3 = high3 - close3;
			lowerShadow3 = open3 - low3;
		}
				
		//Piercing Line
		if(low2 > low1 //1. Market is characterized by downtrend.
		&& open1 > close1 // 2. We see a long black candlestick.
		&& body1 >= avg
		&& open < close // 3. Then we see a long white candlestick whose opening price is below previous day’s low on the second day.
		&& body >= avg
		&& open < low1
		&&	close < open1	// 4. The second day’s close is contained within the first day body and it is also above the midpoint of the first day’s body.
							// 5. The second day however fails to close above the body of the first day.
		&& close >= (body1 / 2)
		){
			array.add(factory.getPattern(12));
		}
		
		//Kicking
		if( //1. Market direction is not important.
		   body1 >= avg //2. We first see a Black Marubozu pattern.
		&& open1 > close1
		&& open1 == upperShadow1
		&& close1 == lowerShadow1
		&& body >= avg //3. Then we see a White Marubozu that gaps upward on the second day.
		&& open < close
		&& open == upperShadow
		&& close == lowerShadow
		&& open1 < open){
			array.add(factory.getPattern(13));
		}
		
		//Abandoned Baby
		if(low4 > low3  //1. Market is characterized by downtrend.
		&& body3 >= avg	 //2. We usually see a long black candlestick in the first day.
		&& open3 > close3
		&& open2 == close2	 //3. Then a Doji appears on the second day whose shadows gap below the previous day's lower shadow and gaps in the direction of the previous downtrend.
		&& high2 < low3
		&& open < close	 //4. Then we see a white candlestick on the third day with a gap in the opposite direction with no overlapping shadows.
		&& high2 < low	
		){
			array.add(factory.getPattern(14));
		}
		//Morning Doji Star
		if(low4 > low3  //1. Market is characterized by downtrend.
		&& body3 >= avg	 //2. We usually see a long black candlestick in the first day.
		&& open3 > close3
		&& open2 == close2	 //3. Then we see a Doji on the second day that gaps in the direction of the previous downtrend.
		&& high2 < low3
		&& open < close	 //4. The white candlestick on the third day confirms the reversal.
		&& high2 >= low	
		){
			array.add(factory.getPattern(15));
		}
		
		//Morning Star
		if(low4 > low3  //1. Market is characterized by downtrend.
		&& body3 >= avg	 //2. We usually see a long black candlestick in the first day.
		&& open3 > close3
		&& body2 > 0	 //Then we see a small body on the second day gapping in the direction of the previous downtrend.
		&& body2 <= avg * 0.5
		&& high2 < low3
		&& open < close	 //4. Finally we see a white candlestick on the third day.
		&& high2 >= low	
		){
			array.add(factory.getPattern(16));
		}
		//Three Inside Up
		//Three Outside Up
		//Three White Soldiers
		//Concealing Baby Swallow
		//Dragonfly Doji
		//Long Legged Doji
		//Engulfing
		//Gravestone Doji
		//Doji Star
		//Harami Cross
		//Homing Pigeon
		//Matching Low
		//Meeting Lines
		//Stick Sandwich
		//Three Stars in the South
		//Tri Star
		//Three River
		//Breakaway
		//Ladder Bottom
		//Belt Hold
		
		//Bullish Hammer		
		if (low1 > low
		&& body <= avg * 0.5
		&& lowerShadow >= body * 2.0
		&& upperShadow <= body){
			
			array.add(factory.getPattern(37));
			
		}
		
		//Inverted Hammer
		//Harami
		//Side-by-side White Lines
		//Mat Hold
		//Rising Three Methods
		//Upside Gap Three Methods
		//Upside Tasuki Gap
		//Separating Lines
		//Three Line Strike
		//Long White Candlestick
		//White Marubozu
		//White Closing Marubozu
		//White Opening Marubozu

		
				
		return array;
		
	}
	
	private float getAverage(Quote[] quotes){
		
		float avg = 0.0F;
		
		for(int i = 0; i <quotes.length;i++){
			
			avg = avg + (quotes[i].getHigh() - quotes[i].getLow());			
		}
		
		return avg / quotes.length;
	}

}
