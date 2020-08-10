package me.saiintbrisson.voidserver.network.connection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionRegistry {

    private static final ConnectionRegistry INSTANCE = new ConnectionRegistry();
    public static ConnectionRegistry getInstance() {
        return INSTANCE;
    }

    private final Map<InetSocketAddress, NetworkConnection> connectionMap = new ConcurrentHashMap<>();

    public Collection<NetworkConnection> getAll() {
        return connectionMap.values();
    }

    public NetworkConnection get(InetSocketAddress address) {
        return connectionMap.get(address);
    }

    public void register(NetworkConnection connection) {
        connectionMap.put(connection.getAddress(), connection);
    }
    public NetworkConnection deregister(InetSocketAddress address) {
        return connectionMap.remove(address);
    }

}
