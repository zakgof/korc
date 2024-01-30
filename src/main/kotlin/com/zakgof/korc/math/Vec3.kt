package com.zakgof.korc.math

import kotlin.math.sqrt

data class Vec3(val x: Double, val y: Double, val z: Double) {

    companion object {
        val ZERO = Vec3(0.0, 0.0, 0.0)
        val X = Vec3(1.0, 0.0, 0.0)
        val Y = Vec3(0.0, 1.0, 0.0)
        val Z = Vec3(0.0, 0.0, 1.0)
    }

    fun negate(): Vec3 = Vec3(-x, -y, -z)
    fun cross(s: Vec3) = Vec3(y * s.z - z * s.y, z * s.x - z * s.z, x * s.y - y * s.z)
    fun dot(s: Vec3) = x * s.x + y * s.y + z * s.z
    fun mult(a: Double) = Vec3(a * x, a * y, a * z)
    fun plus(s: Vec3) = Vec3(x + s.x, y + s.y, z + s.z)
    fun minus(s: Vec3) = Vec3(x - s.x, y - s.y, z - s.z)
    fun lengthSquared() = x * x + y * y + z * z
    fun length() = sqrt(lengthSquared())
    fun normalize(): Vec3 = mult(1.0 / length())
}

