package com.example.traveltaipeiapplication

import com.example.traveltaipeiapplication.model.Attraction
import com.example.traveltaipeiapplication.model.AttractionResponse
import com.example.traveltaipeiapplication.model.News
import com.example.traveltaipeiapplication.model.NewsResponse
import com.example.traveltaipeiapplication.repository.ConnectionCallback
import com.example.traveltaipeiapplication.repository.HomeRepository
import com.example.traveltaipeiapplication.viewmodel.HomeViewModel
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testHome_getNews() {
        val viewModel = HomeViewModel()
        val repository = mockk<HomeRepository>()
        val slot = CapturingSlot<ConnectionCallback<NewsResponse>>()
        viewModel.setRepository(repository)

        every { repository.getNews(any(), any(), capture(slot)) }.answers {
            var array = arrayOf<News>(
                News("水門何時關", "[水門何時關]", "https://www.google.com"),
                News("兒童節", "[兒童節]", "https://www.google.com")
            )
            var response = NewsResponse(array.size, array)
            slot.captured.onResponse(response)
        }

        viewModel.getNews(null)

        assertEquals(2, viewModel.getAllNews().size)
    }

    @Test
    fun testHome_getAttractions() {
        val viewModel = HomeViewModel()
        val repository = mockk<HomeRepository>()
        val slot = CapturingSlot<ConnectionCallback<AttractionResponse>>()
        viewModel.setRepository(repository)

        every { repository.getAttractions(any(), any(), capture(slot)) }.answers {
            var array = arrayOf<Attraction>(
                    Attraction("優人神鼓", "[優人神鼓]", "Monday ~ Friday", "台北市", "+886", "備註", "https://www.google.com", arrayOf()),
                    Attraction("建國假日玉市", "[建國假日玉市]", "Monday ~ Friday", "台北市", "+886", "備註", "https://www.google.com", arrayOf())
            )
            var response = AttractionResponse(array.size, array)
            slot.captured.onResponse(response)
        }

        viewModel.getAttractions(null)

        assertEquals(2, viewModel.getAllAttractions().size)
    }
}