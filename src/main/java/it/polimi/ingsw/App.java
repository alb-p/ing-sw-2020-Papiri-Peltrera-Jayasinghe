package it.polimi.ingsw;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;

import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.TreeMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )throws Exception{
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        view.addViewListener(controller);
        model.addModelListener(view);
        view.run();

    }
}
