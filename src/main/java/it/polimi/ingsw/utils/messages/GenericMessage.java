package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

public class GenericMessage implements Message , Serializable {

    private String message;
    private int id;

    public GenericMessage(int i,String s){
        this.message=s;
        this.id=i;
    }


    public int getId(){
        return this.id;
    }
    public String getMessage() {
        return message;
    }

    @Override
    public void setId(int i) {

    }

}
