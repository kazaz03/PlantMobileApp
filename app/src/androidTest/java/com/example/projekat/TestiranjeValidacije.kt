package com.example.projekat

import android.content.Intent
import android.view.View
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
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.hamcrest.Matchers.anything
import androidx.test.rule.GrantPermissionRule
import androidx.test.internal.platform.content.PermissionGranter
import android.Manifest
import android.widget.TextView
import org.hamcrest.Matcher

@RunWith(AndroidJUnit4::class)
class TestiranjeValidacije {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    /*@get:Rule
    val permissionRule: GrantPermissionRule=GrantPermissionRule.grant(Manifest.permission.CAMERA)*/
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
        onView(withId(R.id.duplikatJelaError)).check(matches(withErrorText(errorPoruka)))
    }

    @Test
    fun praznaListaJela()
    {
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.praznaListaJelaError)).check(matches(withErrorText("Mora biti dodano barem jedno jelo u listi jela")))
        onView(withId(R.id.main)).perform(swipeDown())
        onView(withId(R.id.jeloET)).perform(typeText("Kljukusa"), closeSoftKeyboard())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.duplikatJelaError)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }
    @Test
    fun profilOkusaTest()
    {
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.main)).perform(swipeDown())
        onView(withId(R.id.profilOkusaError)).check(matches(withErrorText("Mora biti odabran jedan okus")))
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.main)).perform(swipeDown())
        onView(withId(R.id.profilOkusaError)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @Test
    fun medicinskaKoristListaTest()
    {
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.main)).perform(swipeDown())
        onView(withId(R.id.medicinskaKoristError)).check(matches(withErrorText("Mora biti odabrana bar jedna medicinska korist")))
        /* posto mi ne radi scrollTo samo cu napisat kako bi uradila da radi tjst sta bi jos dodala na test
        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo()).check(matches(isDisplayed()))
        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.main)).perform(swipeDown())
        onView(withId(R.id.medicinskaKoristError)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))*/
    }

    @Test
    fun zemljisniTipListaTest(){
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.main)).perform(swipeDown())
        onView(withId(R.id.zemljisniTipError)).check(matches(withErrorText("Mora biti odabrano bar jedno zemljiste")))
        /* posto mi ne radi scrollTo samo cu napisat kako bi uradila da radi tjst sta bi jos dodala na test
        onView(withId(R.id.zemljisnjiTipLV)).perform(scrollTo()).check(matches(isDisplayed()))
        onData(anything()).inAdapterView(withId(R.id.zemljisnjiTipLV).atPosition(0).perform(click())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.main)).perform(swipeDown())
        onView(withId(R.id.zemljisniTipError)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))*/
    }

    @Test
    fun klimatskiTipListaTest()
    {
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.main)).perform(swipeDown())
        onView(withId(R.id.klimatskiTipError)).check(matches(withErrorText("Mora biti odabrana bar jedna klima")))
        /* posto mi ne radi scrollTo samo cu napisat kako bi uradila da radi tjst sta bi jos dodala na test
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo()).check(matches(isDisplayed()))
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV).atPosition(0).perform(click())
        onView(withId(R.id.main)).perform(swipeUp())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.main)).perform(swipeDown())
        onView(withId(R.id.klimatskiTipError)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))*/
    }
    @Test
    fun PrikazSlikeUImageView()
    {
        Intents.init()
        val pokreniDalje=Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniDalje)
        //ne mogu odma dopustit permission kameri znam kako treba ima gore na pocetku klase
        //sa grantpermissionrule ali mi iz nekog razloga ne radi
        onView(withId(R.id.main)).perform(swipeUp())
        val bitmap = BitmapFactory.decodeResource(
            getInstrumentation().targetContext.resources,
            R.drawable.aloevera
        )
        val resultData = Intent().apply {
            putExtra("data", bitmap)
        }
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result)
        onView(withId(R.id.uslikajBiljkuBtn)).perform(click())
        onView(withId(R.id.slikaIV)).check(matches(withImage(R.drawable.aloevera)))
        Intents.release()
    }

    private fun withImage(expectedResourceId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with image drawable from resource id: ")
                description.appendValue(expectedResourceId)
            }
            override fun matchesSafely(view: View): Boolean {
                if (view !is ImageView) {
                    return false
                }
                val expectedBitmap = BitmapFactory.decodeResource(
                    view.resources,
                    expectedResourceId
                )
                val imageViewBitmap = (view as ImageView).drawable.toBitmap()
                return imageViewBitmap.sameAs(expectedBitmap)
            }
        }
    }

    private fun withErrorText(expectedError: String?): Matcher<in View> {
        return object : TypeSafeMatcher<Any>() {
            override fun matchesSafely(item: Any): Boolean {
                if (item is TextView) {
                    return item.error?.toString() == expectedError
                }
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("with error text: $expectedError")
            }
        }
    }
}
