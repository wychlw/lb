package com.wychlw.work1.data

import kotlinx.coroutines.flow.Flow

class OfflineProjRepository(private val projDao: ProjDao) : ProjRepository {
    override fun getAllProj(): Flow<List<ProjDb>> = projDao.getAllProj()
    override fun getProj(pid: Int): Flow<ProjDb> = projDao.getProj(pid)
    override fun getProjCol(pid: Int): Flow<List<ProjColDb>> = projDao.getProjCol(pid)
    override fun getProjItem(cid: Int): Flow<List<ProjItemDb>> = projDao.getProjItem(cid)
    override fun getProjItemTimeline(iid: Int): Flow<List<ProjItemTimelineDb>> = projDao.getProjItemTimeline(iid)
    override suspend fun insertProj(proj: ProjDb) = projDao.insertProj(proj)
    override suspend fun insertProjCol(projCol: ProjColDb) = projDao.insertProjCol(projCol)
    override suspend fun insertProjItem(projItem: ProjItemDb) = projDao.insertProjItem(projItem)
    override suspend fun insertProjItemTimeline(projItemTimeline: ProjItemTimelineDb) = projDao.insertProjItemTimeline(projItemTimeline)
    override suspend fun updateProj(proj: ProjDb) = projDao.updateProj(proj)
    override suspend fun updateProjCol(projCol: ProjColDb) = projDao.updateProjCol(projCol)
    override suspend fun updateProjItem(projItem: ProjItemDb) = projDao.updateProjItem(projItem)
    override suspend fun updateProjItemTimeline(projItemTimeline: ProjItemTimelineDb) = projDao.updateProjItemTimeline(projItemTimeline)
}