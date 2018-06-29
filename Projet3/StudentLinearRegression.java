package student.P3_CAP_00;

import chart.Chart;
import chart.DataSet;
import data.Generator;
import interfaces.Sample;

import java.io.IOException;

import time.Instant;
import utils.*;

import java.util.Date;
import java.util.List;

import pushbullet.PushListener;
import pushbullet.PushbulletClient;
import regression.LinearPseudoInverseRegression;
import regression.LinearPseudoInverseRegression.PolynomialEquation;
import sensors.SensorConnector;

/**
 * Prediction de l'evolution de la temperature ou de l'humidite ambiante sur base
 * d'une regression d'un polynome de degre 3 sur les donnees historiques
 *
 * @author Francois Duchene
 * @author Julien Bastin
 * @author Nicky Riat
 * @version 30/11/2015
 */
public class StudentLinearRegression
{
	private MySampleFileIO genIO;
	private String message;

	public StudentLinearRegression(String fileName, boolean append) throws IOException, InterruptedException
	{
		genIO = new MySampleFileIO(fileName, append);     //Creation d'un utilitaire de lecture/ecriture de donnees     
		Instant now = Instant.now();        
		genIO.close();

		// Lecture des echantillons de temperatures et humidites
		List<Sample> points = genIO.readSample(now.minus(1, Instant.CHRONO_UNIT_HOURS),now);

		// Nous allons tenter de predire l'evolution de la temperature 
		// sur base d'une regression d'un polynome de degre 3 sur les donnees historiques

		int degree = 3;
		boolean temperature = true;

		// Creation de l'objet permettant de calculer une regression
		PolynomialEquation eq = LinearPseudoInverseRegression.findPseudoInverseRegression(points,degree,temperature);

		// Calcul de l'heure suivante
		Instant nextHour = now.plus(1, Instant.CHRONO_UNIT_HOURS);
		// Evaluation du polynome d'interpolation a l'heure suivante
		double temperatureNextHour =  eq.evaluate(nextHour.toEpochSecond());
		// Affichage de la prediction de temperature
		System.out.println("Prediction of Temperature for next hour  : " + temperatureNextHour);
		this.message = "Prediction of Temperature for next hour  : " + temperatureNextHour;
	}
	public String getMessage()
	{
		return message;
	}
}
