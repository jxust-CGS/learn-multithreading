import java.util.ArrayList;
import java.util.List;

public class ProducterCustomerTest {
    public static void main(String[] args) {
        List<String> wareHouse = new ArrayList<>();//模拟仓库
        Object lock = new Object();//锁对象，用于锁的对象监听
        Thread p, c;
        for (int i = 0; i < 5; i++) {//循环创建5个生产者和消费者
            p = new Thread(new Producter(wareHouse,lock));
            p.setName("Producter" + i);
            p.start();
            c = new Thread(new Customer(wareHouse,lock));
            c.setName("Customer" + i);
            c.start();
        }
    }

    /**
     * 工具方法打印当前仓库内容
     * @param wareHouse
     * @return
     */
    public static  String  printWare(List<String> wareHouse){
        String k = "";
        if(wareHouse.size()==0){
            k="\t当前仓库内容：{"+"空"+"}";
            return k;
        }
        for(String temp:wareHouse){
            k+=temp+",";
        }
        k="\t当前仓库内容：{"+k.substring(0,k.length()-1)+"}";
        return k;
    }
}

/**
 * 生产者对象
 */
class Producter implements Runnable {
    private List<String> ware;
    private Object lock;
    private Producter producter;

    public Producter(List<String> ware,Object lock) {
        this.ware = ware;
        this.lock = lock;
    }

    @Override
    public void run() {
        while(true){//保持生产者一直在运行
            synchronized (lock){//生产者争夺同步锁，开始执行生产，保证同一时间内只有一个生产者或消费者在运行
                if(ware.size()>=10){//仓库容量10，当超出容量则暂停当前线程，释放对lock对象的同步锁
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    String temp = Thread.currentThread().getName()+"-"+((int)(Math.random()*1000));
                    System.out.println(Thread.currentThread().getName()+">>>>>>>准备生产\t"+temp+ProducterCustomerTest.printWare(ware));
                    ware.add(temp);//生产者生产对象放入仓库
                    lock.notifyAll();//通知所有在等待lock同步锁的消费者和生产者
                }
            }
            //为了能够体现多生产者之间合作完成生产，暂停当前生产者，将cpu释放给其他线程
            //Thread.yield();//这里释放cpu所有权效果不明显，仍然是当前线程争夺到了lock锁
            try {
                Thread.sleep((int)(Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 *  消费者
 */
class Customer implements Runnable {
    private List<String> ware;
    private Object lock;

    public Customer(List<String> ware,Object lock) {
        this.ware = ware;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true){//死循环保持消费者一直在运行
            synchronized (lock) {//当消费者争夺到了lock对象的同步锁，开始执行消费
                if (this.ware.size()==0){//当仓库内容为空时，暂停当前消费者线程，并释放lock对象同步锁
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println(Thread.currentThread().getName()+"-------准备消费\t"+ware.get(0)+ProducterCustomerTest.printWare(ware));
                    ware.remove(0);//消费者从仓库中消费掉第0个对象
                    lock.notifyAll();//发送通知给所有等待lock同步锁的暂停线程
                }
            }
            try {
                Thread.sleep((int)(Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
