package it.polimi.ingsw.view;

import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.utils.messages.ActionMessage;
import it.polimi.ingsw.utils.messages.ColorMessage;
import it.polimi.ingsw.utils.messages.GodMessage;
import it.polimi.ingsw.utils.messages.NicknameMessage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class CLI extends RemoteView implements Runnable, PropertyChangeListener {

    private Scanner scanner;
    private PrintWriter socketOut;
    private PrintStream printer;
    private Console console;
    String playerChoice;
    private IslandBoard board;
    private PropertyChangeSupport viewListeners = new PropertyChangeSupport(this);



    public CLI(Socket socket) throws IOException {
        this.scanner = new Scanner(System.in);
        this.socketOut = new PrintWriter(socket.getOutputStream());
        this.printer= System.out;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //At the beginning of the game, Model sends a board copy
        if (evt.getPropertyName().equals("initialBoard")) {
            board = (IslandBoard) evt.getNewValue();
            System.out.println(board);
            
        }
    }

    public NicknameMessage askNickPlayer(NicknameMessage message){
        printer.println(message.getMessage()+ "\n");
        playerChoice = console.readLine();
        message.setNick(playerChoice);
        return message;
    }

    @Override
    public void welcomeMessage() {
        printer.println("\n" +
                "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░╔╦═╦╗░░░░░░░░░░░░░╔╗░░░░░░░░░░░░\n" +
                "░░░░░║║║║╠═╦╗╔═╦═╦══╦═╗║╚╦═╗░░░░░░░░░\n" +
                "░░░░░║║║║║╩╣╚╣═╣╬║║║║╩╣║╔╣╬║░░░░░░░░░\n" +
                "░░░░░╚═╩═╩═╩═╩═╩═╩╩╩╩═╝╚═╩═╝░░░░░░░░░\n" +
                "░░░░░╔══╗░░░░░╔╗░░░░╔╗░░╔╗░░░░░░░░░░░\n" +
                "░░░░░║══╬═╗╔═╦╣╚╦═╦╦╬╬═╦╬╣░░░░░░░░░░░\n" +
                "░░░░░╠══║╬╚╣║║║╔╣╬║╔╣║║║║║░░░░░░░░░░░\n" +
                "░░░░░╚══╩══╩╩═╩═╩═╩╝╚╩╩═╩╝░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n");
    }

    @Override
    public ColorMessage askColor(ColorMessage message, String nickname) {
        printer.println("Ciao "+ nickname +"! Quale colore vuoi scegliere tra quelli disponibili? ");
        for(String s: message.getColors()){
            printer.printf(s+" ");
        }
        playerChoice = scanner.nextLine();
        message.setColor(playerChoice);
        return message;
    }

    @Override
    public GodMessage askGod(GodMessage inputObject, String nickname) {
        //TODO da finire dopo commit di sandro
        return null;
    }

    @Override
    public ActionMessage askAction(ActionMessage message, String nickname) {
        printer.println(nickname+ " is your turn! " + message.getAction() + "x,y in z,w" );
        startingBrackets();
        return null;
    }



    public void startingBrackets(){
        printer.printf(">>>");
    }


}