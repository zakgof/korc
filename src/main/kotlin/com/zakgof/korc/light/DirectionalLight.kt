package com.zakgof.korc.light

import com.zakgof.korc.math.Color
import com.zakgof.korc.math.Vec3

class DirectionalLight (val direction: Vec3, val color: Color) : LightSource {

    override fun directionAt(position: Vec3): Vec3 = direction

    override fun colorAt(position: Vec3): Color = color
}