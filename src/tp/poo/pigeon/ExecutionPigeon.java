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
 * creation de la sc�ne avec les informations de la classe Config
 * la methode creation pigeon cr�e les pigeons en leur assignant une 
 * couleur aleatoire fournie par la classe CouleurPigeon
 * chaque pigeon est positionn� aleatoirement sur la sc�ne
 * 
 */

public class ExecutionPigeon extends Application {

Group root;	
ArrayList<Pigeon> tabPigeon = new ArrayList<Pigeon>();
ArrayList<Nourriture> tabNourriture = new ArrayList<Nourriture>();
ArrayList<Timeline> runningThread = new ArrayList<Timeline>();
public Long lastClicked = 1L; //le temps de la derni�re nourriture
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
       //mise en page de la sc�ne
        Scene scene = new Scene(root, Config.W_LARGEUR, Config.W_HAUTEUR);
        scene.setFill(Color.WHITE);
        primaryStage.setTitle(Config.APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        scene.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	public void handle(MouseEvent me){     		
        		addNourriture(me.getSceneX(), me.getSceneY()); //appel ajout de nourriture
        		tempsSommeil = new Random().nextInt(60);//Temps aleatoire avant effrayer les pigeons
        		lastClicked = System.currentTimeMillis();
        		firstClic = true;
        	}
        });
        
        //classe anonyme qui assure le scheduled 
        //v�rifie l'�tat des nourritures et fait disparaitre une nourriture non fraiche
        //s'excute � chaque intervalle de Config.ATTENTE
        //s'execute 5 secondes appr�s le lancement
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
	  	  						 * Quand un pigeon a mang� la nourriture, il faut que les autres pigeons soient inform�s et partent se balader
	  	  						 */
	  	  						retourDomicile();
	  	  					}
	  	  				}
	  	  				
	  	  				if(System.currentTimeMillis() > i.tempsCreation + Config.DUREE_NOURRITURE)//La nourriture n'est plus fraiche
	  	  				{
	  	  					
	  	  					System.out.println("Position nourriture (" + i.getPositionX() + "," + i.getPositionY() + ")");
	  						
	  						/*Iterator<Pigeon> lepigeon = tabPigeon.iterator(); //iteration sur arraylist tabPigeon
		  					while(lepigeon.hasNext())
		  					{
		  						Pigeon p = lepigeon.next();
		  	  					System.out.println("Position  pigeon " + p.getColor().toString()+ " (" + p.getPositionX()+ "," + p.getPositionY() + ")");
		  								  						
		  					}*/
		  					
	  	  					i.setVisible(false); // cache la nourriture
	  	  					tabNourriture.remove(i); // supprime la nourriture dans la table
	  	  					
	  	  				}
	  	  				
	  	  			}
	
	  		    	/*if(tabNourriture.isEmpty()) // test de l'existence de nourriture
		  			{
	  				
	  				for(int z = 0; z < runningThread.size(); z++ )
		  	  			{ 			
	  						System.out.println("le thread qui va se stopper : " + z);
		  	  				//runningThread.get(z).stop(); // blocage de mouvement
		  	  				//runningThread.remove(runningThread.get(z)); // suppression du mouvement
		  	  			}
		  			}*/
	  		      
	  		     if(System.currentTimeMillis() > lastClicked + tempsSommeil)//les pigeons sont effrayes
	  			{
	  		    
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
			int x = new Random().nextInt(Config.W_LARGEUR-100); //x random -100 pour ne pas d�border
			int y = new Random().nextInt(Config.W_HAUTEUR-100);// y random
			Color c = new CouleurPigeon().getColor(); // color random
			Pigeon monPigeon = new Pigeon(x, y, c); // instanciation de pigeon
			//System.out.println(monPigeon);
		    this.root.getChildren().add(monPigeon); // ajout � la scene
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
		} catch (NullPointerException e){
			//System.out.println("ajout de nourriture");
			e.printStackTrace();
		}
		try{
			System.out.println("Manger !");
			this.deplacementNourriture(x, y);
		}catch (Exception e){System.out.println("");;}
	}
	
	public void deplacementNourriture(double x, double y) throws Exception
	{// deplacement des pigeon par thread vers une nourriture
		//System.out.println("entre");
		if(this.tabPigeon.size() != 0)
		{ //System.out.println(tabPigeon.size());
			Iterator<Pigeon> lepigeon = this.tabPigeon.iterator(); //iteration sur arraylist tabPigeon
			while(lepigeon.hasNext())
			{
				Pigeon prochain = (Pigeon) lepigeon.next(); // prochain pigeon
				//System.out.println("ipe");
				Timeline tprochain = new PreparationMouvement(prochain, x, y).moov();
				Thread tDeplacement = new Thread(new LancementDeplacement(tprochain));//lancement du thread
				runningThread.add(tprochain);
				
				tDeplacement.start(); //execution
				//prochain.deplacement(x, y);	
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
			//deplacementBallade(prochain);
			int x = new Random().nextInt(Config.W_LARGEUR-100); //x random -100 pour ne pas d�border
			int y = new Random().nextInt(Config.W_HAUTEUR-100);// y random
			Timeline tprochain = new PreparationMouvement(Joey, Joey.getPositionInitialX(), Joey.getPositionInitialY() ).moov();
			Thread tDeplacement = new Thread(new LancementDeplacement(tprochain));
			runningThread.add(tprochain);
			
			tDeplacement.start();
		}
		
	}
	
	public void deplacementBallade() throws Exception{
		
		Iterator<Pigeon> lepigeon = tabPigeon.iterator(); //iteration sur arraylist tabPigeon
		System.out.println("je me balade");
		while(lepigeon.hasNext())
		{
			Pigeon Joey = (Pigeon) lepigeon.next(); // prochain pigeon
			//deplacementBallade(prochain);
			int x = new Random().nextInt(Config.W_LARGEUR-100); //x random -100 pour ne pas d�border
			int y = new Random().nextInt(Config.W_HAUTEUR-100);// y random
			Timeline tprochain = new PreparationMouvement(Joey, x, y ).moov();
			Thread tDeplacement = new Thread(new LancementDeplacement(tprochain));
			runningThread.add(tprochain);
			
			tDeplacement.start();

		}
		SommeilPigeon = true;
		
	}
	
	public static void main(String[] args) {
		launch(args);
		

	}
}



