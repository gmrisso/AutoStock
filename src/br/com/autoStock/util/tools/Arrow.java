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
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
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
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.io.SerialUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;

public class Arrow extends AbstractXYAnnotation implements Cloneable, PublicCloneable, Serializable {

	static Logger logger = Logger.getLogger(Arrow.class.getName());
	
	private static final long serialVersionUID = 1L;

	public static final double DEFAULT_TIP_RADIUS = 10.0;
	public static final double DEFAULT_BASE_RADIUS = 30.0;
	public static final double DEFAULT_LABEL_OFFSET = 0.0;
	public static final double DEFAULT_ARROW_LENGTH = 5.0;
	public static final double DEFAULT_ARROW_WIDTH = 3.0;
	private double angle;
	private double tipRadius;
	private double baseRadius;
	private double arrowLength;
	private double arrowWidth;
	private transient Stroke arrowStroke;
	private transient Paint arrowPaint;
	private double labelOffset;	
	private boolean isSelected;
	private String id;
	private double x1;
	private double y1;
	private int iPlot;	
	private String dataX1;

	public Arrow(double x, double y, double angle) {

		this.x1 = x;
		this.y1 = y;
		this.angle = angle;
		this.tipRadius = DEFAULT_TIP_RADIUS;
		this.baseRadius = DEFAULT_BASE_RADIUS;
		this.arrowLength = DEFAULT_ARROW_LENGTH;
		this.arrowWidth = DEFAULT_ARROW_WIDTH;
		this.labelOffset = DEFAULT_LABEL_OFFSET;
		this.arrowStroke = new BasicStroke(1.0f);
		this.arrowPaint = Color.black;

	}
	
	public void setEnd(double x, double y){
		this.x1 = x;
		this.y1 = y;
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

	public double getAngle() {
		return this.angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getTipRadius() {
		return this.tipRadius;
	}

	public void setTipRadius(double radius) {
		this.tipRadius = radius;
	}

	public double getBaseRadius() {
		return this.baseRadius;
	}

	public void setBaseRadius(double radius) {
		this.baseRadius = radius;
	}

	public double getLabelOffset() {
		return this.labelOffset;
	}

	public void setLabelOffset(double offset) {
		this.labelOffset = offset;
	}

	public double getArrowLength() {
		return this.arrowLength;
	}

	public void setArrowLength(double length) {
		this.arrowLength = length;
	}

	public double getArrowWidth() {
		return this.arrowWidth;
	}

	public void setArrowWidth(double width) {
		this.arrowWidth = width;
	}

	public Stroke getArrowStroke() {
		return this.arrowStroke;
	}

	public void setArrowStroke(Stroke stroke) {
		if (stroke == null) {
			throw new IllegalArgumentException("Null 'stroke' not permitted.");
		}
		this.arrowStroke = stroke;
	}

	public Paint getArrowPaint() {
		return this.arrowPaint;
	}

	public void setArrowPaint(Paint paint) {
		if (paint == null) {
			throw new IllegalArgumentException("Null 'paint' argument.");
		}
		this.arrowPaint = paint;
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

		double startX = xd1 + Math.cos(this.angle) * this.baseRadius;
		double startY = yd1 + Math.sin(this.angle) * this.baseRadius;

		double endX = xd1; // + Math.cos(this.angle) * this.tipRadius;
		double endY = yd1; // + Math.sin(this.angle) * this.tipRadius;

		double arrowBaseX = endX + Math.cos(this.angle) * this.arrowLength;
		double arrowBaseY = endY + Math.sin(this.angle) * this.arrowLength;

		double arrowLeftX = arrowBaseX
		+ Math.cos(this.angle + Math.PI / 2.0) * this.arrowWidth;
		double arrowLeftY = arrowBaseY
		+ Math.sin(this.angle + Math.PI / 2.0) * this.arrowWidth;

		double arrowRightX = arrowBaseX
		- Math.cos(this.angle + Math.PI / 2.0) * this.arrowWidth;
		double arrowRightY = arrowBaseY
		- Math.sin(this.angle + Math.PI / 2.0) * this.arrowWidth;

		GeneralPath arrow = new GeneralPath();
		arrow.moveTo((float) endX, (float) endY);
		arrow.lineTo((float) arrowLeftX, (float) arrowLeftY);
		arrow.lineTo((float) arrowRightX, (float) arrowRightY);
		arrow.closePath();

		g2.setStroke(this.arrowStroke);
		g2.setPaint(this.arrowPaint);
		Line2D line = new Line2D.Double(startX, startY, endX, endY);
		g2.draw(line);
		g2.fill(arrow);
		
		if(this.isSelected){
			g2.setPaint(Color.RED);
			Rectangle2D box1 = new Rectangle2D.Double(startX - 2, startY - 2, 4, 4);			 
			g2.fill(box1);
			Rectangle2D box2 = new Rectangle2D.Double(endX - 2, endY - 2, 4, 4);			 
			g2.fill(box2);
		}

		String toolTip = this.toString();
		this.id =  this.toString();
		String url = getURL();
		if (toolTip != null || url != null) {
			addEntity(info, ShapeUtilities.createLineRegion(line, 5.0f),rendererIndex, toolTip, url);
			addEntity(info,arrow,rendererIndex, toolTip, url);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(angle);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(arrowLength);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(arrowWidth);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(baseRadius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dataX1 == null) ? 0 : dataX1.hashCode());
		result = prime * result + iPlot;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isSelected ? 1231 : 1237);
		temp = Double.doubleToLongBits(labelOffset);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tipRadius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y1);
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
		Arrow other = (Arrow) obj;
		if (Double.doubleToLongBits(angle) != Double
				.doubleToLongBits(other.angle))
			return false;
		if (Double.doubleToLongBits(arrowLength) != Double
				.doubleToLongBits(other.arrowLength))
			return false;
		if (Double.doubleToLongBits(arrowWidth) != Double
				.doubleToLongBits(other.arrowWidth))
			return false;
		if (Double.doubleToLongBits(baseRadius) != Double
				.doubleToLongBits(other.baseRadius))
			return false;
		if (dataX1 == null) {
			if (other.dataX1 != null)
				return false;
		} else if (!dataX1.equals(other.dataX1))
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
		if (Double.doubleToLongBits(labelOffset) != Double
				.doubleToLongBits(other.labelOffset))
			return false;
		if (Double.doubleToLongBits(tipRadius) != Double
				.doubleToLongBits(other.tipRadius))
			return false;
		if (Double.doubleToLongBits(x1) != Double.doubleToLongBits(other.x1))
			return false;
		if (Double.doubleToLongBits(y1) != Double.doubleToLongBits(other.y1))
			return false;
		return true;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	private void writeObject(ObjectOutputStream stream) throws IOException {
		
		stream.writeDouble(this.angle);
		stream.writeDouble(this.tipRadius);
		stream.writeDouble(this.baseRadius);
		stream.writeDouble(this.arrowLength);
		stream.writeDouble(this.arrowWidth);
		SerialUtilities.writePaint(this.arrowPaint, stream);
        SerialUtilities.writeStroke(this.arrowStroke, stream);
		stream.writeDouble(this.labelOffset);	
		stream.writeBoolean(this.isSelected);		
		stream.writeUTF(this.id);
		stream.writeDouble(this.x1);
		stream.writeDouble(this.y1);
		stream.writeInt(this.iPlot);	
		stream.writeUTF(this.dataX1);		
	}

	private void readObject(ObjectInputStream stream)
	throws IOException, ClassNotFoundException {
		this.angle = stream.readDouble();
		this.tipRadius = stream.readDouble();
		this.baseRadius = stream.readDouble();
		this.arrowLength = stream.readDouble();
		this.arrowWidth = stream.readDouble();
		this.arrowPaint = SerialUtilities.readPaint(stream);
        this.arrowStroke = SerialUtilities.readStroke(stream);
		this.labelOffset = stream.readDouble();	
		this.isSelected = stream.readBoolean();
        this.id = stream.readUTF();
		this.x1 = stream.readDouble();
		this.y1 = stream.readDouble();
		this.iPlot = stream.readInt();	
		this.dataX1 = stream.readUTF();	
	}
}
