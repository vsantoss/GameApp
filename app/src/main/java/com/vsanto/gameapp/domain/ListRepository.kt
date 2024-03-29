package com.vsanto.gameapp.domain

import com.vsanto.gameapp.domain.model.GameList

interface ListRepository {

    suspend fun getLists(): List<GameList>?

    suspend fun getList(id: Int): GameList?

    suspend fun addList(list: GameList): Int

    suspend fun removeList(listId: Int)

    suspend fun addGameToLists(gameId: Int, listIds: List<Int>)

    suspend fun removeGameFromList(listId: Int, gameId: Int)
}