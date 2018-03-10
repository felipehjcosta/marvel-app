/*
 * Copyright 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.felipecosta.kotlinrxjavasample.util


fun constrain(min: Float, max: Float, v: Float): Float {
    return Math.max(min, Math.min(max, v))
}

fun interpolate(x1: Float, x2: Float, f: Float): Float {
    return x1 + (x2 - x1) * f
}

fun uninterpolate(x1: Float, x2: Float, v: Float): Float {
    if (x2 - x1 == 0f) {
        throw IllegalArgumentException("Can't reverse interpolate with domain size of 0")
    }
    return (v - x1) / (x2 - x1)
}

fun floorEven(num: Int): Int {
    return num and 0x01.inv()
}

fun roundMult4(num: Int): Int {
    return num + 2 and 0x03.inv()
}

// divide two integers but round up
// see http://stackoverflow.com/a/7446742/102703
fun intDivideRoundUp(num: Int, divisor: Int): Int {
    val sign = (if (num > 0) 1 else -1) * if (divisor > 0) 1 else -1
    return sign * (Math.abs(num) + Math.abs(divisor) - 1) / Math.abs(divisor)
}