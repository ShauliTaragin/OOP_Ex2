package Gui;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;

public class Window extends JFrame implements ActionListener{

    public static void main(String[] args)
    {
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
    private JMenuItem load,save;
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




    public Window() {
        initGUI();
    }
    private void initGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Graph Algorithms");
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize( size.width/2, size.height/2);
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
        this.editor = new JMenu("Edit Graph");
        this.editor.addActionListener(this);
        this.main_menu.add(this.editor);
        this.AddNode= new JMenuItem("AddNode");
        this.AddNode.addActionListener(this);
        this.editor.add(this.AddNode);
        this.RemoveNode= new JMenuItem("RemoveNode");
        this.RemoveNode.addActionListener(this);
        this.editor.add(this.RemoveNode);
        this.Connect= new JMenuItem("Connect");
        this.Connect.addActionListener(this);
        this.editor.add(this.Connect);
        this.RemoveEdge= new JMenuItem("RemoveEdge");
        this.RemoveEdge.addActionListener(this);
        this.editor.add(this.RemoveEdge);

        this.Algorithms = new JMenu("Algorithms");
        this.Algorithms.addActionListener(this);
        this.main_menu.add(this.Algorithms);

        this.center = new JMenuItem("Center");
        this.center.addActionListener(this);
        this.Algorithms.add(this.center);
        this.isConnected= new JMenuItem("IsConnected");
        this.isConnected.addActionListener(this);
        this.Algorithms.add(this.isConnected);
        this.tsp= new JMenuItem("TSP");
        this.tsp.addActionListener(this);
        this.Algorithms.add(this.tsp);
        this.shortestDist= new JMenuItem("ShortestDist");
        this.shortestDist.addActionListener(this);
        this.Algorithms.add(this.shortestDist);
        this.shortestDistPath= new JMenuItem("ShortestDistPath");
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
        mBuffer_image = createImage(mWin_w,mWin_h );
        mBuffer_graphics = mBuffer_image.getGraphics();

        // Draw on the new "canvas"
        paintComponents(mBuffer_graphics);

        // "Switch" the old "canvas" for the new one
        g.drawImage(mBuffer_image, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionevent = e.toString();
    }
}