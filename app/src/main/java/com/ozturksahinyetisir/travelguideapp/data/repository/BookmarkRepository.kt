package com.ozturksahinyetisir.travelguideapp.data.repository

import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import com.ozturksahinyetisir.travelguideapp.room.RoomDao

class BookmarkRepository(private val roomDao: RoomDao){
    val getTravel: List<TravelModel> = roomDao.getTravel()
}