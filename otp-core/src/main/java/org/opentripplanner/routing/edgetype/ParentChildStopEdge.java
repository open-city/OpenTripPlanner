/* This program is free software: you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public License
 as published by the Free Software Foundation, either version 3 of
 the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>. */

package org.opentripplanner.routing.edgetype;

import org.opentripplanner.routing.core.State;
import org.opentripplanner.routing.core.StateEditor;
import org.opentripplanner.routing.graph.Edge;
import org.opentripplanner.routing.graph.Vertex;
import org.opentripplanner.routing.vertextype.TransitStop;

import com.vividsolutions.jts.geom.LineString;

/**
 * An edge that connects a parent station to a constituent stop. This is not intended to provide
 * implicit transfers (i.e. child stop to parent station to another child stop) but instead to allow
 * beginning or ending a path (itinerary) at a parent station.
 * 
 * Currently this edge is only intended for use in the long distance path service. The pathparsers
 * should ensure that it is effectively ignored in other path services, and even in the long distance
 * path service anywhere but the beginning or end of a path.
 * 
 * Note that when most users specify that they want to depart from a parent stop at 8AM, they 
 * actually want to leave from any constituent stop at or after exactly 8AM and are accounting for
 * the initial walk access leg themselves. No edge should have zero cost (this can potentially
 * create endless search loops) but the edges can have zero time while having positive cost.
 */
public class ParentChildStopEdge extends Edge {

    private static final long serialVersionUID = 20130903L;

    /* For now both parent stations and stops are the same vertex type. 
     * TODO Should this really be the case? */
    public ParentChildStopEdge(TransitStop from, TransitStop to) {
        super(from, to);
    }

    @Override
    public State traverse(State s0) {
        StateEditor s1 = s0.edit(this);
        s1.incrementWeight(1);
        // Increment weight, but not time. See Javadoc on this class.
        return s1.makeState();
    }

    @Override
    public double getDistance() {
        return 0;
    }

    @Override
    public LineString getGeometry() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    public String toString() {
        return "ParentChildStopEdge(" + fromv + " -> " + tov + ")";
    }
}
