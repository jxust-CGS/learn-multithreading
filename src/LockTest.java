import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    public static void main(String[] args) {
        Servers s = new Servers();
        new LockThread(s).start();
        new LockThread(s).start();
        new LockThread(s).start();
    }
}
class LockThread extends Thread{
    private Servers s;

    public LockThread(Servers servers){
        this.s= servers;
    }

    @Override
    public void run() {
        super.run();
        s.doSomeThings();
    }
}
class Servers{
    private Lock lock = new ReentrantLock();
    public void doSomeThings(){
        lock.lock();
        for(int i=0;i<5;i++){
            System.out.println(Thread.currentThread().getName() + "|" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }
}