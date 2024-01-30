package com.zakgof.korc

import com.zakgof.korc.camera.PerspectiveCamera
import com.zakgof.korc.light.DirectionalLight
import com.zakgof.korc.math.Color
import com.zakgof.korc.math.Vec3
import com.zakgof.korc.renderable.Plane
import com.zakgof.korc.renderable.Sphere
import com.zakgof.korc.tracer.Scene
import com.zakgof.korc.tracer.Tracer
import java.awt.Desktop
import java.awt.image.RenderedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random
import kotlin.time.measureTime


fun main() {
    val scene = Scene(


        (1..100)
            .map {
                Sphere(
                    center = Vec3(
                        Random.nextInt(-4, 5).toDouble(),
                        0.5,
                        Random.nextInt(-4, 5).toDouble(),
                    ),
                    radius = 0.5
                )
            } + Plane(Vec3.ZERO, Vec3.Y),

        listOf(
            DirectionalLight(direction = Vec3(1.0, -1.0, 0.0).normalize(), color = Color(0.5, 0.5, 0.5))
        )
    )

    val camera = PerspectiveCamera(
        viewPoint = Vec3(0.0, 5.0, 15.0),
        direction = Vec3(0.0, 0.0, -1.0),
        up = Vec3(0.0, 1.0, 0.0),
        width = 10.0,
        height = 10.0,
        near = 10.0
    )

    val tracer = Tracer(scene)

    var image: RenderedImage? = null
    val timeTaken = measureTime {
        image = tracer.render(camera, 1280, 1280)
    }
    println("Time " + timeTaken)


    val outputFile = File("render.png")
    ImageIO.write(image, "PNG", outputFile)

    Desktop.getDesktop().open(outputFile)
}