package com.codinginflow.imagesearchapp.ui.gallery

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.codinginflow.imagesearchapp.data.UnsplashRepository

// viewModel holds data from fragments, and fragments observe live data values.
class GalleryViewModel @ViewModelInject constructor (
    private val repository: UnsplashRepository,
@Assisted state: SavedStateHandle)
    : ViewModel() {

   // private val currentQuery = MutableLiveData(DEFAULT_QUERY)
     private val currentQuery = state.getLiveData(DEFAULT_QUERY, CURRENT_QUERY)

    val photos = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "cats"
        private const val CURRENT_QUERY = "current_query"
    }
}