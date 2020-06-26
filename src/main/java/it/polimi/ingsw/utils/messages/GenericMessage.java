package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

/**
 * The type Generic message is a message
 *  * that vehicles generic information.
 */
public class GenericMessage implements Message , Serializable {

    private static final long serialVersionUID = 4539218544758041688L;
    private String message;
    private int id;

    /**
     * Instantiates a new Generic message.
     */
    public GenericMessage(){}


    /**
     * Instantiates a new Generic message.
     *
     * @param i        the
     * @param nickname the nickname
     * @param message  the message
     */
    public GenericMessage(int i,String nickname, String message){
        this.message=nickname+message;
        this.id=i;
    }

    /**
     * Instantiates a new Generic message.
     *
     * @param id the id
     */
    public GenericMessage(int id) {
        this.id = id;
    }


    /**
     * Get id int.
     *
     * @return the int
     */
    public int getId(){
        return this.id;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

}
