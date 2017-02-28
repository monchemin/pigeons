package tp.poo.pigeon;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/* classe Main
 * creatin de la scène avec les informations de la classe Config
 * la methode creation pigeon crée les pigeons en leur assignant une 
 * couleur aleratoire fournie par la classe CouleurPigeon
 * chaque pigeon est positionné aleatoirement sur la scène
 * 
 */

public class ExcutionPigeon extends Application {

Group root;	

	@Override
 public void start(Stage primaryStage) {
        
        
        root = new Group();
        // creation des pigeons 
       CreationPigeon();
       //mise en page de la scène
        Scene scene = new Scene(root, Config.WLARGEUR, Config.WHAUTEUR);
        scene.setFill(Color.ALICEBLUE);
        primaryStage.setTitle(Config.APPNAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	public void CreationPigeon()
	{
		for(int i=1 ; i<= Config.NPIGEON; i++)
		{
			 
			int x = new Random().nextInt(Config.WLARGEUR); //x random
			int y = new Random().nextInt(Config.WLARGEUR);// y random
			Color c = new CouleurPigeon().getColor(); // color random
			Pigeon monPigeon1 = new Pigeon(x, y, c); // instaciation de pigeon
		        this.root.getChildren().add(monPigeon1); // ajout à la scene
		        
		        //application de la scene
		}
	}
	public static void main(String[] args) {
		launch(args);

	}
}
