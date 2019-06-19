package com.gustavobarbosa.recyclerviewsectioned.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.gustavobarbosa.recyclerviewsectioned.R
import com.gustavobarbosa.recyclerviewsectioned.example.model.BodyModel
import com.gustavobarbosa.recyclerviewsectioned.example.model.HeaderModel
import com.gustavobarbosa.recyclerviewsectioned.example.model.ModelFactory
import com.gustavobarbosa.recyclerviewsectioned.lib.decorator.StickHeaderItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var calc = 0
    private val model
        get() = ModelFactory.getHeaderList(calc)
    private val adapter = MainRecyclerAdapter(object : MainRecyclerAdapter.OnClickListener {
        override fun onItemClicked(message: String) {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        setupSwipeRefresh()
    }

    private fun setupRecyclerView() {
        rvMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvMain.adapter = adapter
        rvMain.addItemDecoration(StickHeaderItemDecoration(adapter))
        adapter.setList(model)
    }

    private fun setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            calc++
            adapter.addList(
                listOf(
                    HeaderModel("Gustavo added", listOf(BodyModel("olá"))),
                    HeaderModel("Mitoso added", listOf(BodyModel("olá2")))
                )
            )

            swipeRefresh.isRefreshing = false
        }
    }
}
