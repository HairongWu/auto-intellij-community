// "Change to function invocation" "true"
fun bar(i: Int, j: Int) {}

fun test(s: String){
    "$bar<caret>(1, 2) sometext $s"
}
// FUS_QUICKFIX_NAME: org.jetbrains.kotlin.idea.quickfix.ChangeToFunctionInvocationFix
// FUS_K2_QUICKFIX_NAME: org.jetbrains.kotlin.idea.quickfix.ChangeToFunctionInvocationFix