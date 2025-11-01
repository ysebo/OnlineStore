package kg.alatoo.onlinestore.data.repository


import kg.alatoo.onlinestore.data.remote.ApiService
import kg.alatoo.onlinestore.data.remote.dto.LoginRequest
import kg.alatoo.onlinestore.data.remote.dto.LoginResponse
import kg.alatoo.onlinestore.data.remote.retrofit.RetrofitInstance
import retrofit2.HttpException
import java.io.IOException

class AuthRepository {
    private val api: ApiService = RetrofitInstance.api

    suspend fun login(username: String, password: String): Result<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(username, password))
            Result.success(response)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}