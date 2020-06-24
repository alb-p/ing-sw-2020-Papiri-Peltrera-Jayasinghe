package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ANSIColor;

import java.io.IOException;

/**
 * The type Slot.
 */
public class Slot {

    private Construction buildings[] = new Construction[4];
    private boolean free;
    private Worker worker;

    /**
     * Instantiates a new Slot.
     */
    public Slot(){
        this.free= true;
        this.worker=null;
    }

    /**
     * Is free boolean.
     *
     * @return the boolean
     */
    public boolean isFree(){
        return free;
    }

    /**
     * Construct.
     *
     * @param c the c
     * @throws Exception the exception
     */
    public void construct(Construction c) throws Exception {
        for(int i = 0 ; i< buildings.length; i++){
            if(buildings[i] == null && this.isFree() && !(c.equals(Construction.DOME))){
                if(i==0 || !(buildings[i-1].equals(Construction.DOME))){
                    buildings[i]= c;
                    return;
                }
            }if(buildings[i] == null && c.equals(Construction.DOME) && this.isFree()){
                this.free = false;
                buildings[i]= c;
                return;

            }
        }
        throw new Exception("Invalid construction error");
    }

    /**
     * Get construction level int.
     *
     * @return the int
     */
    public int getConstructionLevel(){
        int level = 0;
        for(int i = 0; i<buildings.length && buildings[i]!=null ; i++ ){
            if(buildings[i].equals(Construction.FLOOR))level++;
        }
        return level;
    }

    /**
     * Has a dome boolean.
     *
     * @return the boolean
     */
    public boolean hasADome(){
        for(int i = 0; i<buildings.length && buildings[i]!=null ; i++ ){
            if(buildings[i].equals(Construction.DOME))return true;
        }
       return false;
    }

    /**
     * Occupy.
     *
     * @param w the w
     */
    public void occupy(Worker w){
        if(this.isFree()){this.worker=w;}
        else{
            return;
        }
        this.free = false;
    }

    /**
     * Worker on slot boolean.
     *
     * @return the boolean
     */
    public boolean workerOnSlot(){
        if(worker == null) return false;
        return true;
    }

    /**
     * Free.
     */
    public void free(){
        this.worker = null;
        this.free= true;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    public String toString(){
        String color,floor;
        if(this.worker==null) color=" ";
        else color=this.worker.toString();
        if(hasADome()) floor="D";
        else if(this.worker==null && getConstructionLevel()==0) {floor = " ";}
        else{
            floor= String.valueOf(getConstructionLevel());
        }
        return (color+floor+ANSIColor.RESET);
    }

    /**
     * Gets worker.
     *
     * @return the worker
     */
    public Worker getWorker() {
        return this.worker;
    }


}