import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;

/**
 * 
 * @author Jessica Chambers
 * @version 1
 * 
 */
public class Tabou implements Voisin {
	private ArrayList<Voisin> voisins = new ArrayList<Voisin>();// etats voisins
																// de l'etat
																// courant
	private int maxMvt = 20;// nombre max de mouvements permis
	private double valeur;

	/**
	 * constructor from town graph: uses towngrpah to generate a TownTSP type
	 * whoch become the current neighbour
	 * 
	 * @param tg
	 *            TownGraph
	 */
	public Tabou(TownGraph tg) {
		TownTSP tsp = new TownTSP(tg);
		this.valeur = tsp.eval();
	}

	/**
	 * returns list of neighbours (not taboos) of current neighbour
	 */
	@Override
	public List<Voisin> voisin() {
		return this.voisins;
	}

	/**
	 * returns value of current neighbour
	 */
	@Override
	public double eval() {
		return this.valeur;
	}

	@Override
	public void displayPath() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method that runs the tabu algorithm on the current neighbour
	 * @return 
	 * 
	 * @return new taboo neighbour or null if none
	 */
	public List<Voisin> algo(Voisin premier) {
		int iterations = 0;
		Voisin e = premier;
		Voisin eStar = e;
		Voisin ePrime;
		this.voisins.addAll(premier.voisin());
		List<Voisin> tab = new ArrayList<>();
		for (int m = 0; m < this.maxMvt; m++) {
			// e' = melleur de V
			ePrime = this.voisins.get(0);
			if (m == 0) {
				this.voisins.remove(0);
			}
			for (Voisin v : this.voisins) {
				if (v.eval() < ePrime.eval())
					ePrime = v;
			}
			if (ePrime.eval() >= e.eval()) {
				// pas d'amelioration
				if (this.voisins.contains(e))
					this.voisins.remove(e);
				tab.add(e);
			}
			e = ePrime;
			if (eStar.eval() >= ePrime.eval()) {
				eStar = ePrime;
			}
			iterations = m;
			e.displayPath();
		}
		System.out.println("\nIterations : " + iterations);
		return tab;
	}

	@Override
	public String toString() {
		String s = "\nValeur : " + this.valeur
				+ "\nValeurs des voisins\n------------------\n";
		for (int i = 0; i < this.voisins.size(); i++) {
			s += this.voisins.get(i).eval();
			s += "\n";
		}
		return s;
	}

	/*
	 * sBest ← s0 bestCandidate ← s0 tabuList ← [] tabuList.push(s0) while (not
	 * stoppingCondition()) sNeighborhood ← getNeighbors(bestCandidate)
	 * bestCandidate ← sNeighborHood.firstElement for (sCandidate in
	 * sNeighborHood) if ( (not tabuList.contains(sCandidate)) and
	 * (fitness(sCandidate) > fitness(bestCandidate)) ) bestCandidate ←
	 * sCandidate end end if (fitness(bestCandidate) > fitness(sBest)) sBest ←
	 * bestCandidate end tabuList.push(bestCandidate) if (tabuList.size >
	 * maxTabuSize) tabuList.removeFirst() end end return sBest
	 */
}
