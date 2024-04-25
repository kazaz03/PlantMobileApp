package com.example.projekat

import ToastMatcher
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.hamcrest.number.OrderingComparison.greaterThan
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestiranjeValidacije {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun PrekratakNaziv()
    {
        //provjera je li se postavi error kad ima manje od 3 il vise od 19 karaktera
        //pokretanje nove aktivnosti
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
        // Provjerite je li Toast poruka prikazana
        onView(withText(errorPoruka)).inRoot(ToastMatcher()).check(matches(isDisplayed()))
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