// "Wrap with '?.let { ... }' call" "true"
// WITH_STDLIB

fun f(s: String, action: (String.() -> Unit)?) {
    s.foo().bar().action<caret>()
}

fun String.foo() = ""

fun String.bar() = ""
// FUS_QUICKFIX_NAME: org.jetbrains.kotlin.idea.quickfix.WrapWithSafeLetCallFix
// FUS_K2_QUICKFIX_NAME: org.jetbrains.kotlin.idea.k2.codeinsight.fixes.WrapWithSafeLetCallFixFactories$WrapWithSafeLetCallModCommandAction