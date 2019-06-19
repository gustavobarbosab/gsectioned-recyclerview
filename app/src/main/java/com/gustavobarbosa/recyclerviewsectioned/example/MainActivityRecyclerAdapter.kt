package com.gustavobarbosa.recyclerviewsectioned.example

import android.view.View
import android.widget.TextView
import com.gustavobarbosa.recyclerviewsectioned.R
import com.gustavobarbosa.recyclerviewsectioned.lib.SectionedRecyclerAdapter
import com.gustavobarbosa.recyclerviewsectioned.example.model.HeaderModel

class MainActivityRecyclerAdapter(val listener: MainActivityRecyclerAdapter.OnClickListener) :
    SectionedRecyclerAdapter<
        MainActivityRecyclerAdapter.HeaderViewHolder,
        MainActivityRecyclerAdapter.BodyViewHolder>() {

    private var list: MutableList<HeaderModel> = mutableListOf()

    fun putList(newList: List<HeaderModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataChanged()
    }

    override fun onCreateHeaderViewHolder(view: View): HeaderViewHolder = HeaderViewHolder(view)

    override fun onCreateBodyViewHolder(view: View): BodyViewHolder = BodyViewHolder(view)

    override fun onBindHeaderViewHolder(viewHolder: HeaderViewHolder, headerPosition: Int) {
        val header = list[headerPosition]
        viewHolder.title.text = header.title
    }

    override fun onBindBodyViewHolder(viewHolder: BodyViewHolder, headerPosition: Int, bodyPosition: Int) {
        val header = list[headerPosition]
        val body = header.bodies[bodyPosition]
        viewHolder.title.text = body.value
        viewHolder.title.setOnClickListener {
            listener.onItemClicked("header:$headerPosition body:$bodyPosition")
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
        val title: TextView = view.findViewById(R.id.tvBodyMain)
    }

    interface OnClickListener {
        fun onItemClicked(message: String)
    }
}