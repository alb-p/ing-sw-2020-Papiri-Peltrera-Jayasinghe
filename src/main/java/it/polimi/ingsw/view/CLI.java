package it.polimi.ingsw.view;

import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.utils.messages.ActionMessage;
import it.polimi.ingsw.utils.messages.ColorMessage;
import it.polimi.ingsw.utils.messages.GodMessage;
import it.polimi.ingsw.utils.messages.NicknameMessage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class CLI extends RemoteView implements Runnable {

    private Scanner scanner;
    private PrintWriter socketOut;
    private PrintStream printer;
    private Console console;
    private String playerChoice;
    private IslandBoard board;
    private String nickname;



    public CLI(Socket socket) throws IOException {
        this.scanner = new Scanner(System.in);
        this.socketOut = new PrintWriter(socket.getOutputStream());
        this.printer= System.out;
    }


    public NicknameMessage askNickPlayer(NicknameMessage message){
        printer.println(message.getMessage()+ "\n");
        playerChoice = console.readLine();
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
        for(String s: message.getColors()){
            printer.printf(s+" ");
        }
        startingBrackets();
        playerChoice = scanner.nextLine();
        message.setColor(playerChoice);
        return message;
    }

    @Override
    public GodMessage askGod(GodMessage inputObject) {
        //TODO da finire dopo commit di sandro
        return null;
    }

    @Override
    public ActionMessage askAction(ActionMessage message) {
        printer.println(nickname+ " make ! " + message.getAction() + "x,y in z,w" );
        startingBrackets();
        playerChoice = scanner.nextLine();


        return null;
    }



    public void startingBrackets(){
        printer.printf(">>>");
    }


}