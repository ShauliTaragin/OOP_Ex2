package Gui;

import api.EdgeData;
import api.NodeData;
import implementaions.MyDWG;
import implementaions.MyDWGAlgo;
import implementaions.MyNode;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.*;

public class Window extends JFrame implements ActionListener {

    public static void main(String[] args) {
        Window window = new Window();
        window.setVisible(true);
    }

    boolean mDraw_pivot = false;
    boolean mMoving_point = false;
    private int kRADIUS = 5;
    private int mWin_h = 500;
    private int mWin_w = 500;
    private Image mBuffer_image;
    private Graphics mBuffer_graphics;
    private JMenuBar menuBar;
    private JMenu main_menu;
    private JMenu Help_menu;
    private JMenu editor;
    private JMenu Algorithms;
    private JMenuItem load, save , show;
    private JMenuItem AddNode;
    private JMenuItem RemoveNode;
    private JMenuItem Connect;
    private JMenuItem RemoveEdge;
    private JMenuItem center;
    private JMenuItem isConnected;
    private JMenuItem tsp;
    private JMenuItem shortestDist;
    private JMenuItem shortestDistPath;
    private JMenuItem exit;
    private MyDWGAlgo best_algo;
    private double scale_lon;
    private double scale_lat;
    public Window() {
        initGUI();
    }

    private void initGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Graph Algorithms");
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(size.width / 2, size.height / 2);
        this.setVisible(true);
        this.menuBar = new JMenuBar();
        this.main_menu = new JMenu("Menu");
        this.Help_menu = new JMenu("Help");
        this.menuBar.add(this.main_menu);
        this.menuBar.add(this.Help_menu);
        this.setJMenuBar(this.menuBar);
        this.load = new JMenuItem("Load");
        this.load.addActionListener(this);
        this.main_menu.add(this.load);
        this.save = new JMenuItem("Save");
        this.save.addActionListener(this);
        this.main_menu.add(this.save);
        this.show = new JMenuItem("Show");
        this.show.addActionListener(this);
        this.main_menu.add(this.show);
        this.editor = new JMenu("Edit Graph");
        this.editor.addActionListener(this);
        this.main_menu.add(this.editor);
        this.AddNode = new JMenuItem("AddNode");
        this.AddNode.addActionListener(this);
        this.editor.add(this.AddNode);
        this.RemoveNode = new JMenuItem("RemoveNode");
        this.RemoveNode.addActionListener(this);
        this.editor.add(this.RemoveNode);
        this.Connect = new JMenuItem("Connect");
        this.Connect.addActionListener(this);
        this.editor.add(this.Connect);
        this.RemoveEdge = new JMenuItem("RemoveEdge");
        this.RemoveEdge.addActionListener(this);
        this.editor.add(this.RemoveEdge);

        this.Algorithms = new JMenu("Algorithms");
        this.Algorithms.addActionListener(this);
        this.main_menu.add(this.Algorithms);

        this.center = new JMenuItem("Center");
        this.center.addActionListener(this);
        this.Algorithms.add(this.center);
        this.isConnected = new JMenuItem("IsConnected");
        this.isConnected.addActionListener(this);
        this.Algorithms.add(this.isConnected);
        this.tsp = new JMenuItem("TSP");
        this.tsp.addActionListener(this);
        this.Algorithms.add(this.tsp);
        this.shortestDist = new JMenuItem("ShortestDist");
        this.shortestDist.addActionListener(this);
        this.Algorithms.add(this.shortestDist);
        this.shortestDistPath = new JMenuItem("ShortestDistPath");
        this.shortestDistPath.addActionListener(this);
        this.Algorithms.add(this.shortestDistPath);

        this.exit = new JMenuItem("Exit");
        this.exit.addActionListener(this);
        this.main_menu.add(this.exit);

        this.main_menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
        rbMenuItem.setSelected(true);
        rbMenuItem.setMnemonic(KeyEvent.VK_R);
        group.add(rbMenuItem);


        JButton button = new JButton();
        JLabel label = new JLabel();
        //    label.setText("heelo");
        //    this.add(label);
    }


    public void paint(Graphics g) {
        // Create a new "canvas"
        mBuffer_image = createImage(mWin_w, mWin_h);
        mBuffer_graphics = mBuffer_image.getGraphics();

        // Draw on the new "canvas"
        paintComponents(mBuffer_graphics);

        // "Switch" the old "canvas" for the new one
        g.drawImage(mBuffer_image, 0, 0, this);
    }
    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        for (MyNode node : best_algo.getMyGraph().getNodes().values()) {
            g.setColor(Color.BLUE);
            g.fillOval((int) node.getNode().getLocation().x() - kRADIUS, (int) node.getNode().getLocation().y() - kRADIUS,
                    2 * kRADIUS, 2 * kRADIUS);

//            if (prev != null) {
//                g.setColor(Color.RED);
//                g.drawLine((int) p.x(), (int) p.y(),
//                        (int) prev.x(), (int) prev.y());
//
//                double dist = prev.distance3D(p);
//                g.drawString(String.format("%.2f", dist),
//                        (int) ((p.x() + prev.x()) / 2),
//                        (int) ((p.y() + prev.y()) / 2));
//            }

//            prev = p;
        }
        Iterator<EdgeData> edges =best_algo.getGraph().edgeIter();
        while(edges.hasNext()){
            EdgeData edge=edges.next();
            g.setColor(Color.RED);
                g.drawLine((int) best_algo.getGraph().getNode(edge.getSrc()).getLocation().x(), (int) best_algo.getGraph().getNode(edge.getSrc()).getLocation().y()
                        , (int) best_algo.getGraph().getNode(edge.getDest()).getLocation().x(), (int) best_algo.getGraph().getNode(edge.getDest()).getLocation().y());
        }

//        if (mDraw_pivot
//                && !mMoving_point) {
//            g.setColor(Color.BLUE);
//            g.fillOval((int) mPivot_point.x() - kRADIUS, (int) mPivot_point.y() - kRADIUS,
//                    2 * kRADIUS, 2 * kRADIUS);
//            if (prev != null) {
//                g.setColor(Color.RED);
//                g.drawLine((int) mPivot_point.x(), (int) mPivot_point.y(),
//                        (int) prev.x(), (int) prev.y());
//
//
//                float dist = (float)prev.distance3D(mPivot_point);
//                float font_size = (float) Math.max(10.0f, Math.pow(dist,1.5f) / 100);
//                font_size = Math.min(40.0f, font_size);
//
//                Font font = g.getFont().deriveFont(font_size);
//                g.setFont(font);
//                g.drawString(String.format("%.2f", dist), (int) ((mPivot_point.x() + prev.x()) / 2), (int) ((mPivot_point.y() + prev.y()) / 2));
//            }
//
//        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case ("Load"):
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("./data"));
                int response = fileChooser.showOpenDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    this.best_algo = new MyDWGAlgo(file.toString());
                    double Minx = Integer.MAX_VALUE;
                    double Miny = Integer.MAX_VALUE;
                    double Maxx = Integer.MIN_VALUE;
                    double Maxy = Integer.MIN_VALUE;
                    Iterator<NodeData> iterator = this.best_algo.getGraph().nodeIter();
                    while(iterator.hasNext()) {
                        if (iterator.next().getLocation().x()<Minx)Minx=iterator.next().getLocation().x();
                        if (iterator.next().getLocation().x()>Maxx)Maxx=iterator.next().getLocation().x();
                        if (iterator.next().getLocation().y()<Miny)Miny=iterator.next().getLocation().y();
                        if (iterator.next().getLocation().y()<Maxy)Maxy=iterator.next().getLocation().y();
                    }
                    double Absx = Math.abs(Minx-Maxx);
                    double Absy = Math.abs(Miny-Maxy);
                    this.scale_lon = (getWidth()/Absx)*0.7;
                    this.scale_lat = (getHeight()/Absy)*0.7;
                    repaint();
                }
                break;
            case ("Save"):
//                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setCurrentDirectory(new File("./data"));
//                int response = fileChooser.showSaveDialog(null);
                break;

            case ("Show"):

        }
    }
}