/*  
 *  Copyright (C) 2010  Luca Wehrstedt
 *
 *  This file is released under the GPLv2
 *  Read the file 'COPYING' for more information
 */

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gravity extends JPanel implements ActionListener, MouseListener, MouseWheelListener {
	public static final int FPS = 100;
	public static final int MOUSE_MAX_MASS = 200;
	public static final int MOUSE_MIN_MASS = 10;
	public static final int OBJECT_MASS = 25;
	public static final int OBJECT_RADIUS = 5;
	public static final double FRICTION = 0.05;

	private double x;
	private double y;
	private double x_speed;
	private double y_speed;
	
	private int mouse_mass = 50;
	private boolean mouse_inside = false;
	private boolean friction_active = false;
	
	// Constructor
	public Gravity () {
		super ();
		setBackground (new Color (0, 120, 255));
		x = 100;
		y = 100;
		x_speed = 0;
		y_speed = 0;
	}
	
	// Draw
	public void paintComponent (Graphics g) {
		super.paintComponent (g);
		
		// Calcola la nuova posizione
		if (mouse_inside) {
			int x_cursor = getMousePosition().x;
			int y_cursor = getMousePosition().y;
			
			double distance = Math.hypot (x_cursor - x, y_cursor - y);
			if (distance < 10)
				distance = 10; // Prevent excessive acceleration
			
			double x_accel = OBJECT_MASS * mouse_mass / Math.pow (distance, 3) * (x_cursor - x);
			double y_accel = OBJECT_MASS * mouse_mass / Math.pow (distance, 3) * (y_cursor - y);
			x_speed += x_accel;
			y_speed += y_accel;
		}
		if (friction_active) {
			x_speed -= FRICTION * x_speed;
			y_speed -= FRICTION * y_speed;
		}
		
		if (x <= 0)
			x_speed = Math.abs (x_speed);
		if (x + OBJECT_RADIUS * 2 >= getWidth())
			x_speed = -1 * Math.abs (x_speed);
		if (y <= 0)
			y_speed = Math.abs (y_speed);
		if (y + OBJECT_RADIUS * 2 >= getHeight())
			y_speed = -1 * Math.abs (y_speed);
		
		x += x_speed;
		y += y_speed;
		
		g.fillOval ((int)(x) - OBJECT_RADIUS, (int)(y) - OBJECT_RADIUS, OBJECT_RADIUS*2, OBJECT_RADIUS*2);
		g.drawString (mouse_mass + " kg", 3, getHeight() - 5);
	}
	
	// New frame
	public void actionPerformed (ActionEvent e) {
		repaint ();
	}
	
	// Mouse inside
	public void mouseEntered (MouseEvent e) {
		mouse_inside = true;
		friction_active = false;
	}
	
	// Mouse outside
	public void mouseExited (MouseEvent e) {
		mouse_inside = false;
		friction_active = true;
	}
	
	// Mouse pressed
	public void mousePressed (MouseEvent e) {
		friction_active = true;
	}
	
	//Mouse released
	public void mouseReleased (MouseEvent e) {
		friction_active = false;
	}
	
	// Wheel moved
	public void mouseWheelMoved (MouseWheelEvent e) {
		mouse_mass -= 5 * e.getWheelRotation();
		if (mouse_mass > MOUSE_MAX_MASS)
			mouse_mass = MOUSE_MAX_MASS;
		if (mouse_mass < MOUSE_MIN_MASS)
			mouse_mass = MOUSE_MIN_MASS;
	}
	
	// Other mouse events
	public void mouseClicked (MouseEvent e) {}
}
