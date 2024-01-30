package com.zakgof.korc.camera

import com.zakgof.korc.math.Ray
import com.zakgof.korc.math.Vec3

class OrthoCamera(
    val viewPoint: Vec3,
    val direction: Vec3,
    val up: Vec3,
    val width: Double,
    val height: Double
) : Camera {

    private val right = direction.cross(up)

    override fun ray(x: Double, y: Double): Ray = Ray(
        viewPoint
            .plus(up.mult((y - 0.5) * height))
            .plus(right.mult((x - 0.5) * width)),
        direction
    )

}
