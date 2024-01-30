package com.zakgof.korc.renderable

import com.zakgof.korc.math.Ray
import com.zakgof.korc.tracer.Intersection

interface Geometry {
    fun intersection(ray: Ray): Intersection?
}