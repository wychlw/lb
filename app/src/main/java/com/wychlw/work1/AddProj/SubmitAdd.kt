package com.wychlw.work1.AddProj

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.wychlw.work1.AppState
import com.wychlw.work1.Index.IndexUiState
import com.wychlw.work1.data.ProjColDb
import com.wychlw.work1.data.ProjDatabase
import com.wychlw.work1.data.ProjDb
import com.wychlw.work1.data.ProjItemDb
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit

@Composable
fun SubmitAdd(modifier: Modifier = Modifier, state: MutableState<AddProjUiState>) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val appState = AppState.getInstance()
    val indexState = IndexUiState.getInstance()
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        FilledTonalButton(
            modifier = modifier.fillMaxWidth(),
            onClick = {
            val dao = ProjDatabase.getInstance(ctx).projDao()

            if (state.value.projType.value == ProjType.LOCAL) {
                val projDb = ProjDb(
                    pid = 0,
                    name = state.value.projData.value.local!!.name.value,
                    id = 0
                )
                scope.launch {
                    dao.insertProj(projDb)
                    indexState.value.projList.value.plus(projDb)
                    dao.getAllProj().collect() {
                        for (proj in it) {
                            if (proj.name == projDb.name) {
                                dao.insertProjCol(
                                    ProjColDb(
                                        cid = 0,
                                        pid = proj.pid,
                                        name = "Draft"
                                    )
                                )
                                dao.insertProjCol(
                                    ProjColDb(
                                        cid = 0,
                                        pid = proj.pid,
                                        name = "In Progress"
                                    )
                                )
                                dao.insertProjCol(
                                    ProjColDb(
                                        cid = 0,
                                        pid = proj.pid,
                                        name = "Done"
                                    )
                                )
                            }
                        }
                    }
                }
            } else {
                val user = state.value.projData.value.remote!!.user
                val remoteId = state.value.projData.value.remote!!.remote_id
                val token = state.value.projData.value.remote!!.token
                val query = "" +
                        "query {\n" +
                        "  user(login: \"$user\") {\n" +
                        "    projectV2(number: $remoteId){\n" +
                        "      title,\n" +
                        "      items(last: 10){\n" +
                        "        nodes {\n" +
                        "          content {\n" +
                        "            ... on DraftIssue {\n" +
                        "              title,\n" +
                        "              body,\n" +
                        "              assignees(last: 10) {\n" +
                        "                nodes {\n" +
                        "                  name\n" +
                        "                }\n" +
                        "              }\n" +
                        "            }\n" +
                        "            ... on Issue {\n" +
                        "              title,\n" +
                        "              body,\n" +
                        "              assignees(last: 10) {\n" +
                        "                nodes {\n" +
                        "                  name\n" +
                        "                }\n" +
                        "              }\n" +
                        "            }\n" +
                        "          },\n" +
                        "          fieldValues(last:10) {\n" +
                        "            nodes {\n" +
                        "              ... on ProjectV2ItemFieldSingleSelectValue {\n" +
                        "                name,\n" +
                        "              }\n" +
                        "            }\n" +
                        "          }\n" +
                        "        }\n" +
                        "      }\n" +
                        "    },\n" +
                        "  }\n" +
                        "}"
                scope.launch {
                    val url_1p = "https://api.github.com/graphql"
                    val url = "https://api.github.com/graphql?query=$query"
                    // use retrofit to support
                    val client = Retrofit.Builder()
                        .baseUrl(url_1p)
                        .build()
                        .create(OkHttpClient::class.java)
                    val request = okhttp3.Request.Builder()
                        .url(url)
                        .get()
                        .addHeader("Authorization", "Bearer $token")
                        .addHeader("Content-Type", "application/json")
                        .build()
                    val response = client.newCall(request).execute()
                    val json = response.body?.string()
                    val parsed = JSONObject(json)
                    val data = parsed.getJSONObject("data")
                    val user = data.getJSONObject("user")
                    val project = user.getJSONObject("projectV2")
                    val title = project.getString("title")
                    val items = project.getJSONObject("items")
                    val nodes = items.getJSONArray("nodes")

                    val projDb = ProjDb(
                        pid = 0,
                        name = title,
                        id = remoteId.value,
                        remote_id = remoteId.value,
                        user = user.getString("login"),
                        token = token.value
                    )
                    dao.insertProj(projDb)
                    indexState.value.projList.value.plus(projDb)
                    dao.getAllProj().collect() {
                        for (proj in it) {
                            if (proj.name == projDb.name) {
                                dao.insertProjCol(
                                    ProjColDb(
                                        cid = 0,
                                        pid = proj.pid,
                                        name = "Draft"
                                    )
                                )
                                dao.insertProjCol(
                                    ProjColDb(
                                        cid = 0,
                                        pid = proj.pid,
                                        name = "In Progress"
                                    )
                                )
                                dao.insertProjCol(
                                    ProjColDb(
                                        cid = 0,
                                        pid = proj.pid,
                                        name = "Done"
                                    )
                                )
                            }
                        }

                        dao.getProjCol(projDb.pid).collect() {

                            for (i in 0 until nodes.length()) {
                                val node = nodes.getJSONObject(i)
                                val content = node.getJSONObject("content")
                                val title = content.getString("title")
                                val body = content.getString("body")
                                val assignees = content.getJSONObject("assignees")
                                val assigneesNodes = assignees.getJSONArray("nodes")
                                val assignee = assigneesNodes.getJSONObject(0).getString("name")
                                val fieldValues = node.getJSONObject("fieldValues")
                                val fieldValuesNodes = fieldValues.getJSONArray("nodes")
                                val status = fieldValuesNodes.getJSONObject(0).getString("name")
                                val statusInt = when (status) {
                                    "Draft" -> 0
                                    "In Progress" -> 1
                                    "Done" -> 2
                                    else -> 0
                                }

                                var cid = 0
                                it.forEach {
                                    if (it.name == status) {
                                        cid = it.cid
                                    }
                                }

                                dao.insertProjItem(
                                    ProjItemDb(
                                        iid = 0,
                                        cid = cid,
                                        id = projDb.pid,
                                        title = title,
                                        status = statusInt,
                                        assign = assignee
                                    )
                                )
                            }

                        }

                    }
                }
                appState.value.navController.navigateUp()
            }
        }) {
            Text("Confirm Add")
        }
    }
}