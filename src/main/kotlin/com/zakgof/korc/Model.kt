package com.zakgof.korc

import com.zakgof.korc.math.Vec3
import com.zakgof.korc.renderable.Mesh
import com.zakgof.korc.tracer.Scene
import de.javagl.obj.ObjReader


fun loadBunny(): Mesh {
    val stream = Scene::class.java.getResourceAsStream("/bunny.obj")
    val obj = ObjReader.read(stream)

    return Mesh(vertices =
    (1..obj.numVertices)
        .map { obj.getVertex(it - 1) }
        .map { Vec3(it.x.toDouble(), it.y.toDouble(), it.z.toDouble()) },
        faces =
        (1..obj.numFaces)
            .map { obj.getFace(it - 1) }
            .map { face -> arrayOf(face.getVertexIndex(0), face.getVertexIndex(1), face.getVertexIndex(2)) }
    )

}

fun loadSingleTri(): Mesh {
    return Mesh(
        vertices = listOf(Vec3(0.0, 0.0, 0.0), Vec3(1.0, 0.0, 0.0), Vec3(0.0, 1.0, 0.0)),
        faces = listOf(arrayOf(0, 1, 2))
    )
}



