package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.controller.TurnHandler;
import it.polimi.ingsw.model.Coordinate;
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

public class PlayPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private PropertyChangeSupport playPanelListener = new PropertyChangeSupport(this);
    private Image bgIsland = new ImageIcon(this.getClass().getResource("/Home/island0217.jpg")).getImage();
    // private Image bgIsland  = new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png")).getImage().getScaledInstance(656,375,Image.SCALE_SMOOTH);


    int workerPlaced = 0;

    private ModelView modelView;//da impostare
    private int playerID;//da impostare
    private String color;
    private JPanel boardPanel;
    private JLabel messageCenter = new JLabel("");
    private JButton messageSouth = new JButton("Submit");
    private Coordinate toBeSendedWorker = new Coordinate(-1,-1);

    private TileButton boardOfButtons[][] = new TileButton[5][5];


    public PlayPanel(ModelView modelView) {
        this.modelView = modelView;
        this.setLayout(new BorderLayout());
        TileButton east = new TileButton(-1, -1);
        TransferHandler dragAndDrop = new DragAndDrop();
        east.setWorker(new ImageIcon(this.getClass().getResource("/Home/blue_normal_res.png")).getImage());
        east.setTransferHandler(dragAndDrop);
        east.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                TransferHandler handle = button.getTransferHandler();
                handle.exportAsDrag(button, e, TransferHandler.MOVE);
            }
        });
        messageSouth.addActionListener(this);
        TileButton west = new TileButton(-1, -1);

        west.setWorker(new ImageIcon(this.getClass().getResource("/Home/blue_normal_res.png")).getImage());
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
        messageSouth.setName("submit");
        this.add(messageSouth, BorderLayout.SOUTH);
        repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.playerID !=4 && e.getSource().equals("tilebutton")) {
            TileButton t = (TileButton) e.getSource();

            {
                turnHandler(t.getCoordinate());
            }

        } else if(((JComponent)e.getSource()).getName().equalsIgnoreCase("submit")){
            System.out.println("non è il tuo turno coglione");
            if(workerPlaced!=0);


        }


    }

    private void turnHandler(Coordinate coordinate) {


    }

    private void sendWorkers(Coordinate c) {
        if (modelView.getBoard().getSlot(c).isFree()) {
            workerPlaced++;
            boardOfButtons[c.getRow()][c.getCol()].setColor(color);
            this.playPanelListener.firePropertyChange("workerReceived", null, c);
        } else System.out.println("è occupato");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

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
                    if (val instanceof TransferableImage && dest instanceof TileButton) {
                        TileButton tDest = (TileButton) dest;
                        if (tDest.getWorker() != null || tDest.getCoordinate().equals(new Coordinate(-1, -1)))
                            return false;
                        if (!((TransferableImage) val).getCoordinate().equals(tDest.getCoordinate()) &&
                                !((TransferableImage) val).getCoordinate().equals(new Coordinate(-1, -1))) {
                            tDest.setWorker(((TransferableImage) val).getImage());
                            bool = true;
                        } else if (((TransferableImage) val).getCoordinate().equals(new Coordinate(-1, -1)) && workerPlaced<2) {
                            tDest.setWorker(new ImageIcon(this.getClass().getResource("/Home/exit_onmouse.png")).getImage());
                            workerPlaced++;
                            System.out.println(workerPlaced);
                            bool = true;
                        }

                    } else System.out.println(val.getClass());
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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
