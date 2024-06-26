// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.openapi.components.impl;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.testFramework.HeavyPlatformTestCase;
import com.intellij.testFramework.OpenProjectTaskBuilder;
import com.intellij.testFramework.PlatformTestUtil;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

import static com.intellij.testFramework.assertions.Assertions.assertThat;
import static com.intellij.workspaceModel.ide.impl.legacyBridge.module.ModuleTypeUtils.WEB_MODULE_ENTITY_TYPE_ID_NAME;
import static com.intellij.workspaceModel.ide.legacyBridge.impl.java.JavaModuleTypeUtils.JAVA_MODULE_ENTITY_TYPE_ID_NAME;

public class OverwriteProjectConfigurationTest extends HeavyPlatformTestCase {
  private Path myProjectDir;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    myProjectDir = createTempDir(getTestDirectoryName()).toPath();
  }

  public void testOverwriteModulesList() {
    Project project = ProjectManagerEx.getInstanceEx().newProject(myProjectDir, new OpenProjectTaskBuilder().build());
    try {
      createModule(project, "module", JAVA_MODULE_ENTITY_TYPE_ID_NAME);
      PlatformTestUtil.saveProject(project);
    }
    finally {
      PlatformTestUtil.forceCloseProjectWithoutSaving(project);
    }

    project = ProjectManagerEx.getInstanceEx().newProject(myProjectDir, new OpenProjectTaskBuilder().build());
    try {
      PlatformTestUtil.saveProject(project);
      assertThat(ModuleManager.getInstance(project).getModules()).isEmpty();
    }
    finally {
      PlatformTestUtil.forceCloseProjectWithoutSaving(project);
    }
  }

  public void testOverwriteModuleType() {
    Project project = ProjectManagerEx.getInstanceEx().newProject(myProjectDir, new OpenProjectTaskBuilder().build());
    try {
      Path imlFile = createModule(project, "module", JAVA_MODULE_ENTITY_TYPE_ID_NAME);
      PlatformTestUtil.saveProject(project);
      assertThat(imlFile).isRegularFile();
    }
    finally {
      PlatformTestUtil.forceCloseProjectWithoutSaving(project);
    }

    project = ProjectManagerEx.getInstanceEx().newProject(myProjectDir, new OpenProjectTaskBuilder().build());
    try {
      createModule(project, "module", WEB_MODULE_ENTITY_TYPE_ID_NAME);
      PlatformTestUtil.saveProject(project);
      Module module = assertOneElement(ModuleManager.getInstance(project).getModules());
      assertEquals(WEB_MODULE_ENTITY_TYPE_ID_NAME, ModuleType.get(module).getId());
    }
    finally {
      PlatformTestUtil.forceCloseProjectWithoutSaving(project);
    }
  }

  @NotNull
  private Path createModule(@NotNull Project project, @NotNull String moduleName, @NotNull String moduleTypeId) {
    Path imlFile = myProjectDir.resolve(moduleName + ".iml");
    ApplicationManager.getApplication().runWriteAction(() -> {
      ModuleManager.getInstance(project).newModule(imlFile.toAbsolutePath().toString(), moduleTypeId);
    });
    return imlFile;
  }
}
