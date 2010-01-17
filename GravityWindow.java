/*  
 *  Copyright (C) 2010  Luca Wehrstedt
 *
 *  This file is released under the GPLv2
 *  Read the file 'COPYING' for more information
 */

import javax.swing.JFrame;
import javax.swing.Timer;

public class GravityWindow extends JFrame {
	public GravityWindow () {
		super ();
		
		setTitle ("Gravity");
		setSize (640, 480);
		
		Gravity content = new Gravity ();
		getContentPane ().add (content);
		
		addMouseListener (content);
		addMouseWheelListener (content);
		
		Timer timer = new Timer (1000/content.FPS, content);
		timer.start ();
	}
}
