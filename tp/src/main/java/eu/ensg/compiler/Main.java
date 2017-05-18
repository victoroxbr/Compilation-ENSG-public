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

import eu.ensg.data.GeometryCollection;
import eu.ensg.data.Point;
import java.util.List;

/**
 * Entry point of the program.
 * The job start here.
 * @author cyann for ENSG
 */
public class Main {

    /**
     * Start here.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        {
            String wellKnownTest = "POINT(6.2377500 46.1943900)";
            List<Token> lexems = WktLexer.parse(wellKnownTest);

            lexems.forEach(System.out::println);
            
            Point p = WktParser.parsePoint(lexems);
            System.out.println(p);

            System.out.println(p.toWkt());
        }

        System.out.println("");
        System.out.println("-----------------------");
        System.out.println("");
        
        {
            String wellKnownTest = "GEOMETRYCOLLECTION(POINT(0 0), GEOMETRYCOLLECTION(POINT (0 0)))";
            List<Token> lexems = WktLexer.parse(wellKnownTest);

            lexems.forEach(System.out::println);

            GeometryCollection c = WktParser.parseGeometryCollection(lexems);
            System.out.println(c);
            
            System.out.println(c.toWkt());
        }

    }
}
