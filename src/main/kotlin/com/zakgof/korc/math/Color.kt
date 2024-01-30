package com.zakgof.korc.math



data class Color(val r: Double, val g: Double, val b: Double) {

    companion object {
        val BLACK = Color(0.0, 0.0, 0.0)
        val WHITE = Color(1.0, 1.0, 1.0)
    }

    fun multiply(s: Double): Color = Color(s * r, s * g, s * b)
    fun plus(c: Color): Color = Color(r + c.r, r + c.g, r + c.b)
}