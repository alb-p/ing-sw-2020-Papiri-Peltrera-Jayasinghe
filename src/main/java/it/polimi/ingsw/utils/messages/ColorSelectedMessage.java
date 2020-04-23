package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Color;

import java.io.Serializable;

public class ColorSelectedMessage implements Message , Serializable {
     String message;
     int id;

     public ColorSelectedMessage(int i, Color c){
         this.id=i;
         this.message="Il colore "+c.getName()+" è stato scelto";

     }


    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public void setId(int i) {

    }

    @Override
    public int getId() {
        return 0;
    }
}
