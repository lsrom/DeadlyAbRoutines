package cz.lsrom.deadlyabroutine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View

/**
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
open class BaseActivity : AppCompatActivity() {
    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }

    lateinit var spManager: SpManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        spManager = SpManager.getInstance(this)

        showTheme(spManager.isDarkTheme())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.action_bar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionBarMenu) {
            showPopupMenu()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showPopupMenu() {
        val menuItemView: View = findViewById(R.id.actionBarMenu)
        val popupMenu = PopupMenu(this, menuItemView)
        popupMenu.inflate(R.menu.popup_menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuSwitchTheme -> {
                    val dark: Boolean = spManager.isDarkTheme()
                    showTheme(!dark)
                    spManager.setDarkTheme(!dark)
                    recreate()
                }

                R.id.menuAbout -> {
                    startActivity(AboutActivity.startIntent(this))
                }
            }
            false
        }
        popupMenu.show()
    }

    fun showTheme(dark: Boolean) {
        if (dark) {
            Log.d(TAG, "Starting activity with dark theme.")
            setTheme(R.style.DarkTheme)
        } else {
            Log.d(TAG, "Starting activity with light theme.")
            setTheme(R.style.LightTheme)
        }
    }
}