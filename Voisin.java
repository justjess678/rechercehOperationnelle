
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
public interface Voisin {

    public List<Voisin> voisin();

    public double eval();

    public void displayPath();
}
