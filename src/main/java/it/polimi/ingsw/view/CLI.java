package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.ANSIColor;
import it.polimi.ingsw.utils.ActionsEnum;
import it.polimi.ingsw.utils.messages.*;

import java.io.*;
import java.util.Scanner;

public class CLI extends RemoteView implements Runnable {

    private final Scanner scanner;
    private final PrintStream printer;
    private String playerChoice;
    private VirtualBoard board;
    private String nickname;


    public CLI() {
        this.scanner = new Scanner(System.in);
        this.printer = System.out;
        this.board = new VirtualBoard();
    }


    public NicknameMessage askNickPlayer(NicknameMessage message) {
        printer.println(message.getMessage() + "\n");
        startingBrackets();
        playerChoice = scanner.nextLine();
        message.setNick(playerChoice);
        this.nickname = playerChoice;
        return message;
    }

    @Override
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

    @Override
    public ColorMessage askColor(ColorMessage message) {
        printer.println("Hey " + nickname + "! Which colour you want to choose among those available?");
        for (Color c : message.getColors()) {
            printer.print(c.getName() + " ");
        }
        startingBrackets();
        playerChoice = scanner.nextLine();
        for (Color c : Color.values()) {
            if (playerChoice.equalsIgnoreCase(c.getName()))
                message.setColor(c);
        }
        printBreakers();
        return message;
    }

    @Override
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
            message.addToSelectedList(s.toUpperCase());
        }
        printBreakers();
        return message;
    }

    @Override
    public void showColor(ColorSelectedMessage inputObject) {
        if (inputObject.getMessage() != null) printer.println(inputObject.getMessage());
    }

    @Override
    public FirstPlayerMessage firstPlayer(FirstPlayerMessage message) {
        printer.println(message.getMessage());
        startingBrackets();
        String name = scanner.nextLine();
        for (String s : message.getNames()) {
            if (s.equalsIgnoreCase(name))
                message.setChosenName(name);
        }
        printBreakers();
        return message;
    }

    @Override
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
        }while(row>5);

        do {
            startingBrackets();
            printer.print("col: ");
            inputToParse = "";
            col = Integer.parseInt(inputToParse.concat("0" + scanner.nextLine().replaceAll("[^0-5]", "9")));
        }while(col>5);
        Coordinate c = new Coordinate(row, col);
        message.setCoordinate(c);
        return message;
    }

    @Override
    public void gameIsReady(StartGameMessage inputObject) {
        printer.println(inputObject.getMessage());
    }

    @Override
    public void updateVBoard(VirtualSlotMessage inputObject) {
        board.setSlot(inputObject.getVirtualSlot());
    }

    @Override
    public void winnerMess(WinnerMessage inputObject) {
        printBreakers();
        printBreakers();
        printer.println("\n\n");
        printer.println(inputObject.getMessage());
        printer.println("\n\n");
        printBreakers();
        printBreakers();

    }

    @Override
    public ChoiceMessage askChoice(ChoiceMessage message) {
        String input;
        String onlyNumbers;
        int parsed = 0;
        printBoard();
        printer.println("Hey "+nickname+"! Possibile actions: ");
        do {
            for (int i = 0; i < message.getChoices().size(); i++) {
                printer.println(i + ":: " + message.getChoices().get(i));
            }
            startingBrackets();
            input = scanner.nextLine();
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

    @Override
    public void genericMess(GenericMessage inputObject) {
        printBreakers();
        printBreakers();
        printer.println("\n\n"+ANSIColor.BOLD+ANSIColor.RED+inputObject.getMessage()+ANSIColor.RESET+"\n\n");
        printBreakers();
        printBreakers();
    }

    @Override
    public void waitingMess(WaitingMessage inputObject) {
        printBoard();
        printer.println(inputObject.getMessage());
    }


    @Override
    public GodMessage askGod(GodMessage inputObject) {
        printer.println(inputObject.getMessage());
        startingBrackets();
        inputObject.setGod(scanner.nextLine().toUpperCase());
        printBreakers();
        return inputObject;
    }

    @Override
    public ActionMessage askAction(ActionMessage message) {
        printBoard();
        printer.println(nickname + " make " + message.getAction().getActionName() + " x,y in z,w");
        startingBrackets();

        playerChoice = scanner.nextLine();
        message.setAction(parseAction(playerChoice, message.getAction()));

        return message;
    }

    public SetupMessage askNumOfPlayers(SetupMessage message) {
        printer.println(message.getMessage());
        startingBrackets();
        int i = Integer.parseInt(scanner.nextLine());
        message.setField(i);
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
}