package io.openmg.metagraph.ignite;

import org.janusgraph.diskstorage.BackendException;
import org.janusgraph.diskstorage.Entry;
import org.janusgraph.diskstorage.EntryList;
import org.janusgraph.diskstorage.StaticBuffer;
import org.janusgraph.diskstorage.keycolumnvalue.*;

import java.util.List;
import java.util.Map;

/**
 * Created by eguoyix on 17/9/5.
 */
public class IgniteKCVStore implements KeyColumnValueStore {

    public IgniteKCVStore(String cacheName,KeyColumnValueStoreManager manager){

    }

    public EntryList getSlice(KeySliceQuery keySliceQuery, StoreTransaction storeTransaction) throws BackendException {
        return null;
    }

    public Map<StaticBuffer, EntryList> getSlice(List<StaticBuffer> list, SliceQuery sliceQuery, StoreTransaction storeTransaction) throws BackendException {
        return null;
    }

    public void mutate(StaticBuffer staticBuffer, List<Entry> list, List<StaticBuffer> list1, StoreTransaction storeTransaction) throws BackendException {

    }

    public void acquireLock(StaticBuffer staticBuffer, StaticBuffer staticBuffer1, StaticBuffer staticBuffer2, StoreTransaction storeTransaction) throws BackendException {

    }

    public KeyIterator getKeys(KeyRangeQuery keyRangeQuery, StoreTransaction storeTransaction) throws BackendException {
        return null;
    }

    public KeyIterator getKeys(SliceQuery sliceQuery, StoreTransaction storeTransaction) throws BackendException {
        return null;
    }

    public String getName() {
        return null;
    }

    public void close() throws BackendException {

    }
}
