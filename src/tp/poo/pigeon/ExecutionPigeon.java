package tp.poo.pigeon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
/* classe Main
 * creation de la scène avec les informations de la classe Config
 * la methode creation pigeon crée les pigeons en leur assignant une 
 * couleur aleatoire fournie par la classe CouleurPigeon
 * chaque pigeon est positionné aleatoirement sur la scène
 * 
 */

public class ExecutionPigeon extends Application {

Group root;	
ArrayList<Pigeon> tabPigeon = new ArrayList<Pigeon>();
ArrayList<Nourriture> tabNourriture = new ArrayList<Nourriture>();
ArrayList<Timeline> runningThread = new ArrayList<Timeline>();
public Long lastClicked = 1L; //le temps de la dernière nourriture
Timer timer;
boolean firstClic = false;
boolean SommeilPigeon = true;
int tempsSommeil;

	@Override
 public void start(Stage primaryStage) {
        
		
        root = new Group();
        // creation des pigeons 
        CreationPigeon();
        //System.out.println("Ajouts de " + Config.NPIGEON + " pigeons.\n");
       //mise en page de la scène
        Scene scene = new Scene(root, Config.W_LARGEUR, Config.W_HAUTEUR);
        scene.setFill(Color.WHITE);
        primaryStage.setTitle(Config.APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        tempsSommeil = Config.TEMPS_SOMMEIL;
        scene.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	public void handle(MouseEvent me){     		
        		addNourriture(me.getSceneX(), me.getSceneY()); //appel ajout de nourriture
        	//	tempsSommeil = new Random().nextInt(3000);//Temps aleatoire avant effrayer les pigeons
        		lastClicked = System.currentTimeMillis();
        		firstClic = true;
        	}
        });
        
        //classe anonyme qui assure le scheduled 
        //vérifie l'état des nourritures et fait disparaitre une nourriture non fraiche
        //s'excute à chaque intervalle de Config.ATTENTE
        //s'execute 5 secondes apprès le lancement
        ScheduledService<Void> backTask = new ScheduledService<Void>(){

  		  @Override
  		  protected Task<Void> createTask() {
  		    return new Task<Void>(){

  		     @Override
	  		     protected Void call() throws Exception {
	  		    	
	  		    	for(Iterator<Nourriture> n = tabNourriture.iterator(); n.hasNext(); )
	  	  			{
	  	  				Nourriture i = (Nourriture) n.next();
	  	  				
	  	  				//On essai de trouver quand les pigeons sont sur la nourriture
	  	  				Iterator<Pigeon> lepigeon = tabPigeon.iterator();
	  	  				while(lepigeon.hasNext()){
	  	  					
	  	  					Pigeon p = lepigeon.next();
	  	  					
	  	  					if((p.getPositionX()==i.getPositionX())&&(p.getPositionY()==i.getPositionY())){
	  	  						System.out.println("Prem's !");
	  	  						i.setVisible(false);
	  	  						tabNourriture.remove(i);
	  	  						/*
	  	  						 * Quand un pigeon a mangé la nourriture, il faut que les autres pigeons soient informés et partent se balader
	  	  						 */
	  	  						retourDomicile();
	  	  					}
	  	  				}
	  	  				
	  	  				if(System.currentTimeMillis() > i.tempsCreation + Config.DUREE_NOURRITURE)//La nourriture n'est plus fraiche
	  	  				{
	  	  					
	  	  					//System.out.println("Position nourriture (" + i.getPositionX() + "," + i.getPositionY() + ")");
	  	  					i.setVisible(false); // cache la nourriture
	  	  					tabNourriture.remove(i); // supprime la nourriture dans la table
	  	  					
	  	  				}
	  	  				
	  	  			}
	
	  		    	
	  		     if(System.currentTimeMillis() > lastClicked + tempsSommeil)//les pigeons sont effrayes
	  			{
	  		    	 	SommeilPigeon = false;
	  				if(tabPigeon.size() != 0 && firstClic && tabNourriture.size()==0 && !SommeilPigeon)
	  				{
	  					deplacementBallade();	
	  				}
	  			}
	  		        return null;
  		      }
  		    };
  		  }
  		};
  		
  	backTask.setDelay(Duration.seconds(1));
  	backTask.setPeriod(Duration.seconds(Config.SERVICE_DELAY_TIME));
  	backTask.start();
  	
    }
	
	
  //creation des pigeons
	public void CreationPigeon()
	{
		for(int i=1 ; i<= Config.NPIGEON; i++)
		{
			// System.out.println(i);
			int x = new Random().nextInt(Config.W_LARGEUR-100); //x random -100 pour ne pas déborder
			int y = new Random().nextInt(Config.W_HAUTEUR-100);// y random
			Color c = new CouleurPigeon().getColor(); // color random
			Pigeon monPigeon = new Pigeon(x, y, c); // instanciation de pigeon
			this.root.getChildren().add(monPigeon); // ajout à la scene
		    try {
		    	this.tabPigeon.add(monPigeon);
		    	} catch (NullPointerException e){ e.printStackTrace();}

		}
	}
	//ajout de nourriture sur la scene au clic de la souris
	public void addNourriture(double x, double y)
	{
		Nourriture nourriture = new Nourriture(x, y); // creation
		root.getChildren().add(nourriture); //ajout a scene
		try{
		tabNourriture.add(nourriture); //insertion dans la table
		SommeilPigeon = false;
		} catch (NullPointerException e){e.printStackTrace();}
		try{
			this.deplacementNourriture(x, y);
		 	}catch (Exception e){e.printStackTrace();}
	}
	
	public void deplacementNourriture(double x, double y) throws Exception
	{// deplacement des pigeon par thread vers une nourriture
		
		if(this.tabPigeon.size() != 0)
		{ 
			Iterator<Pigeon> lepigeon = this.tabPigeon.iterator(); //iteration sur arraylist tabPigeon
			while(lepigeon.hasNext())
			{
				Pigeon prochain = (Pigeon) lepigeon.next(); // prochain pigeon
				Timeline tprochain = new PreparationMouvement(prochain, x, y).moov();
				Thread tDeplacement = new Thread(new LancementDeplacement(tprochain));//lancement du thread
				runningThread.add(tprochain);
				tDeplacement.start(); //execution
			}
		}
	}
	
	public void retourDomicile() throws Exception{
		/*
		 * LES PIGEONS UNE FOIS DE RETOUR NE SARRETENT PAS 
		 */
		Iterator<Pigeon> lepigeon = tabPigeon.iterator();
		System.out.println("on rentre ! ");
		while(lepigeon.hasNext())
		{
			Pigeon Joey = (Pigeon) lepigeon.next(); // prochain pigeon
			Timeline tprochain = new PreparationMouvement(Joey, Joey.getPositionInitialX(), Joey.getPositionInitialY() ).moov();
			Thread tDeplacement = new Thread(new LancementDeplacement(tprochain));
			runningThread.add(tprochain);
			tDeplacement.start();
		}
		
	}
	
	public void deplacementBallade() throws Exception{
		
		Iterator<Pigeon> lepigeon = tabPigeon.iterator(); //iteration sur arraylist tabPigeon
		System.out.println("je me balade à : " + System.currentTimeMillis());
		while(lepigeon.hasNext())
		{
			Pigeon Joey = (Pigeon) lepigeon.next(); // prochain pigeon
			//deplacementBallade(prochain);
			int x = new Random().nextInt(Config.W_LARGEUR-100); //x random -100 pour ne pas déborder
			int y = new Random().nextInt(Config.W_HAUTEUR-100);// y random
			Timeline tprochain = new PreparationMouvement(Joey, x, y ).moov();
			Thread tDeplacement = new Thread(new LancementDeplacement(tprochain));
			runningThread.add(tprochain);
			
			tDeplacement.start();

		}
		tempsSommeil += Config.TEMPS_SOMMEIL;
		System.out.println("prochain déplacement : " + tempsSommeil);
		
	}
	
	public static void main(String[] args) {
		launch(args);
		

	}
}



