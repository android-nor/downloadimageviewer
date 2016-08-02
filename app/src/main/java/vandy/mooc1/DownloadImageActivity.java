/*
If you want a circular progress bar remove comment at setContentView(R.layout.progress_bar) in onCreate
and put this line below at a new file res/layout/progress_bar.xml
Thanks ;)
Norman


*progress_bar.xml file
*
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.skholingua.android.progressbar_circular.MainActivity" >

    <ProgressBar
        android:id="@+id/progressBar1"
        style="?android:attr/progressBarStyleLarge"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:max="100"
        android:progress="1"
        android:layout_marginTop="104dp" />

    <TextView
        android:id="@+id/textView1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignBottom="@+id/progressBar1"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="28dp"
        android:text="downloading"
        android:textSize="10dp" />

</RelativeLayout>



* */


package vandy.mooc1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * An Activity that downloads an image, stores it in a local file on
 * the local device, and returns a Uri to the image file.
 */
public class DownloadImageActivity extends Activity {
    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG = getClass().getSimpleName();

    Uri url;

    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., UI layout and
     * some class scope variable initialization.
     *
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Always call super class for necessary
        // initialization/implementation.
        // @@ TODO -- you fill in here.
        super.onCreate(savedInstanceState);

        //Added a circular progress bar like in the screencast at link: https://www.youtube.com/watch?v=V8ZXMPziHmU
        //setContentView(R.layout.progress_bar);

        // Get the URL associated with the Intent data.
        // @@ TODO -- you fill in here.
        url = getIntent().getData();

        // Download the image in the background, create an Intent that
        // contains the path to the image file, and set this as the
        // result of the Activity.

        // @@ TODO -- you fill in here using the Android "HaMeR"
        // concurrency framework.  Note that the finish() method
        // should be called in the UI thread, whereas the other
        // methods should be called in the background thread.

        //First working version

        new Thread(new Runnable() {
            @Override
            public void run() {

//                DownloadImageActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        Toast.makeText(getApplicationContext(),
//                                       "Inizio download",
//                                       Toast.LENGTH_SHORT).show();
//                    }
//                });

                Intent resultIntent = new Intent();
                //Intent resultIntent = getIntent();


                Uri urlDownloadImage = DownloadUtils.downloadImage(getApplicationContext(), url);

                if (urlDownloadImage != null){
                    resultIntent.putExtra(MainActivity.USER_DATA_EXTRA, urlDownloadImage);
                    setResult(RESULT_OK, resultIntent);
                } else {
                    setResult(RESULT_CANCELED, resultIntent);
                }

                DownloadImageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

//                        Toast.makeText(getApplicationContext(),
//                                "Return in UI Thread",
//                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        }).start();


        /**
         *Second version
         * */
        //   new Thread(new DownloadImageByUrlTask(url)).start();

    }

/*Second version
    private final Handler handler = new Handler();


    class DownloadImageByUrlTask implements Runnable{

        Uri url;
        public DownloadImageByUrlTask(Uri uri){
            this.url = uri;
        }

        @Override
        public void run() {

            //Long-running operation
            Intent resultIntent = new Intent();
            Uri urlDownloadImage = DownloadUtils.downloadImage(getApplicationContext(), url);

            if (urlDownloadImage != null){
                resultIntent.putExtra(MainActivity.USER_DATA_EXTRA, urlDownloadImage);
                setResult(RESULT_OK, resultIntent);
            } else {
                setResult(RESULT_CANCELED, resultIntent);
            }


            //Running in UIThread
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Return in UI Thread",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }
    }
*/


}
