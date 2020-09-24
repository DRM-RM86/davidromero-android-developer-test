package com.hugo.test.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.test.utils.Constants
import kotlinx.android.parcel.Parcelize


@Entity(tableName = Constants.TABLE_NAME_CARS)
@Parcelize
data class CarModel (@ColumnInfo(name = "placa") @PrimaryKey var placa: String,
                     @ColumnInfo(name = "model") var model: Int,
                     @ColumnInfo(name = "observations") var observations: String,
                     @ColumnInfo(name = "time") var time: Long,
                     @ColumnInfo(name = "isOffice") var isOffice: Int, //1-yes  0-No
                     ) : Parcelable {




}