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
package eu.ensg.compiler;

import eu.ensg.data.Geometry;
import eu.ensg.data.GeometryCollection;
import eu.ensg.data.Point;
import eu.ensg.data.XYCoord;
import java.util.List;

/**
 * The parser solution
 * @author cyann for ENSG
 */
public class WktParser {

    // geometryCollection = 'GEOMETRYCOLLECTION' '(' geometryList ')'
    public static GeometryCollection parseGeometryCollection(List<Token> lexems) {
        // test if lexem left
        if (lexems.isEmpty()) return null;
        // test if next == 'GEOMETRYCOLLECTION'
        if (lexems.get(0).getLexem().equals("GEOMETRYCOLLECTION")) {
            lexems.remove(0);
        } else {
            return null;
        }
        // test if next == '('
        if (lexems.get(0).getLexem().equals("(")) {
            lexems.remove(0);
        } else {
            return null;
        }

        // new geometryCollection
        GeometryCollection geometryCollection = new GeometryCollection();

        // add first child
        Geometry geometry = parseGeometry(lexems);
        if (geometry == null) return null;
        geometryCollection.addGeometry(geometry);

        // while next == ','
        while (lexems.get(0).getLexem().equals(",")) {
            lexems.remove(0);

            // add child
            geometry = parseGeometry(lexems);
            if (geometry == null) return null;
            geometryCollection.addGeometry(geometry);
        }

        // test if next == ')'
        if (lexems.get(0).getLexem().equals(")")) {
            lexems.remove(0);
        } else {
            return null;
        }
        
        // return geometryCollection
        return geometryCollection;
    }
    
    // geometry ::= geometryCollection | point
    public static Geometry parseGeometry(List<Token> lexems) {
        // test if lexem left
        if (lexems.isEmpty()) return null;

        // typical case of assumption
        Geometry geometry;
        
        // try to parseGeometryCollection
        geometry = parseGeometryCollection(lexems);
        if (geometry != null) return geometry;
        
        // try to parsePoint
        geometry = parsePoint(lexems);
        if (geometry != null) return geometry;
        
        return null;
    }
    
    // point :: 'POINT' '(' xyPointCoord ')'
    public static Point parsePoint(List<Token> lexems) {
        // test if lexem left
        if (lexems.isEmpty()) return null;
        
        // test if next == 'GEOMETRYCOLLECTION'
        if (lexems.get(0).getLexem().equals("POINT")) {
            lexems.remove(0);
        } else {
            return null;
        }
        
        // test if next == '('
        if (lexems.get(0).getLexem().equals("(")) {
            lexems.remove(0);
        } else {
            return null;
        }
        
        // parse pointCoord
        XYCoord pointCoord = parseXYPointCoord(lexems);
        if (pointCoord == null) return null;
        
        // create new point with pointCoord
        Point point = new Point(pointCoord);
        
        // test if next == ')'
        if (lexems.get(0).getLexem().equals(")")) {
            lexems.remove(0);
        } else {
            return null;
        }
        
        // return point
        return point;
    }
    
    // xyPointCoord ::= coord coord
    public static XYCoord parseXYPointCoord(List<Token> lexems) {
        // test if lexem left
        if (lexems.isEmpty()) return null;
        
        // parse simple coord
        Float x = parseCoord(lexems);
        if (x == null) return null;
        
        // parse simple coord
        Float y = parseCoord(lexems);
        if (y == null) return null;

        // return pointCoord
        return new XYCoord(x, y);
    }
    
    // coord ::= number
    public static Float parseCoord(List<Token> lexems) {
        // test if next is a NUMBER
        if (lexems.isEmpty() || lexems.get(0).getType() != TokenType.NUMBER) return null;
        // and get it
        return Float.parseFloat(lexems.remove(0).getLexem());        
    }
}
