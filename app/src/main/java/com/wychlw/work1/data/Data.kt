package com.wychlw.work1.data

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import com.wychlw.work1.Index.Proj
import com.wychlw.work1.Index.ProjColumn
import com.wychlw.work1.Index.ProjItem
import com.wychlw.work1.Index.ProjItemAction
import com.wychlw.work1.Index.ProjItemStatus
import com.wychlw.work1.Index.ProjItemTimeline
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

//fun getProjDataAll(dao: ProjDao): List<Proj> {
//    val projList = dao.getAllProj()
//    var projData: List<Proj> = listOf()
//    for (proj in projList) {
//        val projColList = dao.getProjCol(proj.pid)
//        var projColData: List<ProjColumn> = listOf()
//        for (projCol in projColList) {
//            val projItemList = dao.getProjItem(projCol.cid)
//            var projItemData: List<ProjItem> = listOf()
//            for (projItem in projItemList) {
//                val projItemTimelineList = dao.getProjItemTimeline(projItem.iid)
//                var projItemTimelineData: List<ProjItemTimeline> = listOf()
//                for (projItemTimeline in projItemTimelineList) {
//                    val action = ProjItemAction.entries[projItemTimeline.action]
//                    val time = LocalDateTime.parse(projItemTimeline.time)
//                    projItemTimelineData += ProjItemTimeline(
//                        tid = projItemTimeline.tid,
//                        by = projItemTimeline.by,
//                        action = action,
//                        time = time,
//                        content = projItemTimeline.content
//                    )
//                }
//                projItemData += ProjItem(
//                    iid = projItem.iid,
//                    id = projItem.iid,
//                    title = projItem.title,
//                    timeline = projItemTimelineData,
//                    status = ProjItemStatus.entries[projItem.status],
//                    assign = projItem.assign
//                )
//            }
//            projColData += ProjColumn(
//                cid = projCol.cid,
//                name = projCol.name,
//                items = projItemData
//            )
//        }
//        projData += Proj(
//            pid = proj.pid,
//            id = proj.id,
//            name = proj.name,
//            columns = projColData
//        )
//    }
//    return projData
//}
//
//fun addProjColumn(dao: ProjDao, proj: Proj, column: ProjColumn): List<Proj> {
//    val projCol = ProjColDb(cid = 0, pid = proj.pid, name = column.name)
//    dao.insertProjCol(projCol)
//    return getProjDataAll(dao)
//}
//
//fun addProjItem(dao: ProjDao, col: ProjColumn, item: ProjItem): List<Proj> {
//    val projItem = ProjItemDb(iid = 0, cid = col.cid, id = item.id, title = item.title, status = item.status.ordinal, assign = item.assign)
//    dao.insertProjItem(projItem)
//    return getProjDataAll(dao)
//}
//
//fun addProjItemTimeline(dao: ProjDao, item: ProjItem, timeline: ProjItemTimeline): List<Proj> {
//    val projItemTimeline = ProjItemTimelineDb(
//        tid = 0,
//        iid = item.iid,
//        by = timeline.by,
//        action = timeline.action.ordinal,
//        time = timeline.time.toString(),
//        content = timeline.content
//    )
//    dao.insertProjItemTimeline(projItemTimeline)
//    return getProjDataAll(dao)
//}
//
//fun updateProj(dao: ProjDao, proj: Proj): List<Proj> {
//    val projDb = ProjDb(pid = proj.pid, id = proj.id, name = proj.name)
//    dao.updateProj(projDb)
//    return getProjDataAll(dao)
//}
//
//fun updateProjColumn(dao: ProjDao, proj: Proj, column: ProjColumn): List<Proj> {
//    val projCol = ProjColDb(cid = column.cid, pid = proj.pid, name = column.name)
//    dao.updateProjCol(projCol)
//    return getProjDataAll(dao)
//}
//
//fun updateProjItem(dao: ProjDao, column: ProjColumn, item: ProjItem): List<Proj> {
//    val projItem = ProjItemDb(iid = item.iid, cid = column.cid, id = item.id, title = item.title, status = item.status.ordinal, assign = item.assign)
//    dao.updateProjItem(projItem)
//    return getProjDataAll(dao)
//}
//
//// In theory you should only be able to inser the timeline of an item, not modify the action history itself?
//// But... Anyway, I put it here
//fun updateProjItemTimeline(dao: ProjDao, item: ProjItem, timeline: ProjItemTimeline): List<Proj> {
//    val projItemTimeline = ProjItemTimelineDb(
//        tid = timeline.tid,
//        iid = item.iid,
//        by = timeline.by,
//        action = timeline.action.ordinal,
//        time = timeline.time.toString(),
//        content = timeline.content
//    )
//    dao.updateProjItemTimeline(projItemTimeline)
//    return getProjDataAll(dao)
//}