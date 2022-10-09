package com.ozturksahinyetisir.travelguideapp.domain.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ozturksahinyetisir.travelguideapp.utils.Converters
import kotlinx.android.parcel.Parcelize

@Parcelize

@Entity(tableName = "travel")
data class TravelModel (
    var title:String,
    val description:String,
    val country:String,

    @TypeConverters(Converters::class)
    val images: MutableList<ImageRoomlist>? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val category:String?=null,
    val city:String,
    var isBookmark:Boolean
): Parcelable{

    @Parcelize
    data class ImageRoomlist (
        val height: Long,
        val width: Long,
        val url: String?=null
    ):Parcelable

}
