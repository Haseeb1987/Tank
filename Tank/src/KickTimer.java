
import java.io.*;
public class KickTimer implements Runnable, Serializable{
    TankGame player;
    /** Creates a new instance of KickTimer */
    public KickTimer(TankGame o) {
        player = o;
    }

    public void run(){
        try{
            Thread.sleep(20000);
            player.kick = 0;
        }catch(Exception e){
            System.out.println(e);
        }
    }
}