package com.hugo.test.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.test.utils.Constants
import kotlinx.android.parcel.Parcelize


@Entity(tableName = Constants.TABLE_NAME_RESIDENTS)
@Parcelize
data class ResidentModel( @ColumnInfo(name = "placa") @PrimaryKey
var placa: String,
@ColumnInfo(name = "model") var model: Int,
@ColumnInfo(name = "owner") var owner: String
) : Parcelable {




}