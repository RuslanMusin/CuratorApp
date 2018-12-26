package com.summer.itis.curatorapp.ui.work.work_step.material.add_material

import com.summer.itis.curatorapp.model.material.Material
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface AddMaterialView: BaseFragView {

    fun showChanges(material: Material)

}