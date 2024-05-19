package com.wychlw.work1.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proj")
data class ProjDb(
    @PrimaryKey(autoGenerate = true)
    val pid: Int,
    @ColumnInfo(name = "projName")
    val name: String,
    @ColumnInfo(name = "id")
    val id: Int,

    // For remote use
    @ColumnInfo(name = "remote_id")
    val remote_id: Int = 0,
    @ColumnInfo(name = "user")
    val user: String = "",
    @ColumnInfo(name = "token")
    val token: String = ""
)

@Entity(tableName = "projCol")
data class ProjColDb(
    @PrimaryKey(autoGenerate = true)
    val cid: Int,
    @ColumnInfo(name = "pid")
    val pid: Int,
    @ColumnInfo(name = "name")
    val name: String
)

@Entity(tableName = "projItem")
data class ProjItemDb(
    @PrimaryKey(autoGenerate = true)
    val iid: Int,
    @ColumnInfo(name = "cid")
    val cid: Int,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "status")
    val status: Int,
    @ColumnInfo(name = "assign")
    val assign: String
)

@Entity(tableName = "projItemTimeline")
data class ProjItemTimelineDb(
    @PrimaryKey(autoGenerate = true)
    val tid: Int,
    @ColumnInfo(name = "iid")
    val iid: Int,
    @ColumnInfo(name = "by")
    val by: String,
    @ColumnInfo(name = "action")
    val action: Int,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "content")
    val content: String
)


