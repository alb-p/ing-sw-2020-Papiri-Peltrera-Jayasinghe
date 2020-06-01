package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.controller.TurnHandler;
import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.utils.messages.WorkerMessage;
import it.polimi.ingsw.view.ModelView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PlayPanel  extends JPanel implements ActionListener, PropertyChangeListener {

    private PropertyChangeSupport playPanelListener = new PropertyChangeSupport(this);


    int workerPlaced=0;

    private ModelView modelView;//da impostare
    private int playerID;//da impostare
    private String color;

    private TileButton boardOfButtons[][]=new TileButton[5][5];


    public PlayPanel(int id){
        this.setLayout(new GridLayout(5,5));

        for(int row=4;row>=0;row--){
            for(int col=0;col<5;col++){
                boardOfButtons[row][col]=new TileButton(row,col);
                boardOfButtons[row][col].addActionListener(this);
                this.add(boardOfButtons[row][col]);
            }
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.playerID != modelView.getActualPlayerId()&&e.getSource().equals("tilebutton")){
            TileButton t= (TileButton) e.getSource();

            if(workerPlaced<2) setWorkers(t.getCoordinate());

            else{
                turnHandler(t.getCoordinate());
            }

        }else{
            System.out.println("non è il tuo turno coglione");
        }


    }

    private void turnHandler(Coordinate coordinate) {



    }

    private void setWorkers(Coordinate c) {
       if(modelView.getBoard().getSlot(c).isFree()) {
           workerPlaced++;
           boardOfButtons[c.getRow()][c.getCol()].setColor(color);
           this.playPanelListener.firePropertyChange("workerReceived", null, c);
       }else System.out.println("è occupato");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public void addPlayPanelListener(PropertyChangeListener listener) {
        playPanelListener.addPropertyChangeListener(listener);
    }


}
