package com.gustavobarbosa.recyclerviewsectioned.lib.business

import com.gustavobarbosa.recyclerviewsectioned.lib.model.Header

interface SectionedAdapterManager<HEADER_MODEL : List<Header<*>>> {

    fun mapPositions(list: HEADER_MODEL)

    fun headerPositionInRecycler(position: Int): Int

    fun headerPositionInOriginalList(position: Int): Int

    fun bodyPositionInOriginalList(actualRecyclerPosition: Int): Int

    fun isRecyclerPositionHeader(positionRecycler: Int): Boolean

    fun totalSize(): Int
}