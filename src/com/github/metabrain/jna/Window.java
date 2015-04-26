package com.github.metabrain.jna;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public interface Window {
	public Rectangle getBounds();
	
	public String getTitle();

	public void click(int x, int y);

	public void pressKey(KeyEvent... keys);
	
	public void terminate();

	BufferedImage getScreenshot(Robot r);
}
