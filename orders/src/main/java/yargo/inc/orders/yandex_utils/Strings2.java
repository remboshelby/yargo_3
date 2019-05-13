/*
 * The MIT License (MIT)
 * Copyright © 2018 NBCO Yandex.Money LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the “Software”), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package yargo.inc.orders.yandex_utils;

import androidx.annotation.NonNull;

public final class Strings2 {

    private Strings2() {
    }

    static int getCursorPositionAfterFormat(
            @NonNull final String formattedString,
            @NonNull final String originalString,
            final int originalPosition
    ) {
        int digitsCounter = 0;
        for (int i = originalString.length() - 1; i >= originalPosition; i--) {
            if (Character.isDigit(originalString.charAt(i))) {
                digitsCounter++;
            }
        }

        int positionToSet = formattedString.length();
        int digitsCounterAfterFormat = 0;
        while (digitsCounter > digitsCounterAfterFormat && positionToSet > 0) {
            if (Character.isDigit(formattedString.charAt(--positionToSet))) {
                digitsCounterAfterFormat++;
            }
        }
        return positionToSet;
    }

    static int getFirstDigitIndex(@NonNull CharSequence sequence) {
        int sequenceLength = sequence.length();
        for (int i = 0; i < sequenceLength; i++) {
            if (Character.isDigit(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }
}
