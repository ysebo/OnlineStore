package kg.alatoo.onlinestore.data.remote.dto

data class CartRequest(
    val userId: Int,
    val products: List<CartProduct>
)

data class CartProduct(
    val id: Int,
    val quantity: Int
)

data class CartResponse(
    val id: Int,
    val products: List<CartProductDetail>,
    val total: Double,
    val discountedTotal: Double,
    val userId: Int,
    val totalProducts: Int,
    val totalQuantity: Int
)

data class CartProductDetail(
    val id: Int,
    val title: String,
    val price: Double,
    val quantity: Int,
    val total: Double,
    val discountPercentage: Double,
    val discountedPrice: Double,
    val thumbnail: String
)

data class CartsListResponse(
    val carts: List<CartResponse>,
    val total: Int
)