import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class, used to test and use the various graph search algorithms in
 * different conditions
 * 
 * @author Jessica Chambers
 * @version 1
 * 
 */
public class MainTest {

	/**
	 * Main function to test methods
	 * 
	 * @param args
	 *            empty
	 */
	public static void main(String[] args) {
		DieHard dh = null;
		int[][] pos = null;
		double[][] time = null;
		System.out.println("Veuillez choisir l'algorithme à lancer:\n"
				+ "1. Die Hard en PROFONDEUR\n" + "2. Die Hard en LARGEUR\n"
				+ "3. Die Hard avec A-STAR\n" + "4. TSP avec A-STAR\n"
				+ "5. TSP avec Tabou\n");
		Scanner sc = new Scanner(System.in);
		int i = sc.nextInt();
		if (i <= 3) {
			System.out.println("Volume du seau 1? (saisir un entier positif!)");
			int s1 = sc.nextInt();
			System.out.println("Volume du seau 2? (saisir un entier positif!)");
			int s2 = sc.nextInt();
			System.out
					.println("Volume à atteindre? (saisir un entier positif!)");
			int vol = sc.nextInt();
			dh = new DieHard(s1, s2, vol);
		} else {
			pos = new int[10][10];
			time = new double[10][10];
		}
		switch (i) {
		case 1:
			if (dh != null) {
				AlgosNonInformes ani = new AlgosNonInformes();
				ani.parcours("P", dh);
			}
			break;
		case 2:
			if (dh != null) {
				AlgosNonInformes ani = new AlgosNonInformes();
				ani.parcours("L", dh);
			}
			break;
		case 3:
			if (dh != null) {
				AStar as = new AStar();
				as.parcours(dh);
			}
			break;
		case 4:
			if (pos != null && time != null) {
				AStar as = new AStar();
				TownEtat te = new TownEtat(new TownGraph(pos, time, false));
				as.parcours(te);
				ArrayList<Etat> pere = as.getVus();
			}
			// te.displayPath(pere);
			break;
		case 5:
			if (pos != null && time != null) {
				Tabou tab = new Tabou(new TownGraph(pos, time, true));
				Voisin v1 = new TownTSP(new TownGraph(pos, time, true));
				List<Voisin> tabou = tab.algo(v1);
				TownGraph.generate(10);
				for (Voisin v : tabou) {
					System.out.println(v.toString());
					// v.displayPath();
				}
			}

			break;
		default:
			System.out
					.println("Vous avez sélectionné une option non-implémentée ou non-existant");
			break;
		}
		sc.close();
	}
}
