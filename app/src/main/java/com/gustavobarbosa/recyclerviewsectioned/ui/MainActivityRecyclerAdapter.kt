package com.gustavobarbosa.recyclerviewsectioned.ui

import android.view.View
import android.widget.TextView
import com.gustavobarbosa.recyclerviewsectioned.R
import com.gustavobarbosa.recyclerviewsectioned.lib.SectionedRecyclerAdapter

class MainActivityRecyclerAdapter :
    SectionedRecyclerAdapter<
        MainActivityRecyclerAdapter.HeaderViewHolder,
        MainActivityRecyclerAdapter.BodyViewHolder,
        List<HeaderModel>>() {

    private var list: MutableList<HeaderModel> = mutableListOf()

    override fun putList(newList: List<HeaderModel>) {
        super.putList(newList)
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
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
    }

    override fun getBodyLayout(): Int = R.layout.item_body_main

    override fun getHeaderLayout(): Int = R.layout.item_header_main

    class HeaderViewHolder(view: View) : SectionedRecyclerAdapter.SectionedHeaderViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvHeaderMain)
    }

    class BodyViewHolder(view: View) : SectionedRecyclerAdapter.SectionedBodyViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvBodyMain)
    }
}