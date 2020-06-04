package it.polimi.ingsw.view.GUIpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class InitialWaitingPanel extends JPanel implements ActionListener {


    Image background;
    private JLabel label;
    private Timer timer;
    ImageIcon[] frames=new ImageIcon[7];
    int currentFrame=0;
    JLabel animation;
    private Dimension frameDimension;

    public InitialWaitingPanel(Dimension d) throws IOException, FontFormatException{
        frameDimension=d;

        JPanel innerPanel =new JPanel(){
            Image image= new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png")).getImage().getScaledInstance((int) (frameDimension.width/1.5),(int) (frameDimension.height/1.95),Image.SCALE_SMOOTH);
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(this.image, 0, 0, this);
            }
        };

        for(int i =0;i<7;i++){
            frames[i]=new ImageIcon(this.getClass().getResource("/SelectPlayers/Loading/loading"+(i+1)+".png"));
        }


        timer =new Timer(500,this);
        animation=new JLabel();
        this.background=new ImageIcon(this.getClass().getResource("/Home/HomeBG.jpg")).getImage();
        label=new JLabel("Waiting for players...");
        Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")); //carica font


        innerPanel.setOpaque(false);
        innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.Y_AXIS));
        this.setLayout(new GridBagLayout());
        label.setFont(font.deriveFont(Font.PLAIN,frameDimension.width/18)); //imposta font liscio e dimensione 45
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.setPreferredSize(new Dimension((int) (frameDimension.width/1.5),(int) (frameDimension.height/1.95)));
        animation.setAlignmentX(Component.CENTER_ALIGNMENT);




        innerPanel.add(Box.createRigidArea(new Dimension(0,(int) (frameDimension.height/15))));
        innerPanel.add(label);
        innerPanel.add(animation);

        this.add(innerPanel);

        startTimer();



    }

    public void startTimer(){
        this.timer.start();
    }

    public void stopTimer(){
        this.timer.stop();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.background, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        animation.setIcon(frames[this.currentFrame%this.frames.length]);
        if (this.currentFrame==120) stopTimer(); //l'animazione va avanti per 120 transizioni
        else this.currentFrame++;
    }






}