package Gui;

import api.EdgeData;
import api.NodeData;
import implementaions.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

public class Window extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

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
    private JMenuItem load, save , clear;
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
    public ArrayList<NodeData> path;
    public Double dist;
    public int nodeKey;
    public Window() {
        initGUI();
    }

    private void initGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Graph Algorithms");
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.mWin_w= (int)(size.width/1.5);
        this.mWin_h= (int)(size.height/1.2);
        this.setSize(this.mWin_w,this.mWin_h);
        this.setVisible(true);
        this.menuBar = new JMenuBar();
        this.main_menu = new JMenu("Menu");
        this.Help_menu = new JMenu("Help");
        this.Help_menu.setIcon(new ImageIcon("src\\Gui\\help.png"));
        this.clear = new JMenuItem("Clear Drawings");
        this.clear.addActionListener(this);
        this.clear.setIcon(new ImageIcon("src\\Gui\\Eraser-icon.png"));
        this.menuBar.add(this.main_menu);
        this.menuBar.add(this.Help_menu);
        this.menuBar.add(this.clear);
        this.setJMenuBar(this.menuBar);
        this.load = new JMenuItem("Load");
        this.load.setIcon(new ImageIcon(("src\\Gui\\load.png")));
        this.load.addActionListener(this);
        this.main_menu.add(this.load);
        this.save = new JMenuItem("Save");
        this.save.setIcon(new ImageIcon(("src\\Gui\\save.png")));
        this.save.addActionListener(this);
        this.main_menu.add(this.save);
        this.editor = new JMenu("Edit Graph");
        this.editor.addActionListener(this);
        this.editor.setIcon(new ImageIcon(("src\\Gui\\Pencil-icon.png")));
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
        this.Algorithms.setIcon(new ImageIcon(("src\\Gui\\graph-icon.png")));
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
        this.path=null;
        this.dist=Double.MAX_VALUE;
        this.nodeKey= Integer.MAX_VALUE;

        this.exit = new JMenuItem("Exit");
        this.exit.addActionListener(this);
        this.exit.setIcon(new ImageIcon(("src/Gui/logout-icon.png")));
        this.main_menu.add(this.exit);
        this.main_menu.addSeparator();

        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
        rbMenuItem.setSelected(true);
        group.add(rbMenuItem);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
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
        Graphics2D g2d = (Graphics2D) g;
        Iterator<EdgeData> edges =best_algo.getGraph().edgeIter();
        while(edges.hasNext()){
            EdgeData edge=edges.next();
            g.setColor(new Color(200,30,70));
            double x1 = (best_algo.getGraph().getNode(edge.getSrc()).getLocation().x()-this.Minx)*this.scale_lon+60;
            double y1 = (best_algo.getGraph().getNode(edge.getSrc()).getLocation().y()-this.Miny)*this.scale_lat+60;
            double x2 =(best_algo.getGraph().getNode(edge.getDest()).getLocation().x()-this.Minx)*this.scale_lon+60;
            double y2 = (best_algo.getGraph().getNode(edge.getDest()).getLocation().y()-this.Miny)*this.scale_lat+60;
            drawArrowLine(g2d,(int)x1,(int)y1,(int)x2,(int)y2,8,6);
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
        if(this.path!=null){
            for(int i=0;i<this.path.size()-1;i++){
                g.setColor(new Color(90,50,60));
                double x1 = (this.path.get(i).getLocation().x()-this.Minx)*this.scale_lon+60;
                double y1 = (this.path.get(i).getLocation().y()-this.Miny)*this.scale_lat+60;
                double x2 =(this.path.get(i+1).getLocation().x()-this.Minx)*this.scale_lon+60;
                double y2 = (this.path.get(i+1).getLocation().y()-this.Miny)*this.scale_lat+60;
                EdgeData edge = best_algo.getMyGraph().getEdge(this.path.get(i).getKey(),this.path.get(i+1).getKey());
                g2d.setStroke(new BasicStroke(3));
                drawArrowLine(g2d,(int)x1,(int)y1,(int)x2,(int)y2,8,6);
                g.fillOval((int) x1-kRADIUS, (int) y1-kRADIUS , (int) (2 * kRADIUS), (int) (2 * kRADIUS));
                g.setColor(new Color(50,200,70));

            }
        }
        if(this.nodeKey!=Integer.MAX_VALUE){
            double x1 = (this.best_algo.getGraph().getNode(this.nodeKey).getLocation().x()-this.Minx)*this.scale_lon+60;
            double y1 = (this.best_algo.getGraph().getNode(this.nodeKey).getLocation().y()-this.Miny)*this.scale_lat+60;
            g.setColor(Color.CYAN);
            g.fillOval((int) (x1-1.5*kRADIUS), (int) (y1-1.5*kRADIUS) , (int) (3 * kRADIUS), (int) (3 * kRADIUS));
        }
        this.path=null;
        this.dist=Double.MAX_VALUE;
        this.nodeKey=Integer.MAX_VALUE;
    }
    private void drawArrowLine(Graphics2D g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - kRADIUS, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;
        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;
        double newX2 = (xm+xn)/2;
        double newY2 = (ym+yn)/2;
        double dx1 = newX2 - x1, dy1 = newY2 - y1;
        double D1 = Math.sqrt(dx1 * dx1 + dy1 * dy1);
        double xm1 = D1 - d, xn1 = xm1, ym1 = h, yn1 = -h, nx;
        double sin1 = dy1 / D1, cos1 = dx1 / D1;
        nx = xm1 * cos1 - ym1 * sin1 + x1;
        ym1 = xm1 * sin1 + ym1 * cos1 + y1;
        xm1 = nx;
        nx = xn1 * cos1 - yn1 * sin1 + x1;
        yn1 = xn1 * sin1 + yn1 * cos1 + y1;
        xn1 = nx;
        int[] xpoints = {
                (int)newX2,
                (int) xm1,
                (int) xn1
        };
        int[] ypoints = {
                (int)newY2,
                (int) ym1,
                (int) yn1
        };
        g.drawLine(x1, y1, (int)newX2, (int)newY2);
        g.setFont( new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
        g.fillPolygon(xpoints, ypoints, 3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mBuffer_image = createImage(mWin_w, mWin_h);
        mBuffer_graphics = mBuffer_image.getGraphics();
        this.getGraphics().drawImage(mBuffer_image, 0, 0, this);
        Container container = getContentPane();
        getContentPane().removeAll();
        container.setLayout(new FlowLayout());
        this.path=null;
        this.dist=Double.MAX_VALUE;
        this.nodeKey= Integer.MAX_VALUE;
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
            case ("Clear Drawings"):
                if (this.best_algo!=null) {
                    show_graph(null, this.dist , this.nodeKey);
                    this.setVisible(true);
                }
                break;
            case ("Exit"):
                dispose();
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
                        public void actionPerformed(ActionEvent e)  {
                            try{
                                String[] geoloc = text_geoloc.getText().split(",");
                                int id = Integer.parseInt(text_id.getText());
                                GeoL geoL = new GeoL(Double.parseDouble(geoloc[0]), Double.parseDouble(geoloc[1]), 0.0);
                                best_algo.getGraph().addNode(new NodeD(id, geoL));
                                show_graph(null,Double.MAX_VALUE,Integer.MAX_VALUE);
                            }
                            catch (Exception exception){
                                JFrame f=new JFrame();
                                JOptionPane.showMessageDialog(f,"Error, invalid arguments.\nEnter correct id and geo location");
//                                JLabel label_error = new JLabel("Error. Enter correct id and geolocation");
//                                container.add(label_error);
                                setVisible(true);
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
            case ("RemoveNode"):
                if (this.best_algo != null) {
                    JLabel label_id = new JLabel("Enter id for Node to remove");
                    JTextField text_id = new JTextField();
                    JButton submit_button = new JButton("Submit");
                    text_id.setPreferredSize(new Dimension(250, 40));
                    submit_button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)  {
                            try{
                                int id = Integer.parseInt(text_id.getText());
                                best_algo.getGraph().removeNode(id);
                                show_graph(null,Double.MAX_VALUE,Integer.MAX_VALUE);
                            }
                            catch (Exception exception){
                                JFrame f=new JFrame();
                                JOptionPane.showMessageDialog(f,"Error, invalid arguments.\nEnter correct id");
//                                container.add(label_error);
                                setVisible(true);
                            }
                        }
                    });
                    container.add(label_id);
                    container.add(text_id);
                    container.add(submit_button);
                    this.setVisible(true);
                    break;
                }
            case ("RemoveEdge"):
                if (this.best_algo != null) {
                    JLabel label_id = new JLabel("Enter id for src of Edge");
                    JTextField text_id_src = new JTextField();
                    JLabel label_geoloc = new JLabel("Enter id for dst of Edge");
                    JTextField text_id_dst = new JTextField();
                    JButton submit_button = new JButton("Submit");
                    text_id_src.setPreferredSize(new Dimension(250, 40));
                    text_id_dst.setPreferredSize(new Dimension(250, 40));
                    submit_button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)  {
                            try{
                                int id_src = Integer.parseInt(text_id_src.getText());
                                int id_dst = Integer.parseInt(text_id_dst.getText());
                                best_algo.getGraph().removeEdge(id_src , id_dst);
                                show_graph(null,Double.MAX_VALUE,Integer.MAX_VALUE);
                            }
                            catch (Exception exception){
                                JFrame f=new JFrame();
                                JOptionPane.showMessageDialog(f,"Error, invalid arguments.\nEnter correct id for src and dst");
//                                container.add(label_error);
                                setVisible(true);
                            }
                        }
                    });
                    container.add(label_id);
                    container.add(text_id_src);
                    container.add(label_geoloc);
                    container.add(text_id_dst);
                    container.add(submit_button);
                    this.setVisible(true);
                    break;
                }
            case ("Connect"):
                if (this.best_algo != null) {
                    JLabel id_src = new JLabel("Enter id for src Node");
                    JTextField text_id_src = new JTextField();
                    JLabel id_dst = new JLabel("Enter id for dst Node");
                    JTextField text_id_dst = new JTextField();
                    JLabel weight = new JLabel("Enter a positive weight for new edge");
                    JTextField text_weight = new JTextField();
                    JButton submit_button = new JButton("Submit");
                    text_id_src.setPreferredSize(new Dimension(100, 20));
                    text_id_dst.setPreferredSize(new Dimension(100, 20));
                    text_weight.setPreferredSize(new Dimension(100, 20));
                    submit_button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)  {
                            try{
                                int id_src = Integer.parseInt(text_id_src.getText());
                                int id_dst = Integer.parseInt(text_id_dst.getText());
                                double weight = Double.parseDouble(text_weight.getText());
                                best_algo.getGraph().connect(id_src , id_dst , weight);
                                show_graph(null,Double.MAX_VALUE,Integer.MAX_VALUE);
                            }
                            catch (Exception exception){
                                JFrame f=new JFrame();
                                JOptionPane.showMessageDialog(f,"Error, invalid arguments.\nEnter correct id for src and dst");
//                                container.add(label_error);
                                setVisible(true);
                            }
                        }
                    });
                    container.add(id_src);
                    container.add(text_id_src);
                    container.add(id_dst);
                    container.add(text_id_dst);
                    container.add(weight);
                    container.add(text_weight);
                    container.add(submit_button);
                    this.setVisible(true);
                    break;
                }
            case ("ShortestDist"):
                if (this.best_algo != null) {
                    JLabel label_src = new JLabel("Enter id for source Node");
                    JTextField text_src = new JTextField();
                    JLabel label_dest = new JLabel("Enter id for destination Node:");
                    JTextField text_dest = new JTextField();
                    JButton submit_button = new JButton("Submit");
                    text_src.setPreferredSize(new Dimension(250, 40));
                    text_dest.setPreferredSize(new Dimension(250, 40));
                    submit_button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)  {
                            JLabel label_ans=new JLabel();
                            try{
                                int dest = Integer.parseInt(text_dest.getText());
                                int src = Integer.parseInt(text_src.getText());
                                double dist=best_algo.shortestPathDist(src,dest);
                                label_ans.setText("The shortest dist from "+" "+src+" "+"to "+" "+dest+" "+"is:"+dist);
                                container.add(label_ans);
                                setVisible(true);
                            }
                            catch (Exception exception){
                                JFrame f=new JFrame();
                                JOptionPane.showMessageDialog(f,"Error, no path between source and dest.\nEnter correct src and dest");
//                                JLabel label_error = new JLabel("Error, no path between source and dest.\n Enter correct src and dest");
//                                container.add(label_error);
                                setVisible(true);
                            }
                        }
                    });
                    container.add(label_src);
                    container.add(text_src);
                    container.add(label_dest);
                    container.add(text_dest);
                    container.add(submit_button);
                    this.setVisible(true);
                    break;
                }
            case ("Center"):
                if (this.best_algo != null) {
                    try {
                        this.nodeKey = this.best_algo.center().getKey();
                        show_graph(null, Double.MAX_VALUE, this.nodeKey);
                    } catch (Exception exception) {
                        JFrame f = new JFrame();
                        JOptionPane.showMessageDialog(f, "Error, invalid arguments.\nEnter correct id and geo location");
//                                JLabel label_error = new JLabel("Error. Enter correct id and geolocation");
//                                container.add(label_error);
                        this.setVisible(true);
                    }
                    this.setVisible(true);
                    break;
                }
            case ("ShortestDistPath"):
                if (this.best_algo != null) {
                    JLabel label_src = new JLabel("Enter id for source Node");
                    JTextField text_src = new JTextField();
                    JLabel label_dest = new JLabel("Enter id for destination Node:");
                    JTextField text_dest = new JTextField();

                    JButton submit_button = new JButton("Submit");

                    text_src.setPreferredSize(new Dimension(250, 40));
                    text_dest.setPreferredSize(new Dimension(250, 40));
                    submit_button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)  {
                            try{
                                int dest = Integer.parseInt(text_dest.getText());
                                int src = Integer.parseInt(text_src.getText());
                                ArrayList<NodeData> temp=(ArrayList<NodeData>) best_algo.shortestPath(src,dest);
                                show_graph(temp,Double.MAX_VALUE,Integer.MAX_VALUE);
                            }
                            catch (Exception exception){
                                JFrame f=new JFrame();
                                JOptionPane.showMessageDialog(f,"Error, no path between source and dest.\nEnter correct src and dest");
//                                JLabel label_error = new JLabel("Error, no path between source and dest.\n Enter correct src and dest");
//                                container.add(label_error);
                                setVisible(true);
                            }
                        }
                    });
                    container.add(label_src);
                    container.add(text_src);
                    container.add(label_dest);
                    container.add(text_dest);
                    container.add(submit_button);
                    this.setVisible(true);
                    break;
                }
            case ("TSP"):
                if (this.best_algo != null) {
                    JLabel label_id = new JLabel("Enter Node id");
                    JTextField text_id = new JTextField();
                    JButton add_button = new JButton("ADD");
                    text_id.setPreferredSize(new Dimension(250, 40));
                    // text_dest.setPreferredSize(new Dimension(250, 40));
                    ArrayList<NodeData> nodes=new ArrayList<>();
                    add_button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                int id = Integer.parseInt(text_id.getText());
                                text_id.setText("");
                                nodes.add(best_algo.getGraph().getNode(id));
                            } catch (Exception exception) {
                                JFrame f = new JFrame();
                                JOptionPane.showMessageDialog(f, "Error, no path between source and dest.\nEnter correct src and dest");
//                                JLabel label_error = new JLabel("Error, no path between source and dest.\n Enter correct src and dest");
//                                container.add(label_error);
                                setVisible(true);
                            }
                        }
                    });
                    this.path=nodes;
                    ArrayList<NodeData> nodes1=this.path;
                    JButton find_button = new JButton("Find tsp");
                    find_button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                show_graph((ArrayList<NodeData>) best_algo.tsp(nodes1),Double.MAX_VALUE,Integer.MAX_VALUE);
                            }
                            catch (Exception exception) {
                                JFrame f = new JFrame();
                                JOptionPane.showMessageDialog(f, "Error, no path between source and dest.\nEnter correct src and dest");
                                setVisible(true);
                            }
                        }
                    });
                    container.add(label_id);
                    container.add(text_id);
                    container.add(find_button);
                    container.add(add_button);
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
    public void show_graph(ArrayList<NodeData> path,double dist,int nodeKey){
        mBuffer_image = createImage(mWin_w, mWin_h);
        mBuffer_graphics = mBuffer_image.getGraphics();
        this.getGraphics().drawImage(mBuffer_image, 0, 0, this);
        Container container = getContentPane();
        getContentPane().removeAll();
        container.setLayout(new FlowLayout());
        this.path=path;
        this.dist=dist;
        this.nodeKey=nodeKey;
        repaint();
    }
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.best_algo != null) {
            for (MyNode node : best_algo.getMyGraph().getNodes().values()) {
                double x_scale = (node.getNode().getLocation().x() - this.Minx) * (this.scale_lon) + 60;
                double y_scale = (node.getNode().getLocation().y() - this.Miny) * (this.scale_lat) + 60;
                e.getX();
                e.getY();
                x_scale -=kRADIUS;
                y_scale -=kRADIUS;
                if ((Math.abs(x_scale- e.getX())<=kRADIUS) && (Math.abs(y_scale- e.getY())<=kRADIUS)){
                    drawgeo(getGraphics(), (GeoL) node.getNode().getLocation() , x_scale , y_scale);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    private void drawgeo(Graphics g, GeoL location , double x , double y ) {
        // g.drawString(id , (int)x-kRADIUS,(int)y-kRADIUS);
        g.setColor(Color.black);
        g.drawString("" + location.x() + "," + location.y(), (int) x - kRADIUS*2, (int) y - kRADIUS*2);
        setVisible(true);
    }


    @Override
    public void mouseExited(MouseEvent e) {

    }
}
