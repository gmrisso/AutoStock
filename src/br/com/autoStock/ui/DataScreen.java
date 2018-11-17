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

package br.com.autoStock.ui;

import java.text.SimpleDateFormat;

import br.com.autoStock.util.I18n;

import com.mf4j.Quote;

public class DataScreen extends javax.swing.JInternalFrame{

	private static final long serialVersionUID = 1L;

	public DataScreen(Quote quote, float variation) {
        initComponents(quote, variation);
    }
    
    private void initComponents(Quote quote, float pcVariation) {

    	simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");
        title = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        openLabel = new javax.swing.JLabel();
        highLabel = new javax.swing.JLabel();
        lowLabel = new javax.swing.JLabel();
        closeLabel = new javax.swing.JLabel();
        volumeLabel = new javax.swing.JLabel();
        negotiationLabel = new javax.swing.JLabel();
        variationLabel = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        variation = new javax.swing.JLabel();
        open = new javax.swing.JLabel();
        high = new javax.swing.JLabel();
        low = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        volume = new javax.swing.JLabel();
        negotiation = new javax.swing.JLabel();

        setBackground(new java.awt.Color(240, 240, 240));
        this.setSize(210, 230);     
       
        getContentPane().setLayout(null);

        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText(quote.getCodeStock() + " - " + quote.getStockName());
        getContentPane().add(title);
        title.setBounds(10, 10, 180, 14);

        dateLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateLabel.setText(I18n.getMessage("cy")+":");
        getContentPane().add(dateLabel);
        dateLabel.setBounds(10, 30, 75, 14);

        openLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        openLabel.setText(I18n.getMessage("cz")+":");
        getContentPane().add(openLabel);
        openLabel.setBounds(10, 90, 75, 14);

        highLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        highLabel.setText(I18n.getMessage("da")+":");
        getContentPane().add(highLabel);
        highLabel.setBounds(10, 110, 75, 14);

        lowLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lowLabel.setText(I18n.getMessage("db")+":");
        getContentPane().add(lowLabel);
        lowLabel.setBounds(10, 130, 75, 14);

        closeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        closeLabel.setText(I18n.getMessage("dc")+":");
        getContentPane().add(closeLabel);
        closeLabel.setBounds(10, 150, 75, 14);

        volumeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        volumeLabel.setText(I18n.getMessage("dd")+":");
        getContentPane().add(volumeLabel);
        volumeLabel.setBounds(10, 180, 75, 14);

        negotiationLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        negotiationLabel.setText(I18n.getMessage("de")+":");
        getContentPane().add(negotiationLabel);
        negotiationLabel.setBounds(10, 200, 75, 14);

        variationLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        variationLabel.setText(I18n.getMessage("df")+":");
        getContentPane().add(variationLabel);
        variationLabel.setBounds(10, 50, 75, 14);

        date.setText(simpleDateformat.format(quote.getDate()));
        getContentPane().add(date);
        date.setBounds(90, 30, 100, 14);

        variation.setText(pcVariation + " %");
        getContentPane().add(variation);
        variation.setBounds(90, 50, 100, 14);

        open.setText(new Float(quote.getOpen()).toString());
        getContentPane().add(open);
        open.setBounds(90, 90, 100, 14);

        high.setText(new Float(quote.getHigh()).toString());
        getContentPane().add(high);
        high.setBounds(90, 110, 100, 14);

        low.setText(new Float(quote.getLow()).toString());
        getContentPane().add(low);
        low.setBounds(90, 130, 100, 14);

        close.setText(new Float(quote.getClose()).toString());
        getContentPane().add(close);
        close.setBounds(90, 150, 100, 14);

        volume.setText(new Long(quote.getVolume()).toString());
        getContentPane().add(volume);
        volume.setBounds(90, 180, 100, 14);

        negotiation.setText(new Long(quote.getOpenInterest()).toString());
        getContentPane().add(negotiation);
        negotiation.setBounds(90, 200, 100, 14);
        
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(""));    	
        this.setLocation(15,75);
        this.setVisible(true);
    }
    
    public void setData(Quote quote, float pcVariation){
    	
    	date.setText(simpleDateformat.format(quote.getDate()));

        variation.setText(pcVariation + " %");

        open.setText(new Float(quote.getOpen()).toString());

        high.setText(new Float(quote.getHigh()).toString());

        low.setText(new Float(quote.getLow()).toString());

        close.setText(new Float(quote.getClose()).toString());

        volume.setText(new Long(quote.getVolume()).toString());

        negotiation.setText(new Long(quote.getOpenInterest()).toString());
    }
    
    public void repaint(){    	
    	super.validate();
    	super.repaint();    	    	
    }
    
    private javax.swing.JLabel close;
    private javax.swing.JLabel closeLabel;
    private javax.swing.JLabel date;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel high;
    private javax.swing.JLabel highLabel;
    private javax.swing.JLabel title;    
    private javax.swing.JLabel low;
    private javax.swing.JLabel lowLabel;
    private javax.swing.JLabel negotiation;
    private javax.swing.JLabel negotiationLabel;
    private javax.swing.JLabel open;
    private javax.swing.JLabel openLabel;
    private javax.swing.JLabel variation;
    private javax.swing.JLabel variationLabel;
    private javax.swing.JLabel volume;
    private javax.swing.JLabel volumeLabel;    
    private SimpleDateFormat simpleDateformat;
}
