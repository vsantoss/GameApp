package com.vsanto.gameapp.data

import android.util.Log
import com.vsanto.gameapp.data.database.dao.ListDao
import com.vsanto.gameapp.data.database.dao.ListGameDao
import com.vsanto.gameapp.data.database.entity.ListGameCrossRef
import com.vsanto.gameapp.data.database.entity.toEntity
import com.vsanto.gameapp.domain.ListRepository
import com.vsanto.gameapp.domain.model.GameList
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val listDao: ListDao,
    private val listGameDao: ListGameDao
) : ListRepository {

    override suspend fun getLists(): List<GameList>? {
        runCatching { listDao.getListsWithGames() }
            .onSuccess {
                return it.map { list -> list.toDomain() }
            }.onFailure { Log.e("game", "Ha ocurrido el error ${it.message}") }

        return null
    }

    override suspend fun getList(id: Int): GameList? {
        runCatching { listDao.getListWithGames(id) }
            .onSuccess {
                return it.toDomain()
            }.onFailure { Log.e("game", "Ha ocurrido el error ${it.message}") }

        return null
    }

    override suspend fun addList(list: GameList): Int {
        return listDao.insert(list.toEntity()).toInt()
    }

    override suspend fun removeList(listId: Int) {
        listDao.delete(listId)
    }

    override suspend fun addGameToLists(gameId: Int, listIds: List<Int>) {
        listGameDao.insertAll(listIds.map { ListGameCrossRef(listId = it, gameId = gameId) })
    }

}