package com.gustavobarbosa.recyclerviewsectioned.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gustavobarbosa.recyclerviewsectioned.R
import com.gustavobarbosa.recyclerviewsectioned.lib.ItemType
import com.gustavobarbosa.recyclerviewsectioned.lib.SectionedRecyclerAdapter

class MainActivityRecyclerAdapter :
    SectionedRecyclerAdapter<MainActivityRecyclerAdapter.HeaderViewHolder,
        MainActivityRecyclerAdapter.BodyViewHolder,
        List<HeaderModel>>() {

    private var list: MutableList<HeaderModel> = mutableListOf()

    override fun putList(newList: List<HeaderModel>) {
        super.putList(newList)
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header_main, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onCreateBodyViewHolder(parent: ViewGroup): BodyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_body_main, parent, false)
        return BodyViewHolder(view)
    }

    override fun onBindHeaderViewHolder(viewHolder: HeaderViewHolder, headerPosition: Int) {
        val header = list[headerPosition]
        viewHolder.title.text = header.title
    }

    override fun onBindBodyViewHolder(viewHolder: BodyViewHolder, headerPosition: Int, bodyPosition: Int) {
        val header = list[headerPosition]
        val body = header.bodies[bodyPosition]
        viewHolder.title.text = body.value
    }

    class HeaderViewHolder(view: View) : SectionedRecyclerAdapter.SectionedHeaderViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvHeaderMain)
    }
    class BodyViewHolder(view: View) : SectionedRecyclerAdapter.SectionedBodyViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvBodyMain)
    }
}