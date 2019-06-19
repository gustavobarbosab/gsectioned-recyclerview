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
                    HeaderModel("NEW HEADER 1", listOf(BodyModel("Adicionado", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)." ))),
                    HeaderModel("NEW HEADER 2", listOf(BodyModel("Adicionado 1", "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.")))
                )
            )

            swipeRefresh.isRefreshing = false
        }
    }
}
