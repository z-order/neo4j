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
package org.neo4j.internal.id;

public interface IdType {
    /**
     * @return whether there's a high activity of id allocations/deallocations for this type.
     */
    boolean highActivity();

    String name();

    /**
     * @return {@code true} for types that represent IDs for schema-related stores, otherwise {@code false}.
     * This is useful to know in certain test setups where the environment is set up to let stores
     * start from a higher, sometimes much higher, start ID. This generally doesn't work so well for
     * schema/token stores.
     */
    default boolean isSchemaType() {
        return false;
    }
}
