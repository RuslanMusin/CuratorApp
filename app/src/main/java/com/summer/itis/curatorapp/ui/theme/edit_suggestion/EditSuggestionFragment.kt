package com.summer.itis.curatorapp.ui.theme.edit_suggestion

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.utils.Const.ALL_CHOOSED
import com.summer.itis.curatorapp.utils.Const.EDIT_SUGGESTION
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_edit_suggestion.*
import kotlinx.android.synthetic.main.toolbar_back_done.*

class EditSuggestionFragment : BaseFragment<EditSuggestionPresenter>(), EditSuggestionView, View.OnClickListener {

    private lateinit var suggestion: Suggestion

    override lateinit var mainListener: NavigationView

    private var studentType = ALL_CHOOSED

    @InjectPresenter
    lateinit var presenter: EditSuggestionPresenter

    companion object {

        fun newInstance(args: Bundle, mainListener: NavigationView): Fragment {
            val fragment = EditSuggestionFragment()
            fragment.arguments = args
            fragment.mainListener = mainListener
            return fragment
        }

        fun newInstance(mainListener: NavigationView): Fragment {
            val fragment = EditSuggestionFragment()
            fragment.mainListener = mainListener
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            suggestion = gsonConverter.fromJson(it.getString(THEME_KEY), Suggestion::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_suggestion, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setToolbarData()
        setListeners()
        setThemeData()
    }

    private fun setThemeData() {
        et_theme_name.setText(suggestion.getCorrectTitle())
        et_theme_desc.setText(suggestion.getCorrectDesc())
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_back_done)
        mainListener.setToolbarTitle(getString(R.string.change_suggestion))
    }

    private fun setListeners() {
        btn_ok.setOnClickListener(this)
        btn_back.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_ok -> {
                if(validateData()) {
                    val title = et_theme_name.text.toString()
                    val desc = et_theme_desc.text.toString()

                    suggestion.progress?.title = title
                    suggestion.progress?.description = desc

                    presenter.updateSuggestion(suggestion)
                }
            }

            R.id.btn_back -> backFragment()

        }
    }
    override fun returnEditResult(intent: Intent?) {
        targetFragment?.onActivityResult(EDIT_SUGGESTION, Activity.RESULT_OK, intent)
        mainListener.hideFragment()
    }

    private fun validateData(): Boolean{
        val name = et_theme_name.text.toString()
        val desc  = et_theme_desc.text.toString()
        if(name.equals("") || desc.equals("")) {
            return false
        } else {
            return true
        }
    }
}