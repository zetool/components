/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
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

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.zetool.components.JRuler.RulerDisplayUnit;
import org.zetool.components.JRuler.RulerOrientation;

/**
 * Tests propagation of zoom factor to {@link JZoomableRuler} by listeners.
 * @author Jan-Philipp Kappmeier
 */
public class TestZoomableRuler {
    private JZoomableRuler ruler;
    
    @Before
    public void setup() {
        ruler = new JZoomableRuler(RulerOrientation.HORIZONTAL, RulerDisplayUnit.CENTIMETER);
    }
    
    @Test
    public void testInitializesCorrectly() {
        assertThat(ruler.getZoomFactor(), is(closeTo(1.0, 10e-8)));
    }
    
    @Test
    public void testZoomFactorListener() {
        JSlider slider = new JSlider(1, 200);

        slider.addChangeListener(ruler);
        assertThat(ruler.getZoomFactor(), is(closeTo(1.0, 10e-8)));

        slider.setValue(1);
        assertThat(ruler.getZoomFactor(), is(closeTo(0.01, 10e-8)));
        slider.setValue(20);
        assertThat(ruler.getZoomFactor(), is(closeTo(0.2, 10e-8)));
        slider.setValue(100);
        assertThat(ruler.getZoomFactor(), is(closeTo(1, 10e-8)));
        slider.setValue(200);
        assertThat(ruler.getZoomFactor(), is(closeTo(2, 10e-8)));
    }
    
    @Test
    public void testIgnoresOtherListener() {
        ruler.setZoomFactor(0.2);

        ruler.stateChanged(new ChangeEvent(new Object()));
        assertThat(ruler.getZoomFactor(), is(closeTo(0.2, 10e-8)));
        
        ruler.stateChanged(new ChangeEvent(new JSlider()));
        assertThat(ruler.getZoomFactor(), is(closeTo(0.5, 10e-8)));
    }
    
    @Test
    public void testIgnoresDuringChange() {
        JSlider slider = new JSlider(1, 100);
        slider.addChangeListener(ruler);
        slider.setValueIsAdjusting(true);
        
        slider.setValue(1);
        assertThat(ruler.getZoomFactor(), is(closeTo(1, 10e-8)));
        slider.setValue(100);
        assertThat(ruler.getZoomFactor(), is(closeTo(1, 10e-8)));        
    }
}
