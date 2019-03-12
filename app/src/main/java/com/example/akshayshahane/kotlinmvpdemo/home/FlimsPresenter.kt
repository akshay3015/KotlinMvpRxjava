package com.example.akshayshahane.kotlinmvpdemo.home

import android.annotation.SuppressLint
import com.example.akshayshahane.kotlinmvpdemo.network.API
import com.example.akshayshahane.kotlinmvpdemo.network.getRetrofitInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FlimsPresenter(private val view: GetFlimsContract.View) : GetFlimsContract.Presenter {


    private lateinit var disposable: Disposable

    init {
        view.presenter = this
    }

    @SuppressLint("CheckResult")
    override fun fetchFilms() {
        view.showLoader(true)
        disposable = getRetrofitInstance(false).create(API::class.java)
                .fetchFilms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            println(it)
                            view.setAdapter(it?.results as List<ResultsItem>)
                            view.showLoader(false)
                        }, {
                    it.printStackTrace()
                    view.showLoader(false)
                    view.showError(it.message.toString())

                }

                )

    }

    override fun subscribe() {
        fetchFilms()

    }

    override fun unsubscribe() {
        disposable?.dispose()
    }

}