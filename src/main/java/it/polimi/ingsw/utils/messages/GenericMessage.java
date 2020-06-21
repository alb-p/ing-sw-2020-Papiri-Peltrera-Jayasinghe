package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

public class GenericMessage implements Message , Serializable {

    private static final long serialVersionUID = 4539218544758041688L;
    private String message;
    private int id;

    public GenericMessage(){}


    public GenericMessage(int i,String nickname, String message){
        this.message=nickname+message;
        this.id=i;
    }

    public GenericMessage(int id) {
        this.id = id;
    }


    public int getId(){
        return this.id;
    }
    public String getMessage() {
        return message;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

}
