import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Jessica Chambers
 * @version 1
 * 
 */
public class Tabou implements Voisin {
	private ArrayList<Voisin> voisins;// etats voisins de l'etat courant
	private ArrayList<Etat> tabous;// etats visités donc interdits
	private int maxMvt=20;//nombre max de mouvements permis
	

	public Tabou() {
		this.voisins = new ArrayList<Voisin>();
		this.tabous = new ArrayList<Etat>();
	}

	@Override
	public List<Voisin> voisin() {
		
		return this.voisins;
	}

	@Override
	public double eval() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void displayPath() {
		// TODO Auto-generated method stub

	}

	/*sBest ← s0
bestCandidate ← s0
tabuList ← []
tabuList.push(s0)
while (not stoppingCondition())
	sNeighborhood ← getNeighbors(bestCandidate)
	bestCandidate ← sNeighborHood.firstElement
	for (sCandidate in sNeighborHood)
		if ( (not tabuList.contains(sCandidate)) and (fitness(sCandidate) > fitness(bestCandidate)) )
			bestCandidate ← sCandidate
		end
	end
	if (fitness(bestCandidate) > fitness(sBest))
		sBest ← bestCandidate
	end
	tabuList.push(bestCandidate)
	if (tabuList.size > maxTabuSize)
		tabuList.removeFirst()
	end
end
return sBest*/
}
