package student.P3_CAP_00;

import interfaces.Sample;
import interfaces.SampleIO;
import pushbullet.PushbulletClient;

import java.io.*;

import sensors.SampleListener;
import sensors.SensorConnector;
import utils.*;


/**
 * @author Maxime Piraux, maxime.piraux@student.uclouvain.be
 * @author Pierre Schaus, pierre.schaus@uclouvain.be
 */
public class StudentSensorListener {
	/**
	 * 
	 * @throws IOException
	 */
	public static void listen() throws IOException {     	
		// Creation d'un utilitaire de fichier d'�criture/lecture de donnees
		SampleIO sampleIO = new MySampleFileIO("sensor.txt", true);

		// Connexion au senseur en lui indiquant l'utilitaire de fichier pour l'ecriture de donnees
		SensorConnector sensor = new SensorConnector(sampleIO);

		// Modification de l'intervalle entre deux mesures du senseur par rapport a ce que choisi l'user
		sensor.setSamplingDelay(Main.getFrequence());
		// Creation d'un client pushBullet
		final PushbulletClient client = new PushbulletClient(Main.getPushKey());
		final double limMaxTemp = Main.getTempMax();
		final double limMinTemp = Main.getTempMin();
		final double limMaxHum = Main.getHumMax();
		final double limMinHum = Main.getHumMin();

		// Ajout d'un listener sur le senseur
		// a chaque fois qu'une mesure est prise par le senseur
		// la methode sampleTaken() sera appelee avec la nouvelle mesure en argument
		sensor.addListener(new SampleListener() {
			@Override
			public void sampleTaken(Sample sample) {
				Double hum = 0.0; Double temp = 0.0;
				double humTmp = 0, tempTmp = 0;
				Boolean cels = true;
				boolean cels_tmp = true;
				String str1 = "", str2 = "", str3 = "";			
				
				try {   
					//Declaration de quelques variables
					
					humTmp = sample.getHumidity(); tempTmp = sample.getTemperature(); cels_tmp = sample.isCelsius();
					hum = humTmp; temp = tempTmp; cels = cels_tmp;
					str1 = hum.toString(); str2 = temp.toString(); str3 = cels.toString();					
					
					// Systeme de notifs
					
					if(humTmp < limMinHum)	{
						client.pushNote("ALERTE", "HUMIDITE TROP BASSE : " + hum);
					}else if(humTmp > limMaxHum) {
						client.pushNote("ALERTE", "HUMIDITE TROP HAUTE : " + hum);
					}else if(tempTmp < limMinTemp){
						client.pushNote("ALERTE", "TEMPERATURE TROP BASSE : " + temp);
					}else if(tempTmp > limMaxTemp){
						client.pushNote("ALERTE", "TEMPERATURE TROP HAUTE : " +temp);
					}								
					
					// Creation d'un fichier contenant l'humidite actuelle
					
					PrintWriter outFile = new PrintWriter(new FileWriter("hum.txt"));
					outFile.println(str1);
					outFile.close();
					
					// Creation d'un fichier contenant la temperature actuelle
					
					PrintWriter outFile2 = new PrintWriter(new FileWriter("temp.txt"));
					outFile2.println(str2);
					outFile2.close();
					
					// Creation d'un fichier contenant l'unite de la temperature
					
					PrintWriter outFile3 = new PrintWriter(new FileWriter("unite.txt"));
					outFile3.println(str3);
					outFile3.close();
					
				}catch(IOException e){
					System.err.println(e);
					System.exit(-1);
				}
			}
		});

		// Lancement de la collecte de mesure.
		sensor.start();
	}
	public static void afficheMesures()
	{
		try {
			BufferedReader Bf1 = new BufferedReader(new FileReader("hum.txt"));
			BufferedReader Bf2 = new BufferedReader(new FileReader("temp.txt"));
			BufferedReader Bf3 = new BufferedReader(new FileReader("unite.txt"));
			String hum = Bf1.readLine(); String temp = Bf2.readLine(); String celsTmp = Bf3.readLine();
			boolean cels = Boolean.parseBoolean(celsTmp);
			System.out.println("Sample: "+ temp + ((cels == true)?" C/":" F/") + hum);	
			
			Bf1.close();
			Bf2.close();
			Bf3.close();
		} catch (FileNotFoundException e) {			
			System.err.println(e);
			System.exit(-1);
		} catch (IOException e) {
			System.err.println(e);
			System.exit(-1);
		}
		
	}
}