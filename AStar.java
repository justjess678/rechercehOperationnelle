import java.util.ArrayList;

/**
 * 
 * @author Jessica Chambers
 * @version 1
 * 
 */
public class AStar {

	protected ArrayList<Etat> vus;
	protected ArrayList<Etat> enAttente;

	/**
	 * Constructor, all variables are empty
	 */
	public AStar() {
		this.vus = new ArrayList<Etat>();
		this.enAttente = new ArrayList<Etat>();
	}

	// NOTES:
	// cout de l'etat = e.h();
	// cout de transfert vers etat e2 = e.k(e2);
	/**
	 * Method using the A star method to scour the graph to find the solution
	 * 
	 * @param e0
	 *            startgin point state
	 * @return true if solution was found, false if not
	 */
	public boolean parcours(Etat e0) {
		this.enAttente.add(e0);
		int iterations = 0;
		Etat e = null;
		while (!this.enAttente.isEmpty()) {
			//clean out the waiting list to avoid doubles
			for (Etat i : this.vus) {
				if (this.enAttente.contains(i)) {
					this.enAttente.remove(i);
				}
			}
			e = this.enAttente.get(0);
			// gets cheapest road
			for (Etat i : this.enAttente) {
				if (i.h() < e.h()) {
					e = i;
				}
			}
			this.enAttente.remove(e);
			if (e.estSolution()) {
				System.out.println(this.toString() + "\n Success!");
				System.out.println("Iterations to solution:" + iterations);
				return true;
			} else {
				for (Etat i : e.successeurs()) {
					if (!this.enAttente.contains(i) && !this.vus.contains(i)) {
						// JC: on ajoute l'etat fils a la liste
						this.enAttente.add(i);
					}
					// si l'etat i est dans la liste mais que le i en entree a
					// une meilleure heuristique, on le remplace
					if (this.enAttente.contains(i) && !this.vus.contains(i)) {
						int tmp = this.enAttente.indexOf(i);
						if (i.h() + i.k(e) < this.enAttente.get(tmp).h()
								+ this.enAttente.get(tmp).k(e)) {
							this.enAttente.remove(tmp);
							this.enAttente.add(i);
						}
					}
				}
				// JC: on ajoute l'etat courant e a la liste des etats vus
				this.vus.add(e);
				iterations++;
			}
		}
		System.out.println(this.toString() + "\n Solution not found");
		System.out.println("Iterations:" + iterations);
		return false;
	}

	@Override
	/**
	 * To String shows current states that have been "Seen" by the algorithm
	 */
	public String toString() {
		String s = "A star\nVus:";
		for (int i = 0; i < this.vus.size(); i++) {
			s += this.vus.get(i).toString() + "\n";
		}

		s += "\nEn Attente:";
		for (int i = 0; i < this.enAttente.size(); i++) {
			s += this.enAttente.get(i).toString()+"\n";
		}

		return s;
	}

	public ArrayList<Etat> getVus() {
		return vus;
	}

	public ArrayList<Etat> getEnAttente() {
		return enAttente;
	}

}
