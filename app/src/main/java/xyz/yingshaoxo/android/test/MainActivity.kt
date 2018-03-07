package xyz.yingshaoxo.android.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler



class MainActivity : AppCompatActivity() {
    lateinit var ffmpeg: FFmpeg

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ffmpeg = FFmpeg.getInstance(this)
        ffmpeg.loadBinary(object : LoadBinaryResponseHandler() {})

        button.setOnClickListener {
            do_things()
        }
    }

    fun do_things() {
        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"
            ffmpeg.execute(arrayOf(editText.text.toString()), object : ExecuteBinaryResponseHandler() {

                override fun onStart() {
                    showBox.text.append("already start\n")
                }

                override fun onProgress(message: String?) {
                    showBox.text.append(showBox.text.toString() + message.toString() + "\n")
                }

                override fun onFailure(message: String?) {
                    showBox.text.append(showBox.text.toString() + message.toString() + "\n")
                }

                override fun onSuccess(message: String?) {
                    showBox.text.append(showBox.text.toString() + message.toString() + "\n")
                }

                override fun onFinish() {
                    showBox.text.append("over\n")
                }
            })
        } catch (e: FFmpegCommandAlreadyRunningException) {
            // Handle if FFmpeg is already running
            showBox.text.append(e.toString())
        }

    }
}
