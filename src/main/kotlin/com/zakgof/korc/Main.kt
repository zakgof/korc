package com.zakgof.korc

import com.zakgof.korc.camera.PerspectiveCamera
import com.zakgof.korc.light.DirectionalLight
import com.zakgof.korc.material.DiffuseMaterial
import com.zakgof.korc.math.Color
import com.zakgof.korc.math.Vec3
import com.zakgof.korc.renderable.Plane
import com.zakgof.korc.renderable.Renderable
import com.zakgof.korc.renderable.Sphere
import com.zakgof.korc.tracer.Scene
import com.zakgof.korc.tracer.Tracer
import java.awt.Desktop
import java.awt.image.RenderedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random
import kotlin.time.DurationUnit
import kotlin.time.measureTime


fun main() {

    val bunnyMesh = loadBunny()

    val ballMaterials = arrayOf(
        DiffuseMaterial(color = Color(0.2, 1.0, 0.2)),
        DiffuseMaterial(color = Color(1.0, 0.5, 0.5)),
        DiffuseMaterial(mirror =  0.8, diffuse = 0.1, specular = 0.0),
    )

    val scene = Scene(

        (1..5)
            .map {
                Renderable(
                    geometry = Sphere(
                        center = Vec3(
                            Random.nextInt(-4, 5).toDouble(),
                            -0.5,
                            Random.nextInt(-4, 5).toDouble(),
                        ),
                        radius = 0.5
                    ), material = ballMaterials[Random.nextInt(ballMaterials.size)]
                )
            }
                + Renderable(bunnyMesh, DiffuseMaterial(color = Color(0.0, 0.0, 1.0)))
                + Renderable(Plane(Vec3(0.0, -3.0, 0.0), Vec3.Y), DiffuseMaterial()),

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
    val resolution = 1024
    val timeTaken = measureTime {
        image = tracer.render(camera, resolution, resolution)
    }
    println("Time = $timeTaken,  FillRate = ${resolution.toDouble() * resolution.toDouble() / timeTaken.toDouble(DurationUnit.SECONDS)} frags / sec")


    val outputFile = File("render.png")
    ImageIO.write(image, "PNG", outputFile)

    Desktop.getDesktop().open(outputFile)
}