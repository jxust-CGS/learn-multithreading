public class JoinTest2 {
    public static void main(String[] args) throws InterruptedException {
        Computer computer = new Computer();
        Thread a1 = new CalculateThread(computer);
        Thread a2 = new CalculateThread(computer);
        Thread a3 = new CalculateThread(computer);
        Thread a4 = new CalculateThread(computer);
        a1.start();
        a2.start();
        a3.start();
        a4.start();
        a1.join();
        a2.join();
        a3.join();
        a4.join();
        computer.printTotal();
    }
}
class CalculateThread extends Thread{
    private Computer c;

    public CalculateThread(Computer c){
        this.c = c;
    }

    @Override
    public void run() {
        super.run();
        c.calculatePanMenory();
    }
}
class Computer{
    private int total_memory = 0;
    public void calculatePanMenory(){
        int temp = (int) (Math.random() * 10000);
        total_memory += temp;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"计算加"+temp);
    }
    public void printTotal(){
        System.out.println("total_memory = " + total_memory);
    }
}