
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;

public class ValuePackGenerator implements Runnable, Serializable{
    GamePanel gp;
    int vpx,vpy;
    int time;
    int vpType;
    int tempCount;
    /** Creates a new instance of ValuePackGenerator */
    public ValuePackGenerator(GamePanel o) {
        vpx = vpy = -1;
        gp = o;
        time = 0;
        vpType = -1;
        tempCount = 0;
    }
    /**
     * Implemented Method of Runnable Interface
     * This Thread is responsible for generating the value packs randomly.
     * It generate a random number, on that random number basis, it decided the delay time between displaying of value pack.
     * Randomly decides the type of the value pack and then display that value pack for 20 seconds.
     */
    public void run(){
        double d;
        double d1;
        while(true){
            try{
                Thread.yield();
                time = (int)(Math.random()*10000);
                Thread.sleep(time);
                do{
                    d = Math.random();
                    d1 = Math.random();
                    d = d*10;
                    d1 = d1*10;
                    vpx = (int)d;
                    vpy = (int)d1;
                    if(d1<0.5)     // just adding randomness
                        vpx += 4;
                    else
                        vpy += 4;
                }while(gp.arr[vpx][vpy].type != 2);
                gp.vpx = vpx;
                gp.vpy = vpy;
                ////////// VP selection code ////////
                d = Math.random();
                if(d<=0.2)
                    gp.vpType = 0;  // Speed Up
                else if(d > 0.2 && d <= 0.4)
                    gp.vpType = 1;  // bomb Value pack
                else if(d > 0.4 && d <= 0.7)
                    gp.vpType = 2;    // Score Value Pack
                else if(d > 0.7 && d <= 0.85)
                    gp.vpType = 4;    // Range Value Pack
                else if(d > 0.85 && d <= 1)
                    gp.vpType = 5;    // Kick Value Pack
                /////////// Generating Additional life value pack once in a game course  //////
                tempCount += 1;
                if(tempCount == 8)
                    gp.vpType = 3;    // Additional life Value Pack
                //////////////////////////////////////
                gp.repaint();
                Thread.sleep(5000);
                vpx=vpy=-1;
                gp.repaint();
            }
            catch(InterruptedException ex){
                System.out.println(ex);
            }
            catch(Exception e){
                System.out.println(e);
                e.printStackTrace();
            }
        }// end while
    }
}
