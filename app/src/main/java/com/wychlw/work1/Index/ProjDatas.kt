package com.wychlw.work1.Index

import java.time.LocalDateTime

enum class ProjItemAction {
    Open, Add, Move, Convert, Assign, Commit, Close
}

enum class ProjItemStatus {
    Draft, Open, Closed
}

data class ProjItemTimeline(val tid: Int = 0, val by: String = "", val action: ProjItemAction = ProjItemAction.Open, val time: LocalDateTime = LocalDateTime.now(), val content: String = ""){
}

data class ProjItem(val iid: Int = 0, val id: Int = 0, val title: String = "", val timeline: List<ProjItemTimeline> = listOf(), val status: ProjItemStatus = ProjItemStatus.Draft, val assign: String = "") {
}

data class ProjColumn(val cid: Int = 0, val name: String = "", val items: List<ProjItem> = listOf()) {
}

data class Proj(val pid: Int = 0, val id: Int = 0, val name: String = "", val columns: List<ProjColumn> = listOf()) {
}