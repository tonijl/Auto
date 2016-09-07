package moottoritie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.applet.*;
import java.io.File;
import java.net.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Ohjaus extends JPanel implements ActionListener, KeyListener,
        MouseListener, MouseMotionListener {

    Timer t = new Timer(5, this);
    double x = 130, y = 500, velx = 0, vely = 0; 
    double speed;
    double leveys=69, korkeus=124;
    double Rleveys=10, Rkorkeus=20;
    double xKeski = x+leveys/2;
    double yKeski = y+korkeus/2;
    double angle=0;
    double maxspeed=4;
    double maxpakki=1.5;
    double turnspeed;
    double maxturn=1;
    int autopakki=0;
    
    int cameraX=0, cameraY=0;
    int checkpoint=0;
    int p = 0;
    
    int moottoriaani=0;
    int rengasaani=0;
    
    boolean kiihdyta, jarruta;
    boolean oikea, vasen;
    boolean liuku=false;
    boolean auto=false;
    
    
    int level=0;
    int pisteet=0;
    int highscore=0;
    int gold=5;

    int spawn = 500;
    double radians=0;
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
    boolean firing = false;
    boolean menu=true;
  
    double mx, my;
    int fix, fiy;
    
    String polku = "";
    String pelaaja = "auto.png";
    String tausta = "tausta2.png";
    
    int xPoints[] = {(int)x,(int)x+(int)leveys,(int)x+(int)leveys,(int)x};
    int yPoints[] = {(int)y,(int)y,(int)y+(int)korkeus,(int)y+(int)korkeus};
    
     int[] xi = {xPoints[0]-(int)xKeski,xPoints[1]-(int)xKeski,
        xPoints[2]-(int)xKeski,xPoints[3]-(int)xKeski};
    
    int[] yi = {yPoints[0]-(int)yKeski,yPoints[1]-(int)yKeski,
        yPoints[2]-(int)yKeski,yPoints[3]-(int)yKeski};
   
    
    
    List<Car> autot = new ArrayList<Car>();
    List<Pisteet> points = new ArrayList<Pisteet>();
  

    // Pisara pisara1 = new Pisara();
    //Pallo pallo1 = new Pallo();
    public Ohjaus() {
        t.start();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setBackground(Color.darkGray);
        System.setProperty("apple.awt.fullscreenhidecursor","true");
        // pallo1.setVipu(true);

        polku=(getClass().getResource(".").getPath()); 

        points.add(new Pisteet(220,250,10,10));
        points.add(new Pisteet(500,190,10,10));
        points.add(new Pisteet(800,160,10,10));
        points.add(new Pisteet(1100,160,10,10));
        points.add(new Pisteet(1400,250,10,10));
        points.add(new Pisteet(1500,500,10,10));
        points.add(new Pisteet(1400,800,10,10));
        //points.add(new Pisteet(1200,750,10,10));
        points.add(new Pisteet(1000,550,10,10));
        points.add(new Pisteet(800,450,10,10));
        
        points.add(new Pisteet(600,550,10,10));
        points.add(new Pisteet(550,750,10,10));
        points.add(new Pisteet(350,850,10,10));
        points.add(new Pisteet(200,600,10,10));
 

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        
       // g2d.translate(this.getWidth()/2, this.getHeight()/2);
       // g2d.rotate(angle);
        
        ImageIcon player = new ImageIcon(polku + pelaaja);
        ImageIcon rata = new ImageIcon(polku + tausta);
        
        rata.paintIcon(this,g,cameraX,cameraY);
        
        g.setColor(Color.RED);
       // g.fillPolygon(xPoints, yPoints, 4);
        
        //g.fillOval((int)xKeski-((int)korkeus/2), (int)yKeski-((int)korkeus/2), (int)korkeus, (int)korkeus);
        
     
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle),x+(leveys/2),y+(korkeus/2));
        AffineTransform old = g2d.getTransform();
        g2d.transform(transform);
        g.setColor(Color.RED);
        //g.fillRect((int)x, (int)y, (int)leveys, (int)korkeus);
        //g.drawPolygon(xPoints, yPoints, 4);
        player.paintIcon(this,g,(int)x, (int)y);
        g2d.setTransform(old);
        
       /* AffineTransform transform2 = new AffineTransform();
        transform.rotate(Math.toRadians(angle),x+(Rleveys/2),y+(Rkorkeus/2));
        AffineTransform old2 = g2d.getTransform();
        g2d.transform(transform);
        g.setColor(Color.BLACK);
        g.fillRect((int)x, (int)y, (int)Rleveys, (int)Rkorkeus);
        g2d.setTransform(old);*/
        
        /*AffineTransform transform3 = new AffineTransform();
        transform.rotate(Math.toRadians(angle),x+(leveys-(Rleveys/2)),y+(Rkorkeus/2));
        AffineTransform old3 = g2d.getTransform();
        g2d.transform(transform);
        g.setColor(Color.BLACK);
        g.fillRect((int)(x+(leveys-Rleveys)), (int)y, (int)Rleveys, (int)Rkorkeus);
        g2d.setTransform(old);*/
        
        for(int i=0; i<points.size();i++){
            g.setColor(Color.YELLOW);
            g.fillOval(points.get(i).getX(), points.get(i).getY(), points.get(i).getLeveys(), points.get(i).getKorkeus());
        }
        
        
       // g.translate((int)x,(int)y);
        


        //aim.paintIcon(this,g,(int)mx-25, (int)my-25);

    }

    public void actionPerformed(ActionEvent e) {
        
        if(kiihdyta==true){
            if (moottoriaani<=0){
            moottori();
            moottoriaani=100;
            }
            if(speed<maxspeed&&liuku==false){
            speed+=0.02;
            }
        }
        
        if(jarruta==true){
            if(speed>-maxpakki)
            speed-=0.02;
            if(speed>2){
                if(rengasaani<=0){
                    rengas();
                    rengasaani=100;
                }
            }
        }
        if(oikea==true){
            if(turnspeed<maxturn)
            turnspeed+=0.02;
            if(Math.abs(turnspeed*speed)>1.1){
                liuku=true;
                if(rengasaani<=0){
                    rengas();
                    rengasaani=100;
                }
            }
        }
        if(vasen==true){
            if(turnspeed>-maxturn)
            turnspeed-=0.02;
             if(Math.abs(turnspeed*speed)>1.1){
                 liuku=true;
                if(rengasaani<=0){
                    rengas();
                    rengasaani=100;
                }
            }
        }
        
    if(auto==true){
      Autopilot();
      }
 
       
         
       
          System.out.println(autopakki);
        
        //System.out.println(xKeski+" "+yKeski);
        //System.out.println(speed*turnspeed);
        //System.out.println(angle+" "+Math.toRadians(angle));
        
        //System.out.println(Math.sin(angle)+" "+Math.cos(angle));
        angle+=turnspeed*speed;
        
        if(liuku==false){
        velx=(Math.cos(Math.toRadians(angle-90)));
        vely=(Math.sin(Math.toRadians(angle-90)));
        x+=velx*speed;
        y+=vely*speed;
        }else{
        x+=velx*speed;
        y+=vely*speed;
        }
        
        
        //cameraX=(int)x-(this.getWidth()/2);
        //cameraY=(int)y-(this.getHeight()/2);
        
        /*x-=cameraX;
        y-=cameraY;*/
        
        
        moottoriaani--;
        rengasaani--;
        
        speed=speed*0.995;
        turnspeed=turnspeed*0.95;
        
        if(angle>360){
            angle-=360;
        }
        if(angle<0){
            angle+=360;
        }
        
        if(liuku==true){
            kitka();
        }
        if(Math.abs(turnspeed*speed)<0.1){
            liuku=false;
        }
        
        /*xPoints[0] = (int)x;
        xPoints[1] = (int)x+(int)leveys;
        xPoints[2] = (int)x+(int)leveys;
        xPoints[3] = (int)x;
        yPoints[0] = (int)y;
        yPoints[1] = (int)y;
        yPoints[2] = (int)y+(int)korkeus;
        yPoints[3] = (int)y+(int)korkeus;*/
        
     xKeski = x+leveys/2;
     yKeski = y+korkeus/2;
        
     xPoints[0] = (int)((xKeski+(xi[0]*Math.cos(Math.toRadians(angle))))-(((yi[0]*Math.sin(Math.toRadians(angle))))));
     xPoints[1] = (int)((xKeski+(xi[1]*Math.cos(Math.toRadians(angle))))-(((yi[1]*Math.sin((Math.toRadians(angle)))))));
     xPoints[2] = (int)((xKeski+(xi[2]*Math.cos((Math.toRadians(angle)))))-(((yi[2]*Math.sin((Math.toRadians(angle)))))));
     xPoints[3] = (int)((xKeski+(xi[3]*Math.cos((Math.toRadians(angle)))))-(((yi[3]*Math.sin((Math.toRadians(angle)))))));
             
    yPoints[0] = (int)(((xi[0]*Math.sin((Math.toRadians(angle)))))+((yKeski+(yi[0]*Math.cos((Math.toRadians(angle)))))));
    yPoints[1] = (int)(((xi[1]*Math.sin((Math.toRadians(angle)))))+((yKeski+(yi[1]*Math.cos((Math.toRadians(angle)))))));
    yPoints[2] = (int)(((xi[2]*Math.sin((Math.toRadians(angle)))))+((yKeski+(yi[2]*Math.cos((Math.toRadians(angle)))))));
    yPoints[3] = (int)(((xi[3]*Math.sin((Math.toRadians(angle)))))+((yKeski+(yi[3]*Math.cos((Math.toRadians(angle)))))));
    
     for(int i = 0;i<xPoints.length;i++){
         if(xPoints[i]>this.getWidth()){
             crash();
            speed=0;
            if(i==0||i==1){
            autopakki=100;
            }
            x--;
             
         }
           if(xPoints[i]<0){
               crash();
           speed=0;
           if(i==0||i==1){
           autopakki=100;
           }
           x++;  
         }
     }
     
      for(int i = 0;i<yPoints.length;i++){
          
         if(yPoints[i]>this.getHeight()){
             crash();
             speed=0;
             if(i==0||i==1){
             autopakki=100;
             }
              y--;

         }
         if(yPoints[i]<0){
             crash();
            speed=0;
            if(i==0||i==1){
            autopakki=100;
            }
            y++;
         }
     }

      for(int i=0; i<points.size();i++){

         if (Math.sqrt(Math.pow(Math.abs(xKeski - (points.get(i).getX() + (points.get(i).getLeveys() / 2))), 2)
                    + Math.pow(Math.abs(yKeski - (points.get(i).getY() + (points.get(i).getLeveys() / 2))), 2))
                        < (korkeus/2)+(points.get(i).getLeveys()/2)) {             
            if(i==checkpoint){
                if(checkpoint==points.size()-1){
                    checkpoint=0;
                }else{
                   checkpoint++; 
                }
                
            }
         }
        }
      
     
        repaint();
    }
    


    public void addCar() {

      
            }
    
    public void kitka(){
        speed=speed*0.995;
        //maxturn=1.0;
        turnspeed=turnspeed*0.995;
    }
    
    public void Autopilot(){
        
        double xero,yero;
        double pAngle;
        
         if(speed<2.7){
        kiihdyta=true;
        jarruta=false;
        }else{
            kiihdyta=false;
        }
         
         
        //System.out.println("checkpoint: "+checkpoint);
        
        xero = points.get(checkpoint).getX() - xKeski;
        yero = points.get(checkpoint).getY() - yKeski;
        
        pAngle = Math.toDegrees(Math.atan2(yero, xero));
        
        if(xero>0&&yero>0){
            
            //System.out.println("1");
            
              pAngle+=90;

              if(angle<pAngle){
                vasen=false;
                oikea=true;
            }else{
               vasen=true;
                oikea=false; 
            }
            
        }else if(xero>0&&yero<0){
            
            pAngle+=90;
            
            //System.out.println("2");
              if(angle<pAngle||angle>180){
                vasen=false;
                oikea=true;
            }else{
               vasen=true;
                oikea=false; 
            }
            
        }else if(xero<0&&yero<0){
            // needs fixing
            pAngle+=450;
            if(angle>pAngle){
                 vasen=true;
                oikea=false;
            }else{
               vasen=false;
                oikea=true; 
            }
            //System.out.println("3");
            
            
        }else{
           // System.out.println("4");
            
              pAngle+=90;
            if(angle>pAngle){
                 vasen=true;
                oikea=false;
            }else{
               vasen=false;
                oikea=true; 
            }
        }
        
         autopakki--;
        
         if(autopakki>0){
            jarruta=true;
            kiihdyta=false;
            oikea=false;
            vasen=false;
        }
        
      
        
    }
 


    public void reset() {

        if(pisteet>highscore){
            highscore=pisteet;
        }
        gold = 5;
        x = 300;
        y = 500;
        velx = 0;
        vely = 0;
        pisteet=0;
        spawn=500;
 

    }


    
    public void moottori(){
        
               try{
         Clip clip = AudioSystem.getClip();
         clip.open(AudioSystem.getAudioInputStream(new File(polku+"moottori.wav")));
        //clip.open(AudioSystem.getAudioInputStream(new File(polku+"moottori")), 0, 0, 0);
         
        FloatControl gainControl = (FloatControl) clip
        .getControl(FloatControl.Type.MASTER_GAIN);
        double gain = (speed/4); // number between 0 and 1 (loudest)
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
         
         
         clip.start();

     }catch(Exception exc){
         exc.printStackTrace(System.out);
     }  
        
    }
    
      public void rengas(){
        
               try{
         Clip clip = AudioSystem.getClip();
         clip.open(AudioSystem.getAudioInputStream(new File(polku+"rengas.wav")));
         
                 FloatControl gainControl = (FloatControl) clip
        .getControl(FloatControl.Type.MASTER_GAIN);
        double gain = (speed/4); // number between 0 and 1 (loudest)
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
        
         clip.start();

     }catch(Exception exc){
         exc.printStackTrace(System.out);
     }  
        
    }
      
        public void crash(){
            
            
            //System.out.println("crash");
               try{
         Clip clip = AudioSystem.getClip();
         clip.open(AudioSystem.getAudioInputStream(new File(polku+"crash.wav")));
         
         FloatControl gainControl = (FloatControl) clip
        .getControl(FloatControl.Type.MASTER_GAIN);
        double gain = (speed/4); // number between 0 and 1 (loudest)
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
         
         clip.start();

     }catch(Exception exc){
         exc.printStackTrace(System.out);
     }  
        
    }
    
    
 
    


    public void mouseMoved(MouseEvent e) {

        int koordX = e.getX();
        int koordY = e.getY();

        mx = koordX;
        my = koordY;
    }
    
    

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        firing = false;
    }

    public void mousePressed(MouseEvent e) {
         int koordX = e.getX();
        int koordY = e.getY();


    }

    public void mouseClicked(MouseEvent e) {

       


        //System.out.println(apux+" "+apuy);


    }

    public void mouseDragged(MouseEvent e) {
     
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            kiihdyta=true;
            jarruta=false;
        }
        if (code == KeyEvent.VK_DOWN) {
            jarruta=true;
            kiihdyta=false;
        }

        if (code == KeyEvent.VK_RIGHT) {
            oikea=true;
        }
        if (code == KeyEvent.VK_LEFT) {
            vasen=true;
        }
        
        if(code == KeyEvent.VK_SPACE){
            if(auto==false){
            auto=true;
            }else{
                auto=false;
                oikea=false;
                vasen=false;
                kiihdyta=false;
            }
        }



    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
         int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            kiihdyta=false;
        }
        if (code == KeyEvent.VK_DOWN) {
            jarruta=false;
        }

        if (code == KeyEvent.VK_RIGHT) {
            oikea=false;
        }
        if (code == KeyEvent.VK_LEFT) {
            vasen=false;
        }


      
        
        
        
        
        
    }
}
