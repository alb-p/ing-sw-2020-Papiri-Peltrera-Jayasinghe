package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.VirtualBoard;

import java.util.ArrayList;
import java.util.Collections;

public class ModelView {

    private VirtualBoard board;
    private ArrayList<Color> colors = new ArrayList<>();
    private ArrayList<PlayerView> players = new ArrayList<>();
    private ArrayList<String[]> gods = new ArrayList<>();
    private int actualPlayerId;
    private int godlyId;
    private int deletedPlayerId = -1;

    public ModelView(){
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
        gods.add(new String[]{"DEMETER", "Your Build: Your Worker may\n" +
                "build one additional time, but not\n" +
                "on the same space."});
        gods.add(new String[]{"HEPHAESTUS", "Your Build: Your Worker may\n" +
                "build one additional block (not\n" +
                "dome) on top of your first block"});
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
    }

    public ArrayList<String[]> getGods() {
        return gods;
    }

    public void setGods(ArrayList<String[]> gods) {
        this.gods = gods;
    }
    public VirtualBoard getBoard(){return board;}

    public PlayerView getPlayer(int id){
        for(PlayerView p : players){
            if(p.getId() == id)return p;
        }
        return null;
    }

    public void addPlayer(int id, String nick) {
        players.add( new PlayerView(id, nick));
    }

    public boolean checkNickname(String nickname) {
        for(PlayerView p : players){
            if(p.getNickname().equals(nickname)) return false;
        }
        return true;
    }

    public void setColor(int id, Color color) {
        getPlayer(id).setColor(color);
        colors.remove(color);
    }

    public boolean isInColor(Color color) {
        for(Color c: colors){
            if(c.equals(color)) return true;
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

    public void setNextPlayerId(){
        actualPlayerId = (actualPlayerId + 1) % players.size();
        if(actualPlayerId == deletedPlayerId){
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

    public int getGodlyId(){
        return godlyId;
    }

    public class PlayerView{
        private int id;
        private String nickname;
        private String[] god;
        private Color color;

        public PlayerView(int id, String nickname){
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
