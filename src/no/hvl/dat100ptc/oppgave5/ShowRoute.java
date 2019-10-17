package no.hvl.dat100ptc.oppgave5;

import static java.lang.String.format;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);

		playRoute(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 
		
		//System.out.println(xstep);

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
	
		double ystep;
		
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		ystep = MAPYSIZE / (Math.abs(maxlat - minlat));
		//System.out.println(ystep);
			
		return ystep;
	}

	public void showRouteMap(int ybase) {
		
		int rad = 5;
		double xstep = xstep();
		double ystep = ystep();
		
		double minX = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minY = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		int xF = (int) (MARGIN + (gpspoints[0].getLongitude()-minX)*xstep);
		int yF = (int) (ybase-(gpspoints[0].getLatitude() -minY)*ystep);

		setColor(0,255,0);
		for(int i = 0; i < gpspoints.length; i++) {
			GPSPoint gps = gpspoints[i];
			
			double x = MARGIN + (gps.getLongitude()-minX)*xstep;
			double y = ybase-(gps.getLatitude() -minY)*ystep;
			
			int xI = (int)x;
			int yI = (int)y;
			
			fillCircle(xI, yI, rad);
			
			drawLine(xF,yF,xI,yI);
			
			xF = xI;
			yF = yI;
		}
	}

	private static double WEIGHT = 80.0;
	
	public void showStatistics() {

		setColor(0,0,0);
		setFont("Courier",12);
		//System.out.println("==============================================");
		String totalT = ("Total Time     :" + format("  %d sek",gpscomputer.totalTime())); //System.out.println(totalT);
		String totalD = ("Total distance :" + format("  %04.2f km", gpscomputer.totalDistance()/1000)); //System.out.println(totalD);
		String totalE = ("Total elevation:" + format("  %04.2f m", gpscomputer.totalElevation())); //System.out.println(totalE);
		String maxS = ("Max speed      :" + format("  %04.2f km/t", gpscomputer.maxSpeed())); //System.out.println(maxS);
		String avrS = ("Average speed  :" + format("  %04.2f km/t", gpscomputer.averageSpeed())); //System.out.println(avrS);
		String kcal = ("Energy         :" + format("  %04.2f kcal", gpscomputer.totalKcal(WEIGHT))); //System.out.println(kcal);
		//System.out.println("==============================================");
		
		int xS = 20;
		int yS = 20;
		
		int oker = 10;
		
		drawString(totalT, xS,yS);
		drawString(totalD, xS,yS+(oker*1));
		drawString(totalE, xS,yS+(oker*2));
		drawString(maxS, xS,yS+(oker*3));
		drawString(avrS, xS,yS+(oker*4));
		drawString(kcal, xS,yS+(oker*5));
		
	}

	public void playRoute(int ybase) {

		
		
		int rad = 5;
		double xstep = xstep();
		double ystep = ystep();
		
		double minX = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minY = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		setColor(0,0,255);
		int id = fillCircle(0,0,rad);
		
		for(int i = 0; i < gpspoints.length; i++) {
			GPSPoint gps = gpspoints[i];
			
			double x = MARGIN + (gps.getLongitude()-minX)*xstep;
			double y = ybase-(gps.getLatitude() -minY)*ystep;
			
			int xI = (int)x;
			int yI = (int)y;
			
			moveCircle(id, xI, yI);
			pause(30);
			
		}
		
	}

}
