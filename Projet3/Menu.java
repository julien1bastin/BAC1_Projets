package student.P3_CAP_00;

import interfaces.Sample;

import java.io.IOException;
import java.util.List;

import time.Instant;
import utils.MySampleFileIO;

public class Menu {
	/**
	 * Méthode d'affiche d'un menu + gestion des fonctions dans celui-ci
	 */
	public static void displayMenu(){
		/*
		 * Affiche le menu
		 */
		System.out.println("****************************");
		System.out.println("*           Menu           *");
		System.out.println("****************************");
		System.out.println("\n1 : Settings");
		System.out.println("2 : " + (Main.getLangue() == 0?"Lancer la prise des mesures de température et humidite en directe.":"Start temperature ans humidity measurement."));
		System.out.println("3 : " + (Main.getLangue() == 0?"Génération du graphe d'humidité.":"Building humidity's graph."));
		System.out.println("4 : " + (Main.getLangue() == 0?"Génération du graphe de température.":"Building temperature's graph."));
		System.out.println("5 : " + (Main.getLangue() == 0?"Statistique des 24 dernières heures.":"Stats of the 24 lasts hours."));
		System.out.println("6 : " + (Main.getLangue() == 0?"Prévisions pour le prochain jour.":"Forecasts of the next day."));
		System.out.println("7 : " + (Main.getLangue() == 0?"Arret du programme.":"Exit the program."));

		/*
		 * Récupération du choix de l'utilisateur
		 */
		int choix = 1;
		boolean correct = false;

		System.out.println("\n" + (Main.getLangue() == 0?"Que voulez vous faire?":"What would you like to do?"));
		while(!correct){
			System.out.println(Main.getLangue() == 0?"Choisissez un chiffre entre 1 et 7.":"Choose a number between 1 and 7.");
			String str = TextIO.getln();
			try{
				choix = Integer.parseInt(str);
				if(choix > 0 && choix < 8){
					correct = true;
				}
				else{
					System.out.println("Entrez un chiffre entre 1 et 7! | Please enter a number between 1 and 7!");
				}
			}
			catch(NumberFormatException e){
				System.out.println("Veuillez entrer un chiffre valide! | Please enter a correct number!");
			}
		}


		/*
		 * Application du choix de l'utilisateur
		 */
		MySampleFileIO genIO;
		try{
			genIO = new MySampleFileIO("sensor.txt", true);				
			Instant now = Instant.now();
			List<Sample> samples = genIO.readSample(now.minus(1, Instant.CHRONO_UNIT_DAYS), now);
			genIO.close();
			switch(choix){
			case 1:
				Main.settings();
				break;
			case 2:
				StudentSensorListener.listen();
				break;
			case 3:
				System.out.println(Main.getLangue()==0?"Pour voir le graphe, veuillez le demander sur pushBullet":"To see graph, send a request with PushBullet");
				StudentHumGraph hum = new StudentHumGraph("sensor.txt", false);
				break;
			case 4:
				System.out.println(Main.getLangue()==0?"Pour voir le graphe, veuillez le demander sur pushBullet":"To see graph, send a request with PushBullet");
				StudentTempGraph temp = new StudentTempGraph("sensor.txt", false);
				break;
			case 5:
				StatsHum sth = new StatsHum();    
				StatsTemp stt = new StatsTemp();
				System.out.println("Stats :" + sth.toString(samples) + "\n" + stt.toString(samples));
				break;
			case 6:
				StudentLinearRegression StdLin = new StudentLinearRegression("sensor.txt", true);
				System.out.println("Previsions :\n" + StdLin.getMessage());
				break;
			case 7:
				Main.arret = false;

			}
		}
		catch(IOException e){

		}
		catch(InterruptedException e){

		}

	}

}
