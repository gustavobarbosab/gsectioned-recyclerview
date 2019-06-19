package com.gustavobarbosa.recyclerviewsectioned.lib.model

import android.support.annotation.IntDef
import com.gustavobarbosa.recyclerviewsectioned.lib.model.ItemType.Companion.BODY
import com.gustavobarbosa.recyclerviewsectioned.lib.model.ItemType.Companion.HEADER

@Retention
@IntDef(HEADER, BODY)
annotation class ItemType {
    companion object {
        const val HEADER: Int = 0
        const val BODY: Int = 1
    }
}