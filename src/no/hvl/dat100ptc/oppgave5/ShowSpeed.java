package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowSpeed extends EasyGraphics {
			
	private static int MARGIN = 50;
	private static int BARHEIGHT = 200; // assume no speed above 200 km/t

	private GPSComputer gpscomputer;
	private GPSPoint[] gpspoints;
	
	public ShowSpeed() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();
		
	}
	
	// read in the files and draw into using EasyGraphics
	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		int N = gpspoints.length-1; // number of data points
		
		makeWindow("Speed profile", 2*MARGIN + 3 * N, 2 * MARGIN + BARHEIGHT);
		
		showSpeedProfile(MARGIN + BARHEIGHT,N);
	}
	
	public void showSpeedProfile(int ybase, int N) {
		
		System.out.println("Angi tidsskalering i tegnevinduet ...");
		int timescaling = Integer.parseInt(getText("Tidsskalering"));
				
		double[] speeds = gpscomputer.speeds();
		
		int j = 0;
		
		setColor(0, 0,255);
		for(int i = 0; i < gpspoints.length-1; i++) {
			/*
			int xStart = MARGIN + (2*i);
			int yStart = ybase;
			int xSlutt = xStart;
			int ySlutt = ybase - ((int)(speeds[i]));
			
			if(ySlutt < 0) {
				ySlutt = 0;
			}
			
			drawLine(xStart,yStart,xSlutt,ySlutt);
			*/
			GPSPoint gpsi = gpspoints[i];
			GPSPoint gpsi2 = gpspoints[i+1];
			double endY = GPSUtils.speed(gpsi, gpsi2);
			int endy = ybase - (int)endY;
			drawLine(i+MARGIN+ j ,ybase,i+MARGIN+ j, endy);
			j += 2;
			
		}
		setColor(0,255,0);
		int gjHoyde = ybase - ((int)(gpscomputer.maxSpeed())/2);
		
		int xStart = 0;
		int xSlutt = speeds.length + (speeds.length * 5);
		
		drawLine(xStart,gjHoyde,xSlutt,gjHoyde);
	}
}
