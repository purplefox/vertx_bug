import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;
import java.util.Collection;
import java.io.Serializable;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import org.vertx.java.core.net.impl.ServerID;
import org.vertx.java.spi.cluster.impl.hazelcast.HazelcastServerID;

import java.io.IOException;

public class Hz {
  public static void main(String[] args) {
    ClientConfig clientConfig = new ClientConfig();
    clientConfig.addAddress("localhost:5701");
    HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
    MultiMap <String , Collection<ServerID>> map = client.getMultiMap("subs");
    System.out.println("Map Size:" + map.size());
    for ( String key : map.keySet() ){
      // Collection <String > values = map.get( key );
      // System.out.println( "%s -> %s\n", key, values );
      System.out.printf("%-35s", key);
      map.get(key);
      // Collection<ServerID> sid = map.get(key);
      System.out.println(map.get(key));
    }
    System.exit(0);
  }
}

