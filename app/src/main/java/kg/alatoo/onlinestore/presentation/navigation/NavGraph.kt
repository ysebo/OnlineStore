package kg.alatoo.onlinestore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kg.alatoo.onlinestore.presentation.auth.AuthScreen
import kg.alatoo.onlinestore.presentation.cart.CartScreen
import kg.alatoo.onlinestore.presentation.cart.CartViewModel
import kg.alatoo.onlinestore.presentation.order.OrderSuccessScreen
import kg.alatoo.onlinestore.presentation.products.ProductDetailScreen
import kg.alatoo.onlinestore.presentation.products.ProductListScreen
import kg.alatoo.onlinestore.presentation.products.ProductViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val productViewModel: ProductViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()

    var currentUserId = remember { 0 }

    NavHost(
        navController = navController,
        startDestination = Screen.Auth.route
    ) {
        composable(Screen.Auth.route) {
            AuthScreen(
                onLoginSuccess = { userId ->
                    currentUserId = userId
                    navController.navigate(Screen.Products.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Products.route) {
            ProductListScreen(
                onNavigateToCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onNavigateToDetail = { product ->
                    productViewModel.selectProduct(product)
                    navController.navigate(Screen.ProductDetail.route)
                },
                productViewModel = productViewModel,
                cartViewModel = cartViewModel
            )
        }

        composable(Screen.ProductDetail.route) {
            val selectedProduct = productViewModel.selectedProduct.value
            if (selectedProduct != null) {
                ProductDetailScreen(
                    product = selectedProduct,
                    onNavigateBack = { navController.popBackStack() },
                    cartViewModel = cartViewModel
                )
            }
        }

        composable(Screen.Cart.route) {
            CartScreen(
                userId = currentUserId,
                onNavigateBack = { navController.popBackStack() },
                onOrderSuccess = {
                    navController.navigate(Screen.OrderSuccess.route) {
                        popUpTo(Screen.Products.route)
                    }
                },
                cartViewModel = cartViewModel
            )
        }

        composable(Screen.OrderSuccess.route) {
            OrderSuccessScreen(
                onNavigateToProducts = {
                    navController.navigate(Screen.Products.route) {
                        popUpTo(Screen.Products.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Products : Screen("products")
    object ProductDetail : Screen("product_detail")
    object Cart : Screen("cart")
    object OrderSuccess : Screen("order_success")
}