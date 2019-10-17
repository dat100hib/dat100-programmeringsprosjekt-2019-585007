package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;
import static java.lang.String.format;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {


		double min; 
		
		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] lat = new double[gpspoints.length];
		
		for(int i = 0; i < gpspoints.length; i++) {
			GPSPoint gps = gpspoints[i];
			double latitude = gps.getLatitude();
			
			lat[i] = latitude;
		}
		return lat;
		
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] lon = new double[gpspoints.length];
		
		for(int i = 0; i < gpspoints.length; i++) {
			GPSPoint gps = gpspoints[i];
			double longitude = gps.getLongitude();
			
			lon[i] = longitude;
		}
		return lon;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;
		
		latitude1 = toRadians(gpspoint1.getLatitude());
		longitude1 = toRadians(gpspoint1.getLongitude());
		latitude2 = toRadians(gpspoint2.getLatitude());
		longitude2 = toRadians(gpspoint2.getLongitude());
		

        double Dlatitude = latitude2-latitude1;
        double Dlongitude = longitude2-longitude1;
        
        double a = pow((sin(Dlatitude/2)),2) + cos(latitude1) * cos(latitude2) * pow((sin(Dlongitude/2)),2);
        
        double c = 2 * atan2(sqrt(a), sqrt(1-a));
        
        d = c * R;
        
        		
        return d;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;

		double distance = distance(gpspoint1,gpspoint2);
		secs = gpspoint2.getTime() - gpspoint1.getTime();
		
		
		speed = (distance / secs) * 3.6;
		
		return speed;

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";

		int hr = secs / 3600;
		int sekTilovers = secs % 3600;
		int min = sekTilovers / 60;
		int sek = sekTilovers % 60;		
		
		timestr = format("  %02d:%02d:%02d", hr, min, sek);
		
		return timestr;

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str = format("%10.2f",d);     // sender ut , og ikke . for desimal...
		str = str.replace(',', '.');

		return str;
		
	}
}
