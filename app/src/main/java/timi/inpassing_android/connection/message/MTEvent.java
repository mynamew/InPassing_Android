package timi.inpassing_android.connection.message;

/** 
 * 事件的基类
 * @autor timi
 * create at 2017/7/12 13:54
 */
public class MTEvent {

    private String event;

    public MTEvent(String event){
        this.event = event;
    }

    public String getEvent(){
        return event;
    }
}
