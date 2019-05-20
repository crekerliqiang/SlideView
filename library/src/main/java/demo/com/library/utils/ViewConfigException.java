package demo.com.library.utils;


/**
 * 自定义异常类
 * 做运行时异常的检测
 */
public class ViewConfigException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public ViewConfigException() {
        super();
    }

    public ViewConfigException(String msg){
        super(msg);
    }

}
