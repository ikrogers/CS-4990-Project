

package mk.ea.ir.blazercorpscentral;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


/**
 * An activity to list all available demo activities.
 */
public class HomeActivity extends Activity {


    private Button mOPORDbtn;
    private Button mAnnouncementsBtn;
    private Button mATOMMS;
    private Button mShuttleTracker;
    private Button mAFI;
    private Button mSrc;
    private Button mPFA;
    private Button mOPORDbtn6;
    private Button mOPORDbtn7;
    private Button debug;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String[] titles = getResources().getStringArray(R.array.titles_array);
        mOPORDbtn = (Button) findViewById(R.id.OPORD_btn);
        mAnnouncementsBtn = (Button) findViewById(R.id.announcements_btn);
        mATOMMS = (Button) findViewById(R.id.ATOMMSbtn);
        mShuttleTracker = (Button) findViewById(R.id.shuttleTracker);
        mSrc = (Button) findViewById(R.id.rec);
         mAFI = (Button) findViewById(R.id.AFIbtn);
        mPFA = (Button) findViewById(R.id.pfa);


        mOPORDbtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), OperationsOrdersActivity.class);
                startActivity(intent);
            }
        });

        mPFA.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PFAcalcActivity.class);
                startActivity(intent);
            }
        });

        mAnnouncementsBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AnnouncementsActivity.class);
                startActivity(intent);
            }
        });

        mATOMMS.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ATOMMSActivity.class);
                startActivity(intent);
            }
        });

        mShuttleTracker.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ShuttleTrackerActiviy.class);
                startActivity(intent);
            }
        });

        mSrc.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SRCActivity.class);
                startActivity(intent);
            }
        });
        mAFI.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AFIActivity.class);
                startActivity(intent);
            }
        });







       //debug.setOnClickListener(new View.OnClickListener() {
          //  @Override
          //  public void onClick(View v) {
           //     Intent intent = new Intent(getBaseContext(), PickFolderWithOpenerActivity.class);
            //    startActivity(intent);
           // }
      //  });

    }

}
