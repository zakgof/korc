package com.zakgof.korc.renderable

import com.zakgof.korc.math.Ray
import com.zakgof.korc.math.Vec3
import com.zakgof.korc.tracer.Intersection

class Plane(val point: Vec3, val normal: Vec3) : Geometry {

    val PUSH = 0.001

    override fun intersection(ray: Ray): Intersection? {
        val t = (point.minus(ray.start)).dot(normal) / ray.direction.dot(normal)
        if (t > 0) {
            val pos = ray.start.plus(ray.direction.mult(t))
            return Intersection(
                rayT = t,
                position = pos.plus(normal.mult(PUSH)),
                normal = normal
            )
        }
        return null
    }

}