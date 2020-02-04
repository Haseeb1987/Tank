import java.io.*;

public class ClockThread implements Runnable, Serializable{
    GamePanel gp;

    /** Creates a new instance of ClockThread */
    public ClockThread(GamePanel ggp) {
        gp = ggp;
    }

    public void run() {
        try{
            while(true){
                Thread.sleep(1000);
                gp.updateClock();
                if(gp.min == 0 && gp.sec == 0)
                    break;
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

}
