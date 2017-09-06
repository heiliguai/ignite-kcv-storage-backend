package io.openmg.metagraph.ignite;

import com.google.common.collect.Lists;
import org.janusgraph.diskstorage.BackendException;
import org.janusgraph.diskstorage.BaseTransactionConfig;
import org.janusgraph.diskstorage.StaticBuffer;
import org.janusgraph.diskstorage.StoreMetaData;
import org.janusgraph.diskstorage.configuration.Configuration;
import org.janusgraph.diskstorage.keycolumnvalue.*;
import org.janusgraph.diskstorage.util.StaticArrayBuffer;
import org.janusgraph.graphdb.configuration.GraphDatabaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.janusgraph.graphdb.configuration.GraphDatabaseConfiguration.METRICS_PREFIX;

/**
 * Created by eguoyix on 17/9/4.
 */
public class IgniteKCVStoreManager implements KeyColumnValueStoreManager {

    private static final Logger log = LoggerFactory.getLogger(IgniteKCVStoreManager.class);

    private final Map<String, IgniteKCVStore> openStores;

    private volatile StoreFeatures features = null;

    public static final String IGNITE_CONF_DEFAULT = "./conf/ignite.xml";

    public IgniteKCVStoreManager(Configuration config){
        String igniteConfig = IGNITE_CONF_DEFAULT;
        if (config.has(GraphDatabaseConfiguration.STORAGE_CONF_FILE)) {
            igniteConfig = config.get(GraphDatabaseConfiguration.STORAGE_CONF_FILE);
        }

        assert igniteConfig != null && !igniteConfig.isEmpty();

        File ccf = new File(igniteConfig);

        if (ccf.exists() && ccf.isAbsolute()) {
            igniteConfig = "file://" + igniteConfig;
            log.debug("Set ignite config string \"{}\"", igniteConfig);
        }
        this.openStores = new HashMap<String, IgniteKCVStore>(8);
    }


    public KeyColumnValueStore openDatabase(String name, StoreMetaData.Container container) throws BackendException {
        if (openStores.containsKey(name))
            return openStores.get(name);

        IgniteKCVStore store = new IgniteKCVStore(name, this);
        openStores.put(name, store);
        return store;
    }

    public void mutateMany(Map<String, Map<StaticBuffer, KCVMutation>> map, StoreTransaction storeTransaction) throws BackendException {

    }

    public StoreTransaction beginTransaction(BaseTransactionConfig baseTransactionConfig) throws BackendException {
        return null;
    }

    public void close() throws BackendException {
        openStores.clear();
    }

    public void clearStorage() throws BackendException {
        openStores.clear();
    }

    public StoreFeatures getFeatures() {
            if (features == null) {

                StandardStoreFeatures.Builder fb = new StandardStoreFeatures.Builder();
                fb.batchMutation(true).distributed(true);
                fb.timestamps(true).cellTTL(true);
                fb.optimisticLocking(true);
                fb.keyOrdered(false).orderedScan(false).unorderedScan(true);
                fb.multiQuery(false).localKeyPartition(false);
                features = fb.build();
            }

            return features;
    }


    public String getName() {
        return getClass().getSimpleName();
    }

    public List<KeyRange> getLocalKeyPartition() throws BackendException {
        List<KeyRange> ranges = Lists.newArrayList();
        ByteBuffer startBuf = ByteBuffer.allocate(Long.SIZE);
        startBuf.putLong(Long.MAX_VALUE);
        StaticBuffer start = StaticArrayBuffer.of(startBuf);

        ByteBuffer endBuf = ByteBuffer.allocate(Long.SIZE);
        endBuf.putLong(0L);
        StaticBuffer end = StaticArrayBuffer.of(endBuf);

        KeyRange range = new KeyRange(start,end);
        ranges.add(range);
        return ranges;
    }
}
