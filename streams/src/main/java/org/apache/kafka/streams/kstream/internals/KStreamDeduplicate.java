package org.apache.kafka.streams.kstream.internals;

import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.processor.api.ContextualFixedKeyProcessor;
import org.apache.kafka.streams.processor.api.FixedKeyProcessor;
import org.apache.kafka.streams.processor.api.FixedKeyProcessorSupplier;
import org.apache.kafka.streams.processor.api.FixedKeyRecord;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.TTLKeyValueStore;

import java.time.Duration;

// TODO: name is fine ?
public class KStreamDeduplicate<K, KId, V> implements FixedKeyProcessorSupplier<K, V, V> {

    private KeyValueStore<KId, DedupStoreValue> store;
    private final KeyValueMapper<? super K, ? super V, ? extends KId> idSelector;

    public KStreamDeduplicate(KeyValueMapper<? super K, ? super V, ? extends KId> idSelector,
                              Duration deduplicationInterval) {
        this.idSelector = idSelector;
        this.store = new TTLKeyValueStore<>(deduplicationInterval);
    }

    @Override
    public FixedKeyProcessor<K, V, V> get() {
        return new KStreamDeduplicateProcessor();
    }

    private class KStreamDeduplicateProcessor extends ContextualFixedKeyProcessor<K, V, V> {
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
