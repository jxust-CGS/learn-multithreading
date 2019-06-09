public class MutilSyncTest {

    public static void main(String[] args) {
        MutilSyncMethod object = new MutilSyncMethod();
        Thread thread = new Thread(object);
        thread.start();

    }
}
class MutilSyncMethod implements Runnable{

    @Override
    public void run() {
        this.methoda();
    }

    synchronized public void methoda(){
        System.out.println("MethodA do");
        this.methodb();
    }

    synchronized public void methodb(){
        System.out.println("MethodB do");
    }

}