package com.gustavobarbosa.recyclerviewsectioned.lib

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gustavobarbosa.recyclerviewsectioned.lib.ItemType.Companion.BODY
import com.gustavobarbosa.recyclerviewsectioned.lib.ItemType.Companion.HEADER
import java.util.TreeMap

abstract class SectionedRecyclerAdapter<
    HEADER_VH : SectionedRecyclerAdapter.SectionedViewHolder,
    BODY_VH : SectionedRecyclerAdapter.SectionedViewHolder,
    HEADER : List<Header>
    > : RecyclerView.Adapter<SectionedRecyclerAdapter.SectionedViewHolder>() {

    var totalSize = 0
    var hash = TreeMap<Int, Int>() // <HEADER_START,BODY_SIZE>
    private var actualHeaderList = -1
    private var actualHeaderPosition = 0
        set(value) {
            lastHeader = actualHeaderPosition
            field = value
        }

    private var lastHeader: Int = 0

    open fun putList(newList: HEADER) {
        totalSize = 0
        hash.clear()
        actualHeaderList = -1
        actualHeaderPosition = 0
        newList.forEach { header ->
            val bodySize = header.getListBody()?.size ?: 0
            hash[totalSize] = bodySize
            totalSize += bodySize + 1
        }
    }

    abstract fun onCreateHeaderViewHolder(
        parent: ViewGroup
    ): HEADER_VH

    abstract fun onCreateBodyViewHolder(
        parent: ViewGroup
    ): BODY_VH

    init {

    }

    abstract fun onBindHeaderViewHolder(viewHolder: HEADER_VH, headerPosition: Int)

    abstract fun onBindBodyViewHolder(viewHolder: BODY_VH, headerPosition: Int, bodyPosition: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionedViewHolder {
        return when (viewType) {
            HEADER -> {
                onCreateHeaderViewHolder(parent)
            }
            else -> { //BODY
                onCreateBodyViewHolder(parent)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: SectionedViewHolder, position: Int) {
        when (viewHolder.getSectionId()) {
            HEADER -> {
                actualHeaderPosition = hash.floorKey(position)
                actualHeaderList = hash.keys.indexOf(actualHeaderPosition)
                onBindHeaderViewHolder(viewHolder as HEADER_VH, actualHeaderList)
            }
            else -> {
                if (actualHeaderPosition > position) {
                    actualHeaderPosition = hash.floorKey(position)
                    actualHeaderList = hash.keys.indexOf(actualHeaderPosition)
                }
                onBindBodyViewHolder(viewHolder as BODY_VH, actualHeaderList, bodyPosition(actualHeaderPosition, viewHolder.adapterPosition))
            }
        }
    }

    private fun bodyPosition(headerPosition: Int, actualPosition: Int): Int =
        actualPosition - (headerPosition + 1)

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) {
            HEADER
        } else {
            BODY
        }
    }

    private fun isPositionHeader(position: Int) = hash.containsKey(position)

    override fun getItemCount(): Int {
        return totalSize
    }

    open abstract class SectionedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @ItemType
        abstract fun getSectionId(): Int
    }
}