mport com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;
import java.util.Collection;
import java.io.Serializable;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
// import org.vertx.java.core.net.impl.ServerID;

import java.io.IOException;

class ServerID implements Serializable {

  public int port;
  public String host;

  public ServerID(int port, String host) {
    this.port = port;
    this.host = host;
  }

  public ServerID() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || !(o instanceof ServerID)) return false;

    ServerID serverID = (ServerID) o;

    if (port != serverID.port) return false;
    if (!host.equals(serverID.host)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = port;
    result = 31 * result + host.hashCode();
    return result;
  }

  public String toString() {
    return host + ":" + port;
  }
}

class HazelcastServerID extends ServerID implements DataSerializable {
  public HazelcastServerID() {}
  public HazelcastServerID(ServerID serverID) { super(serverID.port, serverID.host);}

  @Override
  public void writeData(ObjectDataOutput dataOutput) throws IOException {
    dataOutput.writeInt(port);
    dataOutput.writeUTF(host);
  }

  @Override
  public void readData(ObjectDataInput dataInput) throws IOException {
    port = dataInput.readInt();
    host = dataInput.readUTF();
  }

  // We replace any ServerID instances with HazelcastServerID - this allows them to be serialized more optimally using
  // DataSerializable
  public static <V> V convertServerID(V val) {
    if (val.getClass() == ServerID.class) {
      ServerID sid = (ServerID)val;
      HazelcastServerID hsid = new HazelcastServerID(sid);
      return (V)hsid;
    } else {
      return val;
    }
  }
}

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
      System.out.println(key);
      map.get(key);
      // Collection<ServerID> sid = map.get(key);
      System.out.println(map.get(key));
    }
    System.exit(0);
  }
}

