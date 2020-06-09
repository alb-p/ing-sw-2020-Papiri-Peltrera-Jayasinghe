package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.ClientMain;
import it.polimi.ingsw.network.SocketServerConnection;
import it.polimi.ingsw.utils.ANSIColor;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI extends RemoteView implements Runnable {

    private final Scanner scanner;
    private final PrintStream printer;
    private String playerChoice;
    private SocketServerConnection connection;
    private String nickname;
    private Color color;
    private boolean nickValidate = false;
    private boolean colorValidate = false;
    private boolean godlySelected = false;
    private boolean winnerDetected = false;
    private boolean endTurn;
    private ModelView modelView;
    private static final String arrangeList = "%d- %-20s";

    public CLI(SocketServerConnection connection) {
        super(connection);
        this.connection = connection;
        this.scanner = ClientMain.scanner;
        this.printer = System.out;
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

    public NicknameMessage askNickPlayer() {
        NicknameMessage message = new NicknameMessage();
        do {
            printer.println(message.getMessage() + "\n");
            startingBrackets();
            playerChoice = scanner.nextLine();
            message.setNickname(playerChoice);
            this.nickname = playerChoice;
        } while (!modelView.checkNickname(nickname));
        return message;
    }


    public ColorMessage askColor() {
        ColorMessage message = new ColorMessage(getPlayerId());
        String isNumber;
        int indexColor = 9;
        do {
            printAvailableColors();
            playerChoice = scanner.nextLine();
            isNumber = playerChoice.replaceAll("[^1-9]", "");
            if (!isNumber.equals("")) {
                indexColor = Integer.parseInt(isNumber);
                indexColor -= 1;
                if (indexColor < modelView.getColors().size()) {
                    message.setColor(modelView.getColors().get(indexColor));
                }
            }
            printBreakers();
        } while (!modelView.isInColor(message.getColor()) || (indexColor >= modelView.getColors().size()));
        this.color = message.getColor();
        return message;
    }

    public void printAvailableColors() {
        printBreakers();
        printer.println("\n");
        printer.println("\nHey " + nickname + "! Which colour you want to choose? Available: ");

        for (int i = 0; i < modelView.getColors().size(); i++) {
            printer.printf(arrangeList, i + 1, modelView.getColors().get(i).getName());
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
                if (indexGod > 0) {
                    indexGod -= 1;
                    if (indexGod < modelView.getGods().size()) {
                        if (s.contains("info") || s.contains("man")) {
                            printer.println(modelView.getGods().get(indexGod)[0]);
                            printer.println(modelView.getGods().get(indexGod)[1]);
                            printBreakers();
                        } else {
                            message.addToSelectedList(modelView.getGods().get(indexGod)[0]);
                            modelView.getChosenGods().add(modelView.getGods().get(indexGod));
                            modelView.getGods().remove(indexGod);
                        }
                    }
                }
            }
        } while (message.getSelectedList().size() != modelView.getPlayers().size());
        modelView.getGods().addAll(modelView.getChosenGods());
        modelView.getChosenGods().clear();

        printBreakers();
        return message;
    }

    public void printGodsList(InitialCardsMessage message) {
        printer.println("Select " + (modelView.getPlayers().size() - message.getSelectedList().size()) +
                " gods from the list below: \ninfo or man + #god");
        for (int i = 0; i < modelView.getGods().size(); i++) {
            if (i % 2 == 0) {
                printer.println();
            }
            printer.printf(arrangeList, i + 1, modelView.getGods().get(i)[0]);
        }
        printer.println("\n");

    }

    public GodMessage askGod() {
        GodMessage message = new GodMessage();
        message.setId(getPlayerId());
        String s;
        String isNumber;
        //TODO deidere convenzione per inizializzazione dei controlli o 9 o -2
        int indexGod = 9;
        printBreakers();
        if (modelView.getChosenGods().size() == 1) {
            message.setGod(modelView.getChosenGods().get(0)[0]);
            modelView.getPlayer(getPlayerId()).setGod(modelView.getChosenGods().get(0));
            printer.println("You automatically got " + modelView.getChosenGods().get(0)[0] +
                    "\n" + modelView.getChosenGods().get(0)[1] + "\n");
        } else {
            do {
                printer.println("\nSelect God among the chosen gods:\n");
                for (int i = 0; i < modelView.getChosenGods().size(); i++) {
                    printer.printf(arrangeList, i + 1, modelView.getChosenGods().get(i)[0]);
                }
                printer.println();
                startingBrackets();
                s = scanner.nextLine();
                isNumber = s.replaceAll("[^1-9]", "");
                if (!isNumber.equals("")) {
                    indexGod = Integer.parseInt(isNumber);
                    indexGod -= 1;
                    if (indexGod < modelView.getChosenGods().size()) {
                        if (s.contains("info") || s.contains("man")) {
                            printer.println(modelView.getChosenGods().get(indexGod)[0]);
                            printer.println(modelView.getChosenGods().get(indexGod)[1]);
                            indexGod = 9;
                            printBreakers();
                        } else {
                            message.setGod(modelView.getChosenGods().get(indexGod)[0]);
                        }
                    }
                }
            } while (indexGod >= modelView.getChosenGods().size() );
        }
        printBreakers();
        return message;
    }


    public synchronized void firstPlayer() throws InterruptedException {
        int indexNickname = -2;

        if (getPlayerId() == modelView.getGodlyId()) {
            NicknameMessage message = new NicknameMessage();
            String s;
            String isNumber;
            do {
                printer.println("Chose first player: ");
                for (int i = 0; i < modelView.getPlayers().size(); i++) {
                    printer.printf(arrangeList, i + 1, modelView.getPlayer(i).getNickname());
                }
                printer.println();
                startingBrackets();
                s = scanner.nextLine();
                isNumber = s.replaceAll("[^0-9]", "");
                if (!isNumber.equals("")) {
                    indexNickname = Integer.parseInt(isNumber);
                    indexNickname -= 1;
                    if (indexNickname < modelView.getPlayers().size()) {
                        if (s.contains("info") || s.contains("man")) {
                            printer.println(modelView.getGods().get(indexNickname)[0]);
                            printer.println(modelView.getGods().get(indexNickname)[1]);
                            indexNickname = 9;
                            printBreakers();
                        } else {
                            break;
                        }
                    }
                }
            } while (indexNickname >= modelView.getPlayers().size() || indexNickname < 0);

            message.setNickname(modelView.getPlayer(indexNickname).getNickname());
            connection.sendEvent(new PropertyChangeEvent(this,
                    "firstPlayerSelected", null, message));
        }
        wait();
    }

    public synchronized void setupWorkers() throws InterruptedException {
        while (getPlayerId() != modelView.getActualPlayerId()) {
            wait();
        }
        WorkerMessage message;
        Coordinate c;
        for (int i = 0; i < 2; i++) {
            message = new WorkerMessage(getPlayerId(), i);
            //printBoard();
            do {
                int row;
                row = 9;
                int col;
                col = 9;
                String[] inputArray;
                String inputParsed;
                printer.println("Place your " + (i + 1) + "° worker:");
                String inputToParse;
                do {
                    printer.println("Insert row,col : ");
                    startingBrackets();
                    inputToParse = scanner.nextLine();
                    inputParsed = inputToParse.replaceAll("[^1-5]", "");
                    inputArray = inputParsed.split("");
                    if (inputArray.length == 2) {
                        row = Integer.parseInt(inputArray[0]);
                        col = Integer.parseInt(inputArray[1]);
                    }
                } while (!(row > 0 && row < 6 && col > 0 && col < 6));

                c = new Coordinate(row - 1, col - 1);
                message.setCoordinate(c);
            } while (!modelView.getBoard().getSlot(c).isFree());

            connection.sendEvent(new PropertyChangeEvent(this, "notifyWorker", null, message));
            wait();
        }
    }

    private synchronized void play() throws InterruptedException {
        while (!winnerDetected && getPlayerId() != modelView.getDeletedPlayerId()) {
            modelView.getActionsAvailable().clear();
            while (getPlayerId() == modelView.getActualPlayerId() && !endTurn) {
                connection.sendEvent(new PropertyChangeEvent(this, "actionsRequest",
                        null, new GenericMessage()));
                wait();
                if (!modelView.getActionsAvailable().isEmpty() && getPlayerId() == modelView.getActualPlayerId() && !winnerDetected) {
                    ArrayList<String> choices = modelView.getActionChoices();
                    Action action = null;
                    do {
                        int choiceIndex = 0;
                        ArrayList<Coordinate> coords;
                        if (choices.size() > 1) {
                            String inputChoice;
                            printer.println(modelView.getPlayer(getPlayerId()).getColor().colorizedText(nickname)
                                    +", which action do you want to perform?");

                            int i;
                            for (i = 0; i < choices.size(); i++) {
                                printer.printf(arrangeList, i + 1, choices.get(i).toLowerCase());
                            }

                            do {
                                printer.println();
                                startingBrackets();
                                inputChoice = scanner.nextLine();
                                String numbers = inputChoice.replaceAll("[^0-9]", "");
                                if (!numbers.equals("")) {
                                    choiceIndex = Integer.parseInt(numbers) - 1;
                                }
                            } while (choiceIndex < 0 || choiceIndex >= choices.size());
                        }
                        printer.println();
                        if (choices.get(choiceIndex).equals("end turn") && modelView.isOptional()) {
                            endTurn = true;
                        } else {
                            printer.println(modelView.getPlayer(getPlayerId()).getColor().colorizedText(nickname)
                                    +", perform your " + choices.get(choiceIndex).toLowerCase() + ": (x,y in r,s)");
                            startingBrackets();
                            coords = parseCoordinateAction(scanner.nextLine());
                            if (coords.size() == 2) {
                                action = modelView.searchAction(choices.get(choiceIndex), coords.get(0), coords.get(1));
                            }
                        }
                    } while (action == null && !endTurn);

                    if (endTurn) {
                        modelView.getActionsAvailable().clear();
                        connection.sendEvent(new PropertyChangeEvent(this,
                                "endTurn", null, new GenericMessage()));
                    } else {
                        ActionMessage mess = new ActionMessage();
                        mess.setAction(action);
                        modelView.getActionsAvailable().clear();
                        connection.sendEvent(new PropertyChangeEvent(this,
                                "notifyAction", null, mess));
                    }
                    printBreakers();

                }
                if (winnerDetected) return;
            }
            wait();
        }
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

    public void genericMess(GenericMessage inputObject) {
        printBreakers();
        printBreakers();
        printer.println("\n\n" + ANSIColor.BOLD + ANSIColor.RED + inputObject.getMessage() + ANSIColor.RESET + "\n\n");
        printBreakers();
        printBreakers();
    }


    public ArrayList<Coordinate> parseCoordinateAction(String input) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        String[] coords;
        int[] nums = new int[4];
        String noSpaces;
        String noWrongChars;
        //0,0 in 0,1 -> 0,0in0,1 -> 0 0,0 1
        noSpaces = input.replace(" ", "");
        noWrongChars = noSpaces.replaceAll("[^0-5]", "");
        coords = noWrongChars.split("");
        if (coords.length == 4) {
            for (int i = 0; i < 4; i++) {
                nums[i] = Integer.parseInt(coords[i]);
                nums[i] -= 1;
            }
            coordinates.add(new Coordinate(nums[0], nums[1]));
            coordinates.add(new Coordinate(nums[2], nums[3]));
        } else {
            coordinates.add(new Coordinate(-1, -1));
            coordinates.add(new Coordinate(-1, -1));
        }
        return coordinates;
    }

    public void printBoard() {
        if(!winnerDetected)printer.println(this.modelView.getBoard());
        printBreakers();
    }

    public void startingBrackets() {
        printer.print(">>>");
    }

    public void printBreakers() {
        printer.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
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
            }
            synchronized (this) {
                wait();
            }

            godChoice();
            allGodSelected();
            firstPlayer();
            setupWorkers();
            allWorkerPlaced();
            play();
            System.out.println("ENDGAME");


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private synchronized void allWorkerPlaced() throws InterruptedException {
        while (modelView.getFirstPlayerId() != modelView.getActualPlayerId()) {
            wait();
        }
    }

    private synchronized void allGodSelected() throws InterruptedException {
        while (!modelView.getChosenGods().isEmpty()) {
            wait();
        }
    }

    private synchronized void godChoice() throws InterruptedException {
        while (modelView.getActualPlayerId() != getPlayerId()) {
            wait();
        }
        GodMessage message = askGod();
        connection.sendEvent(new PropertyChangeEvent(this, "notifyGod", null, message));
    }

    private synchronized void startingGame() throws InterruptedException {
        welcomeMessage();
        int choiceMenu;
        do{
            printer.println("\t0- play\t1- help");
            String input = scanner.nextLine();
            String nums =input.replaceAll("[^0-9]", "");
            if(!nums.equalsIgnoreCase("")){
                choiceMenu = Integer.parseInt(nums);
            }else choiceMenu=-1;
            if (choiceMenu == 1){
                printer.println("HELPFAKE");
            }
        } while (choiceMenu!=0);
        connection.start();
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
    protected synchronized void gameReady() {
        notify();
    }

    @Override
    protected synchronized void nicknameReceived(NicknameMessage message) {
        super.nicknameReceived(message);
        if (!message.getNickname().equals(nickname)) {
            //printer.println("\n" + message.getNick() + " joined the game!");
            //if (!nickValidate) startingBrackets();
            return;
        }
        if (message.getId() == this.getPlayerId()) {
            nickValidate = true;
        }
        this.notify();
    }

    @Override
    protected synchronized void colorReceived(ColorMessage message) {
        super.colorReceived(message);
        if (!message.getColor().equals(color)) {

            if (!colorValidate && nickValidate) {
                printAvailableColors();
            }
            return;
        }
        if (message.getId() == this.getPlayerId()) {
            colorValidate = true;
        }
        this.notify();
    }

    @Override
    protected synchronized void setGodly(GodlyMessage message) {
        super.setGodly(message);
        if (getPlayerId() != modelView.getGodlyId()) {
            printer.println("\n" + modelView.getPlayer(modelView.getGodlyId()).getNickname() + " is choosing Gods\n");
            printBreakers();
        }
        godlySelected = true;
        notify();
    }


    @Override
    protected synchronized void chosenGods(InitialCardsMessage message) {
        super.chosenGods(message);
        if (modelView.getChosenGods().size() == modelView.getPlayers().size()) {
            printer.println("\tCHOSEN GODS:");
            for (String[] g : modelView.getChosenGods()) {
                printer.println("\n" + g[0] + "\n" + g[1]);
            }
            this.notify();
        }
    }

    @Override
    protected synchronized void assignedGod(GodMessage message) {
        super.assignedGod(message);
        if (modelView.getChosenGods().isEmpty()) {
            for (ModelView.PlayerView p : modelView.getPlayers()) {
                printer.println(p.getNickname() + " chose " + p.getGod()[0]);
            }
        }
        this.notify();
    }

    @Override
    protected synchronized void setFirstPlayer(NicknameMessage message) {
        super.setFirstPlayer(message);
        printBreakers();
        printer.println(modelView.getPlayer(modelView.getGodlyId()).getNickname() +
                " chose " + message.getNickname() + " as first player!");
        printBoard();
        notify();
    }

    @Override
    protected synchronized void actionsAvailable(ActionMessage message) {
        super.actionsAvailable(message);
        printBoard();
        notify();
    }

    @Override
    protected synchronized void setWorker(WorkerMessage message) {
        super.setWorker(message);
        printBoard();
        notify();
    }

    @Override
    protected synchronized void endTurn(NicknameMessage message) {
        super.endTurn(message);
        endTurn = false;
        notify();
    }

    @Override
    protected synchronized void winnerDetected(WinnerMessage message) {
        winnerDetected = true;
        winnerMess(message);
        notify();
    }

    @Override
    protected synchronized void playerHasLost(GenericMessage message) {
        super.playerHasLost(message);
        printer.println("\n" + modelView.getPlayer(message.getId()).getColor().colorizedText(modelView.getPlayer(message.getId()).getNickname()) + " has lost for no available moves!\n");
        printBreakers();
        notify();
    }

}
