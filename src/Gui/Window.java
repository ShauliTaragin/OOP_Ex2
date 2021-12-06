package Gui;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.JFrame;

public class Window extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

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

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        menuBar.add(menu);
        this.setMenuBar(menuBar);

        MenuItem item1 = new MenuItem("simpleTriangle");
        item1.addActionListener(this);

        MenuItem item2 = new MenuItem("clean-up");
        item2.addActionListener(this);

        menu.add(item1);
        menu.add(item2);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
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
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered");

    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited");
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}