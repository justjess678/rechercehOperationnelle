
import java.util.List;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author math
 */
public interface Etat extends Comparable<Object>
{

    public boolean estSolution();

    public List<Etat> successeurs();

    public double h();

    public double k(Etat e);
    
    
    public void displayPath(Map<Etat, Etat> pere);
   
}
