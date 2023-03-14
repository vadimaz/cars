package com.android.cars.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.cars.data.local.models.topic.DbTopic
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {

    // TODO: don't use REPLACE strategy if you have relations with the data, use Insert and Update in transaction method to save topics
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(topics: List<DbTopic>): List<Long>

    @Query("SELECT * FROM ${DbTopic.TABLE_NAME}")
    fun getAll(): Flow<List<DbTopic>>

    @Query("SELECT * FROM ${DbTopic.TABLE_NAME} WHERE ${DbTopic.COLUMN_ID}=:id")
    fun getTopicById(id: String): Flow<DbTopic>

    @Query("DELETE FROM ${DbTopic.TABLE_NAME}")
    suspend fun deleteAll(): Int // or return nothing if no need to know count of deleted rows
}