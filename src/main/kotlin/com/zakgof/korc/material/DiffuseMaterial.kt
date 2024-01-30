package com.zakgof.korc.material

import com.zakgof.korc.math.Color
import com.zakgof.korc.math.Vec3
import com.zakgof.korc.tracer.ColorRay
import java.lang.Math.pow
import kotlin.math.max

class DiffuseMaterial : Material {

    override fun interact(ray: ColorRay): Set<ColorRay> = emptySet()

    override fun directLightResponse(
        point: Vec3,
        normal: Vec3,
        incident: Vec3,
        lightColor: Color,
        direction: Vec3
    ): Color {
        val diffuse = max(0.0, -incident.dot(normal))
        val reflection = incident.minus(normal.mult(2.0 * normal.dot(incident)))
        val specular = pow(max (0.0, reflection.dot(direction)), 10.0) // TODO: parameterize

        return lightColor.multiply(diffuse + specular)
    }
}