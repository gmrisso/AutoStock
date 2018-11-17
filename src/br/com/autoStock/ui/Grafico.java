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

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.AbstractXYAnnotation;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.TextAnchor;

import br.com.autoStock.indicator.Bollinger;
import br.com.autoStock.indicator.DataIndicator;
import br.com.autoStock.indicator.MMNormal;
import br.com.autoStock.indicator.Dmi;
import br.com.autoStock.indicator.Estocastico;
import br.com.autoStock.indicator.Ifr;
import br.com.autoStock.indicator.Macd;
import br.com.autoStock.indicator.MediaMovel;
import br.com.autoStock.indicator.Obv;
import br.com.autoStock.util.I18n;
import br.com.autoStock.util.entity.PeriodMap;
import br.com.autoStock.util.exception.PeriodException;
import br.com.autoStock.util.tools.Arrow;
import br.com.autoStock.util.tools.Evolution;
import br.com.autoStock.util.tools.ExtFibo;
import br.com.autoStock.util.tools.HRetFibo;
import br.com.autoStock.util.tools.HorizontalLine;
import br.com.autoStock.util.tools.ParallelLines;
import br.com.autoStock.util.tools.RectangleBox;
import br.com.autoStock.util.tools.RetFibo;
import br.com.autoStock.util.tools.RiskGain;
import br.com.autoStock.util.tools.StraightLine;
import br.com.autoStock.util.tools.TextBox;
import br.com.autoStock.util.tools.VerticalLine;

import com.mf4j.Quote;

public class Grafico extends JInternalFrame {

	static Logger logger = Logger.getLogger(Grafico.class.getName());

	private static final long serialVersionUID = 1L;
	private MainScreen mainScreen;
	private PeriodMap historico;
	private Quote[] quotes;
	private DefaultHighLowDataset dataset;
	private HashMap<String,String> mapaData; //[01/01/1900;15/07/2011]
	private HashMap<String,Integer> mapaDataIndex; //[01/01/1900;1]

	private ChartPanel chartPanel;
	private JFreeChart chart;
	private XYPlot subplotCandle;
	private XYPlot subplotMacd;
	private XYPlot subplotIfr;
	private XYPlot subplotObv;
	private XYPlot subplotVolume;
	private XYPlot subplotEst;
	private XYPlot subplotDMI;
	private XYPlot subplotMMNormal;
	private CombinedDomainXYPlot plot;
	private DataScreen dataScreen;

	private MediaMovel media;
	private Bollinger bollinger;
	private Macd macd;
	private Ifr ifr;
	private Obv obv;
	private Estocastico estocastico;
	private Dmi dmi;
	private MMNormal mmNormal;

	private ArrayList<AbstractXYAnnotation> annotations;

	private int iAddLine = 0;
	private int iAddParallelLines = 0;
	private int iAddHorizontalLine = 0;
	private int iAddVerticalLine = 0;
	private int iAddRect = 0;
	private int iAddEvol = 0;
	private int iAddExtFibo = 0;
	private int iAddRetFibo = 0;
	private int iAddHRetFibo = 0;
	private int iAddArrow = 0;
	private int iAddText  = 0;
	private int iAddRiskGain = 0;

	@SuppressWarnings("static-access")
	public Grafico(MainScreen mainScreen, PeriodMap historico, int x, int y, boolean rAxis,boolean candle) throws PeriodException{
		
		mapaData = new HashMap<String,String>();
		mapaDataIndex = new HashMap<String,Integer>();

		super.setTitle("["+mainScreen.PERIOD+"] " + historico.getCodeStock() + " - " + historico.getStockName());

		this.historico = historico;

		switch (mainScreen.PERIOD) {
		case 'D':
			this.quotes = historico.getQuoteDay();
			break;
		case 'W':
			this.quotes = historico.getQuoteWeek();
			break;
		case 'M':
			this.quotes = historico.getQuoteMonth();
			break;
		}
		
		this.mainScreen = mainScreen;
		this.setResizable(true);
		this.setPreferredSize(new java.awt.Dimension(x, y));

		this.annotations = new ArrayList<AbstractXYAnnotation>();

		calcularDataset(historico.getCodeStock());

		DateAxis    domainAxis       = new DateAxis(I18n.getMessage("cy"));
		domainAxis.setTickLabelsVisible(false);
		
		//TODO Mostrar Tick labels das datas

		if(candle){
			CandlestickRenderer renderer = new CandlestickRenderer();		
			renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
			renderer.setSeriesPaint(0, Color.BLACK);
			renderer.setDownPaint(Color.BLACK);
			renderer.setUpPaint(Color.WHITE);			
			renderer.setDrawVolume(false);

			if(rAxis){		
				NumberAxis  rangeAxis        = new NumberAxis(I18n.getMessage("dg"));	

				rangeAxis.setAutoRangeIncludesZero(false);				
				subplotCandle = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
			}
			else{
				LogarithmicAxis rangeAxis = new LogarithmicAxis(I18n.getMessage("dg"));
				rangeAxis.setAutoRangeIncludesZero(false);
				subplotCandle = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
			}
		}
		else{
			XYItemRenderer renderer = new StandardXYItemRenderer();

			if(rAxis){		
				NumberAxis  rangeAxis        = new NumberAxis(I18n.getMessage("dg"));			
				rangeAxis.setAutoRangeIncludesZero(false);				
				subplotCandle = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
			}
			else{
				LogarithmicAxis rangeAxis = new LogarithmicAxis(I18n.getMessage("dg"));
				rangeAxis.setAutoRangeIncludesZero(false);
				subplotCandle = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
			}
		}			

		subplotCandle.setDomainGridlinesVisible(true);

		plot = new CombinedDomainXYPlot(domainAxis);
		plot.setGap(3.0);		

		plot.add(subplotCandle, 4);

		chart = new JFreeChart(title,null,plot,true);

		chartPanel = new ChartPanel(chart,true);
		chartPanel.setMouseZoomable(false);		
		chartPanel.setDisplayToolTips(false);

		getContentPane().addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent ev) {}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {

				keyPressedEvent(e);
			}
		});

		chartPanel.addChartMouseListener(new ChartMouseListener() {

			String dateAux;
			String valueAux;
			DecimalFormat df = new DecimalFormat("0.00");
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");

			double x1, y1,x2,y2;
			StraightLine lineTemporary;
			HorizontalLine hLine;
			VerticalLine vLine;
			ParallelLines parLineTemporary;
			RectangleBox rectTemporary;
			Evolution evolTemporary;
			ExtFibo extFiboTemporary;
			RetFibo retFiboTemporary;
			HRetFibo hRetFiboTemporary;
			Arrow arrowTemporary;
			RiskGain riskTemporary;

			@SuppressWarnings("unchecked")
			@Override
			public void chartMouseMoved(ChartMouseEvent event) {

				try{
					Point mousePoint = new Point(event.getTrigger().getX(), event.getTrigger().getY()); 

					ChartRenderingInfo chartInfo = chartPanel.getChartRenderingInfo(); 
					Point2D java2DPoint = chartPanel.translateScreenToJava2D(mousePoint); 
					PlotRenderingInfo plotInfo = chartInfo.getPlotInfo(); 

					int subplotIndex = plotInfo.getSubplotIndex(java2DPoint); 
					long date = 0;

					if (subplotIndex >= 0) 
					{ 						
						Rectangle2D dataArea = plotInfo.getDataArea();
						try{
							date = (long) plot.getDomainAxis().java2DToValue(java2DPoint.getX(), dataArea, plot.getDomainAxisEdge());
							dateAux = mapaData.get(simpleDateformat.format(new Date(date))).toString();

						}catch(Exception e){}						

						Rectangle2D panelArea = chartPanel.getScreenDataArea(event.getTrigger().getX(),event.getTrigger().getY()); 

						List subplotsList = plot.getSubplots(); 
						Iterator iterator = subplotsList.iterator(); 
						int index = 0; 

						while (iterator.hasNext())   
						{ 
							XYPlot subplot = (XYPlot) iterator.next(); 

							if (subplotIndex == index) 
							{ 

								if(iAddArrow == 2){
									if(arrowTemporary != null){

										subplot.removeAnnotation(arrowTemporary);

										arrowTemporary.setEnd(convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));

										subplot.addAnnotation(arrowTemporary);
									}
									else{
										arrowTemporary = new Arrow(
												convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event),
												Math.toRadians(180));

										arrowTemporary.setSelected(true);
										subplot.addAnnotation(arrowTemporary);
									}										
								}

								if(iAddLine == 2){

									if(lineTemporary != null){
										subplot.removeAnnotation(lineTemporary);

										lineTemporary.setEnd(convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));

										subplot.addAnnotation(lineTemporary);
									}
									else{
										lineTemporary = new StraightLine(convertCoordinate(x1, true, event),
												convertCoordinate(y1, false, event),
												convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));

										lineTemporary.setSelected(true);
										subplot.addAnnotation(lineTemporary);
									}							
								}

								if(iAddParallelLines == 2){

									if(parLineTemporary != null){
										subplot.removeAnnotation(parLineTemporary);										
									}

									parLineTemporary = new ParallelLines(
											convertCoordinate(x1, true, event),
											convertCoordinate(y1, false, event),
											convertCoordinate(event.getTrigger().getX(), true, event),
											convertCoordinate(event.getTrigger().getY(), false, event));

									parLineTemporary.setSelected(true);
									subplot.addAnnotation(parLineTemporary);


								} else if(iAddParallelLines == 3){

									if(parLineTemporary != null){
										subplot.removeAnnotation(parLineTemporary);										
									}

									parLineTemporary = new ParallelLines(
											convertCoordinate(x1, true, event),
											convertCoordinate(y1, false, event),
											convertCoordinate(x2, true, event),
											convertCoordinate(y2, false, event),										
											convertCoordinate(event.getTrigger().getX(), true, event),
											convertCoordinate(event.getTrigger().getY(), false, event));

									parLineTemporary.setSelected(true);
									subplot.addAnnotation(parLineTemporary);
								}

								if(iAddHorizontalLine == 2){
									if(hLine != null){
										subplot.removeAnnotation(hLine);
										hLine.setEnd(convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										subplot.addAnnotation(hLine);
									}
									else{
										hLine = new HorizontalLine(											
												convertCoordinate(x1, true, event),
												convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										hLine.setSelected(true);
										subplot.addAnnotation(hLine);
									}	
								}

								if(iAddVerticalLine == 2){
									if(vLine != null){
										subplot.removeAnnotation(vLine);
										vLine.setEnd(convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										subplot.addAnnotation(vLine);
									}
									else{
										vLine = new VerticalLine(											
												convertCoordinate(y1, false, event),
												convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										vLine.setSelected(true);
										subplot.addAnnotation(vLine);
									}	
								}

								if(iAddRect == 2){
									if(rectTemporary != null){
										subplot.removeAnnotation(rectTemporary);
										rectTemporary.setEnd(convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										subplot.addAnnotation(rectTemporary);
									}
									else{
										rectTemporary = new RectangleBox(											
												convertCoordinate(x1, true, event),
												convertCoordinate(y1, false, event),
												convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										rectTemporary.setSelected(true);
										subplot.addAnnotation(rectTemporary);
									}										
								}

								if(iAddEvol == 2){
									if(evolTemporary != null){
										subplot.removeAnnotation(evolTemporary);
										evolTemporary.setEnd(convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										subplot.addAnnotation(evolTemporary);
									}
									else{
										evolTemporary = new Evolution(											
												convertCoordinate(x1, true, event),
												convertCoordinate(y1, false, event),
												convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										evolTemporary.setSelected(true);
										subplot.addAnnotation(evolTemporary);
									}										
								}

								if(iAddExtFibo == 2){
									if(extFiboTemporary != null){
										subplot.removeAnnotation(extFiboTemporary);
										extFiboTemporary.setEnd(convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										subplot.addAnnotation(extFiboTemporary);
									}
									else{
										extFiboTemporary = new ExtFibo(											
												convertCoordinate(x1, true, event),
												convertCoordinate(y1, false, event),
												convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										extFiboTemporary.setSelected(true);
										subplot.addAnnotation(extFiboTemporary);
									}										
								}

								if(iAddRetFibo == 2){
									if(retFiboTemporary != null){
										subplot.removeAnnotation(retFiboTemporary);
										retFiboTemporary.setEnd(convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										subplot.addAnnotation(retFiboTemporary);
									}
									else{
										retFiboTemporary = new RetFibo(											
												convertCoordinate(x1, true, event),
												convertCoordinate(y1, false, event),
												convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										retFiboTemporary.setSelected(true);
										subplot.addAnnotation(retFiboTemporary);
									}										
								}	

								if(iAddHRetFibo == 2){
									if(hRetFiboTemporary != null){
										subplot.removeAnnotation(hRetFiboTemporary);
										hRetFiboTemporary.setEnd(convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										subplot.addAnnotation(hRetFiboTemporary);
									}
									else{
										hRetFiboTemporary = new HRetFibo(											
												convertCoordinate(x1, true, event),
												convertCoordinate(y1, false, event),
												convertCoordinate(event.getTrigger().getX(), true, event),
												convertCoordinate(event.getTrigger().getY(), false, event));
										hRetFiboTemporary.setSelected(true);
										subplot.addAnnotation(hRetFiboTemporary);
									}										
								}																	

								if(iAddRiskGain == 2){

									if(riskTemporary != null){
										subplot.removeAnnotation(riskTemporary);										
									}

									riskTemporary = new RiskGain(
											convertCoordinate(x1, true, event),
											convertCoordinate(y1, false, event),
											convertCoordinate(event.getTrigger().getY(), false, event));
									riskTemporary.setSelected(true);
									subplot.addAnnotation(riskTemporary);


								} else if(iAddRiskGain == 3){

									if(riskTemporary != null){
										subplot.removeAnnotation(riskTemporary);										
									}

									riskTemporary = new RiskGain(
											convertCoordinate(x1, true, event),
											convertCoordinate(y1, false, event),
											convertCoordinate(y2, false, event),
											convertCoordinate(event.getTrigger().getY(), false, event));
									riskTemporary.setSelected(true);
									subplot.addAnnotation(riskTemporary);
								}

								double value = subplot.getRangeAxis().java2DToValue(mousePoint.getY(), panelArea, subplot.getRangeAxisEdge());

								if(value > 1000000000 || value < -1000000000){
									valueAux = df.format(value/1000000000)+" G";
								}
								else if(value > 1000000 || value < -1000000){
									valueAux = df.format(value/1000000)+" M";
								}
								else if(value > 1000 || value < -1000){
									valueAux = df.format(value/1000)+" K";
								}
								else{
									valueAux = df.format(value)+" ";
								}			                			                
							} 
							index++; 
						} 
						setInfoField(dateAux, valueAux);			        			        
					}

					try{
						setDataScreen(mapaDataIndex.get(simpleDateformat.format(new Date(date))));
					}
					catch(Exception e){}				

				} catch(Exception e){
					e.printStackTrace();
				}			    
			}

			@SuppressWarnings("unchecked")
			@Override
			public void chartMouseClicked(ChartMouseEvent event) {

				try{
					Point mousePoint = new Point(event.getTrigger().getX(), event.getTrigger().getY());
					
					ChartRenderingInfo chartInfo = chartPanel.getChartRenderingInfo(); 
					Point2D java2DPoint = chartPanel.translateScreenToJava2D(mousePoint); 
					PlotRenderingInfo plotInfo = chartInfo.getPlotInfo(); 

					int subplotIndex = plotInfo.getSubplotIndex(java2DPoint);

					if (subplotIndex >= 0) 
					{ 							
						List subplotsList = plot.getSubplots(); 
						Iterator iterator = subplotsList.iterator(); 
						int index = 0; 

						while (iterator.hasNext())   
						{ 							
							XYPlot subplot = (XYPlot) iterator.next(); 
							
							if (subplotIndex == index) 
							{ 		
								//Excluir ferramentas
								for(int i = 0; i < annotations.size();i++){

									if(iAddArrow == 0
											&& annotations.get(i) instanceof Arrow
											&& chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText() != null){

										Arrow arrow = (Arrow)annotations.get(i);

										if( chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText().equals(arrow.getId())){													
											arrow.setSelected(true);

											if(subplot.removeAnnotation(arrow))
												subplot.addAnnotation(arrow);
										}
										else if(arrow.isSelected()){
											arrow.setSelected(false);

											if(subplot.removeAnnotation(arrow))
												subplot.addAnnotation(arrow);
										}												
									}
									else if(iAddLine == 0
											&& annotations.get(i) instanceof StraightLine
											&& chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText() != null){

										StraightLine line = (StraightLine)annotations.get(i);
										if( chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText().equals(line.getId())){

											line.setSelected(true);

											if(subplot.removeAnnotation(line))
												subplot.addAnnotation(line);
										}
										else if(line.isSelected()){
											line.setSelected(false);

											if(subplot.removeAnnotation(line))
												subplot.addAnnotation(line);
										}
									}
									else if(iAddParallelLines == 0
											&& annotations.get(i) instanceof ParallelLines
											&&chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText() != null){

										ParallelLines pLine = (ParallelLines)annotations.get(i);
										if( chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText().equals(pLine.getId())){

											pLine.setSelected(true);

											if(subplot.removeAnnotation(pLine))
												subplot.addAnnotation(pLine);
										}
										else if(pLine.isSelected()){
											pLine.setSelected(false);

											if(subplot.removeAnnotation(pLine))
												subplot.addAnnotation(pLine);
										}										
									}
									else if(iAddHorizontalLine == 0
											&& annotations.get(i) instanceof HorizontalLine
											&& chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText() != null){

										HorizontalLine hLine = (HorizontalLine)annotations.get(i);
										if( chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText().equals(hLine.getId())){

											hLine.setSelected(true);

											if(subplot.removeAnnotation(hLine))
												subplot.addAnnotation(hLine);
										}
										else if(hLine.isSelected()){
											hLine.setSelected(false);

											if(subplot.removeAnnotation(hLine))
												subplot.addAnnotation(hLine);
										}
									}
									else if(iAddVerticalLine == 0
											&& annotations.get(i) instanceof VerticalLine
											&& chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText() != null){

										VerticalLine vLine = (VerticalLine)annotations.get(i);
										if( chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText().equals(vLine.getId())){

											vLine.setSelected(true);

											if(subplot.removeAnnotation(vLine))
												subplot.addAnnotation(vLine);
										}
										else if(vLine.isSelected()){
											vLine.setSelected(false);

											if(subplot.removeAnnotation(vLine))
												subplot.addAnnotation(vLine);
										}
									}
									else if(iAddRect == 0
											&& annotations.get(i) instanceof RectangleBox
											&& chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText() != null){

										RectangleBox rBox = (RectangleBox)annotations.get(i);
										if( chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText().equals(rBox.getId())){													
											rBox.setSelected(true);													
											if(subplot.removeAnnotation(rBox))
												subplot.addAnnotation(rBox);
										}
										else if(rBox.isSelected()){
											rBox.setSelected(false);

											if(subplot.removeAnnotation(rBox))
												subplot.addAnnotation(rBox);
										}
									}
									else if(iAddEvol == 0
											&& annotations.get(i) instanceof Evolution
											&& chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText() != null){

										Evolution evolution = (Evolution)annotations.get(i);
										if( chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText().equals(evolution.getId())){													
											evolution.setSelected(true);													
											if(subplot.removeAnnotation(evolution))
												subplot.addAnnotation(evolution);
										}
										else if(evolution.isSelected()){
											evolution.setSelected(false);

											if(subplot.removeAnnotation(evolution))
												subplot.addAnnotation(evolution);
										}
									}
									else if(iAddText == 0
											&& annotations.get(i) instanceof TextBox
											&& chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText() != null){

										TextBox tBox = (TextBox)annotations.get(i);

										if( chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText().equals(tBox.getId())){													
											tBox.setSelected(true);

											if(subplot.removeAnnotation(tBox))
												subplot.addAnnotation(tBox);
										}
										else if(tBox.isSelected()){
											tBox.setSelected(false);

											if(subplot.removeAnnotation(tBox))
												subplot.addAnnotation(tBox);
										}
									}
									else if(iAddExtFibo == 0
											&& annotations.get(i) instanceof ExtFibo
											&& chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText() != null){

										ExtFibo exFibo = (ExtFibo)annotations.get(i);
										if( chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText().equals(exFibo.getId())){													
											exFibo.setSelected(true);													
											if(subplot.removeAnnotation(exFibo))
												subplot.addAnnotation(exFibo);
										}
										else if(exFibo.isSelected()){
											exFibo.setSelected(false);

											if(subplot.removeAnnotation(exFibo))
												subplot.addAnnotation(exFibo);
										}
									}
									else if(iAddRetFibo == 0
											&& annotations.get(i) instanceof RetFibo
											&& chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText() != null){

										RetFibo rFibo = (RetFibo)annotations.get(i);
										if( chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText().equals(rFibo.getId())){													
											rFibo.setSelected(true);													
											if(subplot.removeAnnotation(rFibo))
												subplot.addAnnotation(rFibo);
										}
										else if(rFibo.isSelected()){
											rFibo.setSelected(false);

											if(subplot.removeAnnotation(rFibo))
												subplot.addAnnotation(rFibo);
										}
									}
									else if(iAddHRetFibo == 0
											&& annotations.get(i) instanceof HRetFibo
											&& chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText() != null){

										HRetFibo hRFibo = (HRetFibo)annotations.get(i);
										if( chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText().equals(hRFibo.getId())){													
											hRFibo.setSelected(true);													
											if(subplot.removeAnnotation(hRFibo))
												subplot.addAnnotation(hRFibo);
										}
										else if(hRFibo.isSelected()){
											hRFibo.setSelected(false);

											if(subplot.removeAnnotation(hRFibo))
												subplot.addAnnotation(hRFibo);
										}
									}
									else if(iAddRiskGain == 0
											&& annotations.get(i) instanceof RiskGain
											&& chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText() != null){
										RiskGain rg = (RiskGain)annotations.get(i);
										if( chartPanel.getEntityForPoint(event.getTrigger().getX(), event.getTrigger().getY()).getToolTipText().equals(rg.getId())){

											rg.setSelected(true);

											if(subplot.removeAnnotation(rg))
												subplot.addAnnotation(rg);
										}
										else if(rg.isSelected()){
											rg.setSelected(false);

											if(subplot.removeAnnotation(rg))
												subplot.addAnnotation(rg);
										}
									}									
								}

								//Adicionar Ferramentas
								if(iAddArrow == 1){
									x1 = event.getTrigger().getX();
									y1 = event.getTrigger().getY();
									iAddArrow++;
								}
								else if(iAddArrow == 2){

									if(arrowTemporary != null){

										subplot.removeAnnotation(arrowTemporary);

										arrowTemporary = null;
									}

									Arrow arrow = new Arrow(											
											convertCoordinate(event.getTrigger().getX(), true, event),
											convertCoordinate(event.getTrigger().getY(), false, event),
											Math.toRadians(180));

									arrow.setDataX1(mapaData.get(simpleDateformat.format(convertCoordinate(event.getTrigger().getX(), true, event))));
									arrow.setiPlot(getIPlot(subplot));
									annotations.add(arrow);
									
									subplot.addAnnotation(arrow);

									setArrowButtonEnabled();
									iAddArrow = 0;	

								}

								if(iAddLine == 1){
									x1 = event.getTrigger().getX();
									y1 = event.getTrigger().getY();
									iAddLine++;
								}
								else if(iAddLine == 2){

									if(lineTemporary != null){
										subplot.removeAnnotation(lineTemporary);
										lineTemporary = null;
									}

									StraightLine line = new StraightLine(convertCoordinate(x1, true, event),
											convertCoordinate(y1, false, event),
											convertCoordinate(event.getTrigger().getX(), true, event),
											convertCoordinate(event.getTrigger().getY(), false, event));
									
									line.setDataX1(mapaData.get(simpleDateformat.format(convertCoordinate(x1, true, event))));
									line.setDataX2(mapaData.get(simpleDateformat.format(convertCoordinate(event.getTrigger().getX(), true, event))));
									line.setiPlot(getIPlot(subplot));
									
									annotations.add(line);
									subplot.addAnnotation(line);								

									setLineButtonEnabled();
									iAddLine = 0;
								}

								if(iAddParallelLines == 1){
									x1 = event.getTrigger().getX();
									y1 = event.getTrigger().getY();
									iAddParallelLines++;
								}
								else if(iAddParallelLines == 2){
									x2 = event.getTrigger().getX();
									y2 = event.getTrigger().getY();
									iAddParallelLines++;									
								}
								else if(iAddParallelLines == 3){

									if(parLineTemporary != null){
										subplot.removeAnnotation(parLineTemporary);
										parLineTemporary = null;
									}

									ParallelLines parLine = new ParallelLines(
											convertCoordinate(x1, true, event),
											convertCoordinate(y1, false, event),
											convertCoordinate(x2, true, event),
											convertCoordinate(y2, false, event),
											convertCoordinate(event.getTrigger().getX(), true, event),
											convertCoordinate(event.getTrigger().getY(), false, event));

									parLine.setDataX1(mapaData.get(simpleDateformat.format(convertCoordinate(x1, true, event))));
									parLine.setDataX2(mapaData.get(simpleDateformat.format(convertCoordinate(x2, true, event))));
									parLine.setDataX3(mapaData.get(simpleDateformat.format(convertCoordinate(event.getTrigger().getX(), true, event))));
									parLine.setiPlot(getIPlot(subplot));
									
									annotations.add(parLine);
									subplot.addAnnotation(parLine);
									setParallelLinesButtonEnabled();									
									iAddParallelLines = 0;
								}

								if(iAddHorizontalLine == 1){
									x1 = event.getTrigger().getX();
									y1 = event.getTrigger().getY();
									iAddHorizontalLine++;
								}
								else if(iAddHorizontalLine == 2){

									if(hLine != null){
										subplot.removeAnnotation(hLine);
										hLine = null;
									}

									HorizontalLine hLine = new HorizontalLine(											
											convertCoordinate(x1, true, event),
											convertCoordinate(event.getTrigger().getX(), true, event),
											convertCoordinate(event.getTrigger().getY(), false, event));

									hLine.setDataX1(mapaData.get(simpleDateformat.format(convertCoordinate(x1, true, event))));
									hLine.setDataX2(mapaData.get(simpleDateformat.format(convertCoordinate(event.getTrigger().getX(), true, event))));
									hLine.setiPlot(getIPlot(subplot));
									
									annotations.add(hLine);
									subplot.addAnnotation(hLine);
									setHorizontalLineEnabled();
									iAddHorizontalLine = 0;
								}

								if(iAddVerticalLine == 1){
									x1 = event.getTrigger().getX();
									y1 = event.getTrigger().getY();
									iAddVerticalLine++;
								}
								else if(iAddVerticalLine == 2){

									if(vLine != null){
										subplot.removeAnnotation(vLine);
										vLine = null;
									}

									VerticalLine line = new VerticalLine(
											convertCoordinate(y1, false, event),
											convertCoordinate(event.getTrigger().getX(), true, event),
											convertCoordinate(event.getTrigger().getY(), false, event));

									line.setDataX1(mapaData.get(simpleDateformat.format(convertCoordinate(x1, true, event))));
									line.setiPlot(getIPlot(subplot));
									
									annotations.add(line);
									subplot.addAnnotation(line);
									setVerticalLineEnabled();
									iAddVerticalLine = 0;
								}								

								if(iAddRect == 1){
									x1 = event.getTrigger().getX();
									y1 = event.getTrigger().getY();
									iAddRect++;
								}
								else if(iAddRect == 2){
									if(rectTemporary != null){
										subplot.removeAnnotation(rectTemporary);
										rectTemporary = null;
									}

									RectangleBox rectBox = new RectangleBox(											
											convertCoordinate(x1, true, event),
											convertCoordinate(y1, false, event),
											convertCoordinate(event.getTrigger().getX(), true, event),
											convertCoordinate(event.getTrigger().getY(), false, event));

									rectBox.setDataX1(mapaData.get(simpleDateformat.format(convertCoordinate(x1, true, event))));
									rectBox.setDataX2(mapaData.get(simpleDateformat.format(convertCoordinate(event.getTrigger().getX(), true, event))));
									rectBox.setiPlot(getIPlot(subplot));
									
									annotations.add(rectBox);
									subplot.addAnnotation(rectBox);
									setRectangleEnabled();
									iAddRect = 0;									
								}

								if(iAddEvol == 1){
									x1 = event.getTrigger().getX();
									y1 = event.getTrigger().getY();
									iAddEvol++;
								}
								else if(iAddEvol == 2){
									if(evolTemporary != null){
										subplot.removeAnnotation(evolTemporary);
										evolTemporary = null;
									}

									Evolution evolution = new Evolution(											
											convertCoordinate(x1, true, event),
											convertCoordinate(y1, false, event),
											convertCoordinate(event.getTrigger().getX(), true, event),
											convertCoordinate(event.getTrigger().getY(), false, event));

									evolution.setDataX1(mapaData.get(simpleDateformat.format(convertCoordinate(x1, true, event))));
									evolution.setDataX2(mapaData.get(simpleDateformat.format(convertCoordinate(event.getTrigger().getX(), true, event))));
									evolution.setiPlot(getIPlot(subplot));
									
									annotations.add(evolution);
									subplot.addAnnotation(evolution);
									setEvolutionButtonEnabled();
									iAddEvol = 0;								
								}

								if(iAddText == 1){

									String text = JOptionPane.showInputDialog(null,I18n.getMessage("dh"),I18n.getMessage("di"),JOptionPane.INFORMATION_MESSAGE);

									TextBox texto = new TextBox(text,
											convertCoordinate(event.getTrigger().getX(), true, event),
											convertCoordinate(event.getTrigger().getY(), false, event));

									texto.setDataX1(mapaData.get(simpleDateformat.format(convertCoordinate(event.getTrigger().getX(), true, event))));
									texto.setiPlot(getIPlot(subplot));
									
									annotations.add(texto);
									subplot.addAnnotation(texto);

									setTextButtonEnabled();
									iAddText = 0;
								}

								if(iAddExtFibo == 1){
									x1 = event.getTrigger().getX();
									y1 = event.getTrigger().getY();
									iAddExtFibo++;
								}
								else if(iAddExtFibo == 2){
									if(extFiboTemporary != null){
										subplot.removeAnnotation(extFiboTemporary);
										extFiboTemporary = null;
									}

									ExtFibo extFibo = new ExtFibo(											
											convertCoordinate(x1, true, event),
											convertCoordinate(y1, false, event),
											convertCoordinate(event.getTrigger().getX(), true, event),
											convertCoordinate(event.getTrigger().getY(), false, event));

									extFibo.setDataX1(mapaData.get(simpleDateformat.format(convertCoordinate(x1, true, event))));
									extFibo.setDataX2(mapaData.get(simpleDateformat.format(convertCoordinate(event.getTrigger().getX(), true, event))));
									extFibo.setiPlot(getIPlot(subplot));
									
									annotations.add(extFibo);
									subplot.addAnnotation(extFibo);
									setExtFiboEnabled();
									iAddExtFibo = 0;								
								}


								if(iAddRetFibo == 1){
									x1 = event.getTrigger().getX();
									y1 = event.getTrigger().getY();
									iAddRetFibo++;
								}
								else if(iAddRetFibo == 2){
									if(retFiboTemporary != null){
										subplot.removeAnnotation(retFiboTemporary);
										retFiboTemporary = null;
									}

									RetFibo retFibo = new RetFibo(											
											convertCoordinate(x1, true, event),
											convertCoordinate(y1, false, event),
											convertCoordinate(event.getTrigger().getX(), true, event),
											convertCoordinate(event.getTrigger().getY(), false, event));

									retFibo.setDataX1(mapaData.get(simpleDateformat.format(convertCoordinate(x1, true, event))));
									retFibo.setDataX2(mapaData.get(simpleDateformat.format(convertCoordinate(event.getTrigger().getX(), true, event))));
									retFibo.setiPlot(getIPlot(subplot));
									
									annotations.add(retFibo);
									subplot.addAnnotation(retFibo);
									setVFiboRetractionEnabled();
									iAddRetFibo = 0;								
								}

								if(iAddHRetFibo == 1){
									x1 = event.getTrigger().getX();
									y1 = event.getTrigger().getY();
									iAddHRetFibo++;
								}
								else if(iAddHRetFibo == 2){
									if(hRetFiboTemporary != null){
										subplot.removeAnnotation(hRetFiboTemporary);
										hRetFiboTemporary = null;
									}

									HRetFibo hRetFibo = new HRetFibo(											
											convertCoordinate(x1, true, event),
											convertCoordinate(y1, false, event),
											convertCoordinate(event.getTrigger().getX(), true, event),
											convertCoordinate(event.getTrigger().getY(), false, event));

									hRetFibo.setDataX1(mapaData.get(simpleDateformat.format(convertCoordinate(x1, true, event))));
									hRetFibo.setDataX2(mapaData.get(simpleDateformat.format(convertCoordinate(event.getTrigger().getX(), true, event))));
									hRetFibo.setiPlot(getIPlot(subplot));
									
									annotations.add(hRetFibo);
									subplot.addAnnotation(hRetFibo);
									setHFiboRetractionEnabled();
									iAddHRetFibo = 0;								
								}

								if(iAddRiskGain == 1){
									x1 = event.getTrigger().getX();
									y1 = event.getTrigger().getY();
									iAddRiskGain++;
								}
								else if(iAddRiskGain == 2){
									x2 = event.getTrigger().getX();
									y2 = event.getTrigger().getY();
									iAddRiskGain++;									
								}
								else if(iAddRiskGain == 3){

									if(riskTemporary != null){
										subplot.removeAnnotation(riskTemporary);
										riskTemporary = null;
									}

									RiskGain riskGain = new RiskGain(											
											convertCoordinate(x1, true, event),
											convertCoordinate(y1, false, event),
											convertCoordinate(y2, false, event),
											convertCoordinate(event.getTrigger().getY(), false, event));

									riskGain.setDataX1(mapaData.get(simpleDateformat.format(convertCoordinate(x1, true, event))));
									riskGain.setiPlot(getIPlot(subplot));
									
									annotations.add(riskGain);
									subplot.addAnnotation(riskGain);
									setRiskGainEnabled();									
									iAddRiskGain = 0;
								}

							} 
							index++;
						} 									        			        
					}

				} catch(Exception e){
					e.printStackTrace();
				}

			}					
		});	

		this.add(chartPanel);
		this.setBounds(0,0, x, y);		
		this.setVisible(true);

		media = new MediaMovel();
		bollinger = new Bollinger();
		macd = new Macd();
		ifr = new Ifr();
		obv = new Obv();
		estocastico = new Estocastico();
		dmi = new Dmi();
		mmNormal = new MMNormal();

	}	
	
	private int getIPlot(XYPlot subplot){
		
		if(subplot.equals(subplotCandle))
			return 0;
			
		if(subplot.equals(subplotMacd))
			return 1;
			
		if(subplot.equals(subplotIfr))
			return 2;
			
		if(subplot.equals(subplotObv))
			return 3;
			
		if(subplot.equals(subplotVolume))
			return 4;
			
		if(subplot.equals(subplotEst))
			return 5;
		
		if(subplot.equals(subplotDMI))
			return 6;
		
		if(subplot.equals(subplotMMNormal))
			return 7;
		
		return 0;

	}

	@SuppressWarnings("unchecked")
	private double convertCoordinate(double value, boolean xCoordenate, ChartMouseEvent event){

		try{
			Point mousePoint = new Point(event.getTrigger().getX(), event.getTrigger().getY()); 

			ChartRenderingInfo chartInfo = chartPanel.getChartRenderingInfo(); 
			Point2D java2DPoint = chartPanel.translateScreenToJava2D(mousePoint); 
			PlotRenderingInfo plotInfo = chartInfo.getPlotInfo(); 
			int subplotIndex = plotInfo.getSubplotIndex(java2DPoint);

			if (subplotIndex >= 0) 
			{ 						
				Rectangle2D dataAreaX = chartPanel.getScreenDataArea(event.getTrigger().getX(), event.getTrigger().getY());
				@SuppressWarnings("unused")
				Rectangle2D dataArea = plotInfo.getDataArea();
				List subplotsList = plot.getSubplots(); 
				Iterator iterator = subplotsList.iterator(); 
				int index = 0; 

				while (iterator.hasNext())   
				{ 
					XYPlot subplot = (XYPlot) iterator.next(); 

					if (subplotIndex == index) 
					{ 
						Rectangle2D dataAreaY = plotInfo.getSubplotInfo(index).getDataArea();

						if(xCoordenate){
							//return subplot.getDomainAxis().valueToJava2D(subplot.getDomainAxis().java2DToValue(value,dataAreaX,subplot.getDomainAxisEdge()), dataArea, subplot.getDomainAxisEdge());
							return subplot.getDomainAxis().java2DToValue(value,dataAreaX,subplot.getDomainAxisEdge());
						}
						else{						
							return subplot.getRangeAxis().java2DToValue(value,dataAreaY,subplot.getRangeAxisEdge());
						}							
					}
					index++;
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	private void keyPressedEvent(KeyEvent e){

		try{
			PlotRenderingInfo plotInfo = chartPanel.getChartRenderingInfo().getPlotInfo(); 

			int subplotCount = plotInfo.getSubplotCount(); 

			if (subplotCount >= 0) 
			{
				List subplotsList = plot.getSubplots(); 
				Iterator iterator = subplotsList.iterator(); 

				while (iterator.hasNext())   
				{
					XYPlot subplot = (XYPlot) iterator.next(); 

					if(e.getKeyCode() == 127)//Del
					{
						for(int i = 0; i < annotations.size();i++){

							if(annotations.get(i) instanceof StraightLine){

								StraightLine line = (StraightLine)annotations.get(i);

								if(line.isSelected()){
									subplot.removeAnnotation(line);
								}									
							}
							else if(annotations.get(i) instanceof ParallelLines){

								ParallelLines pLine = (ParallelLines)annotations.get(i);

								if(pLine.isSelected()){
									subplot.removeAnnotation(pLine);
								}									
							}
							else if(annotations.get(i) instanceof HorizontalLine){

								HorizontalLine hLine = (HorizontalLine)annotations.get(i);

								if(hLine.isSelected()){
									subplot.removeAnnotation(hLine);
								}									
							}
							else if(annotations.get(i) instanceof VerticalLine){

								VerticalLine vLine = (VerticalLine)annotations.get(i);

								if(vLine.isSelected()){
									subplot.removeAnnotation(vLine);
								}									
							}
							else if(annotations.get(i) instanceof RectangleBox){

								RectangleBox box = (RectangleBox)annotations.get(i);

								if(box.isSelected()){
									subplot.removeAnnotation(box);
								}									
							}
							else if(annotations.get(i) instanceof Evolution){

								Evolution evol = (Evolution)annotations.get(i);

								if(evol.isSelected()){
									subplot.removeAnnotation(evol);
								}									
							}
							else if(annotations.get(i) instanceof ExtFibo){

								ExtFibo exFibo = (ExtFibo)annotations.get(i);

								if(exFibo.isSelected()){
									subplot.removeAnnotation(exFibo);
								}									
							}
							else if(annotations.get(i) instanceof RetFibo){

								RetFibo retFibo = (RetFibo)annotations.get(i);

								if(retFibo.isSelected()){
									subplot.removeAnnotation(retFibo);
								}									
							}
							else if(annotations.get(i) instanceof HRetFibo){

								HRetFibo hFibo = (HRetFibo)annotations.get(i);

								if(hFibo.isSelected()){
									subplot.removeAnnotation(hFibo);
								}									
							}
							else if(annotations.get(i) instanceof Arrow){

								Arrow arrow = (Arrow)annotations.get(i);

								if(arrow.isSelected()){
									subplot.removeAnnotation(arrow);
								}									
							}
							else if(annotations.get(i) instanceof TextBox){

								TextBox text = (TextBox)annotations.get(i);

								if(text.isSelected()){
									subplot.removeAnnotation(text);
								}									
							}
							else if(annotations.get(i) instanceof RiskGain){

								RiskGain riskGain = (RiskGain)annotations.get(i);

								if(riskGain.isSelected()){
									subplot.removeAnnotation(riskGain);
								}									
							}
						}	
					}

				} 								        			        
			}
		} catch(Exception ex){
			ex.printStackTrace();
		}		
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	public void reconfiguration(boolean rAxis, boolean candle) throws PeriodException{

		mapaData.clear();
		mapaDataIndex.clear();

		switch (mainScreen.PERIOD) {
		case 'D':
			this.quotes = historico.getQuoteDay();
			break;
		case 'W':
			this.quotes = historico.getQuoteWeek();
			break;
		case 'M':
			this.quotes = historico.getQuoteMonth();
			break;
		}

		chart.setTitle("["+mainScreen.PERIOD+"] " + historico.getCodeStock() + " - " + historico.getStockName());

		calcularDataset(historico.getCodeStock());
		subplotCandle.setDataset(dataset);

		if(candle){
			CandlestickRenderer renderer = new CandlestickRenderer();		
			renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
			renderer.setSeriesPaint(0, Color.BLACK);
			renderer.setDownPaint(Color.BLACK);
			renderer.setUpPaint(Color.WHITE);
			renderer.setDrawVolume(false);

			if(rAxis){		
				NumberAxis  rangeAxis        = new NumberAxis(I18n.getMessage("dg"));					
				rangeAxis.setAutoRangeIncludesZero(false);				
				subplotCandle.setRangeAxis(rangeAxis);				
			}
			else{
				LogarithmicAxis rangeAxis = new LogarithmicAxis(I18n.getMessage("dg"));
				rangeAxis.setAutoRangeIncludesZero(false);
				subplotCandle.setRangeAxis(rangeAxis);
			}

			subplotCandle.setRenderer(renderer);
		}
		else{
			XYItemRenderer renderer = new StandardXYItemRenderer();
			renderer.setPaint(Color.BLACK);
			if(rAxis){		
				NumberAxis  rangeAxis        = new NumberAxis(I18n.getMessage("dg"));			
				rangeAxis.setAutoRangeIncludesZero(false);				
				subplotCandle.setRangeAxis(rangeAxis);
			}
			else{
				LogarithmicAxis rangeAxis = new LogarithmicAxis(I18n.getMessage("dg"));
				rangeAxis.setAutoRangeIncludesZero(false);
				subplotCandle.setRangeAxis(rangeAxis);
			}
			subplotCandle.setRenderer(renderer);
		}

		for(int i = 0; i < annotations.size();i++){

			if(annotations.get(i) instanceof StraightLine){
				StraightLine line = (StraightLine)this.annotations.get(i);				
				line.changeDate(historico.convertDate(mainScreen.PERIOD,line.getDataX1()),
						historico.convertDate(mainScreen.PERIOD,line.getDataX2()));
			}	
			else if(annotations.get(i) instanceof Arrow){

				Arrow arrow = (Arrow)annotations.get(i);
				arrow.changeDate(historico.convertDate(mainScreen.PERIOD,arrow.getDataX1()));

			} else if(annotations.get(i) instanceof ParallelLines){

				ParallelLines pLine = (ParallelLines)annotations.get(i);				
				pLine.changeDate(historico.convertDate(mainScreen.PERIOD,pLine.getDataX1()),
						historico.convertDate(mainScreen.PERIOD,pLine.getDataX2()),
						historico.convertDate(mainScreen.PERIOD,pLine.getDataX3()));

			}else if(annotations.get(i) instanceof HorizontalLine){

				HorizontalLine hLine = (HorizontalLine)annotations.get(i);				
				hLine.changeDate(historico.convertDate(mainScreen.PERIOD,hLine.getDataX1()),
						historico.convertDate(mainScreen.PERIOD,hLine.getDataX2()));

			}else if(annotations.get(i) instanceof VerticalLine){

				VerticalLine vLine = (VerticalLine)annotations.get(i);
				vLine.changeDate(historico.convertDate(mainScreen.PERIOD,vLine.getDataX1()));

			}else if(annotations.get(i) instanceof RectangleBox){

				RectangleBox rBox = (RectangleBox)annotations.get(i);
				rBox.changeDate(historico.convertDate(mainScreen.PERIOD,rBox.getDataX1()),
						historico.convertDate(mainScreen.PERIOD,rBox.getDataX2()));

			}else if(annotations.get(i) instanceof Evolution){

				Evolution evolution = (Evolution)annotations.get(i);
				evolution.changeDate(historico.convertDate(mainScreen.PERIOD,evolution.getDataX1()),
						historico.convertDate(mainScreen.PERIOD,evolution.getDataX2()));

			}else if(annotations.get(i) instanceof TextBox){

				TextBox tBox = (TextBox)annotations.get(i);
				tBox.changeDate(historico.convertDate(mainScreen.PERIOD,tBox.getDataX1()));

			}else if(annotations.get(i) instanceof ExtFibo){

				ExtFibo exFibo = (ExtFibo)annotations.get(i);
				exFibo.changeDate(historico.convertDate(mainScreen.PERIOD,exFibo.getDataX1()),
						historico.convertDate(mainScreen.PERIOD,exFibo.getDataX2()));

			}else if(annotations.get(i) instanceof RetFibo){

				RetFibo rFibo = (RetFibo)annotations.get(i);
				rFibo.changeDate(historico.convertDate(mainScreen.PERIOD,rFibo.getDataX1()),
						historico.convertDate(mainScreen.PERIOD,rFibo.getDataX2()));

			}else if(annotations.get(i) instanceof HRetFibo){

				HRetFibo hRFibo = (HRetFibo)annotations.get(i);				
				hRFibo.changeDate(historico.convertDate(mainScreen.PERIOD,hRFibo.getDataX1()),
						historico.convertDate(mainScreen.PERIOD,hRFibo.getDataX2()));

			}else if(annotations.get(i) instanceof RiskGain ){

				RiskGain rg = (RiskGain)annotations.get(i);
				rg.changeDate(historico.convertDate(mainScreen.PERIOD,rg.getDataX1()));
			}
		}
	}

	public void addLine(){
		this.iAddLine = 1;		
	}

	public void setLineButtonEnabled(){
		this.mainScreen.setLineButtonEnabled();	
	}

	public void addParallelLines(){
		this.iAddParallelLines = 1;		
	}

	public void setParallelLinesButtonEnabled(){
		this.mainScreen.setParallelLinesButtonEnabled();	
	}

	public void addHorizontalLine(){
		this.iAddHorizontalLine = 1;
	}

	public void setHorizontalLineEnabled(){
		this.mainScreen.setHorizontalButtonEnabled();	
	}

	public void addVerticalLine(){
		this.iAddVerticalLine = 1;
	}

	public void setVerticalLineEnabled(){
		this.mainScreen.setVerticalButtonEnabled();	
	}

	public void addRectangle(){
		this.iAddRect = 1;
	}

	public void setRectangleEnabled(){
		this.mainScreen.setRectangleEnabled();	
	}

	public void addEvolutionLine(){
		this.iAddEvol = 1;
	}

	public void setEvolutionButtonEnabled(){
		this.mainScreen.setEvolutionButtonEnabled();	
	}

	public void addExtFibo(){
		this.iAddExtFibo = 1;
	}

	public void setExtFiboEnabled(){
		this.mainScreen.setExtFiboEnabled();
	}

	public void addVFiboRetraction(){
		this.iAddRetFibo = 1;
	}

	public void setVFiboRetractionEnabled(){
		this.mainScreen.setVFiboRetractionEnabled();
	}

	public void addHFiboRetraction(){
		this.iAddHRetFibo = 1;
	}

	public void setHFiboRetractionEnabled(){
		this.mainScreen.setHFiboRetractionEnabled();
	}

	public void addArrow(){
		this.iAddArrow = 1;
	}

	public void setArrowButtonEnabled(){
		this.mainScreen.setArrowButtonEnabled();
	}

	public void addText(){
		this.iAddText = 1;
	}

	public void setTextButtonEnabled(){
		this.mainScreen.setTextButtonEnabled();
	}

	public void addRiskGain(){
		this.iAddRiskGain = 1;
	}

	public void setRiskGainEnabled(){
		this.mainScreen.setRiskGainEnabled();
	}	

	private void setInfoField(String date, String value){		    
		this.mainScreen.setInfoField(date, value);
	}

	public void setCrossHair(boolean value){		
		chartPanel.setVerticalAxisTrace(value);
		chartPanel.setHorizontalAxisTrace(value);

		//FIXME problema com repaint;
	}

	private void setDataScreen(int index){
		float variacao = 0;
		if(dataScreen != null){

			if(index > 0){
				variacao = ((quotes[index].getClose() / quotes[index - 1].getClose()) - 1F)*100F;
			}
			dataScreen.setData(quotes[index], variacao);

			dataScreen.repaint();			
		}

	}

	public void setInfoScreen(boolean isVisible){

		if(isVisible){

			dataScreen = new DataScreen(quotes[0], 1);

			mainScreen.add(dataScreen);
			dataScreen.repaint();
		}
		else{
			if(dataScreen != null){

				mainScreen.remove(dataScreen);
				this.repaint();
				dataScreen = null;
			}			
		}    	
	}

	public void doSaveAs(){

		try {
			chartPanel.doSaveAs();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void calcularDataset(String acao){

		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");

		Date[] date = new Date[quotes.length];
		double[] high = new double[quotes.length];
		double[] low = new double[quotes.length];
		double[] open = new double[quotes.length];
		double[] close = new double[quotes.length];
		double[] volume = new double[quotes.length];

		Calendar gambiarra = Calendar.getInstance();
		gambiarra.set(1900, 0, 1); 

		for (int i = 0; i < quotes.length; i++) {

			date[i] = gambiarra.getTime();

			gambiarra.add(Calendar.DATE, 1);        	
			date[i].setHours(12);
			high[i] = (Float) quotes[i].getHigh();
			low[i] = (Float) quotes[i].getLow();
			open[i] = (Float) quotes[i].getOpen();
			close[i] = (Float) quotes[i].getClose();
			volume[i] = (Long)quotes[i].getVolume();   

			mapaData.put(simpleDateformat.format(date[i]),simpleDateformat.format(quotes[i].getDate()));
			mapaDataIndex.put(simpleDateformat.format(date[i]), i);        	
		}

		this.dataset = new DefaultHighLowDataset(acao, date, high, low, open, close, volume);
	}

	private XYDataset getXYDataset(DataIndicator[] source, String title){

		XYSeriesCollection result = new XYSeriesCollection();

		XYSeries s = new XYSeries(title);

		for (int i = 0; i < source.length; i++) {

			s.add(dataset.getXValue(0, i),source[i].getClose());
		}

		result.addSeries(s);

		return result;
	}

	private DataIndicator[] getDado(boolean isVolIndicator){
		
		DataIndicator[] dado = new DataIndicator[quotes.length];

		for (int i = 0; i < quotes.length; i++) {
			dado[i] = new DataIndicator();
			dado[i].setIndex(quotes[i].getDate().getTime());

			if(isVolIndicator){
				dado[i].setClose(quotes[i].getVolume());
			}
			else{
				dado[i].setClose(quotes[i].getClose());
			}

			dado[i].setOpen(quotes[i].getOpen());
			dado[i].setHigh(quotes[i].getHigh());
			dado[i].setLow(quotes[i].getLow());
			dado[i].setVolume(quotes[i].getVolume());
		}

		return dado;
	}

	public void setMM(int qtmme, int controle, char category){

		switch(category){
		case 'a':
			subplotCandle.setDataset(controle, getXYDataset(media.calcularMMS(getDado(false), qtmme),I18n.getMessage("dj") + qtmme));
			break;
		case 'e':
			subplotCandle.setDataset(controle, getXYDataset(media.calcularMME(getDado(false), qtmme),I18n.getMessage("dk") + qtmme));
			break;		
		} 

		XYItemRenderer rendererMme = new StandardXYItemRenderer();

		switch(controle){
		case 1:
			rendererMme.setSeriesPaint(0,Color.BLUE);
			break;
		case 2:
			rendererMme.setSeriesPaint(0,Color.RED);
			break;
		case 3:
			rendererMme.setSeriesPaint(0,Color.GREEN);
			break;
		case 4:
			rendererMme.setSeriesPaint(0,Color.YELLOW);
			break;
		case 5:
			rendererMme.setSeriesPaint(0,Color.DARK_GRAY);
			break;

		}

		subplotCandle.setRenderer(controle,rendererMme);

	}

	public void removeMM(int controle){
		try{
			subplotCandle.setDataset(controle, null); 
			subplotCandle.setRenderer(controle, null);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	public void setBollingerBands(int qtBB, int desvio){

		DataIndicator[][] bb = bollinger.calcBollingerBands(getDado(false), qtBB, desvio);

		XYDataset bandaMedia = getXYDataset(bb[0],I18n.getMessage("dl"));
		XYDataset bandaS = getXYDataset(bb[2],I18n.getMessage("dm"));
		XYDataset bandaI = getXYDataset(bb[1],I18n.getMessage("dn"));

		subplotCandle.setDataset(6,bandaMedia);
		XYItemRenderer rendererBBMedia = new StandardXYItemRenderer();
		rendererBBMedia.setSeriesPaint(0,Color.LIGHT_GRAY);
		subplotCandle.setRenderer(6,rendererBBMedia);

		subplotCandle.setDataset(7,bandaS);
		XYItemRenderer rendererBBS = new StandardXYItemRenderer();
		rendererBBS.setSeriesPaint(0,Color.ORANGE);
		subplotCandle.setRenderer(7,rendererBBS);

		subplotCandle.setDataset(8,bandaI);
		XYItemRenderer rendererBBI = new StandardXYItemRenderer();
		rendererBBI.setSeriesPaint(0,Color.ORANGE);
		subplotCandle.setRenderer(8,rendererBBI);

	}

	public void removeBollingerBands(){
		try{
			subplotCandle.setDataset(6, null);
			subplotCandle.setRenderer(6, null);
			subplotCandle.setDataset(7, null);
			subplotCandle.setRenderer(7, null);
			subplotCandle.setDataset(8, null); 
			subplotCandle.setRenderer(8, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setIFR(int qtifr, float sc, float sv){

		removeIFR();

		DataIndicator[] ind = ifr.calcularIFR(getDado(false), qtifr);

		XYDataset dataIfr = getXYDataset(ind,I18n.getMessage("bx") + " " + qtifr);

		XYItemRenderer rendererIfr = new StandardXYItemRenderer();
		rendererIfr.setSeriesPaint(0,Color.BLACK);
		NumberAxis rangeAxis1 = new NumberAxis(I18n.getMessage("bx"));
		subplotIfr = new XYPlot(dataIfr, null, rangeAxis1, rendererIfr);

		if(sc > 0){
			DataIndicator[] source = new DataIndicator[getDado(false).length];

			for(int i = 0; i< source.length;i++){
				source[i] = new DataIndicator();
				source[i].setIndex(i);
				source[i].setClose(sc);
			}

			XYItemRenderer rendererMme = new StandardXYItemRenderer();
			rendererMme.setSeriesPaint(0,Color.DARK_GRAY);
			subplotIfr.setRenderer(1,rendererMme);
			subplotIfr.setDataset(1, getXYDataset(source,""));
		}

		if(sv > 0){
			DataIndicator[] source = new DataIndicator[getDado(false).length];

			for(int i = 0; i< source.length;i++){
				source[i] = new DataIndicator();
				source[i].setIndex(i);
				source[i].setClose(sv);
			}

			XYItemRenderer rendererMme = new StandardXYItemRenderer();
			rendererMme.setSeriesPaint(0,Color.DARK_GRAY);
			subplotIfr.setRenderer(2,rendererMme);
			subplotIfr.setDataset(2, getXYDataset(source,""));
		}

		plot.add(subplotIfr, 2);

	}

	public void setMMIFR(int qtmme,int qtifr, int controle, char category){

		switch(category){
		case 'a':
			subplotIfr.setDataset(controle, getXYDataset(media.calcularMMS(ifr.calcularIFR(getDado(false), qtifr), qtmme),
					I18n.getMessage("do")+qtmme));
			break;
		case 'e':
			subplotIfr.setDataset(controle, getXYDataset(media.calcularMME(ifr.calcularIFR(getDado(false), qtifr), qtmme),
					I18n.getMessage("dp")+qtmme));
			break;		
		} 

		XYItemRenderer rendererMme = new StandardXYItemRenderer();

		switch(controle){
		case 1:
			rendererMme.setSeriesPaint(0,Color.BLACK);
			break;
		case 2:
			rendererMme.setSeriesPaint(0,Color.BLACK);
			break;
		case 3:
			rendererMme.setSeriesPaint(0,Color.BLUE);
			break;
		case 4:
			rendererMme.setSeriesPaint(0,Color.RED);
			break;
		default:
			rendererMme.setSeriesPaint(0,Color.GREEN);
			break;
		}

		subplotIfr.setRenderer(controle,rendererMme);
	}

	public void removeMMIfr(int controle){
		try{
			if(subplotIfr != null){
				subplotIfr.setDataset(controle, null); 
				subplotIfr.setRenderer(controle, null);
			}

		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	public void removeIFR(){
		try{
			if(subplotIfr != null)
				plot.remove(subplotIfr);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setMACD( int qtmacdlongo, int qtmacdcurto, int qtmacdsinal, boolean isCrossMacd){

		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");

		removeMACD();

		DataIndicator[][] m = macd.calculateMACD(getDado(false), qtmacdlongo, qtmacdcurto, qtmacdsinal);

		if(isCrossMacd){

			TimePeriodValues s = new TimePeriodValues(I18n.getMessage("br"));

			for (int i = 0; i < m[2].length; i++) {

				try {				
					Iterator<String> iterator = mapaData.keySet().iterator();

					while(iterator. hasNext()){
						String key = (String)iterator.next();						
						if(mapaData.get(key).equals(simpleDateformat.format(m[2][i].getIndex()))){			            	
							Day day = new Day(simpleDateformat.parse(key));	

							s.add(new SimpleTimePeriod(day.getStart(), day.getEnd()), m[2][i].getClose());
							break;
						}
					}			

				} catch (ParseException e) {

					e.printStackTrace();
				}   			
			}

			TimePeriodValuesCollection data = new TimePeriodValuesCollection();
			data.addSeries(s);

			XYBarRenderer renderer = new XYBarRenderer(0.33);
			renderer.setSeriesPaint(0, Color.BLACK);
			renderer.setShadowVisible(false);    

			NumberAxis rangeAxis = new NumberAxis(I18n.getMessage("br"));
			subplotMacd = new XYPlot(data, null, rangeAxis, renderer);
		}
		else{
			XYDataset data1 = getXYDataset(m[0],I18n.getMessage("br"));
			XYDataset data2 = getXYDataset(m[1],I18n.getMessage("dq"));

			XYItemRenderer rendererMacd = new StandardXYItemRenderer();
			rendererMacd.setSeriesPaint(0,Color.RED);
			NumberAxis rangeAxis1 = new NumberAxis(I18n.getMessage("br"));     
			subplotMacd = new XYPlot(data1, null, rangeAxis1, rendererMacd);
			subplotMacd.setDataset(1, data2); 
			XYItemRenderer rendererSinal = new StandardXYItemRenderer();
			rendererSinal.setSeriesPaint(0,Color.BLUE);
			subplotMacd.setRenderer(1, rendererSinal);
		}

		plot.add(subplotMacd, 2);
	}

	public void removeMACD(){
		try{
			if(subplotMacd != null)
				plot.remove(subplotMacd);
		}catch(Exception e){
			e.printStackTrace();
		}				
	}

	public void setEstocastico(int periodoK,int periodoD, int periodoLento, boolean isLento, boolean isCrossStoch, float sc, float sv){

		removeEstocastico();

		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");

		DataIndicator[][] ind = estocastico.calcularEstocastico(getDado(false), periodoK, periodoD, periodoLento, isLento);
		
		if(isCrossStoch){
			TimePeriodValues s = new TimePeriodValues(I18n.getMessage("dr"));

			for (int i = 0; i < ind[2].length; i++) {

				try {				
					Iterator<String> iterator = mapaData.keySet().iterator();

					while(iterator. hasNext()){
						String key = (String)iterator.next();	
						if(mapaData.get(key).equals(simpleDateformat.format(ind[2][i].getIndex()))){			            	
							Day day = new Day(simpleDateformat.parse(key));
							s.add(new SimpleTimePeriod(day.getStart(), day.getEnd()), ind[2][i].getClose());
							break;
						}
					}			

				} catch (ParseException e) {					
					e.printStackTrace();
				}
			}

			TimePeriodValuesCollection data = new TimePeriodValuesCollection();
			data.addSeries(s);

			XYBarRenderer renderer = new XYBarRenderer(0.33);
			renderer.setSeriesPaint(0, Color.BLACK);

			renderer.setShadowVisible(false);

			NumberAxis rangeAxis = new NumberAxis(I18n.getMessage("dr"));
			subplotEst = new XYPlot(data, null, rangeAxis, renderer);

			subplotEst.setRenderer(4,renderer);
		} 
		else{
			XYDataset data1 = getXYDataset(ind[0],"%K");
			XYDataset data2 = getXYDataset(ind[1],"%D");

			XYItemRenderer rendererEst1 = new StandardXYItemRenderer();
			rendererEst1.setSeriesPaint(0,Color.BLUE);
			NumberAxis rangeAxis1 = new NumberAxis(I18n.getMessage("dr"));
			subplotEst = new XYPlot(data1, null, rangeAxis1, rendererEst1);

			subplotEst.setDataset(1, data2); 
			XYItemRenderer rendererEst2 = new StandardXYItemRenderer();
			rendererEst2.setSeriesPaint(0,Color.RED);
			subplotEst.setRenderer(1,rendererEst2);

			if(sc > 0){
				DataIndicator[] source = new DataIndicator[getDado(false).length];

				for(int i = 0; i< source.length;i++){
					source[i] = new DataIndicator();
					source[i].setIndex(i);
					source[i].setClose(sc);
				}

				XYItemRenderer rendererMme = new StandardXYItemRenderer();
				rendererMme.setSeriesPaint(0,Color.DARK_GRAY);
				subplotEst.setRenderer(2,rendererMme);
				subplotEst.setDataset(2, getXYDataset(source,""));
			}

			if(sv > 0){
				DataIndicator[] source = new DataIndicator[getDado(false).length];

				for(int i = 0; i< source.length;i++){
					source[i] = new DataIndicator();
					source[i].setIndex(i);
					source[i].setClose(sv);
				}

				XYItemRenderer rendererMme = new StandardXYItemRenderer();
				rendererMme.setSeriesPaint(0,Color.DARK_GRAY);
				subplotEst.setRenderer(3,rendererMme);
				subplotEst.setDataset(3, getXYDataset(source,""));
			}        	
		}

		plot.add(subplotEst, 2);
	}

	public void removeEstocastico(){
		try{
			if(subplotEst!=null)
				plot.remove(subplotEst);		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setDmi(int qtPeriod,int qtAvg){
		
		removeDMI();

		DataIndicator[][] ind = dmi.calcularDMI(getDado(false), qtPeriod, qtAvg);
		
		XYDataset data1 = getXYDataset(ind[0],I18n.getMessage("ds"));
		XYDataset data2 = getXYDataset(ind[1],I18n.getMessage("dt"));
		XYDataset data3 = getXYDataset(ind[2],I18n.getMessage("du"));
		
		XYItemRenderer rendererDmi1 = new StandardXYItemRenderer();
		rendererDmi1.setSeriesPaint(0,Color.BLACK);
		NumberAxis rangeAxis1 = new NumberAxis(I18n.getMessage("ds"));
		subplotDMI = new XYPlot(data1, null, rangeAxis1, rendererDmi1);

		subplotDMI.setDataset(1, data2); 
		XYItemRenderer rendererDmi2 = new StandardXYItemRenderer();
		rendererDmi2.setSeriesPaint(0,Color.BLUE);
		subplotDMI.setRenderer(1,rendererDmi2);
		
		subplotDMI.setDataset(2, data3); 
		XYItemRenderer rendererDmi3 = new StandardXYItemRenderer();
		rendererDmi3.setSeriesPaint(0,Color.RED);
		subplotDMI.setRenderer(2,rendererDmi3);
		
		plot.add(subplotDMI, 2);
	}
	
	public void removeDMI(){
		
		try{
			if(subplotDMI!=null)
				plot.remove(subplotDMI);		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setMMNormal(int qtShortMM,int qtMediumMM, int qtLongMM){
		
		removeMMNormal();

		DataIndicator[][] ind = mmNormal.calcularMMNormal(getDado(false), qtShortMM, qtMediumMM, qtLongMM);
		
		XYDataset data1 = getXYDataset(ind[0],I18n.getMessage("dj")+qtShortMM);
		XYDataset data2 = getXYDataset(ind[1],I18n.getMessage("dj")+qtMediumMM);
		XYDataset data3 = getXYDataset(ind[2],I18n.getMessage("dj")+qtLongMM);
		
		XYItemRenderer rendererEst1 = new StandardXYItemRenderer();
		rendererEst1.setSeriesPaint(0,Color.BLUE);
		NumberAxis rangeAxis1 = new NumberAxis(I18n.getMessage("cb"));
		subplotMMNormal = new XYPlot(data1, null, rangeAxis1, rendererEst1);

		subplotMMNormal.setDataset(1, data2); 
		XYItemRenderer rendererEst2 = new StandardXYItemRenderer();
		rendererEst2.setSeriesPaint(0,Color.BLACK);
		subplotMMNormal.setRenderer(1,rendererEst2);
		
		subplotMMNormal.setDataset(2, data3); 
		XYItemRenderer rendererEst3 = new StandardXYItemRenderer();
		rendererEst3.setSeriesPaint(0,Color.RED);
		subplotMMNormal.setRenderer(2,rendererEst3);
		
		plot.add(subplotMMNormal, 2);
	}
	
	public void removeMMNormal(){
		try{
			if(subplotMMNormal!=null)
				plot.remove(subplotMMNormal);		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setOBV(){
		DataIndicator[] ind = obv.calcularOBV(getDado(false));
		
		XYDataset dataObv = getXYDataset(ind,I18n.getMessage("bz"));

		XYItemRenderer rendererObv = new StandardXYItemRenderer();
		rendererObv.setSeriesPaint(0,Color.BLACK);
		NumberAxis rangeAxis1 = new NumberAxis(I18n.getMessage("bz"));
		subplotObv = new XYPlot(dataObv, null, rangeAxis1, rendererObv);

		plot.add(subplotObv, 2);	
		
	}

	public void setMMOBV(int qtmm,int controle, char category){
				
		switch(category){
		case 'a':
			subplotObv.setDataset(controle, getXYDataset(media.calcularMMS(obv.calcularOBV(getDado(false)), qtmm),
					I18n.getMessage("dv")+qtmm));
			break;
		case 'e':
			subplotObv.setDataset(controle, getXYDataset(media.calcularMME(obv.calcularOBV(getDado(false)), qtmm),
					I18n.getMessage("dx")+qtmm));
			break;		
		}

		XYItemRenderer rendererMme = new StandardXYItemRenderer();

		switch(controle){
		case 1:
			rendererMme.setSeriesPaint(0,Color.BLUE);
			break;
		case 2:
			rendererMme.setSeriesPaint(0,Color.RED);
			break;
		default:
			rendererMme.setSeriesPaint(0,Color.DARK_GRAY);
			break;
		}

		subplotObv.setRenderer(controle,rendererMme);
	}

	public void removeOBV(){
		try{
			if(subplotObv!=null)
				plot.remove(subplotObv);		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void removeMMOBV(int controle){
		try{
			subplotObv.setDataset(controle, null); 
			subplotObv.setRenderer(controle, null);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	public void setVolume(){

		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");

		removeVolume();

		DataIndicator[] dados = getDado(false);

		TimePeriodValues s = new TimePeriodValues(I18n.getMessage("by"));

		for (int i = 0; i < dados.length; i++) {

			try {				
				Iterator<String> iterator = mapaData.keySet().iterator();

				while(iterator. hasNext()){
					String key = (String)iterator.next();						
					if(mapaData.get(key).equals(simpleDateformat.format(dados[i].getIndex()))){			            	
						Day day = new Day(simpleDateformat.parse(key));	

						s.add(new SimpleTimePeriod(day.getStart(), day.getEnd()), dados[i].getVolume());
						break;
					}
				}			

			} catch (ParseException e) {

				e.printStackTrace();
			}   			
		}

		TimePeriodValuesCollection data = new TimePeriodValuesCollection();
		data.addSeries(s);

		XYBarRenderer renderer = new XYBarRenderer(0.33);
		renderer.setSeriesPaint(0, Color.BLACK);
		renderer.setShadowVisible(false);    

		NumberAxis rangeAxis = new NumberAxis(I18n.getMessage("by"));
		subplotVolume = new XYPlot(data, null, rangeAxis, renderer);

		plot.add(subplotVolume, 2);

	}

	public void setMMVolume(int qtmm,int controle, char category){

		switch(category){
		case 'a':
			subplotVolume.setDataset(controle, getXYDataset(media.calcularMMS(getDado(true), qtmm),
					I18n.getMessage("dy")+qtmm));
			break;
		case 'e':
			subplotVolume.setDataset(controle, getXYDataset(media.calcularMME(getDado(true), qtmm),
					I18n.getMessage("dz")+qtmm));
			break;		
		} 

		XYItemRenderer rendererMme = new StandardXYItemRenderer();

		switch(controle){
		case 1:
			rendererMme.setSeriesPaint(0,Color.BLUE);
			break;
		case 2:
			rendererMme.setSeriesPaint(0,Color.RED);
			break;
		default:
			rendererMme.setSeriesPaint(0,Color.DARK_GRAY);
			break;
		}

		subplotVolume.setRenderer(controle,rendererMme);
	}

	public void removeMMVolume(int controle){
		try{
			subplotVolume.setDataset(controle, null); 
			subplotVolume.setRenderer(controle, null);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	public void removeVolume(){
		try{
			if(subplotVolume!=null)
				plot.remove(subplotVolume);		
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	public ArrayList<AbstractXYAnnotation> getAnnotations() {

		return annotations;
	}

	public void setAnnotations(ArrayList<AbstractXYAnnotation> annotations) {

		this.annotations = annotations;
				
		/*try{
			PlotRenderingInfo plotInfo = chartPanel.getChartRenderingInfo().getPlotInfo(); 

			int subplotIndex = plotInfo.getSubplotIndex(java2DPoint);

			if (subplotIndex >= 0) 
			{ 
				List subplotsList = plot.getSubplots(); 
				Iterator iterator = subplotsList.iterator(); 
				int index = 0; 

				while (iterator.hasNext())   
				{ 
					XYPlot subplot = (XYPlot) iterator.next(); 

					if (subplotIndex == index) 
					{ 
					} 
					index++; 
				}		        			        
			}

		} catch(Exception e){
			e.printStackTrace();
		}	*/	

		for(int i = 0; i < this.annotations.size();i++){

			if(annotations.get(i) instanceof StraightLine){

				StraightLine line = (StraightLine)annotations.get(i);
				
				addAnnotation(line.getiPlot(), line);
				
			}
			else if(annotations.get(i) instanceof ParallelLines){

				ParallelLines pLine = (ParallelLines)annotations.get(i);

				addAnnotation(pLine.getiPlot(), pLine);									
			}
			else if(annotations.get(i) instanceof HorizontalLine){

				HorizontalLine hLine = (HorizontalLine)annotations.get(i);

				addAnnotation(hLine.getiPlot(), hLine);									
			}
			else if(annotations.get(i) instanceof VerticalLine){

				VerticalLine vLine = (VerticalLine)annotations.get(i);

				addAnnotation(vLine.getiPlot(), vLine);									
			}
			else if(annotations.get(i) instanceof RectangleBox){

				RectangleBox box = (RectangleBox)annotations.get(i);

				addAnnotation(box.getiPlot(), box);								
			}
			else if(annotations.get(i) instanceof Evolution){

				Evolution evol = (Evolution)annotations.get(i);

				addAnnotation(evol.getiPlot(), evol);									
			}
			else if(annotations.get(i) instanceof ExtFibo){

				ExtFibo exFibo = (ExtFibo)annotations.get(i);

				addAnnotation(exFibo.getiPlot(), exFibo);								
			}
			else if(annotations.get(i) instanceof RetFibo){

				RetFibo retFibo = (RetFibo)annotations.get(i);

				addAnnotation(retFibo.getiPlot(), retFibo);								
			}
			else if(annotations.get(i) instanceof HRetFibo){

				HRetFibo hFibo = (HRetFibo)annotations.get(i);

				addAnnotation(hFibo.getiPlot(), hFibo);									
			}
			else if(annotations.get(i) instanceof Arrow){

				Arrow arrow = (Arrow)annotations.get(i);

				addAnnotation(arrow.getiPlot(), arrow);								
			}
			else if(annotations.get(i) instanceof TextBox){

				TextBox text = (TextBox)annotations.get(i);

				addAnnotation(text.getiPlot(), text);									
			}
			else if(annotations.get(i) instanceof RiskGain){

				RiskGain riskGain = (RiskGain)annotations.get(i);

				addAnnotation(riskGain.getiPlot(), riskGain);									
			}
		}	
	}
	
	private void addAnnotation(int iPlot, AbstractXYAnnotation annotation){
		
		switch(iPlot){
		case 0:
			this.subplotCandle.addAnnotation(annotation);
			break;
		case 1:
			this.subplotMacd.addAnnotation(annotation);
			break;
		case 2:
			this.subplotIfr.addAnnotation(annotation);
			break;
		case 3:
			this.subplotObv.addAnnotation(annotation);
			break;
		case 4:
			this.subplotVolume.addAnnotation(annotation);
			break;
		case 5:
			this.subplotEst.addAnnotation(annotation);
			break;
		case 6:
			this.subplotDMI.addAnnotation(annotation);
			break;
		case 7:
			this.subplotMMNormal.addAnnotation(annotation);
			break;
		}	
	}

	public void setaCompraVenda(boolean compra,int candle){
		
		XYPointerAnnotation pointer;

		if(compra){
			pointer = new XYPointerAnnotation(I18n.getMessage("ea"), dataset.getXValue(0, candle),dataset.getYValue(0, candle),Math.toRadians(90));
			pointer.setPaint(Color.BLUE);
			pointer.setTextAnchor(TextAnchor.HALF_ASCENT_CENTER);

		}
		else {
			pointer = new XYPointerAnnotation(I18n.getMessage("eb"), dataset.getXValue(0, candle),dataset.getYValue(0, candle),Math.toRadians(270));
			pointer.setPaint(Color.RED);
			pointer.setTextAnchor(TextAnchor.HALF_ASCENT_CENTER);
		}

		pointer.setBaseRadius(35.0);
		pointer.setTipRadius(10.0);

		subplotCandle.addAnnotation(pointer);

	}

	public void resetCompraVenda(){
		subplotCandle.clearAnnotations();		
	}
}