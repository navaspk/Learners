HLD: This has been implemented based on Clean architecture with MVVM.
Entity: Having enterprise rules, which contains with some com.sample.clean.data classes
Use cases: Taken care of flow of com.sample.clean.data from and to entities
Controller/Presenter: Taking up the com.sample.clean.data and converting to convenient wa for UI
UI: Take care of showing the content to user
Domain consists of business logic which contains server com.sample.clean.data model repository and use cases etc

LLD: 
Platform : Android
Language: Kotlin 
Library used:
1) Hilt: For dependency injection
2) RxJava: For asynchronous update of com.sample.clean.data
3) Glide: Image loading and cache
4) AndroidX
5) Jetpack NavigatorController: For navigating between multiple screen
6) Retrofit: Type safe http client
7) OkHttp: For network call
8) Coroutine: To perform some operation and dispatch on specific context

Retrofit is a type safe http client used to getting com.sample.clean.data from server, it calling to server by using
java interface with many request methods and request params. Okhttp used for network call. 
RxJava is another library added in this to get asynchronous call. This is working based on observer
and observable concept. Now a days RxJava is more important as this is having good feature like able
to perform network call, perform synchronous and asynchronous call, performing background listening.

Application Architecture

Using MVVM with clean architecture


Just clone the repository and build. Make sure your system has active internet connection to download
all the dependencies.



