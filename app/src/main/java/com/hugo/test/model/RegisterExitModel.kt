package com.hugo.test.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.test.utils.Constants

@Entity(tableName = Constants.TABLE_NAME_REGISTER_EXIT_CARS)
 data class RegisterExitModel(
   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "id")
   val id: Int = 0,


   @ColumnInfo(name = "placa")
   val placa:String? ="",


   @ColumnInfo(name = "minutes")
   val minutes:Long? =0,

   )