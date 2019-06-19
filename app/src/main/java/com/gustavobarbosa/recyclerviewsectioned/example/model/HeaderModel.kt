package com.gustavobarbosa.recyclerviewsectioned.example.model

import com.gustavobarbosa.recyclerviewsectioned.lib.model.Header

data class HeaderModel(
    var title: String,
    var bodies: List<BodyModel>
): Header<BodyModel> {
    override fun getListBody(): List<BodyModel>? = bodies
}