import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipeStreamTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        PipedInputStream input = new PipedInputStream();
        PipedOutputStream output = new PipedOutputStream();
        input.connect(output);
        Thread outputThread = new Thread(new Write(output));
        outputThread.start();
        //Thread.sleep(1000);
        outputThread.join();
        Thread inputThread = new Thread(new Reader(input));
        inputThread.start();

    }
}
class Reader implements Runnable{

    private PipedInputStream input;

    public Reader(PipedInputStream input){
        this.input = input;
    }

    @Override
    public void run() {
        System.out.println("Reader start");
        try {
            byte[] byteArray = new byte[20];
            int length = input.read(byteArray);
            String k = new String(byteArray,0,length);
            System.out.println("read result:"+k);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Reader end");
    }
}

class Write implements Runnable{
    private PipedOutputStream output;
    public Write(PipedOutputStream output){
        this.output = output;
    }
    @Override
    public void run() {
        System.out.println("Writer start");
        String k = "123456789123456789123456789";
        try {
            output.write(k.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Writer end");
    }
}