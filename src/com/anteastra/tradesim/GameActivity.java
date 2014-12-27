package com.anteastra.tradesim;

import java.util.Timer;
import java.util.TimerTask;

import com.anteastra.tradesim.activitylisteners.OnMoneyChangeListener;
import com.anteastra.tradesim.gui.ControlFragment;
import com.anteastra.tradesim.gui.TimeControlFragment;
import com.anteastra.tradesim.gui.GraphFragment;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;



public class GameActivity extends ActionBarActivity implements OnMoneyChangeListener {
	
	
	private Timer mainTimer;
	private TimerTask mainTimerTask;	
	private GameStateSingleton state;
		
	private UpdateUIThread myUpdateUIThread;	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        
        state = GameStateSingleton.getInstance();
    	mainTimer = new Timer();
    	mainTimerTask = new MainTimerTask();
        
        if (savedInstanceState == null) {        	
            myUpdateUIThread = new UpdateUIThread();
        }
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	GameStateSingleton.setContext(this);    	
		mainTimer.schedule(mainTimerTask, state.MAIN_TIMER_DURATION, state.MAIN_TIMER_DURATION);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
       
    class UpdateUIThread implements Runnable{

		@Override
		public void run() {
			GraphFragment fragment = (GraphFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentGraph);
			if (fragment != null) fragment.updateViews();
			
			TimeControlFragment fragmentTime = (TimeControlFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTimeControl);
			if (fragmentTime != null) fragmentTime.updateViews();
			
			ControlFragment fragmentControl = (ControlFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentControl);
			if (fragmentControl != null) fragmentControl.updateViews();
		}
		
	}
	
	class MainTimerTask extends TimerTask{

		@Override
		public void run() {
			state.dayTimeLeft -= state.gameSpeed;
			if (state.dayTimeLeft<=0){
				state.nextDay();
				runOnUiThread(myUpdateUIThread);
			}	
		}
		
	}

	@Override
	public void onMoneyChange() {
		GraphFragment fragment = (GraphFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentGraph);
		if (fragment != null) fragment.updateViews();
	}	
	
}
