import java.io.*;

public class PipeReaderTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        PipedReader input = new PipedReader();
        PipedWriter output = new PipedWriter();
        input.connect(output);
        Thread outputThread = new Thread(new Writer_char(output));
        outputThread.start();
        Thread.sleep(1000);
        Thread inputThread = new Thread(new Reader_char(input));
        inputThread.start();
    }
}
class Reader_char implements Runnable{
    private PipedReader input;
    public Reader_char(PipedReader input){
        this.input = input;
    }
    @Override
    public void run() {
        System.out.println("Reader start");
        try {
            char[] charArray = new char[20];
            int length = input.read(charArray);
            input.close();
            String k = new String(charArray,0,length);
            System.out.println("read result:"+k);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Reader end");
    }
}
class Writer_char implements Runnable{
    private PipedWriter output;
    public Writer_char(PipedWriter output){
        this.output = output;
    }
    @Override
    public void run() {
        System.out.println("Writer start");
        String k = "123456789123456789123456789";
        try {
            output.write(k);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Writer end");
    }
}