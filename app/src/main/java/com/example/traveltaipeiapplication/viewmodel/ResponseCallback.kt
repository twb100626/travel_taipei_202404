package com.example.traveltaipeiapplication.viewmodel

interface ResponseCallback<T> {
    fun onResponse(response: T)
    fun onFailure(e: Exception?)
}