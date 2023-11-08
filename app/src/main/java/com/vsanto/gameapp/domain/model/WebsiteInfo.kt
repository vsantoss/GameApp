package com.vsanto.gameapp.domain.model

import com.vsanto.gameapp.R
import java.io.Serializable

enum class WebsiteInfo(val id: Int, val key: Int, val img: Int) : Serializable {
    OFFICIAL(1, R.string.official, R.drawable.ic_official_site),
    WIKIA(2, R.string.wikia, R.drawable.ic_wikia),
    WIKIPEDIA(3, R.string.wikipedia, R.drawable.ic_wikipedia),
    FACEBOOK(4, R.string.facebook, R.drawable.ic_facebook),
    TWITTER(5, R.string.twitter, R.drawable.ic_twitter),
    TWITCH(6, R.string.twitch, R.drawable.ic_twitch),
    INSTAGRAM(8, R.string.instagram, R.drawable.ic_instagram),
    YOUTUBE(9, R.string.youtube, R.drawable.ic_youtube),
    IPHONE(10, R.string.iphone, R.drawable.ic_iphone),
    IPAD(11, R.string.ipad, R.drawable.ic_ipad),
    ANDROID(12, R.string.android, R.drawable.ic_android),
    STEAM(13, R.string.steam, R.drawable.ic_steam),
    REDDIT(14, R.string.reddit, R.drawable.ic_reddit),
    ITCH(15, R.string.itch, R.drawable.ic_itch),
    EPIC_GAMES(16, R.string.epic, R.drawable.ic_epic),
    GOG(17, R.string.gog, R.drawable.ic_gog),
    DISCORD(18, R.string.discord, R.drawable.ic_discord);

    companion object {
        fun find(id: Int): WebsiteInfo? = WebsiteInfo.values().find { it.id == id }
    }

}