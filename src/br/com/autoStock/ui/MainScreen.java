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

import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;

import br.com.autoStock.Principal;
import br.com.autoStock.util.I18n;
import br.com.autoStock.util.entity.Configuration;
import br.com.autoStock.util.entity.PeriodMap;
import br.com.autoStock.util.entity.Stock;
import br.com.autoStock.util.entity.Trade;
import br.com.autoStock.util.exception.PeriodException;
import br.com.autoStock.util.exception.StockException;

/**
 *
 * @author gilnei
 */
public class MainScreen extends javax.swing.JFrame {

	static Logger logger = Logger.getLogger(MainScreen.class.getName());

	private static final long serialVersionUID = 1L;
	protected static char PERIOD;
	private Principal sistema;
	private Grafico grafico;

	private boolean isDataScreen;
	private PeriodMap historico;
	private Configuration conf;
	boolean isCrossHair;
	boolean scaleChart = true;
	boolean candleChart = true;

	private Calendar cal;
	private SimpleDateFormat simpleDateformat;

	private ArrayList<Configuration>  confAcompanhamento;
	private ArrayList<Configuration>  confSimulacao;

	public MainScreen(Principal system) {

		this.sistema = system;	

		initComponents();
	}

	private void initComponents() {
		
		simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");

		cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -2);

		stock = new javax.swing.JLabel();
		stockCombo = new javax.swing.JComboBox();
		calendar = new javax.swing.JLabel();

		conf = new Configuration();

		dateStart = new javax.swing.JFormattedTextField(); 
		try 
		{ 
			dateStart.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")) ); 
		} 
		catch (java.text.ParseException ex){ ex.printStackTrace(); } 

		dateEnd = new javax.swing.JFormattedTextField(); 
		try 
		{ 
			dateEnd.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")) ); 
		} 
		catch (java.text.ParseException ex){ ex.printStackTrace(); } 

		dayButton = new javax.swing.JButton();
		weekButton = new javax.swing.JButton();
		tabbedPane = new javax.swing.JTabbedPane();
		chart = new javax.swing.JDesktopPane();
		indicators = new javax.swing.JPanel();
		eixoPrincipal = new javax.swing.JPanel();
		qtMM1 = new javax.swing.JTextField();
		tpMM1 = new javax.swing.JComboBox();
		tpMM2 = new javax.swing.JComboBox();
		qtMM2 = new javax.swing.JTextField();
		tpMM3 = new javax.swing.JComboBox();
		qtMM3 = new javax.swing.JTextField();
		tpMM4 = new javax.swing.JComboBox();
		qtMM4 = new javax.swing.JTextField();
		qtMM5 = new javax.swing.JTextField();
		tpMM5 = new javax.swing.JComboBox();
		qtBB = new javax.swing.JTextField();
		mm1 = new javax.swing.JLabel();
		mm2 = new javax.swing.JLabel();
		mm3 = new javax.swing.JLabel();
		mm4 = new javax.swing.JLabel();
		mm5 = new javax.swing.JLabel();
		bb = new javax.swing.JLabel();
		desvio = new javax.swing.JLabel();
		dsvBB = new javax.swing.JTextField();
		indVolume = new javax.swing.JPanel();
		isVolSelect = new javax.swing.JCheckBox();
		qtMMVol1 = new javax.swing.JTextField();
		qtMMVol2 = new javax.swing.JTextField();
		qtMMVol3 = new javax.swing.JTextField();
		tpMMVol1 = new javax.swing.JComboBox();
		tpMMVol2 = new javax.swing.JComboBox();
		tpMMVol3 = new javax.swing.JComboBox();
		qtMMObv1 = new javax.swing.JTextField();
		qtMMObv2 = new javax.swing.JTextField();
		qtMMObv3 = new javax.swing.JTextField();
		tpMMObv1 = new javax.swing.JComboBox();
		tpMMObv2 = new javax.swing.JComboBox();
		tpMMObv3 = new javax.swing.JComboBox();
		mm6 = new javax.swing.JLabel();
		mm7 = new javax.swing.JLabel();
		mm8 = new javax.swing.JLabel();
		indOBV = new javax.swing.JPanel();
		isOBVSelect = new javax.swing.JCheckBox();
		qtMMIfr1 = new javax.swing.JTextField();
		qtMMIfr2 = new javax.swing.JTextField();
		qtMMIfr3 = new javax.swing.JTextField();
		tpMMIfr1 = new javax.swing.JComboBox();
		tpMMIfr2 = new javax.swing.JComboBox();
		tpMMIfr3 = new javax.swing.JComboBox();
		ifr = new javax.swing.JLabel();
		qtIfr = new javax.swing.JTextField();
		sc = new javax.swing.JLabel();
		scIfr = new javax.swing.JTextField();
		sv = new javax.swing.JLabel();
		svIfr = new javax.swing.JTextField();
		mm9 = new javax.swing.JLabel();
		mm10 = new javax.swing.JLabel();
		mm11 = new javax.swing.JLabel();
		mm12 = new javax.swing.JLabel();
		mm13 = new javax.swing.JLabel();
		mm14 = new javax.swing.JLabel();
		indIFR = new javax.swing.JPanel();
		isIFRSelect = new javax.swing.JCheckBox();
		k = new javax.swing.JLabel();
		qtK = new javax.swing.JTextField();
		d = new javax.swing.JLabel();
		qtD = new javax.swing.JTextField();
		isEstLow = new javax.swing.JCheckBox();
		qtEstLow = new javax.swing.JTextField();
		isCrossEst = new javax.swing.JCheckBox();
		scE = new javax.swing.JLabel();
		scEst = new javax.swing.JTextField();
		svE = new javax.swing.JLabel();
		svEst = new javax.swing.JTextField();
		indMACD = new javax.swing.JPanel();
		isEstSelect = new javax.swing.JCheckBox();
		isMACDSelect = new javax.swing.JCheckBox();
		isDmiSelect = new javax.swing.JCheckBox();
		isMMNSelect = new javax.swing.JCheckBox();
		macdL = new javax.swing.JLabel();
		qtMacdL = new javax.swing.JTextField();
		isMacdCross = new javax.swing.JCheckBox();
		macdC = new javax.swing.JLabel();
		macdS = new javax.swing.JLabel();
		qtMacdC = new javax.swing.JTextField();
		qtMacdS = new javax.swing.JTextField();
		indEstocastico = new javax.swing.JPanel();
		indDmi = new javax.swing.JPanel();
		dmiPeriod = new javax.swing.JLabel();
		dmiAverage = new javax.swing.JLabel();
		qtDmiPeriod = new javax.swing.JTextField();
		qtDmiAverage = new javax.swing.JTextField();
		indMMNormal = new javax.swing.JPanel();
		shortDidi = new javax.swing.JLabel();
		mediumDidi = new javax.swing.JLabel();
		longDidi = new javax.swing.JLabel();
		qtShortDidi = new javax.swing.JTextField();
		qtMediumDidi = new javax.swing.JTextField();
		qtLongDidi = new javax.swing.JTextField();
		exportButton = new javax.swing.JButton();
		candleButton = new javax.swing.JButton();
		lineChartButton = new javax.swing.JButton();
		arithmeticButton = new javax.swing.JButton();
		logButton = new javax.swing.JButton();
		crossHairButton = new javax.swing.JButton();
		infoButton = new javax.swing.JButton();
		monthButton = new javax.swing.JButton();
		dateField = new javax.swing.JTextField();
		valueField = new javax.swing.JTextField();
		extFiboButton = new javax.swing.JButton();
		arrowButton = new javax.swing.JButton();
		lineButton = new javax.swing.JButton();
		parallelButton = new javax.swing.JButton();
		riskGainButton = new javax.swing.JButton();
		evolutionButton = new javax.swing.JButton();
		horizontalButton = new javax.swing.JButton();
		verticalButton = new javax.swing.JButton();
		rectButton = new javax.swing.JButton();
		retFiboButton = new javax.swing.JButton();
		hFiboButton = new javax.swing.JButton();
		textButton = new javax.swing.JButton();
		menu = new javax.swing.JMenuBar();
		file = new javax.swing.JMenu();
		open = new javax.swing.JMenuItem();
		save = new javax.swing.JMenuItem();
		selStock = new javax.swing.JMenuItem();
		confMenu = new javax.swing.JMenuItem();
		sepFile = new javax.swing.JPopupMenu.Separator();
		sepConf = new javax.swing.JPopupMenu.Separator();
		export = new javax.swing.JMenuItem();
		sepExit = new javax.swing.JPopupMenu.Separator();
		exit = new javax.swing.JMenuItem();
		tools = new javax.swing.JMenu();
		arrow = new javax.swing.JMenuItem();
		line = new javax.swing.JMenuItem();
		parallelLines = new javax.swing.JMenuItem();
		horizontalStraight = new javax.swing.JMenuItem();
		verticalStraight = new javax.swing.JMenuItem();
		rectangle = new javax.swing.JMenuItem();
		evolutionStraight = new javax.swing.JMenuItem();
		text = new javax.swing.JMenuItem();
		fiboExtension = new javax.swing.JMenuItem();
		vFiboRetraction = new javax.swing.JMenuItem();
		hFiboRetraction = new javax.swing.JMenuItem();
		riskGain = new javax.swing.JMenuItem();
		view = new javax.swing.JMenu();
		scale = new javax.swing.JMenu();
		arithmetic = new javax.swing.JMenuItem();
		logarithmic = new javax.swing.JMenuItem();
		crossHair = new javax.swing.JMenuItem();
		dataView = new javax.swing.JMenuItem();
		period = new javax.swing.JMenu();
		daily = new javax.swing.JMenuItem();
		weekly = new javax.swing.JMenuItem();
		monthly = new javax.swing.JMenuItem();
		type = new javax.swing.JMenu();
		candle = new javax.swing.JMenuItem();
		linechart = new javax.swing.JMenuItem();
		simulateMenu = new javax.swing.JMenu();
		selectSimulate = new javax.swing.JMenuItem();
		simulate = new javax.swing.JMenuItem();
		sepSimulate = new javax.swing.JPopupMenu.Separator();
		monitorMenu = new javax.swing.JMenu();
		selectMonitor = new javax.swing.JMenuItem();
		monitor = new javax.swing.JMenuItem();
		stopMonitor = new javax.swing.JMenuItem();
		sepMonitor = new javax.swing.JPopupMenu.Separator();
		help = new javax.swing.JMenu();
		tutorial = new javax.swing.JMenuItem();
		sepHelp = new javax.swing.JPopupMenu.Separator();
		useTerms = new javax.swing.JMenuItem();
		about = new javax.swing.JMenuItem();
		report = new javax.swing.JPanel();
		reportText = new javax.swing.JTextArea();
		scroll = new javax.swing.JScrollPane();

		qtBB.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				bollingerFocusLost(evt);
			}
		});

		dsvBB.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				bollingerFocusLost(evt);
			}
		});

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		stock.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		stock.setIcon(new javax.swing.ImageIcon("image/dinheiro.gif")); // NOI18N

		stockCombo.setToolTipText(I18n.getMessage("ec"));
		stockCombo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stockComboActionPerformed(evt);
			}
		});

		calendar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		calendar.setIcon(new javax.swing.ImageIcon("image/calendario.gif")); // NOI18N

		dateStart.setText(simpleDateformat.format(cal.getTime()));
		dateStart.setToolTipText(I18n.getMessage("cc"));
		dateStart.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				dateFocusLost(evt);
			}
		});

		dateEnd.setText(simpleDateformat.format(Calendar.getInstance().getTime()));
		dateEnd.setToolTipText(I18n.getMessage("cd"));
		dateEnd.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				dateFocusLost(evt);
			}
		});

		dayButton.setIcon(new javax.swing.ImageIcon("image/dia.gif")); // NOI18N
		dayButton.setToolTipText(I18n.getMessage("ed"));
		dayButton.setEnabled(false);
		dayButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dayButtonActionPerformed(evt);
			}
		});

		weekButton.setIcon(new javax.swing.ImageIcon("image/semana.gif")); // NOI18N
		weekButton.setToolTipText(I18n.getMessage("ef"));
		weekButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				weekButtonActionPerformed(evt);
			}
		});

		chart.setLayout(null);

		tabbedPane.addTab(I18n.getMessage("eg"), chart);

		reportText.setColumns(20);
		reportText.setRows(5);
		reportText.setEditable(false);
		
		scroll.setViewportView(reportText);

		javax.swing.GroupLayout reportLayout = new javax.swing.GroupLayout(report);
		report.setLayout(reportLayout);
		reportLayout.setHorizontalGroup(
				reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(reportLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1176, Short.MAX_VALUE)
						.addContainerGap())
		);
		reportLayout.setVerticalGroup(
				reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(reportLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
						.addContainerGap())
		);

		tabbedPane.addTab(I18n.getMessage("eh"), report);

		eixoPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, I18n.getMessage("ei"), 
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
				javax.swing.border.TitledBorder.DEFAULT_POSITION, 
				new java.awt.Font("Tahoma", 0, 11), 
				new java.awt.Color(102, 102, 102))); // NOI18N
		eixoPrincipal.setPreferredSize(new java.awt.Dimension(291, 226));

		qtMM1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMM1FocusLost(evt);
			}
		});
		
		tpMM1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") }));
		tpMM1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMM1FocusLost(evt);
			}
		});

		qtMM2.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMM2FocusLost(evt);
			}
		});
		tpMM2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") }));
		tpMM2.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMM2FocusLost(evt);
			}
		});

		qtMM3.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMM3FocusLost(evt);
			}
		});
		tpMM3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") }));
		tpMM3.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMM3FocusLost(evt);
			}
		});

		qtMM4.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMM4FocusLost(evt);
			}
		});
		tpMM4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") }));
		tpMM4.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMM4FocusLost(evt);
			}
		});

		qtMM5.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMM5FocusLost(evt);
			}
		});
		tpMM5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") }));
		tpMM5.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMM5FocusLost(evt);
			}
		});

		mm1.setText(I18n.getMessage("el"));
		mm2.setText(I18n.getMessage("el"));
		mm3.setText(I18n.getMessage("el"));
		mm4.setText(I18n.getMessage("el"));
		mm5.setText(I18n.getMessage("el"));
		bb.setText(I18n.getMessage("em"));

		desvio.setText(I18n.getMessage("en"));

		javax.swing.GroupLayout eixoPrincipalLayout = new javax.swing.GroupLayout(eixoPrincipal);
		eixoPrincipal.setLayout(eixoPrincipalLayout);
		eixoPrincipalLayout.setHorizontalGroup(
				eixoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(eixoPrincipalLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(eixoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(eixoPrincipalLayout.createSequentialGroup()
										.addComponent(bb)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(qtBB, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(eixoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)                            
												.addGroup(eixoPrincipalLayout.createSequentialGroup()
														.addComponent(desvio)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(dsvBB, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
														.addGroup(eixoPrincipalLayout.createSequentialGroup()
																.addComponent(mm1)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(qtMM1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(tpMM1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addGroup(eixoPrincipalLayout.createSequentialGroup()
																		.addComponent(mm2)
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(qtMM2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(tpMM2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addGroup(eixoPrincipalLayout.createSequentialGroup()
																				.addComponent(mm3)
																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(qtMM3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(tpMM3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addGroup(eixoPrincipalLayout.createSequentialGroup()
																						.addComponent(mm4)
																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																						.addComponent(qtMM4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																						.addComponent(tpMM4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(eixoPrincipalLayout.createSequentialGroup()
																								.addComponent(mm5)
																								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(qtMM5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(tpMM5, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
																								.addGap(125, 125, 125))
		);
		eixoPrincipalLayout.setVerticalGroup(
				eixoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(eixoPrincipalLayout.createSequentialGroup()
						.addGroup(eixoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(mm1)
								.addComponent(qtMM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(tpMM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(eixoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(mm2)
										.addComponent(qtMM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(tpMM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(eixoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(mm3)
												.addComponent(qtMM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(tpMM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(eixoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(mm4)
														.addComponent(qtMM4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(tpMM4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(eixoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(eixoPrincipalLayout.createSequentialGroup()
																		.addGroup(eixoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(mm5)
																				.addComponent(qtMM5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(tpMM5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addGap(18, 18, 18)
																				.addGroup(eixoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(bb)
																						.addComponent(qtBB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(desvio)
																						.addComponent(dsvBB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
		);

		indVolume.setBorder(javax.swing.BorderFactory.createTitledBorder(null, I18n.getMessage("by"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N
		indVolume.setPreferredSize(new java.awt.Dimension(291, 226));

		isVolSelect.setText(I18n.getMessage("eo"));
		isVolSelect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				volumeActionPerformed(evt);
			}
		});

		qtMMVol1.setEditable(false);
		qtMMVol1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMVol1FocusLost(evt);
			}
		});
		tpMMVol1.setEnabled(false);
		tpMMVol1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMVol1FocusLost(evt);
			}
		});
		tpMMVol1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") })); 

		qtMMVol2.setEditable(false);
		qtMMVol2.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMVol2FocusLost(evt);
			}
		});
		tpMMVol2.setEnabled(false);
		tpMMVol2.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMVol2FocusLost(evt);
			}
		});
		tpMMVol2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") }));

		qtMMVol3.setEditable(false);
		qtMMVol3.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMVol3FocusLost(evt);
			}
		});
		tpMMVol3.setEnabled(false);
		tpMMVol3.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMVol3FocusLost(evt);
			}
		});
		tpMMVol3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") }));

		mm6.setText(I18n.getMessage("el")); 
		mm7.setText(I18n.getMessage("el"));
		mm8.setText(I18n.getMessage("el"));

		javax.swing.GroupLayout volumeLayout = new javax.swing.GroupLayout(indVolume);
		indVolume.setLayout(volumeLayout);
		volumeLayout.setHorizontalGroup(
				volumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(volumeLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(volumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(isVolSelect, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(volumeLayout.createSequentialGroup()
										.addComponent(mm6)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(qtMMVol1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(volumeLayout.createSequentialGroup()
												.addComponent(mm7)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(qtMMVol2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(volumeLayout.createSequentialGroup()
														.addComponent(mm8)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(qtMMVol3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(volumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																.addComponent(tpMMVol3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(tpMMVol2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(tpMMVol1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addGap(47, 47, 47))
		);
		volumeLayout.setVerticalGroup(
				volumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(volumeLayout.createSequentialGroup()
						.addComponent(isVolSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(11, 11, 11)
						.addGroup(volumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(qtMMVol1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(mm6)
								.addComponent(tpMMVol1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(volumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(tpMMVol2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(qtMMVol2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(mm7))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(volumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(tpMMVol3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(qtMMVol3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(mm8))
												.addContainerGap(96, Short.MAX_VALUE))
		);

		indOBV.setBorder(javax.swing.BorderFactory.createTitledBorder(null, I18n.getMessage("bz"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N
		indOBV.setPreferredSize(new java.awt.Dimension(291, 226));

		isOBVSelect.setText(I18n.getMessage("ep"));
		isOBVSelect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				obvActionPerformed(evt);
			}
		});

		qtMMObv1.setEditable(false);
		qtMMObv1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMObv1FocusLost(evt);
			}
		});
		tpMMObv1.setEnabled(false);
		tpMMObv1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") }));
		tpMMObv1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMObv1FocusLost(evt);
			}
		});
		qtMMObv2.setEditable(false);
		qtMMObv2.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMObv2FocusLost(evt);
			}
		});
		tpMMObv2.setEnabled(false);
		tpMMObv2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") }));
		tpMMObv2.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMObv2FocusLost(evt);
			}
		});
		qtMMObv3.setEditable(false);
		qtMMObv3.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMObv3FocusLost(evt);
			}
		});
		tpMMObv3.setEnabled(false);
		tpMMObv3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") }));
		tpMMObv3.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMObv3FocusLost(evt);
			}
		});

		mm9.setText(I18n.getMessage("el"));

		mm10.setText(I18n.getMessage("el"));

		mm11.setText(I18n.getMessage("el"));

		javax.swing.GroupLayout obvLayout = new javax.swing.GroupLayout(indOBV);
		indOBV.setLayout(obvLayout);
		obvLayout.setHorizontalGroup(
				obvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(obvLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(obvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(isOBVSelect, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(obvLayout.createSequentialGroup()
										.addComponent(mm9)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
										.addComponent(qtMMObv1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(obvLayout.createSequentialGroup()
												.addComponent(mm10)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(qtMMObv2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(obvLayout.createSequentialGroup()
														.addComponent(mm11)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
														.addComponent(qtMMObv3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(obvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																.addComponent(tpMMObv3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(tpMMObv2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(tpMMObv1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addGap(47, 47, 47))
		);
		obvLayout.setVerticalGroup(
				obvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(obvLayout.createSequentialGroup()
						.addComponent(isOBVSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(11, 11, 11)
						.addGroup(obvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(qtMMObv1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(mm9)
								.addComponent(tpMMObv1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(obvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(tpMMObv2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(qtMMObv2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(mm10))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(obvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(tpMMObv3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(qtMMObv3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(mm11))
												.addContainerGap(96, Short.MAX_VALUE))
		);

		tpMMIfr1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") }));
		tpMMIfr1.setEnabled(false);
		tpMMIfr1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMIfr1FocusLost(evt);
			}
		});
		qtMMIfr1.setEditable(false);
		qtMMIfr1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMIfr1FocusLost(evt);
			}
		});
		tpMMIfr2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") }));
		tpMMIfr2.setEnabled(false);
		tpMMIfr2.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMIfr2FocusLost(evt);
			}
		});
		qtMMIfr2.setEditable(false);
		qtMMIfr2.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMIfr2FocusLost(evt);
			}
		});
		tpMMIfr3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18n.getMessage("ej"),I18n.getMessage("ek") }));
		tpMMIfr3.setEnabled(false);
		tpMMIfr3.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMIfr3FocusLost(evt);
			}
		});
		qtMMIfr3.setEditable(false);
		qtMMIfr3.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMMIfr3FocusLost(evt);
			}
		});

		qtIfr.setEditable(false);
		qtIfr.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtIfrFocusLost(evt);
			}
		});
		scIfr.setEditable(false);
		scIfr.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtIfrFocusLost(evt);
			}
		});
		svIfr.setEditable(false);        
		svIfr.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtIfrFocusLost(evt);
			}
		});

		ifr.setText(I18n.getMessage("bx"));

		sc.setText(I18n.getMessage("bt"));

		sv.setText(I18n.getMessage("bu"));

		mm12.setText(I18n.getMessage("el"));

		mm13.setText(I18n.getMessage("el"));

		mm14.setText(I18n.getMessage("el"));

		indIFR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, I18n.getMessage("bx"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N
		indIFR.setPreferredSize(new java.awt.Dimension(291, 226));

		isIFRSelect.setText(I18n.getMessage("eq"));
		isIFRSelect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				isIFRSelectActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout ifrLayout = new javax.swing.GroupLayout(indIFR);
		indIFR.setLayout(ifrLayout);         
		ifrLayout.setHorizontalGroup(
				ifrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(ifrLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(ifrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(ifrLayout.createSequentialGroup()
										.addComponent(isIFRSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(197, 197, 197))
										.addGroup(ifrLayout.createSequentialGroup()
												.addGroup(ifrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(javax.swing.GroupLayout.Alignment.LEADING, ifrLayout.createSequentialGroup()
																.addComponent(ifr)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(qtIfr, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addGroup(javax.swing.GroupLayout.Alignment.LEADING, ifrLayout.createSequentialGroup()
																		.addComponent(sc)
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(scIfr, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addGroup(javax.swing.GroupLayout.Alignment.LEADING, ifrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																				.addComponent(mm12)
																				.addComponent(mm13))
																				.addComponent(mm14, javax.swing.GroupLayout.Alignment.LEADING))
																				.addGroup(ifrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(ifrLayout.createSequentialGroup()
																								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																								.addGroup(ifrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
																										.addGroup(javax.swing.GroupLayout.Alignment.LEADING, ifrLayout.createSequentialGroup()
																												.addComponent(qtMMIfr3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																												.addComponent(tpMMIfr3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
																												.addGroup(javax.swing.GroupLayout.Alignment.LEADING, ifrLayout.createSequentialGroup()
																														.addComponent(qtMMIfr2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(tpMMIfr2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
																														.addGroup(javax.swing.GroupLayout.Alignment.LEADING, ifrLayout.createSequentialGroup()
																																.addComponent(qtMMIfr1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addComponent(tpMMIfr1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
																																.addGap(113, 113, 113))
																																.addGroup(ifrLayout.createSequentialGroup()
																																		.addGap(14, 14, 14)
																																		.addComponent(sv)
																																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(svIfr, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																																		.addContainerGap())))))
		);
		ifrLayout.setVerticalGroup(
				ifrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(ifrLayout.createSequentialGroup()
						.addComponent(isIFRSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(ifrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(ifr)
								.addComponent(qtIfr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(ifrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(sc)
										.addComponent(scIfr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(svIfr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(sv))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
										.addGroup(ifrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(qtMMIfr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(tpMMIfr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(mm12))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(ifrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(qtMMIfr2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(tpMMIfr2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(mm13))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(ifrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(ifrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(qtMMIfr3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(tpMMIfr3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addComponent(mm14))
																		.addGap(27, 27, 27))
		);


		isMACDSelect.setText(I18n.getMessage("er"));
		isMACDSelect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				macdActionPerformed(evt);
			}
		});

		macdL.setText(I18n.getMessage("es"));
		isMacdCross.setText(I18n.getMessage("et"));
		macdC.setText(I18n.getMessage("eu"));
		macdS.setText(I18n.getMessage("ev"));

		qtMacdS.setEditable(false);
		qtMacdS.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMacdActionPerformed(evt);
			}
		});
		qtMacdC.setEditable(false);
		qtMacdC.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMacdActionPerformed(evt);
			}
		});
		qtMacdL.setEditable(false);
		qtMacdL.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMacdActionPerformed(evt);
			}
		});
		isMacdCross.setEnabled(false);
		isMacdCross.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtMacdActionPerformed(evt);
			}
		});

		indMACD.setBorder(javax.swing.BorderFactory.createTitledBorder(null, I18n.getMessage("br"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N
		indMACD.setPreferredSize(new java.awt.Dimension(291, 226));
		javax.swing.GroupLayout macdLayout = new javax.swing.GroupLayout(indMACD);
		indMACD.setLayout(macdLayout);
		macdLayout.setHorizontalGroup(
				macdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(macdLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(macdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, macdLayout.createSequentialGroup()
										.addComponent(isMacdCross)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 456, Short.MAX_VALUE)
										.addGap(129, 129, 129))
										.addGroup(macdLayout.createSequentialGroup()
												.addGroup(macdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(javax.swing.GroupLayout.Alignment.LEADING, macdLayout.createSequentialGroup()
																.addGroup(macdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(macdC)
																		.addComponent(macdS))
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(macdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(qtMacdS, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(qtMacdC, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
																				.addGroup(javax.swing.GroupLayout.Alignment.LEADING, macdLayout.createSequentialGroup()
																						.addComponent(macdL)
																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																						.addComponent(qtMacdL, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addComponent(isMACDSelect, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGap(720, 720, 720))))
																						.addGroup(macdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING))
		);
		macdLayout.setVerticalGroup(
				macdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(macdLayout.createSequentialGroup()
						.addComponent(isMACDSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(macdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(macdL)
								.addComponent(qtMacdL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(macdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(macdC)
										.addComponent(qtMacdC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(macdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(macdS)
												.addComponent(qtMacdS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(macdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(macdLayout.createSequentialGroup()
																.addGap(18, 18, 18)
																.addComponent(isMacdCross)
																.addContainerGap())))
		);		

		k.setText("%K");

		d.setText("%D");

		isEstLow.setText(I18n.getMessage("ex"));
		isEstLow.setEnabled(false);
		isEstLow.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(isEstLow.isSelected()){
					qtEstLow.setEditable(true);
				}
				else{
					qtEstLow.setEditable(false);
				}
				estocFocusLost(null);
			}
		});

		isCrossEst.setText(I18n.getMessage("et"));

		scE.setText(I18n.getMessage("bt"));

		svE.setText(I18n.getMessage("bu"));

		isEstSelect.setText(I18n.getMessage("ey"));
		isEstSelect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				estocasticoActionPerformed(evt);
			}
		});

		qtK.setEditable(false);
		qtK.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				estocFocusLost(evt);
			}
		});
		qtD.setEditable(false);
		qtD.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				estocFocusLost(evt);
			}
		});
		scEst.setEditable(false);
		scEst.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				estocFocusLost(evt);
			}
		});
		svEst.setEditable(false);
		svEst.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				estocFocusLost(evt);
			}
		});

		qtEstLow.setEditable(false);
		qtEstLow.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				estocFocusLost(evt);
			}
		});
		isCrossEst.setEnabled(false);
		isCrossEst.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				estocFocusLost(evt);
			}
		});

		indEstocastico.setBorder(javax.swing.BorderFactory.createTitledBorder(null, I18n.getMessage("ez"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N
		indEstocastico.setPreferredSize(new java.awt.Dimension(291, 226));

		javax.swing.GroupLayout estocLayout = new javax.swing.GroupLayout(indEstocastico);
		indEstocastico.setLayout(estocLayout);
		estocLayout.setHorizontalGroup(
				estocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(estocLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(estocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(isEstSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(estocLayout.createSequentialGroup()
										.addGroup(estocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addGroup(javax.swing.GroupLayout.Alignment.LEADING, estocLayout.createSequentialGroup()
														.addComponent(scE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(scEst, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(svE))
														.addGroup(javax.swing.GroupLayout.Alignment.LEADING, estocLayout.createSequentialGroup()
																.addComponent(k)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(qtK, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18, 18)
																.addComponent(d)))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(estocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																		.addComponent(svEst, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(qtD, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addGroup(estocLayout.createSequentialGroup()
																				.addComponent(isEstLow)
																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(qtEstLow, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addComponent(isCrossEst))
																				.addContainerGap(132, Short.MAX_VALUE))
		);
		estocLayout.setVerticalGroup(
				estocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(estocLayout.createSequentialGroup()
						.addComponent(isEstSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(estocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(k)
								.addComponent(qtK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(qtD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(d))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(estocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(estocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(scE)
												.addComponent(scEst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(estocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(svE)
														.addComponent(svEst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addGroup(estocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																.addComponent(isEstLow)
																.addComponent(qtEstLow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(isCrossEst)
																.addGap(66, 66, 66))
		);
		
		isDmiSelect.setText(I18n.getMessage("fa"));
		isDmiSelect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dmiActionPerformed(evt);
			}
		});

		dmiPeriod.setText(I18n.getMessage("fb"));
		dmiAverage.setText(I18n.getMessage("fc"));

		qtDmiPeriod.setEditable(false);
		 
		qtDmiAverage.setEditable(false);
		qtDmiAverage.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtDmiActionPerformed(evt);
			}
		});
		
		indDmi.setBorder(javax.swing.BorderFactory.createTitledBorder(null, I18n.getMessage("fd"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N
		indDmi.setPreferredSize(new java.awt.Dimension(291, 226));
		javax.swing.GroupLayout dmiLayout = new javax.swing.GroupLayout(indDmi);
		indDmi.setLayout(dmiLayout);
		dmiLayout.setHorizontalGroup(
				dmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(dmiLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(dmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dmiLayout.createSequentialGroup()
										.addComponent(isDmiSelect)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 456, Short.MAX_VALUE)
										.addGap(129, 129, 129))
										.addGroup(dmiLayout.createSequentialGroup()
												.addGroup(dmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(javax.swing.GroupLayout.Alignment.LEADING, dmiLayout.createSequentialGroup()
																.addGroup(dmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(dmiPeriod)
																		.addComponent(dmiAverage))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addGroup(dmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(qtDmiAverage, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(qtDmiPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))																				
														.addComponent(isDmiSelect, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))												
		))));		
	
		dmiLayout.setVerticalGroup(
				dmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(dmiLayout.createSequentialGroup()
						.addComponent(isDmiSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(dmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(dmiPeriod)
								.addComponent(qtDmiPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(dmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(dmiAverage)
								.addComponent(qtDmiAverage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))										
		));
		
		isMMNSelect.setText(I18n.getMessage("fe"));
		isMMNSelect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				didiActionPerformed(evt);
			}
		});
		
		shortDidi.setText(I18n.getMessage("eu"));
		mediumDidi.setText(I18n.getMessage("ff"));
		longDidi.setText(I18n.getMessage("es"));
		
		qtShortDidi.setEditable(false);
		qtShortDidi.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtDidiActionPerformed(evt);
			}
		});
		
		qtMediumDidi.setEditable(false);
		qtMediumDidi.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtDidiActionPerformed(evt);
			}
		});
		
		qtLongDidi.setEditable(false);
		qtLongDidi.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				qtDidiActionPerformed(evt);
			}
		});
		
		indMMNormal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, I18n.getMessage("fg"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N
		indMMNormal.setPreferredSize(new java.awt.Dimension(291, 226));
		javax.swing.GroupLayout didiLayout = new javax.swing.GroupLayout(indMMNormal);
		indMMNormal.setLayout(didiLayout);
		didiLayout.setHorizontalGroup(
				didiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(didiLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(didiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, didiLayout.createSequentialGroup()
										.addComponent(isMMNSelect)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 456, Short.MAX_VALUE)
										.addGap(129, 129, 129))
										.addGroup(didiLayout.createSequentialGroup()
												.addGroup(didiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(javax.swing.GroupLayout.Alignment.LEADING, didiLayout.createSequentialGroup()
																.addGroup(didiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(shortDidi)
																		.addComponent(mediumDidi)
																		.addComponent(longDidi))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addGroup(didiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(qtShortDidi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(qtMediumDidi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(qtLongDidi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))																				
														.addComponent(isMMNSelect, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))												
		))));
		
		didiLayout.setVerticalGroup(
				didiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(didiLayout.createSequentialGroup()
						.addComponent(isMMNSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(didiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(shortDidi)
								.addComponent(qtShortDidi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(didiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(mediumDidi)
								.addComponent(qtMediumDidi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(didiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(longDidi)
								.addComponent(qtLongDidi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		));	

		javax.swing.GroupLayout configurationLayout = new javax.swing.GroupLayout(indicators);
		indicators.setLayout(configurationLayout);
		configurationLayout.setHorizontalGroup(
				configurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(configurationLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(configurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(indVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(eixoPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(10, 10, 10)
						.addGroup(configurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(indIFR, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(indOBV, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(10, 10, 10)
						.addGroup(configurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(indMACD, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(indEstocastico, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(10, 10, 10)
						.addGroup(configurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(indDmi, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(indMMNormal, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
		));
		configurationLayout.setVerticalGroup(
				configurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(configurationLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(configurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)								
								.addGroup(configurationLayout.createSequentialGroup()
										.addComponent(indMACD, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(indEstocastico, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(configurationLayout.createSequentialGroup()
										.addComponent(indOBV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(indIFR, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(configurationLayout.createSequentialGroup()
										.addComponent(eixoPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(indVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(configurationLayout.createSequentialGroup()
										.addComponent(indDmi, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(indMMNormal, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
		)));

		tabbedPane.addTab(I18n.getMessage("fh"), indicators);

		exportButton.setIcon(new javax.swing.ImageIcon("image/exportar.gif")); // NOI18N
		exportButton.setToolTipText(I18n.getMessage("fj"));
		exportButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exportButtonActionPerformed(evt);
			}
		});

		candleButton.setIcon(new javax.swing.ImageIcon("image/candle.gif")); // NOI18N
		candleButton.setToolTipText(I18n.getMessage("fk"));
		candleButton.setEnabled(false);
		candleButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				candleActionPerformed(evt);
			}
		});

		lineChartButton.setIcon(new javax.swing.ImageIcon("image/linha.gif")); // NOI18N
		lineChartButton.setToolTipText(I18n.getMessage("fl"));
		lineChartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				linechartActionPerformed(evt);
			}
		});

		arithmeticButton.setIcon(new javax.swing.ImageIcon("image/escalaA.gif")); // NOI18N
		arithmeticButton.setToolTipText(I18n.getMessage("fm"));
		arithmeticButton.setEnabled(false);
		arithmeticButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				arithmeticActionPerformed(evt);
			}
		});

		logButton.setIcon(new javax.swing.ImageIcon("image/escalaL.gif")); // NOI18N
		logButton.setToolTipText(I18n.getMessage("fn"));
		logButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logarithmicActionPerformed(evt);
			}
		});

		crossHairButton.setIcon(new javax.swing.ImageIcon("image/guia.gif")); // NOI18N
		crossHairButton.setToolTipText(I18n.getMessage("fo"));
		crossHairButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				crossHairActionPerformed(evt);
			}
		});

		infoButton.setIcon(new javax.swing.ImageIcon("image/janela.gif")); // NOI18N
		infoButton.setToolTipText(I18n.getMessage("fp"));
		infoButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				infoButtonActionPerformed(evt);
			}
		});

		monthButton.setIcon(new javax.swing.ImageIcon("image/mes.gif")); // NOI18N
		monthButton.setToolTipText(I18n.getMessage("fq"));
		monthButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				monthButtonActionPerformed(evt);
			}
		});

		dateField.setEditable(false);

		valueField.setEditable(false);

		extFiboButton.setIcon(new javax.swing.ImageIcon("image/extFibo.gif")); // NOI18N
		extFiboButton.setToolTipText(I18n.getMessage("fr"));
		extFiboButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fiboExtensionActionPerformed(evt);
			}
		});

		arrowButton.setIcon(new javax.swing.ImageIcon("image/seta24.gif")); // NOI18N
		arrowButton.setToolTipText(I18n.getMessage("fs"));
		arrowButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				arrowActionPerformed(evt);
			}
		});

		lineButton.setIcon(new javax.swing.ImageIcon("image/line.gif")); // NOI18N
		lineButton.setToolTipText(I18n.getMessage("ft"));
		lineButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				lineActionPerformed(evt);
			}
		});

		parallelButton.setIcon(new javax.swing.ImageIcon("image/paralelas.gif")); // NOI18N
		parallelButton.setToolTipText(I18n.getMessage("fu"));
		parallelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				parallelLinesActionPerformed(evt);
			}
		});

		riskGainButton.setIcon(new javax.swing.ImageIcon("image/lr.gif")); // NOI18N
		riskGainButton.setToolTipText(I18n.getMessage("fv"));
		riskGainButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				riskGainActionPerformed(evt);
			}
		});

		evolutionButton.setIcon(new javax.swing.ImageIcon("image/evolucao.gif")); // NOI18N
		evolutionButton.setToolTipText(I18n.getMessage("fx"));
		evolutionButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				evolutionStraightActionPerformed(evt);
			}
		});

		horizontalButton.setIcon(new javax.swing.ImageIcon("image/horizontal.gif")); // NOI18N
		horizontalButton.setToolTipText(I18n.getMessage("fy"));
		horizontalButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				horizontalStraightActionPerformed(evt);
			}
		});

		verticalButton.setIcon(new javax.swing.ImageIcon("image/vertical.gif")); // NOI18N
		verticalButton.setToolTipText(I18n.getMessage("fz"));
		verticalButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				verticalStraightActionPerformed(evt);
			}
		});

		rectButton.setIcon(new javax.swing.ImageIcon("image/retangulo.gif")); // NOI18N
		rectButton.setToolTipText(I18n.getMessage("ga"));
		rectButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rectangleActionPerformed(evt);
			}
		});

		retFiboButton.setIcon(new javax.swing.ImageIcon("image/retFiboH.gif")); // NOI18N
		retFiboButton.setToolTipText(I18n.getMessage("gb"));
		retFiboButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				vFiboRetractionActionPerformed(evt);
			}
		});

		hFiboButton.setIcon(new javax.swing.ImageIcon("image/retFiboV.gif")); // NOI18N
		hFiboButton.setToolTipText(I18n.getMessage("gc"));
		hFiboButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				hFiboRetractionActionPerformed(evt);
			}
		});

		textButton.setIcon(new javax.swing.ImageIcon("image/texto.gif")); // NOI18N
		textButton.setToolTipText(I18n.getMessage("gd"));
		textButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				textActionPerformed(evt);
			}
		});

		file.setMnemonic(I18n.getMessage("gn").charAt(0));
		file.setText(I18n.getMessage("go"));

		open.setIcon(new javax.swing.ImageIcon("image/open.gif")); // NOI18N
		open.setMnemonic(I18n.getMessage("gp").charAt(0)); 
		open.setText(I18n.getMessage("gq"));
		open.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				openActionPerformed(evt);
			}
		});
		file.add(open);

		save.setIcon(new javax.swing.ImageIcon("image/salvar.gif")); // NOI18N
		save.setMnemonic(I18n.getMessage("gr").charAt(0));
		save.setText(I18n.getMessage("gs"));
		save.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveActionPerformed(evt);
			}
		});
		file.add(save);

		selStock.setIcon(new javax.swing.ImageIcon("image/dinheiro.gif")); // NOI18N
		selStock.setMnemonic(I18n.getMessage("gt").charAt(0));
		selStock.setText(I18n.getMessage("gu"));
		selStock.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selStockActionPerformed(evt);
			}
		});
		file.add(selStock);        

		file.add(sepFile);
		
		//selStock.setIcon(new javax.swing.ImageIcon("image/dinheiro.gif")); // NOI18N
		confMenu.setMnemonic(I18n.getMessage("gv").charAt(0));
		confMenu.setText(I18n.getMessage("gx"));
		confMenu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				confMenuActionPerformed(evt);
			}
		});
		file.add(confMenu); 	
		
		file.add(sepConf);

		export.setIcon(new javax.swing.ImageIcon("image/exportar.gif")); // NOI18N
		export.setMnemonic(I18n.getMessage("gy").charAt(0));
		export.setText(I18n.getMessage("gz"));
		export.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exportButtonActionPerformed(evt);
			}
		});
		file.add(export);
		file.add(sepExit);

		exit.setIcon(new javax.swing.ImageIcon("image/sair.gif")); // NOI18N
		exit.setMnemonic(I18n.getMessage("ha").charAt(0));
		exit.setText(I18n.getMessage("hb"));
		exit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.exit(0);
			}
		});
		file.add(exit);

		menu.add(file);

		tools.setMnemonic(I18n.getMessage("hc").charAt(0));
		tools.setText(I18n.getMessage("hd"));

		arrow.setIcon(new javax.swing.ImageIcon("image/seta24.gif")); // NOI18N
		arrow.setMnemonic(I18n.getMessage("jb").charAt(0));
		arrow.setText(I18n.getMessage("fs"));
		arrow.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				arrowActionPerformed(evt);
			}
		});
		tools.add(arrow);

		line.setIcon(new javax.swing.ImageIcon("image/line.gif")); // NOI18N
		line.setMnemonic(I18n.getMessage("jc").charAt(0));
		line.setText(I18n.getMessage("ft"));
		line.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				lineActionPerformed(evt);
			}
		});
		tools.add(line);

		parallelLines.setIcon(new javax.swing.ImageIcon("image/paralelas.gif")); // NOI18N
		parallelLines.setMnemonic(I18n.getMessage("jd").charAt(0));
		parallelLines.setText(I18n.getMessage("fu"));
		parallelLines.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				parallelLinesActionPerformed(evt);
			}
		});
		tools.add(parallelLines);

		horizontalStraight.setIcon(new javax.swing.ImageIcon("image/horizontal.gif")); // NOI18N
		horizontalStraight.setMnemonic(I18n.getMessage("jg").charAt(0));
		horizontalStraight.setText(I18n.getMessage("fy"));
		horizontalStraight.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				horizontalStraightActionPerformed(evt);
			}
		});
		tools.add(horizontalStraight);

		verticalStraight.setIcon(new javax.swing.ImageIcon("image/vertical.gif")); // NOI18N
		verticalStraight.setMnemonic(I18n.getMessage("jh").charAt(0));
		verticalStraight.setText(I18n.getMessage("fz"));
		verticalStraight.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				verticalStraightActionPerformed(evt);
			}
		});
		tools.add(verticalStraight);

		rectangle.setIcon(new javax.swing.ImageIcon("image/retangulo.gif")); // NOI18N
		rectangle.setMnemonic(I18n.getMessage("ji").charAt(0));
		rectangle.setText(I18n.getMessage("ga"));
		rectangle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rectangleActionPerformed(evt);
			}
		});
		tools.add(rectangle);

		evolutionStraight.setIcon(new javax.swing.ImageIcon("image/evolucao.gif")); // NOI18N
		evolutionStraight.setMnemonic(I18n.getMessage("jf").charAt(0));
		evolutionStraight.setText(I18n.getMessage("fx"));
		evolutionStraight.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				evolutionStraightActionPerformed(evt);
			}
		});
		tools.add(evolutionStraight);

		text.setIcon(new javax.swing.ImageIcon("image/texto.gif")); // NOI18N
		text.setMnemonic(I18n.getMessage("jl").charAt(0));
		text.setText(I18n.getMessage("gd"));
		text.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				textActionPerformed(evt);
			}
		});
		tools.add(text);

		fiboExtension.setIcon(new javax.swing.ImageIcon("image/extFibo.gif")); // NOI18N
		fiboExtension.setMnemonic(I18n.getMessage("ja").charAt(0));
		fiboExtension.setText(I18n.getMessage("fr"));
		fiboExtension.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fiboExtensionActionPerformed(evt);
			}
		});
		tools.add(fiboExtension);

		vFiboRetraction.setIcon(new javax.swing.ImageIcon("image/retFiboH.gif")); // NOI18N
		vFiboRetraction.setMnemonic(I18n.getMessage("jj").charAt(0));
		vFiboRetraction.setText(I18n.getMessage("gb"));
		vFiboRetraction.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				vFiboRetractionActionPerformed(evt);
			}
		});
		tools.add(vFiboRetraction);

		hFiboRetraction.setIcon(new javax.swing.ImageIcon("image/retFiboV.gif")); // NOI18N
		hFiboRetraction.setMnemonic(I18n.getMessage("jk").charAt(0));
		hFiboRetraction.setText(I18n.getMessage("gc"));
		hFiboRetraction.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				hFiboRetractionActionPerformed(evt);
			}
		});
		tools.add(hFiboRetraction);

		riskGain.setIcon(new javax.swing.ImageIcon("image/lr.gif")); // NOI18N
		riskGain.setMnemonic(I18n.getMessage("je").charAt(0));
		riskGain.setText(I18n.getMessage("fv")); 
		riskGain.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				riskGainActionPerformed(evt);
			}
		});
		tools.add(riskGain);

		menu.add(tools);

		view.setMnemonic(I18n.getMessage("he").charAt(0));
		view.setText(I18n.getMessage("hf"));

		scale.setIcon(new javax.swing.ImageIcon("image/escala.gif")); // NOI18N
		scale.setMnemonic(I18n.getMessage("hg").charAt(0));
		scale.setText(I18n.getMessage("hh"));

		arithmetic.setIcon(new javax.swing.ImageIcon("image/escalaA.gif")); // NOI18N
		arithmetic.setMnemonic(I18n.getMessage("hi").charAt(0));
		arithmetic.setText(I18n.getMessage("ej"));
		arithmetic.setEnabled(false);
		arithmetic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				arithmeticActionPerformed(evt);
			}
		});
		scale.add(arithmetic);

		logarithmic.setIcon(new javax.swing.ImageIcon("image/escalaL.gif")); // NOI18N
		logarithmic.setMnemonic(I18n.getMessage("hj").charAt(0));
		logarithmic.setText(I18n.getMessage("hk"));
		logarithmic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logarithmicActionPerformed(evt);
			}
		});
		scale.add(logarithmic);

		view.add(scale);

		crossHair.setIcon(new javax.swing.ImageIcon("image/guia.gif")); // NOI18N
		crossHair.setMnemonic(I18n.getMessage("hl").charAt(0));
		crossHair.setText(I18n.getMessage("hm"));
		crossHair.setActionCommand(I18n.getMessage("hm"));
		crossHair.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				crossHairActionPerformed(evt);
			}
		});
		view.add(crossHair);

		dataView.setIcon(new javax.swing.ImageIcon("image/janela.gif")); // NOI18N
		dataView.setMnemonic(I18n.getMessage("hn").charAt(0));
		dataView.setText(I18n.getMessage("ho"));
		dataView.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				infoButtonActionPerformed(evt);
			}
		});
		view.add(dataView);

		period.setIcon(new javax.swing.ImageIcon("image/calendario.gif")); // NOI18N
		period.setMnemonic(I18n.getMessage("hp").charAt(0));
		period.setText(I18n.getMessage("hq"));

		daily.setIcon(new javax.swing.ImageIcon("image/dia.gif")); // NOI18N
		daily.setMnemonic(I18n.getMessage("hr").charAt(0));
		daily.setText(I18n.getMessage("hs"));
		daily.setEnabled(false);
		daily.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dayButtonActionPerformed(evt);
			}
		});
		period.add(daily);

		weekly.setIcon(new javax.swing.ImageIcon("image/semana.gif")); // NOI18N
		weekly.setMnemonic(I18n.getMessage("ht").charAt(0));
		weekly.setText(I18n.getMessage("hu"));
		weekly.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				weekButtonActionPerformed(evt);
			}
		});
		period.add(weekly);

		monthly.setIcon(new javax.swing.ImageIcon("image/mes.gif")); // NOI18N
		monthly.setMnemonic(I18n.getMessage("hv").charAt(0));
		monthly.setText(I18n.getMessage("hx"));
		monthly.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				monthButtonActionPerformed(evt);
			}
		});
		period.add(monthly);

		view.add(period);

		type.setIcon(new javax.swing.ImageIcon("image/grafico.gif")); // NOI18N
		type.setMnemonic(I18n.getMessage("hy").charAt(0));
		type.setText(I18n.getMessage("hz"));

		candle.setIcon(new javax.swing.ImageIcon("image/candle.gif")); // NOI18N
		candle.setMnemonic(I18n.getMessage("ia").charAt(0));
		candle.setText(I18n.getMessage("ib"));
		candle.setEnabled(false);
		candle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				candleActionPerformed(evt);
			}
		});
		type.add(candle);

		linechart.setIcon(new javax.swing.ImageIcon("image/linha.gif")); // NOI18N
		linechart.setMnemonic(I18n.getMessage("ic").charAt(0));
		linechart.setText(I18n.getMessage("id"));
		linechart.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				linechartActionPerformed(evt);
			}
		});
		type.add(linechart);

		view.add(type);

		menu.add(view);

		simulateMenu.setMnemonic(I18n.getMessage("ie").charAt(0));
		simulateMenu.setText(I18n.getMessage("if"));
		//simulateMenu.setEnabled(false);

		selectSimulate.setIcon(new javax.swing.ImageIcon("image/selecionar.gif")); // NOI18N
		selectSimulate.setMnemonic(I18n.getMessage("ig").charAt(0));
		selectSimulate.setText(I18n.getMessage("ih"));
		selectSimulate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectSimulateActionPerformed(evt);
			}
		});
		simulateMenu.add(selectSimulate);

		simulate.setIcon(new javax.swing.ImageIcon("image/play.gif")); // NOI18N
		simulate.setMnemonic(I18n.getMessage("ii").charAt(0));
		simulate.setText(I18n.getMessage("ij"));
		simulate.setEnabled(false);
		simulate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				simulateActionPerformed(evt);
			}
		});
		simulateMenu.add(simulate);
		simulateMenu.add(sepSimulate);
		
		menu.add(simulateMenu);

		monitorMenu.setMnemonic(I18n.getMessage("ik").charAt(0));
		monitorMenu.setText(I18n.getMessage("il"));

		selectMonitor.setIcon(new javax.swing.ImageIcon("image/selecionar.gif")); // NOI18N
		selectMonitor.setMnemonic(I18n.getMessage("im").charAt(0));
		selectMonitor.setText(I18n.getMessage("in"));
		selectMonitor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectMonitorActionPerformed(evt);
			}
		});
		monitorMenu.add(selectMonitor);
		monitorMenu.setEnabled(true); //TODO
		monitor.setIcon(new javax.swing.ImageIcon("image/play.gif")); // NOI18N
		monitor.setMnemonic(I18n.getMessage("ik").charAt(0));
		monitor.setText(I18n.getMessage("il"));
		monitor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				monitorActionPerformed(evt);
			}
		});
		monitor.setEnabled(false);
		monitorMenu.add(monitor);

		stopMonitor.setIcon(new javax.swing.ImageIcon("image/stop.gif")); // NOI18N
		stopMonitor.setMnemonic(I18n.getMessage("io").charAt(0));
		stopMonitor.setText(I18n.getMessage("ip"));
		stopMonitor.setEnabled(false);
		stopMonitor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stopMonitorActionPerformed(evt);
			}
		});
		
		monitorMenu.add(stopMonitor);
		monitorMenu.add(sepMonitor);

		menu.add(monitorMenu);

		help.setMnemonic(I18n.getMessage("iq").charAt(0));
		help.setText(I18n.getMessage("ir"));

		tutorial.setIcon(new javax.swing.ImageIcon("image/tutorial.gif")); // NOI18N
		tutorial.setMnemonic(I18n.getMessage("is").charAt(0));
		tutorial.setText(I18n.getMessage("it"));
		tutorial.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tutorialActionPerformed(evt);
			}
		});
		help.add(tutorial);
		help.add(sepHelp);

		useTerms.setIcon(new javax.swing.ImageIcon("image/termos.gif")); // NOI18N
		useTerms.setMnemonic(I18n.getMessage("iu").charAt(0));
		useTerms.setText(I18n.getMessage("iv"));
		useTerms.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				useTermsActionPerformed(evt);
			}
		});
		help.add(useTerms);

		about.setIcon(new javax.swing.ImageIcon("image/sobre.gif")); // NOI18N
		about.setMnemonic(I18n.getMessage("ix").charAt(0));
		about.setText(I18n.getMessage("iy")); 
		about.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				aboutActionPerformed(evt);
			}
		});
		help.add(about);

		menu.add(help);

		setJMenuBar(menu);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(stock, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(stockCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(calendar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(layout.createSequentialGroup()
																		.addGap(4, 4, 4)
																		.addComponent(dateStart, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(dateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(dayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(weekButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(monthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addGroup(layout.createSequentialGroup()
																				.addGap(822, 822, 822)
																				.addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(valueField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
																				.addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1199, Short.MAX_VALUE))
																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(riskGainButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(hFiboButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																										.addComponent(extFiboButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addComponent(textButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addComponent(evolutionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addComponent(rectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addComponent(verticalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																														.addComponent(parallelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addComponent(lineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addComponent(arrowButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
																														.addComponent(horizontalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
																														.addComponent(retFiboButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
																														.addGap(12, 12, 12))
																														.addGroup(layout.createSequentialGroup()
																																.addComponent(exportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addComponent(candleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addComponent(lineChartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addComponent(arithmeticButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addComponent(logButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addComponent(crossHairButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addComponent(infoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
																																.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addContainerGap()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(layout.createSequentialGroup()
																.addGap(1, 1, 1)
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(dateStart, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(dateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addComponent(dayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(weekButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(monthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
																				.addComponent(stockCombo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addComponent(stock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(calendar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																								.addComponent(exportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addComponent(candleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addComponent(lineChartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addComponent(arithmeticButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addComponent(logButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addComponent(crossHairButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addComponent(infoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
																								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																										.addComponent(valueField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
																										.addGroup(layout.createSequentialGroup()
																												.addGap(75, 75, 75)
																												.addComponent(arrowButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																												.addComponent(lineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																												.addComponent(parallelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																												.addComponent(horizontalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																												.addComponent(verticalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																												.addComponent(rectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																												.addComponent(evolutionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																												.addComponent(textButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																												.addComponent(extFiboButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																												.addComponent(retFiboButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																												.addComponent(hFiboButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																												.addComponent(riskGainButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
																												.addContainerGap())
		);

		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width-1288)/2, (screenSize.height-689)/2, 1288, 689);
		
		setVisible(true);
	}// </editor-fold>

	public void setStock(Stock[] ativo){

		stockCombo.removeAllItems();

		for (int i = 0; i < ativo.length; ++i) {  	    		
			stockCombo.addItem(ativo[i].getStockCode().trim());		

		} 	    	
	}
	
	public void listarTradesSimulacao(Configuration conf){

		int qttradesucesso = 0;
		float percacerto = 0;
		float vlCarteiraTeorica = 100F;		

		Trade[] trades = conf.getTrades().toArray(new Trade[0]);

		setSimulationReport(I18n.getMessage("ge"), true);

		sistema.mostrarMensagem(true, conf.toString());

		for (int i = 0; i < trades.length; i++){

			String mensagem = I18n.getMessage("gm")+": ";

			if(trades[i].getDtBuy() != null){
				mensagem += simpleDateformat.format(trades[i].getDtBuy());

				setaCompraVenda(true, trades[i].getIndexBuy());
			}

			if(trades[i].getVlBuy() > 0){
				mensagem +=" "+I18n.getMessage("gf")+" " + String.format("%05.2f",trades[i].getVlBuy())+"    "+I18n.getMessage("gg")+": ";
			}							

			if(trades[i].getDtSale() != null){
				mensagem += simpleDateformat.format(trades[i].getDtSale());

				setaCompraVenda(false,  trades[i].getIndexSale());
			}

			if(trades[i].getVlSale() > 0){
				mensagem +=" "+I18n.getMessage("gh")+" " + String.format("%05.2f",trades[i].getVlSale());
			}							

			mensagem +="    "+I18n.getMessage("gi")+": " + String.format("%05.2f", trades[i].getPcProfit() * 100)+"%";

			vlCarteiraTeorica += vlCarteiraTeorica * trades[i].getPcProfit();

			mensagem += "    "+I18n.getMessage("gj")+": R$ " + String.format("%05.2f",vlCarteiraTeorica);

			if(trades[i].getPcProfit() > 0){
				qttradesucesso++;
			}

			percacerto += trades[i].getPcProfit();

			sistema.mostrarMensagem(true, mensagem);
		}

		sistema.mostrarMensagem(true, I18n.getMessage("gk") + ": "+String.format("%05.2f", (new Double(qttradesucesso).doubleValue() / trades.length * 100D))+"%  " 
				+ qttradesucesso + "/" + trades.length);
		sistema.mostrarMensagem(true,I18n.getMessage("gl") + ": " + String.format("%05.2f", percacerto * 100)+"%");

		conf.setPcSucessTrade(new Float(qttradesucesso).floatValue() / trades.length);
		conf.setPercAccuracy(percacerto);	

	} 

	public void setSimulationReport(String mensagem,boolean reset){

		if(reset){
			reportText.setText(mensagem);
		}
		else{
			reportText.setText(reportText.getText() +"\n"+mensagem);
		}	    	
	}

	public void setGrafico(boolean isNewGraphic){

		if(grafico != null)
			chart.remove(grafico);

		if(isNewGraphic){
			try {
				grafico = new Grafico(this,this.historico, chart.getWidth(), chart.getHeight(), scaleChart,candleChart);
			} catch (PeriodException e) {
				e.printStackTrace();
			}
		}
		else{    		
			try {
				grafico.reconfiguration(scaleChart,candleChart);
			} catch (PeriodException e) {
				e.printStackTrace();
			}    		
		}

		grafico.setCrossHair(isCrossHair);
		grafico.setInfoScreen(isDataScreen);

		for(int i = 1; i<=5;i++){
			grafico.removeMM(i);
		}

		grafico.removeIFR();
		grafico.removeMACD();
		grafico.removeEstocastico();
		grafico.removeOBV();

		if(conf.getQtSMA1() > 0){
			grafico.setMM(conf.getQtSMA1(), 1,conf.getCatSMA1());
		}

		if(conf.getQtSMA2() > 0){
			grafico.setMM(conf.getQtSMA2(), 2,conf.getCatSMA2());
		}

		if(conf.getQtSMA3() > 0){
			grafico.setMM(conf.getQtSMA3(), 3,conf.getCatSMA3());
		}

		if(conf.getQtSMA4() > 0){
			grafico.setMM(conf.getQtSMA4(), 4,conf.getCatSMA4());
		}

		if(conf.getQtSMA5() > 0){
			grafico.setMM(conf.getQtSMA5(), 5,conf.getCatSMA5());
		}

		if(conf.getQtBB() > 0){
			grafico.setBollingerBands(conf.getQtBB(), conf.getDvBB());
		}

		if(conf.isVolume()){
			grafico.setVolume();

			if(conf.getQtSMAVol1() > 0)
				grafico.setMMVolume(conf.getQtSMAVol1(),1,conf.getCatSMAVol1());

			if(conf.getQtSMAVol2() > 0)
				grafico.setMMVolume(conf.getQtSMAVol2(),2,conf.getCatSMAVol2());

			if(conf.getQtSMAVol3() > 0)
				grafico.setMMVolume(conf.getQtSMAVol3(),3,conf.getCatSMAVol3());
		}

		if(conf.isObv()){
			grafico.setOBV();

			if(conf.getQtSMAOBV1() > 0)
				grafico.setMMOBV(conf.getQtSMAOBV1(),1,conf.getCatSMAOBV1());

			if(conf.getQtSMAOBV2() > 0)
				grafico.setMMOBV(conf.getQtSMAOBV2(),2,conf.getCatSMAOBV2());

			if(conf.getQtSMAOBV3() > 0)
				grafico.setMMOBV(conf.getQtSMAOBV3(),3,conf.getCatSMAOBV3());
		}    	

		if(conf.getQtRSI() > 0){
			grafico.setIFR(conf.getQtRSI(),conf.getOverboughtRsi(),conf.getOversoldRsi());

			if(conf.getQtSMARSI1() > 0)
				grafico.setMMIFR(conf.getQtSMARSI1(),conf.getQtRSI(), 3, conf.getCatSMARSI1());

			if(conf.getQtSMARSI2() > 0)
				grafico.setMMIFR(conf.getQtSMARSI2(),conf.getQtRSI(), 4, conf.getCatSMARSI2());

			if(conf.getQtSMARSI3() > 0)
				grafico.setMMIFR(conf.getQtSMARSI3(),conf.getQtRSI(), 5, conf.getCatSMARSI3());
		}

		if(conf.getQtShortMacd() > 0){
			grafico.setMACD(conf.getQtLongMacd(), conf.getQtShortMacd(), conf.getQtSignalMacd(),conf.isCrossMacd());
		}

		if(conf.getQtKStochastic() > 0){
			grafico.setEstocastico(conf.getQtKStochastic(), 
					conf.getQtDStochastic(), 
					conf.getQtLowStoch(), 
					conf.isLowStoch(),
					conf.isCrossStoch(),
					conf.getOverboughtStoch(),
					conf.getOversoldStoch());
		}  
		
		if(conf.getQtPeriodDmi() > 0){
			grafico.setDmi(conf.getQtPeriodDmi(), conf.getQtAvgDmi());
		}
		
		if(conf.getQtShortDidi() > 0){
			grafico.setMMNormal(conf.getQtShortDidi(),conf.getQtMediumDidi(),conf.getQtLongDidi());
		}

		grafico.setLocation(0,0);
		((javax.swing.plaf.basic.BasicInternalFrameUI) grafico.getUI()).setNorthPane(null);
		grafico.setBorder(null);

		chart.add(grafico);

		java.awt.EventQueue.invokeLater(new Runnable(){     
			public void run(){ 
				chart.revalidate();  
				chart.repaint();     
			}             
		});    	
	}

	public void setaCompraVenda(boolean compra,int candle){
		grafico.setaCompraVenda(compra, candle);
	}

	private void setAcaoData(boolean isNewGraphic) throws StockException{
				
		if(stockCombo.getSelectedItem() == null){
			throw new StockException("?");
		}

		conf.setStockCode(stockCombo.getSelectedItem().toString().toUpperCase());

		try {
			if(!dateStart.getText().equals("  /  /    ")){
				conf.setDtStart(simpleDateformat.parse(dateStart.getText()));
			}				

			if(dateEnd.getText().equals("  /  /    ")){
				conf.setDtEnd(Calendar.getInstance().getTime());
			}
			else{
				conf.setDtEnd(simpleDateformat.parse(dateEnd.getText()));
			}			

		} catch (ParseException e) {
			e.printStackTrace();
		}

		try{
			if(isNewGraphic)
			this.historico = sistema.getHistorico(conf);

			reportText.setText("");

			setGrafico(isNewGraphic);

		}
		catch(StockException e){

			JOptionPane.showMessageDialog(null, e.toString(), conf.getStockCode().trim(),0);

			logger.error(e.toString());
		} catch (PeriodException e) {
			e.printStackTrace();
		}		
	}

	public void paint(Graphics g){		
		super.paint(g); 

		if(grafico != null)
			grafico.setSize(chart.getWidth(), chart.getHeight());
	}

	public void setInfoField(String date, String value){
		dateField.setText(date);
		valueField.setText(value);		
	}

	private void openActionPerformed(java.awt.event.ActionEvent evt) {                                     
		
		JFileChooser fileChooser = new JFileChooser();  
		FileNameExtensionFilter filter = new FileNameExtensionFilter(I18n.getMessage("fi"), "stk");  
		fileChooser.setFileFilter(filter);  
		int option = fileChooser.showOpenDialog(null);  
		if (option == JFileChooser.APPROVE_OPTION) {  
			File file = fileChooser.getSelectedFile();  
			// abrir  
			try {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois;
				ois = new ObjectInputStream(fis);
				Configuration config = (Configuration) ois.readObject();			
				ois.close();

				this.conf = config;

				if(conf.getQtSMA1() > 0){
					qtMM1.setText(conf.getQtSMA1()+"");
					logger.debug(conf.getCatSMA1());
					tpMM1.setSelectedItem(conf.getCatSMA1() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
				}
				else{
					qtMM1.setText("");
					tpMM1.setSelectedItem(I18n.getMessage("ej"));
				}

				if(conf.getQtSMA2() > 0){
					qtMM2.setText(conf.getQtSMA2()+"");
					tpMM2.setSelectedItem(conf.getCatSMA2() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
				}
				else{
					qtMM2.setText("");
					tpMM2.setSelectedItem(I18n.getMessage("ej"));
				}

				if(conf.getQtSMA3() > 0){
					qtMM3.setText(conf.getQtSMA3()+"");
					tpMM3.setSelectedItem(conf.getCatSMA3() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
				}
				else{
					qtMM3.setText("");
					tpMM3.setSelectedItem(I18n.getMessage("ej"));
				}

				if(conf.getQtSMA4() > 0){
					qtMM4.setText(conf.getQtSMA4()+"");
					tpMM4.setSelectedItem(conf.getCatSMA4() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
				}
				else{
					qtMM4.setText("");
					tpMM4.setSelectedItem(I18n.getMessage("ej"));
				}

				if(conf.getQtSMA5() > 0){
					qtMM5.setText(conf.getQtSMA5()+"");
					tpMM5.setSelectedItem(conf.getCatSMA5() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
				}
				else{
					qtMM5.setText("");
					tpMM5.setSelectedItem(I18n.getMessage("ej"));
				}

				if(conf.getQtBB() > 0){
					qtBB.setText(conf.getQtBB()+"");
					dsvBB.setText(conf.getDvBB()+"");
				}
				else{
					qtBB.setText("");
					dsvBB.setText("");
				}

				if(conf.isVolume()){
					isVolSelect.setSelected(true);
					qtMMVol1.setEditable(true);
					tpMMVol1.setEnabled(true);
					qtMMVol2.setEditable(true);
					tpMMVol2.setEnabled(true);
					qtMMVol3.setEditable(true);
					tpMMVol3.setEnabled(true);

					if(conf.getQtSMAVol1() > 0){
						qtMMVol1.setText(conf.getQtSMAVol1()+"");
						tpMMVol1.setSelectedItem(conf.getCatSMAVol1() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
					}
					else{
						qtMMVol1.setText("");
						tpMMVol1.setSelectedItem(I18n.getMessage("ej"));
					}

					if(conf.getQtSMAVol2() > 0){
						qtMMVol2.setText(conf.getQtSMAVol2()+"");
						tpMMVol2.setSelectedItem(conf.getCatSMAVol2() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
					}
					else{
						qtMMVol2.setText("");
						tpMMVol2.setSelectedItem(I18n.getMessage("ej"));
					}

					if(conf.getQtSMAVol3() > 0){
						qtMMVol3.setText(conf.getQtSMAVol3()+"");
						tpMMVol3.setSelectedItem(conf.getCatSMAVol3() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
					}
					else{
						qtMMVol3.setText("");
						tpMMVol3.setSelectedItem(I18n.getMessage("ej"));
					}
				}
				else{
					isVolSelect.setSelected(false);
					qtMMVol1.setEditable(false);
					tpMMVol1.setEnabled(false);
					qtMMVol2.setEditable(false);
					tpMMVol2.setEnabled(false);
					qtMMVol3.setEditable(false);
					tpMMVol3.setEnabled(false);
					
					qtMMVol1.setText("");
					tpMMVol1.setSelectedItem(I18n.getMessage("ej"));
					qtMMVol2.setText("");
					tpMMVol2.setSelectedItem(I18n.getMessage("ej"));
					qtMMVol3.setText("");
					tpMMVol3.setSelectedItem(I18n.getMessage("ej"));
				}

				if(conf.isObv()){
					isOBVSelect.setSelected(true);
					qtMMObv1.setEditable(true);
					qtMMObv2.setEditable(true);
					qtMMObv3.setEditable(true);
					tpMMObv1.setEnabled(true);
					tpMMObv2.setEnabled(true);
					tpMMObv3.setEnabled(true);

					if(conf.getQtSMAOBV1() > 0){
						qtMMObv1.setText(conf.getQtSMAOBV1()+"");
						tpMMObv1.setSelectedItem(conf.getCatSMAOBV1() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
					}
					else{
						qtMMObv1.setText("");
						tpMMObv1.setSelectedItem(I18n.getMessage("ej"));
					}

					if(conf.getQtSMAOBV2() > 0){
						qtMMObv2.setText(conf.getQtSMAOBV2()+"");
						tpMMObv2.setSelectedItem(conf.getCatSMAOBV2() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
					}
					else{
						qtMMObv2.setText("");
						tpMMObv2.setSelectedItem(I18n.getMessage("ej"));
					}

					if(conf.getQtSMAOBV3() > 0){
						qtMMObv3.setText(conf.getQtSMAOBV3()+"");
						tpMMObv3.setSelectedItem(conf.getCatSMAOBV3() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
					} 
					else{
						qtMMObv3.setText("");
						tpMMObv3.setSelectedItem(I18n.getMessage("ej"));
					}   			
				}  
				else{
					isOBVSelect.setSelected(false);
					qtMMObv1.setEditable(false);
					qtMMObv2.setEditable(false);
					qtMMObv3.setEditable(false);
					tpMMObv1.setEnabled(false);
					tpMMObv2.setEnabled(false);
					tpMMObv3.setEnabled(false);
					
					qtMMObv1.setText("");
					qtMMObv2.setText("");
					qtMMObv3.setText("");
					tpMMObv1.setSelectedItem(I18n.getMessage("ej"));
					tpMMObv2.setSelectedItem(I18n.getMessage("ej"));
					tpMMObv3.setSelectedItem(I18n.getMessage("ej"));
				}  	

				if(conf.getQtRSI() > 0){
					isIFRSelect.setSelected(true);
					qtIfr.setEditable(true);
					scIfr.setEditable(true);
					svIfr.setEditable(true);
					qtMMIfr1.setEditable(true);
					tpMMIfr1.setEnabled(true);
					qtMMIfr2.setEditable(true);
					tpMMIfr2.setEnabled(true);
					qtMMIfr3.setEditable(true);
					tpMMIfr3.setEnabled(true);

					qtIfr.setText(conf.getQtRSI()+"");

					if(conf.getOverboughtRsi() > 0)
						scIfr.setText(conf.getOverboughtRsi()+"");

					if(conf.getOversoldRsi() > 0)
						svIfr.setText(conf.getOversoldRsi()+"");

					if(conf.getQtSMARSI1() > 0){
						qtMMIfr1.setText(conf.getQtSMARSI1()+"");
						tpMMIfr1.setSelectedItem(conf.getCatSMARSI1() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
					}
					else{
						qtMMIfr1.setText("");
						tpMMIfr1.setSelectedItem(I18n.getMessage("ej"));
					}
					
					if(conf.getQtSMARSI2() > 0){
						qtMMIfr2.setText(conf.getQtSMARSI2()+"");
						tpMMIfr2.setSelectedItem(conf.getCatSMARSI2() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
					}
					else{
						qtMMIfr2.setText("");
						tpMMIfr2.setSelectedItem(I18n.getMessage("ej"));
					}

					if(conf.getQtSMARSI3() > 0){
						qtMMIfr3.setText(conf.getQtSMARSI3()+"");
						tpMMIfr3.setSelectedItem(conf.getCatSMARSI3() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
					}
					else{
						qtMMIfr3.setText("");
						tpMMIfr3.setSelectedItem(I18n.getMessage("ej"));
					}

				}
				else{
					isIFRSelect.setSelected(false);
					qtIfr.setEditable(false);
					scIfr.setEditable(false);
					svIfr.setEditable(false);
					qtMMIfr1.setEditable(false);
					tpMMIfr1.setEnabled(false);
					qtMMIfr2.setEditable(false);
					tpMMIfr2.setEnabled(false);
					qtMMIfr3.setEditable(false);
					tpMMIfr3.setEnabled(false);
					
					qtIfr.setText("");
					scIfr.setText("");
					svIfr.setText("");
					qtMMIfr1.setText("");
					tpMMIfr1.setSelectedItem(I18n.getMessage("ej"));
					qtMMIfr2.setText("");
					tpMMIfr2.setSelectedItem(I18n.getMessage("ej"));
					qtMMIfr3.setText("");
					tpMMIfr3.setSelectedItem(I18n.getMessage("ej"));
					
				}

				if(conf.getQtShortMacd() > 0){
					isMACDSelect.setSelected(true);
					qtMacdS.setEditable(true);
					qtMacdC.setEditable(true);
					qtMacdL.setEditable(true);
					isMacdCross.setEnabled(true);

					qtMacdS.setText(conf.getQtSignalMacd()+"");
					qtMacdC.setText(conf.getQtShortMacd()+"");
					qtMacdL.setText(conf.getQtLongMacd()+"");
					isMacdCross.setSelected(conf.isCrossMacd());
				}
				else{
					isMACDSelect.setSelected(false);
					qtMacdS.setEditable(false);
					qtMacdC.setEditable(false);
					qtMacdL.setEditable(false);
					isMacdCross.setEnabled(false);
					isMacdCross.setSelected(false);
					qtMacdS.setText("");
					qtMacdC.setText("");
					qtMacdL.setText("");
				}

				if(conf.getQtKStochastic() > 0){
					isEstSelect.setSelected(true);
					qtK.setEditable(true);
					qtD.setEditable(true);
					scEst.setEditable(true);
					svEst.setEditable(true);
					isEstLow.setEnabled(true);
					qtEstLow.setEditable(true);
					isCrossEst.setEnabled(true);

					qtK.setText(conf.getQtKStochastic()+"");
					qtD.setText(conf.getQtDStochastic()+"");
					scEst.setText(conf.getOverboughtStoch()+"");
					svEst.setText(conf.getOversoldStoch()+"");

					isEstLow.setSelected(conf.isLowStoch());
					qtEstLow.setText(conf.getQtLowStoch()+"");
					isCrossEst.setSelected(conf.isCrossStoch());
				} 
				else{
					isEstSelect.setSelected(false);
					qtK.setEditable(false);
					qtD.setEditable(false);
					scEst.setEditable(false);
					svEst.setEditable(false);
					isEstLow.setEnabled(false);
					qtEstLow.setEditable(false);
					isCrossEst.setEnabled(false);

					qtK.setText("");
					qtD.setText("");
					scEst.setText("");
					svEst.setText("");

					isEstLow.setSelected(false);
					qtEstLow.setText("");
					isCrossEst.setSelected(false);
				}
				
				if(conf.getQtPeriodDmi() > 0){
					
					isDmiSelect.setSelected(true);
					qtDmiPeriod.setEditable(true);
					qtDmiAverage.setEditable(true);
					
					qtDmiPeriod.setText(conf.getQtPeriodDmi()+"");
					qtDmiAverage.setText(conf.getQtAvgDmi()+"");
				}
				else{
					isDmiSelect.setSelected(false);
					qtDmiPeriod.setEditable(false);
					qtDmiAverage.setEditable(false);
					
					qtDmiPeriod.setText("");
					qtDmiAverage.setText("");
				}
				
				if(conf.getQtShortDidi() > 0){
					
					isMMNSelect.setSelected(true);					
					qtShortDidi.setEditable(true);
					qtMediumDidi.setEditable(true);
					qtLongDidi.setEditable(true);
					
					qtShortDidi.setText(conf.getQtShortDidi()+"");
					qtMediumDidi.setText(conf.getQtMediumDidi()+"");
					qtLongDidi.setText(conf.getQtLongDidi()+"");
				}
				else{
					isMMNSelect.setSelected(false);					
					qtShortDidi.setEditable(false);
					qtMediumDidi.setEditable(false);
					qtLongDidi.setEditable(false);
					
					qtShortDidi.setText("");
					qtMediumDidi.setText("");
					qtLongDidi.setText("");
				}

				switch(conf.getPeriod()){				
				case 'D':
					dayButton.setEnabled(false);
					daily.setEnabled(false);
					weekButton.setEnabled(true);
					weekly.setEnabled(true);
					monthButton.setEnabled(true);
					monthly.setEnabled(true);

					break;
				case 'W':
					dayButton.setEnabled(true);
					daily.setEnabled(true);
					weekButton.setEnabled(false);
					weekly.setEnabled(false);
					monthButton.setEnabled(true);
					monthly.setEnabled(true);
					break;
				case 'M':
					dayButton.setEnabled(true);
					daily.setEnabled(true);
					weekButton.setEnabled(true);
					weekly.setEnabled(true);
					monthButton.setEnabled(false);
					monthly.setEnabled(false);
					break;
				}
				dateStart.setText(simpleDateformat.format(conf.getDtStart()));
				dateEnd.setText(simpleDateformat.format(conf.getDtEnd()));
				stockCombo.setSelectedItem(conf.getStockCode());
				
				this.grafico.setAnnotations(config.getAnnotations());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	}                                                                        

	private void saveActionPerformed(java.awt.event.ActionEvent evt) {                                     

		JFileChooser fileChooser = new JFileChooser();  
		FileNameExtensionFilter filter = new FileNameExtensionFilter(I18n.getMessage("fi"), "stk");  
		fileChooser.setFileFilter(filter);
		int option = fileChooser.showSaveDialog(this);  
		if (option == JFileChooser.APPROVE_OPTION) { 

			String fileName = fileChooser.getSelectedFile().getAbsoluteFile().toString();

			if(!fileName.endsWith(".stk")){
				fileName += ".stk";
			}

			// salvar 
			try{
				logger.debug(fileName);
				FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				
				this.conf.setAnnotations(this.grafico.getAnnotations());
				
				oos.writeObject(this.conf);
				oos.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}  
	}

	private void selStockActionPerformed(java.awt.event.ActionEvent evt) { 

		new StockSelect(this,0);

	}    
	
	private void confMenuActionPerformed(java.awt.event.ActionEvent evt) { 

		new PropertyGui();

	}  

	private void fiboExtensionActionPerformed(java.awt.event.ActionEvent evt) {                                              

		extFiboButton.setEnabled(false);
		fiboExtension.setEnabled(false);
		grafico.addExtFibo();
	} 

	public void setExtFiboEnabled(){
		extFiboButton.setEnabled(true);
		fiboExtension.setEnabled(true);
	}

	private void arrowActionPerformed(java.awt.event.ActionEvent evt) { 
		arrowButton.setEnabled(false);
		arrow.setEnabled(false);
		grafico.addArrow();
	}  

	public void setArrowButtonEnabled(){
		arrowButton.setEnabled(true);
		arrow.setEnabled(true);
	}

	private void lineActionPerformed(java.awt.event.ActionEvent evt) {    
		lineButton.setEnabled(false);
		line.setEnabled(false);
		grafico.addLine();
	} 

	public void setLineButtonEnabled(){
		lineButton.setEnabled(true);
		line.setEnabled(true);
	}

	private void parallelLinesActionPerformed(java.awt.event.ActionEvent evt) {                                              
		parallelButton.setEnabled(false);
		parallelLines.setEnabled(false);
		grafico.addParallelLines();
	} 

	public void setParallelLinesButtonEnabled(){
		parallelButton.setEnabled(true);
		parallelLines.setEnabled(true);
	}

	private void riskGainActionPerformed(java.awt.event.ActionEvent evt) { 
		riskGainButton.setEnabled(false);
		riskGain.setEnabled(false);
		grafico.addRiskGain();
	} 

	public void setRiskGainEnabled(){
		riskGainButton.setEnabled(true);
		riskGain.setEnabled(true);
	}

	private void evolutionStraightActionPerformed(java.awt.event.ActionEvent evt) {                                                  
		evolutionButton.setEnabled(false);
		evolutionStraight.setEnabled(false);
		grafico.addEvolutionLine();
	} 

	public void setEvolutionButtonEnabled(){
		evolutionButton.setEnabled(true);
		evolutionStraight.setEnabled(true);
	}

	private void horizontalStraightActionPerformed(java.awt.event.ActionEvent evt) {                                                   
		horizontalButton.setEnabled(false);
		horizontalStraight.setEnabled(false);
		grafico.addHorizontalLine();
	} 

	public void setHorizontalButtonEnabled(){
		horizontalButton.setEnabled(true);
		horizontalStraight.setEnabled(true);
	}

	private void verticalStraightActionPerformed(java.awt.event.ActionEvent evt) {                                                 
		verticalButton.setEnabled(false);
		verticalStraight.setEnabled(false);
		grafico.addVerticalLine();
	}  

	public void setVerticalButtonEnabled(){
		verticalButton.setEnabled(true);
		verticalStraight.setEnabled(true);
	}

	private void rectangleActionPerformed(java.awt.event.ActionEvent evt) {                                          
		rectButton.setEnabled(false);
		rectangle.setEnabled(false);
		grafico.addRectangle();
	}    

	public void setRectangleEnabled(){
		rectButton.setEnabled(true);
		rectangle.setEnabled(true);
	}

	private void vFiboRetractionActionPerformed(java.awt.event.ActionEvent evt) {                                                
		retFiboButton.setEnabled(false);
		vFiboRetraction.setEnabled(false);
		grafico.addVFiboRetraction();
	} 

	public void setVFiboRetractionEnabled(){
		retFiboButton.setEnabled(true);
		vFiboRetraction.setEnabled(true);
	}

	private void hFiboRetractionActionPerformed(java.awt.event.ActionEvent evt) {                                                
		hFiboButton.setEnabled(false);
		hFiboRetraction.setEnabled(false);
		grafico.addHFiboRetraction();
	} 

	public void setHFiboRetractionEnabled(){
		hFiboButton.setEnabled(true);
		hFiboRetraction.setEnabled(true);    	
	}

	private void textActionPerformed(java.awt.event.ActionEvent evt) { 
		textButton.setEnabled(false);
		text.setEnabled(false);
		grafico.addText();
	}

	public void setTextButtonEnabled(){
		textButton.setEnabled(true);
		text.setEnabled(true);
	}

	private void arithmeticActionPerformed(java.awt.event.ActionEvent evt) {                                           
		scaleChart = true;
		setGrafico(false);
		arithmeticButton.setEnabled(false);
		arithmetic.setEnabled(false);
		logButton.setEnabled(true);
		logarithmic.setEnabled(true);
	}                                          

	private void logarithmicActionPerformed(java.awt.event.ActionEvent evt) {                                            
		scaleChart = false;
		setGrafico(false);                
		arithmeticButton.setEnabled(true);
		arithmetic.setEnabled(true);
		logButton.setEnabled(false);
		logarithmic.setEnabled(false);
	}                                           

	private void crossHairActionPerformed(java.awt.event.ActionEvent evt) {                                          
		isCrossHair = !isCrossHair;
		grafico.setCrossHair(isCrossHair);    	
	}                                         

	private void candleActionPerformed(java.awt.event.ActionEvent evt) {                                       
		candleChart = true;
		setGrafico(false);
		candleButton.setEnabled(false);
		candle.setEnabled(false);
		lineChartButton.setEnabled(true);
		linechart.setEnabled(true);
	}                                      

	private void linechartActionPerformed(java.awt.event.ActionEvent evt) {                                          
		candleChart = false;
		setGrafico(false);
		candleButton.setEnabled(true);
		candle.setEnabled(true);
		lineChartButton.setEnabled(false);
		linechart.setEnabled(false);
	}                                         

	private void selectSimulateActionPerformed(java.awt.event.ActionEvent evt) {
		
		new StockSelect(this,1);
	} 
	
	public void setListSimulation(Stock[] list){
		
		selectSimulate.setEnabled(false);
		simulate.setEnabled(true);
		
		confSimulacao = new ArrayList<Configuration>();
		
		for(int i = 0; i< list.length;i++){
			
			Configuration c = new Configuration(list[i].getStockCode());
			
			c.setDtStart(this.conf.getDtStart());
			c.setDtEnd(this.conf.getDtEnd());
			c.setPeriod(PERIOD);
			
			confSimulacao.add(c);			
		}
		
		for(int i = 3; i< simulateMenu.getMenuComponentCount();i++){
			
			simulateMenu.remove(simulateMenu.getMenuComponent(i));
				i--;	
		}		
	}

	private void simulateActionPerformed(java.awt.event.ActionEvent evt) {                                        

		selectSimulate.setEnabled(true);
		simulate.setEnabled(false);

		//simular		
		sistema.simulateConf(confSimulacao);

		//Adiciona ao menu
		for(int i = 0; i< confSimulacao.size();i++){

			javax.swing.JMenuItem acaoS = new javax.swing.JMenuItem();

			final int index = i;	
			
			acaoS.setText(confSimulacao.get(i).getStockCode());
			
			acaoS.addActionListener(new java.awt.event.ActionListener() {
				
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					try {
						Configuration conf = sistema.getAgenteSimulacao(index);
						
						if(conf.getQtSMA1() > 0){
							qtMM1.setText(conf.getQtSMA1()+"");
							tpMM1.setSelectedItem(conf.getCatSMA1() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
						}
						else{
							qtMM1.setText("");
							tpMM1.setSelectedItem(I18n.getMessage("ej"));
						}

						if(conf.getQtSMA2() > 0){
							qtMM2.setText(conf.getQtSMA2()+"");
							tpMM2.setSelectedItem(conf.getCatSMA2() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
						}
						else{
							qtMM2.setText("");
							tpMM2.setSelectedItem(I18n.getMessage("ej"));
						}

						if(conf.getQtSMA3() > 0){
							qtMM3.setText(conf.getQtSMA3()+"");
							tpMM3.setSelectedItem(conf.getCatSMA3() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
						}
						else{
							qtMM3.setText("");
							tpMM3.setSelectedItem(I18n.getMessage("ej"));
						}

						if(conf.getQtSMA4() > 0){
							qtMM4.setText(conf.getQtSMA4()+"");
							tpMM4.setSelectedItem(conf.getCatSMA4() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
						}
						else{
							qtMM4.setText("");
							tpMM4.setSelectedItem(I18n.getMessage("ej"));
						}

						if(conf.getQtSMA5() > 0){
							qtMM5.setText(conf.getQtSMA5()+"");
							tpMM5.setSelectedItem(conf.getCatSMA5() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
						}
						else{
							qtMM5.setText("");
							tpMM5.setSelectedItem(I18n.getMessage("ej"));
						}

						if(conf.getQtBB() > 0){
							qtBB.setText(conf.getQtBB()+"");
							dsvBB.setText(conf.getDvBB()+"");
						}
						else{
							qtBB.setText("");
							dsvBB.setText("");
						}

						if(conf.isVolume()){
							isVolSelect.setSelected(true);
							qtMMVol1.setEditable(true);
							tpMMVol1.setEnabled(true);
							qtMMVol2.setEditable(true);
							tpMMVol2.setEnabled(true);
							qtMMVol3.setEditable(true);
							tpMMVol3.setEnabled(true);

							if(conf.getQtSMAVol1() > 0){
								qtMMVol1.setText(conf.getQtSMAVol1()+"");
								tpMMVol1.setSelectedItem(conf.getCatSMAVol1() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMVol1.setText("");
								tpMMVol1.setSelectedItem(I18n.getMessage("ej"));
							}

							if(conf.getQtSMAVol2() > 0){
								qtMMVol2.setText(conf.getQtSMAVol2()+"");
								tpMMVol2.setSelectedItem(conf.getCatSMAVol2() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMVol2.setText("");
								tpMMVol2.setSelectedItem(I18n.getMessage("ej"));
							}

							if(conf.getQtSMAVol3() > 0){
								qtMMVol3.setText(conf.getQtSMAVol3()+"");
								tpMMVol3.setSelectedItem(conf.getCatSMAVol3() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMVol3.setText("");
								tpMMVol3.setSelectedItem(I18n.getMessage("ej"));
							}
						}
						else{
							isVolSelect.setSelected(false);
							qtMMVol1.setEditable(false);
							tpMMVol1.setEnabled(false);
							qtMMVol2.setEditable(false);
							tpMMVol2.setEnabled(false);
							qtMMVol3.setEditable(false);
							tpMMVol3.setEnabled(false);

							qtMMVol1.setText("");
							tpMMVol1.setSelectedItem(I18n.getMessage("ej"));
							qtMMVol2.setText("");
							tpMMVol2.setSelectedItem(I18n.getMessage("ej"));
							qtMMVol3.setText("");
							tpMMVol3.setSelectedItem(I18n.getMessage("ej"));
						}

						if(conf.isObv()){
							isOBVSelect.setSelected(true);
							qtMMObv1.setEditable(true);
							qtMMObv2.setEditable(true);
							qtMMObv3.setEditable(true);
							tpMMObv1.setEnabled(true);
							tpMMObv2.setEnabled(true);
							tpMMObv3.setEnabled(true);

							if(conf.getQtSMAOBV1() > 0){
								qtMMObv1.setText(conf.getQtSMAOBV1()+"");
								tpMMObv1.setSelectedItem(conf.getCatSMAOBV1() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMObv1.setText("");
								tpMMObv1.setSelectedItem(I18n.getMessage("ej"));
							}

							if(conf.getQtSMAOBV2() > 0){
								qtMMObv2.setText(conf.getQtSMAOBV2()+"");
								tpMMObv2.setSelectedItem(conf.getCatSMAOBV2() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMObv2.setText("");
								tpMMObv2.setSelectedItem(I18n.getMessage("ej"));
							}

							if(conf.getQtSMAOBV3() > 0){
								qtMMObv3.setText(conf.getQtSMAOBV3()+"");
								tpMMObv3.setSelectedItem(conf.getCatSMAOBV3() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							} 
							else{
								qtMMObv3.setText("");
								tpMMObv3.setSelectedItem(I18n.getMessage("ej"));
							}   			
						}  
						else{
							isOBVSelect.setSelected(false);
							qtMMObv1.setEditable(false);
							qtMMObv2.setEditable(false);
							qtMMObv3.setEditable(false);
							tpMMObv1.setEnabled(false);
							tpMMObv2.setEnabled(false);
							tpMMObv3.setEnabled(false);

							qtMMObv1.setText("");
							qtMMObv2.setText("");
							qtMMObv3.setText("");
							tpMMObv1.setSelectedItem(I18n.getMessage("ej"));
							tpMMObv2.setSelectedItem(I18n.getMessage("ej"));
							tpMMObv3.setSelectedItem(I18n.getMessage("ej"));
						}  	

						if(conf.getQtRSI() > 0){
							isIFRSelect.setSelected(true);
							qtIfr.setEditable(true);
							scIfr.setEditable(true);
							svIfr.setEditable(true);
							qtMMIfr1.setEditable(true);
							tpMMIfr1.setEnabled(true);
							qtMMIfr2.setEditable(true);
							tpMMIfr2.setEnabled(true);
							qtMMIfr3.setEditable(true);
							tpMMIfr3.setEnabled(true);

							qtIfr.setText(conf.getQtRSI()+"");

							if(conf.getOverboughtRsi() > 0)
								scIfr.setText(conf.getOverboughtRsi()+"");

							if(conf.getOversoldRsi() > 0)
								svIfr.setText(conf.getOversoldRsi()+"");

							if(conf.getQtSMARSI1() > 0){
								qtMMIfr1.setText(conf.getQtSMARSI1()+"");
								tpMMIfr1.setSelectedItem(conf.getCatSMARSI1() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMIfr1.setText("");
								tpMMIfr1.setSelectedItem(I18n.getMessage("ej"));
							}

							if(conf.getQtSMARSI2() > 0){
								qtMMIfr2.setText(conf.getQtSMARSI2()+"");
								tpMMIfr2.setSelectedItem(conf.getCatSMARSI2() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMIfr2.setText("");
								tpMMIfr2.setSelectedItem(I18n.getMessage("ej"));
							}

							if(conf.getQtSMARSI3() > 0){
								qtMMIfr3.setText(conf.getQtSMARSI3()+"");
								tpMMIfr3.setSelectedItem(conf.getCatSMARSI3() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMIfr3.setText("");
								tpMMIfr3.setSelectedItem(I18n.getMessage("ej"));
							}

						}
						else{
							isIFRSelect.setSelected(false);
							qtIfr.setEditable(false);
							scIfr.setEditable(false);
							svIfr.setEditable(false);
							qtMMIfr1.setEditable(false);
							tpMMIfr1.setEnabled(false);
							qtMMIfr2.setEditable(false);
							tpMMIfr2.setEnabled(false);
							qtMMIfr3.setEditable(false);
							tpMMIfr3.setEnabled(false);

							qtIfr.setText("");
							scIfr.setText("");
							svIfr.setText("");
							qtMMIfr1.setText("");
							tpMMIfr1.setSelectedItem(I18n.getMessage("ej"));
							qtMMIfr2.setText("");
							tpMMIfr2.setSelectedItem(I18n.getMessage("ej"));
							qtMMIfr3.setText("");
							tpMMIfr3.setSelectedItem(I18n.getMessage("ej"));

						}

						if(conf.getQtShortMacd() > 0){
							isMACDSelect.setSelected(true);
							qtMacdS.setEditable(true);
							qtMacdC.setEditable(true);
							qtMacdL.setEditable(true);
							isMacdCross.setEnabled(true);

							qtMacdS.setText(conf.getQtSignalMacd()+"");
							qtMacdC.setText(conf.getQtShortMacd()+"");
							qtMacdL.setText(conf.getQtLongMacd()+"");
							isMacdCross.setSelected(conf.isCrossMacd());
						}
						else{
							isMACDSelect.setSelected(false);
							qtMacdS.setEditable(false);
							qtMacdC.setEditable(false);
							qtMacdL.setEditable(false);
							isMacdCross.setEnabled(false);
							isMacdCross.setSelected(false);
							qtMacdS.setText("");
							qtMacdC.setText("");
							qtMacdL.setText("");
						}

						if(conf.getQtKStochastic() > 0){
							isEstSelect.setSelected(true);
							qtK.setEditable(true);
							qtD.setEditable(true);
							scEst.setEditable(true);
							svEst.setEditable(true);
							isEstLow.setEnabled(true);
							qtEstLow.setEditable(true);
							isCrossEst.setEnabled(true);

							qtK.setText(conf.getQtKStochastic()+"");
							qtD.setText(conf.getQtDStochastic()+"");
							scEst.setText(conf.getOverboughtStoch()+"");
							svEst.setText(conf.getOversoldStoch()+"");

							isEstLow.setSelected(conf.isLowStoch());
							qtEstLow.setText(conf.getQtLowStoch()+"");
							isCrossEst.setSelected(conf.isCrossStoch());
						} 
						else{
							isEstSelect.setSelected(false);
							qtK.setEditable(false);
							qtD.setEditable(false);
							scEst.setEditable(false);
							svEst.setEditable(false);
							isEstLow.setEnabled(false);
							qtEstLow.setEditable(false);
							isCrossEst.setEnabled(false);

							qtK.setText("");
							qtD.setText("");
							scEst.setText("");
							svEst.setText("");

							isEstLow.setSelected(false);
							qtEstLow.setText("");
							isCrossEst.setSelected(false);
						}
						
						if(conf.getQtPeriodDmi() > 0){
							
							isDmiSelect.setSelected(true);
							qtDmiPeriod.setEditable(true);
							qtDmiAverage.setEditable(true);
							
							qtDmiPeriod.setText(conf.getQtPeriodDmi()+"");
							qtDmiAverage.setText(conf.getQtAvgDmi()+"");
						}
						else{
							isDmiSelect.setSelected(false);
							qtDmiPeriod.setEditable(false);
							qtDmiAverage.setEditable(false);
							
							qtDmiPeriod.setText("");
							qtDmiAverage.setText("");
						}
						
						if(conf.getQtShortDidi() > 0){
							
							isMMNSelect.setSelected(true);					
							qtShortDidi.setEditable(true);
							qtMediumDidi.setEditable(true);
							qtLongDidi.setEditable(true);
							
							qtShortDidi.setText(conf.getQtShortDidi()+"");
							qtMediumDidi.setText(conf.getQtMediumDidi()+"");
							qtLongDidi.setText(conf.getQtLongDidi()+"");
						}
						else{
							isMMNSelect.setSelected(false);					
							qtShortDidi.setEditable(false);
							qtMediumDidi.setEditable(false);
							qtLongDidi.setEditable(false);
							
							qtShortDidi.setText("");
							qtMediumDidi.setText("");
							qtLongDidi.setText("");
						}

						switch(conf.getPeriod()){				
						case 'D':
							dayButton.setEnabled(false);
							daily.setEnabled(false);
							weekButton.setEnabled(true);
							weekly.setEnabled(true);
							monthButton.setEnabled(true);
							monthly.setEnabled(true);

							break;
						case 'W':
							dayButton.setEnabled(true);
							daily.setEnabled(true);
							weekButton.setEnabled(false);
							weekly.setEnabled(false);
							monthButton.setEnabled(true);
							monthly.setEnabled(true);
							break;
						case 'M':
							dayButton.setEnabled(true);
							daily.setEnabled(true);
							weekButton.setEnabled(true);
							weekly.setEnabled(true);
							monthButton.setEnabled(false);
							monthly.setEnabled(false);
							break;
						}
						dateStart.setText(simpleDateformat.format(conf.getDtStart()));
						dateEnd.setText(simpleDateformat.format(conf.getDtEnd()));
						stockCombo.setSelectedItem(conf.getStockCode());
						
						qtMM1FocusLost(null);
						qtMM2FocusLost(null);
						qtMM3FocusLost(null);
						qtMM4FocusLost(null);
						qtMM5FocusLost(null);
						bollingerFocusLost(null);
						volumeActionPerformed(null);
						obvActionPerformed(null);
						isIFRSelectActionPerformed(null);
						estocasticoActionPerformed(null);
						dmiActionPerformed(null);
						didiActionPerformed(null);
						macdActionPerformed(null);
						
						listarTradesSimulacao(conf);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}					
			});

			simulateMenu.add(acaoS);
		}	
	}
	
	public void setListAcomp(Stock[] list){
		
		monitor.setEnabled(true);
		selectMonitor.setEnabled(false);
		confAcompanhamento = new ArrayList<Configuration>();
		
		for(int i = 0; i< list.length;i++){
			
			Configuration c = new Configuration(list[i].getStockCode());
			
			c.setDtStart(this.conf.getDtStart());
			c.setDtEnd(this.conf.getDtEnd());
			c.setPeriod(PERIOD);
			
			confAcompanhamento.add(c);			
		}
		
		for(int i = 4; i< monitorMenu.getMenuComponentCount();i++){
			
			monitorMenu.remove(monitorMenu.getMenuComponent(i));
			i--;	
		}		
	}

	private void selectMonitorActionPerformed(java.awt.event.ActionEvent evt) {                                              
		
		new StockSelect(this,2);
	}                                             

	private void monitorActionPerformed(java.awt.event.ActionEvent evt) {                                        
		
		stopMonitor.setEnabled(true);
		monitor.setEnabled(false);

		//monitorar		
		sistema.acompanharAtivos(confAcompanhamento);

		//Adiciona ao menu
		for(int i = 0; i< confAcompanhamento.size();i++){

			javax.swing.JMenuItem acaoAcomp = new javax.swing.JMenuItem();

			final int index = i;	
			
			acaoAcomp.setText(confAcompanhamento.get(i).getStockCode());
			
			acaoAcomp.addActionListener(new java.awt.event.ActionListener() {
				
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					try {
						Configuration conf = sistema.getAgenteAcompanhamento(index);
						
						if(conf.getQtSMA1() > 0){
							qtMM1.setText(conf.getQtSMA1()+"");
							tpMM1.setSelectedItem(conf.getCatSMA1() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
						}
						else{
							qtMM1.setText("");
							tpMM1.setSelectedItem(I18n.getMessage("ej"));
						}

						if(conf.getQtSMA2() > 0){
							qtMM2.setText(conf.getQtSMA2()+"");
							tpMM2.setSelectedItem(conf.getCatSMA2() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
						}
						else{
							qtMM2.setText("");
							tpMM2.setSelectedItem(I18n.getMessage("ej"));
						}

						if(conf.getQtSMA3() > 0){
							qtMM3.setText(conf.getQtSMA3()+"");
							tpMM3.setSelectedItem(conf.getCatSMA3() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
						}
						else{
							qtMM3.setText("");
							tpMM3.setSelectedItem(I18n.getMessage("ej"));
						}

						if(conf.getQtSMA4() > 0){
							qtMM4.setText(conf.getQtSMA4()+"");
							tpMM4.setSelectedItem(conf.getCatSMA4() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
						}
						else{
							qtMM4.setText("");
							tpMM4.setSelectedItem(I18n.getMessage("ej"));
						}

						if(conf.getQtSMA5() > 0){
							qtMM5.setText(conf.getQtSMA5()+"");
							tpMM5.setSelectedItem(conf.getCatSMA5() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
						}
						else{
							qtMM5.setText("");
							tpMM5.setSelectedItem(I18n.getMessage("ej"));
						}

						if(conf.getQtBB() > 0){
							qtBB.setText(conf.getQtBB()+"");
							dsvBB.setText(conf.getDvBB()+"");
						}
						else{
							qtBB.setText("");
							dsvBB.setText("");
						}

						if(conf.isVolume()){
							isVolSelect.setSelected(true);
							qtMMVol1.setEditable(true);
							tpMMVol1.setEnabled(true);
							qtMMVol2.setEditable(true);
							tpMMVol2.setEnabled(true);
							qtMMVol3.setEditable(true);
							tpMMVol3.setEnabled(true);

							if(conf.getQtSMAVol1() > 0){
								qtMMVol1.setText(conf.getQtSMAVol1()+"");
								tpMMVol1.setSelectedItem(conf.getCatSMAVol1() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMVol1.setText("");
								tpMMVol1.setSelectedItem(I18n.getMessage("ej"));
							}

							if(conf.getQtSMAVol2() > 0){
								qtMMVol2.setText(conf.getQtSMAVol2()+"");
								tpMMVol2.setSelectedItem(conf.getCatSMAVol2() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMVol2.setText("");
								tpMMVol2.setSelectedItem(I18n.getMessage("ej"));
							}

							if(conf.getQtSMAVol3() > 0){
								qtMMVol3.setText(conf.getQtSMAVol3()+"");
								tpMMVol3.setSelectedItem(conf.getCatSMAVol3() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMVol3.setText("");
								tpMMVol3.setSelectedItem(I18n.getMessage("ej"));
							}
						}
						else{
							isVolSelect.setSelected(false);
							qtMMVol1.setEditable(false);
							tpMMVol1.setEnabled(false);
							qtMMVol2.setEditable(false);
							tpMMVol2.setEnabled(false);
							qtMMVol3.setEditable(false);
							tpMMVol3.setEnabled(false);

							qtMMVol1.setText("");
							tpMMVol1.setSelectedItem(I18n.getMessage("ej"));
							qtMMVol2.setText("");
							tpMMVol2.setSelectedItem(I18n.getMessage("ej"));
							qtMMVol3.setText("");
							tpMMVol3.setSelectedItem(I18n.getMessage("ej"));
						}

						if(conf.isObv()){
							isOBVSelect.setSelected(true);
							qtMMObv1.setEditable(true);
							qtMMObv2.setEditable(true);
							qtMMObv3.setEditable(true);
							tpMMObv1.setEnabled(true);
							tpMMObv2.setEnabled(true);
							tpMMObv3.setEnabled(true);

							if(conf.getQtSMAOBV1() > 0){
								qtMMObv1.setText(conf.getQtSMAOBV1()+"");
								tpMMObv1.setSelectedItem(conf.getCatSMAOBV1() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMObv1.setText("");
								tpMMObv1.setSelectedItem(I18n.getMessage("ej"));
							}

							if(conf.getQtSMAOBV2() > 0){
								qtMMObv2.setText(conf.getQtSMAOBV2()+"");
								tpMMObv2.setSelectedItem(conf.getCatSMAOBV2() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMObv2.setText("");
								tpMMObv2.setSelectedItem(I18n.getMessage("ej"));
							}

							if(conf.getQtSMAOBV3() > 0){
								qtMMObv3.setText(conf.getQtSMAOBV3()+"");
								tpMMObv3.setSelectedItem(conf.getCatSMAOBV3() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							} 
							else{
								qtMMObv3.setText("");
								tpMMObv3.setSelectedItem(I18n.getMessage("ej"));
							}   			
						}  
						else{
							isOBVSelect.setSelected(false);
							qtMMObv1.setEditable(false);
							qtMMObv2.setEditable(false);
							qtMMObv3.setEditable(false);
							tpMMObv1.setEnabled(false);
							tpMMObv2.setEnabled(false);
							tpMMObv3.setEnabled(false);

							qtMMObv1.setText("");
							qtMMObv2.setText("");
							qtMMObv3.setText("");
							tpMMObv1.setSelectedItem(I18n.getMessage("ej"));
							tpMMObv2.setSelectedItem(I18n.getMessage("ej"));
							tpMMObv3.setSelectedItem(I18n.getMessage("ej"));
						}  	

						if(conf.getQtRSI() > 0){
							isIFRSelect.setSelected(true);
							qtIfr.setEditable(true);
							scIfr.setEditable(true);
							svIfr.setEditable(true);
							qtMMIfr1.setEditable(true);
							tpMMIfr1.setEnabled(true);
							qtMMIfr2.setEditable(true);
							tpMMIfr2.setEnabled(true);
							qtMMIfr3.setEditable(true);
							tpMMIfr3.setEnabled(true);

							qtIfr.setText(conf.getQtRSI()+"");

							if(conf.getOverboughtRsi() > 0)
								scIfr.setText(conf.getOverboughtRsi()+"");

							if(conf.getOversoldRsi() > 0)
								svIfr.setText(conf.getOversoldRsi()+"");

							if(conf.getQtSMARSI1() > 0){
								qtMMIfr1.setText(conf.getQtSMARSI1()+"");
								tpMMIfr1.setSelectedItem(conf.getCatSMARSI1() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMIfr1.setText("");
								tpMMIfr1.setSelectedItem(I18n.getMessage("ej"));
							}

							if(conf.getQtSMARSI2() > 0){
								qtMMIfr2.setText(conf.getQtSMARSI2()+"");
								tpMMIfr2.setSelectedItem(conf.getCatSMARSI2() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMIfr2.setText("");
								tpMMIfr2.setSelectedItem(I18n.getMessage("ej"));
							}

							if(conf.getQtSMARSI3() > 0){
								qtMMIfr3.setText(conf.getQtSMARSI3()+"");
								tpMMIfr3.setSelectedItem(conf.getCatSMARSI3() == 'a'?I18n.getMessage("ej"):I18n.getMessage("ek"));
							}
							else{
								qtMMIfr3.setText("");
								tpMMIfr3.setSelectedItem(I18n.getMessage("ej"));
							}

						}
						else{
							isIFRSelect.setSelected(false);
							qtIfr.setEditable(false);
							scIfr.setEditable(false);
							svIfr.setEditable(false);
							qtMMIfr1.setEditable(false);
							tpMMIfr1.setEnabled(false);
							qtMMIfr2.setEditable(false);
							tpMMIfr2.setEnabled(false);
							qtMMIfr3.setEditable(false);
							tpMMIfr3.setEnabled(false);

							qtIfr.setText("");
							scIfr.setText("");
							svIfr.setText("");
							qtMMIfr1.setText("");
							tpMMIfr1.setSelectedItem(I18n.getMessage("ej"));
							qtMMIfr2.setText("");
							tpMMIfr2.setSelectedItem(I18n.getMessage("ej"));
							qtMMIfr3.setText("");
							tpMMIfr3.setSelectedItem(I18n.getMessage("ej"));

						}

						if(conf.getQtShortMacd() > 0){
							isMACDSelect.setSelected(true);
							qtMacdS.setEditable(true);
							qtMacdC.setEditable(true);
							qtMacdL.setEditable(true);
							isMacdCross.setEnabled(true);

							qtMacdS.setText(conf.getQtSignalMacd()+"");
							qtMacdC.setText(conf.getQtShortMacd()+"");
							qtMacdL.setText(conf.getQtLongMacd()+"");
							isMacdCross.setSelected(conf.isCrossMacd());
						}
						else{
							isMACDSelect.setSelected(false);
							qtMacdS.setEditable(false);
							qtMacdC.setEditable(false);
							qtMacdL.setEditable(false);
							isMacdCross.setEnabled(false);
							isMacdCross.setSelected(false);
							qtMacdS.setText("");
							qtMacdC.setText("");
							qtMacdL.setText("");
						}

						if(conf.getQtKStochastic() > 0){
							isEstSelect.setSelected(true);
							qtK.setEditable(true);
							qtD.setEditable(true);
							scEst.setEditable(true);
							svEst.setEditable(true);
							isEstLow.setEnabled(true);
							qtEstLow.setEditable(true);
							isCrossEst.setEnabled(true);

							qtK.setText(conf.getQtKStochastic()+"");
							qtD.setText(conf.getQtDStochastic()+"");
							scEst.setText(conf.getOverboughtStoch()+"");
							svEst.setText(conf.getOversoldStoch()+"");

							isEstLow.setSelected(conf.isLowStoch());
							qtEstLow.setText(conf.getQtLowStoch()+"");
							isCrossEst.setSelected(conf.isCrossStoch());
						} 
						else{
							isEstSelect.setSelected(false);
							qtK.setEditable(false);
							qtD.setEditable(false);
							scEst.setEditable(false);
							svEst.setEditable(false);
							isEstLow.setEnabled(false);
							qtEstLow.setEditable(false);
							isCrossEst.setEnabled(false);

							qtK.setText("");
							qtD.setText("");
							scEst.setText("");
							svEst.setText("");

							isEstLow.setSelected(false);
							qtEstLow.setText("");
							isCrossEst.setSelected(false);
						}
						
						if(conf.getQtPeriodDmi() > 0){
							
							isDmiSelect.setSelected(true);
							qtDmiPeriod.setEditable(true);
							qtDmiAverage.setEditable(true);
							
							qtDmiPeriod.setText(conf.getQtPeriodDmi()+"");
							qtDmiAverage.setText(conf.getQtAvgDmi()+"");
						}
						else{
							isDmiSelect.setSelected(false);
							qtDmiPeriod.setEditable(false);
							qtDmiAverage.setEditable(false);
							
							qtDmiPeriod.setText("");
							qtDmiAverage.setText("");
						}
						
						if(conf.getQtShortDidi() > 0){
							
							isMMNSelect.setSelected(true);					
							qtShortDidi.setEditable(true);
							qtMediumDidi.setEditable(true);
							qtLongDidi.setEditable(true);
							
							qtShortDidi.setText(conf.getQtShortDidi()+"");
							qtMediumDidi.setText(conf.getQtMediumDidi()+"");
							qtLongDidi.setText(conf.getQtLongDidi()+"");
						}
						else{
							isMMNSelect.setSelected(false);					
							qtShortDidi.setEditable(false);
							qtMediumDidi.setEditable(false);
							qtLongDidi.setEditable(false);
							
							qtShortDidi.setText("");
							qtMediumDidi.setText("");
							qtLongDidi.setText("");
						}

						switch(conf.getPeriod()){				
						case 'D':
							dayButton.setEnabled(false);
							daily.setEnabled(false);
							weekButton.setEnabled(true);
							weekly.setEnabled(true);
							monthButton.setEnabled(true);
							monthly.setEnabled(true);

							break;
						case 'W':
							dayButton.setEnabled(true);
							daily.setEnabled(true);
							weekButton.setEnabled(false);
							weekly.setEnabled(false);
							monthButton.setEnabled(true);
							monthly.setEnabled(true);
							break;
						case 'M':
							dayButton.setEnabled(true);
							daily.setEnabled(true);
							weekButton.setEnabled(true);
							weekly.setEnabled(true);
							monthButton.setEnabled(false);
							monthly.setEnabled(false);
							break;
						}
						dateStart.setText(simpleDateformat.format(conf.getDtStart()));
						dateEnd.setText(simpleDateformat.format(conf.getDtEnd()));
						stockCombo.setSelectedItem(conf.getStockCode());
						
						qtMM1FocusLost(null);
						qtMM2FocusLost(null);
						qtMM3FocusLost(null);
						qtMM4FocusLost(null);
						qtMM5FocusLost(null);
						bollingerFocusLost(null);
						volumeActionPerformed(null);
						obvActionPerformed(null);
						isIFRSelectActionPerformed(null);
						estocasticoActionPerformed(null);
						dmiActionPerformed(null);
						didiActionPerformed(null);
						macdActionPerformed(null);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}				
			});

			monitorMenu.add(acaoAcomp);
		}
	} 
	
	private void stopMonitorActionPerformed(java.awt.event.ActionEvent evt) {                                         
		stopMonitor.setEnabled(false);
		selectMonitor.setEnabled(true);
		
		confAcompanhamento = null;
		sistema.resetAcompanhamento();
		
		for(int i = 4; i< monitorMenu.getMenuComponentCount();i++){
			
			monitorMenu.remove(monitorMenu.getMenuComponent(i));
			i--;	
		}	
	}

	private void tutorialActionPerformed(java.awt.event.ActionEvent evt) {                                         
		// TODO add your handling code here:
	}                                        

	private void useTermsActionPerformed(java.awt.event.ActionEvent evt) {                                         
		// TODO add your handling code here:		
	}                                        

	private void aboutActionPerformed(java.awt.event.ActionEvent evt) {                                      
	
		new About();
	}                                     

	private void dateFocusLost(java.awt.event.FocusEvent evt) {
		
		if(!dayButton.isEnabled()){
			PERIOD = 'D';
		} 
		else if(!weekButton.isEnabled()){
			PERIOD = 'W';
		}
		else if(!monthButton.isEnabled()){
			PERIOD = 'M';
		}  	   
		conf.setPeriod(PERIOD);

		try {
			setAcaoData(true);
		} catch (StockException e) {

			e.printStackTrace();
		}   	
	} 

	private void stockComboActionPerformed(java.awt.event.ActionEvent evt) {   
		
		if(!dayButton.isEnabled()){
			PERIOD = 'D';
		} 
		else if(!weekButton.isEnabled()){
			PERIOD = 'W';
		}
		else if(!monthButton.isEnabled()){
			PERIOD = 'M';
		}    	
		conf.setPeriod(PERIOD);

		try {
			setAcaoData(true);
		} catch (StockException e) {

			logger.error(e.toString());
		}    
	}                                          

	private void dayButtonActionPerformed(java.awt.event.ActionEvent evt) {                                          
		dayButton.setEnabled(false);
		daily.setEnabled(false);
		weekButton.setEnabled(true);
		weekly.setEnabled(true);
		monthButton.setEnabled(true);
		monthly.setEnabled(true);
		conf.setPeriod('D');
		PERIOD = 'D';
		try {
			setAcaoData(false);
		} catch (StockException e) {

			logger.error(e.toString());
		}    	
	}                                         

	private void weekButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
		dayButton.setEnabled(true);
		daily.setEnabled(true);
		weekButton.setEnabled(false);
		weekly.setEnabled(false);
		monthButton.setEnabled(true);
		monthly.setEnabled(true);
		conf.setPeriod('W');
		PERIOD = 'W';

		try {
			setAcaoData(false);
		} catch (StockException e) {

			logger.error(e.toString());
		}  
	}                                          

	private void monthButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
		dayButton.setEnabled(true);
		daily.setEnabled(true);
		weekButton.setEnabled(true);
		weekly.setEnabled(true);
		monthButton.setEnabled(false);
		monthly.setEnabled(false);
		conf.setPeriod('M');
		PERIOD = 'M';
		try {
			setAcaoData(false);
		} catch (StockException e) {

			logger.error(e.toString());
		}  
	}                                           

	private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {
		grafico.doSaveAs();
	}                                                                                                                                

	private void infoButtonActionPerformed(java.awt.event.ActionEvent evt) {
		isDataScreen = ! isDataScreen;
		grafico.setInfoScreen(isDataScreen);
	} 

	private void qtMM1FocusLost(java.awt.event.FocusEvent evt) {
		int qtmm;

		try{
			qtmm = new Integer(qtMM1.getText()).intValue();

			conf.setQtSMA1(qtmm, tpMM1.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMM(conf.getQtSMA1(), 1,conf.getCatSMA1());
		}
		catch(Exception e){
			conf.setQtSMA1(0, tpMM1.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMM(1);
		}    	
	}

	private void qtMM2FocusLost(java.awt.event.FocusEvent evt) {
		int qtmm;

		try{
			qtmm = new Integer(qtMM2.getText()).intValue();

			conf.setQtSMA2(qtmm, tpMM2.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMM(conf.getQtSMA2(), 2,conf.getCatSMA2());
		}
		catch(Exception e){
			conf.setQtSMA2(0, tpMM2.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMM(2);
		}    	
	}

	private void qtMM3FocusLost(java.awt.event.FocusEvent evt) {
		int qtmm;

		try{
			qtmm = new Integer(qtMM3.getText()).intValue();

			conf.setQtSMA3(qtmm, tpMM3.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMM(conf.getQtSMA3(), 3,conf.getCatSMA3());
		}
		catch(Exception e){
			conf.setQtSMA3(0, tpMM3.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMM(3);
		}    	
	}

	private void qtMM4FocusLost(java.awt.event.FocusEvent evt) {
		int qtmm;

		try{
			qtmm = new Integer(qtMM4.getText()).intValue();

			conf.setQtSMA4(qtmm, tpMM4.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMM(conf.getQtSMA4(), 4,conf.getCatSMA4());
		}
		catch(Exception e){
			conf.setQtSMA4(0, tpMM4.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMM(4);
		}    	
	}

	private void qtMM5FocusLost(java.awt.event.FocusEvent evt) {
		int qtmm;

		try{
			qtmm = new Integer(qtMM5.getText()).intValue();

			conf.setQtSMA5(qtmm, tpMM5.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMM(conf.getQtSMA5(), 5,conf.getCatSMA5());
		}
		catch(Exception e){
			conf.setQtSMA5(0, tpMM5.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMM(5);
		}    	
	}

	private void bollingerFocusLost(java.awt.event.FocusEvent evt) {

		int qtBandas = 0;
		int desvio = 0;

		try{
			qtBandas = new Integer(qtBB.getText()).intValue();
		}
		catch(Exception e){}

		try{
			desvio = new Integer(dsvBB.getText()).intValue();
		}
		catch(Exception e){}

		if(qtBandas>0 && desvio > 0){

			conf.setQtBB(qtBandas);
			conf.setDvBB(desvio);

			grafico.setBollingerBands(qtBandas, desvio); 
		}
		else{
			grafico.removeBollingerBands();
		} 
	}

	private void volumeActionPerformed(java.awt.event.ActionEvent evt) {  

		if(isVolSelect.isSelected()){
			qtMMVol1.setEditable(true);
			tpMMVol1.setEnabled(true);
			qtMMVol2.setEditable(true);
			tpMMVol2.setEnabled(true);
			qtMMVol3.setEditable(true);
			tpMMVol3.setEnabled(true);

			grafico.setVolume();

			conf.setVolume(true);

			qtMMVol1FocusLost(null);
			qtMMVol2FocusLost(null);
			qtMMVol3FocusLost(null);
		}
		else{
			qtMMVol1.setEditable(false);
			tpMMVol1.setEnabled(false);
			qtMMVol2.setEditable(false);
			tpMMVol2.setEnabled(false);
			qtMMVol3.setEditable(false);
			tpMMVol3.setEnabled(false);

			conf.setVolume(false);

			grafico.removeVolume();

		}
	}

	private void qtMMVol1FocusLost(java.awt.event.FocusEvent evt) {
		int qtmm;

		try{
			qtmm = new Integer(qtMMVol1.getText()).intValue();

			conf.setQtSMAVol1(qtmm, tpMMVol1.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMMVolume(conf.getQtSMAVol1(), 1, conf.getCatSMAVol1());
		}
		catch(Exception e){
			conf.setQtSMAVol1(0, tpMMVol1.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMMVolume(1);
		} 
	}
	private void qtMMVol2FocusLost(java.awt.event.FocusEvent evt) {
		int qtmm;

		try{
			qtmm = new Integer(qtMMVol2.getText()).intValue();

			conf.setQtSMAVol2(qtmm, tpMMVol2.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMMVolume(conf.getQtSMAVol2(), 2, conf.getCatSMAVol2());
		}
		catch(Exception e){
			conf.setQtSMAVol2(0, tpMMVol2.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMMVolume(2);
		} 
	}
	private void qtMMVol3FocusLost(java.awt.event.FocusEvent evt) {
		int qtmm;

		try{
			qtmm = new Integer(qtMMVol3.getText()).intValue();

			conf.setQtSMAVol3(qtmm, tpMMVol3.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMMVolume(conf.getQtSMAVol3(), 3, conf.getCatSMAVol3());
		}
		catch(Exception e){
			conf.setQtSMAVol3(0, tpMMVol3.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMMVolume(3);
		} 
	}

	private void isIFRSelectActionPerformed(java.awt.event.ActionEvent evt) {                                             
		if(isIFRSelect.isSelected()){
			qtIfr.setEditable(true);
			scIfr.setEditable(true);
			svIfr.setEditable(true);
			qtMMIfr1.setEditable(true);
			tpMMIfr1.setEnabled(true);
			qtMMIfr2.setEditable(true);
			tpMMIfr2.setEnabled(true);
			qtMMIfr3.setEditable(true);
			tpMMIfr3.setEnabled(true);

			qtIfrFocusLost(null);
			qtMMIfr1FocusLost(null);
			qtMMIfr2FocusLost(null);
			qtMMIfr3FocusLost(null);

		}
		else{
			qtIfr.setEditable(false);
			scIfr.setEditable(false);
			svIfr.setEditable(false);
			qtMMIfr1.setEditable(false);
			tpMMIfr1.setEnabled(false);
			qtMMIfr2.setEditable(false);
			tpMMIfr2.setEnabled(false);
			qtMMIfr3.setEditable(false);
			tpMMIfr3.setEnabled(false);

			grafico.removeIFR();            
		}    	
	}

	private void qtIfrFocusLost(java.awt.event.FocusEvent evt) {

		int ifrValue = 0;
		int scValue = 0;
		int svValue = 0;

		try{
			ifrValue = new Integer(qtIfr.getText()).intValue();
		}
		catch(Exception e){}

		try{
			scValue = new Integer(scIfr.getText()).intValue();
		}
		catch(Exception e){}

		try{
			svValue = new Integer(svIfr.getText()).intValue();
		}
		catch(Exception e){}

		if(ifrValue>0){
			conf.setQtRSI(ifrValue);
			conf.setOverboughtRsi(scValue);
			conf.setOversoldRsi(svValue);
			grafico.setIFR(ifrValue, scValue, svValue); 
			qtMMIfr1FocusLost(null);
			qtMMIfr2FocusLost(null);
			qtMMIfr3FocusLost(null);
		}
		else{
			grafico.removeIFR();
		}    	   	
	}

	private void qtMMIfr1FocusLost(java.awt.event.FocusEvent evt) {
		int qtmm;    	
		try{
			qtmm = new Integer(qtMMIfr1.getText()).intValue();

			conf.setQtSMARSI1(qtmm, tpMMIfr1.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMMIFR(conf.getQtSMARSI1(), conf.getQtRSI(), 3, conf.getCatSMARSI1());
		}
		catch(Exception e){
			conf.setQtSMARSI1(0, tpMMIfr1.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMMIfr(3);
		} 
	}

	private void qtMMIfr2FocusLost(java.awt.event.FocusEvent evt) {
		int qtmm;    	
		try{
			qtmm = new Integer(qtMMIfr2.getText()).intValue();

			conf.setQtSMARSI2(qtmm, tpMMIfr2.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMMIFR(conf.getQtSMARSI2(), conf.getQtRSI(), 4, conf.getCatSMARSI2());
		}
		catch(Exception e){
			conf.setQtSMARSI2(0, tpMMIfr2.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMMIfr(4);
		} 
	}

	private void qtMMIfr3FocusLost(java.awt.event.FocusEvent evt) {
		int qtmm;    	
		try{
			qtmm = new Integer(qtMMIfr3.getText()).intValue();

			conf.setQtSMARSI3(qtmm, tpMMIfr3.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMMIFR(conf.getQtSMARSI3(), conf.getQtRSI(), 5, conf.getCatSMARSI3());
		}
		catch(Exception e){
			conf.setQtSMARSI3(0, tpMMIfr3.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMMIfr(5);
		}
	}

	private void obvActionPerformed(java.awt.event.ActionEvent evt) {                                             

		if(isOBVSelect.isSelected()){
			qtMMObv1.setEditable(true);
			qtMMObv2.setEditable(true);
			qtMMObv3.setEditable(true);
			tpMMObv1.setEnabled(true);
			tpMMObv2.setEnabled(true);
			tpMMObv3.setEnabled(true);

			conf.setObv(true);
			grafico.setOBV();
			qtMMObv1FocusLost(null);
			qtMMObv2FocusLost(null);
			qtMMObv3FocusLost(null);
		}
		else{
			qtMMObv1.setEditable(false);
			qtMMObv2.setEditable(false);
			qtMMObv3.setEditable(false);
			tpMMObv1.setEnabled(false);
			tpMMObv2.setEnabled(false);
			tpMMObv3.setEnabled(false);

			conf.setObv(false);
			grafico.removeOBV();
		}
	} 

	private void qtMMObv1FocusLost(java.awt.event.FocusEvent evt) {

		int qtmm;

		try{
			qtmm = new Integer(qtMMObv1.getText()).intValue();

			conf.setQtSMAOBV1(qtmm, tpMMObv1.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMMOBV(conf.getQtSMAOBV1(), 1, conf.getCatSMAOBV1());
		}
		catch(Exception e){
			conf.setQtSMAOBV1(0, tpMMObv1.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMMOBV(1);
		} 
	}

	private void qtMMObv2FocusLost(java.awt.event.FocusEvent evt) {
		int qtmm;

		try{
			qtmm = new Integer(qtMMObv2.getText()).intValue();

			conf.setQtSMAOBV2(qtmm, tpMMObv2.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMMOBV(conf.getQtSMAOBV2(), 2, conf.getCatSMAOBV2());
		}
		catch(Exception e){
			conf.setQtSMAOBV2(0, tpMMObv2.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMMOBV(2);
		} 
	}

	private void qtMMObv3FocusLost(java.awt.event.FocusEvent evt) {
		int qtmm;

		try{
			qtmm = new Integer(qtMMObv3.getText()).intValue();

			conf.setQtSMAOBV3(qtmm, tpMMObv3.getSelectedItem().toString().toLowerCase().charAt(0)); 

			grafico.setMMOBV(conf.getQtSMAOBV3(), 3, conf.getCatSMAOBV3());
		}
		catch(Exception e){
			conf.setQtSMAOBV3(0, tpMMObv3.getSelectedItem().toString().toLowerCase().charAt(0));
			grafico.removeMMOBV(3);
		} 
	}

	private void macdActionPerformed(java.awt.event.ActionEvent evt) {                                             
		if(isMACDSelect.isSelected()){
			qtMacdS.setEditable(true);
			qtMacdC.setEditable(true);
			qtMacdL.setEditable(true);
			isMacdCross.setEnabled(true);

			qtMacdActionPerformed(null);
		}
		else{
			qtMacdS.setEditable(false);
			qtMacdC.setEditable(false);
			qtMacdL.setEditable(false);
			isMacdCross.setEnabled(false);

			conf.setQtLongMacd(0);
			conf.setQtShortMacd(0);
			conf.setQtSignalMacd(0);
			conf.setCrossMacd(false);
			grafico.removeMACD();
		}


	} 

	private void qtMacdActionPerformed(java.awt.event.FocusEvent evt) {
		int macdS = 0;
		int macdC = 0;
		int macdL = 0;

		try{
			macdS = new Integer(qtMacdS.getText()).intValue();
		}
		catch(Exception e){}

		try{
			macdC = new Integer(qtMacdC.getText()).intValue();
		}
		catch(Exception e){}

		try{
			macdL = new Integer(qtMacdL.getText()).intValue();
		}
		catch(Exception e){}

		if(macdS > 0
				&& macdC > 0
				&& macdL > 0) {
			conf.setQtLongMacd(macdL);
			conf.setQtShortMacd(macdC);
			conf.setQtSignalMacd(macdS);
			conf.setCrossMacd(isMacdCross.isSelected());
			grafico.setMACD(macdL, macdC, macdS, isMacdCross.isSelected());
		}
		else{
			grafico.removeMACD();
		}    
	}
	
	private void dmiActionPerformed(java.awt.event.ActionEvent evt) {
		
		if(isDmiSelect.isSelected()){
			qtDmiPeriod.setEditable(true);
			qtDmiAverage.setEditable(true);
			
			qtDmiActionPerformed(null);
		}
		else{
			qtDmiPeriod.setEditable(false);
			qtDmiAverage.setEditable(false);

			conf.setDmi(0, 0);
			grafico.removeDMI();
		}
	}
	
	private void qtDmiActionPerformed(java.awt.event.FocusEvent evt) {
		
		int qtPeriod = 0;
		int qtAvg = 0;

		try{
			qtPeriod = new Integer(qtDmiPeriod.getText()).intValue();
		}
		catch(Exception e){}

		try{
			qtAvg = new Integer(qtDmiAverage.getText()).intValue();
		}
		catch(Exception e){}

		if(qtPeriod > 0
		&& qtAvg > 0) {
			conf.setDmi(qtPeriod, qtAvg);
			grafico.setDmi(qtPeriod, qtAvg);
			
		}
		else{
			grafico.removeDMI();
		} 
	}
	
	private void didiActionPerformed(java.awt.event.ActionEvent evt) {
		
		if(isMMNSelect.isSelected()){
			qtShortDidi.setEditable(true);
			qtMediumDidi.setEditable(true);
			qtLongDidi.setEditable(true); 
			
			qtDidiActionPerformed(null);
		}
		else{
			qtShortDidi.setEditable(false);
			qtMediumDidi.setEditable(false);
			qtLongDidi.setEditable(false);

			conf.setDidi(0, 0, 0);
			grafico.removeMMNormal();
		}
	}
	
	private void qtDidiActionPerformed(java.awt.event.FocusEvent evt) {
		
		int qtShort = 0;
		int qtMedium = 0;
		int qtLong = 0;

		try{
			qtShort = new Integer(qtShortDidi.getText()).intValue();
		}
		catch(Exception e){}

		try{
			qtMedium = new Integer(qtMediumDidi.getText()).intValue();
		}
		catch(Exception e){}
		
		try{
			qtLong = new Integer(qtLongDidi.getText()).intValue();
		}
		catch(Exception e){}

		if(qtShort > 0
		&& qtMedium > 0
		&& qtLong > 0) {
			conf.setDidi(qtShort, qtMedium, qtLong);
			grafico.setMMNormal(qtShort, qtMedium, qtLong);
		}
		else{
			grafico.removeMMNormal();
		}
	}

	private void estocasticoActionPerformed(java.awt.event.ActionEvent evt) {

		if(isEstSelect.isSelected()){
			qtK.setEditable(true);
			qtD.setEditable(true);
			scEst.setEditable(true);
			svEst.setEditable(true);
			isEstLow.setEnabled(true);
			if(isEstLow.isSelected()){
				qtEstLow.setEditable(true);
			}
			isCrossEst.setEnabled(true);
			estocFocusLost(null);
		}
		else{
			qtK.setEditable(false);
			qtD.setEditable(false);
			scEst.setEditable(false);
			svEst.setEditable(false);
			isEstLow.setEnabled(false);
			qtEstLow.setEditable(false);
			isCrossEst.setEnabled(false);

			conf.setQtKStochastic(0);
			conf.setQtDStochastic(0);
			conf.setOverboughtStoch(0);
			conf.setOversoldStoch(0);
			conf.setLowStoch(false);
			conf.setQtLowStoch(0);
			conf.setCrossStoch(false);

			grafico.removeEstocastico();
		}
	}

	private void estocFocusLost(java.awt.event.FocusEvent evt) {
		int k = 0;
		int d = 0;
		int sc = 0;
		int sv = 0;
		int el = 0;
		boolean isEL = isEstLow.isSelected();

		try{
			k = new Integer(qtK.getText()).intValue();
		}
		catch(Exception e){}

		try{
			d = new Integer(qtD.getText()).intValue();
		}
		catch(Exception e){}

		try{
			sc = new Integer(scEst.getText()).intValue();
		}
		catch(Exception e){}

		try{
			sv = new Integer(svEst.getText()).intValue();
		}
		catch(Exception e){}

		try{
			el = new Integer(qtEstLow.getText()).intValue();
		}
		catch(Exception e){ isEL = false; }

		conf.setQtKStochastic(k);
		conf.setQtDStochastic(d);
		conf.setOverboughtStoch(sc);
		conf.setOversoldStoch(sv);
		conf.setLowStoch(isEL);
		conf.setQtLowStoch(el);
		conf.setCrossStoch(isCrossEst.isSelected());

		if(k > 0
		&& d > 0) {    		
			grafico.setEstocastico(k, d, el, isEL, isCrossEst.isSelected(), sc, sv);
		}
		else{
			grafico.removeEstocastico();
		}      	
	}
	
	public Principal getSistema(){
		
		return this.sistema;
	}
	
	public Configuration getConf(){
		
		return this.conf;
	}

	// Variables declaration - do not modify
	private javax.swing.JMenuItem about;
	private javax.swing.JMenuItem arithmetic;
	private javax.swing.JButton arithmeticButton;
	private javax.swing.JMenuItem arrow;
	private javax.swing.JButton arrowButton;
	private javax.swing.JLabel bb;
	private javax.swing.JLabel calendar;
	private javax.swing.JMenuItem candle;
	private javax.swing.JButton candleButton;
	private javax.swing.JDesktopPane chart;
	private javax.swing.JPanel indicators;
	private javax.swing.JMenuItem crossHair;
	private javax.swing.JButton crossHairButton;
	private javax.swing.JLabel d;
	private javax.swing.JMenuItem daily;
	private javax.swing.JMenuItem dataView;
	private javax.swing.JFormattedTextField dateEnd;
	private javax.swing.JTextField dateField;
	private javax.swing.JFormattedTextField dateStart;
	private javax.swing.JButton dayButton;
	private javax.swing.JLabel desvio;
	private javax.swing.JTextField dsvBB;
	private javax.swing.JPanel eixoPrincipal;
	private javax.swing.JButton evolutionButton;
	private javax.swing.JMenuItem evolutionStraight;
	private javax.swing.JMenuItem exit;
	private javax.swing.JMenuItem export;
	private javax.swing.JButton exportButton;
	private javax.swing.JButton extFiboButton;
	private javax.swing.JMenuItem fiboExtension;
	private javax.swing.JMenu file;
	private javax.swing.JMenuItem hFiboRetraction;
	private javax.swing.JMenu help;
	private javax.swing.JButton hFiboButton;
	private javax.swing.JButton horizontalButton;
	private javax.swing.JMenuItem horizontalStraight;
	private javax.swing.JLabel ifr;
	private javax.swing.JPanel indVolume;
	private javax.swing.JPanel indOBV;
	private javax.swing.JPanel indIFR;
	private javax.swing.JPanel indMACD;
	private javax.swing.JPanel indEstocastico;
	private javax.swing.JPanel indDmi;
	private javax.swing.JLabel dmiPeriod;
	private javax.swing.JLabel dmiAverage;
	private javax.swing.JTextField qtDmiPeriod;
	private javax.swing.JTextField qtDmiAverage;
	private javax.swing.JPanel indMMNormal;
	private javax.swing.JLabel shortDidi;
	private javax.swing.JLabel mediumDidi;
	private javax.swing.JLabel longDidi;
	private javax.swing.JTextField qtShortDidi;
	private javax.swing.JTextField qtMediumDidi;
	private javax.swing.JTextField qtLongDidi;
	private javax.swing.JButton infoButton;
	private javax.swing.JCheckBox isCrossEst;
	private javax.swing.JCheckBox isEstLow;
	private javax.swing.JCheckBox isMacdCross;
	private javax.swing.JLabel k;
	private javax.swing.JMenuItem line;
	private javax.swing.JButton lineButton;
	private javax.swing.JButton lineChartButton;
	private javax.swing.JMenuItem linechart;
	private javax.swing.JButton logButton;
	private javax.swing.JMenuItem logarithmic;
	private javax.swing.JLabel macdC;
	private javax.swing.JLabel macdL;
	private javax.swing.JLabel macdS;
	private javax.swing.JMenuBar menu;
	private javax.swing.JLabel mm1;
	private javax.swing.JLabel mm10;
	private javax.swing.JLabel mm11;
	private javax.swing.JLabel mm12;
	private javax.swing.JLabel mm13;
	private javax.swing.JLabel mm14;
	private javax.swing.JLabel mm2;
	private javax.swing.JLabel mm3;
	private javax.swing.JLabel mm4;
	private javax.swing.JLabel mm5;
	private javax.swing.JLabel mm6;
	private javax.swing.JLabel mm7;
	private javax.swing.JLabel mm8;
	private javax.swing.JLabel mm9;
	private javax.swing.JMenuItem monitor;
	private javax.swing.JMenu monitorMenu;
	private javax.swing.JButton monthButton;
	private javax.swing.JMenuItem monthly;
	private javax.swing.JMenuItem open;
	private javax.swing.JButton parallelButton;
	private javax.swing.JMenuItem parallelLines;
	private javax.swing.JMenu period;
	private javax.swing.JTextField qtBB;
	private javax.swing.JTextField qtD;
	private javax.swing.JTextField qtEstLow;
	private javax.swing.JTextField qtIfr;
	private javax.swing.JTextField qtK;
	private javax.swing.JTextField qtMM1;
	private javax.swing.JTextField qtMM2;
	private javax.swing.JTextField qtMM3;
	private javax.swing.JTextField qtMM4;
	private javax.swing.JTextField qtMM5;
	private javax.swing.JTextField qtMMIfr1;
	private javax.swing.JTextField qtMMIfr2;
	private javax.swing.JTextField qtMMIfr3;
	private javax.swing.JTextField qtMMVol1;
	private javax.swing.JTextField qtMMVol2;
	private javax.swing.JTextField qtMMVol3;
	private javax.swing.JTextField qtMMObv1;
	private javax.swing.JTextField qtMMObv2;
	private javax.swing.JTextField qtMMObv3;
	private javax.swing.JTextField qtMacdC;
	private javax.swing.JTextField qtMacdL;
	private javax.swing.JTextField qtMacdS;
	private javax.swing.JButton rectButton;
	private javax.swing.JMenuItem rectangle;
	private javax.swing.JButton retFiboButton;
	private javax.swing.JMenuItem save;
	private javax.swing.JMenuItem selStock;
	private javax.swing.JMenuItem confMenu;
	private javax.swing.JLabel sc;
	private javax.swing.JLabel scE;
	private javax.swing.JTextField scEst;
	private javax.swing.JTextField scIfr;
	private javax.swing.JMenu scale;
	private javax.swing.JMenuItem selectMonitor;
	private javax.swing.JMenuItem selectSimulate;
	private javax.swing.JPopupMenu.Separator sepExit;
	private javax.swing.JPopupMenu.Separator sepFile;
	private javax.swing.JPopupMenu.Separator sepConf;
	private javax.swing.JPopupMenu.Separator sepHelp;
	private javax.swing.JPopupMenu.Separator sepMonitor;
	private javax.swing.JPopupMenu.Separator sepSimulate;
	private javax.swing.JMenuItem simulate;
	private javax.swing.JMenu simulateMenu;
	private javax.swing.JLabel stock;
	private javax.swing.JComboBox stockCombo;
	private javax.swing.JMenuItem stopMonitor;
	private javax.swing.JLabel sv;
	private javax.swing.JLabel svE;
	private javax.swing.JTextField svEst;
	private javax.swing.JTextField svIfr;
	private javax.swing.JTabbedPane tabbedPane;
	private javax.swing.JMenuItem text;
	private javax.swing.JButton textButton;
	private javax.swing.JMenu tools;
	private javax.swing.JCheckBox isVolSelect;
	private javax.swing.JCheckBox isOBVSelect;
	private javax.swing.JCheckBox isIFRSelect;
	private javax.swing.JCheckBox isEstSelect;
	private javax.swing.JCheckBox isMACDSelect;
	private javax.swing.JCheckBox isDmiSelect;
	private javax.swing.JCheckBox isMMNSelect;
	private javax.swing.JComboBox tpMM1;
	private javax.swing.JComboBox tpMM2;
	private javax.swing.JComboBox tpMM3;
	private javax.swing.JComboBox tpMM4;
	private javax.swing.JComboBox tpMM5;
	private javax.swing.JComboBox tpMMIfr1;
	private javax.swing.JComboBox tpMMIfr2;
	private javax.swing.JComboBox tpMMIfr3;
	private javax.swing.JComboBox tpMMVol1;
	private javax.swing.JComboBox tpMMVol2;
	private javax.swing.JComboBox tpMMVol3;
	private javax.swing.JComboBox tpMMObv1;
	private javax.swing.JComboBox tpMMObv2;
	private javax.swing.JComboBox tpMMObv3;
	private javax.swing.JMenuItem tutorial;
	private javax.swing.JMenu type;
	private javax.swing.JMenuItem useTerms;
	private javax.swing.JMenuItem vFiboRetraction;
	private javax.swing.JTextField valueField;
	private javax.swing.JButton verticalButton;
	private javax.swing.JMenuItem verticalStraight;
	private javax.swing.JMenu view;
	private javax.swing.JMenuItem riskGain;
	private javax.swing.JButton riskGainButton;
	private javax.swing.JButton weekButton;
	private javax.swing.JMenuItem weekly;
	private javax.swing.JPanel report;
	private javax.swing.JTextArea reportText;
	private javax.swing.JScrollPane scroll;
}