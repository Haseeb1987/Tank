
import java.io.*;
public class SpeedTimer implements Runnable, Serializable{

    TankGame player;
    /** Creates a new instance of SpeedTimer */
    public SpeedTimer(TankGame o) {
        player = o;
    }
    /**
     * Implemented method of Runnable Interface.
     * This thread is created when a speed timer is picked.
     * After 20 seconds, it decreases the speed of Tank, which was increased by picking the value pack.
     */
    public void run(){
        try{
            Thread.sleep(20000);
            player.speed = 2;
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
