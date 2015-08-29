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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.zetool.components;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.zetool.components.JRuler.RulerDisplayUnits;
import org.zetool.components.JRuler.RulerOrientation;

/**
 * Displays a {@link JRuler} that is capable to perform zooming. The ruler can listen to {@link ChangeEvent}s. If
 * the event is sent from a {@link JSlider} instance then the zoomfactor gets set according to the value of the slider.
 *
 * @author Jan-Philipp Kappmeier
 */
public class JZoomableRuler extends JRuler implements ChangeListener {

    /**
     * Creates a new instance of JZoomableRuler
     *
     * @param orientation
     * @param unit
     */
    public JZoomableRuler(RulerOrientation orientation, RulerDisplayUnits unit) {
        super(orientation, unit);
    }

    /**
     * Handles Swing events sent to the floor.
     * 
     * The possibility to handle events sent by slider events is implemented.
     * The slider needs to have positive values (thus 0 is not allowed). A
     * value of 100 implies a zoom factor of 1, that is displayed as 1mm for 1
     * pixel.
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider) {
            JSlider slider = (JSlider) e.getSource();
            if (!slider.getValueIsAdjusting()) {
                setZoomFactor(slider.getValue() * 0.01);
                repaint();
            }
        }
    }
}
