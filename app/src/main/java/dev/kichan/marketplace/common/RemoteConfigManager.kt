package dev.kichan.marketplace.common

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.gson.Gson
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.R
import kotlinx.coroutines.tasks.await

/**
 * Firebase Remote Config 관리
 *
 * 공식 문서: https://firebase.google.com/docs/remote-config/android/get-started
 */
object RemoteConfigManager {
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private val gson = Gson()

    private const val KEY_APP_ANNOUNCEMENT = "app_announcement"

    fun init(context: Context) {
        remoteConfig = FirebaseRemoteConfig.getInstance()

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(
                if (BuildConfig.DEBUG) 60 else 3600
            )
            .build()

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    /**
     * Remote Config 값을 서버에서 가져와 활성화
     * @return fetch 및 activate 성공 여부
     */
    suspend fun fetchAndActivate(): Boolean {
        return try {
            remoteConfig.fetchAndActivate().await()
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 공지 데이터 조회
     * @return AnnouncementData (파싱 실패 시 기본값 반환)
     */
    fun getAnnouncement(): AnnouncementData {
        val json = remoteConfig.getString(KEY_APP_ANNOUNCEMENT)
        return if (json.isNotBlank()) {
            try {
                gson.fromJson(json, AnnouncementData::class.java)
            } catch (e: Exception) {
                AnnouncementData()
            }
        } else {
            AnnouncementData()
        }
    }
}
