package com.zakgof.korc.camera

import com.zakgof.korc.math.Ray

interface Camera {
    fun ray(x: Double, y: Double): Ray
}
