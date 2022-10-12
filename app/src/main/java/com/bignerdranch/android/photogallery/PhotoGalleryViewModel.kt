package com.bignerdranch.android.photogallery

import android.app.Application
import androidx.lifecycle.*
import com.bignerdranch.android.photogallery.api.FlickrFetchr
import com.bignerdranch.android.photogallery.api.FlickrResponse

class PhotoGalleryViewModel(private val app: Application) : AndroidViewModel(app) {
    val galleryItemLiveData: LiveData<List<GalleryItem>>

    val flickrFetchr = FlickrFetchr()

    val mutableSearchTerm = MutableLiveData<String>()

    val searchTerm: String
        get() = mutableSearchTerm.value ?: ""

    init {
        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)
        galleryItemLiveData = Transformations.switchMap(mutableSearchTerm) { searchTerm ->
            if (searchTerm.isBlank()) {
                flickrFetchr.fetchPhotos()
            } else {
                flickrFetchr.searchPhotos(searchTerm)
            }
        }
    }

    fun fetchPhotos(query: String = "") {
        QueryPreferences.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }
}