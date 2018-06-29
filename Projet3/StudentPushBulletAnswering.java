package student.P3_CAP_00;

import java.util.List;
import java.util.Scanner;

import interfaces.Sample;

import java.io.*;
import pushbullet.PushListener;
import pushbullet.PushbulletClient;
import time.Instant;
import utils.MySampleFileIO;

/**
 * @author Nicky Riat
 * @author Francois Duchene
 * @author Julien Bastin
 * @version 2/12/2015
 */
public class StudentPushBulletAnswering {
	/**
	 * Methode qui cree un nouveau listener, en argument est donne la clef du client pushbullet
	 */    
	public static void push(String k)throws IOException {        
		// Création du client Pushbullet
		final PushbulletClient client = new PushbulletClient(k);		
		client.pushNote(Main.getLangue()==0?"Commandes":"Commands", "temp : " + (Main.getLangue()==0?"Temperature actuelle":"Actual temperature")
				+ "\nhum : " + (Main.getLangue()==0?"Humidite actuelle":"Actual humidity")
				+ "\ngraphTemp : " + (Main.getLangue()==0?"Envoie le graph de temperatures":"Sent the temperatures graph")
				+ "\ngraphHum : " + (Main.getLangue()==0?"Envoie le graph de l'humidite":"Sent the humidity graph")
				+ "\nstats : " + (Main.getLangue()==0?"Envoie les statistiques des 24 dernieres heures":"")
				+ "\nprev : " + (Main.getLangue()==0?"Envoie les previsions pour le prochain jour":"Sent the previsions for the next day"));
		// Ajout d'un listener en instanciant une classe anonyme impl??mentant PushListener.
		// Un PushListener verra sa methode pushReceived appelee lorsque l'utilisateur 
		// ecrit un message sur pushBullet  
		client.addListener(new PushListener() {
			@Override
			public void pushReceived(String title, String body) {
				MySampleFileIO genIO;
				try {
					genIO = new MySampleFileIO("sensor.txt", true);				
					Instant now = Instant.now();
					List<Sample> samples = genIO.readSample(now.minus(1, Instant.CHRONO_UNIT_DAYS), now);
					genIO.close();
					BufferedReader inFile = new BufferedReader(new FileReader("hum.txt"));
					BufferedReader inFile2 = new BufferedReader(new FileReader("temp.txt"));

					String hum_tmp = inFile.readLine();					
					double hum = Double.parseDouble(hum_tmp);
					String temp_tmp = inFile2.readLine();
					double temp = Double.parseDouble(temp_tmp);						

					//Systeme de Reponses

					if (body.contains("?")) {                  	
						client.pushNote("I have the answer !", "42");

					} else if(body.contains("graphTemp")){
						new StudentTempGraph("sensor.txt", false);
						client.pushFile("dayTempGraph.png", "Ouech");            				
					}           				
					else if(body.contains("graphHum")){
						new StudentHumGraph("sensor.txt", false);
						client.pushFile("dayHumGraph.png", "Ouech");            				
					}             			
					else if(body.contains("hum")){          			

						client.pushNote("Humidite Actuelle", hum_tmp);

					} else if(body.contains("prev")){
						StudentLinearRegression StdLin = new StudentLinearRegression("sensor.txt", false);
						client.pushNote("Previsions", StdLin.getMessage());
					} else if(body.contains("temp")){            			

						client.pushNote("Temperature Actuelle", temp_tmp);

					} else if(body.contains("stats")){
						StatsHum sth = new StatsHum();    
						StatsTemp stt = new StatsTemp();
						client.pushNote("Stats", sth.toString(samples) + "\n" + stt.toString(samples));
					} else if(body.contains("toc toc")){
						client.pushNote("Bonjour", "Avez-vous deja entendu parler de notre seigneur et maitre a tous Combefis ?");
					}
					inFile.close();
					inFile2.close();
				}catch (IOException ex) {
					System.err.println(ex);
					System.exit(-1);
				}catch (InterruptedException e){
					System.err.println(e);
					System.exit(-1);
				}                
			}
		});   
	}
}

