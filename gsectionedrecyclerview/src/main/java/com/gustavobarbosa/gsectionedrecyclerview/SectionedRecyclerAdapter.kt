package com.gustavobarbosa.gsectionedrecyclerview

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gustavobarbosa.gsectionedrecyclerview.business.SectionedAdapterFactory
import com.gustavobarbosa.gsectionedrecyclerview.business.SectionedAdapterManager
import com.gustavobarbosa.gsectionedrecyclerview.decorator.StickHeaderItemDecoration
import com.gustavobarbosa.gsectionedrecyclerview.model.ItemType
import com.gustavobarbosa.gsectionedrecyclerview.model.ItemType.Companion.BODY
import com.gustavobarbosa.gsectionedrecyclerview.model.ItemType.Companion.HEADER

abstract class SectionedRecyclerAdapter<
        HEADER_VIEW_HOLDER : SectionedRecyclerAdapter.SectionedViewHolder,
        BODY_VIEW_HOLDER : SectionedRecyclerAdapter.SectionedViewHolder> :
    RecyclerView.Adapter<SectionedRecyclerAdapter.SectionedViewHolder>(),
    StickHeaderItemDecoration.StickyHeaderInterface {

    private val listener = object : SectionedAdapterManager.SectionedManagerListener {
        override fun bodySize(headerPosition: Int): Int = getBodySize(headerPosition)
    }

    private val adapterManager = SectionedAdapterFactory.createSectionedAdapterManager(listener)

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
                onBindHeaderViewHolder(
                    viewHolder as HEADER_VIEW_HOLDER,
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

    override fun bindHeaderData(parent: ViewGroup, headerPosition: Int): View {
        val viewHolder = onCreateHeaderViewHolder(parent)
        onBindHeaderViewHolder(
            viewHolder,
            adapterManager.headerPositionInOriginalList(headerPosition)
        )
        return viewHolder.itemView
    }

    override fun isHeader(itemPosition: Int): Boolean =
        adapterManager.isRecyclerPositionHeader(itemPosition)

    override fun getItemCount(): Int = adapterManager.totalSize()

    fun notifySectionedDataChanged() {
        mapPositions()
        notifyDataSetChanged()
    }

    fun notifySectionedDataAdded(initialIndex: Int) {
        mapPositions()
        notifyItemRangeChanged(initialIndex, adapterManager.totalSize())
    }

    abstract fun onCreateHeaderViewHolder(parent: ViewGroup): HEADER_VIEW_HOLDER

    abstract fun onCreateBodyViewHolder(parent: ViewGroup): BODY_VIEW_HOLDER

    abstract fun onBindHeaderViewHolder(viewHolder: HEADER_VIEW_HOLDER, headerPosition: Int)

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

    private fun mapPositions() {
        adapterManager.mapPositions(getHeaderSize())
    }

    fun inflateHeader(parent: ViewGroup): View =
        LayoutInflater.from(parent.context).inflate(getHeaderLayout(), parent, false)

    fun inflateBody(parent: ViewGroup): View =
        LayoutInflater.from(parent.context).inflate(getBodyLayout(), parent, false)
}