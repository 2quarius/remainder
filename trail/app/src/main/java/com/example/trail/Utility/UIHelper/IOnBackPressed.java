package com.example.trail.Utility.UIHelper;

/**
 * 用于CalendarFragment override onBackPressed
 * 除此之外还需要在CalendarFragment的 hosting activity里override
 */
public interface IOnBackPressed {
    /**
     * If you return true the back press will not be taken into account, otherwise the activity will act naturally
     * @return true if your processing has priority if not false
     */
    boolean onBackPressed();
}
