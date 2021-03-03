package com.example.api_pjt

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.api_pjt.databinding.ActivityMainBinding
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User

var is_login = false

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //KakaoSdk.init(this, "348fa810493bebc28c9acbd1483cbe83")
        var keyHash = Utility.getKeyHash(this)
        println("keyHash = " + keyHash)

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null)
                Toast.makeText(this, "아이디 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
             else if (token != null) {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
                is_login = true
            }
            update_ui()
        }

        binding.btnKakaoLogin.setOnClickListener {
            LoginClient.instance.run {
                if (isKakaoTalkLoginAvailable(this@MainActivity)) {
                    loginWithKakaoAccount(this@MainActivity, callback = callback)
                } else loginWithKakaoAccount(this@MainActivity, callback = callback)
            }
            update_ui()
        }

        binding.btnKakaoLogout.setOnClickListener {
            log_out()
            is_login = false
            update_ui()
        }


    }

    override fun onResume() {
        super.onResume()
        update_ui()
    }

    fun update_ui() {
        UserApiClient.instance.me() { user: User?, throwable: Throwable? ->
            if (user != null) {
                Log.i("user_info", "user id = " + user.id)
                Log.i("user_info", "user email = " + user.kakaoAccount!!.email)
                Log.i("user_info", "user gender = " + user.kakaoAccount!!.gender)
                Log.i("user_info", "user age = " + user.kakaoAccount!!.ageRange)

                binding.txtNickname.text = (user.kakaoAccount!!.profile!!.nickname)
                binding.btnKakaoLogin.visibility = View.GONE
                binding.btnKakaoLogout.visibility = View.VISIBLE
                binding.imgLoading.visibility = View.VISIBLE
                //Glide.with(binding.imgProfile).load(user.kakaoAccount!!.profile!!.thumbnailImageUrl).circleCrop().into(binding.imgProfile)
                println("이미지 = " + user.kakaoAccount!!.profile!!.thumbnailImageUrl)

               /*if (user.kakaoAccount!!.profile!!.thumbnailImageUrl == null)
                    Glide.with(binding.imgProfile).load(R.drawable.null_profile_img).circleCrop()
                        .into(binding.imgProfile)*/

                val handler = Handler()
                handler.postDelayed({
                    val start_intent = Intent(this, SecondActivity::class.java)
                    startActivity(start_intent)
                }, 2000)
            } else {
                binding.txtNickname.text = ("로그인이 필요합니다.")
                binding.imgProfile.setImageResource(R.drawable.basic_profile_img)
                binding.btnKakaoLogin.visibility = View.VISIBLE
                binding.btnKakaoLogout.visibility = View.GONE
                //Glide.with(this).load(R.drawable.loading_gjf).into(binding.imgLoading)
                binding.imgLoading.visibility = View.GONE
            }
        }
    }

    private fun log_out() {
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle("로그아웃")
        dialog.setMessage("정말 로그아웃 하시겠습니까?")
        fun toast_p() {
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
        }
        var dialog_listener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        UserApiClient.instance.logout {
                            update_ui()
                        }
                        toast_p()
                    }
                }
            }
        }
        dialog.setPositiveButton("YES", dialog_listener)
        dialog.setNegativeButton("NO", dialog_listener)
        dialog.show()
    }
}