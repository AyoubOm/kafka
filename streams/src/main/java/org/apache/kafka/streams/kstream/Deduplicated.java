package org.apache.kafka.streams.kstream;

import org.apache.kafka.common.serialization.Serde;

// TODO: add java docs
public class Deduplicated<K, V> implements NamedOperation<Deduplicated<K, V>> {

    protected final String name;
    protected final String storeName;
    protected final Serde<K> keySerde;
    protected final Serde<V> valueSerde;

    private Deduplicated(final String name,
                         final String storeName,
                         final Serde<K> keySerde,
                         final Serde<V> valueSerde) {
        this.name = name;
        this.storeName = storeName;
        this.keySerde = keySerde;
        this.valueSerde = valueSerde;
    }

    protected Deduplicated(final Deduplicated<K, V> deduplicated) {
        this(deduplicated.name, deduplicated.storeName, deduplicated.keySerde, deduplicated.valueSerde);
    }

    // TODO: KEEP ONLY RELEVANT METHODS THAT ARE REALLY USEFUL
    /**
     * Create an instance of {@code Deduplicated} with provided name (// TODO: more details about where this name will be used)
     * @param name
     *          // TODO: add details what is the name about [see other serde classes]
     *
     * @param <K>
     * @param <V>
     *
     * @return
     */
    public static <K, V> Deduplicated<K, V> as(final String name);


    public static <K> Deduplicated<K> keySerde(final Serde<K> keySerde);


    public static <V> Deduplicated<V> valueSerde(final Serde<V> valueSerde);


    public static <K, V> Deduplicated<K, V> with(final Serde<V> storeName,
                                                 final Serde<K> keySerde,
                                                 final Serde<V> valueSerde);


    public static <K, V> Deduplicated<K, V> with(final Serde<K> keySerde,
                                                 final Serde<V> valueSerde);

    public Deduplicated<K, V> withName(final String name);

    public Deduplicated<K, V> withKeySerde(final Serde<K> keySerde);

    public Deduplicated<K, V> withValueSerde(final Serde<V> valueSerde);
}
