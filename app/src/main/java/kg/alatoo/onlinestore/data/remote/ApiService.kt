package kg.alatoo.onlinestore.data.remote

import kg.alatoo.onlinestore.data.remote.dto.CartRequest
import kg.alatoo.onlinestore.data.remote.dto.CartResponse
import kg.alatoo.onlinestore.data.remote.dto.CartsListResponse
import kg.alatoo.onlinestore.data.remote.dto.Category
import kg.alatoo.onlinestore.data.remote.dto.LoginRequest
import kg.alatoo.onlinestore.data.remote.dto.LoginResponse
import kg.alatoo.onlinestore.data.remote.dto.Product
import kg.alatoo.onlinestore.data.remote.dto.ProductsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int = 20, @Query("skip") skip: Int = 0): ProductsResponse
    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): ProductsResponse

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @GET("products/categories")
    suspend fun getCategories(): List<Category>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): ProductsResponse

    @POST("carts/add")
    suspend fun addCart(@Body request: CartRequest): CartResponse

    @GET("carts/user/{userId}")
    suspend fun getUserCarts(@Path("userId") userId: Int): CartsListResponse
}

