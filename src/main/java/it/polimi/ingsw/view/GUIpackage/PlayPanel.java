package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.VirtualSlot;
import it.polimi.ingsw.utils.messages.WorkerMessage;
import it.polimi.ingsw.view.ModelView;

import javax.swing.*;
import java.awt.*;
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

public class PlayPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private PropertyChangeSupport playPanelListener = new PropertyChangeSupport(this);
    private Image bgIsland = new ImageIcon(this.getClass().getResource("/IslandAnimation/island120.jpg")).getImage();
    // private Image bgIsland  = new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png")).getImage().getScaledInstance(656,375,Image.SCALE_SMOOTH);


    boolean workerPlaced = false;
    private ArrayList<Coordinate> workerPositions = new ArrayList<>();

    private ModelView modelView;//da impostare
    private int playerID;//da impostare
    private String color;
    private JPanel boardPanel;
    private JLabel messageCenter = new JLabel("");
    private JButton submitButton = new JButton("Submit");
    private Coordinate toBeSendedWorker = new Coordinate(-1, -1);

    private TileButton boardOfButtons[][] = new TileButton[5][5];


    public PlayPanel(ModelView modelView) {
        this.modelView = modelView;
        this.setLayout(new BorderLayout());
        TileButton east = new TileButton(-1, -1);
        TransferHandler dragAndDrop = new DragAndDrop();
        east.setWorker(new ImageIcon(this.getClass().getResource("/Colors/blue_normal.png")).getImage());
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
        TileButton west = new TileButton(-1, -1);

        west.setWorker(new ImageIcon(this.getClass().getResource("/Colors/blue_normal.png")).getImage());
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
                boardOfButtons[row][col] = new TileButton(row, col);
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

                boardOfButtons[row][col].setBackground(new Color(0, 0, 0, 0));
                boardPanel.add(boardOfButtons[row][col]);
            }
        }
        this.setOpaque(false);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(165, 150, 90, 165));
        boardPanel.setOpaque(false);
        this.add(boardPanel, BorderLayout.CENTER);
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(Box.createVerticalStrut(200));
        east.setBorder(BorderFactory.createEmptyBorder(70, 0, 70, 0));
        p.add(east);
        p.setAlignmentY(1);
        p.setOpaque(false);
        this.add(p, BorderLayout.EAST);
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(Box.createVerticalStrut(200));
        west.setBorder(BorderFactory.createEmptyBorder(70, 0, 70, 0));

        p.add(west);
        p.setOpaque(false);
        this.add(p, BorderLayout.WEST);
        this.add(messageCenter, BorderLayout.NORTH);
        submitButton.setName("submit");
        submitButton.setEnabled(false);
        this.add(submitButton, BorderLayout.SOUTH);
        repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.playerID != 4 && e.getSource().equals("tilebutton")) {
            TileButton t = (TileButton) e.getSource();

            {
                turnHandler(t.getCoordinate());
            }

        } else if (((JComponent) e.getSource()).getName().equalsIgnoreCase("submit")) {
            sendWorkers();
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
        if(evt.getPropertyName().equalsIgnoreCase("playerID")){
            playerID = (int)evt.getNewValue();
        } else if(evt.getPropertyName().equalsIgnoreCase("boardUpdate")){
            VirtualSlot vSlot = (VirtualSlot)evt.getNewValue();
            // UPDATE BOARD
            boardOfButtons[vSlot.getCoordinate().getRow()][vSlot.getCoordinate().getCol()].updateView(vSlot);
        }
    }

    public void addPlayPanelListener(PropertyChangeListener listener) {
        playPanelListener.addPropertyChangeListener(listener);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.bgIsland, 0, 0, this);
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
            Transferable t = null;
            if (c instanceof TileButton)
                System.out.println("CREO TRANSFERABLE");
            t = new TransferableImage(((TileButton) c).getWorker(), ((TileButton) c).getCoordinate());
            return t;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            if (action != NONE) {
                if (source instanceof TileButton) {
                    TileButton tSource = (TileButton) source;
                    tSource.setWorker(null);
                    tSource.repaint();
                    tSource.setOpaque(false);
                    if (!workerPlaced) {
                        if (!tSource.getCoordinate().equals(new Coordinate(-1, -1)))
                            workerPositions.remove(tSource.getCoordinate());
                        if (workerPositions.size() == 2) {
                            submitButton.setEnabled(true);
                        } else submitButton.setEnabled(false);

                    } else {
                        //PLAY
                    }
                }
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
            boolean bool = false;
            if (canImport(support)) {
                try {
                    Transferable t = support.getTransferable();
                    Object val = t.getTransferData(DataFlavor.imageFlavor);
                    Component dest = support.getComponent();
                    if (val instanceof TransferableImage && dest instanceof TileButton && playerID == modelView.getActualPlayerId()) {
                        TileButton tDest = (TileButton) dest;
                        if (!workerPlaced) {
                            if (tDest.getWorker() != null || tDest.getCoordinate().equals(new Coordinate(-1, -1))
                                    || ((TransferableImage) val).getCoordinate().equals(tDest.getCoordinate())
                                    || modelView.getBoard().getSlot(tDest.getCoordinate()).hasWorker()) { // Last condition MUST NOT be in the play else i.e. minotaur
                                return false;
                            }
                            workerPositions.add(tDest.getCoordinate());
                            // Setting the image of the button
                            if (!((TransferableImage) val).getCoordinate().equals(new Coordinate(-1, -1))) {
                                tDest.setWorker(((TransferableImage) val).getImage());
                            } else {
                                tDest.setWorker(new ImageIcon(this.getClass().getResource("/Home/exit_onmouse.png")).getImage());
                            }

                            System.out.println(workerPlaced);
                            bool = true;

                        } else {
                            // PLAY
                        }
                    }
                } catch (
                        UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
            }
            return bool;
        }
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

        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
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
