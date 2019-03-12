package com.example.akshayshahane.kotlinmvpdemo.home

import com.example.akshayshahane.kotlinmvpdemo.BasePresenter
import com.example.akshayshahane.kotlinmvpdemo.BaseView

interface GetFlimsContract {
    interface View : BaseView<Presenter> {

        fun showLoader(show: Boolean)

        fun showError(error: String)

        fun setAdapter(list: List<ResultsItem>)




    }


    interface Presenter : BasePresenter {

        fun fetchFilms()
    }
}