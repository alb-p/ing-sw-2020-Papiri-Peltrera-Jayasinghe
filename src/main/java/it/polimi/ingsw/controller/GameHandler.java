package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.InitSetup;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameHandler implements PropertyChangeListener {

    InitSetup data;

    public GameHandler(InitSetup initSetup) {
        data = initSetup;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) { //gestire caso input errato
        if (evt.getPropertyName().equals("colorMessage")) {
            String color = (String) evt.getNewValue();
            if (data.isInColor(color))
                data.delColor(color);
        } else if (evt.getPropertyName().equals("userMessage")) {
            String name = (String) evt.getNewValue();
            if (!data.isInUser(name))
                data.setUsername(name);
        } else if (evt.getPropertyName().equals("godMessage")) {
            String god = (String) evt.getNewValue();
            if (data.isInGod(god))
                data.delGod(god);
        }
    }


}
