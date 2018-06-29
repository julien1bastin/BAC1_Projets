package student.P3_CAP_00;

import java.io.IOException;


/**
 * Décrivez votre classe Main ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Main
{
	private static int langue = 0; //langue d'execution du programme, par defaut francais
	private static String pushKey; // cle pushBullet
	private static int frequence = 5; //frequence a laquelle les mesures sont prises, par defaut 5 secondes
	private static int tempMin = 10;
	private static int tempMax = 13;
	private static int humMin = 30;
	private static int humMax = 50;
	/**
	 * Methode pour changer les settings
	 * @param args
	 */
	public static void settings(){
		boolean b = false;
		System.out.println("Bienvenue dans les settings. | Welcome in the settings.");
		System.out.println("Nous vous recommandons de faire toutes les settings la premiere fois. | We recommend to set up the program if this is the first time you run it.");
		do{
			/*
			 * Menu des settings
			 */
			System.out.println("Que voulez-vous modifier? | What would you like to modify?");
			System.out.println("\n1) Langage (Defaut : Francais | By default : French)");
			System.out.println("2) PushBullet Key");
			System.out.println("3) Frequence de prise des mesures (defaut : 5 secondes) | Time beween each measurement (by default : 5 seconds)");
			System.out.println("4) Temperature a ne pas depasser (Defaut : min 10, max 13 |");
			System.out.println("5) Humidite a ne pas depasser (Defaut : min 30, max 50 |");

			/*
			 * Recuperation du choix
			 */
			boolean ch = false;
			int choice = 1;
			while(!ch){ //tant que le choix n'est pas valable, on recommence
				String str = TextIO.getln();
				try{
					choice = Integer.parseInt(str);
					if(choice > 0 && choice < 6){
						ch = true;
					}
					else{
						System.out.println("Veuillez entrer un chiffre entre 1 et 5. | Please enter en number between 1 and 5.");
					}
				}
				catch(NumberFormatException e){
					System.out.println("Veuillez entrer un chiffre valable! | Please enter a correct number!");
				}
			}

			/*
			 * Application en fonction du choix
			 */
			switch(choice){
			case 1:
				choseLangage();
				break;
			case 2:
				chosePushKey();
				break;
			case 3:
				choseFrequence();
				break;
			case 4:
				choseLimitTemp();
				break;
			case 5:
				choseLimitHum();
				break;
			}
			/*
			 * Demande de recommencement et recommencement
			 */
			boolean recom = false;
			System.out.println(langue == 0 ? "Voulez vous modifier autre chose? (O/N)" : "Would you like to modify an other thing (O/N)");
			while(!recom){
				String str = TextIO.getln();
				char c = str.charAt(0);
				if(c =='O' || c == 'N'|| c == 'o' || c == 'n'){
					recom = true;
					if(c == 'N'|| c == 'n'){
						b = true;
					}
				}
				else{
					System.out.println("Veuillez entrer O ou N! | Please enter O or N!");
				}
			}
		}
		while(!b);

	}

	/**
	 * Methode qui permet de choisir la langue dans laquelle le programme sera execute
	 */
	public static void choseLangage(){
		boolean b = false;
		while(!b){ //tant que 0 ou 1 n'est pas entre, on recommence l'operation
			System.out.println("Veuillez choisir la langue: | Please choose de langage : Franeais (0) | English(1)");
			String s = TextIO.getln();
			try{
				langue = Integer.parseInt(s);
				if(langue == 1 || langue == 0){
					b=true;
				}
				else{
					System.out.println("Veuillez entrer 0 ou 1. | Please enter 0 or 1.");
				}
			}
			catch(NumberFormatException e){
				System.out.println("Veuillez entrer un chiffre valable. | Please enter a correct number.");
			}
		}

	}

	/**
	 * getter de la variable d'instance langue
	 * @return langue
	 */
	public static int getLangue(){
		return langue;
	}

	/**
	 * Methode qui permet de choisir la cle pushBullet
	 */
	public static void chosePushKey(){
		boolean b = false;

		while(!b){
			System.out.println(langue == 0 ? "Veuillez entrer votre clef pushBullet:" : "Please enter your pushBullet key:");
			pushKey = TextIO.getln();
			System.out.println(langue == 0 ? "Est-ce que ceci est bien votre cle? " + pushKey + " O/N" : "Is this key yours? " + pushKey + " O/N");
			boolean recom = false;
			while(!recom){
				String str = TextIO.getln();
				char c = str.charAt(0);
				if(c =='O' || c == 'o' || c == 'n' ||c == 'N'){
					recom = true;
					if(c == 'O'|| c == 'o'){
						b = true;
					}

				}
				else{
					System.out.println(langue == 0 ? "Veuillez entrer O ou N!" : "Please enter O or N!");
				}
			}
		}
	}

	/**
	 * getter de la variable d'instance pushKey
	 * @return pushKey
	 */
	public static String getPushKey(){
		return pushKey;
	}

	/**
	 * Methode qui permet de choisir la frequence e laquelle les mesures sont prises
	 */
	public static void choseFrequence(){
		int heures;
		int minutes;
		int secondes;
		boolean correct = false;
		System.out.println(langue == 0? "Veuillez entrer le temps entre chaque mesure en heures, minutes et secondes"
				+ " sous la forme HH:MM:SS" : "Please enter the time that you would like to set up between two mesurements in hours, minutes and seconds"
				+ " following that pattern HH:MM:SS");
		while(!correct){
			String[] str = TextIO.getln().split(":");
			try{
				heures = Integer.parseInt(str[0]);
				minutes = Integer.parseInt(str[1]);
				secondes = Integer.parseInt(str[2]);
				frequence = 3600*heures + 60*minutes + secondes;
				correct = true;
			}
			catch(NumberFormatException e){
				System.out.println(langue == 0 ? "Veuillez entrer des nombres valides" : "Please enter valides numbers");
			}
			catch(IndexOutOfBoundsException e){
				System.out.println(langue == 0 ? "Veuillez respecter le format HH:MM:SS" : "Please follow the pattern HH:MM:SS");
			}

		}
	}

	/**
	 * getter de la varible d'instance frequence
	 * @return frequence
	 */
	public static int getFrequence(){
		return frequence;
	}

	/**
	 * 
	 * 
	 */
	public static void choseLimitTemp(){
		boolean correct = false;
		System.out.println(langue == 0? "Veuillez entrer la temperature maximale te minimale"
				+ " sous la forme tempMax-tempMin (ex:10-13)" : "Please enter "
				+ " following that pattern tempMax-tempMin (ex:10-13)");
		while(!correct){
			String[] str = TextIO.getln().split("-");
			try{
				tempMax = Integer.parseInt(str[0]);
				tempMin = Integer.parseInt(str[1]);
				
				correct = true;
			}
			catch(NumberFormatException e){
				System.out.println(langue == 0 ? "Veuillez entrer des nombres valides" : "Please enter valides numbers");
			}
			catch(IndexOutOfBoundsException e){
				System.out.println(langue == 0 ? "Veuillez respecter le format tempMax-tempMin (ex:10-13)" : "Please follow the pattern tempMax-tempMin (ex:10-13)");
			}

		}
	}
	
	/**
	 * 
	 * @return tempMax
	 */
	public static int getTempMax(){
		return tempMax;
	}

	/**
	 * 
	 * @return tempMin
	 */
	public static int getTempMin(){
		return tempMin;
	}

	public static void choseLimitHum(){
		boolean correct = false;
		System.out.println(langue == 0? "Veuillez entrer l'humidite maximale te minimale"
				+ " sous la forme humMax-humMin (ex:30-50)" : "Please enter "
				+ " following that pattern humMax-humMin (ex:30-50)");
		while(!correct){
			String[] str = TextIO.getln().split("-");
			try{
				humMax = Integer.parseInt(str[0]);
				humMin = Integer.parseInt(str[1]);
				
				correct = true;
			}
			catch(NumberFormatException e){
				System.out.println(langue == 0 ? "Veuillez entrer des nombres valides" : "Please enter valides numbers");
			}
			catch(IndexOutOfBoundsException e){
				System.out.println(langue == 0 ? "Veuillez respecter le format humMax-humMin (ex:30-50)" : "Please follow the pattern humMax-humMin (ex:30-50)");
			}
		}
	}
	
	/**
	 * 
	 * @return humMax
	 */
	public static int getHumMax(){
		return humMax;
	}
	
	/**
	 * 
	 * @return humMin
	 */
	public static int getHumMin(){
		return humMin;
	}
	
	/**
	 * Execution generale du programme
	 * @param args
	 */
	public static boolean arret = true;

	public static void main(String args[]){
		System.out.println("Bienvenue dans ce programme de station meteo | Welcome in this weather station program\n");
		chosePushKey();
		//settings();


		try{
			StudentPushBulletAnswering.push(pushKey);
			do{
				
				Thread.sleep(3000);
				Menu.displayMenu();
				
			}
			while(arret);

		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}

		catch (InterruptedException e) {

			e.printStackTrace();
		}
		System.exit(0);
	}

}

