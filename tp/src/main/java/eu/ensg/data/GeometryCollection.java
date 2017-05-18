/*
 * Copyright (C) 2017 Yann Caron aka CyaNn
 *
 * This program is free software: you can redistribute it and/or modify
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
package eu.ensg.data;

import java.util.ArrayList;
import java.util.List;

/**
 * The GeometryCollection class. Based on composite design patter.
 *
 * @author cyann for ENSG
 */
public class GeometryCollection extends Geometry {

    private final List<Geometry> children;

    /**
     * Default constructor
     */
    public GeometryCollection() {
        children = new ArrayList<>();
    }

    /**
     * Add a geometry to children list
     *
     * @param geometry the geometry to add
     */
    public void addGeometry(Geometry geometry) {
        children.add(geometry);
    }

    /**
     * Return the string representation of the object<br>
     * Very useful to debug parsing results.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "GeometryCollection{" + "children=" + children + '}';
    }

}
