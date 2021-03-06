package com.example.kakao_fatal_accident

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kakao_fatal_accident.databinding.ActivityMainBinding
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

        var keyHash = Utility.getKeyHash(this)
        println("keyHash = " + keyHash)

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null)
                Toast.makeText(this, "아이디 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
            else if (token != null)
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
            updateUi()
        }


        binding.btnLogin.setOnClickListener {
            UserApiClient.instance.run {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@MainActivity))
                    UserApiClient.instance.loginWithKakaoTalk(this@MainActivity, callback = callback)
                else UserApiClient.instance.loginWithKakaoAccount(this@MainActivity, callback = callback)
            }
            updateUi()
        }

        binding.btnLogout.setOnClickListener {
            log_out()
            updateUi()
        }

        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, testActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        updateUi()
    }


    private fun updateUi() {
        UserApiClient.instance.me() { user: User?, throwable: Throwable? ->
            if (user != null) {

                Log.i("user_info", "user id = " + user.id)
                Log.i("user_info", "user email = " + user.kakaoAccount!!.email)
                Log.i("user_info", "user gender = " + user.kakaoAccount!!.gender)
                Log.i("user_info", "user age = " + user.kakaoAccount!!.ageRange)

                binding.btnLogin.visibility = View.GONE
                binding.btnLogout.visibility = View.VISIBLE
                binding.btnStart.visibility = View.VISIBLE
                binding.txtNickname.text = (user.kakaoAccount!!.profile!!.nickname) + "님 환영합니다"
                //Glide.with(binding.imgProfile).load(user.kakaoAccount!!.profile!!.thumbnailImageUrl)
                  //  .circleCrop().into(binding.imgProfile)

                /*if (user.kakaoAccount!!.profile!!.thumbnailImageUrl == null)
                    Glide.with(binding.imgProfile).load(R.drawable.null_profile_img).circleCrop()
                        .into(binding.imgProfile)*/
            } else {
                binding.btnLogin.visibility = View.VISIBLE
                binding.btnLogout.visibility = View.GONE
                binding.btnStart.visibility = View.GONE
                binding.txtNickname.text = "로그인이 필요합니다"
                binding.imgProfile.setImageResource(R.drawable.basic_profile_img)

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
                            updateUi()
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