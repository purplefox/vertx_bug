import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.apex.Router;
import io.vertx.ext.apex.handler.sockjs.SockJSHandler;
import io.vertx.ext.apex.handler.sockjs.BridgeOptions;

public class EventBusBridge extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    Router router = Router.router(vertx);
    SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
    BridgeOptions options = new BridgeOptions();
    sockJSHandler.bridge(options);
    router.route("/eventbus").handler(sockJSHandler);
    System.out.println("Bridge listening...");
  }
}
