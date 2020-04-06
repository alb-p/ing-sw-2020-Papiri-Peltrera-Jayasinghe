package it.polimi.ingsw.model;

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

    public Worker getCollega(IslandBoard board) throws Exception {
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
}