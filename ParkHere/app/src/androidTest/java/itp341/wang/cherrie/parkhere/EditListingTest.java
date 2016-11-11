package itp341.wang.cherrie.parkhere;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EditListingTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    @Test
    public void editListingTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.buttonLogin), withText("Login"),
                        withParent(allOf(withId(R.id.activity_welcome),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextEmail), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextEmail), isDisplayed()));
        appCompatEditText2.perform(replaceText("jamesharper@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editTextPass), isDisplayed()));
        appCompatEditText3.perform(replaceText("12345678910!"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.rememberCheckBox), withText("Remember Me"), isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.buttonEnter), withText("Login"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(4, click()));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.edit),
                        withParent(withId(R.id.animation_area)),
                        isDisplayed()));
        imageView.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.listingTitleEditText), withText("Beverly hills")));
        appCompatEditText4.perform(scrollTo(), click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.listingTitleEditText), withText("Beverly hills")));
        appCompatEditText5.perform(scrollTo(), replaceText("Beverly hills pad"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.handicappedCheckBox), withText("Handicapped")));
        appCompatCheckBox2.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox3 = onView(
                allOf(withId(R.id.coveredCheckBox), withText("Covered")));
        appCompatCheckBox3.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox4 = onView(
                allOf(withId(R.id.suvCheckBox), withText("SUV")));
        appCompatCheckBox4.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox5 = onView(
                allOf(withId(R.id.mondayCheckBox), withText("Monday")));
        appCompatCheckBox5.perform(scrollTo(), click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.timeButton), withText("Select Time")));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("TO"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.title), withText("TO"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.ok), withText("OK"),
                        withParent(allOf(withId(R.id.done_background),
                                withParent(withId(R.id.time_picker_dialog)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.aboutEditText), withText("Beverly hills")));
        appCompatEditText6.perform(scrollTo(), replaceText("Beverly hills that's where I wanna be"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatSpinner = onView(
                withId(R.id.spinnerCancellation));
        appCompatSpinner.perform(scrollTo(), click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("Flexible"), isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction currencyEditText = onView(
                allOf(withId(R.id.priceEditText), withText("$12.00")));
        currencyEditText.perform(scrollTo(), replaceText("$12.0"), closeSoftKeyboard());

        ViewInteraction currencyEditText2 = onView(
                allOf(withId(R.id.priceEditText), withText("$1.20")));
        currencyEditText2.perform(scrollTo(), replaceText("$1.2"), closeSoftKeyboard());

        ViewInteraction currencyEditText3 = onView(
                allOf(withId(R.id.priceEditText), withText("$0.12")));
        currencyEditText3.perform(scrollTo(), replaceText("$0.129"), closeSoftKeyboard());

        ViewInteraction currencyEditText4 = onView(
                allOf(withId(R.id.priceEditText), withText("$1.29")));
        currencyEditText4.perform(scrollTo(), replaceText("$1.290"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.createListingButton), withText("Edit")));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction relativeLayout = onView(
                allOf(withClassName(is("android.widget.RelativeLayout")),
                        withParent(allOf(withId(R.id.listingBlurLayout),
                                withParent(withClassName(is("android.widget.RelativeLayout"))))),
                        isDisplayed()));
        relativeLayout.perform(click());

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.listingImageView),
                        childAtPosition(
                                allOf(withId(R.id.listingBlurLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        imageView2.check(doesNotExist());

        ViewInteraction imageView3 = onView(
                allOf(withId(R.id.listingDetail),
                        withParent(withId(R.id.animation_area)),
                        isDisplayed()));
        imageView3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.listingTitleTextView), withText("Beverly hills pad"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Beverly hills pad")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.priceTextView), withText("$12.00/hr"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        textView2.check(matches(withText("$12.90/hr")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.aboutTextView), withText("Beverly hills that's where I wanna be"),
                        childAtPosition(
                                allOf(withId(R.id.contentLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                2)),
                                7),
                        isDisplayed()));
        textView3.check(matches(withText("Beverly hills that's where I wanna be")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.cancellationTextView), withText("For a full refund of associated fees, cancellation must be made a full 24 hours prior to listing’s local start time (or 3:00 PM if not specified) on the start time of reserved day. If the Seeker cancels less than 24 hours before the start time, the costs are non-refundable."),
                        childAtPosition(
                                allOf(withId(R.id.contentLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                2)),
                                23),
                        isDisplayed()));
        textView4.check(matches(withText("For a full refund of associated fees, cancellation must be made a full 24 hours prior to listing’s local start time (or 3:00 PM if not specified) on the start time of reserved day. If the Seeker cancels less than 24 hours before the start time, the costs are non-refundable.")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
