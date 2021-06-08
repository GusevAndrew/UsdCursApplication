package ru.gusev.usdcursapplication.utils

import retrofit2.Response
import ru.gusev.usdcursapplication.data.exceptions.FailGetDataFromServer

fun Response<*>.checkResponse() {
    if(code() in 200..204) {
        return
    }
    throw FailGetDataFromServer()
}