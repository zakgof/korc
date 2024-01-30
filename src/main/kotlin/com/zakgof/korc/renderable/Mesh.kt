package com.zakgof.korc.renderable

import com.zakgof.korc.math.Ray
import com.zakgof.korc.math.Vec3
import com.zakgof.korc.tracer.Intersection


class Mesh(val vertices: List<Vec3>, val faces: List<Array<Int>>) : Geometry {

    private var min: Vec3
    private var max: Vec3
    private var rawTris: List<List<Vec3>>

    init {
        min = Vec3(vertices.minOf { it.x }, vertices.minOf { it.y }, vertices.minOf { it.z })
        max = Vec3(vertices.maxOf { it.x }, vertices.maxOf { it.y }, vertices.maxOf { it.z })
        rawTris = faces.map { listOf(vertices[it[0]], vertices[it[1]], vertices[it[2]]) }
    }

    private fun boundingBoxIntersects(ray: Ray): Boolean {
        var tmin: Double
        var tmax: Double
        if (ray.direction.x >= 0.0) {
            tmin = (min.x - ray.start.x) / ray.direction.x
            tmax = (max.x - ray.start.x) / ray.direction.x
        } else {
            tmin = (max.x - ray.start.x) / ray.direction.x
            tmax = (min.x - ray.start.x) / ray.direction.x
        }
        val tminy: Double
        val tmaxy: Double
        if (ray.direction.y >= 0.0) {
            tminy = (min.y - ray.start.y) / ray.direction.y
            tmaxy = (max.y - ray.start.y) / ray.direction.y
        } else {
            tminy = (max.y - ray.start.y) / ray.direction.y
            tmaxy = (min.y - ray.start.y) / ray.direction.y
        }
        if ((tmin > tmaxy) || (tminy > tmax))
            return false;
        if (tminy > tmin)
            tmin = tminy
        if (tmaxy < tmax)
            tmax = tmaxy
        val tminz: Double
        val tmaxz: Double
        if (ray.direction.z >= 0.0) {
            tminz = (min.z - ray.start.z) / ray.direction.z
            tmaxz = (max.z - ray.start.z) / ray.direction.z
        } else {
            tminz = (max.z - ray.start.z) / ray.direction.z
            tmaxz = (min.z - ray.start.z) / ray.direction.z
        }
        if ((tmin > tmaxz) || (tminz > tmax))
            return false
        if (tminz > tmin)
            tmin = tminz
        if (tmaxz < tmax)
            tmax = tmaxz
        return tmin > 0 && tmax > 0
    }

    override fun intersection(ray: Ray): Intersection? {

        if (!boundingBoxIntersects(ray)) {
            return null
        }

        rawTris.forEach {
            val intersection = tri(it[0], it[1], it[2], ray)
            if (intersection != null) {
                return intersection
            }
        }
        return null
    }

    private fun tri(v0: Vec3, v1: Vec3, v2: Vec3, ray: Ray): Intersection? {

        val EPSILON = 0.000001
        val edge1 = v1.minus(v0)
        val edge2 = v2.minus(v0)
        val h = ray.direction.cross(edge2)
        val a = edge1.dot(h)
        if (a > -EPSILON && a < EPSILON) {
            return null
        }
        val f = 1.0 / a
        val s = ray.start.minus(v0)
        val u = f * (s.dot(h))
        if (u < 0.0 || u > 1.0) {
            return null
        }
        val q = s.cross(edge1)
        val v = f * ray.direction.dot(q)
        if (v < 0.0 || u + v > 1.0) {
            return null
        }
        val t = f * edge2.dot(q)
        if (t > EPSILON) {
            val normal = edge1.cross(edge2).normalize()
            if (normal.dot(ray.direction) < 0) {
                return Intersection(
                    rayT = t,
                    position = ray.start.plus(ray.direction.mult(t)),
                    normal = normal
                )
            }
        }
        return null
    }

}