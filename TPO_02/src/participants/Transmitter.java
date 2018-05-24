package participants;

import exceptions.Assignment02Exception;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public abstract class Transmitter {

    private static final int BUF_SIZE = 1024;
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final char END_OF_INPUT_MARK = '\n';

    private ByteBuffer byteBuf = ByteBuffer.allocate(BUF_SIZE);
    private CharBuffer charBuf;

    protected void sendMessage(SocketChannel channel, String message) {
        ByteBuffer sendBuf = CHARSET.encode(CharBuffer.wrap(message + END_OF_INPUT_MARK));
        try {
            channel.write(sendBuf);
        } catch (IOException e) {
            throw new Assignment02Exception(e);
        }
    }

    protected String receiveMessage(SocketChannel channel) {
        if (!channel.isOpen()) {
            return null;
        }

        StringBuilder strBuilder = new StringBuilder();
        while (true) {
            try {
                byteBuf.clear();
                int readBytes = channel.read(byteBuf);
                if (readBytes == -1) {
                    return strBuilder.length() == 0 ? null : strBuilder.substring(0, strBuilder.length() - 1);
                } else if (readBytes > 0) {
                    byteBuf.flip();
                    charBuf = CHARSET.decode(byteBuf);
                    strBuilder.append(charBuf);
                } else if (endReached(strBuilder)) {
                    break;
                }
            } catch (IOException e) {
                throw new Assignment02Exception(e);
            }
        }
        return strBuilder.substring(0, strBuilder.length() - 1);
    }

    //checks if the end of input has been reached
    private boolean endReached(CharSequence input) {
        if (input.length() < 1) {
            return false;
        }
        return input.charAt(input.length() - 1) == END_OF_INPUT_MARK;
    }


}
