package com.github.metabrain.jna.windows;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.github.metabrain.jna.Window;
import com.github.metabrain.snippets.VideoUtils;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;

class WindowsWindow implements Window {

	private char[] buffer = new char[2<<13];
	private HWND hwnd;

	public WindowsWindow(HWND hwnd) {
		this.hwnd = hwnd;
	}	

	@Override
	public Rectangle getBounds() {
		RECT rect = new RECT();
		User32.INSTANCE.GetWindowRect(hwnd, rect);
		Rectangle rectangle = rect.toRectangle();
		return rectangle;
	}

	//FIXME DOESNT WORK!!!!
	@Override
	public String getTitle() {
		User32.INSTANCE.GetWindowText(hwnd, buffer, buffer.length);
		return Native.toString(buffer);
	}

	@Override
	public BufferedImage getScreenshot(Robot r) {
		return VideoUtils.screenshot(getBounds(), r);
	}

	@Override
	public void click(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pressKey(KeyEvent... keys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}

}
