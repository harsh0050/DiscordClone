package com.harsh.discordclone.util

import android.content.Context
import android.util.TypedValue

object UtilMethod {
    fun dpToPixels(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}