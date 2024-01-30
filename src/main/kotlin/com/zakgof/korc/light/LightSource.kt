package com.zakgof.korc.light

import com.zakgof.korc.math.Color
import com.zakgof.korc.math.Vec3

interface LightSource {

    fun directionAt(position: Vec3) : Vec3

    fun colorAt(position: Vec3) : Color // TODO should combine ?

}
