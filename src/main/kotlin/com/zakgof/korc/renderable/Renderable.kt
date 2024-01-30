package com.zakgof.korc.renderable

import com.zakgof.korc.math.Ray
import com.zakgof.korc.tracer.Intersection

interface Renderable {
    fun intersection(ray: Ray): Intersection?
}