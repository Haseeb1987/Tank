import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class MainGUI implements Serializable, ActionListener{
    /////////////class members/////////////////////////////////////////////////
    JFrame fr;
    JPanel pl;
    ///////////////refrencess/////////////////////////////////////////////////
    GMPanel menu;
    GamePanel gp;
    PlayerSelectionPanel psp;
    ImageCtrl img;

    Container c;

    int startMode;
    String p1Img, p2Img;
    JButton start, exit, load;
    JComboBox jcb;
    String[] mapArr;
    String map;

    /** Creates a new instance of MainGUI */
    public MainGUI() {
        img = new ImageCtrl();
        startMode = 0;

        p1Img = null;
        p2Img = null;

        fr = new JFrame("Bomber Man");
        menu = new GMPanel();
        psp = new PlayerSelectionPanel();

        start = new JButton("Start");
        load = new JButton("Load");
        exit = new JButton("Exit");
        mapArr = new String[3];

        mapArr[0] = "Map 1";
        mapArr[1] = "Map 2";
        mapArr[2] = "Random Map";

        jcb = new JComboBox(mapArr);
        jcb.addActionListener(this);

        menu.add(start);
        menu.add(load);
        menu.add(exit);
        menu.add(jcb);

        fr.addMouseListener( new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                int x = e.getX();
                int y = e.getY();
                if(x > 0 && x < 283 && y > 735 && startMode == 2){       //  Save Button
                    ImageCtrl.clockThread.suspend();
                    ImageCtrl.vpgThread.suspend();
                    String fileName = null;
                    ImageCtrl.jfc = new JFileChooser();
                    try {
                        int returnVal = ImageCtrl.jfc.showOpenDialog(null);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File f = ImageCtrl.jfc.getSelectedFile();
                            fileName = f.getAbsolutePath();
                            //if(testFileName(f.getName()) == 1){
                                FileOutputStream fos = new FileOutputStream(fileName);
                                ObjectOutputStream out = new ObjectOutputStream(fos);
                                //serialization
                                out.writeObject(gp);
                                out.close();
                                fos.close();
                                JOptionPane.showMessageDialog(null, "Game Saved.");
                            /*}
                            else
                                JOptionPane.showMessageDialog(null, "Game is not Saved.");*/
                        }
                    } catch (Exception ex){
                        System.out.println(ex);
                    }
                    ImageCtrl.clockThread.resume();
                    ImageCtrl.vpgThread.resume();

                }
                else if(x > 283 && x < 566 && y > 735 && startMode == 2){    // Load Button
                    loadGame();
                }
                else if(x > 566 && x < 849 && y > 735 && startMode == 2){     // Quit Game
                    int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit Game ?");
                    if(i == 0){
                        c.removeAll();
                        c.add(menu);
                        menu.repaint();
                    }
                }
            }
        }
        );
        fr.addKeyListener( new KeyAdapter(){
                public void keyPressed(KeyEvent ke){
                    if(ke.getKeyCode() == KeyEvent.VK_UP){
                        if(startMode == 2){
                            gp.p1.direction = 'u';
                        }
                    }
                    else if(ke.getKeyCode() == KeyEvent.VK_DOWN){
                        if(startMode == 2){
                            gp.p1.direction = 'd';
                        }
                    }
                    else if(ke.getKeyCode() == KeyEvent.VK_LEFT){
                        if(startMode == 1){
                            if(psp.p1 > 1){
                                if(p1Img == null){
                                    if(psp.occArr[psp.p1-2] == 1){
                                        if(psp.p1-2 > 0)
                                            setSelected(1,-2);
                                    }
                                    else
                                        setSelected(1,-1);
                                }
                            }
                        }
                        else if(startMode == 2){
                            gp.p1.direction = 'l';
                        }
                    }
                    else if(ke.getKeyCode() == KeyEvent.VK_RIGHT){
                        if(startMode == 1){
                            if(p1Img == null){
                                if(psp.p1 < 3){
                                    if(psp.occArr[psp.p1] == 1){
                                        if(psp.p1+2 < 4)
                                            setSelected(1,2);
                                    }
                                    else
                                        setSelected(1,1);
                                }
                            }
                        }
                        else if(startMode == 2){
                            gp.p1.direction = 'r';
                        }
                    }
                    else if(ke.getKeyChar() == 'b' || ke.getKeyChar() == 'B'){
                        if(startMode == 2){
                            gp.placeBombP1(1);   // by player 1
                        }
                    }
                    else if(ke.getKeyCode() == KeyEvent.VK_ENTER){
                        if(startMode == 1){
                            fixPlayer(1);
                        }
                    }
                    else if(ke.getKeyChar() == 'a'){                                         //    Keys for Player TWO
                        if(startMode == 1){
                            if(psp.p2 > 1){
                                if(p2Img == null){
                                    if(psp.occArr[psp.p2-2] == 1){
                                        if(psp.p2-2 > 0)
                                            setSelected(2,-2);
                                    }
                                    else
                                        setSelected(2,-1);
                                }
                            }
                        }
                        else if(startMode == 2){
                            gp.p2.direction = 'l';
                        }
                    }
                    else if(ke.getKeyChar() == 'd'){                                         //    Keys for Player TWO
                        if(startMode == 1){
                            if(psp.p2 < 3){
                                if(p2Img == null){
                                    if(psp.occArr[psp.p2] == 1){
                                        if(psp.p2+2 < 4)
                                            setSelected(2,2);
                                    }
                                    else
                                        setSelected(2,1);
                                }
                            }
                        }
                        else if(startMode == 2){
                            gp.p2.direction = 'r';
                        }
                    }
                    else if(ke.getKeyChar() == 'w'){                                         //    Keys for Player TWO
                        if(startMode == 2){
                            gp.p2.direction = 'u';
                        }
                    }
                    else if(ke.getKeyChar() == 's'){                                         //    Keys for Player TWO
                        if(startMode == 2){
                            gp.p2.direction = 'd';
                        }
                    }
                    else if(ke.getKeyChar() == 'z' || ke.getKeyChar() == 'Z'){
                        if(startMode == 1){
                            fixPlayer(2);
                        }
                    }
                    else if(ke.getKeyChar() == 'x' || ke.getKeyChar() == 'X'){
                        if(startMode == 2){
                            gp.placeBombP2(1);   // by player 2
                        }
                    }
                }
            }
        );
    initGUI();
    }

    public void initGUI(){
        //f = new JFrame("Bomber Man");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setResizable(false);
        fr.setLayout(new BorderLayout());
        c = fr.getContentPane();

        if(startMode == 0){
            c.removeAll();
            c.add(menu);
        }
        else if(startMode == 1){
            c.removeAll();
            c.add(psp);
        }
        else if(startMode == 2){
            c.removeAll();
            map = (String)jcb.getSelectedItem();
            gp = new GamePanel(p1Img, p2Img, this, map);
            c.add(gp, BorderLayout.CENTER);
        }


        start.addActionListener(this);
        load.addActionListener(this);
        exit.addActionListener(this);
        fr.setFocusable(true);
        fr.setSize(1000,800);
        fr.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == start){
            changeGameMode(1);
        }
        else if(ae.getSource() == load){
            loadGame();
        }
        else if(ae.getSource() == exit){
            //quitGame();
            System.exit(0);
        }
        else if(ae.getSource() == jcb){
            if(jcb.getSelectedIndex() == 0)
                menu.map = "Map 1";
            else if(jcb.getSelectedIndex() == 1)
                menu.map = "Map 2";
            else
                menu.map = null;
            menu.repaint();
        }
    }

    public void changeGameMode(int i){
        startMode = i;
        initGUI();
    }

    public void quitGame(){
        System.exit(0);
    }

    public void setSelected(int player, int value){
        if(player == 1){   // player 1
            psp.setSelectedP1(value);
        }
        else if(player == 2){   // player 2
            psp.setSelectedP2(value);
        }
    }

    public void fixPlayer(int i){
        if(i == 1){   // player 1
            psp.occArr[psp.p1-1] = 1;
            if(psp.p1 == 1)
                p1Img = psp.img1;
            else if(psp.p1 == 2)
                p1Img = psp.img2;
            else if(psp.p1 == 3)
                p1Img = psp.img3;
            if(p2Img != null){
                startMode = 2;
                initGUI();
            }
        }
        else if(i == 2){  // player 2
            psp.occArr[psp.p2-1] = 1;
            if(psp.p2 == 1)
                p2Img = psp.img1;
            else if(psp.p2 == 2)
                p2Img = psp.img2;
            else if(psp.p2 == 3)
                p2Img = psp.img3;
            if(p1Img != null){
                startMode = 2;
                initGUI();
            }
        }
    }

    public void loadGame(){
        try {
            String fileName = null;
            ImageCtrl.jfc = new JFileChooser();
            int returnVal = ImageCtrl.jfc.showOpenDialog(fr);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = ImageCtrl.jfc.getSelectedFile();
                fileName = file.getAbsolutePath();
                FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fis);
                //de - serialization
                gp = (GamePanel)in.readObject();
                in.close();
                fis.close();
                c.removeAll();
                ImageCtrl.populatePlayerImages(gp.p1, gp.p2);
                c.add(gp, BorderLayout.CENTER);
                startMode = 2;
                gp.repaint();
                for(int i = 0; i<gp.bombArr.size();i++){
                    ImageCtrl.t1 = new Thread(gp.ex);
                    ImageCtrl.t1.start();
                }
                // restarting the threads
                ImageCtrl.vpgThread = new Thread(gp.vpg);
                ImageCtrl.vpgThread.start();
                ImageCtrl.clockThread = new Thread(gp.c);
                ImageCtrl.clockThread.start();

                ImageCtrl.movePlayersThread = new Thread(new MovePlayersThread(gp));
                ImageCtrl.movePlayersThread.start();
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage()+"\nFile Might not be in correct format");
        }
    }// end of load Game

    public int testFileName(String fileName){
        if(fileName == null)
            return 0;
        String[] tokens = fileName.split(".", 2);
        if(tokens.length == 1)
            return 0;
        if(tokens[tokens.length-1].equals("dat") == false)
           return 0;
        return 1;
    }
}
