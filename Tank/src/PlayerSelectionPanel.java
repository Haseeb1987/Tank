import java.io.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;


public class PlayerSelectionPanel extends JPanel implements Serializable{
    //public BufferedImage bg;
    //BufferedImage chImg1, chImg2, chImg3;
    int sx1, sy1, sx2,sy2;
    int p1, p2;
    Font f;
    String img1, img2, img3;
    public int occArr[];

    public PlayerSelectionPanel() {
        img1 = "Images/Player1/T2.gif";
        img2 = "Images/Player1/T22.gif";
        img3 = "Images/Player1/T2.gif";
        occArr = new int[3];
        f = new Font("mf", 1, 50);
        p1 = 0;
        p2 = 0;
        setSelectedP1(3);
        setSelectedP2(1);
    }

    public void paintComponent(Graphics g){
        g.drawImage(ImageCtrl.bg1, 0, 0, 1000, 800, null);
        g.setColor(Color.BLACK);
        g.fillRect(10, 10, 220, 60);
        g.fillRect(540, 10, 220, 60);

        g.setColor(Color.WHITE);
        g.setFont(f);
        g.drawString("Player 1", 550, 60);
        g.drawString("Player 2", 20, 60);

        g.setColor(Color.YELLOW);
        g.fillRect(sx1, sy1, 150, 150);
        g.fillRect(sx2, sy2, 150, 150);
        //p2
        g.drawImage(ImageCtrl.chImg1, 10, 80, 130, 130, null);
        g.drawImage(ImageCtrl.chImg2, 160, 80, 130, 130, null);
        g.drawImage(ImageCtrl.chImg3, 310, 80, 130, 130, null);
        f = new Font("mf", 1, 30);
        g.setColor(Color.WHITE);
        g.drawString("Press 'Z' to select",10 ,250);
        //p1
        g.drawImage(ImageCtrl.chImg1, 550, 80, 130, 130, null);
        g.drawImage(ImageCtrl.chImg2, 700, 80, 130, 130, null);
        g.drawImage(ImageCtrl.chImg3, 850, 80, 130, 130, null);
        g.drawString("Press 'Enter' to select",550 ,250);
    }

    public void setSelectedP1(int i){
        p1 += i;
        if(p1 == 1){
            sx1 = 540;
            sy1 = 70;
        }
        else if(p1 == 2){
            sx1 = 690;
            sy1 = 70;
        }
        else if(p1 == 3){
            sx1 = 840;
            sy1 = 70;
        }
        this.repaint();
    }// end of setSelectedP1
    public void setSelectedP2(int i){
        p2 += i;
        if(p2 == 1){
            sx2 = 0;
            sy2 = 70;
        }
        else if(p2 == 2){
            sx2 = 150;
            sy2 = 70;
        }
        else if(p2 == 3){
            sx2 = 300;
            sy2 = 70;
        }
        this.repaint();
    }// end of setSelectedp2
}
