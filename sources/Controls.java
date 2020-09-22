import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controls extends JFrame {
    private Label mcL = new Label("arrival min:");
    private Label McL = new Label("arrival max:");
    private Label msL = new Label("serving min:");
    private Label MsL = new Label("serving max:");
    private JSlider maxC = new JSlider(0,20); // ClientSpawn
    private JSlider minC = new JSlider(0,20);
    private JSlider maxS = new JSlider(0,20); // ServiceTime
    private JSlider minS = new JSlider(0,20);
    Controls(final Shop test){
        setTitle("Main UI");
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new GridLayout(2,2));
        maxC.setMajorTickSpacing(5);
        maxC.setMinorTickSpacing(1);
        maxC.setPaintTicks(true);
        maxC.setPaintLabels(true);
        maxC.setExtent(50);
        maxC.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (!maxC.getValueIsAdjusting()){
                    test.setMaxArr(maxC.getValue());
                    System.out.println(test.maxArr);
                }
            }
        });
        minC.setMajorTickSpacing(5);
        minC.setMinorTickSpacing(1);
        minC.setPaintTicks(true);
        minC.setPaintLabels(true);
        minC.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (!minC.getValueIsAdjusting()){
                    test.setMinArr(minC.getValue());
                    System.out.println(test.minArr);}
            }
        });
        maxS.setMajorTickSpacing(5);
        maxS.setMinorTickSpacing(1);
        maxS.setPaintTicks(true);
        maxS.setPaintLabels(true);
        maxS.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (!maxS.getValueIsAdjusting()){
                    test.setMaxArr(maxS.getValue());
                    System.out.println(test.maxArr);
                }
            }
        });
        minS.setMajorTickSpacing(5);
        minS.setMinorTickSpacing(1);
        minS.setPaintTicks(true);
        minS.setPaintLabels(true);
        minS.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (!minS.getValueIsAdjusting()){
                    test.setMinArr(minS.getValue());
                    System.out.println(test.minArr);}
            }
        });
        add(mcL);
        add(minC);
        add(McL);
        add(maxC);
        add(msL);
        add(minS);
        add(MsL);
        add(maxS);
        pack();
    }
}
