package com.yangbong.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yangbong.core_ui.util.Event
import com.yangbong.domain.entity.request.DomainLoginRequest
import com.yangbong.domain.use_case.login.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases
) : ViewModel() {

    private val _socialToken = MutableLiveData<String>()
    val socialToken: LiveData<String> = _socialToken

    private val _fcmToken = MutableLiveData<String>()
    val fcmToken: LiveData<String> = _fcmToken

    private lateinit var platform: String

    private val _navigateToSetProfile = MutableLiveData<Event<String>>()
    val navigateToSetProfile: LiveData<Event<String>> = _navigateToSetProfile

    private val _navigateToHome = MutableLiveData<Event<Boolean>>()
    val navigateToHome: LiveData<Event<Boolean>> = _navigateToHome

    private val _loginFailureMessage = MutableLiveData<String>()
    val loginFailureMessage: LiveData<String> = _loginFailureMessage

    fun postLogin() {
        viewModelScope.launch {
            loginUseCases.postLogin(
                DomainLoginRequest(
                    platform = platform,
                    socialToken = socialToken.value ?: "",
                    fcmToken = fcmToken.value ?: ""
                )
            ).onSuccess {
                if (it.isNewUser) {
                    _navigateToSetProfile.postValue(Event(platform))
                } else {
                    loginUseCases.saveAccessToken(it.accessToken ?: "")
                    _navigateToHome.postValue(Event(true))
                }
            }.onFailure {
                _loginFailureMessage.postValue(it.message)
            }
        }
    }


    fun getAccessToken(): String =
        loginUseCases.getAccessToken()

    fun updateSocialToken(socialToken: String) {
        _socialToken.value = socialToken
    }

    fun updatePlatform(platform: String) {
        this.platform = platform
    }

    fun getFcmToken() {
        loginUseCases.getFcmToken {
            updateFcmToken(it)
        }
    }

    private fun updateFcmToken(fcmToken: String) {
        _fcmToken.value = fcmToken
    }
}