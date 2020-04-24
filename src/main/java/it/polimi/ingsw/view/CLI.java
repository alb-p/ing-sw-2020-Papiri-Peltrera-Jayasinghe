package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.IslandBoard;
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
    public GodMessage askGod(GodMessage inputObject) {
        //TODO da finire dopo commit di sandro
        printer.println(inputObject.getMessage());
        startingBrackets();
        inputObject.setGod(scanner.nextLine().toUpperCase());
        return inputObject;
    }

    @Override
    public ActionMessage askAction(ActionMessage message) {
        printer.println(nickname+ " make ! " + message.getAction() + "x,y in z,w" );
        startingBrackets();
        playerChoice = scanner.nextLine();


        return null;
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


}