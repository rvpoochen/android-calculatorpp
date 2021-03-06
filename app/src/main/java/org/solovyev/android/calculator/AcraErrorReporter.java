/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.android.calculator;

import android.util.Log;
import jscl.CustomFunctionCalculationException;
import jscl.text.msg.JsclMessage;
import jscl.text.msg.Messages;
import org.acra.ACRA;
import org.solovyev.common.msg.MessageType;

import javax.annotation.Nonnull;

public class AcraErrorReporter implements ErrorReporter {

    public static final boolean ENABLED = !BuildConfig.DEBUG;

    @Override
    public void onException(@Nonnull Throwable e) {
        if (!ENABLED) {
            Log.e("Acra", e.getMessage(), e);
            return;
        }
        if (e instanceof CustomFunctionCalculationException) {
            final CustomFunctionCalculationException e1 = (CustomFunctionCalculationException) e;
            e1.setMessage(new JsclMessage(Messages.msg_19, MessageType.error, "XXX", e1.getCauseMessage().getMessageCode()));
        }
        ACRA.getErrorReporter().reportBuilder().forceSilent().exception(e).send();
    }

    @Override
    public void onError(@Nonnull String message) {
        if (!ENABLED) {
            Log.e("Acra", message);
            return;
        }
        ACRA.getErrorReporter().reportBuilder().forceSilent().exception(new Throwable(message)).send();
    }
}
