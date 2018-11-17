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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.com.autoStock.Bovespa;
import br.com.autoStock.util.I18n;
import br.com.autoStock.util.entity.Stock;
import br.com.autoStock.util.entity.StockList;

public class StockSelect extends JFrame {
	
	static Logger logger = Logger.getLogger(StockSelect.class.getName());

	private static final long serialVersionUID = 1L;
	
	private Bovespa bovespa;
	private Stock[] allStock;
	private StockList stockListCombo;
	private MainScreen screen;
	private int function;
	
	public StockSelect(MainScreen screen, int function) {

		this.screen = screen;
		this.function = function;
		
		initComponents();
		
		bovespa = new Bovespa();

		allStock = bovespa.listStock();
		
		switch(function){
		case 0:

			try {
				FileInputStream fis = new FileInputStream("stockList.lst");
				ObjectInputStream ois;
				ois = new ObjectInputStream(fis);

				stockListCombo = (StockList) ois.readObject();	
				Stock[] slc = stockListCombo.getAllStock();

				for(int i = 0; i<slc.length;i++){
					stockModel.addElement(slc[i].getStockCode());
				}

				ois.close();

			} catch (Exception e) {
				stockListCombo = new StockList(null);
			}

			for(int i = 0; i<allStock.length;i++){

				if(!stockListCombo.contains(allStock[i])){
					allModel.addElement(allStock[i].getStockCode());
				}
			}			
			break;
		case 1:
			
			try {
				FileInputStream fis = new FileInputStream("stockList.lst");
				ObjectInputStream ois;
				ois = new ObjectInputStream(fis);

				stockListCombo = (StockList) ois.readObject();	
				Stock[] slc = stockListCombo.getAllStock();

				for(int i = 0; i<slc.length;i++){
					allModel.addElement(slc[i].getStockCode());
				}

				ois.close();

			} catch (Exception e) {
				stockListCombo = new StockList(null);
			}
			
			break;
			
		case 2:
			
			try {
				FileInputStream fis = new FileInputStream("stockList.lst");
				ObjectInputStream ois;
				ois = new ObjectInputStream(fis);

				stockListCombo = (StockList) ois.readObject();	
				Stock[] slc = stockListCombo.getAllStock();

				for(int i = 0; i<slc.length;i++){
					allModel.addElement(slc[i].getStockCode());
				}

				ois.close();

			} catch (Exception e) {
				stockListCombo = new StockList(null);
			}
			
			break;
		}		
	}

    private void initComponents() {
    	
    	this.setTitle(I18n.getMessage("cg"));

    	addButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        allList = new javax.swing.JList();
        removeButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        stockList = new javax.swing.JList();
        okButton = new javax.swing.JButton();
        cancButton = new javax.swing.JButton();

        setLayout(null);

        addButton.setText(">>");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        add(addButton);
        addButton.setBounds(112, 22, 50, 23);
        
        allModel = new DefaultListModel();

        allList.setModel(allModel);
        jScrollPane2.setViewportView(allList);

        add(jScrollPane2);
        jScrollPane2.setBounds(10, 11, 95, 171);

        removeButton.setText("<<");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        add(removeButton);
        removeButton.setBounds(112, 51, 50, 23);
        
        stockModel = new DefaultListModel();

        stockList.setModel(stockModel);
        jScrollPane3.setViewportView(stockList);

        add(jScrollPane3);
        jScrollPane3.setBounds(171, 11, 95, 171);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        add(okButton);
        okButton.setBounds(10, 193, 55, 23);

        cancButton.setText(I18n.getMessage("ch"));
        cancButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancButtonActionPerformed(evt);
            }
        });
        add(cancButton);
        cancButton.setBounds(68, 193, 85, 23);     
        
        this.setSize(300, 260); 
        this.setLocation(150, 150);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	
    	if(stockModel.size() == 0){
    		JOptionPane.showMessageDialog(null, I18n.getMessage("ci"), " "+I18n.getMessage("cj"),0);
    		return;
    	}
    	
    	StockList sl = new StockList(null);

		for(int i = 0; i< stockModel.size();i++){
			
			for(int j = 0; j< allStock.length;j++){

				if(allStock[j].getStockCode().equals((String)stockModel.get(i))){
					
					sl.add(allStock[j]);
					break;
				}

			} 		
		}
    	
    	switch(function){
    	case 0:
    		try{
    			FileOutputStream fos = new FileOutputStream("stockList.lst");
    			ObjectOutputStream oos = new ObjectOutputStream(fos);
    			oos.writeObject(sl);
    			oos.close();

    			this.screen.setStock(sl.getAllStock());
    		}
    		catch (Exception ex) {
    			ex.printStackTrace();
    		}
    		break;
		case 1:
			screen.setListSimulation(sl.getAllStock());
			break;
		case 2:
			screen.setListAcomp(sl.getAllStock());
			break;
    	}
    			
		this.dispose();
    }

    @SuppressWarnings("unchecked")
	private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	
    	if(allList.getSelectedValue() != null){
    		stockModel.addElement(allList.getSelectedValue());
        	allModel.removeElement(allList.getSelectedValue());
        	
        	List<String> lista = new ArrayList();
        	
        	while(!stockModel.isEmpty()){
        		lista.add((String)stockModel.remove(0));
        	}
        	        	
        	Collections.sort (lista, new Comparator() {  
        		
                public int compare(Object obj1, Object obj2) { 
                	
                	String a1 = obj1.toString();  
                	String a2 = obj2.toString(); 
                	
                    return a1.compareTo(a2); 
                }				 
            }); 
        	
        	for(int i = 0; i< lista.size();i++){
        		
        		stockModel.addElement(lista.get(i));
        	}
        	
    	}    	
    }

    @SuppressWarnings("unchecked")
	private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	if(stockList.getSelectedValue() != null){
    		allModel.addElement(stockList.getSelectedValue());
    		stockModel.removeElement(stockList.getSelectedValue());
    		
    		List<String> lista = new ArrayList();
        	
        	while(!allModel.isEmpty()){
        		lista.add((String)allModel.remove(0));
        	}
        	
        	while(!allModel.isEmpty()){
        		lista.add((String)allModel.remove(0));
        	}
        	        	
        	Collections.sort (lista, new Comparator() {  
        		
                public int compare(Object obj1, Object obj2) { 
                	
                	String a1 = obj1.toString();  
                	String a2 = obj2.toString(); 
                	
                    return a1.compareTo(a2); 
                }				 
            }); 
        	
        	for(int i = 0; i< lista.size();i++){
        		
        		allModel.addElement(lista.get(i));
        	}
    	} 
    }

    private void cancButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	this.dispose();
    }
    
    private javax.swing.DefaultListModel allModel;
    private javax.swing.DefaultListModel stockModel;

    private javax.swing.JButton addButton;
    private javax.swing.JList allList;
    private javax.swing.JButton cancButton;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton okButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JList stockList;    
}

