/**
 * Copyright (c) 2013 Permeance Technologies. All rights reserved.
 * 
 * This file is part of Log Viewer.
 * 
 * Log Viewer is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * Log Viewer is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Log Viewer. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package com.permeance.utility.logviewer.portlets;

public class RollingLogViewer {

    public static final int CHAR_SIZE = 5 * 1024 * 1024;

    private final char[] intbuf;
    private int pnt;

    public RollingLogViewer() {
        intbuf = new char[CHAR_SIZE];
        pnt = 0;
    }

    public synchronized void write(char[] buf, int offset, int length) {
        if (pnt + length > intbuf.length) {
            int offlength = intbuf.length - pnt;
            System.arraycopy(buf, offset, intbuf, pnt, offlength);
            System.arraycopy(buf, offset + offlength, intbuf, 0, length - offlength);
        } else {
            System.arraycopy(buf, offset, intbuf, pnt, length);
        }

        pnt = (pnt + length) % intbuf.length;
    }

    public int getCurrentPointer() {
        return pnt;
    }

    public char[] getBuffer(int oldpointer, int newpointer) {
        if (oldpointer == -1) {
            oldpointer = ((newpointer - 1000) + intbuf.length) % intbuf.length;
        }

        if (newpointer == oldpointer) {
            return new char[0];
        }

        if (newpointer > oldpointer) {
            char[] toReturn = new char[newpointer - oldpointer];
            System.arraycopy(intbuf, oldpointer, toReturn, 0, newpointer - oldpointer);
            return toReturn;
        } else {
            // loop around
            char[] toReturn = new char[intbuf.length - oldpointer + newpointer];
            int offlength = intbuf.length - oldpointer;
            System.arraycopy(intbuf, oldpointer, toReturn, 0, offlength);
            System.arraycopy(intbuf, 0, toReturn, offlength, newpointer);
            return toReturn;
        }
    }

}
