package com.gustavobarbosa.recyclerviewsectioned.lib.business

import com.gustavobarbosa.recyclerviewsectioned.lib.model.Header
import com.gustavobarbosa.recyclerviewsectioned.lib.model.Section
import java.util.TreeMap

class SectionedAdapterManagerImpl<HEADER_MODEL : List<Header<*>>>
    : SectionedAdapterManager<HEADER_MODEL> {

    private var totalSize = 0
    private var headerTreeMap = TreeMap<Int, Section>() // <HEADER_START,BODY_SIZE>

    override fun mapPositions(list: HEADER_MODEL) {
        resetVariables()
        list.forEachIndexed { headerOriginalPosition, header ->
            val bodySize = calcBodySize(header)
            if (isBodyNotEmpty(bodySize)) {
                mountHeaderTreeMap(headerOriginalPosition, bodySize)
                reCalcTotal(bodySize)
            }
        }
    }

    override fun headerPositionInRecycler(position: Int): Int = headerTreeMap.floorEntry(position).key

    override fun headerPositionInOriginalList(position: Int): Int =
        headerTreeMap.floorEntry(position).value!!.headerOriginalPosition

    override fun bodyPositionInOriginalList(actualRecyclerPosition: Int): Int =
        actualRecyclerPosition - (headerPositionInRecycler(actualRecyclerPosition) + 1)

    override fun isRecyclerPositionHeader(positionRecycler: Int): Boolean =
        headerTreeMap.containsKey(positionRecycler)

    override fun totalSize(): Int = totalSize

    private fun reCalcTotal(bodySize: Int) {
        totalSize += bodySize + 1 // total is body size + header + previous total
    }

    private fun mountHeaderTreeMap(headerOriginalPosition: Int, bodySize: Int) {
        headerTreeMap[totalSize] =
            Section(headerOriginalPosition = headerOriginalPosition, bodySize = bodySize)
    }

    private fun calcBodySize(header: Header<*>) = header.getListBody()?.size ?: 0

    private fun isBodyNotEmpty(body: Int) = body != 0

    private fun resetVariables() {
        totalSize = 0
        headerTreeMap.clear()
    }
}