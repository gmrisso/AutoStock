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

import java.awt.event.FocusEvent;
import java.io.File;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.com.autoStock.util.I18n;
import br.com.autoStock.util.Properties;
import br.com.autoStock.util.Property;

public class PropertyGui extends javax.swing.JFrame {
	
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(PropertyGui.class.getName());
	
    @SuppressWarnings("deprecation")
	public PropertyGui() {
    	
    	Property.loadProperties();
    	
        initComponents();  
        
        strategyField.setText(Property.getProperty('S'));
        
        switch(new Float(Property.getProperty('I')).intValue()){
        case  300000: // 5 minutos
        	intervalCombo.setSelectedIndex(0);
        	break;
		case  900000: //15 minutos
			intervalCombo.setSelectedIndex(1);
			break;
    	case 1800000: // 30 minutos
    		intervalCombo.setSelectedIndex(2);
    		break;
    	case 3600000: // 1 hora
    		intervalCombo.setSelectedIndex(3);
    		break;
    	}
               
        databaseField.setText(Property.getProperty('D'));
        soldCheck.setSelected(Property.getProperty('T').equals("true")?true:false);
        logField.setText(Property.getProperty('G'));
        
        if (Property.getProperty('L').equals("pt")){
        	languageCombo.setSelectedIndex(0);
        	}
        else {
        	languageCombo.setSelectedIndex(1);
        }
        
        show();
    }

    private void initComponents() {

        strategyLabel = new javax.swing.JLabel();
        intervalLabel = new javax.swing.JLabel();
        databaseLabel = new javax.swing.JLabel();
        soldLabel = new javax.swing.JLabel();
        logLabel = new javax.swing.JLabel();
        languageLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        strategyField = new javax.swing.JTextField();
        intervalCombo = new javax.swing.JComboBox();
        databaseField = new javax.swing.JTextField();
        soldCheck = new javax.swing.JCheckBox();
        logField = new javax.swing.JTextField();
        languageCombo = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(I18n.getMessage("ck"));

        strategyLabel.setText(I18n.getMessage("cl"));

        intervalLabel.setText(I18n.getMessage("cm"));

        databaseLabel.setText(I18n.getMessage("cn"));

        soldLabel.setText(I18n.getMessage("co"));

        logLabel.setText(I18n.getMessage("cp"));

        languageLabel.setText(I18n.getMessage("cq"));

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(I18n.getMessage("ch"));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        intervalCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5 "+I18n.getMessage("cr"), "15 "+I18n.getMessage("cr"), "30 "+I18n.getMessage("cr"), "1 "+I18n.getMessage("cs") }));

        databaseField.addFocusListener(new java.awt.event.FocusListener() {
           
			@Override
			public void focusLost(FocusEvent evt) {
				
				databaseFieldFocusLost(evt);
			}

			@Override
			public void focusGained(FocusEvent evt) {}
        });
        
        logField.setEnabled(false);
        
        languageCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ct"), I18n.getMessage("cu") }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(strategyLabel)
                    .addComponent(intervalLabel)
                    .addComponent(databaseLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(logLabel)
                            .addComponent(soldLabel)
                            .addComponent(languageLabel))
                        .addGap(3, 3, 3)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(strategyField, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(intervalCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(databaseField, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soldCheck)
                    .addComponent(logField, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(languageCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(strategyLabel)
                    .addComponent(strategyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intervalLabel)
                    .addComponent(intervalCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(databaseLabel)
                    .addComponent(databaseField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(soldLabel)
                    .addComponent(soldCheck))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logLabel)
                    .addComponent(logField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(languageLabel)
                    .addComponent(languageCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
       
    	this.dispose();
    }                                            

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
    	Properties config;
    	
    	if (databaseFieldFocusLost(null) == false){
    		return;
    	}
    	
    	config = Property.getProperties();
    	
    	config.setStrategy(strategyField.getText());
    	
    	switch(intervalCombo.getSelectedIndex()){
        case 0: // 5 minutos
        	config.setInterval(300000);
        	break;
		case 1: //15 minutos
			config.setInterval(900000);
			break;
    	case 2: // 30 minutos
    		config.setInterval(1800000);
    		break;
    	case 3: // 1 hora
    		config.setInterval(3600000);
    		break;
    	}
    	
    	config.setDatabaseDir(databaseField.getText());
    	config.setSoldTrade(soldCheck.isSelected());
    	config.setLogDir(logField.getText());
    	
    	if (languageCombo.getSelectedIndex() == 0){
    		config.setLanguage("pt");
        	}
        else {
        	config.setLanguage("en");
        }
    	
    	Property.setProperties(config);
    	
    	JOptionPane.showMessageDialog(null,I18n.getMessage("cv"), I18n.getMessage("cj"),1);
    	
    	this.dispose();
    }
    
    private boolean databaseFieldFocusLost(FocusEvent evt) {
        
    	File diretorio = new File(databaseField.getText());  
    	if (!diretorio.exists()) {  
    		JOptionPane.showMessageDialog(null,I18n.getMessage("cx"), I18n.getMessage("cj"),0);
    		
    		return false;
    	}  
    	
    	return true;
    }

    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField databaseField;
    private javax.swing.JLabel databaseLabel;
    private javax.swing.JComboBox intervalCombo;
    private javax.swing.JLabel intervalLabel;
    private javax.swing.JComboBox languageCombo;
    private javax.swing.JLabel languageLabel;
    private javax.swing.JTextField logField;
    private javax.swing.JLabel logLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JCheckBox soldCheck;
    private javax.swing.JLabel soldLabel;
    private javax.swing.JTextField strategyField;
    private javax.swing.JLabel strategyLabel;   

}
