package cn.jji8.KnapsackToGo2.abnormal;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
/**
 * 文件异常
 * */
public class FileException extends RuntimeException{
    IOException ioException;
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public FileException(String message,IOException ioException) {
        super(message);
        this.ioException = ioException;
    }
    /**
     * 获取sql异常
     * */
    public IOException getIOException(){
        return ioException;
    }
    @Override
    public String getMessage() {
        return ioException.getMessage()+"\n"+super.getMessage();
    }
    @Override
    public String toString() {
        return ioException.toString()+"\n"+super.toString();
    }
    @Override
    public void printStackTrace() {
        super.printStackTrace();
        ioException.printStackTrace();
    }
    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        ioException.printStackTrace(s);
    }
    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
        ioException.printStackTrace(s);
    }
}
