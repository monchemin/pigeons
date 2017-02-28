package tp.poo.pigeon;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;

public class CouleurPigeon {

	protected  ArrayList<Color> tab; // pour garder les couleur
	
	public CouleurPigeon() {
		//ajout des couleurs à tavers la classe java Color
		tab = new ArrayList<Color>();
		tab.add(Color.BLACK);
		tab.add(Color.BLUE);
		tab.add(Color.BISQUE);
		tab.add(Color.AQUAMARINE);
		tab.add(Color.BLUEVIOLET);
		tab.add(Color.DARKBLUE);
		tab.add(Color.BLANCHEDALMOND);
		tab.add(Color.CHOCOLATE);
	}
	public Color getColor()
	{
		//choisi une couleur aleatoire 
		return tab.get(new Random().nextInt(tab.size()));
	}
}
