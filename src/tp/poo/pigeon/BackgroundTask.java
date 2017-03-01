package tp.poo.pigeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;
//Met a jour les elements suivants
// Retour des pigeons à leurs positions initiale 
// Disparition de la nourriture qui n'est plus fraîche
public class BackgroundTask extends TimerTask {

	long ledernier;
	ArrayList<Pigeon> listPigeon = new ArrayList<Pigeon>();
	ExecutionPigeon p;
	long actuel;
	public BackgroundTask(long ledernier, ArrayList<Pigeon> listPigeon) {
		this.ledernier = ledernier;
		this.listPigeon = listPigeon;
		
	}
	
	public BackgroundTask(ExecutionPigeon p) {
		this.p = p;
		
	}
	@Override
	public void run() {
		this.actuel = System.currentTimeMillis();
		//System.out.println("actuel : " + actuel + " \nledernier : +" + p.lastClicked);
		
		if(actuel > p.lastClicked + Config.ATTENTE)//le temps d'attente depassé
		{

			if(this.listPigeon != null)
			{
				Iterator lepigeon = p.tabPigeon.iterator(); //iteration sur arraylist tabPigeon
				while(lepigeon.hasNext())
				{
					Pigeon prochain = (Pigeon) lepigeon.next(); // prochain pigeon
					prochain.setInitial();
						
				}
			}
		}
		for(Iterator<Nourriture> n = p.tabNourriture.iterator(); n.hasNext(); )
		{
			Nourriture i = (Nourriture) n.next();
			if(this.actuel > i.tempsCreation + Config.DUREENOURRITURE) i.setVisible(false);
			
		}
		
	}

}
