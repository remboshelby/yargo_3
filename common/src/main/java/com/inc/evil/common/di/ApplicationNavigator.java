package com.inc.evil.common.di;

import com.inc.evil.common.base.BaseActivity;

public interface ApplicationNavigator {
    void openFragment(BaseActivity baseActivity, String fragmentName);
}
