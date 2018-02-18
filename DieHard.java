import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Jessica Chambers
 * @version 1
 * 
 */
public class DieHard implements Etat {
	private int volSeau1;// represente la capacite en litres du seau 1
	private int volSeau2;// represente la capacite en litres du seau 2
	private int volSouhaite;// volume final desire
	private int seau1;// quantite actuelle dans seau 1
	private int seau2;// quantite actuelle dans seau 2
	private List<Etat> nextetats;

	/**
	 * Constructor with empty buckets at initialisation
	 * 
	 * @param volSeau1
	 *            maximum volume of bucket 1
	 * @param volSeau2
	 *            maximum volume of bucket 2
	 * @param volSouhaite
	 *            desired volume
	 */
	public DieHard(int volSeau1, int volSeau2, int volSouhaite) {
		if (volSeau1 > 0 && volSeau1 != volSeau2)
			this.volSeau1 = volSeau1;
		if (volSeau2 > 0 && volSeau1 != volSeau2)
			this.volSeau2 = volSeau2;
		if (volSouhaite > 0 && volSouhaite < volSeau1 + volSeau2)
			this.volSouhaite = volSouhaite;
		this.nextetats = new ArrayList<Etat>();
	}

	/**
	 * Constructor with non-zero bucket contents at initialisation
	 * 
	 * @param volSeau1
	 *            maximum volume of bucket 1
	 * @param volSeau2
	 *            maximum volume of bucket 2
	 * @param volSouhaite
	 *            desired volume
	 * @param seau1
	 *            current value of bucket 1
	 * @param seau2
	 *            current value of bucket 2
	 */
	public DieHard(int volSeau1, int volSeau2, int volSouhaite, int seau1,
			int seau2) {
		if (volSeau1 > 0)
			this.volSeau1 = volSeau1;
		if (volSeau2 > 0)
			this.volSeau2 = volSeau2;
		if (volSouhaite > 0)
			this.volSouhaite = volSouhaite;
		if (seau1 >= 0)
			this.seau1 = seau1;
		if (seau2 >= 0)
			this.seau2 = seau2;
		this.nextetats = new ArrayList<Etat>();
	}

	@Override
	/**
	 * Compare To function for comparing two Die Hard states
	 * @return -1 if 1 and 2 are superior to input
	 * @return 1 if 1 and 2 are inferior to input
	 * @return -2 if 1 is superior and 2 is inferior to input
	 * @return 2 if 1 is inferior and 2 is superior to input
	 * @return -3 if 1 is equal and 2 is inferior to input
	 * @return 3 if 1 is equal and 2 is superior to input
	 * @return -4 if 1 is inferior and 2 is equal to input
	 * @return 4 if 1 is superior and 2 is equal to input
	 * @returns 0 if 1 and 2 are equal to input
	 * @return 10 if none apply
	 */
	public int compareTo(Object arg0) {
		DieHard p = (DieHard) arg0;
		if (this.seau1 < p.seau1 && this.seau2 < p.seau2) {
			return -1;
		}
		if (this.seau1 > p.seau1 && this.seau2 < p.seau2) {
			return -2;
		}
		if (this.seau1 > p.seau1 && this.seau2 > p.seau2) {
			return 1;
		}
		if (this.seau1 < p.seau1 && this.seau2 > p.seau2) {
			return 2;
		}
		if (this.seau1 == p.seau1 && this.seau2 > p.seau2) {
			return 3;
		}
		if (this.seau1 == p.seau1 && this.seau2 < p.seau2) {
			return -3;
		}
		if (this.seau1 < p.seau1 && this.seau2 == p.seau2) {
			return 4;
		}
		if (this.seau1 > p.seau1 && this.seau2 == p.seau2) {
			return -4;
		}
		if (this.seau1 == p.seau1 && this.seau2 == p.seau2)
			return 0;
		return 10;// ?????
	}

	@Override
	/**
	 * tests to see if current state is the solution
	 * @return true if the state is the solution, otherwise false
	 */
	public boolean estSolution() {
		// si un des seaux contient le volume souhaite on retourne "vrai"
		return (this.seau1 == this.volSouhaite || this.seau2 == this.volSouhaite);
	}

	@Override
	/**
	 * Parcourt le graphe Die Hard. Si un seau n'est pas vide, il est vidé,
	 * s'il n'est pas plein, il est rempli et si un deau n'est pas vide et
	 * l'autre n'est pas plein, le pas vide est transversé vers le pas plein
	 * @return list of descendants from current state
	 */
	public List<Etat> successeurs() {
		if (this.seau1 != 0 && this.seau2 != this.volSeau2) {
			DieHard fils = new DieHard(volSeau1, volSeau2, volSouhaite, seau1,
					seau2);
			fils.transverser(1, 2);
			if (fils.compareTo(this) != 0)
				this.nextetats.add(fils);
		}
		if (this.seau2 != 0 && this.seau1 != this.volSeau1) {
			DieHard fils = new DieHard(volSeau1, volSeau2, volSouhaite, seau1,
					seau2);
			fils.transverser(2, 1);
			if (fils.compareTo(this) != 0)
				this.nextetats.add(fils);
		}
		if (this.seau1 != 0) {
			DieHard fils = new DieHard(volSeau1, volSeau2, volSouhaite, seau1,
					seau2);
			fils.vider(1);
			if (fils.compareTo(this) != 0)
				this.nextetats.add(fils);
		}
		if (this.seau2 != 0) {
			DieHard fils = new DieHard(volSeau1, volSeau2, volSouhaite, seau1,
					seau2);
			fils.vider(2);
			if (fils.compareTo(this) != 0)
				this.nextetats.add(fils);
		}
		if (this.seau1 != this.volSeau1) {
			DieHard fils = new DieHard(volSeau1, volSeau2, volSouhaite, seau1,
					seau2);
			fils.remplir(1);
			if (fils.compareTo(this) != 0)
				this.nextetats.add(fils);
		}
		if (this.seau2 != this.volSeau2) {
			DieHard fils = new DieHard(volSeau1, volSeau2, volSouhaite, seau1,
					seau2);
			fils.remplir(2);
			if (fils.compareTo(this) != 0)
				this.nextetats.add(fils);
		}
		return nextetats;
	}

	@Override
	/**
	 * To string function returns current bucket values and their
	 * first level descendants
	 */
	public String toString() {
		String nextetatsS = "--------------------\nseau 1: " + this.getSeau1()
				+ " seau 2: " + this.getSeau2() + "\n" + "Next states=\n";
		DieHard dh;
		for (int i = 0; i < nextetats.size(); i++) {
			dh = (DieHard) nextetats.get(i);
			nextetatsS += "seau 1: " + dh.getSeau1() + " seau 2: "
					+ dh.getSeau2() + "\n";
		}
		return nextetatsS;
	}

	@Override
	/**
	 * @return h value of current state
	 */
	public double h() {// cout de l'etat
		return 0;
	}

	@Override
	/**
	 * @input destination state
	 * @return the k value between the current state
	 * and the input state
	 */
	public double k(Etat e) {// cout de larrete
		return 1;
	}

	@Override
	/**
	 * Displays path.
	 * Code badly copied from the TownGraph class in the hopes that
	 * I would be able to implement it correctly.
	 */
	public void displayPath(Map<Etat, Etat> pere) {
		// TODO Auto-generated method stub
		List<Integer> path = new ArrayList<Integer>();
		Etat current = this;

		while (current != null) {

			path.add(((TownEtat) current).town);
			current = pere.get(current);
		}

	}

	/***** OPERATEURS ******/

	/**
	 * Fills the input bucket
	 * 
	 * @param seau
	 */
	public void remplir(int seau) {
		switch (seau) {
		case 1:
			this.seau1 = this.volSeau1;
			System.out.println("** remplir 1 **");
			break;
		case 2:
			this.seau2 = this.volSeau2;
			System.out.println("** remplir 2 **");
			break;
		default:
			break;
		}
	}

	/**
	 * Empties the input bucket
	 * 
	 * @param seau
	 */
	public void vider(int seau) {
		switch (seau) {
		case 1:
			this.seau1 = 0;
			System.out.println("** vider 1 **");
			break;
		case 2:
			this.seau2 = 0;
			System.out.println("** vider 2 **");
			break;
		default:
			break;
		}
	}

	/**
	 * transfers contents of the first input bucket to the second input bucket
	 * 
	 * @param deb
	 *            first input bucket
	 * @param fin
	 *            second input bucket
	 */
	public void transverser(int deb, int fin) {
		if (deb == 1) { // seau 1 vers 2
			int espace = this.volSeau2 - this.seau2; // difference de volume a
														// remplir
			if (espace > 0) { // s'il y a bien une difference de volume, on
								// procede
				this.seau2 += this.seau1;
				if (this.seau2 > this.volSeau2)
					this.seau2 = this.volSeau2;// le seau deborde brievement,
												// mais dans ce cas il prend sa
												// valeur max
				this.seau1 = this.seau1 - espace;
				if (this.seau1 < 0)
					this.seau1 = 0;// au cas ou le seau est plus que vide, on le
									// met a zero
			}
			System.out.println("** transverser 1 vers 2 **");
		} else if (deb == 2) {// seau 2 vers 1
			int espace = this.volSeau1 - this.seau1; // difference de volume a
														// remplir
			if (espace > 0) { // s'il y a bien une difference de volume, on
								// procede
				this.seau1 += this.seau2;
				if (this.seau1 > this.volSeau1)
					this.seau1 = this.volSeau1;// le seau deborde brievement,
												// mais dans ce cas il prend sa
												// valeur max
				this.seau2 = this.seau2 - espace;
				if (this.seau2 < 0)
					this.seau2 = 0;// au cas ou le seau est plus que vide, on le
									// met a zero
			}
			System.out.println("** transverser 2 vers 1 **");
		}
	}

	/***** GETTERS ******/
	/**
	 * 
	 * @return first bucket volume
	 */
	public int getVolSeau1() {
		return volSeau1;
	}

	/**
	 * 
	 * @return second bucket volume
	 */
	public int getVolSeau2() {
		return volSeau2;
	}

	/**
	 * 
	 * @return desired volume
	 */
	public int getVolSouhaite() {
		return volSouhaite;
	}

	/**
	 * 
	 * @return current quantity in bucket 1
	 */
	public int getSeau1() {
		return seau1;
	}

	/**
	 * 
	 * @return current quantity in bucket 2
	 */
	public int getSeau2() {
		return seau2;
	}

}