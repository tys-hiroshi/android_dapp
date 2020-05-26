package com.hblockth.dapp

import android.app.Application

//https://qiita.com/sudachi808/items/d629e44fe8c1b9a26bb8
class DApplication : android.app.Application() {
    companion object {
        lateinit var instance: Application private set  // <- これ
    }

    override fun onCreate() {
        super.onCreate()

        instance = this  // <- これ
    }
}