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

import org.zetool.common.debug.HTMLLoggerHandler;
import info.clearthought.layout.TableLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * The class {@code JLogPane} is a panel giving out formatted log messages.
 * @author Jan-Philipp Kappmeier
 */
public class JLogPane extends JPanel {

    JEditorPane logPane;
    HTMLLoggerHandler log;

    /**
     * Creates a new instance of {@code JLogPane}.
     * 
     * @param log 
     */
    public JLogPane( HTMLLoggerHandler log ) {
        double size[][] = // Columns
                        {
            {TableLayout.FILL},
            //Rows
            {TableLayout.FILL}
        };

        setLayout( new TableLayout( size ) );

        logPane = new JEditorPane( "text/html", "" );

        JScrollPane scrollPane = new JScrollPane( logPane );

        add( scrollPane, "0,0" );
        //this.log = log;
        log.setLogPane( logPane );
        //da();
    }

//    public synchronized void update() {
//        final String pre = "<html><font face=\"sans-serif\" size=\"-1\">";
//        final String post = "</font></html>";
//        logPane.setText( pre + log.getText() + post );
//    }

    //@Override
    //public void update( Observable o, Object arg ) {
    //    update();
    //}

    
}
