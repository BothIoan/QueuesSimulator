import javax.swing.*;

public class Logs extends JFrame {
    private JTextArea logs = new JTextArea();
    public void write(String s){
        logs.append("\n" +s);
        this.repaint();
        this.pack();
    }
    Logs(){
        setTitle("Logs");
        setLocationRelativeTo(null);
        setVisible(true);
        add(logs);
    }
}
