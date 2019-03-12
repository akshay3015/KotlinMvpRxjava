package com.example.akshayshahane.kotlinmvpdemo.home

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.akshayshahane.kotlinmvpdemo.R

class MainActivity : AppCompatActivity(), GetFlimsContract.View {
    override lateinit var presenter: GetFlimsContract.Presenter
    private lateinit var dialog: ProgressDialog

    override fun showLoader(show: Boolean) {
        if (show) {

            dialog = ProgressDialog(this).also { it?.show() }


        } else {
            dialog?.dismiss()
        }

    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun setAdapter(list: List<ResultsItem>) {
        Toast.makeText(this,"hooooorrrrrrrrray kotlin with MVP and Rx  . btw there are ${list.size} ",Toast.LENGTH_LONG).show()
        println("data $list")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FlimsPresenter(this)
    }

    override fun onResume() {
        super.onResume()


        presenter.subscribe()
    }


    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }
}
