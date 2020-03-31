package it.polimi.ingsw.model;

//import it.polimi.ingsw.gods.Artemis;
//import it.polimi.ingsw.gods.Atlas;

import java.util.Scanner;
import java.util.ArrayList;

public class Player {


    private ArrayList<Worker> workers = new ArrayList<Worker>();

    private String nickName;
    private BasicGodCard card;


    public Player(String nickName, String color) {

        this.nickName = nickName;

        workers.add(new Worker(0, 0, color));
        workers.add(new Worker(0, 0, color));

    }

    public void setCard(String card) {
        BasicGodCard godCard = new BasicGodCard();
        if(card.equals("ATLAS")){
          //  this.card = new Atlas();
            return;
        } else if(card.equals("ARTEMIS")){
          //  this.card = new Artemis();
            return;
        }
        else {this.card = godCard;}
    }

    public BasicGodCard getCard() {
        return this.card;
    }


    public void setWorker(IslandBoard board) throws Exception {
        Scanner in = new Scanner(System.in);

        for(int i = 0 ; i<workers.size(); i++){
            System.out.println("Set position for worker no "+i+":\n\trow :");
            int row = in.nextInt();
            System.out.println("\tcol :");
            int col = in.nextInt();
            Coordinate coord = new Coordinate(row,col);
            while(!board.infoSlot(coord).isFree()){

                System.out.println("Invalid position,\nSet new position for worker no "+i+":\n\trow :");
                row = in.nextInt();
                System.out.println("\tcol :");
                col = in.nextInt();
                coord = new Coordinate(row,col);

            }
            board.infoSlot(coord).occupy(workers.get(i));
            workers.get(i).setPosition(coord);

        }
    }
    public String getNickName() {
        return this.nickName;
    }



    public Worker getWorker(Coordinate c){
        for(int i = 0; i< workers.size(); i++){
            if(c.equals(workers.get(i).getPosition())){
                return workers.get(i);
            }
        }
        return null;
    }

    public Worker getWorker(int i){
        return this.workers.get(i);
    }

    public void turnHan(IslandBoard board, String message)throws Exception{
        this.card.turnHandler(this, board, message);
        return;
    }



}
