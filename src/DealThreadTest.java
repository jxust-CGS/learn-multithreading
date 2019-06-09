public class DealThreadTest {
    public static void main(String[] args) throws InterruptedException {
        DealThread dealThread = new DealThread();
        dealThread.setFlag("one");
        Thread thread1 = new Thread(dealThread);
        thread1.start();
        Thread.sleep(100);
        dealThread.setFlag("two");
        Thread thread2 = new Thread(dealThread);
        thread2.start();
    }
}
class DealThread extends Thread{
    Object a = new Object();
    Object b = new Object();
    private String flag;

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        super.run();
        if(this.flag.equals("one")){
            this.methodA();
        }else{
            this.methodB();
        }
    }

    public void methodA(){
        synchronized (a){
            System.out.println(this.flag + "线程锁定了a对象");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (b){
                System.out.println(this.flag + "线程锁定了b对象");
            }
            System.out.println(this.flag + "线程执行结束");
        }
    }

    public void methodB(){
        synchronized (b){
            System.out.println(this.flag + "线程锁定了b对象");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (a){
                System.out.println(this.flag + "线程锁定了a对象");
            }
            System.out.println(this.flag + "线程执行结束");
        }
    }
}
