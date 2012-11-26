package com.collision;

import com.badlogic.gdx.math.Vector3;

public class Constraint {

 
	public static void solve(float delta, Vector3 pointA, Vector3 pointB, float distance) {
		// F = -k(|x|-d)(x/|x|) - bv
		Vector3 axis = new Vector3().set(pointA).sub(pointB);
		float currentDistance = axis.len();
		Vector3 unitAxis = axis.nor();

		float relVel = pointB.cpy().sub(pointA).dot(unitAxis);
		float relDist = currentDistance - distance;
		Vector3 impulse = new Vector3().set(unitAxis).mul(relDist * -20f + (relVel * 0.5f));
		pointA.add(impulse.mul(delta));
	//	pointB.sub(impulse);
	}

}

// public override function Solve( dt:Number ):void
// {
// // get some information that we need
// const axis:Vector2 = m_bodyB.m_Pos.Sub(m_bodyA.m_Pos);
// const currentDistance:Number = axis.m_Len;
// const unitAxis:Vector2 = axis.MulScalar(1/currentDistance);
//
// // calculate relative velocity in the axis, we want to remove this
// const relVel:Number = m_bodyB.m_vel.Sub(m_bodyA.m_vel).Dot(unitAxis);
//
// const relDist:Number = currentDistance-m_distance;
//
// // calculate impulse to solve
// const remove:Number = relVel+relDist/dt;
// const impulse:Number = remove / (m_bodyA.m_InvMass + m_bodyB.m_InvMass);
//
// // generate impulse vector
// const I:Vector2 = unitAxis.MulScalar(impulse);
//
// // apply
// ApplyImpulse(I);
// }

// public class Constraint
// {
// protected var m_bodyA:RigidBody;
// protected var m_bodyB:RigidBody;
//
// ///
// ///
// ///
// public function Constraint( bodyA:RigidBody, bodyB:RigidBody )
// {
// m_bodyA = bodyA;
// m_bodyB = bodyB;
//
// Assert( m_bodyA.m_InvMass>0||m_bodyB.m_InvMass>0,
// "Constraint between two infinite mass bodies not allowed" );
// }
//
// ///
// ///
// ///
// public function ApplyImpulse( I:Vector2 ):void
// {
// m_bodyA.m_vel = m_bodyA.m_vel.Add( I.MulScalar(m_bodyA.m_InvMass) );
// m_bodyB.m_vel = m_bodyB.m_vel.Sub( I.MulScalar(m_bodyB.m_InvMass) );
// }
//
// ///
// ///
// ///
// public function Solve( dt:Number ):void
// {
// throw new Error("base class doesn't implement!");
// }
// }