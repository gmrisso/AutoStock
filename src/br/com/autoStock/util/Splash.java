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

package br.com.autoStock.util;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.ImageObserver;

import org.apache.log4j.Logger;

public class Splash extends Frame {

	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(Splash.class.getName());

	public Splash(String aImageId) {

    if ( aImageId == null || aImageId.trim().length() == 0 ){
      throw new IllegalArgumentException(I18n.getMessage("av"));
    }
    fImageId = aImageId;
  }
   
  public void splash(){
    initImageAndTracker();
    setSize(fImage.getWidth(NO_OBSERVER), fImage.getHeight(NO_OBSERVER));
    center();
    
    fMediaTracker.addImage(fImage, IMAGE_ID);
    try {
      fMediaTracker.waitForID(IMAGE_ID);
    }
    catch(InterruptedException ex){
    	logger.debug(I18n.getMessage("ax"));
    }

    @SuppressWarnings("unused")
	SplashWindow splashWindow = new SplashWindow(this,fImage);
  }
  
  // PRIVATE//
  private String fImageId;
  private MediaTracker fMediaTracker;
  private Image fImage;
  private static final ImageObserver NO_OBSERVER = null; 
  private static final int IMAGE_ID = 0;

  private void initImageAndTracker(){
    fMediaTracker = new MediaTracker(this);
    //URL imageURL = SplashScreen.class.getResource(fImageId);
    fImage = Toolkit.getDefaultToolkit().getImage(fImageId);
  }

  private void center(){
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    Rectangle frame = getBounds();
    setLocation((screen.width - frame.width)/2, (screen.height - frame.height)/2);
  }
 
  @SuppressWarnings("serial")
private class SplashWindow extends Window {
    SplashWindow(Frame aParent, Image aImage) {
       super(aParent);
       fImage = aImage;
       setSize(fImage.getWidth(NO_OBSERVER), fImage.getHeight(NO_OBSERVER));
       Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
       Rectangle window = getBounds();
       setLocation((screen.width - window.width) / 2,(screen.height - window.height)/2);
       setVisible(true);
    }
    public void paint(Graphics graphics) {
      if (fImage != null) {
        graphics.drawImage(fImage,0,0,this);
      }
    }
    private Image fImage;
  }
  
  
  
}