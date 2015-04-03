

package mk.ea.ir.blazercorpscentral;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.OpenFileActivityBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mk.ea.ir.blazercorpscentral.BaseDemoActivity;

/**
 * An activity to illustrate how to pick a file with the
 * opener intent.
 */
public class AnnouncementsActivity extends BaseDemoActivity {

    private static final String TAG = "PickFileWithOpenerActiv";

    private static final int REQUEST_CODE_OPENER = 1;

    private DriveId mSelectedFileDriveId;
    private String mSelectedFileName;
    private String mSelectedFileExtension;
    private File mFilePATH;


    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);

        if (mSelectedFileDriveId != null) {
            DriveFile.DownloadProgressListener listener = new DriveFile.DownloadProgressListener() {
                @Override
                public void onProgress(long bytesDownloaded, long bytesExpected) {
                    // Update progress dialog with the latest progress.
                    int progress = (int) (bytesDownloaded * 100 / bytesExpected);
                    Log.d(TAG, String.format("Loading progress: %d percent", progress));
                }
            };

            //Get the name of the file selected
            Drive.DriveApi.getFile(getGoogleApiClient(), mSelectedFileDriveId).getMetadata(getGoogleApiClient()).setResultCallback(metadataRetrievedCallback);
            //Download any try to open the file in some installed reader app
            Drive.DriveApi.getFile(getGoogleApiClient(), mSelectedFileDriveId)
                    .open(getGoogleApiClient(), DriveFile.MODE_READ_ONLY, listener)
                    .setResultCallback(driveContentsCallback);
            mSelectedFileDriveId = null;

            return;
        }


        IntentSender intentSender = Drive.DriveApi
                .newOpenFileActivityBuilder()
                .setMimeType(new String[] { "text/plain", "text/html", "application/msword", "application/pdf","image/jpeg","image/png","image/bmp","application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","application/vnd.openxmlformats-officedocument.wordprocessingml.document","application/vnd.openxmlformats-officedocument.presentationml.presentation"})
                .setActivityStartFolder(DriveId.decodeFromString("DriveId:CAESSDBCMEJQZ1lXN29veDBma3RQWkVwdFowNWthVTgyU3pSSGFVUktRMGhhTkdGWFprMDFPRlU0Y21WTVFrY3lSMFJzZG1WMVRHOBiOAyDE7ta50lIoAQ=="))
                .build(getGoogleApiClient());
        // DriveFile file = Drive.DriveApi.getFile(getGoogleApiClient(), DriveId.decodeFromString("DriveId:CAESLDFuTEttWDQxOXh1RVctbnZJN0ljX215VWFoT25QWUtMNjQ1S1lyUjU0MEpJGJADIMTu1rnSUigA"));


        try {
            startIntentSenderForResult(
                    intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0);
        } catch (SendIntentException e) {
            Log.w(TAG, "Unable to send intent", e);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.w(TAG, "right before crash" + requestCode);
        Log.w(TAG, "right before crash" + resultCode);
        Log.w(TAG, "right before crash" + data);


        switch (requestCode) {

            case REQUEST_CODE_OPENER:
                if (resultCode == RESULT_OK && data != null) {
                    Log.w(TAG, "right before crash" + data);
                    DriveId driveId = (DriveId) data.getParcelableExtra(
                            OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
                    Log.w(TAG, "" + driveId);

                    mSelectedFileDriveId = driveId;
                }
                if (resultCode == 0 && data == null)
                    finish();
                break;
            default:
                Log.w(TAG, "Default case" + data);
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }

    private ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback =
            new ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(DriveApi.DriveContentsResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while opening the file contents");
                        return;
                    }
                    DriveContents contents = result.getDriveContents();



                    //if there is no SD card, create new directory objects to make directory on device
                    if (Environment.getExternalStorageState() == null) {
                        //create new file directory object

                        mFilePATH = new File(Environment.getDataDirectory()
                                + "/Blazer Corps Central/");
                        if ( mFilePATH.exists()) {
                            File[] dirFiles =  mFilePATH.listFiles();
                            if (dirFiles.length != 0) {
                                for (int ii = 0; ii <= dirFiles.length; ii++) {
                                    dirFiles[ii].delete();
                                }
                            }
                        }
                        // if no directory exists, create new directory
                        if (! mFilePATH.exists()) {
                            mFilePATH.mkdir();
                        }

                        // if phone DOES have sd card
                    } else if (Environment.getExternalStorageState() != null) {
                        // search for directory on SD card

                        mFilePATH = new File(
                                Environment.getExternalStorageDirectory()
                                        + "/Blazer Corps Central/");
                        if ( mFilePATH.exists()) {
                            File[] dirFiles =  mFilePATH.listFiles();
                            if (dirFiles.length > 0) {
                                for (int ii = 0; ii < dirFiles.length; ii++) {
                                    dirFiles[ii].delete();
                                }
                                dirFiles = null;
                            }
                        }
                        // if no directory exists, create new directory to store test
                        // results
                        if (! mFilePATH.exists()) {
                            mFilePATH.mkdir();
                        }
                    }// end of SD card checking

                    String filePath =  mFilePATH.getPath() + "/" + mSelectedFileName;
                    showMessage("File Successfully Downloaded!: " + mSelectedFileExtension);

                    File file = new File(filePath);
                    Log.w(TAG, "File Location " + file.getAbsolutePath());
                    Log.w(TAG, "File Extension " + mSelectedFileExtension);


                    Log.w(TAG, "File Name " + file.getName());

                    InputStream is = contents.getInputStream();
                    BufferedInputStream input = new BufferedInputStream(is);
                    try {
                        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file), 8192);
                        byte buffer[] = new byte[1024];
                        int length;
                        while (input.available() != 0) {
                            output.write(input.read());
                        }
                        output.flush();

                        is.close();
                        output.close();

                        Uri path = Uri.fromFile(file);
                        Log.w(TAG, "File MIME " + MimeTypeMap.getSingleton().getMimeTypeFromExtension(mSelectedFileExtension));
                        Intent openFileIntent = new Intent(Intent.ACTION_VIEW);
                        openFileIntent.setDataAndType(path, MimeTypeMap.getSingleton().getMimeTypeFromExtension(mSelectedFileExtension));
                        openFileIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        try {
                            startActivity(openFileIntent);
                        } catch (ActivityNotFoundException e) {
                            showMessage("No application available to this file. Please download 3rd party viewer app from Play Store.");
                        }

                        Log.w(TAG, "File Name" + file.length());

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                        finish();
                    }
                }
            };

    ResultCallback<DriveResource.MetadataResult> metadataRetrievedCallback = new
            ResultCallback<DriveResource.MetadataResult>() {
                @Override
                public void onResult(DriveResource.MetadataResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Problem while trying to fetch file information. Make sure file exists.");
                        return;
                    }
                    Metadata metadata = result.getMetadata();
                    mSelectedFileName = metadata.getTitle();
                    mSelectedFileExtension = metadata.getFileExtension();
                }
            };
}