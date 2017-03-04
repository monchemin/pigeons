package tp.poo.pigeon;

//classe d'initilisation des parametres à utiler
public class Config {
	public static String APP_NAME = "LES PIGEONS POO";
//dimensions de la fenetre
	public static int W_LARGEUR = 1000;
	public static int W_HAUTEUR = 700;
	//nombre de pigeon 
	public static int NPIGEON = 10;
	//vitesse des pigeons
	public static int TEMPS_DE_PLACEMENT = 10000; //(ms)
	public static int RETOUR_INITIAL = 1; // temps pour revenir à la position initiale
	public static int DUREE_NOURRITURE = 10000; // (ms)duree de vie d'une nourriture
	public static int CLIC_ATTENTE = 10; //seconde
	public static int TEMPS_SOMMEIL = 30000;
	
	//parametres du service de vérification
	
	public static int SERVICE_DELAY_TIME = 3; // temps d'attente avant lancement service de vérification
	
	
}
