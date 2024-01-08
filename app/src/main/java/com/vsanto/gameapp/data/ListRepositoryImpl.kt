package com.vsanto.gameapp.data

import android.util.Log
import com.vsanto.gameapp.data.database.dao.ListDao
import com.vsanto.gameapp.data.database.entity.toEntity
import com.vsanto.gameapp.domain.ListRepository
import com.vsanto.gameapp.domain.model.GameList
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val listDao: ListDao
) : ListRepository {

    override suspend fun getLists(): List<GameList>? {
        runCatching { listDao.getAllList() }.onSuccess {
                return it.map { list -> list.toDomain() }
            }.onFailure { Log.e("game", "Ha ocurrido el error ${it.message}") }

        return null
    }

    override suspend fun getList(id: Int): GameList? {
        runCatching { listDao.getList(id) }.onSuccess {
                return it.toDomain()
            }.onFailure { Log.e("game", "Ha ocurrido el error ${it.message}") }

        return null
    }

    override suspend fun addList(list: GameList) {
        listDao.insert(list.toEntity())
    }

    override suspend fun removeList(listId: Int) {
        listDao.delete(listId)
    }

}