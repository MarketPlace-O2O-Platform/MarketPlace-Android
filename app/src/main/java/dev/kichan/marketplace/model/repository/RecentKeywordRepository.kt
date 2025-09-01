package dev.kichan.marketplace.model.repository

import android.content.Context
import android.content.SharedPreferences

class RecentKeywordRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("recent_keywords", Context.MODE_PRIVATE)

    fun getRecentKeywords(): List<String> {
        return prefs.getStringSet("keywords", emptySet())?.toList() ?: emptyList()
    }

    fun addRecentKeyword(keyword: String) {
        val keywords = getRecentKeywords().toMutableList()
        if (keywords.contains(keyword)) {
            keywords.remove(keyword)
        }
        keywords.add(0, keyword)
        if (keywords.size > 10) {
            keywords.removeLast()
        }
        prefs.edit().putStringSet("keywords", keywords.toSet()).apply()
    }
}