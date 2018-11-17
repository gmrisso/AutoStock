package br.com.autoStock.ui;

import javax.swing.JFrame;

import br.com.autoStock.util.I18n;

public class About extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public About() {
        initComponents();
        
        this.setResizable(false);
        this.setSize(330, 439);
        this.setLocation(150, 150);
        this.setVisible(true);        
    }

    private void initComponents() {

    	jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        okButton = new javax.swing.JButton();

        this.setLayout(null);

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        
        if(I18n.getLanguage().equals("pt")){
        	jTextArea1.setText("AutoStock - versão 1.03\n\n" +
            		"Copyright (C) 2011  Gilnei Marcos Risso \n" +
            		"All Rights Reserved.\n\n" +
            		"Importante: Este programa é gratuito e para \n" +
            		"uso pessoal. É distribuido sem garantia de \n" +
            		"nenhuma natureza, constituindo-se em uma\n" +
            		"ferramenta auxiliar para interpretação de \n" +
            		"dados de ações da bolsa de valores.\n" +
            		"Os fins do software são estritamente \n" +
            		"didáticos e educacionais; \n\n" +
            		"A base de dados metastock não é distribuida \n" +
            		"junto a este software, ficando a cargo do \n" +
            		"usuário de obtê-la e mantê-la atualizada.\n\n" +
            		"O autor desse software pode ser contatado\n" +
            		"pelo e-mail: gilnei.risso@gmail.com");
        }
        else{
        	jTextArea1.setText("AutoStock - versão 1.03\n\n" +
            		"Copyright (C) 2011  Gilnei Marcos Risso \n" +
            		"All Rights Reserved.\n\n" +
            		"Important: This program is free and for \n" +
            		"personal use. It's distributed without warranty \n" +
            		"of any kind, thus becoming an auxiliary tool  \n" +
            		"for interpreting data from stock exchange. \n" +
            		"The purpose of the software is strictly  \n" +
            		"educational and didactic; \n \n" +
            		"The database metastock is not distributed  \n" +
            		"with this software, leaving it to the user  \n" +
            		"to get it and keep it updated. \n \n" +
            		"The author of this software can be contacted  \n" +
            		"by e-mail: gilnei.risso @ gmail.com");
        }
        
        
        
        
        jTextArea1.setAutoscrolls(false);
        jTextArea1.setFocusable(false);
        jTextArea1.setOpaque(false);
        jTextArea1.setRequestFocusEnabled(false);
        jTextArea1.setBorder(null);
        jScrollPane1.setViewportView(jTextArea1);

        this.add(jScrollPane1);
        jScrollPane1.setBounds(10, 0, 304, 357);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        okButton.setBounds(137, 368, 55, 23);
        getContentPane().add(okButton);
                

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private javax.swing.JButton okButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
}
