package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Worker{

    private Color color;
    private Coordinate[] coord;


    public Worker(int row, int col, String color){
        this.coord=new Coordinate[3];
        this.coord[0] =  new Coordinate(row, col);
        this.coord[1] =  new Coordinate(row, col);
        this.coord[2] =  new Coordinate(row, col);
        this.color = Enum.valueOf(Color.class, color.toUpperCase());


    }

    public Worker(Coordinate coordinate, String color){
        this.coord=new Coordinate[3];
        this.coord[0] = coordinate;
        this.coord[1] = coordinate;
        this.coord[2] = coordinate;
        this.color = Enum.valueOf(Color.class, color.toUpperCase());
    }


    public Coordinate getPosition(){
        return this.coord[0];
    }

    public Coordinate getOldPosition(){

        return this.coord[1];
    }

    public void setPosition(Coordinate coord){

            this.coord[2]=this.coord[1];
            this.coord[1]=this.coord[0];
            this.coord[0] = coord;


    }



    public String toString(){
        return this.color.toString();
    }

    //TODO is it necessary?
    public Worker getColleague(IslandBoard board) throws Exception {
        Coordinate coord;
        Slot slot;
        for(int i=0;i<board.board.length;i++){
            for(int j=0;j<board.board.length;j++){
                coord=new Coordinate(i,j);
                slot=board.infoSlot(coord);
                if(slot.getWorker()!=null&&slot.getWorker().color==this.color&&coord!=this.getPosition())
                    return slot.getWorker();
            }

        }
        return null;
    }

    public Color getColor() {
        return this.color;
    }

    public ArrayList<Coordinate> getAdiacentCoords(){        //it returns a list of adiacent coordinates
        ArrayList<Coordinate> lista= new ArrayList<>();
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                if(this.getPosition().getCol()+i<5 && this.getPosition().getCol()+i>=0
                && this.getPosition().getRow()+j<5 && this.getPosition().getRow()+j>=0
                && (i!=0 || j!=0))
                    lista.add(new Coordinate(this.getPosition().getRow()+j,this.getPosition().getCol()+i));
            }
        }

    return lista;
    }
}

