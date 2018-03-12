
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mathieu
 */
public class TownTSP implements Voisin {

    @Override
    public int hashCode() {
        return (int) (Double.doubleToLongBits(this.val) );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TownTSP other = (TownTSP) obj;
        for(int i=0;i<order.length;i++)
            if(order[i]!=other.order[i])
                return false;
        return true;
    }

    TownGraph tg;
    int[] order;
    double val;

    public TownTSP(TownGraph tg, int[] order, double val) {
        this.tg = tg;
        this.order = order;
        this.val = ((double)((int)(val*1000)))/1000.;
    }

    public TownTSP(TownGraph tg) {
        this.tg = tg;
        order = new int[tg.getNb() + 1];
        for (int i = 0; i < order.length - 1; i++) {
            order[i] = i;
        }

        order[order.length - 1] = 0;

        val = evalLength();
    }

    private double evalLength() {
        double l = 0;
        for (int i = 0; i < order.length - 1; i++) {

            l += tg.getTime(order[i], order[i + 1]);
        }
        l=((double)((int)(l*1000)))/1000.;
        return l;
    }

    public List<Voisin> voisin() {
        List<Voisin> v = new ArrayList<Voisin>();
        for (int i = 1; i < order.length - 2; i++) {
            for (int j = i + 1; j < order.length - 1; j++) {
                double nval = val - tg.getTime(order[i], order[i - 1]) - tg.getTime(order[j], order[j + 1]);
                int[] norder = order.clone();
                for (int k = 0; k <= (j - i); k++) {
                    norder[i + k] = order[j - k];

                }
                nval += tg.getTime(norder[i], norder[i - 1]) + tg.getTime(norder[j], norder[j + 1]);
                TownTSP t = new TownTSP(tg, norder, nval);

                v.add(t);
            }
        }
        return v;
    }

    public double eval() {
        return val;
    }


    public void displayPath() {
        List<Integer> path = new ArrayList<Integer>();
        for (int v : order) {
            path.add(v);
        }
        tg.display(path, 100);
    }
    
	@Override
	public String toString() {
		String s = "\nValeur : " + this.val;
		return s;
	}
}
