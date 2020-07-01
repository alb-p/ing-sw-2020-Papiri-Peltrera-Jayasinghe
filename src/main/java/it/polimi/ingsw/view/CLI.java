package it.polimi.ingsw.view;

import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.network.client.ClientMain;
import it.polimi.ingsw.network.client.SocketServerConnection;
import it.polimi.ingsw.utils.ANSIColor;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command line interface implementation.
 * It includes ASCII extended chars but they will be converted for Windows OS.
 * All the necessary data information for this class during the game is taken from the ModelView
 */
public class CLI extends RemoteView implements Runnable {

    private final Scanner scanner;
    private final SantoriniPrintStream printer;
    private String playerInput;
    private final SocketServerConnection connection;
    private String nickname;
    private Color color;
    private boolean nickValidate = false;
    private boolean colorValidate = false;
    private boolean godlySelected = false;
    private boolean winnerDetected = false;
    private boolean endTurn;
    private final ModelView modelView;
    private static final String arrangeGodList = "%d- %-20s";
    private static final String arrangeColorList = "%d- %-15s";
    private final Logger logger = Logger.getLogger("cli");

    /**
     * Instantiates a new Cli.
     *
     * @param connection
     * @param useAscii   depending on OS
     * @throws UnsupportedEncodingException
     */
    public CLI(SocketServerConnection connection, boolean useAscii) throws UnsupportedEncodingException {
        super(connection);
        this.connection = connection;
        this.scanner = ClientMain.scanner;
        this.printer = new SantoriniPrintStream(System.out, useAscii);
        this.modelView = getModelView();
    }


    /**
     * Welcome message.
     */
    public void welcomeMessage() {
        printer.println("\n" +
                "  _    _      _                            \n" +
                " | |  | |    | |                           \n" +
                " | |  | | ___| | ___ ___  _ __ ___   ___   \n" +
                " | |/\\| |/ _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\  \n" +
                " \\  /\\  /  __/ | (_| (_) | | | | | |  __/  \n" +
                "  \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \n" +
                "                                           \n" +
                "                 _                         \n" +
                "                | |                        \n" +
                "                | |_ ___                   \n" +
                "                | __/ _ \\                  \n" +
                "                | || (_) |                 \n" +
                "                 \\__\\___/                  \n" +
                "                                           \n" +
                ANSIColor.BLUE + " _____             _             _       _ \n" +
                "/  ___|           | |           (_)     (_)\n" +
                "\\ `--.  __ _ _ __ | |_ ___  _ __ _ _ __  _ \n" +
                " `--. \\/ _` | '_ \\| __/ _ \\| '__| | '_ \\| |\n" +
                "/\\__/ / (_| | | | | || (_) | |  | | | | | |\n" +
                "\\____/ \\__,_|_| |_|\\__\\___/|_|  |_|_| |_|_|\n" + ANSIColor.RESET +
                "                                           \n" +
                "                                           "

        );
    }

    /**
     * Ask to the first connected player the number of players for the game
     *
     * @return a SetupMessage within the number of players chosen by first player
     */
    @Override
    protected SetupMessage chooseNumberOfPlayers() {
        printer.println("+----------------------------------------+\n" +
                "|   How Many Players for the game? 2/3   |\n" +
                "+----------------------------------------+\n");
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
        printBreakers();
        SetupMessage message;
        message = new SetupMessage();
        message.setField(i);
        return message;
    }

    /**
     * Ask nickname to player.
     * Nickname have to be unique in each game, not empty and shorter than 20 chars
     *
     * @return the nickname message
     */
    public NicknameMessage askNicknamePlayer() {
        NicknameMessage message = new NicknameMessage();
        String checkSpaces;

        printer.println("\n+-------------------------+\n" +
                "|   Enter your nickname   |\n" +
                "|     (max. 20 chars)     |\n" +
                "+-------------------------+\n");
        do {
            startingBrackets();
            playerInput = scanner.nextLine();
            checkInputExit(playerInput);
            checkSpaces = playerInput.replace(" ", "");
        } while (checkSpaces.equals("") || !modelView.checkNickname(playerInput) || playerInput.length() > 20);
        this.nickname = playerInput;
        message.setNickname(playerInput);
        return message;
    }


    /**
     * Ask color to player
     *
     * @return the color message
     */
    public ColorMessage askColor() {
        ColorMessage message = new ColorMessage(getPlayerId());
        String isNumber;
        int indexColor = 9;
        do {
            printAvailableColors();
            playerInput = scanner.nextLine();
            checkInputExit(playerInput);
            isNumber = playerInput.replaceAll("[^1-9]", "");
            if (!isNumber.equals("")) {
                indexColor = Integer.parseInt(isNumber);
                indexColor -= 1;
                if (indexColor < modelView.getColors().size()) {
                    message.setColor(modelView.getColors().get(indexColor));
                }
            }
            printBreakers();
            printer.println();
        } while (!modelView.isInColor(message.getColor()) || (indexColor >= modelView.getColors().size()));
        this.color = message.getColor();
        return message;
    }

    /**
     * Print available colors.
     */
    public void printAvailableColors() {
        printBreakers();
        printer.println("+-------------------------------------------------+");
        printer.print("|   Hey ");
        printer.printf("%-42s", (nickname + "!"));
        printer.print("|\n");
        printer.print("|   Which colour you want to choose? Available:   |\n" +
                "+-------------------------------------------------+\n");

        for (int i = 0; i < modelView.getColors().size(); i++) {
            printer.printf(arrangeColorList, i + 1, modelView.getColors().get(i).getName());
        }
        printer.println();
        for (ModelView.PlayerView p : modelView.getPlayers()) {
            if (p.getColor() != null)
                printer.println("" + p.getNickname() + " chose " + p.getColor().getName());
        }
        printer.println();
        startingBrackets();
    }

    /**
     * Ask godly to select many gods as number of players.
     * Quit the game typing exit or quit
     *
     * @return the message with the selected gods
     */
    public InitialCardsMessage askGodList() {

        String s;
        String isNumber;
        InitialCardsMessage message = new InitialCardsMessage();

        //info 3
        do {
            printGodsList(message);
            startingBrackets();
            s = scanner.nextLine();
            checkInputExit(s);
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

    /**
     * Print gods list.
     *
     * @param message the message
     */
    public void printGodsList(InitialCardsMessage message) {
        int numberOfPlayers = (modelView.getPlayers().size() - message.getSelectedList().size());
        printer.println("+-----------------------------------------+\n" +
                "|    Select " + numberOfPlayers + " gods from the list below    |\n" +
                "|           type info/man + #god          |\n" +
                "|           to read more details          |\n" +
                "|                 (info 6)                |\n" +
                "+-----------------------------------------+");
        for (int i = 0; i < modelView.getGods().size(); i++) {
            if (i % 2 == 0) {
                printer.println();
            }
            printer.printf(arrangeGodList, i + 1, modelView.getGods().get(i)[0]);
        }
        printer.println();
    }

    /**
     * Ask player to select a god among those selected by godly.
     * Godly's god get assigned automatically with the remaining one.
     * Quit the game typing exit or quit
     *
     * @return the god message
     */
    public GodMessage askGod() {
        GodMessage message = new GodMessage();
        message.setId(getPlayerId());
        String s;
        String isNumber;
        int indexGod = 9;
        if (modelView.getChosenGods().size() == 1) {
            message.setGod(modelView.getChosenGods().get(0)[0]);
            modelView.getPlayer(getPlayerId()).setGod(modelView.getChosenGods().get(0));
            printer.println("You automatically got " + modelView.getChosenGods().get(0)[0] +
                    "\n" + modelView.getChosenGods().get(0)[1] + "\n");
        } else {
            do {
                printer.println("\nSelect God among the chosen gods:\n");
                for (int i = 0; i < modelView.getChosenGods().size(); i++) {
                    printer.printf(arrangeGodList, i + 1, modelView.getChosenGods().get(i)[0]);
                }
                printer.println();
                startingBrackets();
                s = scanner.nextLine();
                checkInputExit(s);
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
            } while (indexGod >= modelView.getChosenGods().size());
        }
        return message;
    }

    /**
     * Quit the game if input string contains exit or quit.
     * Support function
     *
     * @param input player's input
     */
    private void checkInputExit(String input) {
        if (input.contains("exit") || input.contains("quit")) {
            connection.sendEvent(new PropertyChangeEvent(this, "playerDisconnected", null, true));
            connection.closeConnection();
            System.exit(0);
        }
    }

    /**
     * Godly chooses the first player
     * Quit the game typing exit or quit
     *
     * @throws InterruptedException the interrupted exception
     */
    public synchronized void firstPlayer() throws InterruptedException {
        int indexNickname = 9;

        if (getPlayerId() == modelView.getGodlyId()) {
            NicknameMessage message = new NicknameMessage();
            String s;
            String isNumber;
            do {
                printer.println("Choose first player: ");
                for (int i = 0; i < modelView.getPlayers().size(); i++) {
                    printer.printf(arrangeGodList, i + 1, modelView.getPlayer(i).getNickname());
                }
                printer.println();
                startingBrackets();
                s = scanner.nextLine();
                checkInputExit(s);
                isNumber = s.replaceAll("[^0-9]", "");
                if (!isNumber.equals("")) {
                    indexNickname = Integer.parseInt(isNumber);
                    indexNickname -= 1;
                    if (indexNickname < modelView.getPlayers().size() && indexNickname >= 0) {
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

    /**
     * Player places the workers in the board typing valid and free Slot coordinate
     * Quit the game typing exit or quit
     *
     * @throws InterruptedException the interrupted exception
     */
    public synchronized void setupWorkers() throws InterruptedException {
        while (getPlayerId() != modelView.getActualPlayerId()) {
            wait();
        }
        WorkerMessage message;
        Coordinate c;
        for (int i = 0; i < 2; i++) {
            message = new WorkerMessage(getPlayerId(), i);
            do {
                int row;
                row = 9;
                int col;
                col = 9;
                String[] inputArray;
                String inputParsed;
                printer.println("Place your " + (i + 1) + "^ worker:");
                String inputToParse;
                do {
                    printer.println("Insert row,col : ");
                    startingBrackets();
                    inputToParse = scanner.nextLine();
                    checkInputExit(inputToParse);
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

    /**
     * This method manage the game flow after the workers setup.
     * Quit the game typing exit or quit
     * It sends player choices through sendEvent method of SSC, as PropertyChangeEvents
     * Events: actionRequest to get the list of actions available for this turn
     * endTurn to notify the end of this turn
     * notifyAction to send the action the player perform among the available
     *
     * @throws InterruptedException the interrupted exception
     */
    private synchronized void play() throws InterruptedException {
        while (!winnerDetected || getPlayerId() == modelView.getDeletedPlayerId()) {
            modelView.getActionsAvailable().clear();
            while (getPlayerId() == modelView.getActualPlayerId() && !endTurn) {
                if (winnerDetected) return;
                connection.sendEvent(new PropertyChangeEvent(this, "actionsRequest",
                        null, new GenericMessage()));
                wait();
                if (!modelView.getActionsAvailable().isEmpty() && getPlayerId() == modelView.getActualPlayerId() && !winnerDetected) {
                    List<String> choices = modelView.getActionChoices();
                    Action action = null;
                    do {
                        int choiceIndex = 0;
                        List<Coordinate> coords;
                        if (choices.size() > 1) {
                            String inputChoice;
                            printer.println(modelView.getPlayer(getPlayerId()).getColor().colorizedText(nickname)
                                    + ", which action do you want to perform?");

                            int i;
                            for (i = 0; i < choices.size(); i++) {
                                printer.printf(arrangeGodList, i + 1, choices.get(i).toLowerCase());
                            }

                            do {
                                printer.println();
                                startingBrackets();
                                inputChoice = scanner.nextLine();
                                checkInputExit(inputChoice);
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
                                    + ", perform your " + choices.get(choiceIndex).toLowerCase() + ": (x,y in r,s)");
                            startingBrackets();
                            String inputAction = scanner.nextLine();
                            checkInputExit(inputAction);
                            if (inputAction.contains("info") || inputAction.contains("man")) {
                                printer.println(modelView.getPlayer(getPlayerId()).getGod()[0]);
                                printer.println(modelView.getPlayer(getPlayerId()).getGod()[1]);
                            } else {
                                coords = parseCoordinateAction(inputAction);
                                if (coords.size() == 2) {
                                    action = modelView.searchAction(choices.get(choiceIndex), coords.get(0), coords.get(1));
                                }
                            }
                        }
                    } while (action == null && !endTurn && modelView.getActualPlayerId()==getPlayerId());

                    if (endTurn) {
                        modelView.getActionsAvailable().clear();
                        connection.sendEvent(new PropertyChangeEvent(this,
                                "endTurn", null, new GenericMessage()));
                    } else if(action!=null){
                        ActionMessage mess = new ActionMessage();
                        mess.setAction(action);
                        modelView.getActionsAvailable().clear();
                        connection.sendEvent(new PropertyChangeEvent(this,
                                "notifyAction", null, mess));
                    }
                    printBreakers();

                }
            }
            wait();
        }
    }


    /**
     * Parse user's action input string looking for valid couple of Coordinate.
     *
     * @param input got from user typing
     * @return the array list of Start and End Coordinate
     */
    public List<Coordinate> parseCoordinateAction(String input) {
        List<Coordinate> coordinates = new ArrayList<>();
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

    /**
     * Print the VirtualBoard stored in ModelView.
     */
    public void printBoard() {
        if (!winnerDetected) {
            String board = this.modelView.getBoard().toString();
            printer.println(board);
        }
        printBreakers();
    }

    /**
     * Print starting brackets for inputs.
     */
    public void startingBrackets() {
        printer.print(">>>");
    }

    /**
     * Print breakers.
     */
    public void printBreakers() {
        printer.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
    }


    /**
     * It calls all the specific method for ask input to the player and manage the entire game
     */
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

            System.exit(0);

        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }

    /**
     * Every player wait after he placed his workers until all players have placed their own.
     *
     * @throws InterruptedException the interrupted exception
     */
    private synchronized void allWorkerPlaced() throws InterruptedException {
        while (modelView.getFirstPlayerId() != modelView.getActualPlayerId()) {
            wait();
        }
    }

    /**
     * Every player wait after he chose his God until all players have chosen their own.
     *
     * @throws InterruptedException the interrupted exception
     */
    private synchronized void allGodSelected() throws InterruptedException {
        while (!modelView.getChosenGods().isEmpty()) {
            wait();
        }
    }

    /**
     * Invoke askGod when when it is the player's turn, otherwise player will wait.
     * It prepares the God message and, after player choice, send it.
     *
     * @throws InterruptedException the interrupted exception
     */
    private synchronized void godChoice() throws InterruptedException {
        while (modelView.getActualPlayerId() != getPlayerId()) {
            wait();
        }
        GodMessage message = askGod();
        connection.sendEvent(new PropertyChangeEvent(this, "notifyGod", null, message));
    }

    /**
     * Starting game asking to player if he want to play or read the help information.
     *
     * @throws InterruptedException the interrupted exception
     */
    private synchronized void startingGame() throws InterruptedException {
        welcomeMessage();
        int choiceMenu;
        do {
            printer.println("+-------------------------+\n" +
                    "|    Select an option     |\n" +
                    "+-------------------------+\n" +
                    "|                         |\n" +
                    "|    0- play  1- help     |\n" +
                    "|                         |\n" +
                    "+-------------------------+\n");
            startingBrackets();
            String input = scanner.nextLine();
            String nums = input.replaceAll("[^0-9]", "");
            if (!nums.equalsIgnoreCase("")) {
                choiceMenu = Integer.parseInt(nums);
            } else choiceMenu = -1;
            if (choiceMenu == 1) {
                printer.println("Santorini is an accessible strategy game.\n\n" +
                        " The rules are simple. Each turn consists of 2 steps:\n" +
                        "\t1. Move - move one of your workers into a neighboring space.\n" +
                        "You may move your worker on the same level,\n" +
                        "step-up one level, or step down any number of levels.\n" +
                        "\t2. Build - Then construct a building level adjacent\n" +
                        "to the worker you moved. When building on top of the third level,\n" +
                        "will be placed a dome instead, removing that space from play.\n" +
                        "Winning the game - If one of your Workers moves up on top of level 3\n" +
                        " during your turn, you instantly win!\n\n" +
                        "You must always perform a move then build on your turn.\n" +
                        "If you are unable to, you lose.\n" +
                        "God Powers provide you with a powerful ability\n" +
                        "that can be used throughout the game. Many God Powers change the way\n" +
                        "Workers move and build.\n" +
                        "TYPE MAN OR INFO DURING THE GAME TO SHOW THE POWER OF YOUR GOD\n\n" +
                        "Developers: Alberto Papiri, Gioele Peltrera, Sandro Shamal Jayasinghe\n\n");
            }
        } while (choiceMenu != 0);
        new Thread(getConnection()).start();
        this.wait();
    }

    /**
     * It prepares the color message and, after player choice, send it.
     */
    private void colorChoice() {
        while (!colorValidate) {
            ColorMessage message = askColor();
            waitingValidation(new PropertyChangeEvent(this, "notifyColor", false, message));
        }
    }

    /**
     * It prepares the nickname message and, after player choice, send it.
     */
    private void nickChoice() {
        while (!nickValidate) {
            NicknameMessage message = askNicknamePlayer();
            waitingValidation(new PropertyChangeEvent(this, "notifyNickname", false, message));
        }
    }


    /**
     * It send the event and put the player in waiting.
     *
     * @param evt the evt
     */
    public void waitingValidation(PropertyChangeEvent evt) {
        getConnection().sendEvent(evt);
        synchronized (this) {

            try {
                this.wait();
            } catch (InterruptedException | IllegalMonitorStateException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    /**
     * It notifies the player game is ready: all the players are in the room.
     */
    @Override
    protected synchronized void gameReady() {
        notify();
    }

    /**
     * Nickname validate received from the server. The game can go on.
     *
     * @param message the message
     */
    @Override
    protected synchronized void nicknameReceived(NicknameMessage message) {
        super.nicknameReceived(message);
        if (!message.getNickname().equals(nickname)) {
            return;
        }
        if (message.getId() == this.getPlayerId()) {
            nickValidate = true;
        }
        this.notify();
    }

    /**
     * Color validate received from the server. The game can go on.
     *
     * @param message the message
     */
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

    /**
     * Godly received from the server. The game can go on.
     *
     * @param message the message
     */
    @Override
    protected synchronized void setGodly(GodlyMessage message) {
        super.setGodly(message);
        if (getPlayerId() != modelView.getGodlyId()) {
            printer.println("+----------------------------+\n" +
                    "|   Godly is choosing Gods   |\n" +
                    "+----------------------------+");
            printBreakers();
        }
        godlySelected = true;
        notify();
    }


    /**
     * Chosen gods validate received from the server. The game can go on.
     *
     * @param message the message
     */
    @Override
    protected synchronized void chosenGods(InitialCardsMessage message) {
        super.chosenGods(message);
        if (modelView.getChosenGods().size() == modelView.getPlayers().size()) {
            printer.println("\tCHOSEN GODS:");
            for (String[] g : modelView.getChosenGods()) {
                printer.println("\n" + g[0] + "\n" + g[1]);
            }
            printBreakers();
            this.notify();
        }
    }

    /**
     * Assigned god validate received from the server. The game can go on.
     *
     * @param message the message
     */
    @Override
    protected synchronized void assignedGod(GodMessage message) {
        super.assignedGod(message);
        if (modelView.getChosenGods().isEmpty()) {
            for (ModelView.PlayerView p : modelView.getPlayers()) {
                printer.println(p.getNickname() + " chose " + p.getGod()[0]);
            }
        }
        printBreakers();
        printer.println();
        this.notify();
    }

    /**
     * Assigned god validate received from the server. The game can go on.
     *
     * @param message the message
     */
    @Override
    protected synchronized void setFirstPlayer(NicknameMessage message) {
        super.setFirstPlayer(message);
        printBreakers();
        printer.println(modelView.getPlayer(modelView.getGodlyId()).getNickname() +
                " chose " + message.getNickname() + " as first player!");
        printBoard();
        notify();
    }

    /**
     * Actions available for the player's turn. Print the board and the game can go on.
     *
     * @param message the message
     */
    @Override
    protected synchronized void actionsAvailable(ActionMessage message) {
        super.actionsAvailable(message);
        printBoard();
        notify();
    }

    /**
     * Workers placed validate received from the server. Print the board and the game can go on.
     *
     * @param message the message
     */
    @Override
    protected synchronized void setWorker(WorkerMessage message) {
        super.setWorker(message);
        printBoard();
        notify();
    }

    /**
     * End turn received from the server. The game can go on.
     *
     * @param message the message
     */
    @Override
    protected  void endTurn(NicknameMessage message) {
        super.endTurn(message);
        endTurn = false;
        synchronized (this){
            notify();
        }
    }

    /**
     * Winner detected message from the server. Print the winning the message, the game is going to be stopped.
     *
     * @param message the message
     */
    @Override
    protected synchronized void winnerDetected(WinnerMessage message) {
        winnerDetected = true;
        if (message.getId() != getPlayerId()) {
            printer.println("\n\t\t" + ANSIColor.BACK_YELLOW+ANSIColor.WHITE+ANSIColor.BOLD + modelView.getPlayer(message.getId()).getNickname() + ANSIColor.RESET + "\n\n" +
                    "$$\\   $$\\  $$$$$$\\   $$$$$$\\        $$\\      $$\\  $$$$$$\\  $$\\   $$\\ \n" +
                    "$$ |  $$ |$$  __$$\\ $$  __$$\\       $$ | $\\  $$ |$$  __$$\\ $$$\\  $$ |\n" +
                    "$$ |  $$ |$$ /  $$ |$$ /  \\__|      $$ |$$$\\ $$ |$$ /  $$ |$$$$\\ $$ |\n" +
                    "$$$$$$$$ |$$$$$$$$ |\\$$$$$$\\        $$ $$ $$\\$$ |$$ |  $$ |$$ $$\\$$ |\n" +
                    "$$  __$$ |$$  __$$ | \\____$$\\       $$$$  _$$$$ |$$ |  $$ |$$ \\$$$$ |\n" +
                    "$$ |  $$ |$$ |  $$ |$$\\   $$ |      $$$  / \\$$$ |$$ |  $$ |$$ |\\$$$ |\n" +
                    "$$ |  $$ |$$ |  $$ |\\$$$$$$  |      $$  /   \\$$ | $$$$$$  |$$ | \\$$ |\n" +
                    "\\__|  \\__|\\__|  \\__| \\______/       \\__/     \\__| \\______/ \\__|  \\__|\n" +
                    "                                                                     \n" +
                    "                                                                     \n" +
                    "                                                                     " + ANSIColor.RESET);
        } else {
            printer.println("\n" +
                    "$$\\     $$\\  $$$$$$\\  $$\\   $$\\       $$\\      $$\\ $$$$$$\\ $$\\   $$\\ \n" +
                    "\\$$\\   $$  |$$  __$$\\ $$ |  $$ |      $$ | $\\  $$ |\\_$$  _|$$$\\  $$ |\n" +
                    " \\$$\\ $$  / $$ /  $$ |$$ |  $$ |      $$ |$$$\\ $$ |  $$ |  $$$$\\ $$ |\n" +
                    "  \\$$$$  /  $$ |  $$ |$$ |  $$ |      $$ $$ $$\\$$ |  $$ |  $$ $$\\$$ |\n" +
                    "   \\$$  /   $$ |  $$ |$$ |  $$ |      $$$$  _$$$$ |  $$ |  $$ \\$$$$ |\n" +
                    "    $$ |    $$ |  $$ |$$ |  $$ |      $$$  / \\$$$ |  $$ |  $$ |\\$$$ |\n" +
                    "    $$ |     $$$$$$  |\\$$$$$$  |      $$  /   \\$$ |$$$$$$\\ $$ | \\$$ |\n" +
                    "    \\__|     \\______/  \\______/       \\__/     \\__|\\______|\\__|  \\__|\n" +
                    "                                                                     \n" +
                    "                                                                     \n" +
                    "                                                                     " + ANSIColor.RESET);
        }

        notify();
    }

    @Override
    protected void endGame() {

        printer.println("\n\n\n+-----------------------------------------+\n" +
                "|                                         |\n" +
                "|     A player left the lobby, please     |\n" +
                "|            start a new game.            |\n" +
                "|                                         |\n" +
                "+-----------------------------------------+");
        System.exit(0);
    }

    /**
     * Player has lost for no available moves, the message is received from the server.
     *
     * @param message the message
     */
    @Override
    protected  void playerHasLost(GenericMessage message) {
        synchronized (this) {
            printer.println("\n" + modelView.getPlayer(message.getId()).getColor().colorizedText(modelView.getPlayer(message.getId()).getNickname()) + " has lost!\n");
            printBreakers();
            //if(message.getId()==modelView.getActualPlayerId())notify();
            if(message.getId()==modelView.getActualPlayerId()) {
                modelView.setNextPlayerId();
                notify();
            }
        }
    }

    @Override
    protected void invalidAlert(){
        printer.println("\n\n\n+-----------------------------------------+\n" +
                "|                                         |\n" +
                "|     A connection issue has occurred     |\n" +
                "|         please check parameters.        |\n" +
                "|         See readMe for more info        |\n" +
                "|                                         |\n" +
                "+-----------------------------------------+");
        System.exit(0);
    }

}
