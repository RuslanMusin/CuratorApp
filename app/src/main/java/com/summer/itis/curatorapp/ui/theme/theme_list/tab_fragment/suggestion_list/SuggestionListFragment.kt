package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment
import com.summer.itis.curatorapp.ui.theme.suggestion_item.SuggestionFragment
import com.summer.itis.curatorapp.ui.theme.theme_list.ThemeListView
import com.summer.itis.curatorapp.utils.Const.CHANGED_CURATOR
import com.summer.itis.curatorapp.utils.Const.CHANGED_STUDENT
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_CURATOR
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_STUDENT
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.WAITING_CURATOR
import com.summer.itis.curatorapp.utils.Const.WAITING_STUDENT
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_theme_list.*
import kotlinx.android.synthetic.main.layout_recycler_list.*
import java.util.regex.Pattern


class SuggestionListFragment : BaseFragment<SuggestionListPresenter>(), SuggestionListView, View.OnClickListener {

    lateinit override var mainListener: NavigationView
    lateinit var fragmentParent: ThemeListView
    private lateinit var adapter: SuggestionAdapter

    lateinit var suggestions: MutableList<Suggestion>
    lateinit var userId: String

    @InjectPresenter
    lateinit var presenter: SuggestionListPresenter

    var actionNumber: Int = 0

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        const val EDIT_SKILLS = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = SuggestionListFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = SuggestionListFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_theme_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            userId = it.getString(ID_KEY)
            initViews()
            presenter.loadSkills(userId)
        }
    }

    override fun showSuggestions(suggestions: List<Suggestion>) {
        this.suggestions = suggestions.toMutableList()
        changeDataSet(this.suggestions)
    }

    private fun initViews() {
        initRecycler()
        setListeners()
    }

    private fun setListeners() {
        btn_add_theme.setOnClickListener(this)

    }

    override fun setNotLoading() {

    }

    override fun showLoading(disposable: Disposable) {
        pb_list.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_list.visibility = View.GONE
    }

    override fun loadNextElements(i: Int) {
    }


    override fun changeDataSet(tests: List<Suggestion>) {
        adapter.changeDataSet(tests)
        hideLoading()
    }

    override fun handleError(throwable: Throwable) {

    }

    private fun initRecycler() {
        adapter = SuggestionAdapter(ArrayList(), this)
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)

        rv_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && btn_add_theme.isShown())
                    btn_add_theme.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    btn_add_theme.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    override fun onItemClick(item: Suggestion) {
        val args = Bundle()
        args.putString(THEME_KEY, gsonConverter.toJson(item))
        val fragment = SuggestionFragment.newInstance(args, mainListener)
        mainListener.pushFragments(fragment, true)
    }

    override fun chooseUserFakeAction(pos: Int) {
        val suggestion = suggestions[pos]
        var listAction: MutableList<String> = ArrayList()
        val status = suggestion.status.name
        when (status) {

            WAITING_STUDENT -> {
                listAction = listOf(getString(R.string.accept_theme), getString(R.string.reject_theme),
                        getString(R.string.to_revision)).toMutableList()
                openFakeDialog(suggestion, listAction)
            }

            WAITING_CURATOR -> {
                listAction = listOf(getString(R.string.reject_theme)
                ).toMutableList()
                openFakeDialog(suggestion, listAction)
            }

            IN_PROGRESS_STUDENT -> {
                listAction = listOf(getString(R.string.reject_theme),
                        getString(R.string.save_changes)).toMutableList()
                openFakeDialog(suggestion, listAction)
            }

            IN_PROGRESS_CURATOR -> {
                listAction = listOf(getString(R.string.reject_theme)).toMutableList()
                openFakeDialog(suggestion, listAction)

            }

            CHANGED_CURATOR -> {
                listAction = listOf(getString(R.string.accept_theme), getString(R.string.reject_theme),
                        getString(R.string.to_revision)).toMutableList()
                openFakeDialog(suggestion, listAction)
            }

            CHANGED_STUDENT -> {
                listAction = listOf(getString(R.string.reject_theme)
                ).toMutableList()
                openFakeDialog(suggestion, listAction)

            }
        }

    }

    private fun openFakeDialog(suggestion: Suggestion, listAction: MutableList<String>) {
        actionNumber = 0
        this.activity?.let {
            MaterialDialog.Builder(it)
                    .title(R.string._should_user_fake_action_be_choosed)
                    .items(listAction)
                    .itemsCallbackSingleChoice(0 , { dialog, view, which, text ->
                        actionNumber = which
                        Log.d(TAG_LOG, "actionNum = $actionNumber and ${listAction[actionNumber]}")
                        true
                    })
                    .alwaysCallSingleChoiceCallback()
                    .positiveText(R.string.button_ok)
                    .negativeText(R.string.cancel)
                    .onNegative { dialog, which ->
                        dialog.dismiss()
                    }
                    .onPositive { dialog, which ->
                        this.context?.let { it1 ->
                            presenter.setFakeResponse(suggestion, listAction[actionNumber], it1)
                        }
                    }
                    .show()
        }
    }


    override fun findByQuery(query: String) {
        val pattern: Pattern = Pattern.compile("${query.toLowerCase()}.*")
        val list: MutableList<Suggestion> = java.util.ArrayList()
        for(skill in suggestions) {
            if(pattern.matcher(skill.theme?.title?.toLowerCase()).matches()) {
                list.add(skill)
            }
        }
        changeDataSet(list)
    }

    override fun onClick(view: View) {
        when(view.id) {

            R.id.btn_add_theme -> {
                val args = Bundle()
                args.putString(ID_KEY, userId)
                val fragment = AddThemeFragment.newInstance(args, mainListener)
                mainListener.pushFragments(fragment, true)
            }
        }
    }
}
