package br.com.autoStock.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.mf4j.Quote;

public class SinalADVFN {

	static Logger logger = Logger.getLogger(SinalADVFN.class.getName());

	public SinalADVFN(){}

	@SuppressWarnings("deprecation")
	public Quote priceOnline(String idStock){

		Quote ohlc = null;

		try{
			URL url = new URL("http://br.advfn.com/p.php?pid=iquote&symbol=BOV^"+idStock);  

			Calendar data = Calendar.getInstance();
			String linha = "";
			float ultima = 0;
			float maxima = 0;
			float minima = 0;
			float abertura = 0;
			int hora = 0;
			long volume = 0;
			long negocios = 0;
			
			if(data.get(Calendar.DAY_OF_WEEK) == 7 || data.get(Calendar.DAY_OF_WEEK) == 1)
			return null;
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

			while ((linha = reader.readLine()) != null)  {

				linha = linha.trim();

				if(linha.contains("id='quote_cur'")){
					ultima = new Float(linha.substring(linha.indexOf("id='quote_cur'")+15, linha.indexOf("</"))).floatValue();
					
				}
				else if(linha.contains("id='quote_time'")){
					hora = new Integer(linha.substring(linha.indexOf("id='quote_time'")+16, linha.indexOf(":"))).intValue();
					
					if(data.getTime().getHours() < hora){
						data.add(Calendar.DATE, -1);
					}

				}else if(linha.contains("id='quote_vol'")){
					volume = new Long(linha.substring(linha.indexOf("id='quote_vol'")+15, linha.indexOf("</")).replace(",", "")).longValue();
					
				}else if(linha.contains("<span class='smallest'>ABE</span>")){
					linha = reader.readLine();
					linha = linha.trim();
					abertura = new Float(linha.substring(0, linha.indexOf("</"))).floatValue();

				}else if(linha.contains("id='quote_high'")){
					maxima = new Float(linha.substring(linha.indexOf("id='quote_high'")+16, linha.indexOf("</"))).floatValue();
					
				}else if(linha.contains("id='quote_low'")){
					minima = new Float(linha.substring(linha.indexOf("id='quote_low'")+15, linha.indexOf("</"))).floatValue();
					
				}else if(linha.contains("id='quote_trad'")){
					negocios = new Long(linha.substring(linha.indexOf("id='quote_trad'")+16, linha.indexOf("</")).replace(",", "")).longValue();
					
				}
			}	        	
			reader.close();

			ohlc = new Quote(idStock,
					"",
					data.getTime(), 
					maxima, 
					minima, 
					abertura,
					ultima,
					negocios,
					volume); 	               	
		}
		catch(Exception e){
			logger.error("http://br.advfn.com/p.php?pid=iquote&symbol=BOV^"+idStock);
		}		

		return ohlc;		
	}

}
