import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI  extends JFrame {

    private JSlider Sim = new JSlider(0,1000);
    private Button startSim = new Button("Start");
    private Button stopSim = new Button("Stop");
    private Button addThread= new Button("Add new thread");

    private Label simL = new Label("simulation seconds:");
    private Shop test;
    //poate fac tot cu slider nr-u de threaduri // nu , poate add quwu , remove quwu
    private Panel clientControl = new Panel();
    private Panel serviceSpeed;
    private JPanel graphical= new JPanel();
    UI(){
        setTitle("Main UI");
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new GridLayout());
        startSim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                test = new Shop(UI.this,Sim.getValue());
                new Controls(test);
                graphical.setLayout(new GridLayout(10,2));
                graphical.add(test.getVisuals(0));
                graphical.add(test.getThread(0).getVisua2());
                graphical.add(test.getVisuals(1));
                graphical.add(test.getThread(1).getVisua2());
                graphical.add(test.getVisuals(2));
                graphical.add(test.getThread(2).getVisua2());
                graphical.repaint();
                pack();
            }
        });
        addThread.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Queues aux = test.addThread();
                test.startThread(aux);
                graphical.add(aux.getVisual());
                graphical.add(aux.getVisua2());
                graphical.repaint();
                pack();
            }
        });
        stopSim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                test.inchide();
            }
        });
        Sim.setMajorTickSpacing(200);
        Sim.setMinorTickSpacing(100);
        Sim.setPaintTicks(true);
        Sim.setPaintLabels(true);
        clientControl.add(simL);
        clientControl.add(Sim);
        add(graphical);
        add(clientControl);
        add(startSim);
        add(stopSim);
        add(addThread);
        pack();
        }
    public JPanel getgraphical(){
        return graphical;
    }

    }


