import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;


public class GMPanel extends JPanel implements Serializable
{
    String map;
    Font f;

    /** Creates a new instance of GMPanel */
    public GMPanel() {
        map = "Map 1";
        f = new Font("mf", 1, 30);
    }


    public void paintComponent(Graphics g){
        g.drawImage(ImageCtrl.bg1, 0, 0, 1000, 800, null);
            if(map != null){
                if(map.equals("Map 1")){
                    g.setFont(f);
                    g.setColor(Color.BLACK);
                    g.fillRect(150, 70, 700, 690);
                    g.setColor(Color.WHITE);
                    g.drawString("Map 1", 470, 110);
                    g.drawImage(ImageCtrl.map1Img, 200, 150, 600, 600, null);
                }
            else if(map.equals("Map 2")){
                    g.setFont(f);
                    g.setColor(Color.BLACK);
                    g.fillRect(150, 70, 700, 690);
                    g.setColor(Color.WHITE);
                    g.drawString("Map 2", 470, 110);
                    g.drawImage(ImageCtrl.map2Img, 200, 150, 600, 600, null);
                    }
                }
        }
}
