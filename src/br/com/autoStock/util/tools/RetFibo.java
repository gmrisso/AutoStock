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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Line2D;
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
import org.jfree.chart.util.LineUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;

public class RetFibo extends AbstractXYAnnotation implements Cloneable, PublicCloneable, Serializable {

	static Logger logger = Logger.getLogger(ExtFibo.class.getName());
	private static final long serialVersionUID = 1L;

	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private String dataX1;
	private String dataX2;
	private transient Stroke stroke;
	private transient Paint paint;
	private boolean isSelected;
	private String id;
	private int iPlot;

	public RetFibo(double x1, double y1, double x2, double y2) {
		this(x1,y1, x2, y2, new BasicStroke(1.0f), Color.black);
	}

	public RetFibo(double x1, double y1, double x2, double y2,
			Stroke stroke, Paint paint) {

		if (stroke == null) {
			throw new IllegalArgumentException("Null 'stroke' argument.");
		}
		if (paint == null) {
			throw new IllegalArgumentException("Null 'paint' argument.");
		}
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.stroke = stroke;
		this.paint = paint;
	}

	public void setSelected(boolean isSelected){
		this.isSelected = isSelected;
	}

	public boolean isSelected(){
		return this.isSelected;
	}

	public void setEnd(double x2, double y2){
		this.x2 = x2;
		this.y2 = y2;
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

	public String getDataX2() {
		return dataX2;
	}

	public void setDataX2(String dataX2) {
		this.dataX2 = dataX2;
	}
	
	@SuppressWarnings("deprecation")
	public void changeDate(String s1, String s2){
		
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");
				
		try {
			Date dx1 = simpleDateformat.parse(s1);
			dx1.setHours(12);
			
			this.x1 = dx1.getTime();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			Date dx2 = simpleDateformat.parse(s2);
			dx2.setHours(12);
			
			this.x2 = dx2.getTime();
			
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
		double xd2 = domainAxis.valueToJava2D(this.x2, dataArea, plot.getDomainAxisEdge());
		
		double yd1 = rangeAxis.valueToJava2D(this.y1, dataArea, plot.getRangeAxisEdge());
		double yd2 = rangeAxis.valueToJava2D(this.y2, dataArea, plot.getRangeAxisEdge());

		PlotOrientation orientation = plot.getOrientation();
		RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot.getRangeAxisLocation(), orientation);
		
		double y236  = yd1 +(yd2-yd1) * 0.236;
		double y382  = yd1 +(yd2-yd1) * 0.382;
		double y50  = yd1 +(yd2-yd1) * 0.5;
		double y618  = yd1 +(yd2-yd1) * 0.618;
				
		Line2D line0 	= new Line2D.Double(xd1, yd1, xd2, yd1);
		Line2D line236 	= new Line2D.Double(xd1, y236,xd2, y236);
		Line2D line382 	= new Line2D.Double(xd1, y382,xd2, y382);
		Line2D line50 	= new Line2D.Double(xd1, y50, xd2, y50);
		Line2D line618  = new Line2D.Double(xd1, y618,xd2, y618);
		Line2D line100  = new Line2D.Double(xd1, yd2, xd2, yd2);
		
		g2.setPaint(this.paint);
		g2.setStroke(this.stroke);	

		// line is clipped to avoid JRE bug 6574155, for more info
		// see JFreeChart bug 2221495
		boolean visible = LineUtilities.clipLine(line382, dataArea);
		if (visible) {
			g2.draw(line0);
			g2.draw(line236);
			g2.draw(line382);
			g2.draw(line50);
			g2.draw(line618);
			g2.draw(line100);
		}
		g2.setFont(new Font("SansSerif", Font.PLAIN,8));
		TextUtilities.drawRotatedString(String.format("%.2f",rangeAxis.java2DToValue(yd1, dataArea,rangeEdge))+"(0%)", g2, 
				new Double(xd2 + 5).floatValue(), new Double(yd1).floatValue(), TextAnchor.BASELINE_LEFT, 0.0, TextAnchor.CENTER);
		TextUtilities.drawRotatedString(String.format("%.2f",rangeAxis.java2DToValue(y236, dataArea,rangeEdge))+"(23,6%)", g2, 
				new Double(xd2 + 5).floatValue(), new Double(y236).floatValue(), TextAnchor.BASELINE_LEFT, 0.0, TextAnchor.CENTER);
		TextUtilities.drawRotatedString(String.format("%.2f",rangeAxis.java2DToValue(y382, dataArea,rangeEdge))+"(38,2%)", g2, 
				new Double(xd2 + 5).floatValue(), new Double(y382).floatValue(), TextAnchor.BASELINE_LEFT, 0.0, TextAnchor.CENTER);
		TextUtilities.drawRotatedString(String.format("%.2f",rangeAxis.java2DToValue(y50, dataArea,rangeEdge))+"(50%)", g2, 
				new Double(xd2 + 5).floatValue(), new Double(y50).floatValue(), TextAnchor.BASELINE_LEFT, 0.0, TextAnchor.CENTER);
		TextUtilities.drawRotatedString(String.format("%.2f",rangeAxis.java2DToValue(y618, dataArea,rangeEdge))+"(61,8%)", g2, 
				new Double(xd2 + 5).floatValue(), new Double(y618).floatValue(), TextAnchor.BASELINE_LEFT, 0.0, TextAnchor.CENTER);
		TextUtilities.drawRotatedString(String.format("%.2f",rangeAxis.java2DToValue(yd2, dataArea,rangeEdge))+"(100%)", g2, 
				new Double(xd2 + 5).floatValue(), new Double(yd2).floatValue(), TextAnchor.BASELINE_LEFT, 0.0, TextAnchor.CENTER);

		if(this.isSelected){
			g2.setPaint(Color.RED);
			Rectangle2D box1 = new Rectangle2D.Double(xd1 - 2, yd1 - 2, 4, 4);			 
			g2.fill(box1);
			Rectangle2D box2 = new Rectangle2D.Double(xd2 - 2, yd2 - 2, 4, 4);			 
			g2.fill(box2);
		}

		String toolTip = this.toString();
		this.id =  this.toString();
		String url = getURL();
		if (toolTip != null || url != null) {
			addEntity(info, ShapeUtilities.createLineRegion(line0, 5.0f),rendererIndex, toolTip, url);
			addEntity(info, ShapeUtilities.createLineRegion(line236, 5.0f),rendererIndex, toolTip, url);
			addEntity(info, ShapeUtilities.createLineRegion(line382, 5.0f),rendererIndex, toolTip, url);
			addEntity(info, ShapeUtilities.createLineRegion(line50, 5.0f),rendererIndex, toolTip, url);
			addEntity(info, ShapeUtilities.createLineRegion(line618, 5.0f),rendererIndex, toolTip, url);
			addEntity(info, ShapeUtilities.createLineRegion(line100, 5.0f),rendererIndex, toolTip, url);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dataX1 == null) ? 0 : dataX1.hashCode());
		result = prime * result + ((dataX2 == null) ? 0 : dataX2.hashCode());
		result = prime * result + iPlot;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isSelected ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(x1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y2);
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
		RetFibo other = (RetFibo) obj;
		if (dataX1 == null) {
			if (other.dataX1 != null)
				return false;
		} else if (!dataX1.equals(other.dataX1))
			return false;
		if (dataX2 == null) {
			if (other.dataX2 != null)
				return false;
		} else if (!dataX2.equals(other.dataX2))
			return false;
		if (iPlot != other.iPlot)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isSelected != other.isSelected)
			return false;
		if (Double.doubleToLongBits(x1) != Double.doubleToLongBits(other.x1))
			return false;
		if (Double.doubleToLongBits(x2) != Double.doubleToLongBits(other.x2))
			return false;
		if (Double.doubleToLongBits(y1) != Double.doubleToLongBits(other.y1))
			return false;
		if (Double.doubleToLongBits(y2) != Double.doubleToLongBits(other.y2))
			return false;
		return true;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		
		stream.writeDouble(this.x1);
		stream.writeDouble(this.y1);
		stream.writeDouble(this.x2);
		stream.writeDouble(this.y2);
		stream.writeUTF(this.dataX1);
		stream.writeUTF(this.dataX2);
		SerialUtilities.writePaint(this.paint, stream);
        SerialUtilities.writeStroke(this.stroke, stream);
        stream.writeBoolean(this.isSelected);		
		stream.writeUTF(this.id);
		stream.writeInt(this.iPlot);
		
	}

	private void readObject(ObjectInputStream stream)
	throws IOException, ClassNotFoundException {
		this.x1 = stream.readDouble();
		this.y1 = stream.readDouble();
		this.x2 = stream.readDouble();
		this.y2 = stream.readDouble();
		this.dataX1 = stream.readUTF();
		this.dataX2 = stream.readUTF();
		this.paint = SerialUtilities.readPaint(stream);
        this.stroke = SerialUtilities.readStroke(stream);
        this.isSelected = stream.readBoolean();
        this.id = stream.readUTF();
        this.iPlot = stream.readInt();
	}
}