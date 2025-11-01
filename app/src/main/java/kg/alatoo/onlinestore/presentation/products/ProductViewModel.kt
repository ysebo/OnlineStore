package kg.alatoo.onlinestore.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.alatoo.onlinestore.data.remote.dto.Category
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

    private val _categoriesState = MutableStateFlow<CategoriesState>(CategoriesState.Loading)
    val categoriesState: StateFlow<CategoriesState> = _categoriesState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    init {
        loadProducts()
        loadCategories()
    }

    fun loadProducts() {
        _productsState.value = ProductsState.Loading
        viewModelScope.launch {
            val result = productRepository.getProducts()
            _productsState.value = if (result.isSuccess) {
                ProductsState.Success(result.getOrNull()!!.products)
            } else {
                ProductsState.Error(result.exceptionOrNull()?.message ?: "Error loading")
            }
        }
    }

    fun searchProducts(query: String) {
        if (query.isBlank()) {
            if (_selectedCategory.value != null) {
                loadProductsByCategory(_selectedCategory.value!!.slug)
            } else {
                loadProducts()
            }
            return
        }
        _productsState.value = ProductsState.Loading
        viewModelScope.launch {
            val result = productRepository.searchProducts(query)
            _productsState.value = if (result.isSuccess) {
                ProductsState.Success(result.getOrNull()!!.products)
            } else {
                ProductsState.Error(result.exceptionOrNull()?.message ?: "Error searching")
            }
        }
    }

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

    fun loadProductById(id: Int) {
        viewModelScope.launch {
            val result = productRepository.getProductById(id)
            if (result.isSuccess) {
                _selectedProduct.value = result.getOrNull()
            }
        }
    }

    private fun loadCategories() {
        _categoriesState.value = CategoriesState.Loading
        viewModelScope.launch {
            val result = productRepository.getCategories()
            _categoriesState.value = if (result.isSuccess) {
                CategoriesState.Success(result.getOrNull()!!)
            } else {
                CategoriesState.Error(result.exceptionOrNull()?.message ?: "Error loading categories")
            }
        }
    }

    fun selectCategory(category: Category?) {
        _selectedCategory.value = category
        if (category == null) {
            loadProducts()
        } else {
            loadProductsByCategory(category.slug)
        }
    }

    private fun loadProductsByCategory(categorySlug: String) {
        _productsState.value = ProductsState.Loading
        viewModelScope.launch {
            val result = productRepository.getProductsByCategory(categorySlug)
            _productsState.value = if (result.isSuccess) {
                ProductsState.Success(result.getOrNull()!!.products)
            } else {
                ProductsState.Error(result.exceptionOrNull()?.message ?: "Error loading categories")
            }
        }
    }
}

sealed class ProductsState {
    object Loading : ProductsState()
    data class Success(val products: List<Product>) : ProductsState()
    data class Error(val message: String) : ProductsState()
}

sealed class CategoriesState {
    object Loading : CategoriesState()
    data class Success(val categories: List<Category>) : CategoriesState()
    data class Error(val message: String) : CategoriesState()
}