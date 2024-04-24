package com.example.traveltaipeiapplication.repository

interface ConnectionCallback<T> {
    fun onResponse(response: T)
    fun onFailure(e: Exception?)
}