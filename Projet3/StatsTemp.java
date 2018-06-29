package student.P3_CAP_00;

import java.util.*;

import interfaces.Sample;
import interfaces.Stats;
import time.Instant;
import utils.MySample;

public class StatsTemp implements Stats{

	@Override
	public Sample getAverage(List<Sample> arg0) {
		assert arg0 != null : "La liste est nulle";
		Iterator<Sample> it = arg0.iterator();
		double moyTemp = 0;
		int nbr=0;
		Sample samp = new MySample(0,0,Instant.now(), true);
		while(it.hasNext())
		{
			samp = it.next();
			if(samp.isCelsius())
			{
				moyTemp += samp.getTemperature();
				nbr++;
			}
		}
		moyTemp = moyTemp/nbr;
		return new MySample(moyTemp, 0, Instant.now(), true);
	}

	@Override
	public Sample getMax(List<Sample> arg0) {
		assert arg0 != null : "La liste est nulle";
		Iterator<Sample> it = arg0.iterator();
		double maxTemp = 0;
		double hum = 0;
		Instant ins = new Instant(null);
		boolean celsius = true;
		Sample samp = new MySample(0,0,Instant.now(), true);
		while(it.hasNext())
		{
			samp = it.next();
			if(samp.isCelsius())
			{
				if(maxTemp < samp.getTemperature())
				{
					maxTemp = samp.getTemperature();
					hum = samp.getHumidity();
					ins = samp.getTime();
					celsius = samp.isCelsius();
				}
			}
		}	
		return new MySample(maxTemp, hum, ins, celsius );
	}

	@Override
	public Sample getMin(List<Sample> arg0) {
		assert arg0 != null : "La liste est nulle";
		Iterator<Sample> it = arg0.iterator();
		double minTemp = 100;
		double hum = 0;
		Instant ins = new Instant(null);
		boolean celsius = true;
		Sample samp = new MySample(0,0,Instant.now(), true);
		while(it.hasNext())
		{
			samp = it.next();
			if(samp.isCelsius())
			{
				if(minTemp > samp.getHumidity())
				{
					minTemp = samp.getTemperature();
					hum = samp.getHumidity();
					ins = samp.getTime();
					celsius = samp.isCelsius();
				}
			}
		}	
		return new MySample(minTemp, hum, ins, celsius );		
	}

	@Override
	public Sample getStandardDeviation(List<Sample> arg0) {
		assert arg0 != null : "La liste est nulle";		
		Sample samp = this.getVariance(arg0);
		double variance = samp.getTemperature();
		double ecartType = Math.sqrt(variance);
		return new MySample(ecartType, 0, Instant.now(), true);		
	}

	@Override
	public Sample getVariance(List<Sample> arg0) {
		assert arg0 != null : "La liste est nulle";
		Iterator<Sample> it = arg0.iterator();
		double[] nbr = new double[arg0.size()];
		double moyTemp = 0;
		double variance = 0;
		int n=0;
		Sample samp = new MySample(0,0,Instant.now(), true);
		while(it.hasNext())
		{
			samp = it.next();
			if(samp.isCelsius())
			{
				nbr[n] = samp.getTemperature();
				moyTemp += samp.getTemperature();
				n++;
			}
		}
		moyTemp = moyTemp/n;
		for(int i=0; i<nbr.length; i++)
		{
			nbr[i] = nbr[i] - moyTemp;
			nbr[i] = nbr[i]*nbr[i];
			variance += nbr[i];
		}
		variance = variance/(n-1);		
		return new MySample(variance, 0, Instant.now(), true);		
	}
	public String toString(List<Sample> arg0)
	{		
		assert arg0 != null : "La liste est nulle";
		float tmp1 = 0, tmp2 = 0, tmp3 = 0, tmp4 = 0, tmp5 = 0;
		tmp1 = getAverage(arg0).getTemperature();
		tmp2 = getMax(arg0).getTemperature();
		tmp3 = getMin(arg0).getTemperature();
		tmp4 = getVariance(arg0).getTemperature();
		tmp5 = getStandardDeviation(arg0).getTemperature();
		return "\nMoyenne Temperature (C) : " + tmp1 + "\nTemperature Max : " + tmp2 + "\nTemperature Min : " + tmp3 + "\nVariance : " + tmp4 + "\nEcart-Type : " + tmp5;
	}
}
