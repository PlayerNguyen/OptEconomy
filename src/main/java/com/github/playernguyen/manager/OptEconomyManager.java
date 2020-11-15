package com.github.playernguyen.manager;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Manager is a collection class to contain data with specify type in them
 * @param <T>
 */
public interface OptEconomyManager<T> extends Iterable<T> {

    /**
     * The collection with data-mutable using collection of Java.
     * This collection must be declare in the constructor.
     *
     * @return the specific collection which initiate in constructor
     */
    Collection<T> collection();

    /**
     * The {@link Stream} using collection class
     * @return The Stream API by using Collection#stream function
     */
    default Stream<T> stream() {
        return this.collection().stream();
    }

    /**
     * Find the first element by using {@link Predicate}.
     *
     * @param predicate The predicate class to find
     * @return the object whether found, null otherwise
     */
    @Nullable
    default T find(@NotNull Predicate<T> predicate) {
        Preconditions.checkNotNull(predicate);
        return collection().stream().filter(predicate).findFirst().orElse(null);
    }

    /**
     * Inherit method from the {@link Iterator}
     * @return the iterator function from collection
     */
    @NotNull
    @Override
    default Iterator<T> iterator() {
        return this.collection().iterator();
    }
}
