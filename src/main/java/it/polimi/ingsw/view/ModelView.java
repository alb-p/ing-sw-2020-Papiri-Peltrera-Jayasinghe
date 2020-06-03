package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.messages.ColorMessage;
import it.polimi.ingsw.utils.messages.NicknameMessage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;

public class ModelView {

    private VirtualBoard board = new VirtualBoard();
    private ArrayList<Color> colors = new ArrayList<>();
    private ArrayList<PlayerView> players = new ArrayList<>();
    private ArrayList<String[]> gods = new ArrayList<>();
    private ArrayList<String[]> chosenGods = new ArrayList<>();
    private ArrayList<Action> actionsAvailable = new ArrayList<>();
    private int actualPlayerId;
    private int godlyId;
    private int firstPlayerId;
    private int deletedPlayerId = -1;
    private int winnerId = -1;
    private boolean optional;
    private PropertyChangeSupport nickNameListener = new PropertyChangeSupport(this);
    private PropertyChangeSupport colorListener = new PropertyChangeSupport(this);
    private PropertyChangeSupport selectedGodsListener = new PropertyChangeSupport(this);
    private PropertyChangeSupport selectedSingleGodListener = new PropertyChangeSupport(this);

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
        gods.add(new String[]{"MINOTAUR", "Your Move: Your Worker may\n" +
                "move into an opponent Worker’s\n" +
                "space, if their Worker can be\n" +
                "forced one space straight backwards to an\n" +
                "unoccupied space at any level."});
        gods.add(new String[]{"PAN", "Win Condition: You also win if\n" +
                "your Worker moves down two or\n" +
                "more levels"});
        gods.add(new String[]{"PROMETHEUS", "Your Turn: If your Worker does\n" +
                "not move up, it may build both\n" +
                "before and after moving"});
        gods.add(new String[]{"ZEUS", "Your Build: Your Worker may\n" +
                "build a block under itself."});
    }

    public ArrayList<String[]> getGods() {
        return gods;
    }

    public void setGods(ArrayList<String[]> gods) {
        this.gods = gods;
    }

    public VirtualBoard getBoard() {
        return board;
    }

    public PlayerView getPlayer(int id) {
        for (PlayerView p : players) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public PlayerView getPlayer(String nickname) {
        for (PlayerView p : players) {
            if (p.getNickname().equals(nickname)) return p;
        }
        return null;
    }

    public void setActualPlayerId(String nickname) {
        for (PlayerView p : players) {
            if (p.getNickname().equals(nickname)) {
                setActualPlayerId(p.getId());
            }
        }
    }

    public void addPlayer(int id, String nick) {
        players.add(new PlayerView(id, nick));
        NicknameMessage message = new NicknameMessage();
        message.setId(id);
        message.setNickname(nick);
        System.out.println("ADDED +"+nick);
        nickNameListener.firePropertyChange("nicknameConfirm", null, message);
    }

    public boolean checkNickname(String nickname) {
        for (PlayerView p : players) {
            if (p.getNickname().equals(nickname)) return false;
        }
        return true;
    }

    public void setColor(int id, Color color) {
        getPlayer(id).setColor(color);
        colors.remove(color);
        ColorMessage message = new ColorMessage(id);
        message.setColor(color);
        colorListener.firePropertyChange("colorConfirm", null, message);
    }

    public boolean isInColor(Color color) {
        for (Color c : colors) {
            if (c.equals(color)) return true;
        }
        return false;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public ArrayList<PlayerView> getPlayers() {
        return players;
    }

    public int getActualPlayerId() {
        return actualPlayerId;
    }

    public void setActualPlayerId(int actualPlayerId) {
        this.actualPlayerId = actualPlayerId;
    }

    public void setNextPlayerId() {
        actualPlayerId = (actualPlayerId + 1) % players.size();
        if (actualPlayerId == deletedPlayerId) {
            actualPlayerId = (actualPlayerId + 1) % players.size();
        }
    }

    public void setDeletedPlayerId(int deletedPlayerId) {
        this.deletedPlayerId = deletedPlayerId;
    }

    public void setGodlyId(int godlyId) {
        this.godlyId = godlyId;
        actualPlayerId = godlyId;
        setNextPlayerId();
    }

    public int getGodlyId() {
        return godlyId;
    }

    public int getFirstPlayerId() {
        return firstPlayerId;
    }

    public void setFirstPlayerId(int firstPlayerId) {
        this.firstPlayerId = firstPlayerId;
    }

    public ArrayList<String[]> getChosenGods() {
        return chosenGods;
    }

    public void addChosenGods(ArrayList<String> chosenGods) {
        for (String s : chosenGods) {
            for (String[] g : gods) {
                if (s.equalsIgnoreCase(g[0])) {
                    this.chosenGods.add(g);
                }
            }
        }
        selectedGodsListener.firePropertyChange("notifySelectedGods", false, this .chosenGods);

    }

    public void setGod(int id, String god) {
        String[] toRemove = new String[2];
        for (String[] s : chosenGods) {
            if (s[0].equalsIgnoreCase(god)) {
                getPlayer(id).setGod(s);
                toRemove = s;
            }
        }
        chosenGods.remove(toRemove);
        ArrayList<String> godSelected= new ArrayList<String>();
        godSelected.add(getPlayer(id).nickname);
        godSelected.add(god);
        selectedSingleGodListener.firePropertyChange("notifyGodSelected",null, godSelected);

    }

    public void updateBoard(VirtualSlot vSlot) {
        board.setSlot(vSlot);
    }

    public ArrayList<Action> getActionsAvailable() {
        return this.actionsAvailable;
    }

    public void setActionsAvailable(ArrayList<Action> actions) {
        this.actionsAvailable = actions;
    }

    public int getWinnerId() {
        return this.winnerId;
    }

    public int getDeletedPlayerId() {
        return this.deletedPlayerId;
    }

    public void setWinnerId(int id){
        this.winnerId = id;
    }

    public Action searchAction(String s, Coordinate start, Coordinate end) {
        for (Action a : actionsAvailable) {
            System.out.println("ACT NAME:: "+a.getActionName()+ a.getStart() + a.getEnd());
            if (a.getActionName().equalsIgnoreCase(s) &&
                    a.getStart().equals(start)
                    && a.getEnd().equals(end)) {
                return a;
            }
        }
        return null;
    }

    public ArrayList<String> getActionChoices() {
        ArrayList<String> choices = new ArrayList<>();
        Boolean found = false;
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
        if(optional){
            choices.add("end turn");
        }
        return choices;
    }
    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public void playerLost(int id) {
        this.deletedPlayerId=id;
    }

    public void addNicknameListener(PropertyChangeListener listener){
        this.nickNameListener.addPropertyChangeListener(listener);
    }

    public void addColorListener(PropertyChangeListener listener){
        this.colorListener.addPropertyChangeListener(listener);
    }
    public void addSelectedGodsListener(PropertyChangeListener listener){
        this.selectedGodsListener.addPropertyChangeListener(listener);
    }
    public void addSelectedSingleGodListener(PropertyChangeListener listener){
        selectedSingleGodListener.addPropertyChangeListener(listener);
    }
    public class PlayerView {
        private int id;
        private String nickname;
        private String[] god;
        private Color color;

        public PlayerView(int id, String nickname) {
            this.id = id;
            this.nickname = nickname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String[] getGod() {
            return god;
        }

        public void setGod(String[] god) {
            this.god = god;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }


}
