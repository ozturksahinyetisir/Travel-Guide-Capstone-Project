package com.ozturksahinyetisir.travelguideapp.domain.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.RoomDatabase
import com.ozturksahinyetisir.travelguideapp.data.repository.BookmarkRepository
import com.ozturksahinyetisir.travelguideapp.room.TravelDatabase

class TravelViewModel (application: Application): AndroidViewModel(application){
    private val repository:BookmarkRepository

    init {
        val roomDao = TravelDatabase.getDatabase(application).roomDao()
        repository = BookmarkRepository(roomDao)
    }

}