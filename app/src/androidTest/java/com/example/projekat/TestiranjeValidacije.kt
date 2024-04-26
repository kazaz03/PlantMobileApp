package com.example.projekat

import android.content.Intent
import android.os.IBinder
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.ActivityAction
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.view.Window
import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import ToastMatcher


@RunWith(AndroidJUnit4::class)
class TestiranjeValidacije {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun PrekratakNaziv()
    {
        val pokreniDetalje= Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDetalje)
        val tekstZaPopunit1="b"
        val errorPoruka="Naziv mora imati između 2 i 20 znakova"
        onView(withId(R.id.nazivET)).perform(typeText(tekstZaPopunit1),closeSoftKeyboard())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText(errorPoruka)))
    }
    @Test
    fun PredugNaziv()
    {
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        val tekstZaPopunit2="abcdefghijklmnoprstuvzk"
        val errorPoruka="Naziv mora imati između 2 i 20 znakova"
        onView(withId(R.id.nazivET)).perform(typeText(tekstZaPopunit2), closeSoftKeyboard())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText(errorPoruka)))
    }

    @Test
    fun PrekratkaPorodica()
    {
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        val tekstZaPopunit1="a"
        val errorPoruka="Porodica mora imati između 2 i 20 znakova"
        onView(withId(R.id.porodicaET)).perform(typeText(tekstZaPopunit1), closeSoftKeyboard())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText(errorPoruka)))
    }

    @Test
    fun PredugaPorodica()
    {
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        val tekstZaPopunit2="abcdefghijklmnoprstuvzk"
        val errorPoruka="Porodica mora imati između 2 i 20 znakova"
        onView(withId(R.id.porodicaET)).perform(typeText(tekstZaPopunit2), closeSoftKeyboard())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText(errorPoruka)))
    }

    @Test
    fun PrekratkoMedicinskoUpozorenje()
    {
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        val tekstZaPopunit1="a"
        val errorPoruka="Upozorenje mora imat između 2 i 20 znakova"
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText(tekstZaPopunit1), closeSoftKeyboard())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText(errorPoruka)))
    }

    @Test
    fun PredugoMedicinskoUpozorenje()
    {
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        val tekstZaPopunit2="abcdefghijklmnoprstuvzk"
        val errorPoruka="Upozorenje mora imat između 2 i 20 znakova"
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText(tekstZaPopunit2), closeSoftKeyboard())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText(errorPoruka)))
    }

    @Test
    fun PrekratkoJelo()
    {
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        val tekstZaPopunit1="a"
        val errorPoruka="Jelo mora imati između 2 i 20 znakova"
        onView(withId(R.id.jeloET)).perform(typeText(tekstZaPopunit1), closeSoftKeyboard())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText(errorPoruka)))
    }

    @Test
    fun PredugoJelo()
    {
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        val tekstZaPopunit2="abcdefghijklmnoprstuvzk"
        val errorPoruka="Jelo mora imati između 2 i 20 znakova"
        onView(withId(R.id.jeloET)).perform(typeText(tekstZaPopunit2), closeSoftKeyboard())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText(errorPoruka)))
    }
    @Test
    fun DvaIstaJelaUListi()
    {
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        val jelo1="Banana split"
        val kopija="banana split"
        val errorPoruka="Jelo je već dodano u listi"
        onView(withId(R.id.jeloET)).perform(typeText(jelo1), closeSoftKeyboard())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.main)).perform(swipeDown())
        onView(withId(R.id.jeloET)).perform(clearText())
        onView(withId(R.id.jeloET)).perform(typeText(kopija), closeSoftKeyboard())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withText("Jelo je već dodano u listi")).inRoot( ToastMatcher()).check(matches(withText(errorPoruka)))
    }
    /*@Test
    fun PrikazSlikeUImageView()
    {
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.uslikajBiljkuBtn)).perform(click())
        try {
            Thread.sleep(1000);
        } catch (e: Exception) {
            e.printStackTrace();
        }
        onView(withId(R.id.slikaIV)).check(matches(isDisplayed()));
    }*/
}
