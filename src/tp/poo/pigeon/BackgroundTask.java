package tp.poo.pigeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;

public class BackgroundTask extends TimerTask {

	long ledernier;
	ArrayList<Pigeon> listPigeon = new ArrayList<Pigeon>();
	ArrayList<Nourriture> listNourriture = new ArrayList<Nourriture>();
	ExecutionPigeon p;
	long actuel;
	public BackgroundTask(long ledernier, ArrayList<Pigeon> listPigeon, ArrayList<Nourriture> listn) {
		this.ledernier = ledernier;
		this.listPigeon = listPigeon;
		this.listNourriture = listn;
		
	}
	public BackgroundTask(ExecutionPigeon p) {
		this.p = p;
		
	}
	@Override
	public void run() {
		this.actuel = System.currentTimeMillis();
		//System.out.println("actuel : " + actuel + " \nledernier : +" + p.lastClicked);
		if(actuel > this.ledernier + Config.ATTENTE)//le temps d'attente depassé
		{
			
			if(this.listPigeon != null)
			{
				Iterator lepigeon = this.listPigeon.iterator(); //iteration sur arraylist tabPigeon
				while(lepigeon.hasNext())
				{
					Pigeon prochain = (Pigeon) lepigeon.next(); // prochain pigeon
					prochain.setInitial();
						
				}
			}
		}
		for(Iterator<Nourriture> n = this.listNourriture.iterator(); n.hasNext(); )
		{
			Nourriture i = (Nourriture) n.next();
			if(this.actuel > i.tempsCreation + Config.DUREENOURRITURE) i.setVisible(false);
			
		}
		
	}

}
