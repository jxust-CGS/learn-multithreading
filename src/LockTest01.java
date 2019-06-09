import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest01 {
    public static void main(String[] args) throws InterruptedException {
        Server s = new Server();
        ConditionThread ct = new ConditionThread(s,"");//创建条件线程，对s对象操作
        ConditionThread ct1 = new ConditionThread(s,"notify");//创建唤醒线程，对s对象操作，唤醒一个s中等待中的线程
        ct.start();
        Thread.sleep(2000);//启动条件线程2秒后启动唤醒线程
        ct1.start();
    }
}
class ConditionThread extends Thread{
    private Server s;
    private String flag;
    public ConditionThread(Server s,String flag){
        this.s=s;
        this.flag = flag;
    }

    @Override
    public void run() {
        super.run();
        if(flag.equals("notify")){
            s.donotify();
        }else{
            s.doSomeThings();
        }
    }
}
class Server{
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void doSomeThings(){
        try {
            lock.lock();
            System.out.println("A"+System.currentTimeMillis());//条件线程启动后立刻输出A，然后就condition暂停
            condition.await();
            Date deadline = new Date(new Date().getTime()+5*1000);//当ct被ct2发起的信号唤醒后继续，为awaitUntil设置自动唤醒时间点
            System.out.println("B"+System.currentTimeMillis());//当ct被ct2发起的信号唤醒后继续，输出B，与A间隔2秒
            condition.awaitUntil(deadline);//再次暂停ct线程，直到被唤醒或时间等于deadline
            System.out.println("C"+System.currentTimeMillis());//没有接收到唤醒信号，直到deadline自动恢复执行，输出C
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void donotify(){
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}