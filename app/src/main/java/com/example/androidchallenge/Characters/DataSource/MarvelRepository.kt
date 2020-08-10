package com.example.androidchallenge.Characters.DataSource

import com.example.androidchallenge.DataSource.API.MarvelAPI
import com.example.androidchallenge.DataSource.NetResult
import com.example.androidchallenge.Model.Characters.Characters
import com.example.androidchallenge.Utils.Constants.marvelPublicAPIkey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import retrofit2.Response

class MarvelRepository(
    private val marvelAPI: MarvelAPI
) {

    @ExperimentalCoroutinesApi
    suspend fun getCharacters(limit: Int = 15): NetResult<Characters> =
        handleResult(marvelAPI.getCharacters(marvelPublicAPIkey, limit))


    private fun <T> handleResult(result: Response<T>): NetResult<T> {
        if (result.isSuccessful)
            result.body()
                ?.let { characters -> return NetResult.Success(characters) }
        return NetResult.Error(result)
    }
}