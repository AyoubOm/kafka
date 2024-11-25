package org.apache.kafka.streams.kstream.internals;

import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.processor.api.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.TtlStore;

import java.time.Duration;

// TODO: name is fine ?
public class KStreamDeduplicate<K, KId, V> implements FixedKeyProcessorSupplier<K, V, V> {

    private KeyValueStore<KId, DedupStoreValue> store;
    private final KeyValueMapper<? super K, ? super V, ? extends KId> idSelector;
    private final Duration deduplicationInterval;
    private final String storeName;

    public KStreamDeduplicate(final KeyValueMapper<? super K, ? super V, ? extends KId> idSelector,
                              final Duration deduplicationInterval, // TODO: should we make it a long here ? To see when implementing process()
                              final String storeName) {
        this.idSelector = idSelector;
        this.storeName = storeName;
        this.deduplicationInterval = deduplicationInterval;
    }

    @Override
    public FixedKeyProcessor<K, V, V> get() {
        return new KStreamDeduplicateProcessor();
    }

    private class KStreamDeduplicateProcessor extends ContextualFixedKeyProcessor<K, V, V> {
        private TtlStore<KId, DedupStoreValue> store;

        @Override
        public void init(final FixedKeyProcessorContext<K, V> context) {
            super.init(context);
            store = context.getStateStore(storeName); // TODO: understand how we can access a field of the super class from the nested class. It means the nested class can only be instantiated within the parent class
            // TODO
        }

        @Override
        public void process(final FixedKeyRecord<K, V> record) {
            context().forward(record); // TODO: for the moment, no deduplication
            // TODO: I think we should check here for nullability of id to not try to store null keys in the store
        }
    }
}

// TODO: visibility + place in the codebase + name
class DedupStoreValue {
    private long offset;
    private long timestamp;
}
