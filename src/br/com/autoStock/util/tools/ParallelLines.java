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
import org.jfree.chart.util.LineUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;

public class ParallelLines extends AbstractXYAnnotation implements Cloneable, PublicCloneable, Serializable {

	static Logger logger = Logger.getLogger(ParallelLines.class.getName());
	private static final long serialVersionUID = 1L;

	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private double x3;
	private double y3;
	private double x4;
	private double y4;
	private String dataX1;
	private String dataX2;
	private String dataX3;
	
	private transient Stroke stroke;
	private transient Paint paint;
	private boolean isSelected;
	private boolean isLine2Visible;
	private String id;
	private int iPlot;

	public ParallelLines(double x1, double y1, double x2, double y2) {
		this(x1, y1, x2, y2, new BasicStroke(1.0f), Color.black);
	}
	
	public ParallelLines(double x1, double y1, double x2, double y2,
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
	
	public ParallelLines(double x1, double y1, double x2, double y2, double x3, double y3) {
		this(x1, y1, x2, y2, x3, y3, new BasicStroke(1.0f), Color.black);
	}

	public ParallelLines(double x1, double y1, double x2, double y2, double x3, double y3,
			Stroke stroke, Paint paint) {
		double coef;
		
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
		this.x3 = x3;
		this.y3 = y3;
		this.x4 = x1 +(x3 - x2);
		if(x2 != x1){
			coef = (y2-y1)/(x2-x1);	
			this.y4 = coef *(x4-x3) + y3;
		}
		else{
			double dist = Math.sqrt(Math.pow(x1 - x2, 2)+Math.pow(y1-y2, 2));
			y4 = y3 + dist;
		}
				
		this.isLine2Visible = true;
		this.stroke = stroke;
		this.paint = paint;
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

	public String getDataX2() {
		return dataX2;
	}

	public void setDataX2(String dataX2) {
		this.dataX2 = dataX2;
	}
	
	public String getDataX3() {
		return dataX3;
	}

	public void setDataX3(String dataX3) {
		this.dataX3 = dataX3;
	}
	
	@SuppressWarnings("deprecation")
	public void changeDate(String s1, String s2, String s3){
		
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
		
		try {
			Date dx3 = simpleDateformat.parse(s3);
			dx3.setHours(12);
			
			this.x3 = dx3.getTime();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.x4 = x1 +(x3 - x2);
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
		Line2D line2 = null;
		
		g2.setPaint(this.paint);
		g2.setStroke(this.stroke);
		
		double xd1 = domainAxis.valueToJava2D(this.x1, dataArea, plot.getDomainAxisEdge());
		double xd2 = domainAxis.valueToJava2D(this.x2, dataArea, plot.getDomainAxisEdge());
		
		double yd1 = rangeAxis.valueToJava2D(this.y1, dataArea, plot.getRangeAxisEdge());
		double yd2 = rangeAxis.valueToJava2D(this.y2, dataArea, plot.getRangeAxisEdge());
		
		Line2D line1 = new Line2D.Double(xd1, yd1, xd2, yd2);

		if (LineUtilities.clipLine(line1, dataArea)) {
			g2.draw(line1);
		}
		
		if(isLine2Visible){
			double xd3 = domainAxis.valueToJava2D(this.x3, dataArea, plot.getDomainAxisEdge());
			double xd4 = domainAxis.valueToJava2D(this.x4, dataArea, plot.getDomainAxisEdge());
			
			double yd3 = rangeAxis.valueToJava2D(this.y3, dataArea, plot.getRangeAxisEdge());
			double yd4 = rangeAxis.valueToJava2D(this.y4, dataArea, plot.getRangeAxisEdge());
					
			g2.setPaint(this.paint);
			g2.setStroke(this.stroke);
			line2 = new Line2D.Double(xd3, yd3, xd4, yd4);

			if (LineUtilities.clipLine(line2, dataArea)) {
				g2.draw(line2);
			}
		}
		
		if(this.isSelected){
			g2.setPaint(Color.RED);
			Rectangle2D box1 = new Rectangle2D.Double(xd1 - 2, yd1 - 2, 4, 4);			 
			g2.fill(box1);
			Rectangle2D box2 = new Rectangle2D.Double(xd2 - 2, yd2 - 2, 4, 4);			 
			g2.fill(box2);
			
			if(isLine2Visible){
				double xd3 = domainAxis.valueToJava2D(this.x3, dataArea, plot.getDomainAxisEdge());
				double xd4 = domainAxis.valueToJava2D(this.x4, dataArea, plot.getDomainAxisEdge());
				
				double yd3 = rangeAxis.valueToJava2D(this.y3, dataArea, plot.getRangeAxisEdge());
				double yd4 = rangeAxis.valueToJava2D(this.y4, dataArea, plot.getRangeAxisEdge());
				
				Rectangle2D box3 = new Rectangle2D.Double(xd3 - 2, yd3 - 2, 4, 4);			 
				g2.fill(box3);
				Rectangle2D box4 = new Rectangle2D.Double(xd4 - 2, yd4 - 2, 4, 4);			 
				g2.fill(box4);
			}
		}

		String toolTip = this.toString();
		this.id =  this.toString();
		String url = getURL();
		if (toolTip != null || url != null) {
			addEntity(info, ShapeUtilities.createLineRegion(line1, 5.0f),rendererIndex, toolTip, url);
			
			if(line2!= null)
			addEntity(info, ShapeUtilities.createLineRegion(line2, 5.0f),rendererIndex, toolTip, url);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dataX1 == null) ? 0 : dataX1.hashCode());
		result = prime * result + ((dataX2 == null) ? 0 : dataX2.hashCode());
		result = prime * result + ((dataX3 == null) ? 0 : dataX3.hashCode());
		result = prime * result + iPlot;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isLine2Visible ? 1231 : 1237);
		result = prime * result + (isSelected ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(x1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x3);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x4);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y3);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y4);
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
		ParallelLines other = (ParallelLines) obj;
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
		if (dataX3 == null) {
			if (other.dataX3 != null)
				return false;
		} else if (!dataX3.equals(other.dataX3))
			return false;
		if (iPlot != other.iPlot)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isLine2Visible != other.isLine2Visible)
			return false;
		if (isSelected != other.isSelected)
			return false;
		if (Double.doubleToLongBits(x1) != Double.doubleToLongBits(other.x1))
			return false;
		if (Double.doubleToLongBits(x2) != Double.doubleToLongBits(other.x2))
			return false;
		if (Double.doubleToLongBits(x3) != Double.doubleToLongBits(other.x3))
			return false;
		if (Double.doubleToLongBits(x4) != Double.doubleToLongBits(other.x4))
			return false;
		if (Double.doubleToLongBits(y1) != Double.doubleToLongBits(other.y1))
			return false;
		if (Double.doubleToLongBits(y2) != Double.doubleToLongBits(other.y2))
			return false;
		if (Double.doubleToLongBits(y3) != Double.doubleToLongBits(other.y3))
			return false;
		if (Double.doubleToLongBits(y4) != Double.doubleToLongBits(other.y4))
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
		stream.writeDouble(this.x3);
		stream.writeDouble(this.y3);
		stream.writeDouble(this.x4);
		stream.writeDouble(this.y4);
		stream.writeUTF(this.dataX1);
		stream.writeUTF(this.dataX2);
		stream.writeUTF(this.dataX3);
		SerialUtilities.writePaint(this.paint, stream);
        SerialUtilities.writeStroke(this.stroke, stream);
        stream.writeBoolean(this.isSelected);
        stream.writeBoolean(this.isLine2Visible);
        stream.writeUTF(this.id);
		stream.writeInt(this.iPlot);
	}

	private void readObject(ObjectInputStream stream)
	throws IOException, ClassNotFoundException {
		
		this.x1 = stream.readDouble();
		this.y1 = stream.readDouble();
		this.x2 = stream.readDouble();
		this.y2 = stream.readDouble();
		this.x3 = stream.readDouble();
		this.y3 = stream.readDouble();
		this.x4 = stream.readDouble();
		this.y4 = stream.readDouble();
		this.dataX1 = stream.readUTF();
		this.dataX2 = stream.readUTF();
		this.dataX3 = stream.readUTF();
		this.paint = SerialUtilities.readPaint(stream);
        this.stroke = SerialUtilities.readStroke(stream);
        this.isSelected = stream.readBoolean();
        this.isLine2Visible = stream.readBoolean();
        this.id = stream.readUTF();
        this.iPlot = stream.readInt();
		
	}
}
