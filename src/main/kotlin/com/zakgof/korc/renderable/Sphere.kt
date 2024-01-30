package com.zakgof.korc.renderable

import com.zakgof.korc.material.DiffuseMaterial
import com.zakgof.korc.math.Ray
import com.zakgof.korc.math.Vec3
import com.zakgof.korc.tracer.Intersection
import kotlin.math.sqrt

class Sphere(val center: Vec3, val radius: Double) : Renderable {

    override fun intersection(ray: Ray): Intersection? {

        val sc = ray.start.minus(center)
        val a = ray.direction.lengthSquared()
        val b = 2.0 * ray.direction.dot(sc)
        val c = sc.lengthSquared() - radius * radius
        val D = b * b - 4.0 * a * c

        if (D > 0) {

            val d = sqrt(D)
            val t1 = (-b - d) * 0.5 / a
            val t2 = (-b + d) * 0.5 / a  // TODO: refraction uses t2

            if (t1 > 0) {
                val pos = ray.start.plus(ray.direction.mult(t1))
                return Intersection(
                    rayT = t1,
                    position = pos,
                    normal = pos.minus(center).normalize(),
                    material = DiffuseMaterial()
                )
            }
        }
        return null
    }

}