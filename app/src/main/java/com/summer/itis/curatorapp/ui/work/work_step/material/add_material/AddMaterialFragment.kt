package com.summer.itis.curatorapp.ui.work.work_step.material.add_material

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.material.Material
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.curator.curator_item.description.edit.ChangeDescPresenter
import com.summer.itis.curatorapp.ui.curator.curator_item.description.edit.ChangeDescView
import com.summer.itis.curatorapp.ui.curator.curator_item.description.view.DescriptionFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ADD_MATERIAL
import com.summer.itis.curatorapp.utils.Const.MATERIAL_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_change_description.*
import kotlinx.android.synthetic.main.toolbar_edit.*

class AddMaterialFragment : BaseFragment<AddMaterialPresenter>(), AddMaterialView, View.OnClickListener {

    lateinit var workId: String
    lateinit var stepId: String
    lateinit var material: Material
    override lateinit var mainListener: NavigationView

    @InjectPresenter
    lateinit var presenter: AddMaterialPresenter

    companion object {

        const val TAG_CURATOR = "TAG_CURATOR"

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = AddMaterialFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = AddMaterialFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        material = Material()
        arguments?.let {
            workId = it.getString(Const.WORK_KEY)
            stepId = it.getString(Const.STEP_KEY)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_material, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    fun initViews() {
        setToolbarData()
        setListeners()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_edit)
        toolbar_title.text = getString(R.string.add_material)
    }

    private fun setListeners() {
        btn_ok.setOnClickListener(this)
        btn_back.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_ok -> changeData()

            R.id.btn_back -> backFragment()
        }
    }

    private fun changeData() {
        material.content = et_description.text.toString()
        presenter.postMaterial(AppHelper.currentCurator.id, workId, stepId, material)
    }

    override fun showChanges(material: Material) {
        val intent = Intent()
        intent.putExtra(MATERIAL_KEY, gsonConverter.toJson(material))
        targetFragment?.onActivityResult(ADD_MATERIAL, Activity.RESULT_OK, intent)
        mainListener.hideFragment()
    }
}
