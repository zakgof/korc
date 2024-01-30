package com.zakgof.korc.material

import com.zakgof.korc.math.Color
import com.zakgof.korc.math.Ray
import com.zakgof.korc.math.Vec3
import com.zakgof.korc.tracer.ColorRay
import java.lang.Math.pow
import kotlin.math.max

class DiffuseMaterial(
    val color: Color = Color(1.0, 1.0, 1.0),
    val mirror: Double = 0.0,
    val diffuse: Double = 0.8,
    val specular: Double = 1.0,
    val specularPower: Double = 10.0
) : Material {

    override fun interact(point: Vec3, normal: Vec3,ray: ColorRay): Set<ColorRay> {
        if (mirror != 0.0) {
            val reflected = ray.ray!!.direction.minus(normal.mult(2.0 * normal.dot(ray.ray.direction)))
            return setOf(ColorRay(ray = Ray(point, reflected), color = ray.color.multiply(mirror)))
        }
        return emptySet()
    }

    override fun directLightResponse(
        point: Vec3,
        normal: Vec3,
        incident: Vec3,
        lightColor: Color,
        direction: Vec3
    ): Color {
        val reflected = incident.minus(normal.mult(2.0 * normal.dot(incident)))
        val specularWeight = pow(max(0.0, reflected.dot(direction)), specularPower) * specular
        val diffuseWeight = max(0.0, -incident.dot(normal)) * diffuse
        return lightColor.multiply(color.multiply(specularWeight + diffuseWeight))
    }
}