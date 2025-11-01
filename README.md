# ShopHub - Online Store Android App

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5.0-blue.svg)](https://developer.android.com/jetpack/compose)
[![Android](https://img.shields.io/badge/Android-8.0%2B-green.svg)](https://www.android.com)


## Features

###  Authentication
- **User Login** - Using demo username and password

### Shopping Experience
- **Product Catalog** - Browse extensive product collection with grid layout
- **Smart Search** - Real-time product search functionality
- **Category Filtering** - Filter products by 24+ categories (Beauty, Electronics, Fashion, etc.)
- **Product Details** - View comprehensive product information with image gallery
- **Shopping Cart** - Add, update, and remove products from cart
- **Quantity Management** - Adjust product quantities with stock validation

## Download

### Latest Release
Download the latest APK from [Releases page](https://github.com/ysebo/OnlineStore/releases)

### Manual Installation
1. Download the APK file
2. Enable "Install from Unknown Sources" in Android settings
3. Open the APK file and install
4. Launch ShopHub app

**Minimum Requirements:**
- Android 8.0 (API 26) or higher
- 50 MB free space

### Order Management
- **Quick Checkout** - Place orders with one tap
- **Order Confirmation** - Visual feedback on successful orders
- **Cart Persistence** - Cart state maintained across sessions

### User Interface
- **Material 3 Design** - Modern, beautiful UI following Material Design guidelines
- **Smooth Animations** - Fluid transitions and interactions
- **Responsive Layout** - Optimized for different screen sizes
- **Badge Notifications** - Visual cart item counter

##  Architecture

### Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit + OkHttp
- **JSON Parsing**: Gson
- **Image Loading**: Coil
- **Async**: Kotlin Coroutines + Flow
- **Dependency Injection**: Manual (ViewModels)

### Project Structure
```
kg.alatoo.onlinestore/
├── data/
│   ├── remote/
│   │   ├── dto/          # Data Transfer Objects
│   │   ├── ApiService.kt # API endpoints
│   │   └── retrofit/RetrofitClient.kt
│   └── repository/       # Data repositories
├── presentation/
│   ├── auth/            # Login & Registration screens
│   ├── products/        # Product list & details
│   └── cart/            # Shopping cart
└── navigation/          # App navigation setup
```

## Test Credentials

The app uses [DummyJSON API](https://dummyjson.com) for testing. You can use any of these demo accounts(logins ) below:

### Quick Login Credentials

| Username | Password | Name |
|----------|----------|------|
| `emilys` | `emilyspass` | Emily Johnson |
| `michaelw` | `michaelwpass` | Michael Williams |
| `sophiab` | `sophiabpass` | Sophia Brown |
| `jamesd` | `jamesdpass` | James Davis |
| `emmaj` | `emmajpass` | Emma Miller |
| `oliviaw` | `oliviawpass` | Olivia Wilson |
| `alexanderj` | `alexanderjpass` | Alexander Jones |
| `avat` | `avatpass` | Ava Taylor |

### Registration
I wanted to create a new account - the app will simulate registration (data won't be persisted on the server,so we just need to login using existed logins)

**Note**: These are demo accounts from DummyJSON API. In production, implement proper authentication with secure password hashing.

## Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17 or higher
- Android SDK (API 26+)
- Gradle 8.0+

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/ysebo/OnlineStore.git
cd OnlineStore
```

2. **Open in Android Studio**
    - Open Android Studio
    - Select "Open an Existing Project"
    - Navigate to the cloned repository

3. **Sync Gradle**
    - Wait for Gradle sync to complete
    - Download all dependencies

4. **Run the app**
    - Connect an Android device or start an emulator
    - Click "Run" button or press `Shift + F10`

### Configuration

The app uses DummyJSON API (no API key required):
- **Base URL**: `https://dummyjson.com/`
- **Documentation**: [DummyJSON Docs](https://dummyjson.com/docs)

## 📱 Screenshots

### Main Features
- **Product Catalog** - Grid view with search and category filters
- **Product Details** - Full product information with add to cart
- **Shopping Cart** - Manage cart items with quantity controls
- **Authentication** - Login


## API Endpoints

### Authentication
- `POST /auth/login` - User login
- `POST /users/add` - User registration

### Products
- `GET /products` - Get all products
- `GET /products/{id}` - Get product by ID
- `GET /products/search?q={query}` - Search products
- `GET /products/categories` - Get all categories
- `GET /products/category/{category}` - Get products by category

### Orders
- `POST /carts/add` - Create order (add cart)


## Author

**Erdan Aitkaziev**
- GitHub: [@ysebo](https://github.com/ysebo)
- Email: erdan.aitkaziev@alatoo.edu.kg

## Usages

- [DummyJSON](https://dummyjson.com) - Free fake REST API 


