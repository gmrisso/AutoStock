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

package br.com.autoStock.util.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jfree.chart.annotations.AbstractXYAnnotation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.util.PublicCloneable;

import br.com.autoStock.util.I18n;

public class RiskGain extends AbstractXYAnnotation implements Cloneable, PublicCloneable, Serializable {

	static Logger logger = Logger.getLogger(ParallelLines.class.getName());
	private static final long serialVersionUID = 1L;

	private double x1;
	private double y1;
	private double y2;
	private double y3;
	private String dataX1;
	private boolean isSelected;
	private boolean isBox3Visible;
	private String id;
	private int iPlot;
	
	public RiskGain(double x1, double y1, double y2) {

		this.x1 = x1;
		this.y1 = y1;
		this.y2 = y2;
		
		this.isBox3Visible = false;
	}
	
	public RiskGain(double x1, double y1, double y2, double y3) {
		
		this.x1 = x1;
		this.y1 = y1;
		this.y2 = y2;
		this.y3 = y3;
				
		this.isBox3Visible = true;
	}

	public void setSelected(boolean isSelected){
		this.isSelected = isSelected;
	}

	public boolean isSelected(){
		return this.isSelected;
	}

	public String getId(){
		return this.id;
	}
	
	public String getDataX1() {
		return dataX1;
	}

	public void setDataX1(String dataX1) {
		this.dataX1 = dataX1;
	}
	
	@SuppressWarnings("deprecation")
	public void changeDate(String s1){
		
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");
				
		try {
			Date dx1 = simpleDateformat.parse(s1);
			dx1.setHours(12);
			
			this.x1 = dx1.getTime();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}					
	}
	
	public int getiPlot() {
		return iPlot;
	}

	public void setiPlot(int iPlot) {
		this.iPlot = iPlot;
	}
	
	public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea,
			ValueAxis domainAxis, ValueAxis rangeAxis,
			int rendererIndex,
			PlotRenderingInfo info) {
		double xd1 = domainAxis.valueToJava2D(this.x1, dataArea, plot.getDomainAxisEdge());
						
		double yd1 = rangeAxis.valueToJava2D(this.y1, dataArea, plot.getRangeAxisEdge());
		double yd2 = rangeAxis.valueToJava2D(this.y2, dataArea, plot.getRangeAxisEdge());
		
		Rectangle2D box3 = null;
		PlotOrientation orientation = plot.getOrientation();
		RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot.getRangeAxisLocation(), orientation);
						
		g2.setPaint(Color.GREEN);
		Rectangle2D box1 = new Rectangle2D.Double(xd1-2, yd1-2, 4, 4);			 
		g2.fill(box1);
		g2.setFont(new Font("SansSerif", Font.PLAIN,10));
		TextUtilities.drawRotatedString(I18n.getMessage("ce")+" "+String.format("%.2f", rangeAxis.java2DToValue(yd1, dataArea,rangeEdge)), g2, 
				new Double(xd1 - 5).floatValue(), new Double(yd1).floatValue(),TextAnchor.BASELINE_RIGHT, 0.0, TextAnchor.CENTER);
				
		g2.setPaint(Color.RED);
		Rectangle2D box2 = new Rectangle2D.Double(xd1-2, yd2-2, 4, 4);			 
		g2.fill(box2);
		g2.setFont(new Font("SansSerif", Font.PLAIN,10));
		
		double varStop = ((this.y2 / this.y1) - 1F)*100F;
		TextUtilities.drawRotatedString("Stop "+String.format("%.2f", rangeAxis.java2DToValue(yd2, dataArea,rangeEdge))+String.format("(%.2f)", varStop)+ "%", g2, 
				new Double(xd1 - 5).floatValue(), new Double(yd2).floatValue(),
				TextAnchor.BASELINE_RIGHT, 0.0, TextAnchor.CENTER);
				
		if(isBox3Visible){
			
			double yd3 = rangeAxis.valueToJava2D(this.y3, dataArea, plot.getRangeAxisEdge());
			
			g2.setPaint(Color.BLUE);
			box3 = new Rectangle2D.Double(xd1-2, yd3-2, 4, 4);			 
			g2.fill(box3);
			g2.setFont(new Font("SansSerif", Font.PLAIN,10));
			
			//double vl3 = rangeAxis.java2DToValue(this.y3, dataArea,rangeEdge);
					
			double varLucro = ((this.y3 / this.y1) - 1F)*100F;
			TextUtilities.drawRotatedString(I18n.getMessage("cf")+" "+String.format("%.2f", rangeAxis.java2DToValue(yd3, dataArea,rangeEdge))+String.format("(%.2f)", varLucro)+ "%", g2, 
					new Double(xd1 - 5).floatValue(), new Double(yd3).floatValue(),
					TextAnchor.BASELINE_RIGHT, 0.0, TextAnchor.CENTER);
		}
		
		String toolTip = this.toString();
		this.id =  this.toString();
		String url = getURL();
		if (toolTip != null || url != null) {
			addEntity(info, box1,rendererIndex, toolTip, url);
			addEntity(info, box2,rendererIndex, toolTip, url);
			if(box3!= null)
			addEntity(info,box3,rendererIndex, toolTip, url);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(x1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y3);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RiskGain other = (RiskGain) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(x1) != Double.doubleToLongBits(other.x1))
			return false;
		if (Double.doubleToLongBits(y1) != Double.doubleToLongBits(other.y1))
			return false;
		if (Double.doubleToLongBits(y2) != Double.doubleToLongBits(other.y2))
			return false;
		if (Double.doubleToLongBits(y3) != Double.doubleToLongBits(other.y3))
			return false;
		return true;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeDouble(this.x1);
		stream.writeDouble(this.y1);
		stream.writeDouble(this.y2);
		stream.writeDouble(this.y3);
		stream.writeUTF(this.dataX1);
		stream.writeBoolean(this.isSelected);
		stream.writeBoolean(this.isBox3Visible);
		stream.writeUTF(this.id);
		stream.writeInt(this.iPlot);
		
	}

	private void readObject(ObjectInputStream stream)
	throws IOException, ClassNotFoundException {
		this.x1 = stream.readDouble();
		this.y1 = stream.readDouble();
		this.y2 = stream.readDouble();
		this.y3 = stream.readDouble();
		this.dataX1 = stream.readUTF();
		this.isSelected = stream.readBoolean();
		this.isBox3Visible = stream.readBoolean();
		this.id = stream.readUTF();
		this.iPlot = stream.readInt();
	}
}