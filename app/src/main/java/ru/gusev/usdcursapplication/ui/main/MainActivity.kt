package ru.gusev.usdcursapplication.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.gusev.usdcursapplication.R
import ru.gusev.usdcursapplication.UsdCursApplication
import ru.gusev.usdcursapplication.databinding.ActivityMainBinding
import ru.gusev.usdcursapplication.ui.curs_list.CursListFragment
import ru.gusev.usdcursapplication.ui.settings.SettingsFragment
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>

    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        UsdCursApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomBar.setOnNavigationItemSelectedListener {
            presenter.onClickByBottomBarItem(it.itemId)
            return@setOnNavigationItemSelectedListener true
        }

        binding.bottomBar.selectedItemId = R.id.item_curs
    }

    override fun showCursFragment() {
        showFragment(CursListFragment.newInstance())
    }

    override fun showSettingsFragment() {
        showFragment(SettingsFragment.newInstance())
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commitAllowingStateLoss()
    }

}