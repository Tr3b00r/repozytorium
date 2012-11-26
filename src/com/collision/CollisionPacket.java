/*
 *  This file is part of Libgdx by Mario Zechner (badlogicgames@gmail.com)
 *
 *  Libgdx is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Libgdx is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with libgdx.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.collision;

import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;

/**
 * A collision package contains an ellipsoids starting position
 * its velocity as well as its extends in x, y and z. The starting
 * position and velocity are stored twice, once in normal 3-space and
 * once in ellipsoid space, that is scaled by (1/xRadius,1/yRadius,1/zRadius).
 * 
 * An instance of this class is passed to {@link CollisionDetector} and returns
 * whether a collision occured and if so where it occured.
 * 
 * @author badlogicgames@gmail.com
 *
 */
public final class CollisionPacket 
{
        public enum CollisionType
        {
                Plane,
                Vertex,
                Edge,
                Embedded
        }
        
        /** the ellipsoid radii **/
        protected final float radiusX, radiusY, radiusZ;
        
        /** the inverse ellipsoid radii **/
        protected final float invRadiusX, invRadiusY, invRadiusZ;
        
        /** the position and velocity of the ellipsoid in 3-space **/
        protected final Vector3 r3Velocity;
        protected final Vector3 r3Position;
        
        /** the position and velocity of the ellipsoid in ellipsoid-space **/
        protected final Vector3 velocity;
        protected final Vector3 normalizedVelocity;
        protected final Vector3 position;
        
        protected boolean foundCollision;
        protected float nearestDistance = Float.MAX_VALUE;
        protected Vector3 intersectionPoint;
        protected Plane plane = new Plane( new Vector3(), 0 );
        
        public CollisionType type;      
        
        
        public CollisionPacket( Vector3 position, Vector3 velocity, float radiusX, float radiusY, float radiusZ )
        {               
                this.radiusX = radiusX;
                this.radiusY = radiusY;
                this.radiusZ = radiusZ;
                this.invRadiusX = 1 / radiusX;
                this.invRadiusY = 1 / radiusY;
                this.invRadiusZ = 1 / radiusZ;
                
                r3Position = new Vector3( position );
                r3Velocity = new Vector3( velocity );
                
                this.velocity = new Vector3( velocity.x * invRadiusX, velocity.y * invRadiusY, velocity.z * invRadiusZ );
                this.normalizedVelocity = new Vector3( velocity ).nor();
                this.position = new Vector3( position.x * invRadiusX, position.y * invRadiusY, position.z * invRadiusZ );
                
                this.intersectionPoint = new Vector3( );
        }
        
        /**
         * @return whether the ellipsoid is colliding
         */
        public boolean isColliding( )
        {
                return foundCollision;
        }
        
        /**
         * @return the point of intersection
         */
        public Vector3 getIntersectionPoint( )
        {
                return intersectionPoint;
        }
        
        /**
         * @return the nearest distance to the colliding plane
         */
        public float getNearestDistance( )
        {
                return nearestDistance;
        }

        /**
         * Sets the position and velocity of the swept ellipsoid. Resets
         * the old collision flag and distance.
         * 
         * @param position the position of the sphere
         * @param velocity the velocity of the ellipsoid
         */
        public void set(Vector3 position, Vector3 velocity) 
        {       
                r3Position.set( position );
                r3Velocity.set( velocity );
                
                this.velocity.set( velocity.x * invRadiusX, velocity.y * invRadiusY, velocity.z * invRadiusZ );
                this.normalizedVelocity.set( velocity ).nor();
                this.position.set( position.x * invRadiusX, position.y * invRadiusY, position.z * invRadiusZ );
                
                foundCollision = false;
                nearestDistance = Float.MAX_VALUE;
                intersectionPoint.set(0,0,0);
        }
}