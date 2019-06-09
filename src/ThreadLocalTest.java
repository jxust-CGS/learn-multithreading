public class ThreadLocalTest {

    public static void main(String[] args) throws InterruptedException {
        Tools.t2.set("Main");
        Thread a = new LocalThread();
        Thread b = new LocalThread();
        a.start();
        b.start();
        a.join();
        b.join();
        System.out.println(Tools.t2.get());
    }
}
class LocalThread extends Thread{

    @Override
    public void run() {
        super.run();
        if(Tools.t1.get().equals("初始值")){
            Tools.t1.set(Thread.currentThread().getName() + "-" + Math.random());
        }
        if(Tools.t2.get().equals("初始值")){
            Tools.t2.set(Thread.currentThread().getName() + "-" + Math.random());
        }
        if(Tools.k.equals("")){
            Tools.k = "111";
        }
        System.out.println(Thread.currentThread().getName() + "|" + Tools.t1.get());
        System.out.println(Thread.currentThread().getName() + "|" + Tools.t2.get());
        System.out.println(Thread.currentThread().getName() + "|" + Tools.k);
        Tools.t2.set(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName() + "|" + Tools.t2.get());
    }
}
class Tools{
    public static ThreadLocalExt t1 = new ThreadLocalExt();
    public static String k = "";
    public static InheritableThreadLocalExt t2 = new InheritableThreadLocalExt();
}
class ThreadLocalExt extends  ThreadLocal{
    @Override
    protected Object initialValue() {
        return "初始值";
    }
}
class InheritableThreadLocalExt extends InheritableThreadLocal{
    @Override
    protected Object initialValue() {
        return "初始值";
    }

    @Override
    protected Object childValue(Object parentValue) {
        return super.childValue(parentValue)+"-继承自父线程";
    }
}