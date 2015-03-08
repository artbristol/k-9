package com.fsck.k9.endtoend.pages;

import com.fsck.k9.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ComposePage extends AbstractPage {


    public void inputTo(String toAddress) {
        onView(withId(R.id.to)).perform(typeText(toAddress));
    }
}
