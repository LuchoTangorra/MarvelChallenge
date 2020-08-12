package com.example.androidchallenge.heroes.dataSource

import com.example.androidchallenge.dataSource.api.MarvelAPI
import com.example.androidchallenge.dataSource.NetResult
import com.example.androidchallenge.model.heroes.Heroes
import com.example.androidchallenge.utils.Constants.marvelPublicAPIkey
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