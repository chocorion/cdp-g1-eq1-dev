import {browser, by, element} from 'protractor';

export class AppPage {
    navigateTo(section: string): Promise<unknown> {
        if (section) {
            return browser.get(browser.baseUrl + section) as Promise<unknown>;
        }

        return browser.get(browser.baseUrl) as Promise<unknown>;
    }

    getTitleText(): Promise<string> {
        return element(by.css('app-root .content span')).getText() as Promise<string>;
    }
}
