package org.apache.kafka.streams.state;

import java.time.Duration;

// TODO: name ?
// TODO: is it wrappable (cachable, loggable, etc...) ? -> It should be normally
// TODO: should it be an interface for more flexibility ? What concrete benefit ?
// --> I think we should have an interface, for e.g. for the in-memory version, or any other alternative.
abstract class AbstractTtlStore<K, V> implements KeyValueStore<K, V> {

    public AbstractTtlStore(long ttlMs) { // TODO: should be a Duration ? int ? long ?

    }
}
