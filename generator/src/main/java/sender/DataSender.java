package sender;

/**
 * Created by Kyrylo_Kiprushev on 12/21/2017.
 */
public interface DataSender<T> {
    void send(T data);
}
