import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Casa  extends JPanel {
    private final int id;
    Casa(int id){
        this.id=id;
        this.setLayout(new FlowLayout());
        add(new Label("casa: "+id));
    }
    private LinkedList<Label> coada = new LinkedList();
    public void add(int spawn){
        coada.add(new Label(" [ "+spawn+" ] "));
    }
    public void remove(){
        coada.removeFirst();
    }
    public void showInternalState(){
    this.removeAll();
    add(new Label("casa: "+id));
    for(Label aux : coada){
        this.add(aux);
    }
    repaint();
    }
    public void drain(){
        coada.clear();
    }
    public void removeLast(){
        coada.removeLast();
        this.removeAll();
        add(new Label("casa: "+id));
        for(Label aux: coada){
            this.add(aux);
        }
    }
}