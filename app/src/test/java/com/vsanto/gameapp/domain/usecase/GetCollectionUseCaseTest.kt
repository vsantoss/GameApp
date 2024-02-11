package com.vsanto.gameapp.domain.usecase

import com.vsanto.gameapp.domain.GameRepository
import com.vsanto.gameapp.domain.ListRepository
import com.vsanto.gameapp.motherobject.GameMotherObject.gameList
import com.vsanto.gameapp.motherobject.GameMotherObject.userGame
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCollectionUseCaseTest {

    @MockK
    lateinit var gameRepository: GameRepository

    @MockK
    lateinit var listRepository: ListRepository

    private lateinit var collectionUseCase: GetCollectionUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `GetCollectionUseCase should return a correct collection of games and lists`() {
        //Given
        val games = listOf(userGame)
        val lists = listOf(gameList)
        coEvery { gameRepository.getUserGames() } returns games
        coEvery { listRepository.getLists() } returns lists
        collectionUseCase = GetCollectionUseCase(gameRepository, listRepository)

        //When
        val libraryResponse = runBlocking { collectionUseCase.invoke() }

        //Then
        libraryResponse.games shouldBe games
        libraryResponse.lists shouldBe lists
    }
}