package com.fsck.k9.endtoend;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;

import com.fsck.k9.activity.Accounts;
import com.fsck.k9.endtoend.framework.AccountForTest;
import com.fsck.k9.endtoend.framework.ApplicationState;
import com.fsck.k9.endtoend.pages.AccountsPage;
import com.fsck.k9.endtoend.pages.ComposePage;
import com.fsck.k9.endtoend.pages.FolderPage;
import com.jayway.awaitility.Awaitility;
import com.jayway.awaitility.Duration;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * Tests sending an email. An account must already be set up.
 */
public class A020_SendEmailTest extends AbstractEndToEndTest<Accounts> {

    public A020_SendEmailTest() {
        super(Accounts.class);
    }

    @Test
    public void testSendEmail() throws Exception {
        final AccountForTest accountForTest = ApplicationState.getInstance().accounts.get(0);
        accountForTest.stubMailServer.restart();

        FolderPage folderPage = new AccountsPage().clickOnAccount(accountForTest);
        ComposePage composePage = folderPage.clickCompose();
        composePage.inputTo(accountForTest.name + "@example.com");

        composePage.inputSubject("Test email from " + getClass().getSimpleName() + " at "
                + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));

        composePage.inputMessageContent("Content of email from " + getClass().getSimpleName());

        composePage.send();

        // TODO use Espresso's IdlingResource or something to check for notification.
        Awaitility.waitAtMost(Duration.ONE_MINUTE).until(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                return accountForTest.stubMailServer.getReceivedMessages().size() > 0;
            }
        });
    }


}
