package br.com.autoStock.pattern;

import java.util.ArrayList;

import com.mf4j.Quote;

import br.com.autoStock.util.entity.PatternCandle;

public class Engine {
	
	Bullish bull;
	Bearish bear;
	Neutral neutral;
	
	public Engine(){
		
		bull = new Bullish();
		bear = new Bearish();
		neutral = new Neutral();
	}
	
	public ArrayList <PatternCandle> execute(Quote[] quotes, int index){
		
		ArrayList <PatternCandle> bullPt;
		PatternCandle[] bearPt;
		PatternCandle[] neuPt;
		ArrayList <PatternCandle> result;
		
		bull.init(quotes);
		bear.init(quotes);
		neutral.init(quotes);
		
		bullPt = bull.compare(index);
		bearPt = bear.compare(index);
		neuPt  = neutral.compare(index);
		
		result = new ArrayList <PatternCandle>();
		
		if(bullPt != null){
			for(int i = 0; i <bullPt.size();i++){
				result.add(bullPt.get(i));
			}
		}
		
		if(bearPt != null){
			for(int i = 0; i <bearPt.length;i++){
				result.add(bearPt[i]);
			}
		}
		
		if(neuPt != null){
			for(int i = 0; i <neuPt.length;i++){
				result.add(neuPt[i]);
			}
		}
		
			
		return result;
	}	
	
	
}
