package cn.jji8.KnapsackToGo2.abnormal;


import java.io.IOException;

/**
 * 文件读取异常
 * */
public class AbnormalFileReading extends FileException{

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message     the detail message. The detail message is saved for
     *                    later retrieval by the {@link #getMessage()} method.
     * @param ioException
     */
    public AbnormalFileReading(String message, IOException ioException) {
        super(message, ioException);
    }
}
