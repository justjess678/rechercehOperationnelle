import java.util.ArrayList;

/**
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
//		DieHard dh = new DieHard(1, 4, 2);
//		dh.successeurs();
//		AlgosNonInformes ani = new AlgosNonInformes();
//		ani.parcours("L", dh);
//		System.out.println(ani.toString());
		AStar as=new AStar();
		int[][] pos = new int[10][10];
		double[][] time = new double[10][10];
		TownEtat te = new TownEtat(new TownGraph(pos, time, false));
		as.parcours(te);
		ArrayList<Etat> pere=as.getVus();
		//te.displayPath(pere);
	}

}
