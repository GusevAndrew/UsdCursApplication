package ru.gusev.usdcursapplication.ui.curs_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.gusev.usdcursapplication.R
import ru.gusev.usdcursapplication.UsdCursApplication
import ru.gusev.usdcursapplication.databinding.FragmentCursListBinding
import ru.gusev.usdcursapplication.utils.getDateInServerFormat
import ru.gusev.usdcursapplication.utils.hide
import ru.gusev.usdcursapplication.utils.show
import javax.inject.Inject
import javax.inject.Provider

class CursListFragment : MvpAppCompatFragment(), CursListView {

    @Inject
    lateinit var presenterProvider: Provider<CursListPresenter>

    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    private lateinit var binding: FragmentCursListBinding

    private val cursAdapter: CursAdapter by lazy {
        CursAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        UsdCursApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCursListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cursList.adapter = cursAdapter

        binding.retryButton.setOnClickListener {
            presenter.getCursInfo(false)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            presenter.getCursInfo(true)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CursListFragment()
    }

    override fun showLoadingContent(isLoading: Boolean, isFullScreen: Boolean) {
        with(binding) {
            if (isLoading) {
                if (isFullScreen) {
                    swipeRefreshLayout.hide()
                    errorBlock.hide()
                    contentProgressBar.show()
                } else {
                    swipeRefreshLayout.isRefreshing = true
                }
            } else {
                contentProgressBar.hide()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun setData(dataForScreen: DataForCursListFragment) {
        with(binding) {
            errorBlock.hide()
            cursAdapter.setNewData(dataForScreen.dynamicCurs)
            if (dataForScreen.currentCurs.value > 0) {
                errorGetCurrentCurs.hide()
                currentTime.text = getString(
                    R.string.curs_list_for_current_day,
                    dataForScreen.currentCurs.date.getDateInServerFormat()
                )
                currentUsd.text = getString(R.string.curs_list_format_usd_with_name, dataForScreen.currentCurs.value)
                cursBlock.show()
            } else {
                cursBlock.hide()
                errorGetCurrentCurs.show()
            }
            swipeRefreshLayout.show()
        }
    }

    override fun showErrorGetCursList(isFullScreen: Boolean) {
        with(binding) {
             if (isFullScreen) {
                 swipeRefreshLayout.hide()
                 errorBlock.show()
             } else {
                 Snackbar.make(root, getString(R.string.curs_list_error_update_curs), Snackbar.LENGTH_LONG).show()
             }
        }
    }
}