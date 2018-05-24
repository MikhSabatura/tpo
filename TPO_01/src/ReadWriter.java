import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

public class ReadWriter {

    private final static int WRITE_MODE = 0;
    private final static int READ_MODE = 1;

    private final static int INT_LENGTH = 4;

    private RandomAccessFile file;
    private FileChannel channel;
    private MappedByteBuffer buf;

    public ReadWriter(String path) {
        try {
            this.file = new RandomAccessFile(path, "rw");
            this.channel = file.getChannel();
            this.buf = channel.map(FileChannel.MapMode.READ_WRITE, 0, 3 * INT_LENGTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReadWriter rw = new ReadWriter(args[0]);

        if (args[1].equals("read")) {
            rw.read();
        } else if (args[1].equals("write")) {
            rw.write();
        } else {
            System.out.println("Illegal arguments");
        }
    }

    public void read() {
        while (true) {
            buf.rewind();
            if (buf.getInt() == READ_MODE) {
                int sum = buf.getInt() + buf.getInt();
                System.out.println("sum = " + sum);
                buf.putInt(0, WRITE_MODE);
            } else {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void write() {
        Random random = new Random();
        while (true) {
            buf.rewind();
            if (buf.getInt() == WRITE_MODE) {
                buf.putInt(0, READ_MODE)
                        .putInt(random.nextInt())
                        .putInt(random.nextInt());
            } else {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
