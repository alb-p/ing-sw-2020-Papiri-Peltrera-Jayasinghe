package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.ActionsEnum;
import it.polimi.ingsw.utils.messages.*;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class CLI extends RemoteView implements Runnable {

    private Scanner scanner;
    private PrintStream printer;
    private Console console;
    private String playerChoice;
    private IslandBoard board;
    private String nickname;



    public CLI(){
        this.scanner = new Scanner(System.in);
        this.printer= System.out;
    }


    public NicknameMessage askNickPlayer(NicknameMessage message){
        printer.println(message.getMessage()+ "\n");
        startingBrackets();
        playerChoice = scanner.nextLine();
        message.setNick(playerChoice);
        this.nickname = playerChoice;
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
    public ColorMessage askColor(ColorMessage message) {
        printer.println("Ciao "+ nickname +"! Quale colore vuoi scegliere tra quelli disponibili?");
        for(Color c: message.getColors()){
            printer.printf(c.getName()+" ");
        }
        startingBrackets();
        playerChoice = scanner.nextLine();
        for(Color c: Color.values())
            if(playerChoice.equalsIgnoreCase(c.getName()))
                message.setColor(c);
        return message;
    }

    @Override
    public InitialCardsMessage askGodList(InitialCardsMessage message) {
        printer.println(message.getMessage());
        for(String s: message.getCompleteList())
            printer.println(s);
        String s;
        for (int i= 0; i<message.getDim(); i++){
            startingBrackets();
            s= scanner.nextLine();
            message.addToSelectedList(s.toUpperCase());
        }
        return message;
    }

    @Override
    public void showColor(ColorSelectedMessage inputObject) {
        printer.println(inputObject.getMessage());
    }

    @Override
    public FirstPlayerMessage firstPlayer(FirstPlayerMessage message) {
        printer.println(message.getMessage());
        startingBrackets();
        String name = scanner.nextLine();
        for(String s: message.getNames())
            if(s.equalsIgnoreCase(name))
                message.setChosenName(name);
        return message;
    }

    @Override
    public WorkerMessage setWorker(WorkerMessage message) {
        printer.println(message.getMessage());
        startingBrackets();
        printer.print("row: ");
        int row = Integer.parseInt( scanner.nextLine());
        startingBrackets();
        printer.print("col: ");
        int col = Integer.parseInt( scanner.nextLine());
        Coordinate c=new Coordinate(row,col);
        message.setCoordinate(c);

        return message;
    }

    @Override
    public void gameIsReady(StartGameMessage inputObject) {
        printer.println(inputObject.getMessage());
    }


    @Override
    public GodMessage askGod(GodMessage inputObject) {
        printer.println(inputObject.getMessage());
        startingBrackets();
        inputObject.setGod(scanner.nextLine().toUpperCase());
        return inputObject;
    }

    @Override
    public ActionMessage askAction(ActionMessage message) {
        printer.println(nickname+ " make " + message.getActionsAvailable() + "x,y in z,w" );
        startingBrackets();
        Action action = null;
        if(message.getActionsAvailable() == ActionsEnum.MOVE){
            action = new Move(null, null);
        }else if(message.getActionsAvailable() == ActionsEnum.BUILD){
            action = new Build(null, null);
        }else{
            //aggiungere caso BOTH
        }
        playerChoice = scanner.nextLine();
        message.setAction(parseAction(playerChoice, action));
        return message;
    }

    public SetupMessage askNumOfPlayers(SetupMessage message){
        printer.println(message.getMessage());
        startingBrackets();
        int i = Integer.parseInt( scanner.nextLine());
        message.setField(i);
        return message;
    }


    public void startingBrackets(){
        printer.printf(">>>");
    }

    public Action parseAction(String input, Action action){
        String[] start;
        String[] end;
        String[] coords;
        coords = input.split(" * ",2);
        start = coords[0].split(",",2);
        end = coords[1].split(",",2);
        action.setStart(new Coordinate(Integer.parseInt(start[0]), Integer.parseInt(start[1])));
        action.setEnd(new Coordinate(Integer.parseInt(end[0]), Integer.parseInt(end[1])));
        return action;
    }

}