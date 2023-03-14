package com.android.cars.data.local.models.topic

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.cars.data.local.models.photo.DbCoverPhoto
import com.android.cars.data.models.topic.Topic
import java.time.LocalDateTime

@Entity(tableName = DbTopic.TABLE_NAME)
data class DbTopic(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: String,

    @ColumnInfo(name = COLUMN_TITLE)
    val title: String,

    @ColumnInfo(name = COLUMN_DESCRIPTION)
    val description: String?,

    @ColumnInfo(name = COLUMN_PUBLISHED_AT)
    val publishedAt: LocalDateTime,

    @Embedded(prefix = COLUMN_COVER_PHOTO)
    val coverPhoto: DbCoverPhoto?
) {
    companion object {
        const val TABLE_NAME = "topics"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_PUBLISHED_AT = "published_at"
        const val COLUMN_COVER_PHOTO = "cover_photo"

        fun createFromModel(topic: Topic) = DbTopic(
            id = topic.id,
            title = topic.title,
            description = topic.description,
            publishedAt = topic.publishedAt,
            coverPhoto = topic.coverPhoto?.let { DbCoverPhoto.createFromModel(it) }
        )

        fun mapToModel(dbTopic: DbTopic) = Topic(
            id = dbTopic.id,
            title = dbTopic.title,
            description = dbTopic.description,
            publishedAt = dbTopic.publishedAt,
            coverPhoto = dbTopic.coverPhoto?.let { DbCoverPhoto.mapToModel(it) }
        )
    }
}