package no.hvl.dat100ptc.oppgave4;

import static java.lang.String.format;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;
		/*
		GPSPoint gps = gpspoints[0];
		
		for(int i = 0; i < gpspoints.length; i++) {
			GPSPoint gps2 = gpspoints[i];
			distance += GPSUtils.distance(gps, gps2);
			
			gps = gps2;
		}
		
		*/
		for(int i =0; i < gpspoints.length-1; i++){
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i+1]);
		}
		 
		
		return distance;

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;
		double[] Elevation = new double[gpspoints.length];

		for(int i = 0; i < gpspoints.length; i++) {
			GPSPoint gps = gpspoints[i];
			double gpsE = gps.getElevation();
			Elevation[i] = gpsE;
		}
		elevation = GPSUtils.findMax(Elevation);
		
		return elevation;

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {
				
		GPSPoint gps1 = gpspoints[0];
		GPSPoint gps2 = gpspoints[gpspoints.length-1];
		
		int time1 = gps1.getTime();
		int time2 = gps2.getTime();
		
		int totTime = time2 - time1;
		
		return totTime;
		
	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		
		double[] speeds = new double[gpspoints.length-1];
		
		for(int i = 0; i < gpspoints.length-1; i++) {
			GPSPoint gps1 = gpspoints[i];
			GPSPoint gps2 = gpspoints[i+1];
			double speed = GPSUtils.speed(gps1, gps2);
			
			speeds[i] = speed;
		}
		
		return speeds;
	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		
		double[] speeds = speeds();
		
		maxspeed = GPSUtils.findMax(speeds);
		
		return maxspeed;
		
	}

	public double averageSpeed() {

		double average = 0;
		
		double distance = totalDistance();
		int time = totalTime();
		
		
		average = (distance / time) * 3.6;
		
		return average;
		
	}

	

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		double met = 0;		
		double speedmph = speed * MS;
		double hr = secs/3600;

		
		if(speed < 10) {
			met = 4.0;
		}else if(speedmph < 12) {
			met = 6.0;
		}else if(speedmph < 14) {
			met = 8.0;
		}else if(speedmph < 16) {
			met = 10.0;
		}else if(speedmph < 20) {
			met = 12.0;
		}else if(speedmph >= 20) {
			met = 16.0;
		}
		
		kcal = met * weight * secs/3600; //kcal = met * weight * (secs/3600); //Kan ikke sette inn hr.... WY!
		
		return kcal;
		
		
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;
		
		for(int i = 0; i < gpspoints.length-1; i++) {
			GPSPoint gps1 = gpspoints[i];
			GPSPoint gps2 = gpspoints[i+1];
			
			int secs1 = gps1.getTime();
			int secs2 = gps2.getTime();
			int secs = secs2 - secs1;
			
			double speed = GPSUtils.speed(gps1,gps2);
			
			double kcal = kcal(weight, secs, speed);
			
			totalkcal += kcal;
		}
		
		return totalkcal;
		
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		System.out.println("==============================================");

		System.out.println("Total Time     :" + format("  %d sek",this.totalTime()));
		System.out.println("Total distance :" + format("  %04.2f km", this.totalDistance()/1000));
		System.out.println("Total elevation:" + format("  %04.2f m", this.totalElevation()));
		System.out.println("Max speed      :" + format("  %04.2f km/t", this.maxSpeed()));
		System.out.println("Average speed  :" + format("  %04.2f km/t", this.averageSpeed()));
		System.out.println("Energy         :" + format("  %04.2f kcal", this.totalKcal(WEIGHT)));
		
		System.out.println("==============================================");

	}

}
