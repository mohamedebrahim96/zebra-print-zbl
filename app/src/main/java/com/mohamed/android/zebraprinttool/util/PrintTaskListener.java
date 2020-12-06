package com.mohamed.android.zebraprinttool.util;

import com.mohamed.android.zebraprinttool.ZebraPrintTask;

/**
 * Created by ctsims on 7/25/2016.
 */
public interface PrintTaskListener {
    void taskUpdate(ZebraPrintTask task);

    void taskFinished(boolean taskSuccesful);
}
