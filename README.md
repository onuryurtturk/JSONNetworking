Android JSONNetworking App
=====================

[ ![Download](https://api.bintray.com/packages/ayltai/maven/Android-Lib-reCAPTCHA/images/download.svg) ](https://bintray.com/ayltai/maven/Android-Lib-reCAPTCHA/_latestVersion)

JSONNetworking is simple library for sending json or string messages to between server and client side.

<img src="https://raw.githubusercontent.com/onuryurtturk/JSONNetworking/tree/master/screenshot.png" width="422" height="714" alt="Screenshot"/>

Quick Start
-----------

**Installation**

<pre>
	repositories {
	jcenter()
}

dependencies {
compile project(':jsonlibrary')

//or add dependency for jar file

compile files('libs/jsonsocket.jar')
}
</pre>

**Sample App Layout**

Basic XML layout for user input.

<pre>

	&lt;RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
		xmlns:tools="http://schemas.android.com/tools"
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		android:paddingBottom="@dimen/activity_vertical_margin"
		android:paddingLeft="@dimen/activity_horizontal_margin"
		android:paddingRight="@dimen/activity_horizontal_margin"
		android:paddingTop="@dimen/activity_vertical_margin"
		tools:context="onur.com.myjsonapp.MainActivity"&gt;


	&lt;EditText
		android:id="@+id/edt_input"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:layout_alignParentTop="true"
		android:layout_centerHorizontal="true"
		android:hint="@string/text_for_server" /&gt;

	&lt;Button
		android:id="@+id/btn_send"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:layout_alignParentLeft="true"
		android:layout_alignParentStart="true"
		android:layout_below="@+id/edt_input"
		android:text="@string/btn_send_server"
		android:textAllCaps="false" /&gt;

	&lt;ScrollView
		android:layout_below="@+id/btn_send"
		android:layout_width="match_parent"
		android:layout_height="fill_parent"
		android:fillViewport="true"&gt;

	&lt;LinearLayout
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		android:layout_gravity="center_vertical"
		android:weightSum="1"&gt;

	&lt;TextView
		android:id="@+id/txt_results"
		android:layout_width="match_parent"
		android:layout_marginTop="15dp"
		android:layout_height="fill_parent" /&gt;

&lt;/LinearLayout&gt;

&lt;/ScrollView&gt;

&lt;/RelativeLayout&gt;

</pre>

It is important to use threads for using library. Because you don't allowed to run these server or client side operations on Main Thread.

You can use Main Thread for ui updates like this

<pre>      
	MainActivity.this.runOnUiThread(new Runnable() {

	@Override
	public void run() {
	txt_results.setText(String.valueOf(txt_results.getText()) + "\n" + m);
}
});
</pre>

**How to start Server**

<pre>

	JSONServer echo_server = new JSONServer();

</pre>


**How to connect Client to Server - Thread Example**

<pre>

	private class SocketServerTestThread extends Thread {

	@Override
	public void run() {
		
		try {

				//create new client
				echo_client = new JSONClient(HOSTNAME, PORT);

				//connect client to server
				echo_server.clientConnect();

				//create basic json packet
				JSONObject object = new JSONObject();
				object.put("message", String.valueOf(edt_user_input.getText()));
				packet = new JSONPacket(object);

				//send packet to server
				echo_client.sendJsonPacket(packet);

			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	}

</pre>

Sample Application
------------------

A complete sample application is available at <a href="https://github.com/onuryurtturk/JSONNetworking/tree/master/app">https://github.com/onuryurtturk/JSONNetworking/tree/master/app</a>.