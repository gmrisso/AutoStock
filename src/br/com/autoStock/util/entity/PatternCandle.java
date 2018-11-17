package br.com.autoStock.util.entity;

public class PatternCandle {
	
	private String name;
	
	/* 
	 * 0 - reversal/continuation
	 * 1 - reversal
	 * 2 - continuation
	 * */
	private int type;
	
	/*
	 * 0 - indecision
	 * 1 - bullish
	 * 2 - bearish
	 * */	
	private int relevance;
	
	/*
	 * 0 - n/a
	 * 1 - bullish
	 * 2 - bearish
	 * */	
	private int priorTrend;
	
	/*
	 * 0 - high
	 * 1 - medium
	 * 2 - low
	 * */		
	private int reliability; //confiabilidade
	
	/*
	 * 0 - definitely required
	 * 1 - required
	 * 2 - needed
	 * 3 - recommended
	 * 4 - suggested
	 * 5 - strong suggested
	 * 6 - na
	 * */
	private int confirmation;
	
	private int numberOfSticks;
	
	public PatternCandle(String name, int type, int relevance, int priorTrend, int reliability, int confirmation, int numberOfSticks){
		
		this.name = name;
		this.type = type;
		this.relevance = relevance;
		this.priorTrend = priorTrend;
		this.reliability = reliability;
		this.confirmation = confirmation;
		this.numberOfSticks = numberOfSticks;
	}
	
	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public int getRelevance() {
		return relevance;
	}

	public int getPriorTrend() {
		return priorTrend;
	}

	public int getReliability() {
		return reliability;
	}

	public int getConfirmation() {
		return confirmation;
	}

	public int getNumberOfSticks() {
		return numberOfSticks;
	}
}
