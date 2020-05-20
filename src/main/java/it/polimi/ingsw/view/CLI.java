package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.ANSIColor;
import it.polimi.ingsw.utils.ActionsEnum;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class CLI extends RemoteView implements Runnable {

    private final Scanner scanner;
    private Client connection;
    private final PrintStream printer;
    private String playerChoice;
    private VirtualBoard board;
    private String nickname;


    private String filename;                                            //DA TOGLIERE IN FUTURO

    private void setOnFile(String playerChoice) {                       //
        try {                                                           //
            FileWriter fw = new FileWriter(filename, true);      //
            BufferedWriter bw = new BufferedWriter(fw);                 //
            bw.write(playerChoice);                                     //
            bw.newLine();                                               //
            bw.close();                                                 //
        } catch (IOException e) {                                       //
            e.printStackTrace();                                        //
        }                                                               //
    }                                                                   //

    public CLI(Client connection) {
        super(connection);
        this.scanner = new Scanner(System.in);
        this.printer = System.out;
        this.board = new VirtualBoard();

        Random random = new Random();                                //DA TOGLIERE IN FUTURO
        filename = "partita" + random.nextInt(999) + ".txt";        //
        File myObj = new File(filename);                            //
        try {                                                       //
            myObj.createNewFile();                                  //
        } catch (IOException e) {                                   //
            e.printStackTrace();                                    //
        }
    }


    public NicknameMessage askNickPlayer(NicknameMessage message) {
        printer.println(message.getMessage() + "\n");
        startingBrackets();
        playerChoice = scanner.nextLine();
        setOnFile(playerChoice);////////////////////////////DA TOGLIERE IN FUTURO
        message.setNick(playerChoice);
        this.nickname = playerChoice;
        return message;
    }


    
    public void welcomeMessage() {
        printer.println("\n" +
                ANSIColor.WHITE + "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n" +
                ANSIColor.BLUE + "░░░░░╔╦═╦╗░░░░░░░░░░░░░╔╗░░░░░░░░░░░░\n" +
                "░░░░░║║║║╠═╦╗╔═╦═╦══╦═╗║╚╦═╗░░░░░░░░░\n" +
                "░░░░░║║║║║╩╣╚╣═╣╬║║║║╩╣║╔╣╬║░░░░░░░░░\n" +
                "░░░░░╚═╩═╩═╩═╩═╩═╩╩╩╩═╝╚═╩═╝░░░░░░░░░\n" +
                "░░░░░╔══╗░░░░░╔╗░░░░╔╗░░╔╗░░░░░░░░░░░\n" +
                "░░░░░║══╬═╗╔═╦╣╚╦═╦╦╬╬═╦╬╣░░░░░░░░░░░\n" +
                "░░░░░╠══║╬╚╣║║║╔╣╬║╔╣║║║║║░░░░░░░░░░░\n" +
                "░░░░░╚══╩══╩╩═╩═╩═╩╝╚╩╩═╩╝░░░░░░░░░░░\n" +
                ANSIColor.WHITE + "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n");
    }

    
    public ColorMessage askColor(ColorMessage message) {
        printer.println("Hey " + nickname + "! Which colour you want to choose among those available?");
        for (Color c : message.getColors()) {
            printer.print(c.getName() + " ");
        }
        startingBrackets();
        playerChoice = scanner.nextLine();
        setOnFile(playerChoice);/////////////////////DA TOGLIERE IN FUTURO
        for (Color c : Color.values()) {
            if (playerChoice.equalsIgnoreCase(c.getName()))
                message.setColor(c);
        }
        printBreakers();
        return message;
    }

    
    public InitialCardsMessage askGodList(InitialCardsMessage message) {
        printer.println(message.getMessage());
        for (int i = 0; i < message.getCompleteList().size(); i++) {
            if (i % 2 == 0) {
                printer.println();
            }
            printer.printf("%-15s", message.getCompleteList().get(i));
        }
        printer.println("\n");
        String s;
        for (int i = 0; i < message.getDim(); i++) {
            startingBrackets();
            s = scanner.nextLine();
            setOnFile(s);///////////////////////////////////DA TOGLIERE IN FUTURO
            message.addToSelectedList(s.toUpperCase());
        }
        printBreakers();
        return message;
    }

    
    public void showColor(ColorSelectedMessage inputObject) {
        if (inputObject.getMessage() != null) printer.println(inputObject.getMessage());
    }

    
    public FirstPlayerMessage firstPlayer(FirstPlayerMessage message) {
        printer.println(message.getMessage());
        startingBrackets();
        String name = scanner.nextLine();
        setOnFile(playerChoice);////////////////////////////////DA TOGLIERE IN FUTURO
        for (String s : message.getNames()) {
            if (s.equalsIgnoreCase(name))
                message.setChosenName(name);
        }
        printBreakers();
        return message;
    }

    
    public WorkerMessage setWorker(WorkerMessage message) {
        printBoard();
        int row;
        row = 9;
        int col;
        col = 9;
        printer.println(message.getMessage());

        String inputToParse;
        do {
            startingBrackets();
            printer.print("row: ");
            inputToParse = "";
            row = Integer.parseInt(inputToParse.concat("0" + scanner.nextLine().replaceAll("[^0-5]", "9")));
        } while (row > 5);

        do {
            startingBrackets();
            printer.print("col: ");
            inputToParse = "";
            col = Integer.parseInt(inputToParse.concat("0" + scanner.nextLine().replaceAll("[^0-5]", "9")));
        } while (col > 5);

        setOnFile(String.valueOf(row));////////////////////////////DA TOGLIERE IN FUTURO
        setOnFile(String.valueOf(col));///////////////////////////
        Coordinate c = new Coordinate(row, col);
        message.setCoordinate(c);
        return message;
    }

    
    public void gameIsReady(StartGameMessage inputObject) {
        printer.println(inputObject.getMessage());
    }

    
    public void updateVBoard(VirtualSlotMessage inputObject) {
        board.setSlot(inputObject.getVirtualSlot());
    }

    
    public void winnerMess(WinnerMessage inputObject) {
        printBreakers();
        printBreakers();
        printer.println("\n\n");
        printer.println(inputObject.getMessage());
        printer.println("\n\n");
        printBreakers();
        printBreakers();

    }

    
    public ChoiceMessage askChoice(ChoiceMessage message) {
        String input;
        String onlyNumbers;
        int parsed = 0;
        printBoard();
        printer.println("Hey " + nickname + "! Possibile actions: ");
        do {
            for (int i = 0; i < message.getChoices().size(); i++) {
                printer.println(i + ":: " + message.getChoices().get(i));
            }
            startingBrackets();
            input = scanner.nextLine();
            setOnFile(input);//////////////////////////DA TOGLIERE IN FUTURO
            onlyNumbers = input.replaceAll("[^0-5]", "");
            if (!onlyNumbers.equals("")) {
                parsed = Integer.parseInt(onlyNumbers);
            }
        } while (!(parsed < message.getChoices().size() && parsed >= 0));
        message.setMessage(message.getChoices().get(parsed));
        printBreakers();
        printer.println();
        return message;
    }

    
    public void genericMess(GenericMessage inputObject) {
        printBreakers();
        printBreakers();
        printer.println("\n\n" + ANSIColor.BOLD + ANSIColor.RED + inputObject.getMessage() + ANSIColor.RESET + "\n\n");
        printBreakers();
        printBreakers();
    }



    
    public void waitingMess(WaitingMessage inputObject) {
        printBoard();
        printer.println(inputObject.getMessage());
    }


    
    public GodMessage askGod(GodMessage inputObject) {
        printer.println(inputObject.getMessage());
        startingBrackets();
        String input = scanner.nextLine().toUpperCase();
        setOnFile(input);////////////////////////////////DA TOGLIERE IN FUTURO
        inputObject.setGod(input);
        printBreakers();
        return inputObject;
    }

    
    public ActionMessage askAction(ActionMessage message) {
        printBoard();
        printer.println(nickname + " make " + message.getAction().getActionName() + " x,y in z,w");
        startingBrackets();

        playerChoice = scanner.nextLine();
        setOnFile(playerChoice);//////////////////////////////////DA TOGLIERE IN FUTURO
        message.setAction(parseAction(playerChoice, message.getAction()));

        return message;
    }


    public void startingBrackets() {
        printer.print(">>>");
    }

    public Action parseAction(String input, Action action) {
        String[] coords;
        String noSpaces;
        String noWrongChars;
        //0,0 in 0,1 -> 0,0in0,1 -> 0 0,0 1
        noSpaces = input.replace(" ", "");
        noWrongChars = noSpaces.replaceAll("[^0-5]", "");
        coords = noWrongChars.split("");
        if (coords.length != 4) {
            action.setStart(null);
            action.setEnd(null);
        } else {
            action.setStart(new Coordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
            action.setEnd(new Coordinate(Integer.parseInt(coords[2]), Integer.parseInt(coords[3])));
        }
        return action;
    }

    public void printBoard() {
        printer.println(this.board);
        printBreakers();
    }

    public void printBreakers() {
        printer.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
    }

    @Override
    protected void startMainThread() {
        System.out.println("START CLI");
        this.run();
    }

    @Override
    protected SetupMessage chooseNumberOfPlayers() {
        printer.println("How Many Players for the game? 2/3");
        String input;
        String val;
        int i;
        i = 0;
        do {
            startingBrackets();
            input = scanner.nextLine();
            val = input.replaceAll("[^2-3]", "");
            if(!val.equals(""))i=Integer.parseInt(val);
        } while (!(i == 2 || i == 3));
        setOnFile(input);///////////////////////////////DA TOGLIERE IN FUTURO
        SetupMessage message;
        message = new SetupMessage();
        message.setField(i);
        return message;
    }

    @Override
    public void run() {
        welcomeMessage();

        while (true){
            NicknameMessage message =askNickPlayer(new NicknameMessage());
            connection.sendEvent(new PropertyChangeEvent(this, "notifyNick", null, message));
        } }
}