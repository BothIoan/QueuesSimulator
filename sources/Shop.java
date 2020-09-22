import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Shop implements Runnable{
    private FileFunctions fileFunctions;
    public int minArr;
    public int maxArr;
    private int minSer;
    private int maxSer;
    private int SimMax;
    private int peak=0;
    private int timeForFileLogs = 0;
    private int aux = 0;
    private int counter = 0;// ?
    private long timeZero;
    private boolean simTimeOverFlag = false;
    private  int id=0;//?
    private  int idClient = 0;//?
    private  ArrayList<Queues> cozi = new ArrayList<Queues>();//?
    private  ScheduledThreadPoolExecutor magazinu = new ScheduledThreadPoolExecutor(4);//?
    private  ScheduledThreadPoolExecutor fileLogs = new ScheduledThreadPoolExecutor(1);//?
    private UI ui;
    private Future mainFuture;
    private Logs l;
    long avg = 0;
    private  boolean flagUIorTEXT;//?
    private boolean staticClientsFlag = false;
    private ArrayList<Client> staticClients = new ArrayList<>();
    public void setMaxSer(int maxSer) {
        this.maxSer = maxSer;
    }

    public void setMaxArr(int maxArr) {
        this.maxArr = maxArr;
    }

    public void setMinArr(int minArr) {
        this.minArr = minArr;
    }

    public void setMinSer(int minSer) {
        this.minSer = minSer;
    }

    public boolean getState(){return flagUIorTEXT;} //?
    public Shop (ArrayList<Integer> params,String name, boolean staticClientsF, FileFunctions fileFunctionsParam){
        fileFunctions = fileFunctionsParam;
        staticClientsFlag = staticClientsF;
        flagUIorTEXT = false;
        fileFunctions.initFile(name);
        timeZero = System.currentTimeMillis();
        SimMax = params.get(2);
        setMinArr(params.get(3));
        setMaxArr(params.get(4));
        setMinSer(params.get(5));
        setMaxSer(params.get(6));
        if (staticClientsFlag) genClientsStatic(params.get(0));
        magazinu.scheduleAtFixedRate(this,0,1,TimeUnit.MILLISECONDS);
        for (int i = params.get(1);i!=0;--i) startThread(addThread());
        mainFuture= fileLogs.scheduleAtFixedRate(new Thread(()->{
            StringBuilder log = new StringBuilder();
            log.append("\nTime" + timeForFileLogs);
            if(staticClientsFlag) {

                ArrayList<Client> toAdd = (ArrayList<Client>) staticClients.stream().filter(x->x.getSpawn() <=timeForFileLogs).collect(Collectors.toList());
                staticClients.removeAll(toAdd);
                while(!toAdd.isEmpty()) {
                    Client aux = toAdd.get(0);
                    toAdd.remove(0);
                    enterQuwu(aux);
                }

            }
            avg +=  getAverageWaiting();
            ++timeForFileLogs;
            if(staticClientsFlag&&!staticClients.isEmpty())
            {
                log.append("\nWaiting clients: ");
                staticClients.forEach(x->log.append("("+x.getId()+","+x.getSpawn()+","+x.getServing()+"); "));
                log.append("\n");
            }
            cozi.forEach(x->{log.append(x.scuffedToString());});
            fileFunctions.writeInFile(log.toString());
            if((System.currentTimeMillis()-timeZero)/1000>=SimMax) simTimeOverFlag= true;
            if(simTimeOverFlag){
                cozi.stream().filter(x->x.check()).forEach(x->x.getFuture().cancel(true));
                cozi.removeIf(x->x.check());
                if (cozi.isEmpty()) {
                    fileFunctions.writeInFile("\n\nSimulation over(average = "+avg/SimMax +")");
                    try {
                        fileFunctions.commitFile();
                    } catch (IOException ignored) { }
                    mainFuture.cancel(true);
                    magazinu.shutdown();
                    fileLogs.shutdown();
                }

        }
        }),0,1000,TimeUnit.MILLISECONDS);
    }
    public Shop(UI ui,int simMax){//un initialize. Incepe automat cu un thread pt magazin care inca nu mi clar ce face. + 3 queue threaduri
        flagUIorTEXT=true;
        this.ui = ui;
        setMaxSer(20);
        setMinSer(5);
        setMaxArr(20);
        setMinArr(5);
        l = new Logs();
        timeZero = System.currentTimeMillis();
        this.SimMax = simMax;
        magazinu.scheduleAtFixedRate(this,0,1,TimeUnit.MILLISECONDS);//poate se aduna o milisecunda undeva unde nu trebe
        startThread(addThread());
        startThread(addThread());
        startThread(addThread());
       // computeCurrentMinimum(); not sure why I did this .
    }

    private void genClientsStatic (int nrClients){
        for (;nrClients!=0;--nrClients){
            staticClients.add(new Client(getRandomValue(maxSer,minSer),getRandomValue(maxArr,minArr),idClient));
            ++idClient;
        }
    }

    public void run() {//asta ii thread-u principal cu magazinu'. asteapta arive time-u care-i random,
        try{
            if(!simTimeOverFlag&&!staticClientsFlag){
        Thread.sleep((getRandomValue(maxArr,minArr)*1000)-1);// -1 to compensate for the period (which can't be 0);
            enterQuwu(new Client(getRandomValue(maxSer,minSer),(System.currentTimeMillis()-timeZero)/1000,idClient));
            ++idClient;
        }
        if(flagUIorTEXT&&(System.currentTimeMillis()-timeZero)/1000>=SimMax)inchide(); // tre sa vad ce fac cu inchideu
        }catch (InterruptedException e){}
        if(peak<getCurrentMaximum()){
            peak = getCurrentMaximum();
        }
        counter++;
        aux+= getAverageWaiting();
    }
    public int getRandomValue(int above , int below){
        return (int) (Math.random()*(above-below)+below);
    }
    public Queues addThread(){
         Queues aux = new Queues(id++,ui,this,l);
         cozi.add(aux);
        return aux;
    }
    public void enterQuwu(Client c){
    Queues aux = cozi.stream().min(Comparator.comparingInt(Queues::getCurrentWaiting)).orElse(null);
            //cozi.get(computeCurrentMinimum()); Old painful way
    aux.addClient(c);
    }
    public ArrayList<Client> killThread(Queues t){
        ArrayList<Client> toreturn= new ArrayList();
        t.getVisual().removeAll();
        BlockingQueue<Client> aux = t.getClientList();
        if(!aux.isEmpty())t.getVisual().add((int)aux.peek().getSpawn());
        while (!aux.isEmpty()){
            System.out.println(aux.peek().getSpawn());
            toreturn.add(aux.peek());
            aux.remove();
        }
        t.getVisual().showInternalState();
        ui.pack();
        t.getFuture().cancel(true);
        cozi.remove(t);
        magazinu.remove(t);
        ui.getgraphical().remove(t.getVisual());
        ui.getgraphical().remove(t.getVisua2());
        ui.getgraphical().repaint();
        ui.pack();
        return  toreturn;
    }
    public void startThread(Queues t){
        Future f =magazinu.scheduleAtFixedRate(t,0,1, TimeUnit.MILLISECONDS);
        t.saveFuture(f);
        if(flagUIorTEXT)l.write("New thread with id: "+ t.getId());

    }

    /*
    this was the sad life before I knew of streams


    public int computeCurrentMinimum(){
        int position = 0;
       // int currentMinimum = Integer.MAX_VALUE;
        //int index;
        /*
        for (index=0;index<cozi.size();index++) {
            int aux = cozi.get(index).getCurrentWaiting();
            if(aux<currentMinimum) {
                currentMinimum= aux;
                position = index;
            }
        }
        return cozi.stream().min;
    }

    */

    public Casa getVisuals(int index){
        return cozi.get(index).getVisual();
    }
    public Queues getThread(int index){
        return cozi.get(index);
    }
    public void inchide(){
        while(!cozi.isEmpty()){
           killThread(cozi.get(0));
        }
        ui.getgraphical().repaint();
        ui.repaint();
        ui.pack();
        id=0;
        timeZero=System.currentTimeMillis();
        l.write("Simulation ended");
        //System.out.println("Simulation ended");
        new Results(peak,aux/counter);
    }
    public int getAverageWaiting(){
        int aux=0 ;
        for(Queues t: cozi){
            aux +=t.getCurrentWaiting();
        }
        return aux/cozi.size();

    }
    public int getCurrentMaximum(){
        int position = 0;
        int currentMaximum = Integer.MIN_VALUE;
        int index;
        for (index=0;index<cozi.size();index++) {
            int aux = cozi.get(index).getCurrentWaiting();
            if(aux>currentMaximum) {
                currentMaximum= aux;
                position = index;
            }
        }
        return currentMaximum;
    }
}

