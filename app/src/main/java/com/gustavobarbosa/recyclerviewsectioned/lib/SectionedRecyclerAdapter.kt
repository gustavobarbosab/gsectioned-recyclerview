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
    var hash = TreeMap<Int, Section>() // <HEADER_START,BODY_SIZE>
    var actualRange: IntRange = IntRange.EMPTY

    open fun putList(newList: HEADER) {
        totalSize = 0
        hash.clear()
        var headerOriginalPosition = 0
        newList.forEach { header ->
            val bodySize = header.getListBody()?.size ?: 0
            hash[totalSize] =
                Section(headerOriginalPosition = headerOriginalPosition, bodySize = bodySize)
            totalSize += bodySize + 1
            headerOriginalPosition++
        }
    }

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
                onBindHeaderViewHolder(viewHolder as HEADER_VH, headerOriginalList(position))
            }
            else -> {
                onBindBodyViewHolder(
                    viewHolder as BODY_VH,
                    headerOriginalList(position),
                    bodyPosition(headerRecyclerPosition(position), viewHolder.adapterPosition)
                )
            }
        }
    }

    private fun headerRecyclerPosition(position: Int) = hash.floorEntry(position).key
    private fun headerOriginalList(position: Int) =
        hash.floorEntry(position).value!!.headerOriginalPosition

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

    abstract fun onCreateHeaderViewHolder(parent: ViewGroup): HEADER_VH

    abstract fun onCreateBodyViewHolder(parent: ViewGroup): BODY_VH

    abstract fun onBindHeaderViewHolder(viewHolder: HEADER_VH, headerPosition: Int)

    abstract fun onBindBodyViewHolder(viewHolder: BODY_VH, headerPosition: Int, bodyPosition: Int)

    abstract class SectionedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @ItemType
        abstract fun getSectionId(): Int
    }

    abstract class SectionedHeaderViewHolder(view: View) : SectionedViewHolder(view) {
        override fun getSectionId(): Int = HEADER
    }

    abstract class SectionedBodyViewHolder(view: View) : SectionedViewHolder(view) {
        override fun getSectionId(): Int = BODY
    }
}