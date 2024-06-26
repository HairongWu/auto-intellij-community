/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.psi.search;

import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Internal
public interface PsiReferenceProcessor { // todo: drop
  boolean execute(PsiReference element);

  class CollectElements implements PsiReferenceProcessor {
    private final Collection<PsiReference> myCollection;

    public CollectElements(Collection<PsiReference> collection) {
      myCollection = Collections.synchronizedCollection(collection);
    }

    public CollectElements() {
      this(new ArrayList<>());
    }

    public PsiReference @NotNull [] toArray() {
      return myCollection.toArray(PsiReference.EMPTY_ARRAY);
    }

    public PsiReference @NotNull [] toArray(PsiReference[] array) {
      return myCollection.toArray(array);
    }

    @Override
    public boolean execute(PsiReference element) {
      myCollection.add(element);
      return true;
    }
  }
}