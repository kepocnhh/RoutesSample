package test.android.routes.provider

import test.android.routes.entity.Foo

internal class FinalLocals : Locals {
    override var objects = listOf<Foo>()
}
