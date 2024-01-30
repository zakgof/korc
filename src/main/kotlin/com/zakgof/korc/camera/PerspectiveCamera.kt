package com.zakgof.korc.camera

import com.zakgof.korc.math.Ray
import com.zakgof.korc.math.Vec3

class PerspectiveCamera(
    val viewPoint: Vec3,
    val direction: Vec3,
    val up: Vec3,
    val width: Double,
    val height: Double,
    val near: Double
) : Camera {

    private val right = direction.cross(up)

    /** x, y = 0..1 */
    override fun ray(x: Double, y: Double): Ray = Ray(
        viewPoint,
        direction.mult(near)
            .plus(up.mult((y - 0.5) * height))
            .plus(right.mult((x - 0.5) * width))
            .normalize()
    )

}
