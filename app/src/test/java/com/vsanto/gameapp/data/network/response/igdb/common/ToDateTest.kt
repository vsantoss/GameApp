package com.vsanto.gameapp.data.network.response.igdb.common

import com.vsanto.gameapp.motherobject.GameMotherObject.timestampResponse
import io.kotlintest.shouldBe
import org.junit.Test
import java.util.Date
import org.junit.Assert.assertTrue

class ToDateTest {

    @Test
    fun `toDate should return a correct Date`() {
        //Given
        val timestamp = timestampResponse
        val currentDate = Date()

        //When
        val date = toDate(timestamp)

        //Then
        assertTrue(date!!.before(currentDate))
    }

    @Test
    fun `toDate of null should return null`() {
        //Given
        //When
        val date = toDate(null)

        //Then
        date shouldBe null
    }

}