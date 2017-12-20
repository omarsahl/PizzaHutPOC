package com.os.pizzahutpoc.utils

/**
 * Created by Omar on 20-Dec-17 10:17 PM
 */

/**
 * Re-maps a number from one range to another.
 */
fun map(value: Float, iStart: Float, iStop: Float, eStart: Float, eStop: Float): Float {
    return eStart + (eStop - eStart) * ((value - iStart) / (iStop - iStart))
}