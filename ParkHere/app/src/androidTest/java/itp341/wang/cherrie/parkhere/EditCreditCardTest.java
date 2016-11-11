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
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
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
public class EditCreditCardTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    @Test
    public void editCreditCardTest() {
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
        recyclerView.perform(actionOnItemAtPosition(6, click()));

        ViewInteraction creditCardView = onView(
                allOf(withClassName(is("com.cooltechworks.creditcarddesign.CreditCardView")),
                        withParent(withId(R.id.card_container))));
        creditCardView.perform(scrollTo(), click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.card_number_field), withText("4242424242424242"), isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.card_number_field), withText("4242424242424242"), isDisplayed()));
        appCompatEditText5.perform(replaceText("424242424242424"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.card_number_field), withText("4242 4242 4242 424"), isDisplayed()));
        appCompatEditText6.perform(replaceText("4242 4242 4242 "), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.card_number_field), withText("4242 4242 4242"), isDisplayed()));
        appCompatEditText7.perform(replaceText("4242 4242 42425"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.card_number_field), withText("4242 4242 4242 5"), isDisplayed()));
        appCompatEditText8.perform(replaceText("4242 4242 4242 5555"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.card_expiry), withText("01/18"), isDisplayed()));
        appCompatEditText9.perform(replaceText("024"), closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.card_expiry), withText("02/4"), isDisplayed()));
        appCompatEditText10.perform(replaceText("02/40"), closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.card_cvv), withText("420"), isDisplayed()));
        appCompatEditText11.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.card_name), withText("Glarence Zhao"), isDisplayed()));
        appCompatEditText12.perform(pressImeActionButton());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.next), withText("DONE"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.front_card_container),
                        childAtPosition(
                                allOf(withId(R.id.card_container),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        relativeLayout.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.front_card_number), withText("4242  4242  4242  4242"),
                        childAtPosition(
                                allOf(withId(R.id.front_card_container),
                                        childAtPosition(
                                                withId(R.id.card_container),
                                                0)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("4242  4242  4242  5555")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.front_card_holder_name), withText("Glarence Zhao"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Glarence Zhao")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.front_card_expiry), withText("01/18"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("02/40")));

        ViewInteraction relativeLayout3 = onView(
                allOf(withId(R.id.front_card_container),
                        childAtPosition(
                                allOf(withId(R.id.card_container),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        relativeLayout3.check(doesNotExist());

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
