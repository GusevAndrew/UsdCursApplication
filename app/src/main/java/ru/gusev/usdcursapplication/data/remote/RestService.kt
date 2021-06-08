package ru.gusev.usdcursapplication.data.remote

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.gusev.usdcursapplication.data.remote.dto.DynamicCursDto
import ru.gusev.usdcursapplication.data.remote.dto.ValCursDto

interface RestService {

    @GET("XML_daily.asp")
    fun getCurrentCurs(): Single<Response<ValCursDto>>

    @GET("XML_dynamic.asp")
    fun getCursUsdByRange(@Query("date_req1") dateFrom: String, @Query("date_req2") dateTo: String, @Query("VAL_NM_RQ") currency: String = "R01235"): Single<Response<DynamicCursDto>>

    companion object {
        fun createRestService(): RestService {
            val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(logging)

            val tickXml = TikXml.Builder().build()

            return Retrofit.Builder()
                .baseUrl("http://www.cbr.ru/scripts/")
                .client(okHttpBuilder.build())
                .addConverterFactory(TikXmlConverterFactory.create(tickXml))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(RestService::class.java)
        }
    }
}