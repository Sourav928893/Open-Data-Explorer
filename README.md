📱 Open Data Explorer

Open Data Explorer is a portfolio-ready Android app built using Kotlin + Jetpack Compose.
It fetches data from a public API and displays two lists: Products & Categories, along with detailed views.

✅ Key Features

Fetch data from FakeStore API

Two tabs: Products & Categories

Concurrent API requests using RxKotlin Single.zip

Shimmer UI during loading for better UX

Clean MVVM Architecture

Koin Dependency Injection

Product and Category detail screens

Jetpack Compose Navigation

Proper error handling + retry support

LazyColumn list with smooth scrolling

Coil for image loading

🛠 Tech Stack

Language: Kotlin

UI: Jetpack Compose + Material 3

Architecture: MVVM (Repository + ViewModel)

Networking: Retrofit + RxKotlin

DI: Koin

Image Loading: Coil

Navigation: Compose Navigation

🌐 API Used

FakeStoreAPI
https://fakestoreapi.com/

/products

/products/categories

/products/{id}

/products/category/{category}

📌 App Overview
Screen	Description
Home	Tabs for Products & Categories
Product Detail	Shows title, price, rating, description, image
Category Detail	Shows product list related to selected category
Error UI	Retry action on failures


