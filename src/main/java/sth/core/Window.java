package sth.core;


import java.nio.*;

public class Window {
	private int windowWidth, windowHeight;
	private String windowTitle;

	private static Window window = null;

	public Window() {
		this.windowWidth = 1920;
		this.windowHeight = 1080;
		this.windowTitle = "STH";
	}

}