package tp.poo.pigeon;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/* classe Main
 * creatin de la sc�ne avec les informations de la classe Config
 * la methode creation pigeon cr�e les pigeons en leur assignant une 
 * couleur aleratoire fournie par la classe CouleurPigeon
 * chaque pigeon est positionn� aleatoirement sur la sc�ne
 * 
 */

public class ExcutionPigeon extends Application {

Group root;	

	@Override
 public void start(Stage primaryStage) {
        
        
        root = new Group();
        // creation des pigeons 
       CreationPigeon();
       //mise en page de la sc�ne
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
		        this.root.getChildren().add(monPigeon1); // ajout � la scene
		        
		        //application de la scene
		}
	}
	public static void main(String[] args) {
		launch(args);

	}
}
