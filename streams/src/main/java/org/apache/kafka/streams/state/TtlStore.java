package org.apache.kafka.streams.state;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.StateStore;
import org.apache.kafka.streams.processor.StateStoreContext;
import org.apache.kafka.streams.state.internals.RocksDBTimeOrderedKeyValueBuffer;

import java.util.List;
import java.util.Map;

// note (to remove): Added an Abstract class to allow having another/better implementation of TTLStore in the future
public class TtlStore<K, V> extends AbstractTtlStore<K, V> {
    private final KeyValueStore<K, DedupStoreValue> base;
    private final RocksDBTimeOrderedKeyValueBuffer<Long, K> timeIndex;
    private final long ttlMs;

    public static class Builder<K, V> implements StoreBuilder<KeyValueStore<K, V>> { // TODO: If we define the builder as inhereting StoreBuilder<KeyValueStore> will the functionalities of logging and caching work properly ?
        // .. as they should be assuming the existence of only one store. Or if not, should we inherit from StateStore directly ?

        final String storeName;

        public Builder(String storeName) {
            this.storeName = storeName;
        }

        @Override
        public StoreBuilder<KeyValueStore<K, V>> withCachingEnabled() {
            return null;
        }

        @Override
        public StoreBuilder<KeyValueStore<K, V>> withCachingDisabled() {
            return null;
        }

        @Override
        public StoreBuilder<KeyValueStore<K, V>> withLoggingEnabled(Map<String, String> config) {
            return null;
        }

        @Override
        public StoreBuilder<KeyValueStore<K, V>> withLoggingDisabled() {
            return null;
        }

        @Override
        public KeyValueStore<K, V> build() {
            return null; // TODO: I think we should enforce caching & logging (the stores will be persistent, we should have fast access through caching. They should be fault-tolerant as well)
            // unlike the user stores where he can decide these configs, here it's mandatory for the correct behavior of the processor
            // Can we do here new CachingKeyValueStore(new ChangeLoggingKeyValueBytesStore(...)) for the inner stores ?
        }

        @Override
        public Map<String, String> logConfig() {
            return Map.of();
        }

        @Override
        public boolean loggingEnabled() {
            return true;
        }

        @Override
        public String name() {
            return storeName;
        }
    }

    public TtlStore(final KeyValueStore<K, DedupStoreValue> base,
                    final RocksDBTimeOrderedKeyValueBuffer<Long, K> timeIndex,
                    final long ttlMs) {
        this.base = base;
        this.timeIndex = timeIndex;
        this.ttlMs = ttlMs;
    }

    @Override
    public void put(K key, V value) {
        // puts in the base and in timeIndex
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return null;
    }

    @Override
    public void putAll(List<KeyValue<K, V>> entries) {

    }

    @Override
    public V delete(K key) {
        return null;
    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public void init(StateStoreContext context, StateStore root) {
        // init both stores
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean persistent() {
        return base.persistent(); // TODO: what about time
    }

    @Override
    public boolean isOpen() {
        return base.isOpen() && timeIndex.isOpen();
    }

    @Override
    public V get(K key) {
        // put the eviction here before getting a value, have a throttling mechanism to not try eviction at each .get()
        return null;
    }

    @Override
    public KeyValueIterator<K, V> range(K from, K to) {
        return null;
    }

    @Override
    public KeyValueIterator<K, V> all() {
        return null;
    }

    @Override
    public long approximateNumEntries() {
        return 0;
    }
}

// TODO: name + visibility + place in the codebase
class DedupStoreValue<V> {
    private long offset;
    private long timestamp;
    private V value; // TODO: maybe it is good to keep the value for debugging purpose ? Will it be exploitable (queryable)  ?
}
