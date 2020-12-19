package com.mizukikk.mltd.main.idol.model

import android.app.Application
import com.mizukikk.mltd.data.Field
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.main.model.BaseMainViewModel

class IdolViewModel(application: Application) : BaseMainViewModel(application) {
    val langJp get() = PreferencesHelper.apiLanguage == Field.API.LANG_JP
}