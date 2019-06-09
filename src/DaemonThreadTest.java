public class DaemonThreadTest {
    public static void main(String[] args) {
        DeamonThread thread = new DeamonThread("A",100);
        DeamonThread thread1 = new DeamonThread("B",2);
        DeamonThread thread2 = new DeamonThread("C",4);
        thread.setDaemon(true);
        thread.start();
        thread1.start();
        thread2.start();
        System.out.println("Main 线程执行任务完成！");
    }
}
class DeamonThread extends Thread{
    private String name;
    private int k;
    public DeamonThread(String name,int k){
        this.name = name;
        this.k = k;
    }
    @Override
    public void run() {
        super.run();
        int i =0;
        while(i<k){
            System.out.println(this.name +" i="+i++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this.name + "线程任务执行完成");
    }
}