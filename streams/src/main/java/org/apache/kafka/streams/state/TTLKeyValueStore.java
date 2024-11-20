package org.apache.kafka.streams.state;

import java.time.Duration;

// TODO: name ?
// TODO: is it wrappable (cachable, loggable, etc...)
// TODO: should it be an interface for more flexibility ? What concrete benefit ?
public class TTLKeyValueStore<K, V> implements KeyValueStore<K, V> {

    public TTLKeyValueStore(Duration ttl) { // TODO: should be a Duration ? int ? long ?

    }
}
