package br.com.autoStock.pattern;

import br.com.autoStock.util.entity.PatternCandle;

public class PatternFactory {
	
	PatternCandle[] patterns;
	
	public PatternFactory(){
		
		patterns = new PatternCandle[] {
		
		new PatternCandle("High Wave",					1,	0,	0,	1, 	1,	1), // 0 -- neutral
		new PatternCandle("Long Legged Doji",			1,	0,	0,	1, 	1,	1),
		new PatternCandle("Umbrella",					1,	0,	0,	1, 	6,	1),
		new PatternCandle("Inverted Umbrella",			1,	0,	0,	1, 	6,	1),
		new PatternCandle("Doji",						0,	0,	0,	2,	6,	1),
		new PatternCandle("four price doji",			0,	0,	0,	2,	6,	1),
		new PatternCandle("White Spinning Top",			0,	0,	0,	2,	6,	1),
		new PatternCandle("Black Spinning Top",			0,	0,	0,	2,	6,	1),
		new PatternCandle("Short White Candlestick",	0,	0,	0,	2,	6,	1),
		new PatternCandle("Short Black Candlestick",	0,	0,	0,	2,	6,	1),
		new PatternCandle("White Candlestick",			0,	0,	0,	2,	6,	1), // 10
		new PatternCandle("Black Candlestick",			0,	0,	0,	2,	6,	1),
		new PatternCandle("Piercing Line",				1,	1,	2, 	0,	4,	2), // Bullish
		new PatternCandle("Kicking",					1,	1,	0,	0,	2,	2),
		new PatternCandle("Abandoned Baby",				1,	1,	2, 	0,	4,	3),
		new PatternCandle("Morning Doji Star",			1,	1,	2, 	0,	4,	3),
		new PatternCandle("Morning Star",				1,	1,	2, 	0,	4,	3),
		new PatternCandle("Three Inside Up",			1,	1,	2, 	0,	4,	3),
		new PatternCandle("Three Outside Up",			1,	1,	2, 	0,	4,	3),
		new PatternCandle("Three White Soldiers",		1,	1,	2, 	0,	4,	3),
		new PatternCandle("Concealing Baby Swal2,",		1,	1,	2, 	0,	4,	4), // 20
		new PatternCandle("Dragonfly Doji",				1,	1,	2, 	1, 	4,	1),
		new PatternCandle("Long Legged Doji",			1,	1,	2, 	1, 	0,	1),
		new PatternCandle("Engulfing",					1,	1,	2, 	1, 	4,	2),
		new PatternCandle("Gravestone Doji",			1,	1,	2, 	1, 	4,	2),
		new PatternCandle("Doji Star",					1,	1,	2, 	1, 	4,	2),
		new PatternCandle("Harami Cross",				1,	1,	2, 	1, 	3,	2),
		new PatternCandle("Homing Pigeon",				1,	1,	2, 	1, 	4,	2),
		new PatternCandle("Matching 2,",				1, 	1,	2, 	1, 	4,	2),
		new PatternCandle("Meeting Lines",				1,	1,	2, 	1, 	4,	2),
		new PatternCandle("Stick Sandwich",				1,	1,	2, 	1, 	4,	3), // 30
		new PatternCandle("Three Stars in the South",	1,	1,	2, 	1, 	4,	3),
		new PatternCandle("Tri Star",					1,	1,	2, 	1, 	4,	3),
		new PatternCandle("Three River",				1,	1,	2, 	1, 	4,	3),
		new PatternCandle("Breakaway",					1,	1,	2, 	1, 	3,	5),
		new PatternCandle("Ladder Bottom",				1,	1,	2, 	1, 	4,	5),
		new PatternCandle("Belt Hold",					1,	1,	2, 	2,	1,	1),
		new PatternCandle("Hammer",						1,	1,	2, 	2,	0,	1),
		new PatternCandle("Inverted Hammer",			1,	1,	2, 	2,	0,	2),
		new PatternCandle("Harami",						1,	1,	2, 	2,	5,	2),
		new PatternCandle("Side-by-side White Lines",	2,	1,	1,	0,	4,	3), // 40
		new PatternCandle("Mat Hold",					2,	1,	1,	0,	4,	5),
		new PatternCandle("Rising Three Methods",		2,	1,	1,	0,	4,	5),
		new PatternCandle("Upside Gap Three Methods",	2,	1,	1,	1, 	4,	3),
		new PatternCandle("Upside Tasuki Gap",			2,	1,	1,	1, 	3,	3),
		new PatternCandle("Separating Lines",			2,	1,	1,	2,	1,	2),
		new PatternCandle("Three Line Strike",			2,	1,	1,	2,	0,	4),
		new PatternCandle("Long White Candlestick",		0,	1,	0,	2,	1,	1),
		new PatternCandle("White Marubozu",				0,	1,	0,	2,	1,	1),
		new PatternCandle("White Closing Marubozu",		0,	1,	0,	2,	1,	1),
		new PatternCandle("White Opening Marubozu",		0,	1,	0,	2,	1,	1), // 50
		new PatternCandle("Dark Cloud Cover",			1,	2,	1,	0,	4,	2), // Bearish
		new PatternCandle("Kicking",					1,	2,	0,	0,	3,	2),
		new PatternCandle("Abandoned Baby",				1,	2,	1,	0,	4,	3),
		new PatternCandle("Evening Star",				1,	2,	1,	0,	4,	3),
		new PatternCandle("Evening Doji Star",			1,	2,	1,	0,	4,	3),
		new PatternCandle("Three Black Crows",			1,	2,	1,	0,	4,	3),
		new PatternCandle("Three Inside Down",			1,	2,	1,	0,	4,	3),
		new PatternCandle("Three Outside Down",			1,	2,	1,	0,	4,	3),
		new PatternCandle("Upside Gap Two Crows",		1,	2,	1,	0,	4,	3),
		new PatternCandle("Dragonfly Doji",				1,	2,	1,	1, 	4,	1), // 60
		new PatternCandle("Long Legged Doji",			1,	2,	1,	1, 	1,	1),
		new PatternCandle("Engulfing",					1,	2,	1,	1, 	4,	2),
		new PatternCandle("Gravestone Doji",			1,	2,	1,	1, 	4,	2),
		new PatternCandle("Doji Star",					1, 	2,	1,	1, 	4,	2),
		new PatternCandle("Harami Cross",				1,	2,	1,	1, 	3,	2),
		new PatternCandle("Meeting Lines",				1,	2,	1,	1, 	4,	2),
		new PatternCandle("Advance Block",				1,	2,	1,	1, 	4,	3),
		new PatternCandle("Deliberation",				1,	2,	1,	1, 	4,	3),
		new PatternCandle("Tri Star",					1,	2,	1,	1, 	4,	3), 
		new PatternCandle("Two Crows",					1,	2,	1,	1, 	4,	3), // 70
		new PatternCandle("Breakaway",					1,	2,	1,	1, 	3,	5),
		new PatternCandle("Belt Hold",					1,	2,	1,	2, 	1,	1),
		new PatternCandle("Hanging Man",				1,	2,	1,	2,	0,	1),
		new PatternCandle("Shooting Star",				1,	2,	1,	2,	0,	2),
		new PatternCandle("Harami",						1,	2,	1,	2,	5,	2),
		new PatternCandle("Falling Three Methods",		2,	2,	2, 	0,	4,	5),
		new PatternCandle("In Neck",					2,	2,	2, 	1, 	4,	2),
		new PatternCandle("On Neck",					2,	2,	2, 	1, 	4,	2),
		new PatternCandle("Downside Gap Three Methods",	2,	2,	2, 	1, 	4,	3),
		new PatternCandle("Downside Tasuki Gap",		2,	2,	2, 	1, 	3,	3), // 80
		new PatternCandle("Side By Side White Lines",	2,	2,	2, 	1, 	3,	3),
		new PatternCandle("Separating Lines",			2,	2,	2, 	2,	1,	2),
		new PatternCandle("Thrusting",					2,	2,	2, 	2,	1,	2),
		new PatternCandle("Three Line Strike",			2,	2,	2, 	2,	0,	4),
		new PatternCandle("Long Black Candlestick",		0,	2,	0,	2, 	1,	1),
		new PatternCandle("Black Marubozu",				0,	2,	0,	2,	1,	1),
		new PatternCandle("Black Closing Marubozu",		0,	2,	0,	2,	1,	1),
		new PatternCandle("Black Opening Marubozu",		0,	2,	0,	2,	1,	1)
		
		};		
	}
	
	public PatternCandle getPattern(int pattern){
		
		return patterns[pattern];
	}

}
