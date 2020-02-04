import java.io.*;

public class GameObjects implements Serializable{
    int x,y;
    int h,w;
    int type;

    /** Creates a new instance of GameObjects */
    public GameObjects(int xx, int yy, int hh, int ww, int t) {
        x=xx;
        y=yy;
        h=hh;
        w=ww;
        type=t;    // 0=solid,  1=fragile, 2=empty
    }

}
