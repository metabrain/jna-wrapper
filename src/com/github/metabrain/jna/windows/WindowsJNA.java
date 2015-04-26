package com.github.metabrain.jna.windows;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.github.metabrain.jna.JNA;
import com.github.metabrain.jna.Window;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.User32Util;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;

//TODO somehow hide this class from projects that require this library
public class WindowsJNA implements JNA {

	private static WindowsJNA singleton;
	public static WindowsJNA getInstance() {
		if(singleton == null)
			singleton = new WindowsJNA();
		return singleton;
	}

	private WindowsJNASpecifics internals = null;
	private WindowsJNA() {
		internals = new WindowsJNASpecifics();
	}

	// INTERFACE API

	@Override
	public Window getActiveWindow() {
		return new WindowsWindow(internals.activeWindow());
	}

	@Override
	public Window getAllWindows() {
		List<WindowsWindow> l = new ArrayList<WindowsWindow>();
		for(HWND hwnd : internals.getAllWindows()) {
			l.add(new WindowsWindow(hwnd));
		}
		return new WindowsWindow(internals.activeWindow());
	}

	@Override
	public Window getLargestWindow() {
		return new WindowsWindow(internals.getLargestWindow());
	}

	class WindowsJNASpecifics {

		public HWND activeWindow() {
			return User32.INSTANCE.GetForegroundWindow();
		}

		public HWND getLargestWindow() {
			List<HWND> hwnds = new ArrayList<HWND>();
			for(HWND hwnd: getAllWindows()) {
				hwnds.add(hwnd);
			}
			Collections.sort(hwnds, new Comparator<HWND>() {
				@Override
				public int compare(HWND arg0, HWND arg1) {
					return getWindowDimensions(arg1).width - getWindowDimensions(arg0).width;
				}
			});
			return hwnds.get(0);
		}

		public void selectWindow(String title) {
			HWND hwnd = getWindow(title);
		}

		public HWND getWindow(String title) {
			List<HWND> hwnds = getAllWindows();
			for(HWND hwnd: hwnds) {
				if(getWindowTitle(hwnd).equals(title))
					return hwnd;
			}
			return null;
		}

		public List<HWND> getAllWindows() {
			final List<HWND> hwnds = new ArrayList<HWND>();
			User32.INSTANCE.EnumChildWindows(activeWindow(), new User32.WNDENUMPROC() {
				@Override
				public boolean callback(HWND hwnd, Pointer pntr) {
					hwnds.add(hwnd);
					return true;
				}

			}, null);
			return hwnds;
		}

		public String getWindowTitle(HWND hwnd) {
			char[] buffer = new char[2<<10];
			User32.INSTANCE.GetWindowText(hwnd, buffer, buffer.length);
			return Native.toString(buffer);
		}

		public Rectangle activeWindowDimensions(){
			return getWindowDimensions(activeWindow());
		}

		private Rectangle getWindowDimensions(HWND hwnd) {
			RECT rect = new RECT();
		    User32.INSTANCE.GetWindowRect(hwnd, rect);
		    return rect.toRectangle();
		}


	}
}
