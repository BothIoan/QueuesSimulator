import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RemoveButton extends Button {
    RemoveButton(final Queues t, final Shop test, String nume){
        super(nume);
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Client> aux= test.killThread(t);
                for(Client c: aux){
                    test.enterQuwu(c);
                }
            }
        });
    }
}
