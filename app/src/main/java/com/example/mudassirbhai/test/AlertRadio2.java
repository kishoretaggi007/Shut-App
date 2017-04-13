package com.example.mudassirbhai.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
 
public class AlertRadio2  extends DialogFragment{
 
    /** Declaring the interface, to invoke a callback function in the implementing activity class */
    AlertPositiveListener alertPositiveListener;
 
    /** An interface to be implemented in the hosting activity for "OK" button click listener */
    interface AlertPositiveListener {
        public void Click();
    }
 
    /** This is a callback method executed when this fragment is attached to an activity.
    *  This function ensures that, the hosting activity implements the interface AlertPositiveListener
    * */
    @Override
	public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        try{
            alertPositiveListener = (AlertPositiveListener) activity;
        }catch(ClassCastException e){
            // The hosting activity does not implemented the interface AlertPositiveListener
            throw new ClassCastException(activity.toString() + " must implement AlertPositiveListener");
        }
    }
 
    /** This is the OK button listener for the alert dialog,
     *  which in turn invokes the method onPositiveClick(position)
     *  of the hosting activity which is supposed to implement it
    */
    
 
    /** This is a callback method which will be executed
     *  on creating this fragment
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
 
        /** Getting the arguments passed to this fragment */
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");
 
        /** Creating a builder for the alert dialog window */
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
 
        /** Setting a title for the window */
        b.setTitle("Do you want to un-block this application??");
        
        
 
        /** Setting a positive button and its listener */
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
    
            	alertPositiveListener.Click();
     
            }
        });
 
        /** Setting a positive button and its listener */
        b.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
 
        /** Creating the alert dialog window using the builder class */
        AlertDialog d = b.create();
 
        /** Return the alert dialog window */
        return d;
    }
}


