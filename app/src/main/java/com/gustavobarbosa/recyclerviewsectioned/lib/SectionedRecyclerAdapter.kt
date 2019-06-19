package com.gustavobarbosa.recyclerviewsectioned.lib

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gustavobarbosa.recyclerviewsectioned.lib.business.SectionedAdapterFactory
import com.gustavobarbosa.recyclerviewsectioned.lib.business.SectionedAdapterManager
import com.gustavobarbosa.recyclerviewsectioned.lib.decorator.StickHeaderItemDecoration
import com.gustavobarbosa.recyclerviewsectioned.lib.model.ItemType
import com.gustavobarbosa.recyclerviewsectioned.lib.model.ItemType.Companion.BODY
import com.gustavobarbosa.recyclerviewsectioned.lib.model.ItemType.Companion.HEADER

abstract class SectionedRecyclerAdapter<
    HEADER_VIEW_HOLDER : SectionedRecyclerAdapter.SectionedViewHolder,
    BODY_VIEW_HOLDER : SectionedRecyclerAdapter.SectionedViewHolder> :
    RecyclerView.Adapter<SectionedRecyclerAdapter.SectionedViewHolder>(),
    StickHeaderItemDecoration.StickyHeaderInterface {

    private val listener = object: SectionedAdapterManager.SectionedManagerListener {
        override fun bodySize(headerPosition: Int): Int  = getBodySize(headerPosition)
    }

    private val adapterManager = SectionedAdapterFactory.createSectionedAdapterManager(listener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionedViewHolder {
        return when (viewType) {
            HEADER -> {
                val view = mountView(parent, getHeaderLayout())
                onCreateHeaderViewHolder(view)
            }
            else -> { //BODY
                val view = mountView(parent, getBodyLayout())
                onCreateBodyViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: SectionedViewHolder, position: Int) {
        when (viewHolder.getSectionId()) {
            HEADER -> {
                onBindHeaderViewHolder(
                    viewHolder as HEADER_VIEW_HOLDER ,
                    adapterManager.headerPositionInOriginalList(position)
                )
            }
            else -> { //BODY
                onBindBodyViewHolder(
                    viewHolder as BODY_VIEW_HOLDER,
                    adapterManager.headerPositionInOriginalList(position),
                    adapterManager.bodyPositionInOriginalList(viewHolder.adapterPosition)
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (adapterManager.isRecyclerPositionHeader(position)) {
            HEADER
        } else {
            BODY
        }
    }

    override fun getHeaderPositionForItem(itemPosition: Int): Int =
        adapterManager.headerPositionInRecycler(itemPosition)

    override fun bindHeaderData(header: View, headerPosition: Int) {
        onBindHeaderViewHolder(
            onCreateHeaderViewHolder(header),
            adapterManager.headerPositionInOriginalList(headerPosition)
        )
    }

    override fun isHeader(itemPosition: Int): Boolean =
        adapterManager.isRecyclerPositionHeader(itemPosition)

    override fun getItemCount(): Int = adapterManager.totalSize()

    fun notifySectionedDataChanged() {
        mapAllPositions()
        notifyDataSetChanged()
    }

    fun notifySectionedRangeChanged(start: Int, end: Int) {
        mapPositionsAtInterval(start,end)
        notifyItemRangeChanged(start,end)
    }

    abstract fun onCreateHeaderViewHolder(view: View): HEADER_VIEW_HOLDER

    abstract fun onCreateBodyViewHolder(view: View): BODY_VIEW_HOLDER

    abstract fun onBindHeaderViewHolder(viewHolder: HEADER_VIEW_HOLDER , headerPosition: Int)

    abstract fun onBindBodyViewHolder(viewHolder: BODY_VIEW_HOLDER, headerPosition: Int, bodyPosition: Int)

    @LayoutRes
    abstract fun getBodyLayout(): Int

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

    abstract fun getBodySize(headerPosition: Int): Int

    abstract fun getHeaderSize(): Int

    private fun mapPositionsAtInterval(start: Int, end: Int) {
        adapterManager.mapPositionsAtInterval(start,end)
    }

    private fun mapAllPositions() {
        adapterManager.mapPositions(getHeaderSize())
    }

    private fun mountView(parent: ViewGroup, @LayoutRes layoutRes: Int) =
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
}