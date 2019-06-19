package com.gustavobarbosa.gsectionedrecyclerview.business

interface SectionedAdapterManager {

    fun mapPositionsAtInterval(start: Int, end: Int)

    fun mapAllPositions(headerSize: Int)

    fun headerPositionInRecycler(position: Int): Int

    fun headerPositionInOriginalList(position: Int): Int

    fun bodyPositionInOriginalList(actualRecyclerPosition: Int): Int

    fun isRecyclerPositionHeader(positionRecycler: Int): Boolean

    fun totalSize(): Int

    interface SectionedManagerListener {
        fun bodySize(headerPosition: Int): Int
    }
}