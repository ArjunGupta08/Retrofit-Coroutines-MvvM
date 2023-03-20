# Retrofit Coroutines MvvM

### Today we will learn how to get data through API, by following MVVM architecture.

<img align="left" src="https://miro.medium.com/v2/resize:fit:1400/1*BqFy9rd2_hCtOeHgUY72gg.png">

activity/fragments--> View Models--> Repository class--> Database (Remote/Room).

### Here as you can see, 
 - Our Repository class deals / ask for data from our Database and 
 - Our View Models get data from this Repository class and
 - Our activity/fragments observe that data from View Models.
So it is all about MVVM pattern. Let's start,

## Project SetUp - 

In this project we will use bellow API
- `Used Api` : [https://api.quotable.io/](https://api.quotable.io/quotes)  
- `base url` : https://api.quotable.io/
- `end-point` : quotes

Used Dipendencies - 

    // ViewModel
           implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    
    // LiveData
             implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    //Coroutines   
         implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0"
         implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"

    //Retrofit  
        implementation 'com.squareup.retrofit2:retrofit:2.9.0'
        implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

Here, Project SetUp Done

# Let's Start

 - Now create an `package` `models` and inside that create your `data classes` according to the json of this api.
 - In my case there are two data classes -
   1. QuoteList
   2. Result

 - Now create an `package` `api` inside that create two classes
   1. `interface class` ( QuoteService ) and
   2. `object class` ( RetrofitHelper ).

1. In your interface class ( QuoteService )

  - We have to declare all our end-points here.
  - `@GET("quotes") :` define an function and annotate it as @GET and pass your end-point ("quotes") to get your data from the API.
  - `@Query("page") :` In this function pass an query as parameter for reading quote of an specific page number. 
  -  Now this function will return an response of our quotes in our QuoteList data class.

       interface QuoteService {
            @GET("quotes")
            suspend fun getQuotes(@Query("page") page : Int) : Response<QuoteList>
        }

  - Here we declare an suspend function becouse we don't want to run this api function on our main thread. This is the beauty of coroutines.
    
2. Now inside your object class ( RetrofitHelper )

 - Here we will define our base_url.
 - Create an fun which returns an instance of our Retrofit.

      object RetrofitHelper {
            fun getInstance() : Retrofit {
                return Retrofit.Builder().baseUrl("https://api.quotable.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
      } 

Here, setUp of our Remote database has been done.
    
 - Now create another `package` `repository` and create an kotlin class `QuoteRepository`.

 - as we know this `QuoteRepository class` will interact with our remote `database` so,
 - It will take instance of our QuoteService class so that it can have the access of our all end-points.
First Inside `QuoteRepository`,
 - Declare MutableLiveData and LiveData,
 - LiveData holds MutableLiveData and,
 - MutableLiveData gets data from the function getQuotes
 - Now craete the function getQuotes
 - in getQuotes Function, we will get the data from our QuoteService class and set that into our MutableLiveData.

    class QuotesRepository(private val quoteService: QuoteService) {

        private val quotesLiveData = MutableLiveData<QuoteList>()

        val quotes : LiveData<QuoteList>
        get() = quotesLiveData

        suspend fun getQuotes(page : Int){
            val result = quoteService.getQuotes(page)
            if (result?.body() != null){
                quotesLiveData.postValue(result.body())
            }
        }
    }

- Now create another `package` `viewmodels`, inside that create an kotlin class `MainViewModel` and `MainViewModelFactory`.

- Now this MainViewModel class will take `instance of our QuoteRepository class`.
- First initialise it with init block and inside that call the function getQuotes which we declared in our QuoteRepository class.
- but we declare an `suspend function` so for calling that first we have to launch our `coroutine` and inside that we can call our suspend function.
- now declare an LiveData and this LiveData will hold the LiveData of QuoteRepository class. 

    class MainViewModel(private val repository: QuotesRepository) : ViewModel(){

        init {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getQuotes(1)
            }
        }

        val quotes : LiveData<QuoteList>
            get() = repository.quotes
    }

As you can see here we take our QuoteRepository class as an `parameter` so for that we have to create an `MainViewModelFactory class` and inside that -

    class MainViewModelFactory(private val repository: QuotesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }

### The MvvM setUp is Completed, Now come to our MainActivity class

 - Here initialise our MainViewModel class

        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val repository = QuotesRepository(quoteService)
        lateinit var  mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.quotes.observe(this){
            Log.d("cheezyQuote",it.results)
        }

That's it.

I followed this resource to learn this topic :-

[https://youtu.be/D29vhvGv9Cc](https://youtu.be/D29vhvGv9Cc)
    
