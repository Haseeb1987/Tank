
import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;

public class Explosion implements Runnable, Serializable{

    GamePanel gp;
    TankGame player1;
    TankGame bomber;
    /** Creates a new instance of Explosion */
    public Explosion(GamePanel g, TankGame o) {
        gp = g;
        bomber = o;
    }

    public synchronized void run(){
        try{
            Thread.sleep(4000);
        }catch(Exception e){
            System.out.println(e);
        }
        // flags to check where to stop desruction if bomberman has greater range
        int uStop=0;
        int dStop=0;
        int lStop=0;
        int rStop=0;
        int uType=0;
        int dType=0;
        int lType=0;
        int rType=0;
        for(int i = 1; i<= bomber.range; i++){
            if(gp.bombArr.get(0).bx-i >= 0)
                uType = gp.arr[gp.bombArr.get(0).bx-i][gp.bombArr.get(0).by].type;
            if(gp.bombArr.get(0).bx+i < 14)
                dType = gp.arr[gp.bombArr.get(0).bx+i][gp.bombArr.get(0).by].type;
            if(gp.bombArr.get(0).by-i >= 0)
                lType = gp.arr[gp.bombArr.get(0).bx][gp.bombArr.get(0).by-i].type;
            if(gp.bombArr.get(0).by+i < 17)
                rType = gp.arr[gp.bombArr.get(0).bx][gp.bombArr.get(0).by+i].type;

            // setting stop flags
            if(uType == 0)
                uStop = 1;
            if(dType == 0)
                dStop = 1;
            if(lType == 0)
                lStop = 1;
            if(rType == 0)
                rStop = 1;

            if((uType == 1 || uType == 2) && uStop == 0){
                gp.arr[gp.bombArr.get(0).bx-i][gp.bombArr.get(0).by].type = 2;
                player1 = gp.p1;
                int p1x = player1.bx;
                int p1y = player1.by;
                if((p1x == gp.bombArr.get(0).bx-i && p1y == gp.bombArr.get(0).by)) {   // Checking player in the bomb range
                    player1.life--;
                    if(player1.life < 0)
                        player1.bombLimit = -1;
                }
                // checking destruction of p2
                player1 = gp.p2;
                p1x = player1.bx;
                p1y = player1.by;
                if((p1x == gp.bombArr.get(0).bx-i && p1y == gp.bombArr.get(0).by)) {   // Checking player in the bomb range
                    player1.life--;
                    if(player1.life < 0)
                        player1.bombLimit = -1;
                }
                // checking value packs in bomb range
                if((gp.vpx == gp.bombArr.get(0).bx-i && gp.vpy == gp.bombArr.get(0).by)) {
                    ImageCtrl.vpgThread.interrupt();
                    gp.repaint();
                }
                // checking bombs in bomb range
                for(int j = 0; j< gp.bombArr.size(); j++ ){
                    if((gp.bombArr.get(j).bx == gp.bombArr.get(0).bx-i && gp.bombArr.get(j).by == gp.bombArr.get(0).by)) {
                        gp.bombArr.get(j).x = -1000;
                        gp.repaint();
                    }
                }

            }
            if((dType == 1 || dType == 2) && dStop == 0){
                gp.arr[gp.bombArr.get(0).bx+i][gp.bombArr.get(0).by].type = 2;
                player1 = gp.p1;
                int p1x = player1.bx;
                int p1y = player1.by;
                if((p1x == gp.bombArr.get(0).bx+i && p1y == gp.bombArr.get(0).by)) {   // Checking player in the bomb range
                    player1.life--;
                    if(player1.life < 0)
                        player1.bombLimit = -1;
                }
                // checking destruction of p2
                player1 = gp.p2;
                p1x = player1.bx;
                p1y = player1.by;
                if((p1x == gp.bombArr.get(0).bx+i && p1y == gp.bombArr.get(0).by)) {   // Checking player in the bomb range
                    player1.life--;
                    if(player1.life < 0)
                        player1.bombLimit = -1;
                }
                // checking value packs in bomb range
                if((gp.vpx == gp.bombArr.get(0).bx+i && gp.vpy == gp.bombArr.get(0).by)) {
                    ImageCtrl.vpgThread.interrupt();
                    gp.repaint();
                }
                // checking bombs in bomb range
                for(int j = 0; j< gp.bombArr.size(); j++ ){
                    if((gp.bombArr.get(j).bx == gp.bombArr.get(0).bx+i && gp.bombArr.get(j).by == gp.bombArr.get(0).by)) {
                        gp.bombArr.get(j).x = -1000;
                        gp.repaint();
                    }
                }
            }
            if((lType == 1 || lType == 2) && lStop == 0){
                gp.arr[gp.bombArr.get(0).bx][gp.bombArr.get(0).by-i].type = 2;
                player1 = gp.p1;
                int p1x = player1.bx;
                int p1y = player1.by;
                if((p1x == gp.bombArr.get(0).bx && p1y == gp.bombArr.get(0).by-i)) {   // Checking player in the bomb range
                    player1.life--;
                    if(player1.life < 0)
                        player1.bombLimit = -1;
                }
                // checking destruction of p2
                player1 = gp.p2;
                p1x = player1.bx;
                p1y = player1.by;
                if((p1x == gp.bombArr.get(0).bx && p1y == gp.bombArr.get(0).by-i)) {   // Checking player in the bomb range
                    player1.life--;
                    if(player1.life < 0)
                        player1.bombLimit = -1;
                }
                // checking value packs in bomb range
                if((gp.vpx == gp.bombArr.get(0).bx && gp.vpy == gp.bombArr.get(0).by-i)) {
                    ImageCtrl.vpgThread.interrupt();
                    gp.repaint();
                }
                // checking bombs in bomb range
                for(int j = 0; j< gp.bombArr.size(); j++ ){
                    if((gp.bombArr.get(j).bx == gp.bombArr.get(0).bx && gp.bombArr.get(j).by == gp.bombArr.get(0).by-i)) {
                        gp.bombArr.get(j).x = -1000;
                        gp.repaint();
                    }
                }
            }
            if((rType == 1 || rType == 2) && rStop == 0){
                gp.arr[gp.bombArr.get(0).bx][gp.bombArr.get(0).by+i].type = 2;
                player1 = gp.p1;
                int p1x = player1.bx;
                int p1y = player1.by;
                if((p1x == gp.bombArr.get(0).bx && p1y == gp.bombArr.get(0).by+i)) {   // Checking player in the bomb range
                    player1.life--;
                    if(player1.life < 0)
                        player1.bombLimit = -1;
                }
                // checking destruction of p2
                player1 = gp.p2;
                p1x = player1.bx;
                p1y = player1.by;
                if((p1x == gp.bombArr.get(0).bx && p1y == gp.bombArr.get(0).by+i)) {   // Checking player in the bomb range
                    player1.life--;
                    if(player1.life < 0)
                        player1.bombLimit = -1;
                }
                // checking value packs in bomb range
                if((gp.vpx == gp.bombArr.get(0).bx && gp.vpy == gp.bombArr.get(0).by+i)) {
                    ImageCtrl.vpgThread.interrupt();
                    gp.repaint();
                }
                // checking bombs in bomb range
                for(int j = 0; j< gp.bombArr.size(); j++ ){
                    if((gp.bombArr.get(j).bx == gp.bombArr.get(0).bx && gp.bombArr.get(j).by == gp.bombArr.get(0).by+i)) {
                        gp.bombArr.get(j).x = -1000;
                        gp.repaint();
                    }
                }
            }

        }// end of for loop
        player1 = gp.p1;
        int p1x = player1.bx;
        int p1y = player1.by;
        if(p1x == gp.bombArr.get(0).bx && p1y == gp.bombArr.get(0).by){
            player1.life--;
                    if(player1.life < 0)
                        bomber.bombLimit = -1;
        }
        player1 = gp.p2;
        p1x = player1.bx;
        p1y = player1.by;
        if(p1x == gp.bombArr.get(0).bx && p1y == gp.bombArr.get(0).by){
            player1.life--;
                    if(player1.life < 0)
                        bomber.bombLimit = -1;
        }
        /////////////////////////////////
        int tempx, tempy;
        tempx = gp.bombArr.get(0).x;
        tempy = gp.bombArr.get(0).y;
        gp.arr[gp.bombArr.get(0).bx][gp.bombArr.get(0).by].type = 2;
        gp.bombArr.remove(0);
        gp.bombArr.trimToSize();
        bomber.currBombs -= 1;
        Graphics g = gp.getGraphics();

        try{
            g.drawImage(ImageCtrl.exImage5, tempx, tempy-5, 60,60,null);
            Thread.sleep(200);
            g.drawImage(ImageCtrl.exImage6, tempx, tempy-5, 60,60,null);
            Thread.sleep(200);
            g.drawImage(ImageCtrl.exImage7, tempx, tempy-5, 60,60,null);
            Thread.sleep(200);
            g.drawImage(ImageCtrl.exImage8, tempx, tempy-5, 60,60,null);
            Thread.sleep(200);
            g.drawImage(ImageCtrl.exImage9, tempx, tempy-5, 60,60,null);
            Thread.sleep(200);
            g.drawImage(ImageCtrl.exImage10, tempx, tempy-5, 60,60,null);
            Thread.sleep(200);
            g.drawImage(ImageCtrl.exImage11, tempx, tempy-5, 60,60,null);
            Thread.sleep(200);
            g.drawImage(ImageCtrl.exImage12, tempx, tempy-5, 60,60,null);
            Thread.sleep(200);

            gp.exFlag = 0;
        }catch(Exception e){
            System.out.println(e);
        }
        gp.repaint();
    }
}
