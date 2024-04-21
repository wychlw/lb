package com.wychlw.work1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjDao {
    @Query("SELECT * FROM proj")
    fun getAllProj(): Flow<List<ProjDb>>

    @Query("SELECT * FROM proj WHERE pid = :pid")
    fun getProj(pid: Int): Flow<ProjDb>

    @Query("SELECT * FROM projCol WHERE pid = :pid")
    fun getProjCol(pid: Int): Flow<List<ProjColDb>>

    @Query("SELECT * FROM projItem WHERE cid = :cid")
    fun getProjItem(cid: Int): Flow<List<ProjItemDb>>

    @Query("SELECT * FROM projItemTimeline WHERE iid = :iid")
    fun getProjItemTimeline(iid: Int): Flow<List<ProjItemTimelineDb>>

    @Insert
    suspend fun insertProj(proj: ProjDb)

    @Insert
    suspend fun insertProjCol(projCol: ProjColDb)

    @Insert
    suspend fun insertProjItem(projItem: ProjItemDb)

    @Insert
    suspend fun insertProjItemTimeline(projItemTimeline: ProjItemTimelineDb)

    @Update
    suspend fun updateProj(proj: ProjDb)

    @Update
    suspend fun updateProjCol(projCol: ProjColDb)

    @Update
    suspend fun updateProjItem(projItem: ProjItemDb)

    @Update
    suspend fun updateProjItemTimeline(projItemTimeline: ProjItemTimelineDb)
}