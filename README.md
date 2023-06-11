# WhatsApp-Clone-Firebase-Android
# Weather-App-Android

<div>
<img src="https://github.com/PratyayMallik1006/WhatsApp-Clone-Firebase-Android/blob/main/Screenshots/whatsapp1.jfif?raw=true" height="600" style="float: left">
<img src="https://github.com/PratyayMallik1006/WhatsApp-Clone-Firebase-Android/blob/main/Screenshots/whatsapp2.jfif?raw=true" height="600" style="float: left">
</div>

<div>
  <img src="https://github.com/PratyayMallik1006/WhatsApp-Clone-Firebase-Android/blob/main/Screenshots/whatsapp3.jfif?raw=true" height="600" style="float: left">
  <img src="https://github.com/PratyayMallik1006/WhatsApp-Clone-Firebase-Android/blob/main/Screenshots/whatsapp4.jfif?raw=true" height="600" style="float: left">
</div>

<div>
  <img src="https://github.com/PratyayMallik1006/WhatsApp-Clone-Firebase-Android/blob/main/Screenshots/whatsapp5.jfif?raw=true" height="600" style="float: left">
  <img src="https://github.com/PratyayMallik1006/WhatsApp-Clone-Firebase-Android/blob/main/Screenshots/whatsapp6.jfif?raw=true" height="600" style="float: left">
</div>
<img src="https://github.com/PratyayMallik1006/WhatsApp-Clone-Firebase-Android/blob/main/Screenshots/Fb1.PNG?raw=true">
  <img src="https://github.com/PratyayMallik1006/WhatsApp-Clone-Firebase-Android/blob/main/Screenshots/Fb2.PNG?raw=true">

## Notes
# Setup
1. gradle.properties
```XML
android.useAndroidX=true
android.nonTransitiveRClass=true
android.enableJetifier=true
```
2. build.gradle(gradle)
```xml
buildscript {
    repositories {
        // Make sure that you have the following two repositories
        google()  // Google's Maven repository

        mavenCentral()  // Maven Central repository

    }
    dependencies {
        // Add the dependency for the Google services Gradle plugin
        classpath 'com.google.gms:google-services:4.3.15'

    }
}
plugins {
    id 'com.android.application' version '8.0.2' apply false
    id 'com.android.library' version '8.0.2' apply false
}
```
3. build.gradle(app)
```xml
plugins {
    id 'com.android.application'
    id 'com.google.gms.google-services'
}

android {
    namespace 'com.pratyay.whatsapp'
    compileSdk 33

    defaultConfig {
        applicationId "com.pratyay.whatsapp"
        minSdk 24
        targetSdk 33
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.android.material:material:1.0.0'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'com.google.firebase:firebase-auth:17.0.0'
    implementation 'com.google.firebase:firebase-core:16.0.3'
    implementation 'com.google.android.gms:play-services-auth:16.0.0'
    implementation 'com.github.bumptech.glide:glide:3.7.0'
    implementation 'com.google.firebase:firebase-database:19.7.0'
    implementation 'com.google.firebase:firebase-messaging:18.0.0'
    implementation 'com.google.firebase:firebase-storage:15.0.0'
    implementation 'com.google.firebase:firebase-appindexing:15.0.0'
}

```
# Saving Data Locally
1. Storing
```java
private void saveDisplayName(){  
	String displayName = mUsernameView.getText().toString();  
	SharedPreferences prefs = getSharedPreferences(CHAT_PREFS,0);  
	prefs.edit().putString(DISPLAY_NAME_KEY,displayName).apply();  
}
```
2. Retriving
```java
private void setupDisplayName(){  
	SharedPreferences prefs=getSharedPreferences(RegisterActivity.CHAT_PREFS,MODE_PRIVATE);
	mDisplayName=prefs.getString(RegisterActivity.DISPLAY_NAME_KEY,null);  
	  
	if(mDisplayName == null) mDisplayName = "Anonymous";  
}
```
# Firebase
## Register user
```java
private FirebaseAuth mAuth;
mAuth = FirebaseAuth.getInstance();

String email = mEmailView.getText().toString();  
String password = mPasswordView.getText().toString();  
mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {  
    @Override  
  public void onComplete(@NonNull Task<AuthResult> task) {  
        Log.d("WhatsApp","createUser onComplete"+task.isSuccessful());  
  
 if(!task.isSuccessful()){  
            Log.d("WhatsApp","user creation failed");  
  }  
    }  
});
```
## Login User
```java
private FirebaseAuth mAuth;
mAuth = FirebaseAuth.getInstance();

private void attemptLogin() {  
  
    String email = mEmailView.getText().toString();  
  String password = mPasswordView.getText().toString();  
  
 if(email.equals("") || password.equals("")) return;  
  Toast.makeText(this, "Login in progress...",Toast.LENGTH_SHORT).show();  
  
  // TODO: Use FirebaseAuth to sign in with email & password  
  mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {  
        @Override  
  public void onComplete(@NonNull Task<AuthResult> task) {  
  
            Log.d("WhatsApp","signInWithEmail() onComplete: "+task.getException());  
  
 if(!task.isSuccessful()){  
                Log.d("WhatsApp","Problem signing in:"+task.getException());  
  showErrorDialog("There was a problem signing in");  
  } else {  
                Intent intet=new Intent(LoginActivity.this,MainChatActivity.class);  
  startActivity(intet);  
  }  
  
        }  
    });  
  
  
  
}
```
## Storing and retrieving from database
1. InstanceMessage.java
```java

public class InstanceMessage {  
	private String message;  
	private String author;  
	  
	public InstanceMessage(String message, String author) {  
		this.message = message;  
		this.author = author;  
	}  
	  
	public InstanceMessage() {  
	}  
	  
	public String getMessage() {  
		return message;  
	}  
	  
	public String getAuthor() {  
		return author;  
	}
}
```
2. ChatActivity.java
```java
private DatabaseReference mDatabaseReference;
mDatabaseReference = FirebaseDatabase.getInstance().getReference();

private void sendMessage() {  
	String input = mInputText.getText().toString();  
	if(!input.equals("")){  
		InstanceMessage chat = new InstanceMessage(input,mDisplayName);  
		mDatabaseReference.child("messages").push().setValue(chat);  
		mInputText.setText("");    
	}   
}
```
3. Firebase>project> Realtime database> Rules:
```json
{
  "rules": {
    ".read": true,
    ".write": true
  }
}
```
## Retrieving and retrieving from database
1. chatListAdapter.java
```java

public class ChatListAdapter extends BaseAdapter {  
  
private Activity mActivity;  
private DatabaseReference mDatabaseReference;  
private String mDisplayName;  
private ArrayList<DataSnapshot> mSnapshotList;  
  
private ChildEventListener mListener = new ChildEventListener() {  
@Override  
public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {  
mSnapshotList.add(snapshot);  
notifyDataSetChanged();  
}  
  
@Override  
public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {  
  
}  
  
@Override  
public void onChildRemoved(@NonNull DataSnapshot snapshot) {  
  
}  
  
@Override  
public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {  
  
}  
  
@Override  
public void onCancelled(@NonNull DatabaseError error) {  
  
}  
};  
  
public ChatListAdapter(Activity activity, DatabaseReference ref, String name){  
	mActivity=activity;  
	mDisplayName=name;  
	mDatabaseReference=ref.child("messages");  
	mDatabaseReference.addChildEventListener(mListener);  
	  
	mSnapshotList = new ArrayList<>();  
	  
  
}  
  
static class ViewHolder{  
	TextView authorName;  
	TextView body;  
	ViewGroup.LayoutParams params;  
}  
  
  
@Override  
public int getCount() {  
	return mSnapshotList.size();  
}  
  
@Override  
public InstanceMessage getItem(int position) {  
	DataSnapshot snapshot = mSnapshotList.get(position);  
	return snapshot.getValue(InstanceMessage.class);  
}  
  
@Override  
public long getItemId(int position) {  
	return 0;  
}  
  
@Override  
public View getView(int position, View convertView, ViewGroup parent) {  
if(convertView == null){  
	LayoutInflater inflater=(LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	convertView = inflater.inflate(R.layout.chat_msg_row,parent,false);  
	final ViewHolder holder=new ViewHolder();  
	holder.authorName=(TextView) convertView.findViewById(R.id.author);  
	holder.body=(TextView) convertView.findViewById(R.id.message);  
	holder.params=(LinearLayout.LayoutParams) holder.authorName.getLayoutParams();  
	convertView.setTag(holder);  
  
}  
final InstanceMessage message=getItem(position);  
final ViewHolder holder=(ViewHolder) convertView.getTag();  
  
String author = message.getAuthor();  
holder.authorName.setText(author);  
  
String msg = message.getMessage();  
holder.body.setText(msg);  
  
return convertView;  
}  
  
public void cleanup(){  
mDatabaseReference.removeEventListener(mListener);  
}  
}
```
2. mainActivity.java
```java
private ChatListAdapter mAdapter;
private DatabaseReference mDatabaseReference;

mDatabaseReference= FirebaseDatabase.getInstance().getReference();
mAdapter=new ChatListAdapter(this, mDatabaseReference,mDisplayName);  
mChatListView.setAdapter(mAdapter);
```
