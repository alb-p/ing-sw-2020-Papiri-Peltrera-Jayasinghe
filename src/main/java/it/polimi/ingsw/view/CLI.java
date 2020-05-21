package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.ANSIColor;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;
import java.io.*;
import java.util.Scanner;

public class CLI extends RemoteView implements Runnable {

    private final Scanner scanner;
    private final PrintStream printer;
    private String playerChoice;
    private VirtualBoard board;
    private Client connection;
    private String nickname;
    private Color color;
    private Boolean nickValidate = false;
    private Boolean colorValidate = false;
    private boolean godlySelected = false;
    private ModelView modelView;
    private final Object monitor;


    public CLI(Client connection) {
        super(connection);
        this.connection = connection;
        this.monitor = getMonitor();
        System.out.println(this.connection);
        this.scanner = new Scanner(System.in);
        this.printer = System.out;
        this.board = new VirtualBoard();
        this.modelView = getModelView();
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


    public NicknameMessage askNickPlayer() {
        NicknameMessage message = new NicknameMessage();
        do {
            printer.println(message.getMessage() + "\n");
            startingBrackets();
            playerChoice = scanner.nextLine();
            message.setNick(playerChoice);
            this.nickname = playerChoice;
        } while (!modelView.checkNickname(nickname));
        return message;
    }


    public ColorMessage askColor() {
        ColorMessage message = new ColorMessage(getPlayerId());
        do {

            printAvailableColors();
            playerChoice = scanner.nextLine();
            System.out.println(playerChoice);
            for (Color c : modelView.getColors()) {
                if (playerChoice.equalsIgnoreCase(c.getName()))
                    message.setColor(c);
            }
            printBreakers();
        } while (!modelView.isInColor(message.getColor()));
        this.color = message.getColor();
        return message;
    }

    public void printAvailableColors() {
        printBreakers();
        printer.println("\n");
        printer.println("\nHey " + nickname + "! Which colour you want to choose? Available: ");
        for (Color c : modelView.getColors()) {
            printer.print(c.getName() + "\t");
        }
        printer.println();
        for (ModelView.PlayerView p : modelView.getPlayers()) {
            if (p.getColor() != null)
                printer.println("" + p.getNickname() + " chose " + p.getColor().getName());
        }
        printer.println();
        startingBrackets();
    }

    public InitialCardsMessage askGodList() {

        String s;
        String isNumber;
        InitialCardsMessage message = new InitialCardsMessage();

        //info 3
        do {
            printGodsList(message);
            startingBrackets();
            s = scanner.nextLine();
            isNumber = s.replaceAll("[^0-9]", "");
            if (!isNumber.equals("")) {
                int indexGod = Integer.parseInt(isNumber);
                if (indexGod < modelView.getGods().size()) {
                    if (s.contains("info") || s.contains("man")) {
                        printer.println(modelView.getGods().get(indexGod)[0]);
                        printer.println(modelView.getGods().get(indexGod)[1]);
                        printBreakers();
                    } else {
                        message.addToSelectedList(modelView.getGods().get(indexGod)[0]);
                        modelView.getGods().remove(indexGod);
                    }
                }
            }
        } while (message.getSelectedList().size() != modelView.getPlayers().size());


        message.addToSelectedList(s.toUpperCase());

        printBreakers();
        return message;
    }

    public void printGodsList(InitialCardsMessage message) {
        printer.println("Select " + (modelView.getPlayers().size() - message.getSelectedList().size()) + " gods from the list below: \ninfo or man + #god");
        for (int i = 0; i < modelView.getGods().size(); i++) {
            if (i % 2 == 0) {
                printer.println();
            }
            printer.printf("%d- %-15s", i, modelView.getGods().get(i)[0]);
        }
        printer.println("\n");

    }


    public void showColor(ColorSelectedMessage inputObject) {
        if (inputObject.getMessage() != null) printer.println(inputObject.getMessage());
    }


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
        inputObject.setGod(input);
        printBreakers();
        return inputObject;
    }


    public ActionMessage askAction(ActionMessage message) {
        printBoard();
        printer.println(nickname + " make " + message.getAction().getActionName() + " x,y in z,w");
        startingBrackets();

        playerChoice = scanner.nextLine();
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
    protected synchronized void gameReady() {
        notify();

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
            if (!val.equals("")) i = Integer.parseInt(val);
        } while (!(i == 2 || i == 3));
        SetupMessage message;
        message = new SetupMessage();
        message.setField(i);
        return message;
    }

    @Override
    public void run() {
        try {
            startingGame();
            nickChoice();
            colorChoice();
            synchronized (this) {
                while (!godlySelected) {
                    this.wait();
                }
            }
            //stampa info riepilogative
            if (modelView.getGodlyId() == getPlayerId()) {
                getConnection().sendEvent(new PropertyChangeEvent(this, "notify1ofNGod", false, askGodList()));
            } else {
                printer.println(modelView.getPlayer(modelView.getGodlyId()).getNickname() + "is choosing Gods");
            }
            while (modelView.getActualPlayerId() != getPlayerId()) {

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private synchronized void startingGame() throws InterruptedException {
        welcomeMessage();
        this.wait();
    }

    private synchronized void colorChoice() {
        while (!colorValidate) {
            ColorMessage message = askColor();
            waitingValidation(new PropertyChangeEvent(this, "notifyColor", false, message));
        }
    }

    private synchronized void nickChoice() {
        while (!nickValidate) {
            NicknameMessage message = askNickPlayer();
            waitingValidation(new PropertyChangeEvent(this, "notifyNickname", false, message));
        }
    }


    public void waitingValidation(PropertyChangeEvent evt) {
        getConnection().sendEvent(evt);
        try {
            this.wait();
        } catch (InterruptedException | IllegalMonitorStateException e) {
            System.out.println("ILLEGAL MONITOR");
            e.printStackTrace();
        }
    }


    @Override
    protected synchronized void nicknameReceived(NicknameMessage newValue) {
        modelView.addPlayer(newValue.getId(), newValue.getNick());
        if (!newValue.getNick().equals(nickname)) {
            //printer.println("\n" + newValue.getNick() + " joined the game!");
            //if (!nickValidate) startingBrackets();
            return;
        }
        if (newValue.getId() == this.getPlayerId()) {
            nickValidate = true;
        }
        this.notify();
    }

    @Override
    protected synchronized void colorReceived(ColorMessage newValue) {
        modelView.setColor(newValue.getId(), newValue.getColor());
        if (!newValue.getColor().equals(color)) {

            if (!colorValidate && nickValidate) {
                printAvailableColors();
            }
            return;
        }
        if (newValue.getId() == this.getPlayerId()) {
            colorValidate = true;
        }
        this.notify();
    }

    @Override
    protected void godlyReceived() {
        godlySelected = true;
    }


}