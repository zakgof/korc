package com.zakgof.korc.tracer

import com.zakgof.korc.light.LightSource
import com.zakgof.korc.renderable.Renderable

data class Scene(val renderables: List<Renderable>, val lightSources: List<LightSource>) {

}