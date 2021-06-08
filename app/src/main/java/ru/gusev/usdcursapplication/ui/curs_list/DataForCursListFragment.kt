package ru.gusev.usdcursapplication.ui.curs_list

import ru.gusev.usdcursapplication.data.model.CursInfoModel

data class DataForCursListFragment(
    var currentCurs: CursInfoModel,
    var dynamicCurs: List<CursInfoModel>
)