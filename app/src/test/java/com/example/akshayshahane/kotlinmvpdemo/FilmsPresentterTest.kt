package com.example.akshayshahane.kotlinmvpdemo

import com.example.akshayshahane.kotlinmvpdemo.home.*
import com.example.akshayshahane.kotlinmvpdemo.network.API
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


@RunWith(MockitoJUnitRunner::class)

class FilmsPresentterTest {


    @Mock
    private lateinit var filmsPresenter: GetFlimsContract.Presenter

    @Mock
    private lateinit var filmsView: GetFlimsContract.View

    @Mock
    private lateinit var api: API

    lateinit var swapiRepo: StarWarsRespository
    lateinit var mockServer: MockWebServer

    @Before
    fun setup() {

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        RxJavaPlugins.setIoSchedulerHandler { t -> Schedulers.trampoline() }        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Initialize mock webserver
        mockServer = MockWebServer()
        // Start the local server
        mockServer.start()

        // Get an okhttp client
        val okHttpClient = OkHttpClient.Builder()
                .build()

        // Get an instance of Retrofit
        val retrofit = Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mockServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        // Get an instance of blogService
        api = retrofit.create(API::class.java)
        // Initialized repository
        swapiRepo = StarWarsRespository(api)


        filmsPresenter = FlimsPresenter(filmsView, swapiRepo)
//        filmsView = Mockito.mock<GetFlimsContract.View>(GetFlimsContract.View::class.java)
        // Mock scheduler using RxJava TestScheduler.

    }


    @Test
    // Get a reference to the class under test
    fun createPresenter_setsThePresenterToView() {
        filmsPresenter = FlimsPresenter(filmsView, swapiRepo)
        // Then the presenter is set to the view

        verify(filmsView).presenter = filmsPresenter

    }

    @Test
    fun fetchValidDataShouldLoadIntoView() {

       //TODO implement logic


    }

    @Test
    fun fetchInvalidDataShouldThrowError() {

        val testObserver = TestObserver<Response>()

        val path = "/films/"

        val mockResponse = MockResponse()
                .setResponseCode(500) // Simulate a 500 HTTP Code

        mockServer.enqueue(mockResponse)

        // Call the API
        swapiRepo.films().subscribe(testObserver)

        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        // No values
        testObserver.assertNoValues()

        // One error recorded
        assertEquals(1, testObserver.errorCount())


        // Get the request that was just made
        val request = mockServer.takeRequest()
        // Make sure we made the request to the required path
        assertEquals(path, request.path)




    }


    @After
    @Throws
    fun tearDown() {
        // We're done with tests, shut it down
        mockServer.shutdown()
    }


}