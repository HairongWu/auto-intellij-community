package org.jetbrains.plugins.groovy.lang.stubs;

import org.jetbrains.plugins.groovy.testcases.simple.SimpleGroovyFileSetTestCase;
import org.jetbrains.plugins.groovy.util.TestUtils;
import org.jetbrains.annotations.NonNls;
import com.intellij.psi.PsiFile;
import com.intellij.psi.StubBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.NamedStub;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IStubFileElementType;
import com.intellij.lang.ASTNode;
import junit.framework.Test;
import junit.framework.Assert;

import java.util.List;

/**
 * @author ilyas
 */
public class StubsTest extends SimpleGroovyFileSetTestCase {
  @NonNls
  private static final String DATA_PATH = "test/org/jetbrains/plugins/groovy/lang/stubs/data";

  public StubsTest() {
    super(System.getProperty("path") != null ?
            System.getProperty("path") :
            DATA_PATH
    );
  }

  public String transform(String testName, String[] data) throws Exception {

    String fileText = data[0];
    PsiFile psiFile = TestUtils.createPseudoPhysicalGroovyFile(myProject, fileText);

    ASTNode node = psiFile.getNode();
    Assert.assertNotNull(node);
    IElementType type = node.getElementType();
    Assert.assertTrue(type instanceof IStubFileElementType);

    IStubFileElementType stubFileType = (IStubFileElementType) type;
    StubBuilder builder = stubFileType.getBuilder();
    StubElement element = builder.buildStubTree(psiFile);
    String stubTree = getStubTree(element);
    System.out.println("------------------------ " + testName + " ------------------------");
    System.out.println(stubTree);
    System.out.println("");
    return stubTree;
  }

  private static String getStubTree(StubElement element) {
    StringBuffer buffer = new StringBuffer();
    getStubsTreeImpl(element, buffer, "");
    return buffer.toString();
  }

  private static void getStubsTreeImpl(StubElement element, StringBuffer buffer, String offset) {
    PsiElement psi = element.getPsi();
    buffer.append(offset).append("[").append(psi.toString()).
            append(element instanceof NamedStub ? " : "+((NamedStub) element).getName() : "").
            append("]\n");
    for (StubElement stubElement : ((List<StubElement>) element.getChildrenStubs())) {
      getStubsTreeImpl(stubElement, buffer, "  ");
    }
  }

  public static Test suite() {
    return new StubsTest();
  }
}
