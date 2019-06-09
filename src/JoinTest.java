public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread b = new ThreadB();
        Thread a = new ThreadA(b);
        //RunableC c = new RunableC(b);
        a.start();//a线程启动
        b.start();//b线程启动
        b.join(600);
        System.out.println("main end");
        //new Thread(c).start();
    }
}
class ThreadA extends Thread{
    Thread b;

    public ThreadA(Thread b){
        this.b= b;
    }

    public void run() {
        synchronized (b){
            System.out.println("RunableA run start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("RunableA run end");
        }
    }
}
class ThreadB extends Thread{
    @Override
    synchronized public void run() {
        System.out.println("RunableB run start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("RunableB run end");
    }

    synchronized public void doSomeThing(){
        System.out.println("Runable doSomeThing ...");
    }
}