package com.overdrive.foodintake.server

import android.app.Application
import com.overdrive.foodintake.server.request.SignIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Created by Overdrive on 14.04.2018.
 */
class ServerApp : Application() {

    private var retrofit: Retrofit? = null

    companion object {
        const val SERVER_URL = "https://foodintake1234.com"

        var signInApi: SignIn? = null
            private set

    }

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
                .baseUrl(ServerApp.SERVER_URL) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build()
        signInApi = retrofit!!.create<SignIn>(SignIn::class.java) //Создаем объект, при помощи которого будем выполнять запросы
    }


}