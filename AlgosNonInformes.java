import java.util.ArrayList;

/**
 * Class containing an implementation of both the breadth-first search algorithm
 * and the depth-first search algorithm
 * 
 * @author Jessica Chambers
 * @version 1
 */
public class AlgosNonInformes {

	protected ArrayList<Etat> vus;
	protected ArrayList<Etat> enAttente;
	protected int compte;

	/**
	 * Constructor, all variables are empty or zero
	 */
	public AlgosNonInformes() {
		this.vus = new ArrayList<Etat>();
		this.enAttente = new ArrayList<Etat>();
		this.compte = 0;
	}

	/**
	 * Function that carries out breadth/width first search in a graph
	 * 
	 * @param type
	 *            "P" for depth-first search, "L" for breadth-first search
	 * @param einit
	 *            starting state
	 * @return true if the solution was found, false if not
	 */
	public boolean parcours(String type, Etat einit) {
		Etat e = einit;
		int iterations = 0;
		this.enAttente.add(einit);
		while (!this.enAttente.isEmpty()) {
			if (e.estSolution()) {
				System.out.println(this.toString() + "\n Success!");
				System.out.println("Iterations to solution:" + iterations);
				return true;
			} else {
				// on ajoute les successeurs de l'état courant
				switch (type) {
				case "L":// parcours en largeur
					// ajoute les fils à la fin de la liste ^
					// puis enleve de debut de la liste
					e = this.enAttente.get(0);
					this.enAttente.remove(0);
					for (Etat i : e.successeurs()) {
						if (!this.vus.contains(i)) {
							this.enAttente.add(i);
						}
					}
					iterations++;
					break;
				case "P":// parcours en profondeur
					// ajoute au début de la liste ^
					// enleve le dernier element de la liste
					e = this.enAttente.get(this.enAttente.size() - 1);
					this.enAttente.remove(this.enAttente.size() - 1);
					for (Etat i : e.successeurs()) {
						if (!this.vus.contains(i)) {
							this.enAttente.add(0, i);
						}
					}
					iterations++;
					break;
				default:
					System.out.println("Veuillez mettre L ou P en entrée");
					break;
				}
				this.vus.add(e);
			}
		}
		System.out.println(this.toString() + "\n Solution not found");
		System.out.println("Iterations:" + iterations);
		return false;

	}

	/**
	 * 
	 * @return list of states that have been seen by the algorithm
	 */
	public ArrayList<Etat> getVus() {
		return vus;
	}

	/**
	 * 
	 * @return list of states waiting to be seen by the algorithm
	 */
	public ArrayList<Etat> getEnAttente() {
		return enAttente;
	}

	@Override
	/**
	 * To String function that returns the current "Seen" states and the current "Waiting" states
	 */
	public String toString() {
		String s = "Algos Non Informés\nVus:";
		for (int i = 0; i < this.vus.size(); i++) {
			s += this.vus.get(i).toString();
		}
		s += "\nEn Attente:";
		for (int i = 0; i < this.enAttente.size(); i++) {
			s += this.enAttente.get(i).toString();
		}
		return s;
	}

}
