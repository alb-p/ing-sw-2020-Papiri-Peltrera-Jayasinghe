package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.utils.messages.GenericMessage;
import it.polimi.ingsw.utils.messages.NicknameMessage;
import it.polimi.ingsw.utils.messages.WorkerMessage;
import it.polimi.ingsw.view.ModelView;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.util.ArrayList;
import java.util.List;

public class PlayPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private PropertyChangeSupport playPanelListener = new PropertyChangeSupport(this);
    private Image bgIsland = new ImageIcon(this.getClass().getResource("/IslandAnimation/island120.jpg")).getImage();

    int i = 0;
    boolean workerPlaced = false;
    boolean play = false;
    boolean myTurn = false;
    boolean firstPlayerSelected = false;
    private ArrayList<Coordinate> workerPositions = new ArrayList<>();


    private ModelView modelView;//da impostare
    private int playerID;//da impostare
    private String color;
    private JPanel boardPanel;
    private JLabel messageCenter;
    private JButton submitButton = new JButton("Submit");
    private TileButton west;
    private TileButton east;
    private Coordinate toBeSendedWorker = new Coordinate(-1, -1);
    private Coordinate selected;
    private boolean buildDome = false;
    private TileButton[][] boardOfButtons = new TileButton[5][5];
    private Image banner = new ImageIcon(this.getClass().getResource("/Gameplay/messageCenter.jpg")).getImage().getScaledInstance(960, 70, Image.SCALE_SMOOTH);


    public PlayPanel(ModelView modelView) {
        messageCenter = new JLabel();
        Font messageFont;
        Font submitFont;
        try {
            messageFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")).deriveFont(Font.PLAIN, 35); //carica font
            submitFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")).deriveFont(Font.PLAIN, 15); //carica font
        } catch (IOException | FontFormatException e) {
            messageFont = messageCenter.getFont();
            submitFont = messageCenter.getFont();
        }
        messageCenter.setFont(messageFont);
        messageCenter.setHorizontalAlignment(SwingConstants.CENTER);
        this.modelView = modelView;
        this.setLayout(new BorderLayout());
        east = new TileButton(-1, -1, this);
        //east.setPreferredSize(new Dimension(130,200));
        TransferHandler dragAndDrop = new DragAndDrop();
        east.setWorker(new ImageIcon(this.getClass().getResource("/Colors/woker_inactive.png")).getImage().getScaledInstance(130, 200, Image.SCALE_SMOOTH));
        east.setTransferHandler(dragAndDrop);
        east.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                TransferHandler handle = button.getTransferHandler();
                handle.exportAsDrag(button, e, TransferHandler.MOVE);
            }
        });
        submitButton.addActionListener(this);
        west = new TileButton(-1, -1, this);
        west.setWorker(new ImageIcon(this.getClass().getResource("/Colors/woker_inactive.png")).getImage().getScaledInstance(130, 200, Image.SCALE_SMOOTH));
        west.setTransferHandler(dragAndDrop);
        west.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                TransferHandler handle = button.getTransferHandler();
                handle.exportAsDrag(button, e, TransferHandler.MOVE);
            }

        });
        boardPanel = new JPanel(new GridLayout(5, 5, 7, 7));
        for (int row = 4; row >= 0; row--) {
            for (int col = 0; col < 5; col++) {
                boardOfButtons[row][col] = new TileButton(row, col, this);
                boardOfButtons[row][col].addActionListener(this);
                boardOfButtons[row][col].setTransferHandler(dragAndDrop);

                boardOfButtons[row][col].addMouseMotionListener(new MouseAdapter() {

                    @Override
                    public void mouseDragged(MouseEvent e) {
                        //((JComponent)e.getSource()).repaint();
                        JButton button = (JButton) e.getSource();
                        TransferHandler handle = button.getTransferHandler();
                        handle.exportAsDrag(button, e, TransferHandler.MOVE);
                    }

                    /*@Override
                    public void mouseMoved(MouseEvent e) {
                        super.mouseMoved(e);
                        repaint();
                    }*/
                });

                //boardOfButtons[row][col].setBackground(new Color(0, 0, 0, 0));
                boardPanel.add(boardOfButtons[row][col]);
            }
        }
        this.setOpaque(false);
        //boardPanel.setBorder(BorderFactory.createEmptyBorder(135, 120, 60, 135));
        boardPanel.setBorder(BorderFactory.createEmptyBorder((int) (0.155 * GUI.getDimension().height), (int) (0.165 * GUI.getDimension().height), (int) (.09 * GUI.getDimension().height), (int) (0.19 * GUI.getDimension().height)));
        boardPanel.setOpaque(false);
        this.add(boardPanel, BorderLayout.CENTER);
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(Box.createVerticalStrut(200));
        east.setBorder(BorderFactory.createEmptyBorder(100, 75, 100, 75));
        p.add(east);
        p.setAlignmentY(1);
        p.setOpaque(false);
        this.add(p, BorderLayout.EAST);
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(Box.createVerticalStrut(200));
        west.setBorder(BorderFactory.createEmptyBorder(100, 75, 100, 75));
        //east.setBackground(new Color(0,0,0,0));
        //west.setBackground(new Color(0,0,0,0));
        p.add(west);
        p.setOpaque(false);
        this.add(p, BorderLayout.WEST);
        messageCenter.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        this.add(messageCenter, BorderLayout.NORTH);
        submitButton.setName("submit");
        submitButton.setFont(submitFont);
        messageCenter.setForeground(Color.WHITE);
        submitButton.setEnabled(false);
        this.add(submitButton, BorderLayout.SOUTH);
        repaint();
        //messageCenter.setBackground(new Color(65, 81, 194,150));
        messageCenter.setOpaque(false);
        messageCenter.setText("Godly player is selecting the first player");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof TileButton) {
            TileButton t = (TileButton) e.getSource();
            if (myTurn) {
                List<Action> actions = (List<Action>) modelView.getActionsAvailable().clone();
                if (selected == null) {
                    for (Action a : actions) {
                        if (a.getStart().equals(t.getCoordinate())) {
                            selected = t.getCoordinate();
                        }
                    }
                    if (selected == null) {
                        Coordinate startBuild = actions.get(0).getStart();
                        for (Action a : actions) {
                            if (!a.getStart().equals(startBuild)) return;
                        }
                        tryBuild(t, actions);
                    }
                } else {
                    tryBuild(t, actions);
                    selected = null;
                    return;
                }
            }
        } else if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getName().equalsIgnoreCase("submit")) {
                sendWorkers();
                submitButton.setEnabled(false);
            } else if (button.getName().equalsIgnoreCase("end turn")) {
                submitButton.setEnabled(false);
                modelView.getActionsAvailable().clear();
                playPanelListener.firePropertyChange("end turn", null, new GenericMessage());
            } else if (button.getName().equalsIgnoreCase("Build a dome")) {
                if (buildDome) {
                    buildDome = false;
                    submitButton.setForeground(Color.BLACK);
                } else buildDome = true;
            }

        }

        repaint();

    }

    private void tryBuild(TileButton t, List<Action> actions) {
        for (Action a : actions) {
            if (a.getEnd().equals(t.getCoordinate())) {
                if (a.getActionName().equalsIgnoreCase("BUILD") || a instanceof Build) {
                    if (buildDome && a.getActionName().equalsIgnoreCase("BUILD A DOME")) {
                        sendAction(new BuildDome(a.getStart(), a.getEnd()));
                    } else if (!buildDome) {
                        sendAction(new Build(a.getStart(), a.getEnd()));
                    }
                }
            }
        }
    }

    private void turnHandler(Coordinate coordinate) {


    }

    private void sendWorkers() {
        for (int i = 0; i < 2; i++) {
            WorkerMessage mess = new WorkerMessage(playerID, i);
            mess.setCoordinate(workerPositions.get(i));
            playPanelListener.firePropertyChange("workerReceived", null, mess);
        }
        submitButton.setEnabled(false);
        workerPlaced = true;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("playerID")) {
            playerID = (int) evt.getNewValue();
        } else if (evt.getPropertyName().equalsIgnoreCase("boardUpdate")) {
            VirtualSlot vSlot = (VirtualSlot) evt.getNewValue();
            // UPDATE BOARD
            boardOfButtons[vSlot.getCoordinate().getRow()][vSlot.getCoordinate().getCol()].updateView(vSlot);
        } else if (evt.getPropertyName().equalsIgnoreCase("firstPlayer")) {
            firstPlayerSelected = true;
            messagePlayerSettingWorkers();
        } else if (evt.getPropertyName().equalsIgnoreCase("workerConfirm")) {
            WorkerMessage message = (WorkerMessage) evt.getNewValue();
            if (message.getWorkerNumber() == 1
                    && modelView.getFirstPlayerId() == modelView.getActualPlayerId()) {
                play = true;
                if (playerID == modelView.getActualPlayerId()) {
                    //richiedi mosse disponibili
                    playPanelListener.firePropertyChange("actionRequest", false, true);
                }
            } else if (message.getWorkerNumber() == 1) {
                messagePlayerSettingWorkers();
            }
        } else if (evt.getPropertyName().equalsIgnoreCase("actionsReceived")) {
            if (playerID == modelView.getActualPlayerId()) {
                myTurn = true;
            }
            messageCenterTurn();

        } else if (evt.getPropertyName().equalsIgnoreCase("endTurnConfirm")) {
            modelView.getActionsAvailable().clear();
            if (playerID != modelView.getActualPlayerId()) {
                messageCenter.setText(modelView.getPlayer(modelView.getActualPlayerId()).getNickname() + " is playing, please wait 253");
            } else {
                playPanelListener.firePropertyChange("actionRequest", false, true);
            }
        }

        repaint();
    }

    private void messageCenterTurn() {
        if (modelView.getWinnerId() == -1) {
            if (playerID == modelView.getActualPlayerId()) {
                //show options
                List<String> choices = (List<String>) modelView.getActionChoices().clone();
                if (choices.size() == 1)
                    messageCenter.setText("It's your turn, make your " + choices.get(0)); //prob da poter togliere
                else {
                    if (choices.contains("end turn")) {
                        submitButton.setText("End turn");
                        submitButton.setName("End turn");
                        submitButton.setEnabled(true);
                    } else if (choices.contains("Build a dome")) {
                        submitButton.setText("Build a dome");
                        submitButton.setName("Build a dome");
                        submitButton.setForeground(Color.BLACK);
                        buildDome = false;
                        submitButton.setEnabled(true);
                    }
                    StringBuilder message = new StringBuilder("It's your turn, make your");
                    for (int i = 0; i < choices.size() - 1; i++) {
                        message.append(" " + choices.get(i));
                        message.append(" or ");
                    }
                    message.append(choices.get(choices.size() - 1));
                    messageCenter.setText(message.toString());
                }
            } else {
                messageCenter.setText(modelView.getPlayer(modelView.getActualPlayerId()).getNickname() + " is playing, please wait");
            }
        }
        repaint();
    }

    private void messagePlayerSettingWorkers() {
        if (modelView.getActualPlayerId() == playerID) {
            messageCenter.setText("Select your workers!");
        } else {
            String playing = modelView.getPlayer(modelView.getActualPlayerId()).getNickname();
            messageCenter.setText(playing + " is selecting his workers!");
        }
    }

    public void addPlayPanelListener(PropertyChangeListener listener) {
        playPanelListener.addPropertyChangeListener(listener);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.bgIsland, 0, 0, this);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                //boardOfButtons[i][j].repaint();
            }
        }
        if (!workerPlaced) {
            //east.repaint();
            //west.repaint();
        }
        Toolkit.getDefaultToolkit().sync();
        g.drawImage(banner, 0, 0, this);

    }


    //this class extends transfer handler and enables components to drag and drop elements
    protected class DragAndDrop extends TransferHandler {
        /*
            EXPORT
         */

        @Override
        public int getSourceActions(JComponent c) {
            return DnDConstants.ACTION_MOVE;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            Transferable t;
            if (c instanceof TileButton)
                System.out.println("CREO TRANSFERABLE");
            t = new TransferableImage(((TileButton) c).getWorker(), ((TileButton) c).getCoordinate());
            return t;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            if (action != NONE) {
                repaint();
                if (source instanceof TileButton) {
                    TileButton tSource = (TileButton) source;
                    tSource.setWorker(null);
                    tSource.setOpaque(false);
                    if (!workerPlaced) {
                        if (!tSource.getCoordinate().equals(new Coordinate(-1, -1)))
                            workerPositions.remove(tSource.getCoordinate());
                        else tSource.setTransferHandler(null);
                        submitButton.setEnabled(workerPositions.size() == 2);
                        System.out.println("WORKERS SETTED = " + workerPositions.size());
                    } else if (play) {
                        //PLAY
                        if (myTurn) {

                        }
                    }
                }
                repaint();
            }
        }
        /*
            IMPORT
         */

        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.imageFlavor);
        }

        @Override
        public boolean importData(TransferSupport support) {
            boolean dragAndDropResult = false;
            if (!firstPlayerSelected) return false;
            if (canImport(support)) {
                try {
                    Transferable t = support.getTransferable();
                    Object val = t.getTransferData(DataFlavor.imageFlavor);
                    Component dest = support.getComponent();
                    if (val instanceof TransferableImage && dest instanceof TileButton && playerID == modelView.getActualPlayerId()) {
                        Coordinate sourceCoord = ((TransferableImage) val).getCoordinate();
                        TileButton tDest = (TileButton) dest;
                        boolean myWorker = false;
                        for (Coordinate c : workerPositions) {
                            if (c.equals(sourceCoord)) {
                                myWorker = true;
                                break;
                            }
                        }

                        if (!workerPlaced && (myWorker || sourceCoord.equals(new Coordinate(-1, -1)))) {
                            if (tDest.getWorker() != null || tDest.getCoordinate().equals(new Coordinate(-1, -1))
                                    || sourceCoord.equals(tDest.getCoordinate())
                                    || modelView.getBoard().getSlot(tDest.getCoordinate()).hasWorker()) { // Last condition MUST NOT be in the play else i.e. minotaur
                                return false;
                            }
                            workerPositions.add(tDest.getCoordinate());
                            // Setting the image of the button
                            if (!((TransferableImage) val).getCoordinate().equals(new Coordinate(-1, -1))) {
                                tDest.setWorker(((TransferableImage) val).getImage());
                            } else {
                                tDest.setWorker(new ImageIcon(this.getClass().getResource("/Colors/woker_inactive.png")).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
                            }

                            System.out.println(workerPlaced);
                            dragAndDropResult = true;

                        } else if (play) {
                            // PLAY
                            if (myTurn) {
                                Move attemptedMove = new Move(sourceCoord, tDest.getCoordinate());
                                List<Action> actions = (List<Action>) modelView.getActionsAvailable().clone();
                                for (Action a : actions) {
                                    if (a.equals(attemptedMove)) {
                                        dragAndDropResult = true;
                                        sendAction(attemptedMove);
                                    }
                                }
                            }
                        }
                    }
                } catch (
                        UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return dragAndDropResult;
        }
    }

    private void sendAction(Action attemptedAction) {
        playPanelListener.firePropertyChange("actionReceived", false, attemptedAction);
        myTurn = false;
        submitButton.setEnabled(false);
        modelView.getActionsAvailable().clear();
        playPanelListener.firePropertyChange("actionRequest", false, true);

    }


    class TransferableImage implements Transferable {
        private Image image;
        private Coordinate coord;

        public TransferableImage(Image image, Coordinate coord) {
            this.image = image;
            this.coord = coord;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.imageFlavor};
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }

        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (!DataFlavor.imageFlavor.equals(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return this;
        }

        public Coordinate getCoordinate() {
            return coord;
        }

        public Image getImage() {
            return image;
        }


    }
}
