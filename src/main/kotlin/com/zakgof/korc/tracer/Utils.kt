package com.zakgof.korc.tracer

import com.zakgof.korc.math.Color
import java.awt.image.BufferedImage

fun createImage(
    resX: Int,
    resY: Int,
    block: (pixX: Int, pixY: Int) -> Color
): BufferedImage {
    val image = BufferedImage(resX, resY, BufferedImage.TYPE_INT_RGB)
    val raster = image.raster
    val color = DoubleArray(3)
    for (pixX in 0..resX - 1) {
        println(pixX)
        for (pixY in 0..resY - 1) {
            val rgb = block(pixX, pixY)
            color[0] = rgb.r * 255.0
            color[1] = rgb.g * 255.0
            color[2] = rgb.b * 255.0
            raster.setPixel(pixX, resY - 1 - pixY, color)
        }
    }
    return image
}