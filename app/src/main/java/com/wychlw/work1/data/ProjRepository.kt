package com.wychlw.work1.data

import kotlinx.coroutines.flow.Flow

interface ProjRepository {
    fun getAllProj(): Flow<List<ProjDb>>
    fun getProj(pid: Int): Flow<ProjDb>
    fun getProjCol(pid: Int): Flow<List<ProjColDb>>
    fun getProjItem(cid: Int): Flow<List<ProjItemDb>>
    fun getProjItemTimeline(iid: Int): Flow<List<ProjItemTimelineDb>>
    suspend fun insertProj(proj: ProjDb)
    suspend fun insertProjCol(projCol: ProjColDb)
    suspend fun insertProjItem(projItem: ProjItemDb)
    suspend fun insertProjItemTimeline(projItemTimeline: ProjItemTimelineDb)
    suspend fun updateProj(proj: ProjDb)
    suspend fun updateProjCol(projCol: ProjColDb)
    suspend fun updateProjItem(projItem: ProjItemDb)
    suspend fun updateProjItemTimeline(projItemTimeline: ProjItemTimelineDb)

}