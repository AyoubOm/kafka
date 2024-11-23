package org.apache.kafka.streams.kstream.internals;

import org.apache.kafka.streams.kstream.Deduplicated;

public class DeduplicatedInternal<K, V> extends Deduplicated<K, V> {

    DeduplicatedInternal(final Deduplicated<K, V> deduplicated) {
        super(deduplicated);
    }

    String name() {
        return name;
    }

}
