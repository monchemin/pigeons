package tp.poo.pigeon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/* classe Main
 * creatin de la scène avec les informations de la classe Config
 * la methode creation pigeon crée les pigeons en leur assignant une 
 * couleur aleratoire fournie par la classe CouleurPigeon
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
        		
        		
        	}
        });
       
			timer = new Timer();
		   	 
			//this.timer.schedule(new BackgroundTask(this.lastClicked, this.tabPigeon), 0, 1000);
			this.timer.schedule(new BackgroundTask(this), 0, 5000);
			//timer.cancel();
	        
		
		
        
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
			Pigeon monPigeon = new Pigeon(x, y, c); // instaciation de pigeon
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
		} catch (NullPointerException e){ e.printStackTrace();}
		this.deplacementPigeon(x, y);
	}
	public void deplacementPigeon(double x, double y)
	{// deplacement des pigeon par thread
		if(this.tabPigeon != null)
		{
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
