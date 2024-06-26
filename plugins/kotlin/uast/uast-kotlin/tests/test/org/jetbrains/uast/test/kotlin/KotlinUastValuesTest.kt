// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.jetbrains.uast.test.kotlin

import com.intellij.platform.uast.testFramework.common.ValuesTestBase
import org.jetbrains.kotlin.idea.base.plugin.KotlinPluginMode
import org.jetbrains.uast.UFile
import org.junit.Test

class KotlinUastValuesTest : AbstractKotlinValuesTest(), ValuesTestBase {

    override val pluginMode: KotlinPluginMode
        get() = KotlinPluginMode.K1

    override fun check(testName: String, file: UFile) {
        super<ValuesTestBase>.check(testName, file)
    }

    override fun getTestDataPath(): String = TEST_KOTLIN_MODEL_DIR.absolutePath

    @Test
    fun testAssertion() = doTest("Assertion")

    @Test
    fun testDelegate() = doTest("Delegate")

    @Test
    fun testIn() = doTest("In")

    @Test
    fun testLocalDeclarations() = doTest("LocalDeclarations")

    @Test
    fun testSimple() = doTest("Simple")

    @Test
    fun testStringTemplateComplex() = doTest("StringTemplateComplex")
}