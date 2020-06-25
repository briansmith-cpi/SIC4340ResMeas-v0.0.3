/*
 * Copyright (c) 2016 Silicon Craft Technology Co.,Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sic.resmeasure.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.2
 * @since 04/Mar/2016
 */
public class Toaster {

    private volatile static Toaster globalToaster;

    private Toast internalToast;

    /**
     * Private constructor creates a new {@link Toaster} from a given {@link Toast}.
     *
     * @throws NullPointerException if the parameter is null.
     */
    private Toaster(Toast toast) {
        // null check
        if (toast == null) {
            throw new NullPointerException("Boast.Boast(Toast) requires a non-null parameter.");
        }

        internalToast = toast;
    }

    /**
     * Make a standard {@link Toaster} that just contains a text view.
     *
     * @param context  The context to use. Usually your
     *                 {@link android.app.Application} or
     *                 {@link android.app.Activity} object.
     * @param text     The text to show. Can be formatted text.
     * @param duration How long to display the message.
     *                 Either Toast.LENGTH_SHORT or Toast.LENGTH_LONG.
     * @return The toast object completely.
     */
    @SuppressLint("ShowToast")
    private static Toaster makeText(Context context, CharSequence text, int duration) {
        return new Toaster(Toast.makeText(context, text, duration));
    }

    /**
     * Make a standard {@link Toaster} that just contains a text view with the
     * text from a resource.
     *
     * @param context  The context to use. Usually your
     *                 {@link android.app.Application} or
     *                 {@link android.app.Activity} object.
     * @param resId    The resource id of the string resource to use. Can be
     *                 formatted text.
     * @param duration How long to display the message.
     *                 Either Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     * @return The toast object completely.
     * @throws Resources.NotFoundException if the resource can't be found.
     */
    @SuppressLint("ShowToast")
    private static Toaster makeText(Context context, int resId, int duration)
            throws Resources.NotFoundException {
        return new Toaster(Toast.makeText(context, resId, duration));
    }
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void showToast(Context context, String text) {
        makeText(context,
                text,
                Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int resId) {
        makeText(context,
                resId,
                Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String text) {
        makeText(context,
                text,
                Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(Context context, int resId) {
        makeText(context,
                resId,
                Toast.LENGTH_LONG).show();
    }

    /**
     * Close the view if it's showing, or don't show it if it isn't showing yet.
     * You do not normally have to call this. Normally view will disappear on
     * its own after the appropriate duration.
     */
    private void cancel() {
        internalToast = null;
    }

    /**
     * Show the view for the specified duration. By default, this method cancels
     * any current notification to immediately display the new one. For
     * conventional {@link Toast#show()} queueing behaviour, use method
     */
    private void show() {
        // cancel current
        if (globalToaster != null) {
            globalToaster.cancel();
        }

        // save an instance of this current notification
        globalToaster = this;

        internalToast.show();
    }


}