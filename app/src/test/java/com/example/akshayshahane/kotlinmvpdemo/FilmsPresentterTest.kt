package com.example.akshayshahane.kotlinmvpdemo

import com.example.akshayshahane.kotlinmvpdemo.home.FlimsPresenter
import com.example.akshayshahane.kotlinmvpdemo.home.GetFlimsContract
import com.example.akshayshahane.kotlinmvpdemo.home.Response
import com.example.akshayshahane.kotlinmvpdemo.home.ResultsItem
import com.example.akshayshahane.kotlinmvpdemo.network.API
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber
import okhttp3.MediaType
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner

import retrofit2.HttpException
import java.lang.Exception
import java.lang.NullPointerException
import java.util.concurrent.TimeUnit
import okhttp3.ResponseBody
import java.io.IOException
import org.mockito.BDDMockito.given




@RunWith(MockitoJUnitRunner::class)

class FilmsPresentterTest {


    @Mock
    private lateinit var filmsPresenter: GetFlimsContract.Presenter

    @Mock
    private lateinit var filmsView: GetFlimsContract.View

    @Mock
    private lateinit var api: API


    @Before
    fun setup() {

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        RxJavaPlugins.setIoSchedulerHandler { t -> Schedulers.trampoline() }        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)
        filmsPresenter = FlimsPresenter(filmsView)
//        filmsView = Mockito.mock<GetFlimsContract.View>(GetFlimsContract.View::class.java)
        // Mock scheduler using RxJava TestScheduler.

    }

    @Test
    // Get a reference to the class under test
    fun createPresenter_setsThePresenterToView() {
        filmsPresenter = FlimsPresenter(filmsView)
        // Then the presenter is set to the view

        verify(filmsView).presenter = filmsPresenter

    }

    @Test
    fun fetchValidDataShouldLoadIntoView() {

        filmsPresenter.subscribe()

        verify(filmsView, times(1)).showLoader(true)

        val subscriber = TestSubscriber<Response>()

        subscriber.awaitTerminalEvent(5,TimeUnit.SECONDS)
        subscriber.assertNoErrors()

        verify(filmsView).setAdapter(Mockito.anyList() as List<ResultsItem>)

        verify(filmsView).showLoader(false)


    }

    @Test
    fun  fetchInvalidDataShouldThrowError(){

        `when`(api.fetchFilms()).thenReturn(Observable.error(IOException()))
        filmsPresenter.subscribe()
        val subscriber = TestSubscriber<Response>()

        subscriber.awaitTerminalEvent(5,TimeUnit.SECONDS)

        verify(filmsView, times(1)).showLoader(true)
        verify(filmsView).showError("t")
        verify(filmsView).showLoader(false)


    }






}