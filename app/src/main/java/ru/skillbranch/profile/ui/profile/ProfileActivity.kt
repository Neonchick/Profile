package ru.skillbranch.profile.ui.profile

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.profile.R
import ru.skillbranch.profile.models.Profile
import ru.skillbranch.profile.viewmodels.ProfileViewModel

class ProfileActivity : AppCompatActivity()
{
    companion object
    {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    private lateinit var viewModel: ProfileViewModel
    var isEditMode = false
    lateinit var viewFields: Map<String, TextView>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews(savedInstanceState)
        initViewModel()
        Log.d("M_ProfileActivity","onCreate")
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_EDIT_MODE, isEditMode)
    }

    private fun initViewModel()
    {
        viewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it) })
    }

    private fun updateTheme(mode: Int)
    {
        Log.d("M_ProfileActivity","updateTheme")
        delegate.localNightMode = mode
    }

    private fun updateUI(profile: Profile)
    {
        profile.toMap().also {
            for ((k, v) in viewFields)
                v.text = it[k].toString()
        }
    }

    private fun initViews(savedInstanceState: Bundle?)
    {
        viewFields = mapOf(
                "nickname" to tv_nick_name,
                "rank" to tv_rank,
                "firstName" to et_first_name,
                "lastName" to et_last_name,
                "about" to et_about,
                "repository" to et_repository,
                "rating" to tv_rating,
                "respect" to tv_respect)

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        showCurrentMode(isEditMode)

        btn_edit.setOnClickListener {
            if (isEditMode) saveProfileInfo()
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }

        btn_switch_theme.setOnClickListener(View.OnClickListener {
            viewModel.switchTheme()
        })
    }

    private fun showCurrentMode(isEdit: Boolean)
    {
        val info = viewFields.filter {
            it.key in setOf("firstName", "lastName", "about", "repository")
        }
        for ((_, v) in info)
        {
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if (isEdit) 255 else 0
        }

        ic_eye.visibility = if (isEdit) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = isEdit

        with(btn_edit)
        {
            val filter: ColorFilter? = if (isEdit)
            {
                PorterDuffColorFilter(resources.getColor(R.color.color_accent, theme),
                                      PorterDuff.Mode.SRC_IN)
            }
            else null

            val icon = if (isEdit)
                resources.getDrawable(R.drawable.ic_baseline_save_24, theme)
            else resources.getDrawable(R.drawable.ic_baseline_edit_24, theme)

            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }

    private fun saveProfileInfo()
    {
        Profile(firstName = et_first_name.text.toString(),
                lastName = et_last_name.text.toString(),
                about = et_about.text.toString(),
                repository = et_repository.text.toString()
               ).apply {
            viewModel.saveProfileData(this)
        }
    }
}