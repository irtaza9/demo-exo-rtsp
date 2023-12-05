package com.creatives.exoplayerdemo

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.TracksInfo
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionOverrides
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), Player.Listener {
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun initPlayer() {
        val rtspUri = "rtsp://username:password@ip"
        val mediaSource: MediaSource = RtspMediaSource.Factory().setForceUseRtpTcp(true)
            .createMediaSource(MediaItem.fromUri(rtspUri))
        val player = ExoPlayer.Builder(this).build()
        player_exo.player = player
        player.setMediaSource(mediaSource)
        player.prepare()
        player.play()
    }

    private fun releasePlayer() {
        player?.let {
            it.release()
            player = null
        }
    }


    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initPlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }
}