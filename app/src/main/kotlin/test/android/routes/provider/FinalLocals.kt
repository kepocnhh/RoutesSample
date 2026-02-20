package test.android.routes.provider

import test.android.routes.entity.Foo
import java.util.UUID

internal class FinalLocals : Locals {
//    override var objects = listOf<Foo>()
    override var objects = (0 until 32).map { index ->
            Foo(
                id = UUID(0, index.toLong()),
                text = "foo:text:$index",
                number = index,
            )
    }
}
