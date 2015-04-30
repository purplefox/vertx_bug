import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

public class ManyHandlers extends Verticle {

  Handler<Message<String>> foo = new Handler<Message<String>>() {
    @Override
    public void handle(Message<String> message) {
      System.out.println("Received message: " + message.body());
      message.reply("pong!");
    }
  };

  public void start() {
    for(int i=0; i<50; i++){
      vertx.eventBus().registerHandler("ping-address" + i, foo);
    }
  }
}
