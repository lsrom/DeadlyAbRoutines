package cz.lsrom.deadlyabroutine

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_about.*


/**
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
class AboutActivity : BaseActivity() {
    companion object {
        private val TAG = AboutActivity::class.java.simpleName

        fun startIntent(context: Context): Intent {
            return Intent(context, AboutActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "About activity started.")

        setContentView(R.layout.activity_about)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        appVersion.text = BuildConfig.VERSION_NAME

        txtEmailDeveloper.setOnClickListener(View.OnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.about_developer_email))
            emailIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                getString(R.string.about_developer_email_subject)
            )
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")

            startActivity(
                Intent.createChooser(
                    emailIntent,
                    getString(R.string.mail_pick_provider)
                )
            )
        })

        imgVideoThumbnail.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:ekowbKbBNSM"))
            startActivity(intent)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}