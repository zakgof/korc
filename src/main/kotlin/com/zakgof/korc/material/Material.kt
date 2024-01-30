package com.zakgof.korc.material

import com.zakgof.korc.math.Color
import com.zakgof.korc.math.Vec3
import com.zakgof.korc.tracer.ColorRay

interface Material {

    fun interact(ray: ColorRay) : Set<ColorRay>

    fun directLightResponse(point: Vec3, normal: Vec3, incident: Vec3, lightColor: Color, direction: Vec3) : Color // TODO: not optimal using point

}
