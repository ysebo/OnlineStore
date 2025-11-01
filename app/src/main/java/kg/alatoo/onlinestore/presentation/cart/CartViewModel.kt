package kg.alatoo.onlinestore.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.alatoo.onlinestore.data.remote.dto.CartProduct
import kg.alatoo.onlinestore.data.remote.dto.CartRequest
import kg.alatoo.onlinestore.data.remote.dto.Product
import kg.alatoo.onlinestore.data.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val cartRepository = CartRepository()

    private val _cartItems = MutableStateFlow<Map<Int, CartItem>>(emptyMap())
    val cartItems: StateFlow<Map<Int, CartItem>> = _cartItems.asStateFlow()

    private val _orderState = MutableStateFlow<OrderState>(OrderState.Idle)
    val orderState: StateFlow<OrderState> = _orderState.asStateFlow()

    fun addToCart(product: Product, quantity: Int = 1) {
        val currentItems = _cartItems.value.toMutableMap()
        val existingItem = currentItems[product.id]

        currentItems[product.id] = if (existingItem != null) {
            existingItem.copy(quantity = existingItem.quantity + quantity)
        } else {
            CartItem(product, quantity)
        }

        _cartItems.value = currentItems
    }

    fun removeFromCart(productId: Int) {
        val currentItems = _cartItems.value.toMutableMap()
        currentItems.remove(productId)
        _cartItems.value = currentItems
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
            return
        }

        val currentItems = _cartItems.value.toMutableMap()
        currentItems[productId]?.let {
            currentItems[productId] = it.copy(quantity = quantity)
        }
        _cartItems.value = currentItems
    }

    fun getTotalPrice(): Double {
        return _cartItems.value.values.sumOf { it.product.price * it.quantity }
    }

    fun placeOrder(userId: Int) {
        _orderState.value = OrderState.Loading
        viewModelScope.launch {
            val cartProducts = _cartItems.value.values.map {
                CartProduct(id = it.product.id, quantity = it.quantity)
            }

            val request = CartRequest(userId = userId, products = cartProducts)
            val result = cartRepository.addCart(request)

            _orderState.value = if (result.isSuccess) {
                _cartItems.value = emptyMap()
                OrderState.Success(result.getOrNull()!!)
            } else {
                OrderState.Error(result.exceptionOrNull()?.message ?: "Error")
            }
        }
    }

    fun resetOrderState() {
        _orderState.value = OrderState.Idle
    }
}

data class CartItem(
    val product: Product,
    val quantity: Int
)

sealed class OrderState {
    object Idle : OrderState()
    object Loading : OrderState()
    data class Success(val response: kg.alatoo.onlinestore.data.remote.dto.CartResponse) : OrderState()
    data class Error(val message: String) : OrderState()
}