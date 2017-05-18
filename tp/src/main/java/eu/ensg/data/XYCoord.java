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

/**
 * The xy coord class. Used to store X and Y positions. In case of the complete
 * library, it gives the ability to change the number of dimension of the
 * coordinates.
 *
 * @author cyann for ENSG
 */
public class XYCoord {

    private final Float x;
    private final Float y;

    /**
     * The X position getter.
     *
     * @return X
     */
    public Float getX() {
        return x;
    }

    /**
     * The Y position getter.
     *
     * @return Y
     */
    public Float getY() {
        return y;
    }

    /**
     * Construct the object with its coordinates
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public XYCoord(Float x, Float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Return the string representation of the object<br>
     * Very useful to debug parsing results.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "PointCoord{" + "x=" + x + ", y=" + y + '}';
    }

}
