package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.actions.Build;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.VirtualSlot;
import it.polimi.ingsw.utils.messages.GenericMessage;
import it.polimi.ingsw.utils.messages.WorkerMessage;
import it.polimi.ingsw.view.GUIpackage.Components.CustomButton;
import it.polimi.ingsw.view.GUIpackage.Components.WorkerIcon;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Play panel.
 * is the actual game screen
 */
public class PlayPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final PropertyChangeSupport playPanelListener = new PropertyChangeSupport(this);
    private Image bgIsland = new ImageIcon(this.getClass().getResource("/IslandAnimation/island120.jpg")).getImage();
    int i = 0;
    boolean workerPlaced = false;
    boolean play = false;
    boolean myTurn = false;
    boolean firstPlayerSelected = false;
    private final ArrayList<Coordinate> workerPositions = new ArrayList<>();
    private final JLayeredPane layeredPane = new JLayeredPane();
    private final MakeSound music = new MakeSound();
    private final MakeSound music2 = new MakeSound();
    private final Logger logger = Logger.getLogger("playpanel");

    private final ImageIcon buildDomeIcon;
    private final ImageIcon buildDomeClicked;
    private final ModelView modelView;
    private int playerID;
    private Action attemptedAction;
    private final JPanel movementPanel;
    private final JPanel boardPanel;
    private final JPanel workerToSet = new JPanel();
    private final JLabel messageCenter;
    private final JButton submitButton = new CustomButton("/Gameplay/submit_workers");
    private final JButton domeButton = new JButton() {
        @Override
        public void repaint() {
        }
    };
    private final JButton endTurnButton = new CustomButton("/Gameplay/endturn");
    private Coordinate selected;
    private boolean buildDome = false;
    private final TileButton[][] boardOfButtons = new TileButton[5][5];
    private final Image banner = new ImageIcon(this.getClass().getResource("/Gameplay/messageCenter.jpg")).getImage().getScaledInstance(960, 70, Image.SCALE_SMOOTH);
    private final InfoPanel infoPanel;
    private Boolean seaAnimationCheck = true;

    /**
     * Instantiates a new Play panel.
     *
     * @param modelView the model view
     */
    public PlayPanel(ModelView modelView, HomePanel homePanel) {
        messageCenter = new JLabel();
        Font messageFont;
        try {
            messageFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")).deriveFont(Font.PLAIN, 35); //carica font
        } catch (IOException | FontFormatException e) {
            messageFont = messageCenter.getFont();
        }

        layeredPane.setPreferredSize(GUI.getDimension());


        homePanel.addHomePanelListener(music);
        homePanel.addHomePanelListener(music2);
        homePanel.addHomePanelListener(this);

        messageCenter.setFont(messageFont);
        messageCenter.setHorizontalAlignment(SwingConstants.CENTER);
        this.modelView = modelView;
        this.setLayout(new BorderLayout());
        TileButton east = new TileButton(-1, -1, this);
        TransferHandler dragAndDrop = new DragAndDrop();
        east.setWorker(new ImageIcon(this.getClass().getResource("/Colors/worker_inactive.png")).getImage().getScaledInstance(130, 200, Image.SCALE_SMOOTH));
        east.setTransferHandler(dragAndDrop);
        east.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                TransferHandler handle = button.getTransferHandler();
                handle.exportAsDrag(button, e, TransferHandler.MOVE);
            }
        });

        TileButton west = new TileButton(-1, -1, this);
        west.setWorker(new ImageIcon(this.getClass().getResource("/Colors/worker_inactive.png")).getImage().getScaledInstance(130, 200, Image.SCALE_SMOOTH));
        west.setTransferHandler(dragAndDrop);
        west.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                TransferHandler handle = button.getTransferHandler();
                handle.exportAsDrag(button, e, TransferHandler.MOVE);
            }

        });
        boardPanel = new JPanel(new GridLayout(5, 5, 10, 10));
        for (int row = 4; row >= 0; row--) {
            for (int col = 0; col < 5; col++) {
                boardOfButtons[row][col] = new TileButton(row, col, this);
                boardOfButtons[row][col].addActionListener(this);
                boardOfButtons[row][col].setTransferHandler(dragAndDrop);

                boardOfButtons[row][col].addMouseMotionListener(new MouseAdapter() {

                    @Override
                    public void mouseDragged(MouseEvent e) {
                        JButton button = (JButton) e.getSource();
                        TransferHandler handle = button.getTransferHandler();
                        handle.exportAsDrag(button, e, TransferHandler.MOVE);
                    }
                });

                boardPanel.add(boardOfButtons[row][col]);
            }
        }
        this.setOpaque(false);

        boardPanel.setBorder(BorderFactory.createEmptyBorder((int) (0.245 * GUI.getDimension().height), (int) (0.345 * GUI.getDimension().height),
                (int) (0.125 * GUI.getDimension().height), (int) (0.365 * GUI.getDimension().height)));
        boardPanel.setOpaque(false);
        boardPanel.setBounds(0, 0, GUI.getDimension().width, GUI.getDimension().height);
        layeredPane.add(boardPanel, JLayeredPane.PALETTE_LAYER);


        workerToSet.setLayout(new BoxLayout(workerToSet, BoxLayout.X_AXIS));

        workerToSet.setBounds(0, 0, GUI.getDimension().width, GUI.getDimension().height);
        west.setBorder(BorderFactory.createEmptyBorder(150, 150, 150, 150));
        east.setBorder(BorderFactory.createEmptyBorder(150, 150, 150, 150));
        west.setPreferredSize(new Dimension(135, 200));
        east.setPreferredSize(new Dimension(135, 200));

        workerToSet.add(Box.createHorizontalStrut(3));
        workerToSet.add(west);
        workerToSet.add(Box.createHorizontalGlue());
        workerToSet.add(east);
        workerToSet.add(Box.createHorizontalStrut(30));
        workerToSet.setOpaque(false);

        layeredPane.add(workerToSet, JLayeredPane.DRAG_LAYER);

        messageCenter.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        messageCenter.setForeground(Color.WHITE);
        messageCenter.setOpaque(false);
        messageCenter.setText("Godly player is selecting the first player");
        messageCenter.setBounds(0, 0, GUI.getDimension().width, GUI.getDimension().height);


        /*************** BUTTONS*********************/
        endTurnButton.setName("End turn");
        endTurnButton.addActionListener(this);
        endTurnButton.setVisible(false);
        submitButton.setName("submit");
        submitButton.addActionListener(this);
        submitButton.setVisible(false);
        JPanel eastButtons = new JPanel();
        eastButtons.setLayout(new BoxLayout(eastButtons, BoxLayout.Y_AXIS));
        eastButtons.add(Box.createVerticalStrut(500));
        eastButtons.add(endTurnButton);
        eastButtons.add(submitButton);
        eastButtons.add(Box.createVerticalStrut(50));
        eastButtons.setOpaque(false);

        infoPanel = new InfoPanel();
        infoPanel.setBounds(0, 0, GUI.getDimension().width, GUI.getDimension().height);
        layeredPane.add(infoPanel, JLayeredPane.POPUP_LAYER);


        buildDomeIcon = new ImageIcon(this.getClass().getResource("/Gameplay/build_dome.png"));
        buildDomeClicked = new ImageIcon(this.getClass().getResource("/Gameplay/build_dome_clicked.png"));
        domeButton.setName("Build a dome");
        domeButton.setVisible(false);
        domeButton.setOpaque(false);
        domeButton.setContentAreaFilled(false);
        domeButton.setBorder(null);
        domeButton.addActionListener(this);
        domeButton.setIcon(buildDomeIcon);
        JPanel westButtons = new JPanel();
        westButtons.setLayout(new BoxLayout(westButtons, BoxLayout.Y_AXIS));
        westButtons.add(Box.createVerticalStrut(50));
        westButtons.add(domeButton);
        westButtons.add(Box.createVerticalStrut(600));
        westButtons.setOpaque(false);

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.setBounds(0, 0, GUI.getDimension().width, GUI.getDimension().height);
        buttons.setOpaque(false);
        buttons.add(westButtons, BorderLayout.WEST);
        buttons.add(eastButtons, BorderLayout.EAST);
        buttons.add(messageCenter, BorderLayout.NORTH);
        layeredPane.add(buttons, JLayeredPane.PALETTE_LAYER);
        movementPanel = new JPanel(null);
        movementPanel.setBounds(0, 0, GUI.getDimension().width, GUI.getDimension().height);
        movementPanel.setOpaque(false);
        layeredPane.add(movementPanel, JLayeredPane.POPUP_LAYER);
        this.add(layeredPane);
        repaint();


    }


    /**
     * The type Info panel.
     * it is the panel where there
     * is the information of the gods
     * selected by the godly and the exit button
     */
    public class InfoPanel extends JPanel implements ActionListener {
        ArrayList<JButton> godsTabs;
        List<Image> godsInfos;
        ArrayList<Point> tabPosition;
        boolean showing = false;
        int idShowing = 0;
        JButton exit = new JButton();
        Font customFont;
        Image nameBase;

        /**
         * Instantiates a new Info panel.
         */
        protected InfoPanel() {
            super();
            this.setOpaque(false);
            this.setLayout(null);
            nameBase = new ImageIcon(getClass().getResource("/Gameplay/nameBase.png")).getImage();

        }

        /**
         * Create all the components
         * inside the panel
         */
        public void infoCreate() {
            this.setVisible(false);
            this.setBounds(0, 0, GUI.getDimension().width, GUI.getDimension().height);
            godsTabs = new ArrayList<>();
            godsInfos = new ArrayList<>();
            tabPosition = new ArrayList<>();


            this.add(exit);
            exit.setIcon(new ImageIcon(this.getClass().getResource("/Gameplay/exit.png")));
            exit.setBounds(0, 0, 21, 20);
            exit.setContentAreaFilled(false);
            exit.setOpaque(false);
            exit.setLocation(new Point(940, 0));
            exit.setBorder(null);
            exit.setName("exit");
            exit.addActionListener(this);

            int y = GUI.getDimension().height - 150;

            for (int v = 0; v < modelView.getPlayers().size(); v++) {
                ModelView.PlayerView p = modelView.getPlayer(v);
                godsInfos.add(new ImageIcon(getClass().getResource("/GodSelection/" + p.getGod()[0].toLowerCase() + " info.jpg")).getImage().getScaledInstance(212, 450, Image.SCALE_SMOOTH));

                JButton button = new JButton() {
                    Image godMiniature = new ImageIcon(getClass().getResource("/GodSelection/" + p.getGod()[0].toLowerCase() + ".png")).getImage().getScaledInstance(32, 46, Image.SCALE_SMOOTH);
                    Image tab = new ImageIcon(getClass().getResource("/Gameplay/tab.png")).getImage();

                    @Override
                    public void repaint() {
                    }

                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        g.drawImage(godMiniature, 4, 33, PlayPanel.this);
                        g.drawImage(tab, 0, 0, PlayPanel.this);
                    }
                };

                try {
                    customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")); //upload font
                } catch (FontFormatException | IOException e) {
                    logger.log(Level.WARNING, e.getMessage());
                }


                tabPosition.add(new Point(0, y));
                y -= 150;
                button.addActionListener(this);
                button.setBackground(new Color(0, 0, 0, 0));
                button.setName(Integer.toString(p.getId()));
                button.setBounds(tabPosition.get(v).x, tabPosition.get(v).y, 45, 110);
                button.setBorder(null);
                button.setContentAreaFilled(false);
                godsTabs.add(button);


                button.setOpaque(true);
                this.add(button);
            }
        }

        /**
         * manages interactions with clicks
         * on the buttons on this panel
         *
         * @param e the e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                if (((JButton) e.getSource()).getName().equalsIgnoreCase("exit")) {
                    PlayPanel.this.playPanelListener.firePropertyChange("PlayerToDisconnect", false, true);
                }

                int id = Integer.parseInt(((JButton) e.getSource()).getName());
                if (showing && id == idShowing) {
                    showing = false;
                    tabPosition.get(id).x -= 212;

                } else {
                    if (showing) {
                        tabPosition.get(idShowing).x -= 212;
                        godsTabs.get(idShowing).setLocation(tabPosition.get(idShowing));
                    }
                    idShowing = id;
                    showing = true;
                    tabPosition.get(id).x += 212;

                }
                godsTabs.get(id).setLocation(tabPosition.get(id));
            }
            PlayPanel.this.repaint();
        }

        /**
         * Paint component.
         * paint the image of god's infos
         *
         * @param g the g
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (showing) {
                g.drawImage(godsInfos.get(idShowing), 0, 270, PlayPanel.this);
                g.setColor(Color.WHITE);
                g.setFont(customFont.deriveFont(Font.PLAIN,25));
                g.drawImage(nameBase, 0, 225, PlayPanel.this);
                g.drawString(modelView.getPlayer(idShowing).getNickname(),40,257);

            }
        }

        /**
         * Repaint.
         */
        @Override
        public void repaint() {
        }
    }


    /**
     * manages interactions with clicks
     * on the buttons on this panel
     * @param e the e
     */
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
                submitButton.setVisible(false);
            } else if (button.getName().equalsIgnoreCase("end turn")) {
                endTurnButton.setVisible(false);
                modelView.getActionsAvailable().clear();
                endTurnButton.setVisible(false);
                playPanelListener.firePropertyChange("end turn", null, new GenericMessage());
            } else if (button.getName().equalsIgnoreCase("Build a dome")) {
                if (buildDome) {
                    buildDome = false;
                    domeButton.setIcon(buildDomeIcon);
                } else {
                    buildDome = true;
                    domeButton.setIcon(buildDomeClicked);
                }
            }
        }
        repaint();

    }

    /**
     * Handles the build action movement.
     *
     * @param t       the t
     * @param actions the actions
     */
    private void tryBuild(TileButton t, List<Action> actions) {
        for (Action a : actions) {
            if (a.getEnd().equals(t.getCoordinate())) {
                if (a.getActionName().equalsIgnoreCase("BUILD") || a instanceof Build) {
                    if (buildDome && a.getActionName().equalsIgnoreCase("BUILD A DOME")) {
                        sendAction(a);
                        music.playSound("/Sounds/domeBuild.wav", 0f, false);

                    } else if (!buildDome) {
                        sendAction(a);
                        if (modelView.getBoard().getSlot(a.getEnd()).getLevel() == 3)
                            music.playSound("/Sounds/domeBuild.wav", 0f, false);
                        else
                            music.playSound("/Sounds/normalBuild.wav", 0f, false);

                    }
                }
            }
        }
    }


    /**
     * Send workers after moving the in the desired positions.
     */
    private void sendWorkers() {
        for (int j = 0; j < 2; j++) {
            WorkerMessage mess = new WorkerMessage(playerID, j);
            mess.setCoordinate(workerPositions.get(j));
            playPanelListener.firePropertyChange("workerReceived", null, mess);
        }
        submitButton.setEnabled(false);
        workerPlaced = true;
    }

    /**
     * Property change.
     *
     * @param evt the evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("playerID")) {
            playerID = (int) evt.getNewValue();
        } else if (evt.getPropertyName().equalsIgnoreCase("boardUpdate")) {
            VirtualSlot vSlot = (VirtualSlot) evt.getNewValue();
            boardOfButtons[vSlot.getCoordinate().getRow()][vSlot.getCoordinate().getCol()].updateView(vSlot);
        } else if (evt.getPropertyName().equalsIgnoreCase("firstPlayer")) {
            firstPlayerSelected = true;
            messagePlayerSettingWorkers();
            infoPanel.infoCreate();
        } else if (evt.getPropertyName().equalsIgnoreCase("workerConfirm")) {
            WorkerMessage message = (WorkerMessage) evt.getNewValue();
            if (message.getWorkerNumber() == 1
                    && modelView.getFirstPlayerId() == modelView.getActualPlayerId()) {
                play = true;
                if (playerID == modelView.getActualPlayerId()) {
                    playPanelListener.firePropertyChange("actionRequest", false, true);
                }
            } else if (message.getWorkerNumber() == 1) {
                messagePlayerSettingWorkers();
            }
        } else if (evt.getPropertyName().equalsIgnoreCase("actionsReceived")) {
            if (playerID == modelView.getActualPlayerId()) {
                myTurn = true;
            }
            movementPanel.removeAll();
            if (playerID != modelView.getDeletedPlayerId()) messageCenterTurn();
        } else if (evt.getPropertyName().equalsIgnoreCase("endTurnConfirm")) {
            modelView.getActionsAvailable().clear();
            if (playerID == modelView.getActualPlayerId()) {
                playPanelListener.firePropertyChange("actionRequest", false, true);
            }
        } else if (evt.getPropertyName().equalsIgnoreCase("winnerDetected")) {
            if (modelView.getWinnerId() == playerID) {
                messageCenter.setForeground(new Color(255, 235, 140));
                WinningPanel p = new WinningPanel(this);
                layeredPane.add(p, JLayeredPane.POPUP_LAYER);
                p.startTransition();
            } else messageCenter.setForeground(new Color(255, 255, 255));
            messageCenter.setText("Game ended");
        } else if (evt.getPropertyName().equalsIgnoreCase("movementTransitionEnded")) {
            if (attemptedAction != null) {
                sendAction(attemptedAction);
                attemptedAction = null;
            }
        } else if (evt.getPropertyName().equalsIgnoreCase("playerHasLost")) {
            modelView.getActionsAvailable().clear();
            if (playerID == modelView.getActualPlayerId()) {
                playPanelListener.firePropertyChange("actionRequest", false, true);
            } else if (playerID == modelView.getDeletedPlayerId()) {
                messageCenter.setText("You have lost");
            }
        } else if (evt.getPropertyName().equalsIgnoreCase("SeaAnimation")) {
            this.seaAnimationCheck = (Boolean) evt.getNewValue();
        }

        repaint();
    }

    /**
     * Modifies the text in the Message center
     * during the game.
     */
    private void messageCenterTurn() {
        if (modelView.getWinnerId() == -1) {
            if (playerID == modelView.getActualPlayerId()) {
                //show options
                List<String> choices = (List<String>) modelView.getActionChoices().clone();
                if (choices.size() == 1) {
                    messageCenter.setForeground(new Color(255, 235, 140));
                    messageCenter.setText("It's your turn, make your " + choices.get(0));
                } else {
                    if (choices.contains("end turn") && modelView.isOptional()) {
                        endTurnButton.setEnabled(true);
                        endTurnButton.setVisible(true);
                    } else if (choices.contains("Build a dome")) {
                        domeButton.setIcon(buildDomeIcon);
                        buildDome = false;
                        domeButton.setEnabled(true);
                        domeButton.setVisible(true);
                    }
                    StringBuilder message = new StringBuilder("It's your turn, make your");
                    for (int k = 0; k < choices.size() - 1; k++) {
                        message.append(" " + choices.get(k).toLowerCase());
                        message.append(" or ");
                    }
                    message.append(choices.get(choices.size() - 1));
                    messageCenter.setForeground(new Color(255, 235, 140));
                    messageCenter.setText(message.toString());
                }
            } else {
                messageCenter.setForeground(Color.WHITE);
                messageCenter.setText(modelView.getPlayer(modelView.getActualPlayerId()).getNickname() + " is playing, please wait");
            }
            if (playerID == modelView.getDeletedPlayerId()) {
                messageCenter.setText("You have lost");
            }
        }
        repaint();
    }

    /**
     * Modifies the text in the Message center
     * during the workers setup.
     */
    private void messagePlayerSettingWorkers() {
        if (modelView.getActualPlayerId() == playerID) {
            messageCenter.setForeground(new Color(255, 235, 140));
            messageCenter.setText("Select your workers!");
        } else {
            messageCenter.setForeground(Color.WHITE);

            String playing = modelView.getPlayer(modelView.getActualPlayerId()).getNickname();
            messageCenter.setText(playing + " is selecting his workers!");
        }
    }

    /**
     * Add play panel listener.
     *
     * @param listener the listener
     */
    public void addPlayPanelListener(PropertyChangeListener listener) {
        playPanelListener.addPropertyChangeListener(listener);
    }


    /**
     * Send action and requests for new actions.
     *
     * @param attemptedAction the attempted action
     */
    private void sendAction(Action attemptedAction) {
        playPanelListener.firePropertyChange("actionReceived", false, attemptedAction);
        myTurn = false;
        if (buildDome) {
            buildDome = false;
            domeButton.setIcon(buildDomeIcon);
        }
        domeButton.setVisible(false);
        endTurnButton.setVisible(false);
        modelView.getActionsAvailable().clear();
        playPanelListener.firePropertyChange("actionRequest", false, true);

    }

    /**
     * Paint component.
     *
     * @param g the g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.bgIsland, 0, 0, this);
        Toolkit.getDefaultToolkit().sync();
        g.drawImage(banner, 0, 0, this);
    }


    /**
     * The type Drag and drop.
     * Extends Transfer Handler to support drag and
     * drop of an TransferableImage object.
     */
//this class extends transfer handler and enables components to drag and drop elements
    protected class DragAndDrop extends TransferHandler {
        /*
            EXPORT
         */

        /**
         * Gets source actions.
         * The actions that can be done with this transferHandler.
         *
         * @param c the c
         * @return the source actions
         */
        @Override
        public int getSourceActions(JComponent c) {
            return DnDConstants.ACTION_MOVE;
        }

        /**
         * Create transferable object, that is wanted to
         * be drag and dropped.
         *
         * @param c the source component
         * @return the transferable
         */
        @Override
        protected Transferable createTransferable(JComponent c) {
            Transferable t = null;
            if (c instanceof TileButton) {
                t = new TransferableImage(((TileButton) c).getWorker(), ((TileButton) c).getCoordinate());
            }
            return t;
        }

        /**
         * Export done.
         * The drag and drop has had a positive outcome.
         *
         * @param source the source of the drag and drop
         * @param data   the data
         * @param action the action
         */
        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            if (action != NONE) {
                repaint();
                if (source instanceof TileButton) {
                    TileButton tSource = (TileButton) source;
                    tSource.setWorker(null);
                    tSource.setOpaque(false);
                    if (!workerPlaced) {
                        if (!tSource.getCoordinate().equals(new Coordinate(-1, -1))) {
                            workerPositions.remove(tSource.getCoordinate());
                        } else {
                            tSource.getParent().remove(tSource);
                        }
                        if (workerPositions.size() == 2 && tSource.getCoordinate().equals(new Coordinate(-1, -1))) {
                            submitButton.setEnabled(true);
                            submitButton.setVisible(true);
                            layeredPane.remove(workerToSet);
                            infoPanel.setVisible(true);
                        }
                    }
                }
                repaint();
            }
        }
        /*
            IMPORT
         */

        /**
         * Can import boolean.
         *
         * @param support the support
         * @return true if the transferHandler supports
         * the flavor of the transferable
         */
        @Override
        public boolean canImport(TransferSupport support) {
            if (support.getTransferable().isDataFlavorSupported(new DataFlavor(TransferableImage.class, "transferableImage"))) {
                try {
                    if (support.getTransferable().getTransferData(new DataFlavor(TransferableImage.class, "transferableImage")) instanceof TransferableImage) {
                        return true;
                    }
                } catch (UnsupportedFlavorException | ClassCastException e) {
                    logger.log(Level.WARNING, e.getMessage());
                } catch (IOException e) {
                    logger.log(Level.WARNING, e.getMessage());
                    return false;
                }
            }
            return false;
        }


        /**
         * Handles the drag and drop action when it comes to the
         * destination of the drag.
         *
         * @param support the support
         * @return the outcome of the drag and drop action
         */
        @Override
        public boolean importData(TransferSupport support) {
            boolean dragAndDropResult = false;
            if (!firstPlayerSelected) return false;
            if (canImport(support)) {
                try {
                    Transferable t = support.getTransferable();
                    if (t == null) return false;
                    Object val = t.getTransferData(new DataFlavor(TransferableImage.class, "transferableImage"));
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
                                tDest.setWorker(new ImageIcon(this.getClass().getResource("/WorkersAnimation/inactive.png")).getImage());
                            }

                            dragAndDropResult = true;

                        } else if (play && myTurn) {
                            // PLAY
                            Move attemptedMove = new Move(sourceCoord, tDest.getCoordinate());
                            List<Action> actions = (List<Action>) modelView.getActionsAvailable().clone();
                            for (Action a : actions) {
                                if (a.equals(attemptedMove)) {
                                    Random r = new Random();
                                    dragAndDropResult = true;
                                    music.playSound("/Sounds/move" + r.nextInt(3) + ".wav", 0f, false);


                                    /*
                                     * MOVEMENT
                                     * */
                                    WorkerIcon icon = new WorkerIcon(modelView.getBoard().getSlot(sourceCoord).getColor().getName().toLowerCase());
                                    icon.setVisible(false);
                                    movementPanel.add(icon);
                                    myTurn = false;
                                    attemptedAction = attemptedMove;
                                    icon.startTransition(boardOfButtons[sourceCoord.getRow()][sourceCoord.getCol()].getLocation(), tDest.getLocation());
                                    icon.addWorkerIconListener(PlayPanel.this);
                                    /*
                                     * END OF MOVEMENT
                                     * */
                                }
                            }
                        }
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                    return false;
                }
            }
            return dragAndDropResult;
        }
    }

    /**
     * The type Transferable image is the object that can be
     * dragged and dropped.
     */
    private class TransferableImage implements Transferable {
        private final Image image;
        private final Coordinate coordinate;
        public DataFlavor dataFTranferable = new DataFlavor(PlayPanel.TransferableImage.class, "transferableImage");

        /**
         * Instantiates a new Transferable image.
         *
         * @param image the image
         * @param coord the coordinate of the source
         */
        public TransferableImage(Image image, Coordinate coord) {
            this.image = image;
            this.coordinate = coord;
        }

        /**
         * Get the flavor that represent the transferable.
         *
         * @return the data flavor [ ]
         */
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{dataFTranferable};
        }

        /**
         * Is data flavor supported boolean.
         *
         * @param flavor the flavor
         * @return true if the param is supported by this transferable
         */
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return dataFTranferable.equals(flavor);
        }

        /**
         * Gets transfer data.
         *
         * @param flavor the flavor
         * @return the transfer data
         * @throws UnsupportedFlavorException the unsupported flavor exception
         */
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (!dataFTranferable.equals(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return this;
        }

        /**
         * Gets coordinate.
         *
         * @return the coordinate
         */
        public Coordinate getCoordinate() {
            return coordinate;
        }

        /**
         * Gets image.
         *
         * @return the image
         */
        public Image getImage() {
            return image;
        }


    }


    /**
     * Start animation.
     */
    public void startAnimation() {
        if (seaAnimationCheck) {
            Thread seaAnimation = new Thread(() -> {
                int counter = 216;
                try {
                    while (true) {
                        while (counter < 320) {
                            Thread.sleep(50);
                            this.bgIsland = new ImageIcon(this.getClass().getResource("/seaAnimation/island0" + (counter) + ".jpg")).getImage();
                            counter++;
                            repaint();
                        }
                        while (counter > 216) {
                            Thread.sleep(50);
                            this.bgIsland = new ImageIcon(this.getClass().getResource("/seaAnimation/island0" + (counter) + ".jpg")).getImage();
                            counter--;
                            repaint();
                        }
                    }

                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }


            });
            seaAnimation.start();
        }
    }


    /**
     * Start sounds.
     */
    public void startSounds() {

        music2.playSound("/Sounds/environment.wav", -20f, true);

        Thread backgroundSounds = new Thread(() -> {
            Random r = new Random();
            while (modelView.getWinnerId() == -1) {
                try {
                    Thread.sleep(r.nextInt(60000) + 120000); //tra 2 e 3 min
                    music.playSound("/Sounds/environment" + r.nextInt(3) + ".wav", -5f, false);
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
            }
        });
        backgroundSounds.start();
    }

}