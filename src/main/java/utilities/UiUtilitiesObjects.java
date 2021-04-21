package utilities;

import base.Base;
import utilities.javaScriptUtils.JavaScriptUtil;
import utilities.uiActions.UiActions;
import utilities.verfications.Verifications;

public class UiUtilitiesObjects extends Base {
    public UiActions uiActions() { return new UiActions(); }
    public Verifications verifications() { return new Verifications(); }
    public JavaScriptUtil jsUtil() { return new JavaScriptUtil(); }
}
