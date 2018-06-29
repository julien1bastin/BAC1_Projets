
import java.util.Scanner;

import mcq.Choice;
import mcq.AssessmentFormLoader;
import mcq.Question;
import mcq.AssessmentForm;
/**
 * Un simulateur de questionnaire à choix multiple qui fonctionne en mode console.
 * 
 * @author : Bastin Julien, Riat Nicky, Roger France Gauthier
 * @version 29/10/2015
 */
public final class AssessmentFormConsoleFinal
{
    public static String informationEleve;
    
    /**
     * Evaluation de la réponse
     * @param q est la question
     * @param choiceIdx est l'indice de la réponse
     * @param numQ est le numéro de la question
     * @return un tableau avec les notes des différentes cotations
     */
    public static double[] grader(Question q, int choiceIdx){
        double answers[] = new double[3];
        Choice [] choices = q.getChoices();
        Choice choice = choices[choiceIdx];
        // on teste si choiceIdx est bien un choix correct pour cette question
        if (q.isCorrectChoice(choiceIdx)) {
             answers[0] = 1; //stocke dans le tableau à l'indice du choix de cotation la valeur pour ce choix de cotation
             answers[1] = 1;
             answers[2] = 1;
        } 
        else {
            answers[0] = 0;
            answers[1] = -1;
            answers[2] = -0.5;
        }
        return answers;
    }
    
    /**
     * 1) Affiche la question dans la console et 
     * 2) propose les réponses/choix possibles à cette question
     * 3) demande à l'utilisateur un choix parmi une ces réponse 
     * @param q est la question à proposer à l'utilisateur
     * @return l'indice de la réponse faite par l'utilisateur après présentation de la question et des réponses possibles 
     */
    public static int getAnswer(Question q) {
        
       // affiche la question avec une mise en forme amelioree
       System.out.println("=================================================================================================");
       System.out.println("Question : " + q.getWording());
       System.out.println("=================================================================================================");
       System.out.printf("| %-17s | %-30s |\n", "Numero de reponse", "Reponses");
       System.out.println("-------------------------------------------------------------------------------------------------");
        // on regarde si la question admet au moins un bonne reponse, sinon la question n'est pas comptabilisee
        if(q.existsCorrectChoice()){
        // recuperation du tableau des choix possibles
        Choice [] choices = q.getChoices();
        // on itère sur les choix
        for (int i = 0; i < choices.length; i++) {
            Choice choice = choices[i];
            // affichage du choix à l'indice i
            System.out.printf("| %-17s | %-30s |\n", "["+i+"]", choice);
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("S'il vous plait, entrez votre choix de reponse et appuyez sur Enter ...");
      
        // on demande à l'étudiant de taper au clavier sa réponse (un entier parmi les choix possibles)
        Scanner in = new Scanner(System.in);
        return in.nextInt();
       }
       else{
           
           System.out.println("La question n'admet pas de bonne reponse, la question n'est pas comptabilisee");
           return -1;
        }
    }
    
    /**
     * Evaluation où l'utilisateur doit rentrer toutes les bonnes reponses pour avoir juste
     * @param q est la qestion
     * @param choiceIdx est l'indice de la réponse choisie
     * @return +1 si l'utilisateur a choisi toutes les bonnes reponses et 0 sinon
     */
    public static double toutesLesReponses(Question q, int[] tabChoix){
        boolean b = true;
        // on itère sur le tableau des reponses choisient par l'utilisateur et on regarde si le choix est bon ou non, si un des choix n'est pas bon, on sort de la boucle et retourne 0
        for(int i = 0; i<tabChoix.length && b; i++){
            b = q.isCorrectChoice(tabChoix[i]);
        }
        
        if (b) {
            return 1;
        } 
        else {
            return 0;
        }
    } 
    
    
    /**
     * 1) Affiche la question dans la console et 
     * 2) propose les réponses/choix possibles à cette question
     * 3) demande à l'utilisateur tous les choix qu'il pense correcte parmi toutes ces réponses
     * @param q est la question à proposer à l'utilisateur
     * @return un tableau avec les indices des réponses choisient par l'uthg statusilisateur après présentation de la question et des réponses possibles 
     */
    public static int[] getMultipleAnswers(Question q) {
        
        // affiche la question avec une mise en forme amelioree
       System.out.println("=================================================================================================");
       System.out.println("Question : " + q.getWording());
       System.out.println("=================================================================================================");
       System.out.printf("| %-17s | %-30s |\n", "Numero de reponse", "Reponses");
       System.out.println("-------------------------------------------------------------------------------------------------");
        // recuperation du tableau des choix possibles
        Choice [] choices = q.getChoices();
        // on itère sur les choix
        for (int i = 0; i < choices.length; i++) {
            Choice choice = choices[i];
            // affichage du choix à l'indice i
            System.out.printf("| %-17s | %-30s |\n", "["+i+"]", choice);
        }
        int[]choix = new int[q.nbCorrectChoices()];
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("S'il vous plait, entrez vos choix de reponse et appuyez sur Enter après chaque choix ... Il y a " + q.nbCorrectChoices() + " bonnes reponses");
      
        // on demande à l'étudiant de taper au clavier toutes les reponses qu'il choisit
        for(int i = 0; i< choix.length; i++){
            Scanner in = new Scanner(System.in);
            choix[i] = in.nextInt();
        }
        return choix;
    }
    
    
     /**
     * Affichage du total en fonction du choix de cotation choisi
     * @param choixCote : le choix de cotation choisi
     * @param questions : le tableau de question tiré du fichié texte
     * @param prng : le prng choisi pour le pseudo-aléatoire
     */
    public static void afficheTotal(int choixCote, Question[] questions, PRNG prng){
        int count = 0; //compteur de questions n'admettant aucune bonne reponse
        double[][] totalScore = new double[questions.length][3]; //tableau dans lequel sera stocké toutes les notes de l'élève par question
        double totalScoreMulti = 0.0; //score totale si l'étudiant choisi le mode multi-réponses
        
        // On itère sur chaque question dans un ordre aléatoire dû au PRNG pour evalue le score de l'étudiant à cette question en fonction de la méthode de cotation choisie précédemment        
        for (int i = 0; i < questions.length; i++) {
            Question q = questions[prng.nextInt(questions.length)];
            // on regarde si il existe au moins une bonne reponse
            if(q.existsCorrectChoice()){
                //on regarde si l'étudiant a choisi le mode multi-reponses ou non
                if(choixCote != 4){
                    double[] answers = grader(q ,getAnswer(q));
                    for(int j = 0; j<3; j++){
                        totalScore[i][j] = answers[j];
                    }
                }
                else{
                    totalScoreMulti += toutesLesReponses(q, getMultipleAnswers(q));
                }
                
            }
            else{
                getAnswer(q);
                count++;
            }
        }

        //calcul des scores finaux en fonction du mode de cotation choisi
        double scoreFinalC1 = 0.0;
        for(int i = 0; i<totalScore.length; i++){
                    scoreFinalC1 += totalScore[i][0];
                }
        double scoreFinalC2 = 0.0;
        for(int i = 0; i<totalScore.length; i++){
                    scoreFinalC2 += totalScore[i][1];
                }
        double scoreFinalC3 = 0.0;
        for(int i = 0; i<totalScore.length; i++){
                    scoreFinalC3 += totalScore[i][2];
                }
                
        // affichage du score final avec une mise en forme améliorée       
        switch(choixCote){
            case 1: 
                System.out.println("*********************************************************************");
                System.out.println("* D'après la cotation +1 ou 0, vous avez obtenu un score de : " + scoreFinalC1 + "/" + (questions.length-count) + " *");
                System.out.println("*********************************************************************");
                break;
            case 2:
                System.out.println("**********************************************************************");
                System.out.println("* D'après la cotation +1 ou -1, vous avez obtenu un score de : " + scoreFinalC2 + "/" + (questions.length-count) + " *");
                System.out.println("**********************************************************************");
                break;
            case 3: 
                System.out.println("**********************************************************************");
                System.out.println("* D'après la cotation +1 ou -0.5, vous avez obtenu un score de : " + scoreFinalC3 + "/" + (questions.length-count) + " *");
                System.out.println("**********************************************************************");
                break;
            case 4: 
                System.out.println("*****************************************************************************************************************");
                System.out.println("* D'après la cotation +1 si toutes les bonnes réponses sont choisi, 0 sinon, vous avez obtenu un score de : " + totalScoreMulti + "/" + (questions.length-count) + " *");
                System.out.println("*****************************************************************************************************************");
                break;
            case 5: 
                System.out.println("**********************************************************************");
                System.out.println("* D'après la cotation +1 ou 0, vous avez obtenu un score de : " + scoreFinalC1 + "/" + (questions.length-count) + " *");
                System.out.println("* D'après la cotation +1 ou -1, vous avez obtenu un score de : " + scoreFinalC2 + "/" + (questions.length-count + " *"));
                System.out.println("* D'après la cotation +1 ou -0.5, vous avez obtenu un score de : " + scoreFinalC3 + "/" + (questions.length-count) + " *");
                System.out.println("**********************************************************************");
                break;
        }
    }
    
    /**
     * Affiche la correction du QCM
     * @param question est le tableau avec toutes les questions
     * @param numQuest est le numéro du questionnaire choisi
     */
    public static void correction(Question[] questions, int numQuest){
        System.out.println();
        System.out.println("Voici la correction du questionnaire :");
        System.out.println();
        for(int i = 0; i < questions.length; i++){
            Question q = questions[i];
            System.out.println("=================================================================================================");
            System.out.println("Question : " + q.getWording());
            System.out.println("=================================================================================================");
            System.out.printf("| %-17s | %-30s | %-10s | %-30s\n", "Numero de reponse", "Reponses", "Correction", "Explication");
            System.out.println("-------------------------------------------------------------------------------------------------");
            Choice[] choices = q.getChoices();
            for (int j = 0; j < choices.length; j++) {
                Choice choice = choices[j];
                // affichage de la réponse avec soit un V si la reponse est vraie, soit X si la reponse est fausse plus une explication si la reponse en possede une
                System.out.printf("| %-17s | %-30s | %-10s | %-30s\n", "["+j+"]", choice, (choice.isCorrect() ? "V" : "X"), choice.getExplanation());
            }
        }
    }
   
    public static void main(String[] args) {
        // Demande du nom du fichier qui contient le QCM et lz stock dans la variable filename
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom du fichier. Exemple : QCM.txt");
        String filename = sc.nextLine(); 
        System.out.println();
        
        // Charchement du QCM depuis le fichier (questions et réponses aux questions)
        AssessmentForm mcq = AssessmentFormLoader.buildQuestionnaire(filename);
        
        //Demande le nom, le prénom et le noma de l'élève et les stocks dans une variable d'instance si quelqu'un veut l'utiliser ultérieurement
        Scanner info = new Scanner(System.in);
        System.out.println("Veuillez entrer votre nom, prénom et noma dans ce format : nom; prenom; noma");
        informationEleve = info.nextLine();
        System.out.println();
 
        // Recupération du tableau des questions
        Question[] questions = mcq.getQuestions();
        
        // Demande quel questionnaire l'élève veut faire pour la généricité du questionnaire grâce au PRNG
        System.out.println("Entrez le numéro du questionnaire entre 0 et " + (questions.length-2));
        int numQuest = sc.nextInt();
        System.out.println();
        PRNG prng = new PRNG(numQuest);
        
        //Demande la méthode de cotation choisie
        Scanner choixCotation = new Scanner(System.in);
        System.out.println("Quelle methode de cotation voulez vous pour le qcm?");
        System.out.println();
        System.out.println("1) +1 pour une bonne reponse, 0 pour une reponse incorrecte \n2) +1 pour une bonne reponse, -1 pour une reponse incorrecte \n3) +1 pour une bonne reponse, -0,5 pour une mauvaise reponse");
        System.out.println("4) +1 si tous les choix de la question sont choisis, 0 sinon \n5) Le résultat de toutes ces cotations en même temps sauf la 4");
        int choixCote = choixCotation.nextInt();
        System.out.println();
        
        //affiche le total en fonction du mode de cotation
        afficheTotal(choixCote, questions, prng);

        //affiche la correction du QCM
        correction(questions, numQuest);
    }
}
