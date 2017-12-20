package com.os.pizzahutpoc.utils

/**
 * Created by Omar on 20-Dec-17 8:53 PM
 */

/**
 * Re-maps a number from one range to another.
 */
fun map(value: Float, iStart: Float, iEnd: Float, oStart: Float, oEnd: Float): Float {
    return oStart + (oEnd - oStart) * ((value - iStart) / (iEnd - iStart))
}