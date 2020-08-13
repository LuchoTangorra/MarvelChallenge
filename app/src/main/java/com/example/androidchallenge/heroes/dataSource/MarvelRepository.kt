package com.example.androidchallenge.heroes.dataSource

import com.example.androidchallenge.dataSource.api.MarvelAPI
import com.example.androidchallenge.dataSource.NetResult
import com.example.androidchallenge.model.comics.ComicsResponse
import com.example.androidchallenge.model.heroes.HeroesResponse
import com.example.androidchallenge.utils.Constants.marvelPublicAPIkey
import com.example.androidchallenge.utils.Utils
import retrofit2.Response

class MarvelRepository(
    private val marvelAPI: MarvelAPI
) {
    private val authParams = AuthParams(marvelPublicAPIkey, 1, Utils.generateHash())

    suspend fun getHeroes(offset: Int, limit: Int = 15): NetResult<HeroesResponse> =
        handleResult(marvelAPI.getHeroes(authParams.getMap(), offset, limit))

    suspend fun getComics(characterId: Int): NetResult<ComicsResponse> =
        handleResult(marvelAPI.getComics(characterId, authParams.getMap()))

    private fun <T> handleResult(result: Response<T>): NetResult<T> {
        if (result.isSuccessful)
            result.body()
                ?.let { heroes -> return NetResult.Success(heroes) }
        return NetResult.Error(result)
    }
}

class AuthParams(
    private val apikey: String,
    private val ts: Int,
    private val hash: String
) {
    fun getMap(): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        hashMap["apikey"] = apikey
        hashMap["ts"] = ts.toString()
        hashMap["hash"] = hash
        return hashMap
    }
}