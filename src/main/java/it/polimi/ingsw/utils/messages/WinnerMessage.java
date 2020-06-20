package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

public class WinnerMessage implements Message, Serializable {

    private static final long serialVersionUID = 7815827983162006979L;
    private final int id;
    private final String message;
    public WinnerMessage(int id, String nickname){
        this.id = id;
        this.message = nickname+ "\n" +
                "$$\\   $$\\  $$$$$$\\   $$$$$$\\        $$\\      $$\\  $$$$$$\\  $$\\   $$\\ \n" +
                "$$ |  $$ |$$  __$$\\ $$  __$$\\       $$ | $\\  $$ |$$  __$$\\ $$$\\  $$ |\n" +
                "$$ |  $$ |$$ /  $$ |$$ /  \\__|      $$ |$$$\\ $$ |$$ /  $$ |$$$$\\ $$ |\n" +
                "$$$$$$$$ |$$$$$$$$ |\\$$$$$$\\        $$ $$ $$\\$$ |$$ |  $$ |$$ $$\\$$ |\n" +
                "$$  __$$ |$$  __$$ | \\____$$\\       $$$$  _$$$$ |$$ |  $$ |$$ \\$$$$ |\n" +
                "$$ |  $$ |$$ |  $$ |$$\\   $$ |      $$$  / \\$$$ |$$ |  $$ |$$ |\\$$$ |\n" +
                "$$ |  $$ |$$ |  $$ |\\$$$$$$  |      $$  /   \\$$ | $$$$$$  |$$ | \\$$ |\n" +
                "\\__|  \\__|\\__|  \\__| \\______/       \\__/     \\__| \\______/ \\__|  \\__|\n" +
                "                                                                     \n" +
                "                                                                     \n" +
                "                                                                     ";
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setId(int i) {

    }

    @Override
    public int getId() {
        return id;
    }
}
