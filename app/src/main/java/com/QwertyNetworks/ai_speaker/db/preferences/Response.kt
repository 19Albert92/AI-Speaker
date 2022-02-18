package com.QwertyNetworks.ai_speaker.db.preferences

import android.text.Html
import android.webkit.WebMessage
import androidx.annotation.Keep
import org.json.JSONObject
import retrofit2.http.Url
import java.net.URL

@Keep
data class Response(
    val message: Any?,

)
//protocol=http/1.1, code=200, message=OK, url=https://test.mybusines.app/api/test_post
