package com.gustavobarbosa.recyclerviewsectioned

import android.support.v7.widget.CardView
import android.view.View
import android.widget.TextView
import com.gustavobarbosa.gsectionedrecyclerview.SectionedRecyclerAdapter
import com.gustavobarbosa.recyclerviewsectioned.model.HeaderModel

class MainRecyclerAdapter(private val listener: OnClickListener) :
            SectionedRecyclerAdapter<
            MainRecyclerAdapter.HeaderViewHolder,
            MainRecyclerAdapter.BodyViewHolder>() {

    private var list: MutableList<HeaderModel> = mutableListOf()

    fun setList(newList: List<HeaderModel>) {
        list.clear()
        list.addAll(newList)
        notifySectionedDataChanged()
    }

    fun addList(newItems: List<HeaderModel>) {
        val previousSize = list.size
        list.addAll(newItems)
        val newSize = list.size
        notifySectionedRangeChanged(previousSize,newSize)
    }

    override fun onCreateHeaderViewHolder(view: View): HeaderViewHolder =
        HeaderViewHolder(view)

    override fun onCreateBodyViewHolder(view: View): BodyViewHolder =
        BodyViewHolder(view)

    override fun onBindHeaderViewHolder(viewHolder: HeaderViewHolder, headerPosition: Int) {
        val header = list[headerPosition]
        viewHolder.title.text = header.title
    }

    override fun onBindBodyViewHolder(viewHolder: BodyViewHolder, headerPosition: Int, bodyPosition: Int) {
        val header = list[headerPosition]
        val body = header.bodies[bodyPosition]
        viewHolder.titleText.text = body.value
        viewHolder.contentText.text = body.content
        viewHolder.container.setOnClickListener {
            listener.onItemClicked("header:$headerPosition\n body:$bodyPosition")
        }
    }

    override fun getBodyLayout(): Int = R.layout.item_body_main

    override fun getHeaderLayout(): Int = R.layout.item_header_main

    override fun getBodySize(headerPosition: Int): Int = list[headerPosition].bodies.size

    override fun getHeaderSize(): Int = list.size

    class HeaderViewHolder(view: View) : SectionedRecyclerAdapter.SectionedHeaderViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvHeaderMain)
    }

    class BodyViewHolder(view: View) : SectionedRecyclerAdapter.SectionedBodyViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.tvBodyMain)
        val contentText: TextView = view.findViewById(R.id.tvBodyContentMain)
        val container: CardView = view.findViewById(R.id.cvContainerMain)
    }

    interface OnClickListener {
        fun onItemClicked(message: String)
    }
}