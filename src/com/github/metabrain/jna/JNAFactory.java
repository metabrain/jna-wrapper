package com.github.metabrain.jna;

import com.github.metabrain.jna.windows.*;

/**
 * Master class that should be used to attain the platform specific JNA
 * @author metabrain
 *
 */
public class JNAFactory {
	
	public static JNA getJNA() {
		//TODO check if platform is Windows or UNIX and instantiate the correct class
		return WindowsJNA.getInstance();
	}
	
}