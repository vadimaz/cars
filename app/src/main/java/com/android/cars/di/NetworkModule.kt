package com.android.cars.di

import android.content.Context
import com.android.cars.BuildConfig
import com.android.cars.data.models.topic.TopicsOrderByEnum
import com.android.cars.data.remote.adapters.LocalDateTimeTypeAdapter
import com.android.cars.data.remote.adapters.OauthGrantTypeAdapter
import com.android.cars.data.remote.api.AuthApi
import com.android.cars.data.remote.api.PhotoApi
import com.android.cars.data.remote.api.TopicApi
import com.android.cars.data.remote.converters.TopicOrderByConverter
import com.android.cars.data.remote.interceptors.AddTokenInterceptor
import com.android.cars.data.remote.interceptors.HeaderInterceptor
import com.android.cars.data.remote.manager.AuthDataSource
import com.android.cars.data.remote.manager.PhotoDataSource
import com.android.cars.data.remote.manager.TopicDataSource
import com.android.cars.data.remote.manager.implementation.AuthDataSourceImpl
import com.android.cars.data.remote.manager.implementation.PhotoDataSourceImpl
import com.android.cars.data.remote.manager.implementation.TopicDataSourceImpl
import com.android.cars.data.remote.utils.NetworkErrorConverterHelper
import com.android.cars.data.remote.utils.OauthGrantType
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private const val TYPE_ADAPTERS = "type_adapters_gson"

    private const val HTTP_CLIENT_READ_TIMEOUT = 15L
    private const val HTTP_CLIENT_WRITE_TIMEOUT = 10L
    private const val HTTP_CLIENT_CONNECT_TIMEOUT = 10L

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        addTokenInterceptor: AddTokenInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(HTTP_CLIENT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(HTTP_CLIENT_WRITE_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(HTTP_CLIENT_READ_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(headerInterceptor)
        .addInterceptor(addTokenInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    fun provideRetrofit(
        gson: Gson,
        client: OkHttpClient,
        queryConverterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(queryConverterFactory)
        .client(client)
        .build()

    @Provides
    fun provideQueryConverterFactory(
        topicOrderByConverter: TopicOrderByConverter
    ): Converter.Factory =
        object : Converter.Factory() {
            override fun stringConverter(
                type: Type,
                annotations: Array<out Annotation>,
                retrofit: Retrofit
            ): Converter<*, String>? {
                return when (type) {
                    TopicsOrderByEnum::class.java -> topicOrderByConverter
                    else -> super.stringConverter(type, annotations, retrofit)
                }
            }
        }

    @Named(TYPE_ADAPTERS)
    @Provides
    fun provideTypeAdapters(
        localDateTimeTypeAdapter: LocalDateTimeTypeAdapter,
        oauthGrantTypeAdapter: OauthGrantTypeAdapter
    ): HashMap<Type, Any> = HashMap<Type, Any>().apply {
        this[LocalDateTime::class.java] = localDateTimeTypeAdapter
        this[OauthGrantType::class.java] = oauthGrantTypeAdapter
    }

    @Provides
    fun provideGson(
        @Named(TYPE_ADAPTERS) typeAdapters: HashMap<Type, Any>,
    ): Gson = GsonBuilder().apply {
        for ((key, value) in typeAdapters) {
            registerTypeAdapter(key, value)
        }
    }.create()

    @Provides
    fun provideNetworkErrorConverterHelper(
        @ApplicationContext context: Context,
        gson: Gson
    ): NetworkErrorConverterHelper {
        return NetworkErrorConverterHelper(context, gson)
    }

    @Provides
    fun provideTopicApi(retrofit: Retrofit): TopicApi = retrofit.create(TopicApi::class.java)

    @Provides
    fun providePhotoApi(retrofit: Retrofit): PhotoApi = retrofit.create(PhotoApi::class.java)

    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    fun provideTopicDataSource(
        topicApi: TopicApi,
        networkErrorConverterHelper: NetworkErrorConverterHelper
    ): TopicDataSource = TopicDataSourceImpl(topicApi, networkErrorConverterHelper)

    @Provides
    fun providePhotoDataSource(
        photoApi: PhotoApi,
        networkErrorConverterHelper: NetworkErrorConverterHelper
    ): PhotoDataSource = PhotoDataSourceImpl(photoApi, networkErrorConverterHelper)

    @Provides
    fun provideAuthDataSource(
        authApi: AuthApi,
        networkErrorConverterHelper: NetworkErrorConverterHelper
    ): AuthDataSource = AuthDataSourceImpl(authApi, networkErrorConverterHelper)
}