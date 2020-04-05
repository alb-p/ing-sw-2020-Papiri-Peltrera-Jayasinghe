package it.polimi.ingsw.model;

import java.io.IOException;

public class Slot{

    private Construction buildings[] = new Construction[4];
    private boolean free;
    private Worker worker;

    public Slot(){
        this.free= true;
        this.worker=null;
    }

    public boolean isFree(){
        return free;
    }

/*
    public void construct() throws Exception {
        if(this.isFree()){
            if(this.getConstructionLevel()<3){
                buildings[this.getConstructionLevel()+1]=Construction.FLOOR;
            }else{
                buildings[3]=Construction.DOME;
                this.free=false;
            }
        }else{
            throw new IOException("Illegal build!!");
        }
    }
*/
    public void construct(Construction c) throws Exception {
        for(int i = 0 ; i< buildings.length-1; i++){
            if(buildings[i] == null && this.isFree() && !(c.equals(Construction.DOME))){
                if(i==0 || !(buildings[i-1].equals(Construction.DOME))){
                    buildings[i]= c;
                    return;
                }

            }if(c.equals(Construction.DOME) && this.isFree()){
                this.free = false;
                buildings[i]= c;
                return;

            }
        }
        throw new Exception("Invalid construction error");
    }

    public int getConstructionLevel(){
        int level = 0;
        for(int i = 0; i<buildings.length && buildings[i]!=null ; i++ ){
            //if(buildings[i] instanceof FirstFloor || buildings[i] instanceof SecondFloor || buildings[i] instanceof ThirdFloor)level++;
            if(buildings[i].equals(Construction.FLOOR))level++;
        }
        return level;
    }

    public boolean hasADome(){
        for(int i = 0; i<buildings.length && buildings[i]!=null ; i++ ){
            if(buildings[i].equals(Construction.DOME))return true;
        }
       return false;
    }

    public void occupy(Worker w) throws Exception {
        if(this.isFree()){this.worker=w;}
        else{
            throw new Exception("The selected cell is not free");
        }
        this.free = false;
    }

    public void free(){
        this.worker = null;
        this.free= true;
    }
    public String toString(){

        String color,floor;

        if(this.worker==null) color=" ";
        else color=this.worker.toString();

        if(hasADome()) floor="D";
        else if(this.worker==null && getConstructionLevel()==0) {floor = " ";}
        else{
            floor= String.valueOf(getConstructionLevel());
        }

        return (color+floor);
    }

    public Worker getWorker() {
        return this.worker;
    }




}