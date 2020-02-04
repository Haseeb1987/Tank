
import java.io.*;

public class MovePlayersThread implements Runnable, Serializable{
    GamePanel gp;
    /** Creates a new instance of MovePlayersThread */
    public MovePlayersThread(GamePanel o) {
        gp = o;
    }

    public void run(){
        try{
            while(true){
                Thread.sleep(10);
                if(gp.p1.direction == 'u')
                    gp.moveP1(0,-5);
                else if(gp.p1.direction == 'd')
                    gp.moveP1(0,5);
                else if(gp.p1.direction == 'l')
                    gp.moveP1(-5,0);
                else if(gp.p1.direction == 'r')
                    gp.moveP1(5,0);

                if(gp.p2.direction == 'u')
                    gp.moveP2(0,-5);
                else if(gp.p2.direction == 'd')
                    gp.moveP2(0,5);
                else if(gp.p2.direction == 'l')
                    gp.moveP2(-5,0);
                else if(gp.p2.direction == 'r')
                    gp.moveP2(5,0);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
