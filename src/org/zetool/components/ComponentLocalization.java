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

import org.zetool.common.localization.Localization;
import org.zetool.common.localization.LocalizationManager;

/**
 * The localization class for gui components present in the package
 * {@link org.zetool.components}.
 * 
 * @author Jan-Philipp Kappmeier
 */
public final class ComponentLocalization {
  /** Localization access for graph classes. */
  public static final Localization LOC = LocalizationManager.getManager().getLocalization(
          "org.zetool.components.ComponentLocalization" );

  /** Utility class constructor. */
  private ComponentLocalization() {}
}
