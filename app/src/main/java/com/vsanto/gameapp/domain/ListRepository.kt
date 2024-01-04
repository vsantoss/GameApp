package com.vsanto.gameapp.domain

import com.vsanto.gameapp.domain.model.GameList

interface ListRepository {

    suspend fun getLists(): List<GameList>?

    suspend fun addList(list: GameList)

    suspend fun removeList(listId: Int)

}