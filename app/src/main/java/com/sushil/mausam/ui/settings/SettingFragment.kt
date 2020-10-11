package com.sushil.mausam.ui.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sushil.mausam.MausamApplication
import com.sushil.mausam.R
import com.sushil.mausam.preference.PreferenceProviderMausam
import com.sushil.mausam.utils.*
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var settingRepository: SettingRepository
    private lateinit var preference: PreferenceProviderMausam
    var count = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preference = PreferenceProviderMausam(this.requireContext())

        val cityDao = MausamApplication.instance?.getRoomDAO()?.cityDao()
        settingRepository = SettingRepository(cityDao)
        settingViewModel = SettingViewModel(settingRepository)
        Coroutines.io {
            settingViewModel.getBookMarkCountFromDataBase()?.let { count = it }
        }
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUnitType()
        textViewUnitType.text =
            if (preference.getUnitType().isEmpty()) UNIT_STANDARD else preference.getUnitType()
        if (count == 0) {
            activity?.toast("Nothing to delete")
        } else {
            relativeLayoutClearBookmarkedList.setOnClickListener {

                val dialogBuilder = AlertDialog.Builder(context)

                dialogBuilder.setMessage("Are you sure you want to delete all bookmarked cities ?")
                    .setCancelable(true)
                    .setPositiveButton("Yes") { _, _ ->
                        Coroutines.io {
                            settingViewModel.deleteAllCitiesFromDataBase()
                        }
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.cancel()
                    }

                val alert = dialogBuilder.create()
                alert.setTitle("Delete Saved Search")

                alert.show()

            }
        }

        relativeLayoutUnit.setOnClickListener {
            showBottomSheetDialogSelectUnitType()
        }

        relativeLayoutContactSupport.setOnClickListener {
            activity?.toast("Contact Support")
        }
    }

    private fun setUnitType() {
        textViewUnitType.text =
            if (preference.getUnitType().isEmpty()) UNIT_STANDARD else preference.getUnitType()
    }

    private fun showBottomSheetDialogSelectUnitType() {
        lateinit var selectedRadioButton: RadioButton
        val view = layoutInflater.inflate(R.layout.layout_unit_bottom_sheet, null)
        val dialog = activity?.let { BottomSheetDialog(it) }
        dialog?.setContentView(view)
        dialog?.setCancelable(false)
        val buttonSubmit = view.findViewById<Button>(R.id.btnSubmit)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        when {
            preference.getUnitType()
                .equals(UNIT_IMPERIAL, true) -> radioGroup.check(R.id.rbImperial)
            preference.getUnitType().equals(UNIT_METRIC, true) -> radioGroup.check(R.id.rbMetric)
            else -> radioGroup.check(R.id.rbStandard)
        }

        buttonSubmit.setOnClickListener {
            selectedRadioButton = view.findViewById(radioGroup.checkedRadioButtonId) as RadioButton
            val unit = selectedRadioButton.text.toString()
            if (unit == UNIT_STANDARD)
                preference.updateUnitType("")
            else preference.updateUnitType(unit)
            setUnitType()
            dialog?.dismiss()
        }

        dialog?.show()
    }
}