package speakx.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.speakx.Model.DataRep

class MainViewModel : ViewModel() {
    private val dataRep = DataRep()
    private val _items = MutableLiveData<MutableList<String>>()
    val items: LiveData<MutableList<String>> = _items

    private val _isFetching = MutableLiveData(false)
    val isFetching: LiveData<Boolean> = _isFetching

    var scrollDirection: String = "down"

    init {
        _items.value = mutableListOf<String>()
    }

    fun loadInitialData() {
        for (i in 101..121) {
            _items.value?.add("Item $i")
        }
    }

    fun fetchMoreData(direction: String) {
        if (_isFetching.value == true) return
        _isFetching.value = true

        val curr = _items.value ?: return
        val last = if (direction == "down") curr.lastOrNull() else curr.firstOrNull()
        val id = last?.split(" ")?.get(1)?.toInt() ?: return

        dataRep.fetchMockData(id, direction) { newItems, _ ->
            if (newItems.isNotEmpty()) {
                if (direction == "down") {
                    curr.addAll(newItems)
                } else {
                    curr.addAll(0, newItems)
                }
                _items.postValue(curr)
            }
            _isFetching.postValue(false)
        }
    }
}
