package com.example.api_pjt

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KakaoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "348fa810493bebc28c9acbd1483cbe83")
    }
}