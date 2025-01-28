package com.example.speakx

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import speakx.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ArrayAdapter<String>
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val List: ListView = findViewById(R.id.list)
        val progTop: ProgressBar = findViewById(R.id.progressBar)
        val progBot: ProgressBar = findViewById(R.id.progressBar2)
        val searchView: SearchView = findViewById(R.id.searchView)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        List.adapter = adapter

        mainViewModel.items.observe(this, { items ->
            adapter.clear()
            adapter.addAll(items)
            hideProgressBar(progTop)
            hideProgressBar(progBot)
        })

        mainViewModel.isFetching.observe(this, { isFetching ->
            if (isFetching) {
                if (mainViewModel.scrollDirection == "up") {
                    showProgressBar(progTop)
                } else {
                    showProgressBar(progBot)
                }
            } else {
                hideProgressBar(progTop)
                hideProgressBar(progBot)
            }
        })

        mainViewModel.loadInitialData()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        List.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}

            private var lastFirstVisibleItem = 0

            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                val lastVisiblePosition = List.lastVisiblePosition

                if (!mainViewModel.isFetching.value!! && lastFirstVisibleItem < firstVisibleItem && lastVisiblePosition == adapter.count - 1) {
                    mainViewModel.scrollDirection = "down"
                    mainViewModel.fetchMoreData("down")
                }

                val firstVisiblePosition = List.firstVisiblePosition

                if (!mainViewModel.isFetching.value!! && lastFirstVisibleItem > firstVisibleItem && firstVisiblePosition == 0) {
                    mainViewModel.scrollDirection = "up"
                    mainViewModel.fetchMoreData("up")
                }

                lastFirstVisibleItem = firstVisibleItem
            }
        })
    }

    private fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
    }
}
