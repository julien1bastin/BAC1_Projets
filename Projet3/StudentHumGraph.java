package student.P3_CAP_00;

import interfaces.Sample;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import time.Instant;
import utils.MySampleFileIO;
import chart.Chart;
import chart.DataSet;
import data.Generator;

public class StudentHumGraph {
	

	public StudentHumGraph(String fileName, boolean append) throws IOException, InterruptedException
	{
		
		MySampleFileIO genIO = new MySampleFileIO(fileName, append);  // Initialisation d'un utilitaire d'ecriture/lecture de donnees
		Instant now = Instant.now();
		genIO.close();
		// Extraction des echantillons de mesures sur une fenetre de hums (ici de 24 heure en arriere)
		List<Sample> samples = genIO.readSample(now.minus(1, Instant.CHRONO_UNIT_DAYS), now);

		System.out.println(samples.size());

		DataSet hum = new DataSet("Humidity", DataSet.PRECISION_MINUTES);

		for(Sample sample : samples)
		{
			/*
			 * ajout d'un point (x,y) a l'ensemple de donnees correspondant a l'echantillon
			 * x = la date en milliseconde, y = la mesure d'humidite
			 */
			hum.addPoint(new Date(sample.getTime().toEpochMilli()), sample.getHumidity());
		}

		Chart chart = new Chart("Humidity over the last 24 hours", hum);

		chart.saveChartAsPNG("dayHumGraph.png", 1200, 400);    // Sauvegarde du graphique
	}
}
