package com.abnamro.apps.referenceandroid.repository

import com.abnamro.apps.referenceandroid.model.Comment
import com.abnamro.apps.referenceandroid.network.CommentsAPIService
import com.abnamro.apps.referenceandroid.network.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface CommentsRepository {
    suspend fun getComments(): NetworkResult<List<Comment>>
}

class CommentsRepositoryImpl(
    private val apiService: CommentsAPIService,
    private val dispatcher: CoroutineDispatcher
) : CommentsRepository {

    override suspend fun getComments(): NetworkResult<List<Comment>> {
        return withContext(dispatcher) {
            try {
                val response = apiService.getComments()
                NetworkResult.Success(response)
            } catch (ignoreError: Exception) {
                NetworkResult.Error(
                    ignoreError.message ?: "Something went wrong.\n Please try again."
                )
            }
        }
    }
}
