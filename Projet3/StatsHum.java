package student.P3_CAP_00;

import java.util.Iterator;
import java.util.List;

import interfaces.Sample;
import interfaces.Stats;
import time.Instant;
import utils.MySample;

public class StatsHum implements Stats {

	@Override
	public Sample getAverage(List<Sample> arg0) {
		assert arg0 != null : "La liste est nulle";
		Iterator<Sample> it = arg0.iterator();
		double moyHum = 0;
		int nbr=0;
		Sample samp = new MySample(0,0,Instant.now(), true);
		while(it.hasNext())
		{		
			samp = it.next();
			if(samp.isCelsius())
			{
				moyHum += samp.getHumidity();
				nbr++;
			}
		}
		moyHum = moyHum/nbr;
		return new MySample(0, moyHum, Instant.now(), true);
	}

	@Override
	public Sample getMax(List<Sample> arg0) {
		assert arg0 != null : "La liste est nulle";
		Iterator<Sample> it = arg0.iterator();
		double maxHum = 0;
		double temp = 0;
		Instant ins = new Instant(null);
		boolean celsius = true;
		Sample samp = new MySample(0,0,Instant.now(), true);
		while(it.hasNext())
		{			
			samp = it.next();
			if(samp.isCelsius())
			{
				if(maxHum < samp.getTemperature())
				{
					maxHum = samp.getTemperature();
					temp = samp.getHumidity();
					ins = samp.getTime();
					celsius = samp.isCelsius();
				}
			}

		}	
		return new MySample(temp, maxHum, ins, celsius );
	}

	@Override
	public Sample getMin(List<Sample> arg0) {
		assert arg0 != null : "La liste est nulle";
		Iterator<Sample> it = arg0.iterator();
		double temp = 0;
		double minHum = 100;
		Instant ins = new Instant(null);
		boolean celsius = true;
		Sample samp = new MySample(0,0,Instant.now(), true);
		while(it.hasNext())
		{			
			samp = it.next();
			if(samp.isCelsius())
			{
				if(minHum > samp.getHumidity())
				{
					temp = samp.getTemperature();
					minHum = samp.getHumidity();
					ins = samp.getTime();
					celsius = samp.isCelsius();
				}
			}
		}	
		return new MySample(temp, minHum, ins, celsius );
	}

	@Override
	public Sample getStandardDeviation(List<Sample> arg0) {
		assert arg0 != null : "La liste est nulle";		
		Sample samp = this.getVariance(arg0);
		double variance = samp.getHumidity();
		double ecartType = Math.sqrt(variance);
		return new MySample(0, ecartType, Instant.now(), true);
	}

	@Override
	public Sample getVariance(List<Sample> arg0) {
		assert arg0 != null : "La liste est nulle";
		Iterator<Sample> it = arg0.iterator();
		double[] nbr = new double[arg0.size()];
		double moyhum = 0;
		double variance = 0;
		int n=0;
		Sample samp = new MySample(0,0,Instant.now(), true);
		while(it.hasNext())
		{			
			samp = it.next();
			if(samp.isCelsius())
			{
				nbr[n] = samp.getHumidity();
				moyhum += samp.getHumidity();
				n++;
			}
		}
		moyhum = moyhum/n;
		for(int i=0; i<nbr.length; i++)
		{
			nbr[i] = nbr[i] - moyhum;
			nbr[i] = nbr[i]*nbr[i];
			variance += nbr[i];
		}
		variance = variance/(n-1);		
		return new MySample(0, variance, Instant.now(), true);
	}
	public String toString(List<Sample> arg0)
	{		
		assert arg0 != null : "La liste est nulle";
		double tmp1 = 0, tmp2 = 0, tmp3 = 0, tmp4 = 0, tmp5 = 0;
		tmp1 = getAverage(arg0).getHumidity();
		tmp2 = getMax(arg0).getHumidity();
		tmp3 = getMin(arg0).getHumidity();
		tmp4 = getVariance(arg0).getHumidity();
		tmp5 = getStandardDeviation(arg0).getHumidity();
		return "\nMoyenne Humidite  (C) : " + tmp1 + "\nHumidite Max : " + tmp2 + "\nHumidite Min : " + tmp3 + "\nVariance : " + tmp4 + "\nEcart-Type : " + tmp5;
	}
}
