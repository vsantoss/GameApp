package com.vsanto.gameapp.data.network.response.igdb.common

import com.vsanto.gameapp.domain.model.WebsiteInfo
import com.vsanto.gameapp.motherobject.GameMotherObject.websiteResponse
import io.kotlintest.shouldBe
import org.junit.Test

class WebsiteResponseTest {

    @Test
    fun `toDomain should return a correct Website model`() {
        //Given
        val response = websiteResponse

        //When
        val domain = response.toDomain()

        //Then
        domain.url shouldBe response.url
        domain.info shouldBe WebsiteInfo.find(response.category)!!
    }

}