# An example of MVVM architecture for Android app.

[MVVM diagram](images/mvvm_diagram.png)

Language: **Kotlin**

Technology stack:
* Hilt
* DataBinding + LiveData
* Kotlin Coroutines + Flow
* Room Database
* OkHttp + Retrofit 2
* XML (ConstraintLayout)

**Example of data fetching from Network using Kotlin Coroutines + Flow and transferring it between UI and ViewModel using MutableLiveData**

Here we can see how the ProgressBar change visibility depends on MutableLiveData in the ViewModel and observing result at Fragment to show Toast in case of error.
Also it's an example of user actions to fetch data / make request by using ViewModel and DataBinding.

>ViewModel:
```kotlin
@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private var jobLogin: Job? = null
    private val _login = MutableLiveData<Result<LoginResponse>>(Result.Initial)
    val login: LiveData<Result<LoginResponse>> = _login

    fun login() {
        jobLogin?.cancel()
        jobLogin = viewModelScope.launch {
            repository.login().collect {
                _login.value = it
            }
        }
    }

    fun resetLoginResponse() {
        _login.value = Result.Initial
    }
    
    ...
}
```

>Fragment:
```kotlin
@AndroidEntryPoint
class AppFragment: Fragment() { // in real project we will use BaseFragmentViewModel

    private val viewModel: AppViewModel by viewModels()
    private lateinit var binding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.id.fragment_app, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Providing lifecycleOwner and ViewModel objects into DataBinding
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.login.observe(viewLifecycleOwner) { result ->
            if (result is Result.Success) {
                // do something on success login response, e.g.: open home screen
                    
                viewModel.resetLoginResponse()
            } else if (result is Result.Error) {
                showToast(result.message)
                viewModel.resetLoginResponse()
            }
        }
    }   
   
    ...
}    
```

>fragment_app.xml:
```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <import type="android.view.View" />

        <variable
            name="viewModel"
            type="AppViewModel" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
      
        <ProgressBar
            android:id="@+id/progress_bar"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:visibility="@{viewModel.login.loading ? View.VISIBLE : View.GONE}"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            tools:visibility="gone" />
      
        <Button
            android:id="@+id/login_btn"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:enabled="@{!viewModel.login.loading}"
            android:text="Login"
            android:onClick="@{(v) -> viewModel.login()}"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent" />
      
    </androidx.constraintlayout.widget.ConstraintLayout>

</layout>
```

[LiveData diagram](images/live_data.png)

