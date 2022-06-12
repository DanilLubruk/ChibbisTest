package com.example.chibbistest.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {
    var jobs: ArrayList<Job> = arrayListOf()

    override fun onCleared() {
        super.onCleared()
        for (job in jobs) {
            job.cancel()
        }
    }
}