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


    public ModelView(){
        Collections.addAll(colors, Color.values());
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
