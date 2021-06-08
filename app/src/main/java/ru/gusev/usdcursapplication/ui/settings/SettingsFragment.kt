package ru.gusev.usdcursapplication.ui.settings

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.gusev.usdcursapplication.R
import ru.gusev.usdcursapplication.UsdCursApplication
import ru.gusev.usdcursapplication.databinding.FragmentSettingsBinding
import ru.gusev.usdcursapplication.utils.getTimeFormat
import ru.gusev.usdcursapplication.utils.hide
import ru.gusev.usdcursapplication.utils.hideKeyboard
import ru.gusev.usdcursapplication.utils.show
import java.util.*
import javax.inject.Inject
import javax.inject.Provider


class SettingsFragment : MvpAppCompatFragment(), SettingsView {

    @Inject
    lateinit var presenterProvider: Provider<SettingsPresenter>

    private val presenter by moxyPresenter {
        presenterProvider.get()
    }


    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        UsdCursApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchWrapper.setOnClickListener {
            closeKeyBoard()
            presenter.onClickByNotificationEnabled()
        }

        binding.retryButton.setOnClickListener {
            presenter.getSettings()
        }

        binding.cursValue.doAfterTextChanged {
            presenter.saveCursValue(it?.toString().orEmpty())
        }

        binding.cursValue.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closeKeyBoard()
                true
            } else false
        }

        binding.timeNotification.setOnClickListener {
            closeKeyBoard()
            presenter.onClickByTimeNotification()
        }
    }

    override fun onPause() {
        super.onPause()
        closeKeyBoard()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun contentLoading(isLoading: Boolean) {
        binding.contentProgressBar.show(isLoading)
        if (isLoading) {
            binding.contentScreen.hide()
            binding.errorBlock.hide()
        }
    }

    override fun setData(time: Calendar?, cursValue: Float?, isNotificationEnabled: Boolean) {
        binding.errorBlock.hide()
        binding.timeNotification.setText(time?.getTimeFormat())
        binding.cursValue.setText(cursValue?.toString().orEmpty())
        binding.notificationEnabled.isChecked = isNotificationEnabled
        binding.contentScreen.show()
    }

    override fun showErrorLoadData() {
        binding.contentScreen.hide()
        binding.errorBlock.show()
    }

    override fun enabledNotification(isEnabled: Boolean) {
        binding.notificationEnabled.isChecked = isEnabled
    }

    override fun showErrorNotificationEnabled() {
        Snackbar.make(binding.root, getString(R.string.settings_error_enable_notification), Snackbar.LENGTH_LONG).show()
    }

    override fun showErrorNotificationEnabledTimeNotSet() {
        setErrorTimeNotification(getString(R.string.settings_error_enable_notification_time_not_set))
    }

    override fun showTimePickerDialog(calendar: Calendar) {
        context?.let {
            TimePickerDialog(it,
                { _, hourOfDay, minute ->
                    setErrorTimeNotification(null)
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    binding.timeNotification.setText(calendar.getTimeFormat())
                    presenter.saveTimeNotification(calendar)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true).show()
        }
    }

    private fun closeKeyBoard() {
        binding.cursValue.hideKeyboard()
        binding.root.clearFocus()
    }

    private fun setErrorTimeNotification(errorText: String?) {
        with(binding.timeNotificationWrapper) {
            isErrorEnabled = !errorText.isNullOrEmpty()
            error = errorText
        }
    }
}