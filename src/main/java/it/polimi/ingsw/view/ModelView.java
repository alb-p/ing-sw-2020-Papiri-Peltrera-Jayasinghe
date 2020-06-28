package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.messages.ColorMessage;
import it.polimi.ingsw.utils.messages.NicknameMessage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Model view.
 * It contains all the information about the game and it is usually update after confirm by server messages
 */
public class ModelView {

    private final VirtualBoard board = new VirtualBoard();
    private final ArrayList<Color> colors = new ArrayList<>();
    private final ArrayList<PlayerView> players = new ArrayList<>();
    private ArrayList<String[]> gods = new ArrayList<>();
    private final ArrayList<String[]> chosenGods = new ArrayList<>();
    private ArrayList<Action> actionsAvailable = new ArrayList<>();
    private int actualPlayerId;
    private int godlyId;
    private int firstPlayerId;
    private int deletedPlayerId = -1;
    private int winnerId = -1;
    private boolean optional;
    private final PropertyChangeSupport nickNameListener = new PropertyChangeSupport(this);
    private final PropertyChangeSupport colorListener = new PropertyChangeSupport(this);
    private final PropertyChangeSupport selectedGodsListener = new PropertyChangeSupport(this);
    private final PropertyChangeSupport selectedSingleGodListener = new PropertyChangeSupport(this);
    private final PropertyChangeSupport boardListener = new PropertyChangeSupport(this);
    private final PropertyChangeSupport firstPlayerListener = new PropertyChangeSupport(this);
    private final PropertyChangeSupport actionListener = new PropertyChangeSupport(this);

    /**
     * Instantiates a new Model view.
     */
    public ModelView() {
        Collections.addAll(colors, Color.values());
        gods.add(new String[]{"APOLLO", "Your Move: Your Worker may\n" +
                "move into an opponent Worker’s\n" +
                "space by forcing their Worker to\n" +
                "the space yours just vacated."});
        gods.add(new String[]{"ARTEMIS", "Your Move: Your Worker may\n" +
                "move one additional time, but not\n" +
                "back to its initial space. "});
        gods.add(new String[]{"ATHENA", "Opponent’s Turn: If one of your\n" +
                "Workers moved up on your last\n" +
                "turn, opponent Workers cannot\n" +
                "move up this turn."});
        gods.add(new String[]{"ATLAS", "Your Build: Your Worker may\n" +
                "build a dome at any level. "});
        gods.add(new String[]{"CHRONUS", "Win Condition: You also win\n" +
                "when there are at least five\n" +
                "Complete Towers on the board"});
        gods.add(new String[]{"DEMETER", "Your Build: Your Worker may\n" +
                "build one additional time, but not\n" +
                "on the same space."});
        gods.add(new String[]{"HEPHAESTUS", "Your Build: Your Worker may\n" +
                "build one additional block (not\n" +
                "dome) on top of your first block"});
        gods.add(new String[]{"HESTIA", "Your Build: Your Worker may\n" +
                "build one additional time, but this\n" +
                "cannot be on a perimeter space"});
        gods.add(new String[]{"HYPNUS", "Start of Opponent’s Turn: If one\n" +
                "of your opponent’s Workers is\n" +
                "higher than all of their others, it\n" +
                "cannot move."});
        gods.add(new String[]{"MINOTAUR", "Your Move: Your Worker may\n" +
                "move into an opponent Worker’s\n" +
                "space, if their Worker can be\n" +
                "forced one space straight backwards to an\n" +
                "unoccupied space at any level."});
        gods.add(new String[]{"PAN", "Win Condition: You also win if\n" +
                "your Worker moves down two or\n" +
                "more levels"});
        gods.add(new String[]{"POSEIDON", "End of Your Turn: If your\n" +
                "unmoved Worker is on the\n" +
                "ground level, it may build up to\n" +
                "three times."});
        gods.add(new String[]{"PROMETHEUS", "Your Turn: If your Worker does\n" +
                "not move up, it may build both\n" +
                "before and after moving"});
        gods.add(new String[]{"ZEUS", "Your Build: Your Worker may\n" +
                "build a block under itself."});
    }

    /**
     * Gets gods.
     *
     * @return the gods
     */
    public ArrayList<String[]> getGods() {
        return gods;
    }

    /**
     * Sets gods.
     *
     * @param gods the gods
     */
    public void setGods(ArrayList<String[]> gods) {
        this.gods = gods;
    }

    /**
     * Gets board.
     *
     * @return the board
     */
    public VirtualBoard getBoard() {
        return board;
    }

    /**
     * Gets player.
     *
     * @param id the id
     * @return the player
     */
    public PlayerView getPlayer(int id) {
        for (PlayerView p : players) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    /**
     * Gets player.
     *
     * @param nickname the nickname
     * @return the player
     */
    public PlayerView getPlayer(String nickname) {
        for (PlayerView p : players) {
            if (p.getNickname().equals(nickname)) return p;
        }
        return null;
    }

    /**
     * Sets actual player id.
     *
     * @param nickname the nickname
     */
    public void setActualPlayerId(String nickname) {
        for (PlayerView p : players) {
            if (p.getNickname().equals(nickname)) {
                setActualPlayerId(p.getId());
            }
        }
    }

    /**
     * Add player.
     *
     * @param id   the id
     * @param nick the nick
     */
    public void addPlayer(int id, String nick) {
        players.add(new PlayerView(id, nick));
        NicknameMessage message = new NicknameMessage();
        message.setId(id);
        message.setNickname(nick);
        nickNameListener.firePropertyChange("nicknameConfirm", null, message);
    }

    /**
     * Check nickname boolean.
     *
     * @param nickname the nickname
     * @return the boolean
     */
    public boolean checkNickname(String nickname) {
        for (PlayerView p : players) {
            if (p.getNickname().equals(nickname)) return false;
        }
        return true;
    }

    /**
     * Sets color.
     *
     * @param id    the id
     * @param color the color
     */
    public void setColor(int id, Color color) {
        getPlayer(id).setColor(color);
        colors.remove(color);
        ColorMessage message = new ColorMessage(id);
        message.setColor(color);
        colorListener.firePropertyChange("colorConfirm", null, message);
    }

    /**
     * Is in color boolean.
     *
     * @param color the color
     * @return the boolean
     */
    public boolean isInColor(Color color) {
        for (Color c : colors) {
            if (c.equals(color)) return true;
        }
        return false;
    }

    /**
     * Gets colors.
     *
     * @return the colors
     */
    public ArrayList<Color> getColors() {
        return colors;
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public ArrayList<PlayerView> getPlayers() {
        return players;
    }

    /**
     * Gets actual player id.
     *
     * @return the actual player id
     */
    public int getActualPlayerId() {
        return actualPlayerId;
    }

    /**
     * Sets actual player id.
     *
     * @param actualPlayerId the actual player id
     */
    public void setActualPlayerId(int actualPlayerId) {
        this.actualPlayerId = actualPlayerId;
    }

    /**
     * Sets next player id.
     */
    public void setNextPlayerId() {
        actualPlayerId = (actualPlayerId + 1) % players.size();
        if (actualPlayerId == deletedPlayerId) {
            actualPlayerId = (actualPlayerId + 1) % players.size();
        }
    }

    /**
     * Sets deleted player id.
     *
     * @param deletedPlayerId the deleted player id
     */
    public void setDeletedPlayerId(int deletedPlayerId) {
        this.deletedPlayerId = deletedPlayerId;
    }

    /**
     * Sets godly id.
     *
     * @param godlyId the godly id
     */
    public void setGodlyId(int godlyId) {
        this.godlyId = godlyId;
        actualPlayerId = godlyId;
        setNextPlayerId();
    }

    /**
     * Gets godly id.
     *
     * @return the godly id
     */
    public int getGodlyId() {
        return godlyId;
    }

    /**
     * Gets first player id.
     *
     * @return the first player id
     */
    public int getFirstPlayerId() {
        return firstPlayerId;
    }

    /**
     * Sets first player id.
     *
     * @param firstPlayerId the first player id
     */
    public void setFirstPlayerId(int firstPlayerId) {
        this.firstPlayerId = firstPlayerId;
        firstPlayerListener.firePropertyChange("firstPlayer", null, true);
    }

    /**
     * Gets chosen gods.
     *
     * @return the chosen gods
     */
    public ArrayList<String[]> getChosenGods() {
        return chosenGods;
    }

    /**
     * Add chosen gods.
     *
     * @param chosenGods the chosen gods
     */
    public void addChosenGods(ArrayList<String> chosenGods) {
        for (String s : chosenGods) {
            for (String[] g : gods) {
                if (s.equalsIgnoreCase(g[0])) {
                    this.chosenGods.add(g);
                }
            }
        }
        selectedGodsListener.firePropertyChange("notifySelectedGods", false, this.chosenGods);

    }

    /**
     * Sets god.
     *
     * @param id  the id
     * @param god the god
     */
    public void setGod(int id, String god) {
        String[] toRemove = new String[2];
        for (String[] s : chosenGods) {
            if (s[0].equalsIgnoreCase(god)) {
                getPlayer(id).setGod(s);
                toRemove = s;
            }
        }
        chosenGods.remove(toRemove);
        ArrayList<String> godSelected = new ArrayList<>();
        godSelected.add(getPlayer(id).nickname);
        godSelected.add(god);
        selectedSingleGodListener.firePropertyChange("notifyGodSelected", null, godSelected);

    }

    /**
     * Update board.
     *
     * @param vSlot the v slot
     */
    public void updateBoard(VirtualSlot vSlot) {
        board.setSlot(vSlot);
        boardListener.firePropertyChange("boardUpdate", null, vSlot);
    }

    /**
     * Gets actions available.
     *
     * @return the actions available
     */
    public ArrayList<Action> getActionsAvailable() {
        return this.actionsAvailable;
    }

    /**
     * Sets actions available.
     *
     * @param actions the actions
     */
    public void setActionsAvailable(ArrayList<Action> actions) {
        this.actionsAvailable = actions;
        actionListener.firePropertyChange("actionsReceived", false , true);
    }

    /**
     * Gets winner id.
     *
     * @return the winner id
     */
    public int getWinnerId() {
        return this.winnerId;
    }

    /**
     * Gets deleted player id.
     *
     * @return the deleted player id
     */
    public int getDeletedPlayerId() {
        return this.deletedPlayerId;
    }

    /**
     * Sets winner id.
     *
     * @param id the id
     */
    public void setWinnerId(int id) {
        this.winnerId = id;
    }

    /**
     * Search action action.
     *
     * @param s     the s
     * @param start the start
     * @param end   the end
     * @return the action
     */
    public Action searchAction(String s, Coordinate start, Coordinate end) {
        for (Action a : actionsAvailable) {
            if (a.getActionName().equalsIgnoreCase(s) &&
                    a.getStart().equals(start)
                    && a.getEnd().equals(end)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Gets action choices.
     *
     * @return the action choices
     */
    public ArrayList<String> getActionChoices() {
        ArrayList<String> choices = new ArrayList<>();
        boolean found = false;
        for (Action a : actionsAvailable) {
            for (String s : choices) {
                if (s.equalsIgnoreCase(a.getActionName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                choices.add(a.getActionName());
            }
            found = false;
        }
        if (optional) {
            choices.add("end turn");
        }
        return choices;
    }

    /**
     * Is optional boolean.
     *
     * @return the boolean
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     * Sets optional.
     *
     * @param optional the optional
     */
    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    /**
     * Player lost.
     *
     * @param id the id
     */
    public void playerLost(int id) {
        this.deletedPlayerId = id;
    }

    /**
     * Add nickname listener.
     *
     * @param listener the listener
     */
    public void addNicknameListener(PropertyChangeListener listener) {
        this.nickNameListener.addPropertyChangeListener(listener);
    }

    /**
     * Add color listener.
     *
     * @param listener the listener
     */
    public void addColorListener(PropertyChangeListener listener) {
        this.colorListener.addPropertyChangeListener(listener);
    }

    /**
     * Add selected gods listener.
     *
     * @param listener the listener
     */
    public void addSelectedGodsListener(PropertyChangeListener listener) {
        this.selectedGodsListener.addPropertyChangeListener(listener);
    }

    /**
     * Add selected single god listener.
     *
     * @param listener the listener
     */
    public void addSelectedSingleGodListener(PropertyChangeListener listener) {
        selectedSingleGodListener.addPropertyChangeListener(listener);
    }

    /**
     * Add first player listener.
     *
     * @param listener the listener
     */
    public void addFirstPlayerListener(PropertyChangeListener listener) {
        firstPlayerListener.addPropertyChangeListener(listener);
    }

    /**
     * Add board listener.
     *
     * @param listener the listener
     */
    public void addBoardListener(PropertyChangeListener listener) {
        boardListener.addPropertyChangeListener(listener);

    }

    /**
     * Add action listener.
     *
     * @param listener the listener
     */
    public void addActionListener(PropertyChangeListener listener) {
        actionListener.addPropertyChangeListener(listener);
    }

    /**
     * The type Player view.
     */
    public class PlayerView {
        private int id;
        private String nickname;
        private String[] god;
        private Color color;

        /**
         * Instantiates a new Player view.
         *
         * @param id       the id
         * @param nickname the nickname
         */
        public PlayerView(int id, String nickname) {
            this.id = id;
            this.nickname = nickname;
        }

        /**
         * Gets id.
         *
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * Sets id.
         *
         * @param id the id
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         * Gets nickname.
         *
         * @return the nickname
         */
        public String getNickname() {
            return nickname;
        }

        /**
         * Sets nickname.
         *
         * @param nickname the nickname
         */
        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        /**
         * Get god string [ ].
         *
         * @return the string [ ]
         */
        public String[] getGod() {
            return god;
        }

        /**
         * Sets god.
         *
         * @param god the god
         */
        public void setGod(String[] god) {
            this.god = god;
        }

        /**
         * Gets color.
         *
         * @return the color
         */
        public Color getColor() {
            return color;
        }

        /**
         * Sets color.
         *
         * @param color the color
         */
        public void setColor(Color color) {
            this.color = color;
        }
    }


}
