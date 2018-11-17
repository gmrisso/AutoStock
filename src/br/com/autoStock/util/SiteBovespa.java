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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.mf4j.Quote;

/* OK */
public class SiteBovespa {
	
	static Logger logger = Logger.getLogger(SiteBovespa.class.getName());
	
	public SiteBovespa(){}
	
	public Quote priceOnline(String idStock) throws ParserConfigurationException, 
													SAXException, 
													IOException, 
													DOMException, 
													ParseException{
				
		Quote ohlc = null;
		
		try{
			URL url = new URL("http://www.bmfbovespa.com.br/Pregao-Online/ExecutaAcaoAjax.asp?CodigoPapel="+idStock); 
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			URLConnection urlc = url.openConnection();
					
			InputStream xml = urlc.getInputStream();
			
			Document doc = db.parse(xml);
			
			removeWhitespaceNodes(doc.getDocumentElement());			
			
			ohlc = new Quote(getName(doc, "Codigo"),
					getName(doc, "Nome"),
					getDate(doc), 
	        		getValue(doc, "Maximo"),//high 
	        		getValue(doc, "Minimo"),//low 
	        		getValue(doc, "Abertura"),//open 
	        		getValue(doc, "Ultimo"),//close 
	        		0L,//
	        		0L); //			
		}
		catch(Exception e){
			logger.debug(I18n.getMessage("be"));
		}		

        return ohlc;		
	}
			
	private float getValue(Document doc,String value){

		String dado = doc.getDocumentElement().getFirstChild().getAttributes().getNamedItem(value).getNodeValue();
		
		if(dado.equals("")){
			dado = "0.0";
		}
		
		return new Float(dado.replace(',', '.')).floatValue();

	}

	@SuppressWarnings("deprecation")
	private Date getDate(Document doc) throws DOMException, ParseException{
		
		Date data = null;
		
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyyhh:mm:ss"); 
		data = formatador.parse(doc.getDocumentElement().getFirstChild().getAttributes().getNamedItem("Data").getNodeValue());			
		data.setHours(0);
		data.setMinutes(0);
		data.setSeconds(0);
		return data;
	}
	
	private String getName(Document doc,String value){

		String dado = doc.getDocumentElement().getFirstChild().getAttributes().getNamedItem(value).getNodeValue();
				
		return dado;
	}
	
	 private void removeWhitespaceNodes(Element e) {
         NodeList children = e.getChildNodes();
         for (int i = children.getLength() - 1; i >= 0; i--) {
                 Node child = children.item(i);
                 if (child instanceof Text
                                 && ((Text) child).getData().trim().length() == 0) {
                         e.removeChild(child);
                 } else if (child instanceof Element) {
                         removeWhitespaceNodes((Element) child);
                 }
         }
 }
}
