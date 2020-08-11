package com.example.androidchallenge.Heroes.DataSource

import com.example.androidchallenge.DataSource.API.MarvelAPI
import com.example.androidchallenge.DataSource.NetResult
import com.example.androidchallenge.Model.Heroes.Heroes
import com.example.androidchallenge.Utils.Constants.marvelPublicAPIkey
import retrofit2.Response

class MarvelRepository(
    private val marvelAPI: MarvelAPI
) {

    suspend fun getHeroes(limit: Int = 15): NetResult<Heroes> =
        handleResult(marvelAPI.getHeroes(marvelPublicAPIkey, limit))


    private fun <T> handleResult(result: Response<T>): NetResult<T> {
        if (result.isSuccessful)
            result.body()
                ?.let { heroes -> return NetResult.Success(heroes) }
        return NetResult.Error(result)
    }
}