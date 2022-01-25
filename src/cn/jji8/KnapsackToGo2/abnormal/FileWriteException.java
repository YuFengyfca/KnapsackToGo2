package cn.jji8.KnapsackToGo2.abnormal;

import java.io.IOException;
/**
 * 文件写入异常
 * */
public class FileWriteException extends FileException{
    public FileWriteException(String message, IOException ioException) {
        super(message, ioException);
    }
}
