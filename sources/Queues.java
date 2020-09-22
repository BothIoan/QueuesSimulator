import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class Queues implements Runnable {
    private final int id;
    private int currentWaiting =0;
    private Future future;
    private BlockingQueue<Client> clients;
    private Casa visual;
    private RemoveButton visua2;
    private UI ui;
    private Logs l;
    private Shop parentShop;
    private Client current = null;
    private long currentServingStart = 0;
    private boolean sleepFlag = false;// might not be necessary
    public Queues(final int id, UI ui, Shop shop, Logs l) {// ceva initializare
        this.clients = new LinkedBlockingQueue<Client>();
        this.id = id;
        this.visual = new Casa(id);
        this.visua2 = new RemoveButton(this,shop,"Kill thread "+ id);
        this.ui=ui;
        this.l = l;
        this.parentShop = shop;
    }

    public void run(){
        int timp=0;
        try {
            current = clients.take();
            timp =current.getfinalTime();
            currentServingStart = (System.currentTimeMillis()/1000);
            if(parentShop.getState())l.write("Thread: " +this.id+ " started processing client "+ current.getSpawn());
           // System.out.println("Casa: " +this.id+ " started processing client "+ current.getSpawn());
            sleepFlag = true;
            Thread.sleep(current.getServing()* 1000);
            currentWaiting -= current.getServing();
            sleepFlag = false;
            visual.remove();
            visual.showInternalState();
            if(parentShop.getState())ui.pack();
            if(parentShop.getState())l.write("Thread: "+ this.id +" removed client "+current.getSpawn()+" in "+ timp +" timp");
            //System.out.println("Casa: "+ this.id +" removed client "+current.getSpawn()+" in "+ timp +" timp");
        }catch (InterruptedException ignored){}
    }

    public BlockingQueue<Client> getClientList() {
        return clients;
    }

    public void addClient(Client c) {
        c.addWaiting(currentWaiting);
        currentWaiting += c.getServing();
            this.clients.add(c);
            visual.add((int)c.getSpawn());
            visual.showInternalState();
            if(parentShop.getState()){ ui.pack();
            l.write("Client "+c.getSpawn()+" placed at thread "+this.id);}
    }

    public int getId(){
        return this.id;
    }

    public void saveFuture(Future f){
        this.future = f;
    }
    public Future getFuture(){
        return this.future;
    }
    public int getCurrentWaiting(){

        if(sleepFlag == true)
            return (currentWaiting + (int) (System.currentTimeMillis()/1000)- (int)currentServingStart);
        return currentWaiting;
    }
    public Casa getVisual() {
        return visual;
    }
    public RemoveButton getVisua2(){
        return visua2;
    }

    public void setCurrentWaiting(int currentWaiting) {
        this.currentWaiting = currentWaiting;
    }

    /* @Override
    public String toString() {
        String res = new String("");
        for (Client c : this.clients) {
            if (c != this.clients.get(0))
                res += "client number " + Integer.valueOf(this.clients.indexOf(c)).toString() + c.toString();
            else
                res += "first client is being served. Remaining time:" + c.getService_time() + "\n";
        }
        res += "\nTOTAL WAIT:" + Integer.valueOf(this.waiting_time).toString();
        return res;
    }*/
    public String scuffedToString() {
        if (current == null)return " Queue " + id + " closed"+ "\n";
        int servingElapsed = (int) (System.currentTimeMillis()/1000)- (int)currentServingStart;
        if (servingElapsed >current.getServing()) return " Queue" + id + "closed"+"\n";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nQueue" + id+ ": ");
        stringBuilder.append("("+current.getId()+","+current.getSpawn()+"," +(current.getServing() - servingElapsed+"); "));
        clients.forEach(x -> {
            stringBuilder.append(" (" + x.getId() + "," + x.getSpawn() + "," + x.getServing() + "); ");
        });
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
    public boolean check(){
        if (!clients.isEmpty()||sleepFlag) {
        return false;
        }
        return true;
    }
}