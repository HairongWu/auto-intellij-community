// "Replace with 'newFun()'" "true"

@Suppress("DEPRECATED_SINCE_KOTLIN_OUTSIDE_KOTLIN_SUBPACKAGE")
@Deprecated("", ReplaceWith("newFun()"))
@DeprecatedSinceKotlin(warningSince = "1.0")
fun oldFun() {
    newFun()
}

fun newFun() {}

fun foo() {
    <caret>oldFun()
}

// FUS_QUICKFIX_NAME: org.jetbrains.kotlin.idea.quickfix.replaceWith.DeprecatedSymbolUsageFix
// FUS_K2_QUICKFIX_NAME: org.jetbrains.kotlin.idea.k2.codeinsight.fixes.replaceWith.DeprecatedSymbolUsageFix