package com.summer.itis.curatorapp.ui.theme.suggestion_item

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.FOCUS_DOWN
import android.view.ViewGroup
import android.view.WindowManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.Status
import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.model.theme.Progress
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.comment.CommentFragment
import com.summer.itis.curatorapp.ui.curator.curator_item.description.view.DescriptionFragment
import com.summer.itis.curatorapp.ui.student.student_item.StudentFragment
import com.summer.itis.curatorapp.ui.theme.edit_suggestion.EditSuggestionFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ACCEPTED_BOTH
import com.summer.itis.curatorapp.utils.Const.ACCEPTED_BOTH_NUM
import com.summer.itis.curatorapp.utils.Const.CHANGED_CURATOR
import com.summer.itis.curatorapp.utils.Const.CHANGED_STUDENT
import com.summer.itis.curatorapp.utils.Const.CURATOR_KEY
import com.summer.itis.curatorapp.utils.Const.DESC_KEY
import com.summer.itis.curatorapp.utils.Const.EDIT_SUGGESTION
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_CURATOR
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_STUDENT
import com.summer.itis.curatorapp.utils.Const.REJECTED_CURATOR
import com.summer.itis.curatorapp.utils.Const.REJECTED_CURATOR_NUM
import com.summer.itis.curatorapp.utils.Const.REJECTED_STUDENT
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_KEY
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.TAB_NAME
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.TYPE
import com.summer.itis.curatorapp.utils.Const.USER_ID
import com.summer.itis.curatorapp.utils.Const.WAITING_CURATOR
import com.summer.itis.curatorapp.utils.Const.WAITING_STUDENT
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import com.summer.itis.curatorapp.utils.SkillViewHelper
import kotlinx.android.synthetic.main.layout_add_comment.*
import kotlinx.android.synthetic.main.layout_expandable_text_view.*
import kotlinx.android.synthetic.main.layout_suggestion.*
import kotlinx.android.synthetic.main.toolbar_edit.*
import java.util.*
import kotlin.collections.HashMap

class SuggestionFragment : CommentFragment<SuggestionPresenter>(), SuggestionView, View.OnClickListener {

    override var map: HashMap<String, String> = HashMap()
    override var type: String = Const.SUGGESTION_TYPE

    override lateinit var mainListener: NavigationView
    lateinit var suggestion: Suggestion

    @InjectPresenter
    override lateinit var presenter: SuggestionPresenter

    companion object {

        const val TAG_CURATOR = "TAG_CURATOR"

        const val EDIT_CURATOR = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = SuggestionFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = SuggestionFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_suggestion, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.loadComments(map, type)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainListener.hideBottomNavigation()
        mainListener.changeWindowsSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        arguments?.let {
            suggestion = gsonConverter.fromJson(it.getString(THEME_KEY), Suggestion::class.java)
            map[CURATOR_KEY] = AppHelper.currentCurator.id
            map[SUGGESTION_KEY] = suggestion.id
        }
    }

    fun initViews() {
        setToolbarData()
        setListeners()
        setData()
        setBtnsVisibility()
    }

    private fun setBtnsVisibility() {
        hideEdit(suggestion.status)
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_edit)
        toolbar_title.text = getString(R.string.suggestion)
//        suggestion.progress?.title?.let { mainListener.setToolbarTitle(it) }
        btn_back.visibility = View.VISIBLE

    }

    private fun setListeners() {
        setActionListeners()
        btn_edit.setOnClickListener(this)
        btn_back.setOnClickListener(this)

        li_student.setOnClickListener(this)
        li_desc.setOnClickListener(this)

        btn_accept.setOnClickListener(this)
        btn_reject.setOnClickListener(this)
        btn_revision.setOnClickListener(this)
    }

    private fun setActionListeners() {
        when (suggestion.status.name) {

            WAITING_CURATOR -> {
                if(!suggestion.type.equals(STUDENT_TYPE)) {
                    btn_revision.visibility = View.GONE
                }
            }

            WAITING_STUDENT -> {
                btn_accept.visibility = View.GONE
                btn_revision.visibility = View.GONE
            }

            IN_PROGRESS_CURATOR -> {
                btn_accept.visibility = View.GONE
                btn_reject.visibility = View.GONE
                btn_revision.text = getText(R.string.send_changes)
            }

            IN_PROGRESS_STUDENT -> {
                btn_accept.visibility = View.GONE
                btn_revision.visibility = View.GONE
            }

            CHANGED_CURATOR -> {
                btn_accept.visibility = View.GONE
                btn_revision.visibility = View.GONE
            }

            REJECTED_CURATOR -> {
                btn_accept.visibility = View.GONE
                btn_reject.visibility = View.GONE
                btn_revision.visibility = View.GONE
            }

            REJECTED_STUDENT -> {
                btn_accept.visibility = View.GONE
                btn_reject.visibility = View.GONE
                btn_revision.visibility = View.GONE
            }

            ACCEPTED_BOTH -> {
                btn_accept.visibility = View.GONE
                btn_reject.visibility = View.GONE
                btn_revision.visibility = View.GONE
            }

        }

    }

    private fun setData() {
        if(suggestion.theme?.skills?.size == 0) {
            li_skills.visibility = View.GONE
        } else {
            tv_skills.text = this.activity?.let { suggestion.theme?.skills?.let { it1 -> SkillViewHelper.getSkillsText(it1, it) } }
        }
        tv_title.text = suggestion.getCorrectTitle()
        tv_curator.text = suggestion.curator?.name
        tv_student.text = suggestion.student?.name
        tv_subject.text = suggestion.theme?.subject?.name
        setStatus(suggestion.status.name)
        expand_text_view.text = suggestion.getCorrectDesc()
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_back -> backFragment()

            R.id.btn_edit -> editProfile()

            R.id.li_student -> showStudent()

            R.id.li_desc -> showDesc()

            R.id.btn_accept -> acceptTheme()

            R.id.btn_reject -> rejectTheme()

            R.id.btn_revision -> {
                presenter.revisionTheme(suggestion)
            }
        }
    }

    override fun hideEdit(status: Status) {
        if(!status.name.equals(IN_PROGRESS_CURATOR)) {
            btn_edit.visibility = View.GONE
        }
    }

    override fun setStatus(status: String) {
        var uiStatus = getString(R.string.waiting_curator)
        when (status) {

            WAITING_CURATOR -> uiStatus = getString(R.string.waiting_curator)

            WAITING_STUDENT -> uiStatus = getString(R.string.waiting_student)

            ACCEPTED_BOTH -> uiStatus = getString(R.string.accepted_both)

            REJECTED_CURATOR -> uiStatus = getString(R.string.rejected_curator)

            REJECTED_STUDENT -> uiStatus = getString(R.string.rejected_student)

            IN_PROGRESS_CURATOR -> uiStatus = getString(R.string.in_progress_curator)

            IN_PROGRESS_STUDENT -> uiStatus = getString(R.string.in_progress_student)

            CHANGED_CURATOR -> uiStatus = getString(R.string.changed_curator)

            CHANGED_STUDENT -> uiStatus = getString(R.string.changed_student)

        }
        tv_status.text = uiStatus
    }

    private fun showDesc() {
        val args = Bundle()
        args.putString(DESC_KEY, suggestion.progress?.description)
        args.putString(TYPE, SUGGESTION_TYPE)
        args.putString(USER_ID, AppHelper.currentCurator.id)
        args.putString(ID_KEY, suggestion.id)
        val fragment = DescriptionFragment.newInstance(args, mainListener)
        mainListener.pushFragments(fragment, true)
    }

    private fun editProfile() {
        val args = Bundle()
        args.putString(THEME_KEY, gsonConverter.toJson(suggestion))
        val fragment = EditSuggestionFragment.newInstance(args, mainListener)
        fragment.setTargetFragment(this, EDIT_SUGGESTION)
        mainListener.showFragment(this, fragment)
    }

    private fun showStudent() {
        val args = Bundle()
        args.putString(USER_ID, suggestion.studentId)
        args.putString(TAB_NAME, TAB_THEMES)
        val fragment = StudentFragment.newInstance(args, mainListener)
        mainListener.pushFragments(fragment, true)
    }

    private fun acceptTheme() {
        when (suggestion.status.name) {

            WAITING_CURATOR -> {
                for (sug in AppHelper.currentCurator.suggestions) {
                    if(sug.id.equals(suggestion.id)) {
                        sug.status = Status(ACCEPTED_BOTH_NUM, ACCEPTED_BOTH)
                        for(theme in AppHelper.currentCurator.themes) {
                            if(theme.id.equals(suggestion.theme?.id)) {
                                theme.dateAcceptance = Date()
                                theme.student = sug.student
                            }
                        }
                    } else if (suggestion.theme?.id.equals(sug.theme?.id)) {
                        sug.status = Status(REJECTED_CURATOR_NUM, REJECTED_CURATOR)
                    }
                }

            }
        }
        presenter.acceptTheme(suggestion)
        setStatus(ACCEPTED_BOTH)
    }

    private fun rejectTheme() {
        presenter.rejectCurator(suggestion)
    }

    override fun clearAfterSendComment() {
        scrollView.fullScroll(FOCUS_DOWN)
        et_comment.setText(null)
        view?.getRootView()?.let {
            AppHelper.hideKeyboardFrom(this.activity as Context, it)
            Log.d(TAG_LOG, "hide keyboard")
        }
        et_comment.clearFocus()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {

                EDIT_SUGGESTION -> {
                    val json = data?.getStringExtra(THEME_KEY)
                    json?.let {
                        val themeProgress = gsonConverter.fromJson(json, Progress::class.java)
                        tv_title.text = themeProgress.title
                        expand_text_view.text = themeProgress.description

                        suggestion.progress = themeProgress

                        mainListener.showSnackBar(getString(R.string.changes_updated))
                    }
                }
            }
        }
    }
}
