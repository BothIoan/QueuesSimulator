public class Client {

    private int  serving, waiting = 0 ;
    private long spawn;

    public int getId() {
        return id;
    }

    private final int id;
    public Client(int serving,long spawn,int id) {
        this.serving = serving;
        this.spawn= spawn;
        this.id = id ;
    }
    public void addWaiting(int waiting){
        this.waiting=waiting;
    }

    public int getServing() {
        return this.serving;
    }

    public int getWaiting() {
        return this.waiting;
    }

    public void setWaiting(int waiting) {
        this.waiting= waiting;
    }

    public long getSpawn(){
        return spawn;
    }

    public int getfinalTime()
    {
        return this.waiting + this.serving ;
    }


}


