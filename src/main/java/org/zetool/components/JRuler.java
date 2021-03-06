/* zet evacuation tool copyright (c) 2007-14 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.zetool.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;

/**
 * Provides a scalable ruler. The unit can be scaled using different measurements. Additional ticks and numbers on the
 * ruler are added. The current cursor position can be drawn as well.
 *
 * @author Jan-Philipp Kappmeier
 */
public class JRuler extends JComponent {

    /**
     * An enumeration containing several units to measure distance. All units are provided with a scale factor to the
     * base unit 1 meter and a short text as abbreviation (e. g. m for meter).
     */
    public enum RulerDisplayUnit {

        /** Micrometers. */
        MICROMETER(0.0001, "my"),
        /** Millimeters. */
        MILLIMETER(0.001, "mm"),
        /** Centimeters. */
        CENTIMETER(0.01, "cm"),
        /** Two centimeters. */
        TWO_CENTIMETER(0.02, "2cm"),
        /** Decimeters. */
        DECIMETER(0.1, "dm"),
        /** Meters. */
        METER(1.0, "m"),
        /** Inches. */
        INCH(0.0254, "in"),
        /** Feet. */
        FOOT(0.3048, "ft"),
        /** Yards. */
        YARD(0.9144, "yd");
        /** The scaling value for the selected unit. */
        private final double unit;
        /** The short text for the selected unit. */
        private final String text;

        /**
         * Creates a unit instance containing a scale factor and a short text.
         *
         * @param unit the scaling factor for the unit
         * @param text the short text for the unit
         */
        RulerDisplayUnit(double unit, String text) {
            this.unit = unit;
            this.text = text;
        }

        /**
         * Returns the scaling factor to meter from the unit.
         * @return the scaling factor to meter from the unit
         */
        double unit() {
            return unit;
        }

        /**
         * Returns the short name of the unit as text.
         * @return the short name of the unit as text
         */
        @Override
        public String toString() {
            return text;
        }
    }

    /**
     * An enumeration containing the two directions in which the ruler can be drawn, horizontally and vertically.
     */
    public enum RulerOrientation {
        /** The ruler is horizontally drawn. */
        HORIZONTAL,
        /** The ruler is vertically drawn. */
        VERTICAL;
    }
    /** The currently set unit of the ruler. */
    private RulerDisplayUnit unit = RulerDisplayUnit.CENTIMETER;
    /** The height of an horizontal ruler, or the width of a vertical, respectively. */
    private int size = 30;
    /** The orientation of the ruler. Can be horizontal or vertical. */
    private final RulerOrientation orientation;
    /** The background color of the ruler. */
    public Color background = Color.WHITE;
    /** The foreground color of the ruler. */
    public Color foreground = Color.BLACK;
    /** The font for the numbers and units. */
    public Font font = new Font("SansSerif", Font.PLAIN, 10);
    /** The step wide that defines the scale elements painted. */
    public double scalePaintStep = 10;
    /** The most left position. */
    private double offsetInPixel = 80;
    /** Private zoom factor. */
    private double zoomFactor = 1000;
    static final int longTick = 10;
    private int bigScaleStep = 1;
    private int smallScaleStep = 1;

    /**
     * Creates a ruler with the specified orientation and the specified distance measurement.
     *
     * @param orientation the direction of the ruler (horizontally or vertically)
     * @param unit the unit that is used to measure the distance
     */
    public JRuler(RulerOrientation orientation, RulerDisplayUnit unit) {
        this.orientation = orientation;
        this.unit = unit;
    }

    public RulerDisplayUnit getDisplayUnit() {
        return this.unit;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Get bounds
        Rectangle drawArea = g.getClipBounds();

        // Fill background
        g.setColor(background);
        g.fillRect(drawArea.x, drawArea.y, drawArea.width, drawArea.height);

        // Set font and foreground color
        g.setColor(foreground);
        g.setFont(font);

        // Draw border lines
        if (orientation == RulerOrientation.HORIZONTAL) {
            g.drawLine(drawArea.x, size - 1, drawArea.x + drawArea.width, size - 1);
        } else {
            g.drawLine(size - 1, drawArea.y, size - 1, drawArea.y + drawArea.height);
        }

        double pixelPerUnit = getPixelPerUnit();
        double firstTickPosition;
        int unitCountInOffset = 0;
        int smallOffsetCounter;
        int bigOffsetCounter;
        if (offsetInPixel < 0) {
            unitCountInOffset = (int) (Math.floor(offsetInPixel / pixelPerUnit) + 1);
            firstTickPosition = unitCountInOffset * pixelPerUnit;
        }
        if (offsetInPixel == 0) {
            firstTickPosition = 0;
        } else {
            unitCountInOffset = (int) (Math.floor(offsetInPixel / pixelPerUnit));
            firstTickPosition = offsetInPixel - unitCountInOffset * pixelPerUnit;
        }
        smallOffsetCounter = smallScaleStep - (unitCountInOffset % smallScaleStep);
        bigOffsetCounter = bigScaleStep - (unitCountInOffset % bigScaleStep);

        double remainingPixels = getRulerLength(drawArea) - firstTickPosition;
        double tickCount = 1;
        if (remainingPixels > 0) {
            tickCount = remainingPixels / pixelPerUnit;
        }

        drawTicks(g, (int) tickCount, firstTickPosition, unitCountInOffset, bigOffsetCounter, smallOffsetCounter);
    }

    private double getPixelPerUnit() {
        return zoomFactor * unit.unit();
    }

    private double getRulerLength(Rectangle drawArea) {
        return orientation == RulerOrientation.HORIZONTAL ? drawArea.width : drawArea.height;
    }

    private void drawTicks(Graphics g, int tickCount, double firstTickPosition, int tickOffset,
            int bigOffsetCounter, int smallOffsetCounter) {
        for (int i = 0; i < tickCount; ++i) {
            double drawPos = firstTickPosition + i * getPixelPerUnit();
            if (bigOffsetCounter % bigScaleStep == 0) {
                drawTickWithString(g, 10, (int) drawPos, Integer.toString(i - tickOffset));
            } else if (smallOffsetCounter % smallScaleStep == 0) {
                drawTickWithString(g, 7, (int) drawPos, "");
            } else {
                drawTickWithString(g, 0, (int) drawPos, "");
            }
            bigOffsetCounter++;
            smallOffsetCounter++;
        }
    }

    private void drawTickWithString(Graphics g, int tickLength, int drawPos, String text) {
        if (tickLength != 0) {
            if (orientation == RulerOrientation.HORIZONTAL) {
                g.drawLine(drawPos, size - 1, drawPos, size - tickLength - 1);
                if (text != null) {
                    g.drawString(text, drawPos - 3, 16);
                }
            } else {
                g.drawLine(size - 1, drawPos, size - tickLength - 1, drawPos);
                if (text != null) {
                    g.drawString(text, 7, drawPos + 3);
                }
            }
        }
    }
    
    /**
     * Set an offset in millimeter.
     * @param offsetMillimeter 
     */
    public void setOffset(int offsetMillimeter) {
        offsetInPixel = zoomFactor * offsetMillimeter / 1000;
        
    }

    public void setBigScaleStep(int scale) {
        if (scale <= 0) {
            throw new java.lang.IllegalArgumentException("Scale is negative or zero");
        }
        this.bigScaleStep = scale;
    }

    public void setDisplayUnit(RulerDisplayUnit unit) {
        this.unit = unit;
    }

    public void setHeight(int pw) {
        setPreferredHeight((int) Math.ceil(pw * zoomFactor));
    }

    public void setPreferredHeight(int ph) {
        setPreferredSize(new Dimension(size, ph));
    }

    public void setPreferredWidth(int pw) {
        setPreferredSize(new Dimension(pw, size));
    }

    public void setSize(int size) {
        if (size <= 0) {
            throw new java.lang.IllegalArgumentException("Size not positive");
        }
        this.size = size;
    }

    public void setSmallScaleStep(int scale) {
        if (scale <= 0) {
            throw new java.lang.IllegalArgumentException("Scale is negative or zero");
        }
        this.smallScaleStep = scale;
    }

    public void setWidth(int ph) {
        setPreferredWidth((int) Math.ceil(ph * zoomFactor));
    }


    double getZoomFactor() {
        return zoomFactor/1000;
    }
    
    /**
     * Sets a specified zoom factor for the ruler. A zoom factor of 1 means that one pixel represents 1 millimeter.
     *
     * @param zoomFactor the zoom factor
     * @throws java.lang.IllegalArgumentException if the zoom factor is negative
     */
    public void setZoomFactor(double zoomFactor) {
        if (zoomFactor <= 0) {
            throw new java.lang.IllegalArgumentException("Zoomfactor negative or zero: " + zoomFactor);
        }
        this.zoomFactor = 1000 * zoomFactor;
    }
}
