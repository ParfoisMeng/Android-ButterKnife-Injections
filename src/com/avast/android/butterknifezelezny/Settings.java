package com.avast.android.butterknifezelezny;

import com.avast.android.butterknifezelezny.common.Utils;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Settings UI for the plugin.
 *
 * @author David Vávra (vavra@avast.com)
 */
public class Settings implements Configurable {

    public static final String PREFIX = "butterknifezelezny_prefix";
    public static final String VIEWHOLDER_CLASS_NAME = "butterknifezelezny_viewholder_class_name";
    public static final String INIT_BUTTERKNIFE = "init_butterknife";
    public static final String USE_IN_LIB = "use_in_lib";

    private JPanel mPanel;
    private JTextField mHolderName;
    private JTextField mPrefix;
    private JComboBox mComboBox;
    private JComboBox useInLibComboBox;

    @Nls
    @Override
    public String getDisplayName() {
        return "ButterKnifeZelezny";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        reset();
        return mPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        PropertiesComponent.getInstance().setValue(PREFIX, mPrefix.getText());
        PropertiesComponent.getInstance().setValue(VIEWHOLDER_CLASS_NAME, mHolderName.getText());
        PropertiesComponent.getInstance().setValue(INIT_BUTTERKNIFE, mComboBox.getSelectedItem().toString());
        PropertiesComponent.getInstance().setValue(USE_IN_LIB, useInLibComboBox.getSelectedItem().toString());
    }

    @Override
    public void reset() {
        mPrefix.setText(Utils.getPrefix());
        mHolderName.setText(Utils.getViewHolderClassName());
        if (Utils.isInitButterKnifeDefault()) {
            mComboBox.setSelectedIndex(0);
        } else {
            mComboBox.setSelectedIndex(1);
        }
        if (Utils.useInLibDefault()) {
            useInLibComboBox.setSelectedIndex(1);
        } else {
            useInLibComboBox.setSelectedIndex(0);
        }
    }

    @Override
    public void disposeUIResources() {

    }
}
