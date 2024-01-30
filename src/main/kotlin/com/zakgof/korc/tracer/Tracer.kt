package com.zakgof.korc.tracer

import com.zakgof.korc.camera.Camera
import com.zakgof.korc.light.LightSource
import com.zakgof.korc.math.Color
import com.zakgof.korc.math.Ray
import com.zakgof.korc.math.Vec3
import java.awt.image.RenderedImage

class Tracer(val scene: Scene) {

    fun render(camera: Camera, resX: Int, resY: Int): RenderedImage {
        return createImage(resX, resY) { pixX, pixY ->
            val ray = camera.ray(pixX.toDouble() / resY, pixY.toDouble() / resY) // TODO multisampling TODO pixel center
            val colorRay = ColorRay(ray, Color.WHITE)
            trace(colorRay)
        }
    }

    private fun trace(colorRay: ColorRay): Color {

        if (colorRay.ray == null) {
            return colorRay.color
        }

        val intersection = scene.renderables
            .map { it.intersection(colorRay.ray) }
            .filterNotNull()
            .minWithOrNull(Comparator.comparingDouble { it!!.rayT })


        intersection?.let { hit ->
            val point = hit.position
            val material = hit.material

            val childColorRays = material.interact(colorRay)
            val childColor = childColorRays.fold(Color.BLACK) { acc: Color, childColorRay ->
                acc.plus(trace(childColorRay))
            }

            val directLightColor = scene.lightSources
                .filter { isLit(point, it)}
                .map { material.directLightResponse(point = point,
                    incident = it.directionAt(point),
                    normal = hit.normal,
                    lightColor = it.colorAt(point),
                    direction = colorRay.ray.direction.negate())}
                .fold(Color.BLACK) { acc: Color, color -> acc.plus(color)}

            return childColor.plus(directLightColor)
        }
        return Color(0.8, 0.8, 1.0) // TODO: sky color
    }

    private fun isLit(position: Vec3, light: LightSource): Boolean {
        val ray = Ray(position, light.directionAt(position).negate())
        return !scene.renderables
            .map { it.intersection(ray) }// TODO can be optimized
            .filterNotNull()
            .any()
    }


}


