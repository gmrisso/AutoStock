package br.com.autoStock.indicator;

public class MMNormal {
	
	private MediaMovel m;
	
	public MMNormal(){
		m = new MediaMovel();
	}
	
	public DataIndicator[][] calcularMMNormal(DataIndicator[] source, int shortPeriod, int mediumPeriod, int longPeriod ){
		
		DataIndicator[] shortAvg = m.calcularMMS(source, shortPeriod);
		DataIndicator[] mediumAvg = m.calcularMMS(source, mediumPeriod);
		DataIndicator[] longAvg = m.calcularMMS(source, longPeriod);
		
		for(int i = 0; i < source.length; i++){
			
			shortAvg[i].setClose((shortAvg[i].getClose() / mediumAvg[i].getClose()) - 1);			
			longAvg[i].setClose((longAvg[i].getClose() / mediumAvg[i].getClose()) - 1);
			mediumAvg[i].setClose((mediumAvg[i].getClose() / mediumAvg[i].getClose()) - 1);
		}
		
		DataIndicator[][] resultado = new DataIndicator[3][];	
		
		resultado[0] = shortAvg;
		resultado[1] = mediumAvg;
		resultado[2] = longAvg;

		return resultado;	
		
	}

}
