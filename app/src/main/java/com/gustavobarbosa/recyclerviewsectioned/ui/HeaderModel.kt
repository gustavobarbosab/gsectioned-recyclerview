package com.gustavobarbosa.recyclerviewsectioned.ui

import com.gustavobarbosa.recyclerviewsectioned.lib.Header

data class HeaderModel(
    var title: String,
    var bodies: List<BodyModel>
): Header<BodyModel> {
    override fun getListBody(): List<BodyModel>? = bodies
}