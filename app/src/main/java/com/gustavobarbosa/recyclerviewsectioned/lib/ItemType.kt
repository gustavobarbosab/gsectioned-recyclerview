package com.gustavobarbosa.recyclerviewsectioned.lib

import android.support.annotation.IntDef
import com.gustavobarbosa.recyclerviewsectioned.lib.ItemType.Companion.BODY
import com.gustavobarbosa.recyclerviewsectioned.lib.ItemType.Companion.HEADER

@Retention
@IntDef(HEADER, BODY)
annotation class ItemType {
    companion object {
        const val HEADER: Int = 0
        const val BODY: Int = 1
    }
}