// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.ui;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.ApiStatus;

import javax.swing.border.LineBorder;
import java.awt.*;

public class SideBorder extends LineBorder {
  public static final int NONE = 0;
  public static final int LEFT = 0x01;
  public static final int TOP = 0x02;
  public static final int RIGHT = 0x04;
  public static final int BOTTOM = 0x08;
  public static final int ALL = LEFT | TOP | RIGHT | BOTTOM;

  @MagicConstant(flags = {NONE, LEFT, TOP, RIGHT, BOTTOM, ALL})
  public @interface SideMask {}

  @ApiStatus.Internal
  protected final int mySideMask;

  public SideBorder(Color color, @SideMask int mask) {
    this(color, mask, 1);
  }

  public SideBorder(Color color, @SideMask int mask, int thickness) {
    super(color, thickness);
    mySideMask = mask;
  }

  @Override
  public Insets getBorderInsets(Component component) {
    return new Insets(
      (mySideMask & TOP) != 0 ? getThickness() : 0,
      (mySideMask & LEFT) != 0 ? getThickness() : 0,
      (mySideMask & BOTTOM) != 0 ? getThickness() : 0,
      (mySideMask & RIGHT) != 0 ? getThickness() : 0
    );
  }

  @Override
  public Insets getBorderInsets(Component component, Insets insets) {
    insets.top = (mySideMask & TOP) != 0 ? getThickness() : 0;
    insets.left = (mySideMask & LEFT) != 0 ? getThickness() : 0;
    insets.bottom = (mySideMask & BOTTOM) != 0 ? getThickness() : 0;
    insets.right = (mySideMask & RIGHT) != 0 ? getThickness() : 0;
    return insets;
  }

  @Override
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    Color oldColor = g.getColor();
    g.setColor(getLineColor());
    if ((mySideMask & LEFT) != 0) g.fillRect(x, y, thickness, height);
    if ((mySideMask & TOP) != 0) g.fillRect(x, y, width, thickness);
    if ((mySideMask & RIGHT) != 0) g.fillRect(x + width - thickness, y, thickness, height);
    if ((mySideMask & BOTTOM) != 0) g.fillRect(x, y + height - thickness, width, thickness);
    g.setColor(oldColor);
  }
}
