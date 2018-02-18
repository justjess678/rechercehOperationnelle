
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mathieu
 */
public class TownGraph extends JPanel {

    int[][] pos;
    double[][] time;
    JFrame frame;
    Graphics2D g2d;
    int width;
    int height;
    int nb;
    List<Integer> path;
    boolean tsp;

    public TownGraph(int[][] pos, double[][] time, boolean tsp) {
        this.pos = pos;
        this.time = time;
        nb = pos.length;
        frame = new JFrame();
        width = 800;
        height = 600;
        this.tsp = tsp;
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);

        frame.setSize(width, height);

        frame.getContentPane().add(this);
        frame.setVisible(true);
    }

    public void display() {
        frame.setVisible(true);
        frame.repaint();

    }

    public int getNb() {
        return nb;
    }

    public void paintComponent(Graphics g) {
        //  System.out.println("test");
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);

        if (!tsp) {
            g2d.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            g2d.setColor(Color.GREEN);
            for (int i = 1; i < nb; i++) {
                for (int j = 0; j < i; j++) {
                    if (time[i][j] != -1) {
                        g2d.drawLine(pos[i][0], pos[i][1], pos[j][0], pos[j][1]);
                    }
                }
            }
        }
        g2d.setColor(Color.BLUE);
        int size = 10;
        for (int i = 0; i < nb; i++) {
            g2d.fillOval(pos[i][0] - size / 2, pos[i][1] - size / 2, size, size);
        }

        g2d.setStroke(new BasicStroke(2.5f));

        g2d.setColor(Color.RED);
        if (path != null) {
            for (int i = 0; i < path.size(); i++) {
                int a = path.get(i);
                g2d.fillOval(pos[a][0] - size / 2, pos[a][1] - size / 2, size, size);
                if (i < path.size() - 1) {
                    int b = path.get(i + 1);
                    g2d.drawLine(pos[a][0], pos[a][1], pos[b][0], pos[b][1]);
                }
            }
        }

    }

    public int[][] getPos() {
        return pos;
    }

    public double[][] getTime() {
        return time;
    }

    public int[] getCoord(int ind) {
        return pos[ind];
    }

    public List<Integer> getNext(int ind) {
        List<Integer> res = new ArrayList<Integer>();
        for (int j = 0; j < time[ind].length; j++) {
            if (time[ind][j] != -1) {
                res.add(j);
            }
        }
        return res;
    }

    public double getTime(int i, int j) {
        return time[i][j];
    }

    public int getNbTown() {
        return pos.length;
    }

    public void save(String filename) {
        try {
            PrintWriter wri = new PrintWriter(new FileWriter(filename));
            wri.println(pos.length);
            for (int i = 0; i < pos.length; i++) {
                if (i == pos.length - 1) {
                    wri.print(pos[i][0] + " " + pos[i][1]);
                } else {
                    wri.println(pos[i][0] + " " + pos[i][1]);
                }
            }
            for (int i = 1; i < pos.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (time[i][j] != -1) {
                        wri.println();

                        wri.print(i + " " + j + " " + time[i][j]);
                    }
                }
            }
            wri.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
     public void saveTSP(String filename) {
        try {
            PrintWriter wri = new PrintWriter(new FileWriter(filename));
            wri.println(pos.length);
            for (int i = 0; i < pos.length; i++) {
                if (i == pos.length - 1) {
                    wri.print(pos[i][0] + " " + pos[i][1]);
                } else {
                    wri.println(pos[i][0] + " " + pos[i][1]);
                }
            }
            wri.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static TownGraph loadGraphRandDist(String filename) {
        try {
            Random rand = new Random();
            BufferedReader fic = new BufferedReader(new FileReader(filename));
            String l = fic.readLine();
            int nb = Integer.parseInt(l);
            int[][] tpos = new int[nb][2];
            double[][] ttime = new double[nb][nb];
            for (double[] d : ttime) {
                for (int i = 0; i < nb; i++) {
                    d[i] = -1;
                }
            }
            for (int i = 0; i < nb; i++) {
                String[] tmp = fic.readLine().split(" ");
                tpos[i][0] = Integer.parseInt(tmp[0]);
                tpos[i][1] = Integer.parseInt(tmp[1]);
            }
            l = fic.readLine();
            while (l != null) {
                String[] tmp = l.split(" ");
                int a = Integer.parseInt(tmp[0]);
                int b = Integer.parseInt(tmp[1]);
                double d = dist(tpos[a][0], tpos[a][1], tpos[b][0], tpos[b][1]);
                d = (1 + rand.nextDouble()) * d;
                ttime[a][b] = ttime[b][a] = d;
                l = fic.readLine();
            }
            fic.close();
            return new TownGraph(tpos, ttime, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TownGraph generate(int nb) {

        Random rand = new Random();
        int nbbypart = (nb - 2) / 6;
        nb = nbbypart * 6 + 2;

        int[][] tpos = new int[nb][2];
        double[][] ttime = new double[nb][nb];
        for (double[] d : ttime) {
            for (int i = 0; i < nb; i++) {
                d[i] = -1;
            }
        }
        for (int i = 0; i < nb; i++) {
            int start = ((i - 1) / nbbypart + 1) * 100;
            if (i == 0) {
                start = 0;
            }
            tpos[i][0] = start + rand.nextInt(100);
            tpos[i][1] = 50 + rand.nextInt(500);
        }
        for (int i = 1; i < nb - 1; i++) {
            int curpart = (i - 1) / nbbypart;
            int prevstart = 0;
            int prevend = 0;
            if (curpart != 0) {
                prevstart = (curpart - 1) * nbbypart + 1;
                prevend = prevstart + nbbypart - 1;
            }
            boolean linked = false;
            for (int j = prevstart; j <= prevend; j++) {
                if (ttime[j][i] != -1) {
                    linked = true;
                }
            }
            if (!linked) {
                int a = prevstart + rand.nextInt(prevend - prevstart + 1);
                
                ttime[a][i] = ttime[i][a] = randdist(tpos[a][0], tpos[a][1], tpos[i][0], tpos[i][1],rand);
            }
            int curstart = curpart * nbbypart + 1;
            int curend = curstart + nbbypart - 1;

            int inter = rand.nextInt(nbbypart / 20 + 1);
            for (int k = 0; k <= inter; k++) {
                int a = curstart + rand.nextInt(curend - curstart + 1);
                int b = curstart + rand.nextInt(curend - curstart + 1);
                if (a != b) {
                    
                    
                    ttime[a][b] = ttime[b][a] =  randdist(tpos[a][0], tpos[a][1], tpos[b][0], tpos[b][1],rand);
                }
            }
            int nextstart = (curpart+1) * nbbypart + 1;
            int nextend = nextstart + nbbypart - 1;
            if(curpart==5)
                nextend = nextstart;
            int nblink=1+rand.nextInt(nbbypart / 4 + 1);
            for(int k=0;k<nblink;k++)
            {
                 int a = nextstart + rand.nextInt(nextend - nextstart + 1);
                
                ttime[a][i] = ttime[i][a] = randdist(tpos[a][0], tpos[a][1], tpos[i][0], tpos[i][1],rand);
            }
            

        }
        return new TownGraph(tpos, ttime, false);

    }

    public static TownGraph loadTSP(String filename) {
        try {

            BufferedReader fic = new BufferedReader(new FileReader(filename));
            String l = fic.readLine();
            int nb = Integer.parseInt(l);
            int[][] tpos = new int[nb][2];

            for (int i = 0; i < nb; i++) {
                String[] tmp = fic.readLine().split(" ");
                tpos[i][0] = Integer.parseInt(tmp[0]);
                tpos[i][1] = Integer.parseInt(tmp[1]);
            }
            double[][] ttime = new double[nb][nb];
            for (int i = 0; i < tpos.length; i++) {
                for (int j = 0; j <= i; j++) {
                    ttime[i][j] = ttime[j][i] = dist(tpos[i][0], tpos[i][1], tpos[j][0], tpos[j][1]);
                }
            }

            fic.close();
            return new TownGraph(tpos, ttime, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TownGraph loadGraph(String filename) {
        try {

            BufferedReader fic = new BufferedReader(new FileReader(filename));
            String l = fic.readLine();
            int nb = Integer.parseInt(l);
            int[][] tpos = new int[nb][2];
            double[][] ttime = new double[nb][nb];
            for (double[] d : ttime) {
                for (int i = 0; i < nb; i++) {
                    d[i] = -1;
                }
            }
            for (int i = 0; i < nb; i++) {
                String[] tmp = fic.readLine().split(" ");
                tpos[i][0] = Integer.parseInt(tmp[0]);
                tpos[i][1] = Integer.parseInt(tmp[1]);
            }
            l = fic.readLine();
            while (l != null) {
                String[] tmp = l.split(" ");
                int a = Integer.parseInt(tmp[0]);
                int b = Integer.parseInt(tmp[1]);
                double d = Double.parseDouble(tmp[2]);
                ttime[a][b] = ttime[b][a] = d;
                l = fic.readLine();
            }
            fic.close();
            return new TownGraph(tpos, ttime, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void display(List<Integer> path, int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            //Logger.getLogger(AlgoExhaustif.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.path = path;
        display();
    }

    public static double dist(int x1, int y1, int x2, int y2) {
        double a = (x2 - x1) * (x2 - x1);
        double b = (y2 - y1) * (y2 - y1);
        double val = Math.sqrt(a + b);
        val = ((int) (val * 10)) / 10.;
        return val;
    }
    
    public static double randdist(int x1, int y1, int x2, int y2, Random rand) {
        double a = (x2 - x1) * (x2 - x1);
        double b = (y2 - y1) * (y2 - y1);
        double val = Math.sqrt(a + b);
        val = (1 + rand.nextDouble()) * val;
        val = ((int) (val * 10)) / 10.;
        return val;
    }

    public static void main(String[] args) {

        TownGraph tg = generate(1000);
        tg.save("town1000.txt");

    }

}
