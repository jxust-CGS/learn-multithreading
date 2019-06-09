public class Main {

    public static void main(String[] args) {
        MyObject object = new MyObject();
        Thread t1 = new Thread() {
            public void run() {
                try {
                    object.setValue("chenguangsong", "password");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                super.run();
                object.printObject();
            }
        };
        t1.start();
        t2.start();
    }
}

class MyObject {
    private String name = "cgs";
    private String pass = "pass";

    synchronized public void setValue(String name, String pass) throws InterruptedException {
        System.out.println("setvalus start!");
        this.name = name;
        System.out.println("-----------------setName success");
        Thread.currentThread().sleep(3000);
        this.pass = pass;
        System.out.println("-----------------setPass success");
        System.out.println("setvalus over!");
    }

    public void printObject() {
        System.out.println("printObject start!");
        System.out.println(this.name + "|" + this.pass);
        synchronized (this) {
            System.out.println("name = " + name + " pass = " + pass);
        }
        System.out.println("printObject end!");

    }
}