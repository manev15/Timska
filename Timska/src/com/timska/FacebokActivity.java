package com.timska;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import timska.HomeFragment;
import timska.MainActivity;
import timska.Singleton;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook.DialogListener;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
@SuppressWarnings("deprecation")
public class FacebokActivity extends Activity {
	// Your Facebook APP ID
		private static String APP_ID = "370686846465116"; // Replace with your App ID
		// Instance of Facebook Class
				private Facebook facebook = new Facebook(APP_ID);
				private AsyncFacebookRunner mAsyncRunner;
				String FILENAME = "AndroidSSO_data";
				private SharedPreferences mPrefs;

				// Buttons
				Button btnFbLogin;
				Button btnFbGetProfile;
				Button btnPostToWall;
				Button btnShowAccessTokens;
				final Context cont=this;

				@Override
				public void onCreate(Bundle savedInstanceState) {
					super.onCreate(savedInstanceState);
					setContentView(R.layout.activity_facebok);

					btnFbLogin = (Button) findViewById(R.id.fb_login);
					btnFbGetProfile = (Button) findViewById(R.id.btn_get_profile);
					btnPostToWall = (Button) findViewById(R.id.btn_fb_post_to_wall);
					btnShowAccessTokens = (Button) findViewById(R.id.btn_show_access_tokens);
					mAsyncRunner = new AsyncFacebookRunner(facebook);

					int a = Singleton.getInstance().br;
					if(a!=0)
					{
						
						// Making get profile button visible
						btnFbGetProfile.setVisibility(View.VISIBLE);

						// Making post to wall visible
						btnPostToWall.setVisibility(View.VISIBLE);

						// Making show access tokens button visible
						btnShowAccessTokens.setVisibility(View.VISIBLE);
					}
					
					loginToFacebook();
					
					/**
					 * Login button Click event
					 * */
		/*			btnFbLogin.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							Log.d("Image Button", "button Clicked");
							loginToFacebook();
						}
					});
		*/
					/**
					 * Getting facebook Profile info
					 * */
					btnFbGetProfile.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							getProfileInformation();
							finish();
						}
					});

					/**
					 * Posting to Facebook Wall
					 * */
					btnPostToWall.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							postToWall();
						}
					});

					/**
					 * Showing Access Tokens
					 * */
					btnShowAccessTokens.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
					 	logoutFromFacebook();
						}
					});

				}

				/**
				 * Function to login into facebook
				 * */
				@SuppressWarnings("deprecation")
				public void loginToFacebook() {

					mPrefs = getPreferences(MODE_PRIVATE);
					String access_token = mPrefs.getString("access_token", null);
					long expires = mPrefs.getLong("access_expires", 0);

					if (access_token != null) {
						facebook.setAccessToken(access_token);
						
						btnFbLogin.setVisibility(View.INVISIBLE);
						
						// Making get profile button visible
						btnFbGetProfile.setVisibility(View.VISIBLE);

						// Making post to wall visible
						btnPostToWall.setVisibility(View.VISIBLE);

						// Making show access tokens button visible
						btnShowAccessTokens.setVisibility(View.VISIBLE);

						Log.d("FB Sessions", "" + facebook.isSessionValid());
					}

					if (expires != 0) {
						facebook.setAccessExpires(expires);
					}

					if (!facebook.isSessionValid()) {
						facebook.authorize(this,
								new String[] { "publish_actions","email"},
								new DialogListener() {

									@Override
									public void onCancel() {
										// Function to handle cancel event
									}

									@Override
									public void onComplete(Bundle values) {
										// Function to handle complete event
										// Edit Preferences and update facebook acess_token
									
										
										SharedPreferences.Editor editor = mPrefs.edit();
										editor.putString("access_token",
												facebook.getAccessToken());
										editor.putLong("access_expires",
												facebook.getAccessExpires());
										editor.commit();

										// Making Login button invisible
										btnFbLogin.setVisibility(View.INVISIBLE);

										// Making logout Button visible
										btnFbGetProfile.setVisibility(View.VISIBLE);

										// Making post to wall visible
										btnPostToWall.setVisibility(View.VISIBLE);

										// Making show access tokens button visible
										btnShowAccessTokens.setVisibility(View.VISIBLE);
									}

									@Override
									public void onError(DialogError error) {
										// Function to handle error

									}

									@Override
									public void onFacebookError(FacebookError fberror) {
										// Function to handle Facebook errors

									}

								});
					}
				}

				@Override
				public void onActivityResult(int requestCode, int resultCode, Intent data) {
					super.onActivityResult(requestCode, resultCode, data);
					facebook.authorizeCallback(requestCode, resultCode, data);
				}


				/**
				 * Get Profile information by making request to Facebook Graph API
				 * */
				@SuppressWarnings("deprecation")
				public void getProfileInformation() {
					mAsyncRunner.request("me", new RequestListener() {
						@Override
						public void onComplete(String response, Object state) {
							Log.d("Profile", response);
							String json = response;
							try {
								// Facebook Profile JSON data
								JSONObject profile = new JSONObject(json);
								
								// getting name of the user
								final String name = profile.getString("name");
								Log.d("aa",name);
								
								
								Intent intent = new Intent(FacebokActivity.this, MainActivity.class);
								intent.putExtra("name", name);
								Singleton.getInstance().br++;
								Singleton.getInstance().ime=name;
					  	   	    PreferenceManager.getDefaultSharedPreferences(cont).edit().putString("name", Singleton.getInstance().ime).commit();
								Log.d("Aj da vidimeee", Singleton.getInstance().ime);
								startActivity(intent);
							//	finish();
							
							
								
								// getting email of the user
								final String email = profile.getString("email");
								
								Log.e("ACEEEEE", email);
								Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
								
								runOnUiThread(new Runnable() {

									@Override
									public void run() {
										
										Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
									}

								});

								
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onIOException(IOException e, Object state) {
						}

						@Override
						public void onFileNotFoundException(FileNotFoundException e,
								Object state) {
						}

						@Override
						public void onMalformedURLException(MalformedURLException e,
								Object state) {
						}

						@Override
						public void onFacebookError(FacebookError e, Object state) {
						}
					});
				
				}

				/**
				 * Function to post to facebook wall
				 * */
				@SuppressWarnings("deprecation")
				public void postToWall() {
					// post on user's wall.
					facebook.dialog(this, "feed", new DialogListener() {

						@Override
						public void onFacebookError(FacebookError e) {
						}

						@Override
						public void onError(DialogError e) {
						}

						@Override
						public void onComplete(Bundle values) {
						}

						@Override
						public void onCancel() {
						}
					});

				}

				/**
				 * Function to show Access Tokens
				 * */
				public void showAccessTokens() {
					String access_token = facebook.getAccessToken();

					Toast.makeText(getApplicationContext(),
							"Access Token: " + access_token, Toast.LENGTH_LONG).show();
				}
				
				/**
				 * Function to Logout user from Facebook
				 * */
				public void logoutFromFacebook() {
					mAsyncRunner.logout(this, new RequestListener() {
						@Override
						public void onComplete(String response, Object state) {
							Log.d("Logout from Facebook", response);
							if (Boolean.parseBoolean(response) == true) {
								runOnUiThread(new Runnable() {

									@Override
									public void run() {
										// make Login button visible
										btnFbLogin.setVisibility(View.VISIBLE);

										// making all remaining buttons invisible
										btnFbGetProfile.setVisibility(View.INVISIBLE);
										btnPostToWall.setVisibility(View.INVISIBLE);
										btnShowAccessTokens.setVisibility(View.INVISIBLE);
									}

								});

							}
						}

						@Override
						public void onIOException(IOException e, Object state) {
						}

						@Override
						public void onFileNotFoundException(FileNotFoundException e,
								Object state) {
						}

						@Override
						public void onMalformedURLException(MalformedURLException e,
								Object state) {
						}

						@Override
						public void onFacebookError(FacebookError e, Object state) {
						}
					});
				}
				public void finish() {
					
				//	this.finish();
				}
		}
