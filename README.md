# HomeAway-Android-Coding-Challenge

The goal of this sample is to show how to implement a production app that follows [MVVM architecture](https://developer.android.com/jetpack/guide), best practices, leveraging tools like Kotlin Coroutines, StateFlow, Dagger, Glide and more!

Here's a full breakdown of the project:

* Image Loading + Caching is done via [Glide](https://github.com/bumptech/glide)
* Dependency Injection via [Dagger](https://developer.android.com/training/dependency-injection/dagger-android)
* HTTP Client via [Retrofit](https://github.com/square/retrofit)
* JSON Deserialization via [Moshi](https://github.com/square/moshi)
* State + Observable Pattern via [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
* Displaying items in a list via [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView) and [ListAdapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/ListAdapter)

This sample app uses the [FourSquare Places API](https://developer.foursquare.com/docs/places-api/) for powering it's search and other queries. 

# Architecture
<img src="https://github.com/crocsandcoffee/HomeAway-Android-Coding-Challenge/blob/main/demo/architecture.jpeg">

## You can search venues via any keyword and see a list of your results.
### Light Mode
<img src="https://github.com/crocsandcoffee/HomeAway-Android-Coding-Challenge/blob/main/demo/search.png" width="240">

### Dark Mode
<img src="https://github.com/crocsandcoffee/HomeAway-Android-Coding-Challenge/blob/main/demo/search_dark.jpg" width="240">

## Tapping on a venue will launch a details screen with more in depth information about a venue. 
### Light Mode
<img src="https://github.com/crocsandcoffee/HomeAway-Android-Coding-Challenge/blob/main/demo/details.png" width="240">

### Dark Mode
<img src="https://github.com/crocsandcoffee/HomeAway-Android-Coding-Challenge/blob/main/demo/details_dark.jpg" width="240">

### The Google Static Maps API is used to render a map view of the two relevant coordinates, along with additional info.
<img src="https://github.com/crocsandcoffee/HomeAway-Android-Coding-Challenge/blob/main/demo/details.gif" width="240">
