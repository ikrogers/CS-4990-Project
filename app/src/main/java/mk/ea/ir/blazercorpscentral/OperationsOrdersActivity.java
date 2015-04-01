/**
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mk.ea.ir.blazercorpscentral;

import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;

import mk.ea.ir.blazercorpscentral.BaseDemoActivity;

/**
 * An activity to illustrate how to pick a file with the
 * opener intent.
 */
public class OperationsOrdersActivity extends BaseDemoActivity {

    private static final String TAG = "PickFileWithOpenerActiv";

    private static final int REQUEST_CODE_OPENER = 1;

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        IntentSender intentSender = Drive.DriveApi
                .newOpenFileActivityBuilder()
                .setActivityStartFolder(DriveId.decodeFromString("DriveId:CAESSDBCMEJQZ1lXN29veDBma3RQWkVwdFowNWthVTgyU3pSSGFVUktRMGhhTkdGWFprMDFPRlU0Y21WTVFrY3lSMFJzZG1WMVRHOBiOAyDE7ta50lIoAQ=="))
                .build(getGoogleApiClient());
        try {
            startIntentSenderForResult(
                    intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0);
        } catch (SendIntentException e) {
          Log.w(TAG, "Unable to send intent", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.w(TAG, "right before crash"+requestCode);
        Log.w(TAG, "right before crash"+resultCode);
        Log.w(TAG, "right before crash"+data);


        switch(requestCode) {

            case REQUEST_CODE_OPENER:
                if (resultCode == RESULT_OK && data != null) {
                    Log.w(TAG, "right before crash"+data);
                    DriveId driveId = (DriveId) data.getParcelableExtra(
                            OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
                    Log.w(TAG, ""+driveId);
                    showMessage("Selected folder's ID: " + driveId);
                    finish();
                }
                if (resultCode == 0 && data == null)
                    finish();

                break;
            default:
                Log.w(TAG, "Default case"+data);
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

}
