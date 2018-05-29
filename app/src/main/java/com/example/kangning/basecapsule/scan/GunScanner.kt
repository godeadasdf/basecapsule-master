package com.example.kangning.basecapsule.scan

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import android.device.ScanManager
import android.device.scanner.configuration.PropertyID
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log

/**
 * Created by kangning on 2018/5/28.
 */
object GunScanner : BaseScanner {

    var context: Context? = null
    private lateinit var scanManager: ScanManager
    var isScanning = false
    var isFailed = false

    private val contentPublisher: PublishSubject<String>by lazy {
        PublishSubject.create<String>()
    }


    private fun initScan() {
        try {
            scanManager = ScanManager()
            scanManager.openScanner()

        } catch (e: Exception) {
            Log.d(GunScanner::class.java.simpleName, "open scanner failed")
            isFailed = true
        }

    }

    fun fetchScanResult(context: Context): Observable<String> {
        isFailed = false
        initScan()
        if (!isFailed) {
            this.context = context
            isScanning = true
            context?.registerReceiver(receiver, intentFilter)
        }
        return contentPublisher

    }

    private val intentFilter: IntentFilter by lazy {
        val filter = IntentFilter()
        val idbuf = intArrayOf(PropertyID.WEDGE_INTENT_ACTION_NAME, PropertyID.WEDGE_INTENT_DATA_STRING_TAG)
        val value_buf = scanManager.getParameterString(idbuf)
        if (value_buf != null && value_buf!![0] != null && value_buf!![0] != "") {
            filter.addAction(value_buf!![0])
        } else {
            filter.addAction(ScanManager.ACTION_DECODE)
        }
        filter
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        context = null
        context?.unregisterReceiver(receiver)
        isScanning = false
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val barcode = intent.getByteArrayExtra(ScanManager.DECODE_DATA_TAG)
            val barcodelen = intent.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0)
            val s = String(barcode, 0, barcodelen)
            if (isScanning) {
                contentPublisher.onNext(s)
            }
        }
    }


}