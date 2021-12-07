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

    public Window() {
        initGUI();
    }

    private void initGUI() {
        this.setSize(mWin_h, mWin_w);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Graph Algorithms");
        MenuBar menuBar = new MenuBar();
        Menu main_menu = new Menu("Menu");
        Menu Help_menu = new Menu("Help");
        menuBar.add(main_menu);
        menuBar.add(Help_menu);
        this.setMenuBar(menuBar);
        MenuItem item = new MenuItem("Load");
       // item1.addActionListener(this);
        main_menu.add(item);
        item = new MenuItem("Save");
       // item2.addActionListener(this);
        main_menu.add(item);
        item = new MenuItem("Draw");
     //   item3.addActionListener(this);
        main_menu.add(item);
        item = new MenuItem("Exit");
        //   item3.addActionListener(this);
        main_menu.add(item);

        main_menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
        rbMenuItem.setSelected(true);
        rbMenuItem.setMnemonic(KeyEvent.VK_R);
        group.add(rbMenuItem);



        JButton button = new JButton();
        JLabel label = new JLabel();
        label.setText("heelo");
        this.add(label);
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

    }
}