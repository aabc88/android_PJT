package com.example.api_pjt

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.api_pjt.databinding.ActivityMainBinding
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //KakaoSdk.init(this, "348fa810493bebc28c9acbd1483cbe83")
        update_ui()

        var keyHash = Utility.getKeyHash(this)
        println("keyHash = " + keyHash)

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                println("login fail")
            } else if (token != null) {
                println("login success")
            }
            update_ui()
        }

        binding.btnKakaoLogin.setOnClickListener {
            LoginClient.instance.run {
                if (isKakaoTalkLoginAvailable(this@MainActivity)) {
                    loginWithKakaoAccount(this@MainActivity, callback = callback)
                    update_ui()
                } else loginWithKakaoAccount(this@MainActivity, callback = callback)
            }
            update_ui()
        }

        binding.btnKakaoLogout.setOnClickListener {
            UserApiClient.instance.logout {
                update_ui()
            }
            update_ui()
        }

    }

    fun update_ui() {
        UserApiClient.instance.me() { user: User?, throwable: Throwable? ->
            if (user != null) {
                Log.i("user_info", "user id = "+user.id)
                Log.i("user_info", "user email = "+ user.kakaoAccount!!.email)
                Log.i("user_info", "user gender = "+user.kakaoAccount!!.gender)
                Log.i("user_info", "user age = "+user.kakaoAccount!!.ageRange)

                binding.txtNickname.text = (user.kakaoAccount!!.email).toString()
                binding.btnKakaoLogin.visibility = View.GONE
                binding.btnKakaoLogout.visibility = View.VISIBLE
                //binding.imgProfile.setImageURI(user.kakaoAccount?.profile?.thumbnailImageUrl)
            } else {
                binding.txtNickname.text = ("로그인이 필요합니다.")
                binding.btnKakaoLogout.visibility = View.GONE
                binding.btnKakaoLogin.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        update_ui()
    }

    override fun onRestart() {
        super.onRestart()

    }
}