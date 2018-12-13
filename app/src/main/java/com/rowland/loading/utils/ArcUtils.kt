/**
 * ArcUtils.java
 *
 *
 * Copyright (c) 2014 BioWink GmbH.
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.rowland.loading.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF

import java.lang.Math.abs
import java.lang.Math.ceil
import java.lang.Math.cos
import java.lang.Math.floor
import java.lang.Math.sin
import java.lang.Math.sqrt
import java.lang.Math.toRadians

/**
 * Collection of methods to achieve better circular arc drawing, as
 * [Canvas.drawArc] is unreliable.
 *
 *
 * To draw a simple arc, use
 * [.drawArc].
 *
 */
object ArcUtils {
    private val FULL_CIRCLE_RADIANS = toRadians(360.0)

    /**
     * Draws a circular arc on the given `Canvas`.

     * @param canvas       The canvas to draw into.
     * *
     * @param circleCenter The center of the circle on which to draw the arc.
     * *
     * @param circleRadius The radius of the circle on which to draw the arc.
     * *
     * @param startAngle   Starting angle (in degrees) where the arc begins.
     * *
     * @param sweepAngle   Sweep angle (in degrees) measured clockwise.
     * *
     * @param paint        The paint to use then drawing the arc.
     * *
     * *
     * @see .drawArc
     */
    fun drawArc(canvas: Canvas, circleCenter: PointF, circleRadius: Float,
                startAngle: Float, sweepAngle: Float, paint: Paint) {
        canvas.drawPath(createBezierArcDegrees(
                circleCenter, circleRadius, startAngle, sweepAngle, 8, false, null), paint)
    }

    /**
     * Draws a circular arc on the given `Canvas`.

     * @param canvas             The canvas to draw into.
     * *
     * @param circleCenter       The center of the circle on which to draw the arc.
     * *
     * @param circleRadius       The radius of the circle on which to draw the arc.
     * *
     * @param startAngle         Starting angle (in degrees) where the arc begins.
     * *
     * @param sweepAngle         Sweep angle (in degrees) measured clockwise.
     * *
     * @param paint              The paint to use then drawing the arc.
     * *
     * @param arcsPointsOnCircle See [.createBezierArcDegrees].
     * *
     * @param arcsOverlayPoints  See [.createBezierArcDegrees].
     * *
     * *
     * @see .drawArc
     */
    fun drawArc(canvas: Canvas, circleCenter: PointF, circleRadius: Float,
                startAngle: Float, sweepAngle: Float, paint: Paint,
                arcsPointsOnCircle: Int, arcsOverlayPoints: Boolean) {
        canvas.drawPath(createBezierArcDegrees(
                circleCenter, circleRadius, startAngle, sweepAngle,
                arcsPointsOnCircle, arcsOverlayPoints, null), paint)
    }

    /**
     * Normalize the input radians in the range 360° > x >= 0°.

     * @param radians The angle to normalize (in radians).
     * *
     * *
     * @return The angle normalized in the range 360° > x >= 0°.
     */
    fun normalizeRadians(radians: Double): Double {
        var radians = radians
        radians %= FULL_CIRCLE_RADIANS
        if (radians < 0.0) {
            radians += FULL_CIRCLE_RADIANS
        }
        if (radians == FULL_CIRCLE_RADIANS) {
            radians = 0.0
        }
        return radians
    }


    /**
     * Returns the point of a given angle (in radians) on a circle.

     * @param center       The center of the circle.
     * *
     * @param radius       The radius of the circle.
     * *
     * @param angleRadians The angle (in radians).
     * *
     * *
     * @return The point of the given angle on the specified circle.
     * *
     * *
     * @see .pointFromAngleDegrees
     */
    fun pointFromAngleRadians(center: PointF, radius: Float, angleRadians: Double): PointF {
        return PointF((center.x + radius * cos(angleRadians)).toFloat(),
                (center.y + radius * sin(angleRadians)).toFloat())
    }

    /**
     * Returns the point of a given angle (in degrees) on a circle.

     * @param center       The center of the circle.
     * *
     * @param radius       The radius of the circle.
     * *
     * @param angleDegrees The angle (in degrees).
     * *
     * *
     * @return The point of the given angle on the specified circle.
     * *
     * *
     * @see .pointFromAngleRadians
     */
    fun pointFromAngleDegrees(center: PointF, radius: Float, angleDegrees: Float): PointF {
        return pointFromAngleRadians(center, radius, toRadians(angleDegrees.toDouble()))
    }

    /**
     * Adds a circular arc to the given path by approximating it through a cubic Bézier curve.
     *
     *
     *
     *
     * Note that this **does not** split the arc to better approximate it, for that see either:
     *
     *  * [.createBezierArcDegrees]
     *  * [.createBezierArcRadians]
     *
     *
     *
     *
     * For a technical explanation:
     * [
       * http://hansmuller-flex.blogspot.de/2011/10/more-about-approximating-circular-arcs.html
      ](http://hansmuller-flex.blogspot.de/2011/10/more-about-approximating-circular-arcs.html) *

     * @param path        The path to add the arc to.
     * *
     * @param center      The center of the circle.
     * *
     * @param start       The starting point of the arc on the circle.
     * *
     * @param end         The ending point of the arc on the circle.
     * *
     * @param moveToStart If `true`, move to the starting point of the arc
     * *                    (see: [android.graphics.Path.moveTo]).
     * *
     * *
     * @see .createBezierArcDegrees
     * @see .createBezierArcRadians
     */
    fun addBezierArcToPath(path: Path, center: PointF,
                           start: PointF, end: PointF, moveToStart: Boolean) {
        if (moveToStart) {
            path.moveTo(start.x, start.y)
        }
        if (start == end) {
            return
        }

        val ax = (start.x - center.x).toDouble()
        val ay = (start.y - center.y).toDouble()
        val bx = (end.x - center.x).toDouble()
        val by = (end.y - center.y).toDouble()
        val q1 = ax * ax + ay * ay
        val q2 = q1 + ax * bx + ay * by
        val k2 = 4.0 / 3.0 * (sqrt(2.0 * q1 * q2) - q2) / (ax * by - ay * bx)
        val x2 = (center.x + ax - k2 * ay).toFloat()
        val y2 = (center.y.toDouble() + ay + k2 * ax).toFloat()
        val x3 = (center.x.toDouble() + bx + k2 * by).toFloat()
        val y3 = (center.y + by - k2 * bx).toFloat()

        path.cubicTo(x2, y2, x3, y3, end.x, end.y)
    }

    /**
     * Adds a circular arc to the given path by approximating it through a cubic Bézier curve, splitting it if
     * necessary. The precision of the approximation can be adjusted through `pointsOnCircle` and
     * `overlapPoints` parameters.
     *
     *
     * **Example:** imagine an arc starting from 0° and sweeping 100° with a value of
     * `pointsOnCircle` equal to 12 (threshold -> 360° / 12 = 30°):
     *
     *  * if `overlapPoints` is `true`, it will be split as following:
     *
     *  * from 0° to 30° (sweep 30°)
     *  * from 30° to 60° (sweep 30°)
     *  * from 60° to 90° (sweep 30°)
     *  * from 90° to 100° (sweep 10°)
     *
     *
     *  * if `overlapPoints` is `false`, it will be split into 4 equal arcs:
     *
     *  * from 0° to 25° (sweep 25°)
     *  * from 25° to 50° (sweep 25°)
     *  * from 50° to 75° (sweep 25°)
     *  * from 75° to 100° (sweep 25°)
     *
     *
     *
     *
     *
     *
     * For a technical explanation:
     * [
       * http://hansmuller-flex.blogspot.de/2011/10/more-about-approximating-circular-arcs.html
      ](http://hansmuller-flex.blogspot.de/2011/10/more-about-approximating-circular-arcs.html) *

     * @param center            The center of the circle.
     * *
     * @param radius            The radius of the circle.
     * *
     * @param startAngleRadians The starting angle on the circle (in radians).
     * *
     * @param sweepAngleRadians How long to make the total arc (in radians).
     * *
     * @param pointsOnCircle    Defines a *threshold* (360° /`pointsOnCircle`) to split the Bézier arc to
     * *                          better approximate a circular arc, depending also on the value of `overlapPoints`.
     * *                          The suggested number to have a reasonable approximation of a circle is at least 4 (90°).
     * *                          Less than 1 will be ignored (the arc will not be split).
     * *
     * @param overlapPoints     Given the *threshold* defined through `pointsOnCircle`:
     * *
     * *                           * if `true`, split the arc on every angle which is a multiple of the
     * *                          *threshold* (yields better results if drawing precision is required,
     * *                          especially when stacking multiple arcs, but can potentially use more points)
     * *                           * if `false`, split the arc equally so that each part is shorter than
     * *                          the *threshold*
     * *
     * *
     * @param addToPath         An existing path where to add the arc to, or `null` to create a new path.
     * *
     * *
     * @return `addToPath` if it's not `null`, otherwise a new path.
     * *
     * *
     * @see .createBezierArcDegrees
     */
    fun createBezierArcRadians(center: PointF, radius: Float, startAngleRadians: Double,
                               sweepAngleRadians: Double, pointsOnCircle: Int, overlapPoints: Boolean,
                               addToPath: Path?): Path {
        val path = addToPath ?: Path()
        if (sweepAngleRadians == 0.0) {
            return path
        }

        if (pointsOnCircle >= 1) {
            val threshold = FULL_CIRCLE_RADIANS / pointsOnCircle
            if (abs(sweepAngleRadians) > threshold) {
                var angle = normalizeRadians(startAngleRadians)
                var end: PointF
                var start = pointFromAngleRadians(center, radius, angle)
                path.moveTo(start.x, start.y)
                if (overlapPoints) {
                    val cw = sweepAngleRadians > 0 // clockwise?
                    val angleEnd = angle + sweepAngleRadians
                    while (true) {
                        var next = (if (cw) ceil(angle / threshold) else floor(angle / threshold)) * threshold
                        if (angle == next) {
                            next += threshold * if (cw) 1.0 else -1.0
                        }
                        val isEnd = if (cw) angleEnd <= next else angleEnd >= next
                        end = pointFromAngleRadians(center, radius, if (isEnd) angleEnd else next)
                        addBezierArcToPath(path, center, start, end, false)
                        if (isEnd) {
                            break
                        }
                        angle = next
                        start = end
                    }
                } else {
                    val n = abs(ceil(sweepAngleRadians / threshold).toInt())
                    val sweep = sweepAngleRadians / n
                    var i = 0
                    while (i < n) {
                        angle += sweep
                        end = pointFromAngleRadians(center, radius, angle)
                        addBezierArcToPath(path, center, start, end, false)
                        i++
                        start = end
                    }
                }
                return path
            }
        }

        val start = pointFromAngleRadians(center, radius, startAngleRadians)
        val end = pointFromAngleRadians(center, radius, startAngleRadians + sweepAngleRadians)
        addBezierArcToPath(path, center, start, end, true)
        return path
    }

    /**
     * Adds a circular arc to the given path by approximating it through a cubic Bézier curve, splitting it if
     * necessary. The precision of the approximation can be adjusted through `pointsOnCircle` and
     * `overlapPoints` parameters.
     *
     *
     * **Example:** imagine an arc starting from 0° and sweeping 100° with a value of
     * `pointsOnCircle` equal to 12 (threshold -> 360° / 12 = 30°):
     *
     *  * if `overlapPoints` is `true`, it will be split as following:
     *
     *  * from 0° to 30° (sweep 30°)
     *  * from 30° to 60° (sweep 30°)
     *  * from 60° to 90° (sweep 30°)
     *  * from 90° to 100° (sweep 10°)
     *
     *
     *  * if `overlapPoints` is `false`, it will be split into 4 equal arcs:
     *
     *  * from 0° to 25° (sweep 25°)
     *  * from 25° to 50° (sweep 25°)
     *  * from 50° to 75° (sweep 25°)
     *  * from 75° to 100° (sweep 25°)
     *
     *
     *
     *
     *
     *
     * For a technical explanation:
     * [
       * http://hansmuller-flex.blogspot.de/2011/10/more-about-approximating-circular-arcs.html
      ](http://hansmuller-flex.blogspot.de/2011/10/more-about-approximating-circular-arcs.html) *

     * @param center            The center of the circle.
     * *
     * @param radius            The radius of the circle.
     * *
     * @param startAngleDegrees The starting angle on the circle (in degrees).
     * *
     * @param sweepAngleDegrees How long to make the total arc (in degrees).
     * *
     * @param pointsOnCircle    Defines a *threshold* (360° /`pointsOnCircle`) to split the Bézier arc to
     * *                          better approximate a circular arc, depending also on the value of `overlapPoints`.
     * *                          The suggested number to have a reasonable approximation of a circle is at least 4 (90°).
     * *                          Less than 1 will ignored (the arc will not be split).
     * *
     * @param overlapPoints     Given the *threshold* defined through `pointsOnCircle`:
     * *
     * *                           * if `true`, split the arc on every angle which is a multiple of the
     * *                          *threshold* (yields better results if drawing precision is required,
     * *                          especially when stacking multiple arcs, but can potentially use more points)
     * *                           * if `false`, split the arc equally so that each part is shorter than
     * *                          the *threshold*
     * *
     * *
     * @param addToPath         An existing path where to add the arc to, or `null` to create a new path.
     * *
     * *
     * @return `addToPath` if it's not `null`, otherwise a new path.
     * *
     * *
     * @see .createBezierArcRadians
     */
    fun createBezierArcDegrees(center: PointF, radius: Float, startAngleDegrees: Float,
                               sweepAngleDegrees: Float, pointsOnCircle: Int, overlapPoints: Boolean,
                               addToPath: Path?): Path {
        return createBezierArcRadians(center, radius, toRadians(startAngleDegrees.toDouble()), toRadians(sweepAngleDegrees.toDouble()),
                pointsOnCircle, overlapPoints, addToPath)
    }
}