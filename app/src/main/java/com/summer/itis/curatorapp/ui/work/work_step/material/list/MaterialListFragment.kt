package com.summer.itis.curatorapp.ui.work.work_step.material.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.material.Material
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.work.work_step.material.add_material.AddMaterialFragment
import com.summer.itis.curatorapp.utils.Const.ADD_MATERIAL
import com.summer.itis.curatorapp.utils.Const.MATERIAL_KEY
import com.summer.itis.curatorapp.utils.Const.STEP_KEY
import com.summer.itis.curatorapp.utils.Const.WORK_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_recycler_list.*
import kotlinx.android.synthetic.main.toolbar_add.*

class MaterialListFragment : BaseFragment<MaterialListPresenter>(), MaterialListView, View.OnClickListener {

    lateinit var workId: String
    lateinit var stepId: String
    lateinit override var mainListener: NavigationView
    private lateinit var adapter: MaterialAdapter

    lateinit var skills: MutableList<Material>

    @InjectPresenter
    lateinit var presenter: MaterialListPresenter

    companion object {

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = MaterialListFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = MaterialListFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            workId = it.getString(WORK_KEY)
            stepId = it.getString(STEP_KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_materials, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.loadMaterials(workId, stepId)
    }

    override fun showSkills(skills: List<Material>) {
        this.skills = skills.toMutableList()
        changeDataSet(this.skills)
    }

    private fun initViews() {
        setToolbarData()
        initRecycler()
        setListeners()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_add)
        toolbar_title.text = getString(R.string.materials)
    }

    private fun setListeners() {
        btn_add.setOnClickListener(this)
        btn_back.setOnClickListener(this)
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


    override fun changeDataSet(tests: List<Material>) {
        adapter.changeDataSet(tests)
        hideLoading()
    }

    override fun handleError(throwable: Throwable) {

    }

    private fun initRecycler() {
        adapter = MaterialAdapter(ArrayList())
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(item: Material) {
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_add -> addMaterial()

            R.id.btn_back -> backFragment()

        }
    }

    private fun addMaterial() {
        val args = Bundle()
        args.putString(WORK_KEY, workId)
        args.putString(STEP_KEY, stepId)
        val fragment = AddMaterialFragment.newInstance(args, mainListener)
        fragment.setTargetFragment(this, ADD_MATERIAL)
        mainListener.showFragment(this, fragment)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {

                ADD_MATERIAL -> {
                    data?.getStringExtra(MATERIAL_KEY)?.let {
                        val material = gsonConverter.fromJson(it, Material::class.java)
                        skills.add(0, material)
                        changeDataSet(skills)
                    }
                }
            }
        }
    }

}
