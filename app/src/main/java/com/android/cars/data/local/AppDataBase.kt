package com.android.cars.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.cars.data.local.dao.TopicDao
import com.android.cars.data.local.models.topic.DbTopic
import com.android.cars.data.local.utils.Converters

@Database(
    entities = [DbTopic::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getTopicsDao(): TopicDao

    companion object {
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, AppDataBase::class.java, NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getTestInstance(context: Context) =
            Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
                .fallbackToDestructiveMigration()
                .build()

        private const val NAME = "database"
    }
}