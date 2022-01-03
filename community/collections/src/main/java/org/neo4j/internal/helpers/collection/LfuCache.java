/*
 * Copyright (c) "Neo4j"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.internal.helpers.collection;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static org.neo4j.util.Preconditions.requirePositive;

/**
 * Thread safe implementation of cache.
 * <p>
 * The cache has a <CODE>maxSize</CODE> set and when the number of cached
 * elements exceeds that limit the least recently used element will be removed.
 */
public class LfuCache<K, E>
{
    private final String name;
    private final int maxSize;
    private final Cache<K, E> cache;

    /**
     * Creates a LFU cache. If {@code maxSize < 1} an
     * IllegalArgumentException is thrown.
     *
     * @param name    name of cache
     * @param maxSize maximum size of this cache
     */
    public LfuCache( String name, int maxSize )
    {
        this.name = requireNonNull( name );
        this.maxSize = requirePositive( maxSize );
        this.cache = Caffeine.newBuilder().executor( Runnable::run ).maximumSize( maxSize ).build();
    }

    public String getName()
    {
        return this.name;
    }

    /**
     * Returns the maximum size of this cache.
     *
     * @return maximum size
     */
    public int maxSize()
    {
        return maxSize;
    }

    public void put( K key, E element )
    {
        requireNonNull( key );
        requireNonNull( element );
        cache.put( key, element );
    }

    public E get( K key )
    {
        requireNonNull( key );
        return cache.getIfPresent( key );
    }

    public void clear()
    {
        cache.invalidateAll();
    }

    public int size()
    {
        return cache.asMap().size();
    }

    public Set<K> keySet()
    {
        return cache.asMap().keySet();
    }
}
