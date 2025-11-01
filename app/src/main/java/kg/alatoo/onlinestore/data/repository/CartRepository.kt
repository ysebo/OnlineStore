package kg.alatoo.onlinestore.data.repository

import kg.alatoo.onlinestore.data.remote.ApiService
import kg.alatoo.onlinestore.data.remote.dto.CartRequest
import kg.alatoo.onlinestore.data.remote.dto.CartResponse
import kg.alatoo.onlinestore.data.remote.retrofit.RetrofitInstance
import retrofit2.HttpException
import java.io.IOException

class CartRepository {
    private val api: ApiService = RetrofitInstance.api

    suspend fun addCart(request: CartRequest): Result<CartResponse> {
        return try {
            val response = api.addCart(request)
            Result.success(response)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    suspend fun getUserCarts(userId: Int): Result<List<CartResponse>> {
        return try {
            val response = api.getUserCarts(userId)
            Result.success(response.carts)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}