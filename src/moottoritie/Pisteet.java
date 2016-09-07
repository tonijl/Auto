
package moottoritie;



import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;


public class Pisteet {
    
    int x, y;
    int leveys, korkeus;

    public Pisteet(int x, int y, int leveys, int korkeus) {
        this.x = x;
        this.y = y;
        this.leveys = leveys;
        this.korkeus = korkeus;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLeveys() {
        return leveys;
    }

    public void setLeveys(int leveys) {
        this.leveys = leveys;
    }

    public int getKorkeus() {
        return korkeus;
    }

    public void setKorkeus(int korkeus) {
        this.korkeus = korkeus;
    }
    
    
 
    

  
    

    
    
    
    
    
    
    
}

