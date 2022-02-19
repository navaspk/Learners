package com.sample.clean.remote


import com.sample.clean.entity.CommunityHomeService
import com.sample.clean.servermodel.CommunityLearnersResponse
import com.sample.core.domain.entity.GsonProvider
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class CommunityHomeListApiTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private var mockWebServer = MockWebServer()
    private lateinit var communityListApi: CommunityHomeService

    @Before
    fun setUp() {
        mockWebServer.start()
        communityListApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                GsonConverterFactory.create(GsonProvider().instance)
            )
            .create(CommunityHomeService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetch community response is successful`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("1.json").content)

        mockWebServer.enqueue(response)
        runBlocking(coroutineTestRule.testDispatcher) {
            val articleListResponse: Single<CommunityLearnersResponse> = communityListApi.getCommunityUsers()
            Truth.assertThat(articleListResponse.isSuccessful).isTrue()
        }
    }

    @Test
    fun `fetch articles response body has desired num_results`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("response.json").content)

        mockWebServer.enqueue(response)
        runBlocking(coroutineTestRule.testDispatcher) {
            val articleListResponse: Single<CommunityLearnersResponse> = communityListApi.getCommunityUsers()
            Truth.assertThat(articleListResponse.body()?.numResults).isEqualTo(5)
        }
    }
}