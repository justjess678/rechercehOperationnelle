import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author mathieu
 */
public class TownEtat implements Etat {

	TownGraph tg;
	int town;
	int towndest;

	public TownEtat(TownGraph tg, int town, int towndest) {
		this.tg = tg;
		this.town = town;
		this.towndest = towndest;
	}

	public TownEtat(TownGraph tg) {
		this.tg = tg;
		this.town = 0;
		this.towndest = tg.getNbTown() - 1;
	}

	public boolean estSolution() {
		return town == towndest;
	}

	public List<Etat> successeurs() {
		List<Integer> nexttowns = tg.getNext(town);
		List<Etat> nextetats = new ArrayList<Etat>();
		for (int n : nexttowns) {
			nextetats.add(new TownEtat(tg, n, towndest));
		}

		return nextetats;

	}

	/**
	 * Returns the heuristic of the current town (the distance travelled)
	 */
	public double h() {
		int[] coord1 = tg.getCoord(this.town);
		int[] coord2 = tg.getCoord(this.towndest);
		return Math.sqrt((coord2[0] - coord1[0]) * (coord2[0] - coord1[0])
				+ (coord2[1] - coord1[1]) * (coord2[1] - coord1[1]));
	}

	public double k(Etat e) {
		TownEtat p = (TownEtat) e;
		return tg.getTime(town, p.town);
	}

	public int hashCode() {
		return (int) (town + tg.getNbTown() * towndest);
	}

	public boolean equals(Object o) {
		TownEtat p = (TownEtat) o;
		return p.town == town;
	}

	public String toString() {
		return "town n°" + town;
	}

	public int compareTo(Object o) {
		TownEtat p = (TownEtat) o;
		if (town < p.town) {
			return -1;
		}
		if (town > p.town) {
			return 1;
		}
		return 0;
	}

	public void displayPath(Map<Etat, Etat> pere) {
		List<Integer> path = new ArrayList<Integer>();
		Etat current = this;

		while (current != null) {

			path.add(((TownEtat) current).town);
			current = pere.get(current);
		}
		tg.display(path, 100);
	}

}
