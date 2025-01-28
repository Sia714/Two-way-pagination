package com.example.speakx.Model

import android.os.Handler

class DataRep {
    fun fetchMockData(
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
