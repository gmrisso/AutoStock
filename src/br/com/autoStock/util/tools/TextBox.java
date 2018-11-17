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
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.chart.annotations.AbstractXYAnnotation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.text.TextUtilities;
import org.jfree.ui.TextAnchor;
import org.jfree.util.PublicCloneable;

public class TextBox extends AbstractXYAnnotation implements Cloneable, PublicCloneable, Serializable {

	private static final long serialVersionUID = -2946063342782506328L;

	public static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN,10);
	public static final Paint DEFAULT_PAINT = Color.black;
	public static final TextAnchor DEFAULT_TEXT_ANCHOR = TextAnchor.CENTER;
	public static final TextAnchor DEFAULT_ROTATION_ANCHOR = TextAnchor.CENTER;
	public static final double DEFAULT_ROTATION_ANGLE = 0.0;
	private String text;
	private Font font;
	private transient Paint paint;
	private double x1;
	private double y1;
	private String dataX1;
	private TextAnchor textAnchor;
	private TextAnchor rotationAnchor;
	private double rotationAngle;
	private transient Paint backgroundPaint;
	private boolean outlineVisible;
	private transient Paint outlinePaint;
	private transient Stroke outlineStroke;

	private boolean isSelected;
	private String id;
	private int iPlot;
	
	public TextBox(String text, double x, double y) {
		if (text == null) {
			throw new IllegalArgumentException("Null 'text' argument.");
		}
		this.text = text;
		this.font = DEFAULT_FONT;
		this.paint = DEFAULT_PAINT;
		this.x1 = x;
		this.y1 = y;
		this.textAnchor = DEFAULT_TEXT_ANCHOR;
		this.rotationAnchor = DEFAULT_ROTATION_ANCHOR;
		this.rotationAngle = DEFAULT_ROTATION_ANGLE;

		// by default the outline and background won't be visible
		this.backgroundPaint = null;
		this.outlineVisible = false;
		this.outlinePaint = Color.black;
		this.outlineStroke = new BasicStroke(0.5f);
	}
	
	public void setSelected(boolean isSelected){
		this.isSelected = isSelected;
	}

	public boolean isSelected(){
		return this.isSelected;
	}

	public void setEnd(double x, double y){
		this.x1 = x;
		this.y1 = y;
	}

	public String getId(){
		return this.id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Null 'text' argument.");
		}
		this.text = text;
	}

	public Font getFont() {
		return this.font;
	}

	public void setFont(Font font) {
		if (font == null) {
			throw new IllegalArgumentException("Null 'font' argument.");
		}
		this.font = font;
	}

	public Paint getPaint() {
		return this.paint;
	}

	public void setPaint(Paint paint) {
		if (paint == null) {
			throw new IllegalArgumentException("Null 'paint' argument.");
		}
		this.paint = paint;
	}

	public TextAnchor getTextAnchor() {
		return this.textAnchor;
	}

	public void setTextAnchor(TextAnchor anchor) {
		if (anchor == null) {
			throw new IllegalArgumentException("Null 'anchor' argument.");
		}
		this.textAnchor = anchor;
	}

	public TextAnchor getRotationAnchor() {
		return this.rotationAnchor;
	}

	public void setRotationAnchor(TextAnchor anchor) {
		if (anchor == null) {
			throw new IllegalArgumentException("Null 'anchor' argument.");
		}
		this.rotationAnchor = anchor;
	}

	public double getRotationAngle() {
		return this.rotationAngle;
	}

	public void setRotationAngle(double angle) {
		this.rotationAngle = angle;
	}

	public double getX() {
		return this.x1;
	}
	public void setX(double x) {
		this.x1 = x;
	}

	public double getY() {
		return this.y1;
	}

	public void setY(double y) {
		this.y1 = y;
	}

	public Paint getBackgroundPaint() {
		return this.backgroundPaint;
	}

	public void setBackgroundPaint(Paint paint) {
		this.backgroundPaint = paint;
	}
	public Paint getOutlinePaint() {
		return this.outlinePaint;
	}

	public void setOutlinePaint(Paint paint) {
		if (paint == null) {
			throw new IllegalArgumentException("Null 'paint' argument.");
		}
		this.outlinePaint = paint;
	}

	public Stroke getOutlineStroke() {
		return this.outlineStroke;
	}

	public void setOutlineStroke(Stroke stroke) {
		if (stroke == null) {
			throw new IllegalArgumentException("Null 'stroke' argument.");
		}
		this.outlineStroke = stroke;
	}

	public boolean isOutlineVisible() {
		return this.outlineVisible;
	}

	public void setOutlineVisible(boolean visible) {
		this.outlineVisible = visible;
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

		float anchorX = new Double(xd1).floatValue();
		float anchorY = new Double(yd1).floatValue();

		g2.setFont(getFont());
		Shape hotspot = TextUtilities.calculateRotatedStringBounds(
				getText(), g2, anchorX, anchorY, getTextAnchor(),
				getRotationAngle(), getRotationAnchor());
		if (this.backgroundPaint != null) {
			g2.setPaint(this.backgroundPaint);
			g2.fill(hotspot);
		}
		g2.setPaint(getPaint());
		TextUtilities.drawRotatedString(getText(), g2, anchorX, anchorY,
				getTextAnchor(), getRotationAngle(), getRotationAnchor());
		if (this.outlineVisible) {
			g2.setStroke(this.outlineStroke);
			g2.setPaint(this.outlinePaint);
			g2.draw(hotspot);
		}
		
		if(this.isSelected){
			
			g2.setPaint(Color.RED);
			Rectangle2D box1 = new Rectangle2D.Double(hotspot.getBounds2D().getMinX() - 2, hotspot.getBounds2D().getMinY() - 2, 4, 4);			 
			g2.fill(box1);
			Rectangle2D box2 = new Rectangle2D.Double(hotspot.getBounds2D().getMaxX() - 2, hotspot.getBounds2D().getMaxY() - 2, 4, 4);			 
			g2.fill(box2);
		}

		String toolTip = this.toString();
		this.id =  this.toString();
		String url = getURL();
		if (toolTip != null || url != null) {
			addEntity(info, hotspot, rendererIndex, toolTip, url);
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		long temp;
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
		TextBox other = (TextBox) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
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
		stream.writeUTF(text);
		stream.writeDouble(this.x1);
		stream.writeDouble(this.y1);
		stream.writeUTF(this.dataX1);
		stream.writeBoolean(this.isSelected);		
		stream.writeUTF(this.id);
		stream.writeInt(this.iPlot);
	}

	private void readObject(ObjectInputStream stream)
	throws IOException, ClassNotFoundException {
		this.text = stream.readUTF();
		this.x1 = stream.readDouble();
		this.y1 = stream.readDouble();
		this.dataX1 = stream.readUTF();
		this.isSelected = stream.readBoolean();		
		this.id = stream.readUTF();
		this.iPlot = stream.readInt();
	}

}
