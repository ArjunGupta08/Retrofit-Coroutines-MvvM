# Retrofit Coroutines MvvM

### This application is based on MvvM architechture and getting data through retrofit with coroutines 

Used Api - https://api.quotable.io/   
end-point : quotes

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
