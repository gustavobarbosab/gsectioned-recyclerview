package com.gustavobarbosa.gsectionedrecyclerview.model

import android.support.annotation.IntDef
import com.gustavobarbosa.gsectionedrecyclerview.model.ItemType.Companion.BODY
import com.gustavobarbosa.gsectionedrecyclerview.model.ItemType.Companion.HEADER

@Retention
@IntDef(HEADER, BODY)
annotation class ItemType {
    companion object {
        const val HEADER: Int = 0
        const val BODY: Int = 1
    }
}