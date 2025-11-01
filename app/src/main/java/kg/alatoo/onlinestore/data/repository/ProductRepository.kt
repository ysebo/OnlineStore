package kg.alatoo.onlinestore.data.repository

import kg.alatoo.onlinestore.data.remote.ApiService
import kg.alatoo.onlinestore.data.remote.dto.Product
import kg.alatoo.onlinestore.data.remote.dto.ProductsResponse
import kg.alatoo.onlinestore.data.remote.retrofit.RetrofitInstance
import retrofit2.HttpException
import java.io.IOException

class ProductRepository {
    private val api: ApiService = RetrofitInstance.api

    suspend fun getProducts(limit: Int = 20, skip: Int = 0): Result<ProductsResponse> {
        return try {
            val response = api.getProducts(limit, skip)
            Result.success(response)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    suspend fun searchProducts(query: String): Result<ProductsResponse> {
        return try {
            val response = api.searchProducts(query)
            Result.success(response)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    suspend fun getProductById(id: Int): Result<Product> {
        return try {
            val response = api.getProductById(id)
            Result.success(response)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    suspend fun getCategories(): Result<List<String>> {
        return try {
            val response = api.getCategories()
            Result.success(response)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    suspend fun getProductsByCategory(category: String): Result<ProductsResponse> {
        return try {
            val response = api.getProductsByCategory(category)
            Result.success(response)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}