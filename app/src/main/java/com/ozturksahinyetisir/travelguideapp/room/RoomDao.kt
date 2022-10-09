package com.ozturksahinyetisir.travelguideapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel


@Dao
interface RoomDao {

    @Insert
    fun insertTravel(travelModel: List<TravelModel>)

    @Query("UPDATE travel SET isBookmark = :tbookmarkStatus WHERE id = :tid")
    fun updateBookmark(tid: Int, tbookmarkStatus: Boolean)

    @Query("SELECT * FROM travel")
    fun getTravel(): List<TravelModel>

    @Query("DELETE FROM travel")
    fun deleteTravel()

    //TODO: create trip status for new recyclerView with Room Database for listing only saved trip list.

}