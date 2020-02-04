
import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.*;

public class GamePanel extends JPanel implements Serializable{
    MainGUI mg;
    int xlim,ylim;
    int x,y;
    int vpx,vpy;    // va;ue pack block index
    int bombFlag;
    int exFlag;
    int startFlag;
    GameObjects arr[][];
    ArrayList<Bomb> bombArr;
    public TankGame p1;
    public TankGame p2;
    public Explosion ex;
    public ValuePackGenerator vpg;
    public SpeedTimer st;
    int vpType;
    ClockThread c;
    KickTimer kt;
    public Bomb bomb;
    String p1Img,p2Img;
    public int min,sec;
    Font f;
    int p1i, p2i;
    int endGameFlag;
    String map;

    public GamePanel(String img1, String img2, MainGUI o, String map) {
         mg = o;
         mg.startMode = 2;
         this.map = map;
         bombArr = new ArrayList<Bomb>();
         min = 3;
         sec = 60;
         xlim=14;
         ylim=17;
         x=y=-1;
         bombFlag=0;
         exFlag=0;
         endGameFlag=0;
         startFlag=0;
         vpx=vpy=-1;
         vpType = -1;
         p1Img = img1;
         p2Img = img2;
         arr= new GameObjects[xlim][ylim];
         populateGamePanel();
         p1 = new TankGame(arr[xlim-2][ylim-2].x, arr[xlim-2][ylim-2].y, p1Img, xlim-2, ylim-2, 2, 1, 3);
         p2 = new TankGame(55, 55, p2Img, 1, 1, 2, 1, 3);

         ImageCtrl.populatePlayerImages(p1, p2);

         f = new Font("mf", 1, 30);
         p1i = p2i = 0;

         ImageCtrl.movePlayersThread = new Thread(new MovePlayersThread(this));
         ImageCtrl.movePlayersThread.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        //g.fillRect(0,0,1000,800);
        g.drawImage(ImageCtrl.bg,0,0,1000,800,null);
        loadGame(g);
        if(startFlag == 0){
            vpg = new ValuePackGenerator(this);
            ImageCtrl.vpgThread = new Thread(vpg);
            ImageCtrl.vpgThread.start();
            c = new ClockThread(this);
            ImageCtrl.clockThread = new Thread(c);
            ImageCtrl.clockThread.start();
            startFlag = 1;
        }
    }

    public void populateGamePanel(){
        int x=0,y=0,type=0;
        if(map.equals("Map 1") == true){
            for(int i=0;i<xlim;i++){
                for(int j=0;j<ylim;j++){
                    type = 1;
                    if(i==0 || i==xlim-1 || j==0 || j==ylim-1)
                        type=0;
                    arr[i][j] = new GameObjects(x,y,50,50,type);
                    x+=50;
                }

                x=0;
                y+=50;
            }// end of for loop
            //Setting up Solid rocks
            for(int i=1;i<xlim-1;i+=2){
                for(int j=1;j<ylim-1;j+=2){
                    arr[i][j].type = 0;
                }
            }
        }
        else if(map.equals("Map 2") == true){
            for(int i=0;i<xlim;i++){
                for(int j=0;j<ylim;j++){
                    if(i%2 == 0)
                        type = 1;
                    else
                        type = 2;
                    if(i==0 || i==xlim-1 || j==0 || j==ylim-1)
                        type=0;
                    arr[i][j] = new GameObjects(x,y,50,50,type);
                    x+=50;
                }

                x=0;
                y+=50;
            }// end of for loop
            //Setting up Solid rocks
            for(int i=1;i<xlim-1;i+=2){
                for(int j=1;j<ylim-1;j+=2){
                    arr[i][j].type = 0;
                }
            }
        }
        else if(map.equals("Random Map") == true){
            for(int i=0;i<xlim;i++){
                for(int j=0;j<ylim;j++){
                    double temp = Math.random();
                    if(temp>0 && temp<=0.5)
                        type = 1;        // fragile
                    else if(temp>0.5 && temp<=1.0)
                        type = 2;        // empty
                    if(i==0 || i==xlim-1 || j==0 || j==ylim-1)
                        type=0;
                    arr[i][j] = new GameObjects(x,y,50,50,type);
                    x+=50;
                }

                x=0;
                y+=50;
            }// end of for loop
            //Setting up Solid rocks
            for(int i=1;i<xlim-1;i+=2){
                for(int j=1;j<ylim-1;j+=2){
                    arr[i][j].type = 0;
                }
            }
        } // end of if
        arr[1][1].type = 2;    // 1st and last block empty
        arr[xlim-2][ylim-2].type = 2;
        arr[xlim-2][ylim-3].type = 2;
        arr[xlim-3][ylim-2].type = 2;
        arr[1][2].type = 2;
        arr[2][1].type = 2;
    }// end of populateGamePanel


    public void loadGame(Graphics g){
        for(int i=0;i<xlim;i++){
            for(int j=0;j<ylim;j++){
                if(arr[i][j].type == 0)
                    g.drawImage(ImageCtrl.solid,arr[i][j].x,arr[i][j].y,arr[i][j].h,arr[i][j].w,null);
                else if(arr[i][j].type == 1)
                    g.drawImage(ImageCtrl.fragile,arr[i][j].x,arr[i][j].y,arr[i][j].h,arr[i][j].w,null);
            }
        }// end of for
        if(bombFlag == 1){ // if player has placed a bomb
            for(int i=0;i<bombArr.size();i++){
                bomb = bombArr.get(i);
                g.drawImage(ImageCtrl.bombImage, bomb.x, bomb.y, 30,30, null);
                this.repaint();
            }
        }
        if(vpx != -1){
            if(vpType == 0)   // Speed Up value pack
                g.drawImage(ImageCtrl.speedVPImg ,arr[vpx][vpy].x,arr[vpx][vpy].y,40,40,null);
            if(vpType == 1)
                g.drawImage(ImageCtrl.bombVPImg ,arr[vpx][vpy].x,arr[vpx][vpy].y,40,40,null);
            if(vpType == 2)
                g.drawImage(ImageCtrl.scoreVPImg ,arr[vpx][vpy].x,arr[vpx][vpy].y,40,40,null);
            if(vpType == 3)
                g.drawImage(ImageCtrl.medVPImg ,arr[vpx][vpy].x,arr[vpx][vpy].y,40,40,null);
            if(vpType == 4)
                g.drawImage(ImageCtrl.scoreVPImg ,arr[vpx][vpy].x,arr[vpx][vpy].y,40,40,null);
            if(vpType == 5)
                g.drawImage(ImageCtrl.kickVPImg ,arr[vpx][vpy].x,arr[vpx][vpy].y,40,40,null);
        }

        // drawing players on the game screen
        if(p1.life >= 0){
            if(p1.direction == 'l')
                g.drawImage(ImageCtrl.p1LArr[p1i], p1.x, p1.y, 30,30,null);
            else if(p1.direction == 'r')
                g.drawImage(ImageCtrl.p1RArr[p1i], p1.x, p1.y, 30,30,null);
            else if(p1.direction == 'u')
                g.drawImage(ImageCtrl.p1UArr[p1i], p1.x, p1.y, 30,30,null);
            else if(p1.direction == 'd')
                g.drawImage(ImageCtrl.p1DArr[p1i], p1.x, p1.y, 30,30,null);
        }
        if(p2.life >= 0){
            if(p2.direction == 'l')
                g.drawImage(ImageCtrl.p2LArr[p2i], p2.x, p2.y, 30,30,null);
            else if(p2.direction == 'r')
                g.drawImage(ImageCtrl.p2RArr[p2i], p2.x, p2.y, 30,30,null);
            else if(p2.direction == 'u')
                g.drawImage(ImageCtrl.p2UArr[p2i], p2.x, p2.y, 30,30,null);
            else if(p2.direction == 'd')
                g.drawImage(ImageCtrl.p2DArr[p2i], p2.x, p2.y, 30,30,null);
        }

        // Displaying Stats panel
        g.drawImage(ImageCtrl.bg, 850,0, 150, 800, null);
        g.setColor(Color.BLACK);
        g.fillRect(855, 310, 135, 60);
        g.setColor(Color.WHITE);
        g.drawRect(855, 310, 133, 60);
        g.setFont(f);
        g.drawString(min+" : "+sec, 880, 350);
        g.setColor(Color.BLACK);
        // p1 stats
        g.drawString("P1", 860, 40);
        g.drawImage(ImageCtrl.p1Bomber, 910, 17, 30,30,null);
        g.drawImage(ImageCtrl.icon, 860, 60, 30,30,null);
        g.drawImage(ImageCtrl.icon1, 860, 100, 30, 30, null);
        g.drawImage(ImageCtrl.scoreVPImg, 860, 140, 30, 30, null);
        g.drawImage(ImageCtrl.rangeVPImg, 860, 180, 30, 30, null);
        g.drawImage(ImageCtrl.kickVPImg, 860, 220, 30, 30, null);
        if(p1.life >= 0)
            g.drawString(p1.life+"", 900, 90);
        else{
            g.drawString("Dead", 900, 90);
            if(p2.life < 0)
                endGameFlag = 1;
        }
        g.drawString(p1.bombLimit+"", 900, 127);
        g.drawString(p1.score+"", 900, 167);
        g.drawString(p1.range+"", 900, 207);
        g.drawString(p1.kick+"", 900, 247);

        // p2 stats
        g.drawString("P2", 860, 400);
        g.drawImage(ImageCtrl.p2Bomber, 910, 377, 30, 30, null);
        g.drawImage(ImageCtrl.icon, 860, 420, 30,30,null);
        g.drawImage(ImageCtrl.icon1, 860, 460, 30, 30, null);
        g.drawImage(ImageCtrl.scoreVPImg, 860, 500, 30, 30, null);
        g.drawImage(ImageCtrl.rangeVPImg, 860, 540, 30, 30, null);
        g.drawImage(ImageCtrl.kickVPImg, 860, 580, 30, 30, null);
        if(p2.life >= 0){
            g.drawString(p2.life+"", 900, 450);
        }
        else{
            g.drawString("Dead", 900, 450);
            if(p1.life < 0)
                endGameFlag = 1;
        }
        g.drawString(p2.bombLimit+"", 900, 487);
        g.drawString(p2.score+"", 900, 527);
        g.drawString(p2.range+"", 900, 567);
        g.drawString(p2.kick+"", 900, 607);

        //  Drawing Bottom Buttons
        g.setColor(Color.BLACK);
        g.fillRect(0, 700, 850, 60);
        g.setColor(Color.WHITE);
        g.drawRect(0, 700, 283, 60);
        g.drawRect(283, 700, 283, 60);
        g.drawRect(566, 700, 283, 60);
        g.drawString("Save", 90, 740);
        g.drawString("Load", 373, 740);
        g.drawString("Quit", 656, 740);
        if(endGameFlag == 1)
            gameOver(g);
    }//end of loadGame


    public void moveP1(int xx, int yy){
        x = arr[p1.bx][p1.by].x;
        y = arr[p1.bx][p1.by].y;
        //p1.direction = direction;
        if(p1i < 4)
            p1i++;
        else
            p1i = 0;
        this.repaint();
        x=y=-1;
        if(yy == 0){  // moving in x-axis
            if(xx < 0){ //    Moving to left
                if(p1.x+xx >= arr[p1.bx][p1.by].x){    // within same block movement
                    p1.x -= p1.speed;
                }
                else{   // switching the block
                     int lType = arr[p1.bx][p1.by-1].type;  // getting left block type
                     if(lType == 2 || (p1.kick ==1 && lType == 3)){   // left block is empty
                            p1.by -= 1;   // switch the block
                            p1.x -= p1.speed;
                     }
                }
            }
            else{    // moving to right
                if(p1.x+xx+40 <= arr[p1.bx][p1.by].x+50)    // within same block movement
                    p1.x += p1.speed;
                else{   // switching the block
                     int rType = arr[p1.bx][p1.by+1].type;   // getting right block type
                     if(rType == 2 || (p1.kick ==1 && rType == 3)){   // right block is empty
                            p1.by += 1;   // switch the block
                            p1.x += p1.speed;
                     }
                }

            }
        }
        else if(xx == 0){    // moving in y-axis
            if(yy < 0){ //    Moving UP
                if(p1.y+yy >= arr[p1.bx][p1.by].y){    // within same block movement
                    p1.y -= p1.speed;
                }
                else{   // switching the block
                     int uType = arr[p1.bx-1][p1.by].type;  // getting Upper block type
                     if(uType == 2 || (p1.kick ==1 && uType == 3)){   // left block is empty
                            p1.bx -= 1;   // switch the block
                            p1.y -= p1.speed;
                     }
                }
            }
            else{    // moving Down
                if(p1.y+yy+40 <= arr[p1.bx][p1.by].y+50)    // within same block movement
                    p1.y += p1.speed;
                else{   // switching the block
                     int dType = arr[p1.bx+1][p1.by].type;   // getting right block type
                     if(dType == 2 || (p1.kick ==1 && dType == 3)){   // left block is empty
                            p1.bx += 1;   // switch the block
                            p1.y += p1.speed;
                     }
                }

            }
        }
        this.repaint();
        if(p1.kick == 1){                                           // kicking the bombs
            for(int i = 0; i<bombArr.size(); i++){
                if(p1.bx == bombArr.get(i).bx && p1.by == bombArr.get(i).by){
                    if(p1.direction == 'u'){
                        int flag = 0;
                        if(arr[bombArr.get(i).bx-1][bombArr.get(i).by].type == 2){
                            bombArr.get(i).bx -= 1;
                            bombArr.get(i).y -= 50;
                            flag =1;
                        }
                        if(bombArr.get(i).bx-2 >= 0){
                            if(arr[bombArr.get(i).bx-2][bombArr.get(i).by].type == 2 && flag == 1){
                                bombArr.get(i).bx -= 1;
                                bombArr.get(i).y -= 50;
                                flag = 2;
                            }
                        }
                        if(bombArr.get(i).bx-3 >= 0){
                            if(arr[bombArr.get(i).bx-3][bombArr.get(i).by].type == 2 && flag == 2){
                                bombArr.get(i).bx -= 1;
                                bombArr.get(i).y -= 50;
                            }
                        }
                        this.repaint();
                    }
                    else if(p1.direction == 'd'){
                        int flag = 0;
                        if(arr[bombArr.get(i).bx+1][bombArr.get(i).by].type == 2){
                            bombArr.get(i).bx += 1;
                            bombArr.get(i).y += 50;
                            flag =1;
                        }
                        if(bombArr.get(i).bx+2 < 14){
                            if(arr[bombArr.get(i).bx+2][bombArr.get(i).by].type == 2 && flag == 1){
                                bombArr.get(i).bx += 1;
                                bombArr.get(i).y += 50;
                                flag = 2;
                            }
                        }
                        if(bombArr.get(i).bx+3 < 14){
                            if(arr[bombArr.get(i).bx+3][bombArr.get(i).by].type == 2 && flag == 2){
                                bombArr.get(i).bx += 1;
                                bombArr.get(i).y += 50;
                            }
                        }
                        this.repaint();
                    }
                    else if(p1.direction == 'l'){
                        int flag = 0;
                        if(arr[bombArr.get(i).bx][bombArr.get(i).by-1].type == 2){
                            bombArr.get(i).by -= 1;
                            bombArr.get(i).x -= 50;
                            flag =1;
                        }
                        if(bombArr.get(i).by-2 >= 0){
                            if(arr[bombArr.get(i).bx][bombArr.get(i).by-2].type == 2 && flag == 1){
                                bombArr.get(i).by -= 1;
                                bombArr.get(i).x -= 50;
                                flag = 2;
                            }
                        }
                        if(bombArr.get(i).by-3 >= 0){
                            if(arr[bombArr.get(i).bx][bombArr.get(i).by-3].type == 2 && flag == 2){
                                bombArr.get(i).by -= 1;
                                bombArr.get(i).x -= 50;
                            }
                        }
                        this.repaint();
                    }
                    else if(p1.direction == 'r'){
                        int flag = 0;
                        if(arr[bombArr.get(i).bx][bombArr.get(i).by+1].type == 2){
                            bombArr.get(i).by += 1;
                            bombArr.get(i).x += 50;
                            flag =1;
                        }
                        if(bombArr.get(i).by+2 < 17){
                            if(arr[bombArr.get(i).bx][bombArr.get(i).by+2].type == 2 && flag == 1){
                                bombArr.get(i).by += 1;
                                bombArr.get(i).x += 50;
                                flag = 2;
                            }
                        }
                        if(bombArr.get(i).by+3 < 17){
                            if(arr[bombArr.get(i).bx][bombArr.get(i).by+3].type == 2 && flag == 2){
                                bombArr.get(i).by += 1;
                                bombArr.get(i).x += 50;
                            }
                        }
                        this.repaint();
                    }
                }
            }// end of for loop
        }

        if(p1.bx == vpx && p1.by == vpy){  // if any value pack is picked

            if(vpType == 0){ // Speed up Value Pack is picked
                if(p1.speed <=5)
                    p1.speed+=3;
                vpx=vpy=-1;
                this.repaint();
                st = new SpeedTimer(p1);
                ImageCtrl.speedTimerThread = new Thread(st);
                ImageCtrl.speedTimerThread.start();
            }
            else if(vpType == 1){      // bomb value pack
                p1.bombLimit += 1;
                vpx=vpy=-1;
                this.repaint();
            }
            else if(vpType == 2){    // score value pack
                p1.score++;
                vpx=vpy=-1;
                this.repaint();
            }
            else if(vpType == 3){    // additional life value pack
                p1.life++;
                vpx=vpy=-1;
                this.repaint();
            }
            else if(vpType == 4){    // additional range value pack
                p1.range++;
                vpx=vpy=-1;
                this.repaint();
            }
            else if(vpType == 5){    // Kick value pack
                vpx=vpy=-1;
                this.repaint();
                p1.kick = 1;
                kt = new KickTimer(p1);
                ImageCtrl.kickThread = new Thread(kt);
                ImageCtrl.kickThread.start();
            }
        }
    }// end of MoveP1


    public void moveP2(int xx, int yy){
        //x = arr[p1.bx][p1.by].x;
        //y = arr[p1.bx][p1.by].y;
        if(p2i < 4)
            p2i++;
        else
            p2i = 0;
        //p2.direction = direction;
        this.repaint();
        x=y=-1;
        if(yy == 0){  // moving in x-axis
            if(xx < 0){ //    Moving to left
                if(p2.x+xx >= arr[p2.bx][p2.by].x){    // within same block movement
                    p2.x -= p2.speed;
                }
                else{   // switching the block
                     int lType = arr[p2.bx][p2.by-1].type;  // getting left block type
                     if(lType == 2 || (p2.kick ==1 && lType == 3)){   // left block is empty
                            p2.by -= 1;   // switch the block
                            p2.x -= p2.speed;
                     }
                }
            }
            else{    // moving to right
                if(p2.x+xx+40 <= arr[p2.bx][p2.by].x+50)    // within same block movement
                    p2.x += p2.speed;
                else{   // switching the block
                     int rType = arr[p2.bx][p2.by+1].type;   // getting right block type
                     if(rType == 2 || (p2.kick ==1 && rType == 3)){   // left block is empty
                            p2.by += 1;   // switch the block
                            p2.x += p2.speed;
                     }
                }

            }
        }
        else if(xx == 0){    // moving in y-axis
            if(yy < 0){ //    Moving UP
                if(p2.y+yy >= arr[p2.bx][p2.by].y){    // within same block movement
                    p2.y -= p2.speed;
                }
                else{   // switching the block
                     int uType = arr[p2.bx-1][p2.by].type;  // getting Upper block type
                     if(uType == 2 || (p2.kick ==1 && uType == 3)){   // left block is empty
                            p2.bx -= 1;   // switch the block
                            p2.y -= p2.speed;
                     }
                }
            }
            else{    // moving Down
                if(p2.y+yy+40 <= arr[p2.bx][p2.by].y+50)    // within same block movement
                    p2.y += p2.speed;
                else{   // switching the block
                     int dType = arr[p2.bx+1][p2.by].type;   // getting right block type
                     if(dType == 2 || (p2.kick ==1 && dType == 3)){   // left block is empty
                            p2.bx += 1;   // switch the block
                            p2.y += p2.speed;
                     }
                }

            }
        }
        this.repaint();
        if(p2.kick == 1){                                           // kicking the bombs
            for(int i = 0; i<bombArr.size(); i++){
                if(p2.bx == bombArr.get(i).bx && p2.by == bombArr.get(i).by){
                    if(p1.direction == 'u'){
                        int flag = 0;
                        if(arr[bombArr.get(i).bx-1][bombArr.get(i).by].type == 2){
                            bombArr.get(i).bx -= 1;
                            bombArr.get(i).y -= 50;
                            flag =1;
                        }
                        if(bombArr.get(i).bx-2 >= 0){
                            if(arr[bombArr.get(i).bx-2][bombArr.get(i).by].type == 2 && flag == 1){
                                bombArr.get(i).bx -= 1;
                                bombArr.get(i).y -= 50;
                                flag = 2;
                            }
                        }
                        if(bombArr.get(i).bx-3 >= 0){
                            if(arr[bombArr.get(i).bx-3][bombArr.get(i).by].type == 2 && flag == 2){
                                bombArr.get(i).bx -= 1;
                                bombArr.get(i).y -= 50;
                            }
                        }
                        this.repaint();
                    }
                    else if(p1.direction == 'd'){
                        int flag = 0;
                        if(arr[bombArr.get(i).bx+1][bombArr.get(i).by].type == 2){
                            bombArr.get(i).bx += 1;
                            bombArr.get(i).y += 50;
                            flag =1;
                        }
                        if(bombArr.get(i).bx+2 < 14){
                            if(arr[bombArr.get(i).bx+2][bombArr.get(i).by].type == 2 && flag == 1){
                                bombArr.get(i).bx += 1;
                                bombArr.get(i).y += 50;
                                flag = 2;
                            }
                        }
                        if(bombArr.get(i).bx+3 < 14){
                            if(arr[bombArr.get(i).bx+3][bombArr.get(i).by].type == 2 && flag == 2){
                                bombArr.get(i).bx += 1;
                                bombArr.get(i).y += 50;
                            }
                        }
                        this.repaint();
                    }
                    else if(p1.direction == 'l'){
                        int flag = 0;
                        if(arr[bombArr.get(i).bx][bombArr.get(i).by-1].type == 2){
                            bombArr.get(i).by -= 1;
                            bombArr.get(i).x -= 50;
                            flag =1;
                        }
                        if(bombArr.get(i).by-2 >= 0){
                            if(arr[bombArr.get(i).bx][bombArr.get(i).by-2].type == 2 && flag == 1){
                                bombArr.get(i).by -= 1;
                                bombArr.get(i).x -= 50;
                                flag = 2;
                            }
                        }
                        if(bombArr.get(i).by-3 >= 0){
                            if(arr[bombArr.get(i).bx][bombArr.get(i).by-3].type == 2 && flag == 2){
                                bombArr.get(i).by -= 1;
                                bombArr.get(i).x -= 50;
                            }
                        }
                        this.repaint();
                    }
                    else if(p1.direction == 'r'){
                        int flag = 0;
                        if(arr[bombArr.get(i).bx][bombArr.get(i).by+1].type == 2){
                            bombArr.get(i).by += 1;
                            bombArr.get(i).x += 50;
                            flag =1;
                        }
                        if(bombArr.get(i).by+2 < 17){
                            if(arr[bombArr.get(i).bx][bombArr.get(i).by+2].type == 2 && flag == 1){
                                bombArr.get(i).by += 1;
                                bombArr.get(i).x += 50;
                                flag = 2;
                            }
                        }
                        if(bombArr.get(i).by+3 < 17){
                            if(arr[bombArr.get(i).bx][bombArr.get(i).by+3].type == 2 && flag == 2){
                                bombArr.get(i).by += 1;
                                bombArr.get(i).x += 50;
                            }
                        }
                        this.repaint();
                    }
                }
            }// end of for loop
        }
        if(p2.bx == vpx && p2.by == vpy){  // if any value pack is picked

            if(vpType == 0){ // Speed up Value Pack is picked
                if(p2.speed <=5)
                    p2.speed+=3;
                vpx=vpy=-1;
                this.repaint();
                st = new SpeedTimer(p2);
                ImageCtrl.speedTimerThread = new Thread(st);
                ImageCtrl.speedTimerThread.start();
            }
            else if(vpType == 1){     // bomb value pack
                p2.bombLimit += 1;
                vpx=vpy=-1;
                this.repaint();
            }
            else if(vpType == 2){    // score value pack
                p2.score++;
                vpx=vpy=-1;
                this.repaint();
            }
            else if(vpType == 3){    // Additional life value pack
                p2.life++;
                vpx=vpy=-1;
                this.repaint();
            }
            else if(vpType == 4){    // additional range value pack
                p2.range++;
                vpx=vpy=-1;
                this.repaint();
            }
            else if(vpType == 5){    // kick value pack
                vpx=vpy=-1;
                this.repaint();
                p2.kick = 1;
                kt = new KickTimer(p2);
                ImageCtrl.kickThread = new Thread(kt);
                ImageCtrl.kickThread.start();
            }
        }
    }// end of MoveP2

    public void placeBombP1(int x){
        bombFlag = 1;
        if(p1.bombLimit - p1.currBombs > 0){
            bomb = new Bomb(p1.x, p1.y, p1.bx, p1.by);
            arr[p1.bx][p1.by].type = 3;   // block type = bomb
            bombArr.add(bomb);
            if(p1.kick == 1){
                if(p1.direction == 'u'){
                    if(arr[p1.bx+1][p1.by].type == 2){
                        p1.bx += 1;
                        p1.y = arr[p1.bx][p1.by].y+2;
                    }
                }
                else if(p1.direction == 'd'){
                    if(arr[p1.bx-1][p1.by].type == 2){
                        p1.bx -= 1;
                        p1.y = arr[p1.bx][p1.by].y+2;
                    }
                }
                else if(p1.direction == 'l'){
                    if(arr[p1.bx][p1.by+1].type == 2){
                        p1.by += 1;
                        p1.x = arr[p1.bx][p1.by].x+2;
                    }
                }
                else if(p1.direction == 'r'){
                    if(arr[p1.bx][p1.by-1].type == 2){
                        p1.by -= 1;
                        p1.x = arr[p1.bx][p1.by].x+2;
                    }
                }
            }

            this.repaint();
            ex = new Explosion(this, p1);
            ImageCtrl.t1 = new Thread(ex);
            ImageCtrl.t1.start();
            p1.currBombs += 1;
        }
    }


    public void placeBombP2(int x){
        bombFlag = 1;
        if(p2.bombLimit - p2.currBombs > 0){
            bomb = new Bomb(p2.x, p2.y, p2.bx, p2.by);
            arr[p2.bx][p2.by].type = 3;   // block type = bomb
            bombArr.add(bomb);
            if(p2.kick == 1){
                if(p2.direction == 'u'){
                    if(arr[p2.bx+1][p2.by].type == 2){
                        p2.bx += 1;
                        p2.y = arr[p2.bx][p2.by].y+2;
                    }
                }
                else if(p2.direction == 'd'){
                    if(arr[p2.bx-1][p2.by].type == 2){
                        p2.bx -= 1;
                        p2.y = arr[p2.bx][p2.by].y+2;
                    }
                }
                else if(p2.direction == 'l'){
                    if(arr[p2.bx][p2.by+1].type == 2){
                        p2.by += 1;
                        p2.x = arr[p2.bx][p2.by].x+2;
                    }
                }
                else if(p2.direction == 'r'){
                    if(arr[p2.bx][p2.by-1].type == 2){
                        p2.by -= 1;
                        p2.x = arr[p2.bx][p2.by].x+2;
                    }
                }
            }
            this.repaint();
            ex = new Explosion(this, p2);
            ImageCtrl.t1 = new Thread(ex);
            ImageCtrl.t1.start();
            p2.currBombs += 1;
        }
    }


    public void updateClock(){
        if(sec > 0)
            sec--;
        else{
            sec = 59;
            if(min > 0){
                min--;
            }
        }
        this.repaint();
        if(min ==0 && sec ==0)
            endGameFlag = 1;
    }


    public void gameOver(Graphics g){
        //Graphics g = this.getGraphics();
        if(p1.score + p1.life + p1.bombLimit == p2.score + p2.life + p2.bombLimit){
            g.drawImage(ImageCtrl.drawImg, 0,0, 1000, 800, null);
        }
        else if(p1.score + p1.life + p1.bombLimit > p2.score + p2.life + p2.bombLimit){
            g.drawImage(ImageCtrl.p1WinsImg, 0,0, 1000, 800, null);
        }
        else if(p1.score + p1.life + p1.bombLimit < p2.score + p2.life + p2.bombLimit){
            g.drawImage(ImageCtrl.p2WinsImg, 0,0, 1000, 800, null);
        }
        g.drawImage(ImageCtrl.pressEnterImg, 20,500, 544, 36, null);
        mg.startMode = 1;
    }
}
