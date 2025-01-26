package com.example.speakx

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ArrayAdapter<String>
    private val items = mutableListOf<String>()
    private var isFetching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val packageListView: ListView = findViewById(R.id.list)

        for (i in 101..121) {
            items.add("Item $i")
        }
        val prog = findViewById<ProgressBar>(R.id.progressBar)
        val prog2 = findViewById<ProgressBar>(R.id.progressBar2)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        packageListView.adapter = adapter

        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        packageListView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}

            private var lastFirstVisibleItem = 0

            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                val lastVisiblePosition = packageListView.lastVisiblePosition

                if (!isFetching && lastFirstVisibleItem < firstVisibleItem && lastVisiblePosition == adapter.count - 1) {
                    showShimmerEffect(prog2)
                    isFetching = true
                    fetchMockData(items.last().split(" ")[1].toInt(), "down") { newItems, hasMore ->
                        runOnUiThread {
                            if (newItems.isNotEmpty()) items.addAll(newItems)
                            adapter.notifyDataSetChanged()
                            hideShimmerEffect(prog2)
                            if (!hasMore) {
                                Toast.makeText(this@MainActivity, "No more items below", Toast.LENGTH_SHORT).show()
                            }
                            isFetching = false
                        }
                    }
                }

                val firstVisiblePosition = packageListView.firstVisiblePosition

                if (!isFetching && lastFirstVisibleItem > firstVisibleItem && firstVisiblePosition == 0) {
                    showShimmerEffect(prog)
                    isFetching = true
                    fetchMockData(items.first().split(" ")[1].toInt(), "up") { newItems, hasMore ->
                        runOnUiThread {
                            if (newItems.isNotEmpty()) items.addAll(0, newItems)
                            adapter.notifyDataSetChanged()
                            hideShimmerEffect(prog)
                            if (!hasMore) {
                                Toast.makeText(this@MainActivity, "No more items above", Toast.LENGTH_SHORT).show()
                            }
                            isFetching = false
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem
            }
        })
    }

    private fun showShimmerEffect(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideShimmerEffect(progressBar: ProgressBar) {
        progressBar.visibility = View.INVISIBLE
    }

    private fun fetchMockData(
        id: Int,
        direction: String,
        callback: (List<String>, Boolean) -> Unit
    ) {
        Handler().postDelayed({
            val data = mutableListOf<String>()
            val hasMore: Boolean

            if (direction == "up") {
                for (i in id - 1 downTo id - 20) {
                    if (i > 0) data.add(0, "Item $i")
                }
                hasMore = id >= 20
            } else {
                for (i in id + 1..id + 20) {
                    if (i <= 2000) data.add("Item $i")
                }
                hasMore = id < 2000
            }

            callback(data, hasMore)
        }, 2000)
    }
}
