package com.vsanto.gameapp.data.network.response.igdb.common

import com.vsanto.gameapp.motherobject.GameMotherObject.imageResponse
import io.kotlintest.shouldBe
import org.junit.Test

class ImageResponseTest {

    @Test
    fun `toDomain should return a correct Image model`() {
        //Given
        val response = imageResponse

        //When
        val model = response.toDomain(ImageSize.BIG)

        //Then
        model.url shouldBe "https://images.igdb.com/igdb/image/upload/" + ImageSize.BIG.value + "/wiqrk8nif7zntocaj9vf.png"
    }

}