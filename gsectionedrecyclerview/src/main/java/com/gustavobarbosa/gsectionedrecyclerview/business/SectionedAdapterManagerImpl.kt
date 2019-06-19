package com.gustavobarbosa.gsectionedrecyclerview.business

import com.gustavobarbosa.gsectionedrecyclerview.model.Section
import java.util.TreeMap

class SectionedAdapterManagerImpl(
    private val listener: SectionedAdapterManager.SectionedManagerListener
) : SectionedAdapterManager {

    // Total size of RecyclerView items
    private var totalSize =
        ZERO
    // <HEADER_START_AT_RECYCLER_VIEW, Section(POSITION_HEADER_AT_ORIGINAL_LIST,BODY_SIZE)>
    private var headerTreeMap = TreeMap<Int, Section>()

    override fun mapAllPositions(headersSize: Int) {
        mapPositionsAtInterval(ZERO, headersSize)
    }

    override fun mapPositionsAtInterval(start: Int, end: Int) {
        evaluateResetMapping(start)
        calcHeaderPositions(start, end)
    }

    override fun headerPositionInRecycler(position: Int): Int = headerTreeMap.floorEntry(position).key

    override fun headerPositionInOriginalList(position: Int): Int =
        headerTreeMap.floorEntry(position).value!!.headerOriginalPosition

    override fun bodyPositionInOriginalList(actualRecyclerPosition: Int): Int =
        actualRecyclerPosition - (headerPositionInRecycler(actualRecyclerPosition) + 1)

    override fun isRecyclerPositionHeader(positionRecycler: Int): Boolean =
        headerTreeMap.containsKey(positionRecycler)

    override fun totalSize(): Int = totalSize

    private fun evaluateResetMapping(start: Int) {
        if (start == ZERO) {
            resetMapping()
        }
    }

    private fun calcHeaderPositions(start: Int, end: Int) {
        for (headerOriginalPosition in start until end) {
            val bodySize = calcBodySize(headerOriginalPosition)
            evaluateHeaderMounting(bodySize, headerOriginalPosition)
        }
    }

    private fun calcBodySize(headerOriginalPosition: Int) = listener.bodySize(headerOriginalPosition)

    private fun evaluateHeaderMounting(bodySize: Int, headerOriginalPosition: Int) {
        if (isBodyNotEmpty(bodySize)) {
            mountHeaderTreeMap(headerOriginalPosition, bodySize)
            calcTotalSize(bodySize)
        }
    }

    private fun isBodyNotEmpty(body: Int) = body != ZERO

    private fun mountHeaderTreeMap(headerOriginalPosition: Int, bodySize: Int) {
        headerTreeMap[totalSize] =
            Section(
                headerOriginalPosition = headerOriginalPosition,
                bodySize = bodySize
            )
    }

    private fun calcTotalSize(bodySize: Int) {
        totalSize += bodySize + 1 // total is body size + header + previous total
    }

    private fun resetMapping() {
        totalSize = ZERO
        headerTreeMap.clear()
    }

    companion object {
        private const val ZERO = 0
    }
}