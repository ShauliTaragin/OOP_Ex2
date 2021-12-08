package Gui;

import api.EdgeData;
import api.NodeData;
import implementaions.*;
import org.w3c.dom.Node;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
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
    private double Minx,Maxx,Maxy,Miny,Absx,Absy;
    private int kRADIUS = 5;
    private int mWin_h ;
    private int mWin_w ;
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
        this.mWin_w= (int)(size.width/1.5);
        this.mWin_h= (int)(size.height/1.2);
        System.out.println(mWin_w);
        System.out.println(mWin_h);
        this.setSize(this.mWin_w,this.mWin_h);
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
        //rbMenuItem.setMnemonic(KeyEvent.VK_R);
        group.add(rbMenuItem);

        JButton button = new JButton();
        JLabel label = new JLabel();
        //    label.setText("heelo");
        //    this.add(label);
    }


    public void paint(Graphics g) {
        // Create a new "canvas"
        mBuffer_image = createImage(getWidth(),getHeight());
        mBuffer_graphics = mBuffer_image.getGraphics();

        // Draw on the new "canvas"
        paintComponents(mBuffer_graphics);

        // "Switch" the old "canvas" for the new one
        g.drawImage(mBuffer_image, 0, 0, this);
    }
    @Override
    public void paintComponents(Graphics g) {
        if(this.best_algo == null)return;
        super.paintComponents(g);
        double theta;
        Iterator<EdgeData> edges =best_algo.getGraph().edgeIter();
        while(edges.hasNext()){
            EdgeData edge=edges.next();
            g.setColor(new Color(200,30,70));
            double x1 = (best_algo.getGraph().getNode(edge.getSrc()).getLocation().x()-this.Minx)*this.scale_lon+60;
            double y1 = (best_algo.getGraph().getNode(edge.getSrc()).getLocation().y()-this.Miny)*this.scale_lat+60;
            double x2 =(best_algo.getGraph().getNode(edge.getDest()).getLocation().x()-this.Minx)*this.scale_lon+60;
            double y2 = (best_algo.getGraph().getNode(edge.getDest()).getLocation().y()-this.Miny)*this.scale_lat+60;
           // g.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
            drawArrowLine(g,(int)x1,(int)y1,(int)x2,(int)y2,10,5);
            theta = Math.atan2(y2 - y1, x2 - x1);
            //drawArrow(g, theta, x2, y2);
            //arrHead (g,x1,y1,x2,y2);
        }
        for (MyNode node : best_algo.getMyGraph().getNodes().values()) {
            double x = (node.getNode().getLocation().x()-this.Minx)*(this.scale_lon)+60;
            double y = (node.getNode().getLocation().y()-this.Miny)*(this.scale_lat)+60;

            g.setColor(new Color(0,100,150));
            g.fillOval((int) x-kRADIUS, (int) y-kRADIUS , (int) (2 * kRADIUS), (int) (2 * kRADIUS));
            String id = "" + node.getNode().getKey();
            g.setColor(Color.BLACK);
            g.setFont( new Font(Font.DIALOG_INPUT, Font.BOLD| Font.PLAIN, 12));
            g.drawString(id , (int)x-kRADIUS,(int)y-kRADIUS);
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
    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {
                x2,
                (int) xm,
                (int) xn
        };
        int[] ypoints = {
                y2,
                (int) ym,
                (int) yn
        };
        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);

    }
//    public static final double angle = Math.PI/18;
//    public static final double len = 17;
//    private void arrHead (Graphics g,double x1, double y1, double x2, double y2)
//    {
//        double ax1,ay1, ax2, ay2;
//        double c,a,beta,theta,phi;
//        c = Math.sqrt ((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
//        if (Math.abs(x2-x1) < 1e-6)
//            if (y2<y1) theta = Math.PI/2;
//            else theta = - Math.PI/2;
//        else
//        { if (x2>x1)
//            theta = Math.atan ((y1-y2)/(x2-x1)) ;
//        else
//            theta = Math.atan ((y1-y2)/(x1-x2));
//        }
//        a = Math.sqrt (len*len  + c*c - 2*len*c*Math.cos(angle));
//        beta = Math.asin (len*Math.sin(angle)/a);
//        phi = theta - beta;
//        ay1 = y1 - a * Math.sin(phi);		// coordinates of arrowhead endpoint
//        if (x2>x1)
//            ax1 = x1 + a * Math.cos(phi);
//        else
//            ax1 = x1 - a * Math.cos(phi);
//        phi = theta + beta;				// second arrowhead endpoint
//        ay2 = y1 - a * Math.sin(phi);
//        if (x2>x1)
//            ax2 = x1 + a * Math.cos(phi);
//        else
//            ax2 = x1 - a * Math.cos(phi);
//        g.setColor(Color.black);
//        g.drawLine((int)x2,(int)y2,(int)ax1,(int) ay1);
//        g.drawLine((int)x2,(int)y2,(int)ax2,(int) ay2);    }
    private void drawArrow(Graphics g, double theta, double x0, double y0)
    {
        double barb =13;
        double phi = Math.PI/7;
        double x = x0 - barb * Math.cos(theta + phi);
        double y = y0 - barb * Math.sin(theta + phi);
        double x1,y1;
        g.setColor(Color.black);
        //g.drawLine((int) x0, (int)y0,(int) x, (int)y);
        x1 = x0 - barb * Math.cos(theta - phi);
        y1 = y0 - barb * Math.sin(theta - phi);
        //g.drawLine((int)x0,(int) y0, (int)x, (int)y);
        int[] xpoints = {
                (int) x1,
                (int) x0,
                (int) x
        };
        int[] ypoints = {
                (int)y1,
                (int) y0,
                (int) y
        };
        g.fillPolygon(xpoints,ypoints,3);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        mBuffer_image = createImage(mWin_w, mWin_h);
        mBuffer_graphics = mBuffer_image.getGraphics();
        this.getGraphics().drawImage(mBuffer_image, 0, 0, this);

        Container container = getContentPane();
        getContentPane().removeAll();
        container.setLayout(new FlowLayout());
        switch (e.getActionCommand()) {
            case ("Load"):
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("./data"));
                int response = fileChooser.showOpenDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    this.best_algo = new MyDWGAlgo(file.toString());
                    caclulate_minmax();
                    repaint();
                }
                break;
            case ("Save"):
//                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setCurrentDirectory(new File("./data"));
//                int response = fileChooser.showSaveDialog(null);
                break;
            case ("Show"):

                break;
            case ("AddNode"):
                if (this.best_algo != null) {
                    JLabel label_id = new JLabel("Enter id for Node");
                    JTextField text_id = new JTextField();
                    JLabel label_geoloc = new JLabel("Enter geo location for Node:");
                    JTextField text_geoloc = new JTextField();

                    JButton submit_button = new JButton("Submit");

                    text_id.setPreferredSize(new Dimension(250, 40));
                    text_geoloc.setPreferredSize(new Dimension(250, 40));
                    submit_button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try{
                                String[] geoloc = text_geoloc.getText().split(",");
                                int id = Integer.parseInt(text_id.getText());
                                GeoL geoL = new GeoL(Double.parseDouble(geoloc[0]), Double.parseDouble(geoloc[1]), 0.0);
                                best_algo.getGraph().addNode(new NodeD(id, geoL));
                                show_graph();
                            }
                            catch (Exception exception){
                                JLabel label_error = new JLabel("Error. Enter correct id and geolocation");
                                container.add(label_error);
                            }
                        }
                    });
                    container.add(label_id);
                    container.add(text_id);
                    container.add(label_geoloc);
                    container.add(text_geoloc);
                    container.add(submit_button);
                    this.setVisible(true);
                    break;
                }
        }
    }
    public void caclulate_minmax(){
        this.Minx = Integer.MAX_VALUE;
        this.Miny = Integer.MAX_VALUE;
        this.Maxx = Integer.MIN_VALUE;
        this.Maxy = Integer.MIN_VALUE;
        Iterator<NodeData> iterator = this.best_algo.getGraph().nodeIter();
        while(iterator.hasNext()) {
            NodeData node = iterator.next();
            if (node.getLocation().x()<this.Minx)this.Minx=node.getLocation().x();
            if (node.getLocation().x()>this.Maxx)this.Maxx=node.getLocation().x();
            if (node.getLocation().y()<this.Miny)this.Miny=node.getLocation().y();
            if (node.getLocation().y()>this.Maxy)this.Maxy=node.getLocation().y();
        }
        this.Absx = Math.abs(this.Minx-this.Maxx);
        this.Absy = Math.abs(this.Miny-this.Maxy);
        this.scale_lon = ((this.mWin_w-100)/Absx);
        this.scale_lat = ((this.mWin_h-100)/Absy);
    }
    public void show_graph(){
        mBuffer_image = createImage(mWin_w, mWin_h);
        mBuffer_graphics = mBuffer_image.getGraphics();
        this.getGraphics().drawImage(mBuffer_image, 0, 0, this);
        Container container = getContentPane();
        getContentPane().removeAll();
        container.setLayout(new FlowLayout());
        repaint();
    }
}
