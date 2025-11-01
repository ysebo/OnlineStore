package kg.alatoo.onlinestore.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.alatoo.onlinestore.data.remote.dto.Product
import kg.alatoo.onlinestore.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val productRepository = ProductRepository()

    private val _productsState = MutableStateFlow<ProductsState>(ProductsState.Loading)
    val productsState: StateFlow<ProductsState> = _productsState.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        _productsState.value = ProductsState.Loading
        viewModelScope.launch {
            val result = productRepository.getProducts()
            _productsState.value = if (result.isSuccess) {
                ProductsState.Success(result.getOrNull()!!.products)
            } else {
                ProductsState.Error(result.exceptionOrNull()?.message ?: "Ошибка загрузки")
            }
        }
    }

    fun searchProducts(query: String) {
        if (query.isBlank()) {
            loadProducts()
            return
        }
        _productsState.value = ProductsState.Loading
        viewModelScope.launch {
            val result = productRepository.searchProducts(query)
            _productsState.value = if (result.isSuccess) {
                ProductsState.Success(result.getOrNull()!!.products)
            } else {
                ProductsState.Error(result.exceptionOrNull()?.message ?: "Ошибка поиска")
            }
        }
    }

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }
}

sealed class ProductsState {
    object Loading : ProductsState()
    data class Success(val products: List<Product>) : ProductsState()
    data class Error(val message: String) : ProductsState()
}