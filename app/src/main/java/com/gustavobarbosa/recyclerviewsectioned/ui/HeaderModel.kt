package com.gustavobarbosa.recyclerviewsectioned.ui

import com.gustavobarbosa.recyclerviewsectioned.lib.Body
import com.gustavobarbosa.recyclerviewsectioned.lib.Header

data class HeaderModel(
    var title: String,
    var bodies: List<BodyModel>
): Header {
    override fun getListBody(): List<Body>? = bodies
}