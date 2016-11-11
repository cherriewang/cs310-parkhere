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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateListingTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    @Test
    public void createListingTest() {
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
        appCompatEditText3.perform(replaceText("123456"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editTextPass), withText("123456"), isDisplayed()));
        appCompatEditText4.perform(replaceText("12345678910!"), closeSoftKeyboard());

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
        recyclerView.perform(actionOnItemAtPosition(5, click()));

        ViewInteraction appCompatEditText5 = onView(
                withId(R.id.listingTitleEditText));
        appCompatEditText5.perform(scrollTo(), replaceText("Beverly hills"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.suvCheckBox), withText("SUV")));
        appCompatCheckBox2.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox3 = onView(
                allOf(withId(R.id.tandemCheckBox), withText("Tandem")));
        appCompatCheckBox3.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox4 = onView(
                allOf(withId(R.id.coveredCheckBox), withText("Covered")));
        appCompatCheckBox4.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox5 = onView(
                allOf(withId(R.id.mondayCheckBox), withText("Monday")));
        appCompatCheckBox5.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox6 = onView(
                allOf(withId(R.id.tuesdayCheckBox), withText("Tuesday")));
        appCompatCheckBox6.perform(scrollTo(), click());

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

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.locationTextView), withText("Location")));
        appCompatTextView3.perform(scrollTo(), click());

        ViewInteraction appCompatEditText6 = onView(
                withId(R.id.aboutEditText));
        appCompatEditText6.perform(scrollTo(), replaceText("Beverly hills"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatSpinner = onView(
                withId(R.id.spinnerCancellation));
        appCompatSpinner.perform(scrollTo(), click());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("Moderate"), isDisplayed()));
        appCompatTextView4.perform(click());

        ViewInteraction currencyEditText = onView(
                withId(R.id.priceEditText));
        currencyEditText.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());

        ViewInteraction currencyEditText2 = onView(
                allOf(withId(R.id.priceEditText), withText("$0.01")));
        currencyEditText2.perform(scrollTo(), replaceText("$0.012"), closeSoftKeyboard());

        ViewInteraction currencyEditText3 = onView(
                allOf(withId(R.id.priceEditText), withText("$0.12")));
        currencyEditText3.perform(scrollTo(), replaceText("$0.129"), closeSoftKeyboard());

        ViewInteraction currencyEditText4 = onView(
                allOf(withId(R.id.priceEditText), withText("$1.29")));
        currencyEditText4.perform(scrollTo(), replaceText("$1.290"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.createListingButton), withText("Create")));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(4, click()));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.listingImageView),
                        childAtPosition(
                                allOf(withId(R.id.listingBlurLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

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