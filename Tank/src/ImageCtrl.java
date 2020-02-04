import java.io.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;

public class ImageCtrl implements Serializable{

//////members/////////////////////////////////////////////////////////////

    //////bomber//////////////
    public static BufferedImage p1Bomber;
    public static BufferedImage p2Bomber;
    ////////wall//////////////
    public static BufferedImage solid;
    public static BufferedImage fragile;
    ///////background/////////
    public static BufferedImage bg;
    public static BufferedImage bg1;
    ////////bomb/////////////
    public static BufferedImage bombImage;
    public static BufferedImage exImage1,exImage2,exImage3,exImage4,exImage5,exImage6,exImage7,exImage8, exImage9, exImage10, exImage11, exImage12;
    public static BufferedImage speedVPImg;
    ///////valuepack///////////
    public static BufferedImage bombVPImg;
    public static BufferedImage medVPImg;
    public static BufferedImage scoreVPImg;
    public static BufferedImage rangeVPImg;
    public static BufferedImage kickVPImg;
    /////////////game images////////////
    public static BufferedImage icon;
    public static BufferedImage icon1;
    public static BufferedImage p1WinsImg;
    public static BufferedImage p2WinsImg;
    public static BufferedImage drawImg;
    public static BufferedImage pressEnterImg;
    public static BufferedImage map1Img;
    public static BufferedImage map2Img;
    /////////////////////////////////////
    public static BufferedImage p1LArr[];
    public static BufferedImage p1RArr[];
    public static BufferedImage p1UArr[];
    public static BufferedImage p1DArr[];
    public static BufferedImage p2LArr[];
    public static BufferedImage p2RArr[];
    public static BufferedImage p2UArr[];
    public static BufferedImage p2DArr[];
    ////////////////////////////////////
    public static BufferedImage chImg1, chImg2, chImg3;

    static Thread vpgThread;
    static Thread speedTimerThread;
    static Thread t1;
    static Thread clockThread;
    static Thread kickThread;
    public static JFileChooser jfc;

    static Thread movePlayersThread = null;
    /** Creates a new instance of ImageCtrl */
    public ImageCtrl() {
        try{
            bg = ImageIO.read(new File("Images/bg2.jpg"));
            bg1 = ImageIO.read(new File("Images/w9.bmp"));
            bombImage = ImageIO.read(new File("Images/bomb.gif"));
            speedVPImg = ImageIO.read(new File("Images/vp1.jpg"));
            bombVPImg = ImageIO.read(new File("Images/vp2.bmp"));
            medVPImg = ImageIO.read(new File("Images/med.bmp"));
            rangeVPImg = ImageIO.read(new File("Images/Player1/43.gif"));
            scoreVPImg = ImageIO.read(new File("Images/score.gif"));
            kickVPImg = ImageIO.read(new File("Images/kick.jpg"));
            icon = ImageIO.read(new File("Images/bomberIcon.gif"));
            icon1 = ImageIO.read(new File("Images/B1.gif"));
            p1WinsImg = ImageIO.read(new File("Images/Player1Wins.jpg"));
            p2WinsImg = ImageIO.read(new File("Images/Player2Wins.jpg"));
            drawImg = ImageIO.read(new File("Images/Draw.jpg"));
            pressEnterImg = ImageIO.read(new File("Images/Enter.jpg"));
            map1Img = ImageIO.read(new File("Images/map1.bmp"));
            map2Img = ImageIO.read(new File("Images/map2.bmp"));

            solid = ImageIO.read(new File("Images/2.gif"));
            fragile = ImageIO.read(new File("Images/fragile.bmp"));

            p1LArr = new BufferedImage[5];
            p1RArr = new BufferedImage[5];
            p1UArr = new BufferedImage[5];
            p1DArr = new BufferedImage[5];
            p2LArr = new BufferedImage[5];
            p2RArr = new BufferedImage[5];
            p2UArr = new BufferedImage[5];
            p2DArr = new BufferedImage[5];

            chImg1 = ImageIO.read(new File("Images/Player1/T2.gif"));
            chImg2 = ImageIO.read(new File("Images/Player1/T22.gif"));
            chImg3 = ImageIO.read(new File("Images/Player1/T2.gif"));
            /*
            exImage1 = ImageIO.read(new File("Images/ex1.bmp"));
            exImage2 = ImageIO.read(new File("Images/ex2.bmp"));
            exImage3 = ImageIO.read(new File("Images/ex3.bmp"));
            exImage4 = ImageIO.read(new File("Images/ex4.bmp"));*/
            exImage5 = ImageIO.read(new File("Images/Player1/42.gif"));
            exImage6 = ImageIO.read(new File("Images/Player1/43.gif"));
            exImage7 = ImageIO.read(new File("Images/Player1/44.gif"));
            exImage8 = ImageIO.read(new File("Images/Player1/45.gif"));
            exImage9 = ImageIO.read(new File("Images/Player1/42.gif"));
            exImage10 = ImageIO.read(new File("Images/Player1/43.gif"));
            exImage11 = ImageIO.read(new File("Images/Player1/44.gif"));
            exImage12 = ImageIO.read(new File("Images/Player1/45.gif"));

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void populatePlayerImages(TankGame p1, TankGame p2){
        try{
            //p1
            p1LArr[0] = ImageIO.read(new File(p1.dir+"T22.gif"));   // LEFT
            p1LArr[1] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1LArr[2] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1LArr[3] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1LArr[4] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1RArr[0] = ImageIO.read(new File(p1.dir+"T22.gif"));    // RIGHT
            p1RArr[1] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1RArr[2] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1RArr[3] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1RArr[4] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1UArr[0] = ImageIO.read(new File(p1.dir+"T22.gif"));    // Up
            p1UArr[1] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1UArr[2] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1UArr[3] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1UArr[4] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1DArr[0] = ImageIO.read(new File(p1.dir+"T22.gif"));    // Down
            p1DArr[1] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1DArr[2] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1DArr[3] = ImageIO.read(new File(p1.dir+"T22.gif"));
            p1DArr[4] = ImageIO.read(new File(p1.dir+"T22.gif"));
            // p2
            p2LArr[0] = ImageIO.read(new File(p2.dir+"T2.gif"));   // LEFT
            p2LArr[1] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2LArr[2] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2LArr[3] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2LArr[4] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2RArr[0] = ImageIO.read(new File(p2.dir+"T2.gif"));    // RIGHT
            p2RArr[1] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2RArr[2] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2RArr[3] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2RArr[4] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2UArr[0] = ImageIO.read(new File(p2.dir+"T2.gif"));    // Up
            p2UArr[1] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2UArr[2] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2UArr[3] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2UArr[4] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2DArr[0] = ImageIO.read(new File(p2.dir+"T2.gif"));    // Down
            p2DArr[1] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2DArr[2] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2DArr[3] = ImageIO.read(new File(p2.dir+"T2.gif"));
            p2DArr[4] = ImageIO.read(new File(p2.dir+"T2.gif"));

            p1Bomber = ImageIO.read(new File(p1.imageName));
            p2Bomber = ImageIO.read(new File(p2.imageName));
        }catch(Exception e){
        }
    }
}
