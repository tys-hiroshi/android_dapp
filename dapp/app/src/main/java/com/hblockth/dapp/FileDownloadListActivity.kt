package com.hblockth.dapp

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.hblockth.dapp.viewmodels.DownloadListViewModel
import com.hblockth.dapp.viewmodels.DownloadListViewModelFactory

class FileDownloadListActivity {

    private lateinit var mDownloadListViewModel: DownloadListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        mDownloadListViewModel =  ViewModelProviders.of(this, DownloadListViewModelFactory(this.application, ""))
            .get<DownloadListViewModel>(
                DownloadListViewModel::class.java
            )
        super.onCreate(savedInstanceState)
    }
}