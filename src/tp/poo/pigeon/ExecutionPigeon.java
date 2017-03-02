package tp.poo.pigeon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;

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
public Long lastClicked = 1L; //le temps de la dernière nourriture
Timer timer;
BackgroundTask tache;
	@Override
 public void start(Stage primaryStage) {
        
		
        root = new Group();
        // creation des pigeons 
        CreationPigeon();
        //System.out.println("Ajouts de " + Config.NPIGEON + " pigeons.\n");
       //mise en page de la scène
        Scene scene = new Scene(root, Config.WLARGEUR, Config.WHAUTEUR);
        scene.setFill(Color.WHITE);
        primaryStage.setTitle(Config.APPNAME);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        scene.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	public void handle(MouseEvent me){
        		//System.out.println(lastClicked);
        		addNourriture(me.getSceneX(), me.getSceneY()); //appel ajout de nourriture
        		lastClicked = System.currentTimeMillis();
        		//timer = new Timer();
   		   	 
    			//timer.schedule(new BackgroundTask(lastClicked, tabPigeon, tabNourriture), 0, 1000);
    			//this.timer.schedule(new BackgroundTask(this), 0, 5000);
        		
        		
        	}
        });
        
       //classe anonyme qui assure le scheduled 
       //il vérifie l'état des nourritures et fait disparaitre une nourriture non fraiche
      //il s'excute à chaque intervalle de Config.ATTENTE
        //il s'execute 5 seconde apprès le lancement
        ScheduledService<Void> backTask = new ScheduledService<Void>(){

  		  @Override
  		  protected Task<Void> createTask() {
  		    return new Task<Void>(){

  		     @Override
  		     protected Void call() throws Exception {
  		       //System.out.println("appel de service : " + System.currentTimeMillis());
  		     if(System.currentTimeMillis() > lastClicked + Config.ATTENTE)//le temps d'attente depassé
  			{
  		    	 	
  				if(tabPigeon.size() != 0 && tabNourriture.size() == 0)
  				{
  					
  					Iterator lepigeon = tabPigeon.iterator(); //iteration sur arraylist tabPigeon
  					while(lepigeon.hasNext())
  					{
  						Pigeon prochain = (Pigeon) lepigeon.next(); // prochain pigeon
  						prochain.setInitial();
  							
  					}
  				}
  			}
  			for(Iterator<Nourriture> n = tabNourriture.iterator(); n.hasNext(); )
  			{
  				Nourriture i = (Nourriture) n.next();
  				if(System.currentTimeMillis() > i.tempsCreation + Config.DUREENOURRITURE)
  				{
  					i.setVisible(false); // cache la nourriture
  					tabNourriture.remove(i); // supprime la nourriture dans la table
  				}
  				
  			}
  		        return null;
  		      }
  		    };
  		  }
  		};
  	backTask.setDelay(Duration.seconds(2));
  	backTask.setPeriod(Duration.seconds(3));
  	backTask.start();
    }
	
	
  //creation des pigeons
	public void CreationPigeon()
	{
		for(int i=1 ; i<= Config.NPIGEON; i++)
		{
			// System.out.println(i);
			int x = new Random().nextInt(Config.WLARGEUR-100); //x random -100 pour ne pas déborder
			int y = new Random().nextInt(Config.WHAUTEUR-100);// y random
			Color c = new CouleurPigeon().getColor(); // color random
			Pigeon monPigeon = new Pigeon(x, y, c); // instanciation de pigeon
			//System.out.println(monPigeon);
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
		} catch (NullPointerException e){
			//System.out.println("ajout de nourriture");
			e.printStackTrace();
		}
		try{
		this.deplacementPigeon(x, y);
		}catch (IOException e){e.printStackTrace();}
	}
	public void deplacementPigeon(double x, double y) throws IOException
	{// deplacement des pigeon par thread
		if(this.tabPigeon.size() != 0)
		{ //System.out.println(tabPigeon.size());
			
			Iterator lepigeon = this.tabPigeon.iterator(); //iteration sur arraylist tabPigeon
			
			while(lepigeon.hasNext())
			{
				Pigeon prochain = (Pigeon) lepigeon.next(); // prochain pigeon
				Thread tDeplacement = new Thread(new LancementDeplacement(prochain, x, y));//lancement du thread
				tDeplacement.start(); //execution
				//prochain.deplacement(x, y);
				
			}
		}
	}
	public static void main(String[] args) {
		launch(args);
		

	}
}



