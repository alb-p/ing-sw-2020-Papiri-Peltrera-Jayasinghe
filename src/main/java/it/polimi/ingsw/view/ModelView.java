package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.VirtualBoard;

import java.util.ArrayList;

public class ModelView {

    private VirtualBoard board;
    private ArrayList<Color> colors = new ArrayList<>();
    private ArrayList<PlayerView> players = new ArrayList<>();
    private ArrayList<String[]> gods = new ArrayList<>();



    public VirtualBoard getBoard(){return board;}

    public PlayerView getPlayer(int id){
        for(PlayerView p : players){
            if(p.getId() == id)return p;
        }
        return null;
    }

    public class PlayerView{
        private int id;
        private String nickname;
        private String[] god;

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
    }


}
