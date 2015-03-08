package com.fsck.k9.endtoend;

import com.fsck.k9.activity.Accounts;
import com.fsck.k9.endtoend.framework.AccountForTest;
import com.fsck.k9.endtoend.framework.ApplicationState;
import com.fsck.k9.endtoend.pages.AccountsPage;
import com.fsck.k9.endtoend.pages.ComposePage;
import com.fsck.k9.endtoend.pages.FolderPage;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Tests sending an email. An account must already be set up.
 */
public class A020_SendEmailTest extends AbstractEndToEndTest<Accounts> {

    public A020_SendEmailTest() {
        super(Accounts.class);
    }

    @Test
    public void testCreateAccountDirectly() throws Exception {
        AccountForTest accountForTest = ApplicationState.getInstance().accounts.get(0);
        FolderPage folderPage = new AccountsPage().clickOnAccount(accountForTest);
        ComposePage composePage = folderPage.clickCompose();
        composePage.inputTo(accountForTest.name + "@example.com");

        composePage.inputSubject("Test email from " + getClass().getSimpleName() + " at "
                + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));

        composePage.inputMessageContent("Content of email from " + getClass().getSimpleName());

        composePage.send();

        assertEquals(1, ApplicationState.getInstance().stubMailServer.getReceivedMessages().size());
    }


}
