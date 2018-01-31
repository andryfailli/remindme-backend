package it.andreafailli.remindme.auth;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.google.firebase.auth.FirebaseToken;

public class FirebaseTokenBuilder {

	private String uuid;
	
	private String name;
	
	private String email;
	
	private String picture;
	
	public FirebaseTokenBuilder uuid(String uuid) {
		this.uuid = uuid;
		return this;
	}
	
	public FirebaseTokenBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public FirebaseTokenBuilder email(String email) {
		this.email = email;
		return this;
	}
	
	public FirebaseTokenBuilder picture(String picture) {
		this.picture = picture;
		return this;
	}
	
	public FirebaseToken build() throws Exception {
		Class<?> firebaseTokenImplPayloadClass = Class.forName("com.google.firebase.auth.FirebaseToken$FirebaseTokenImpl$Payload");
		Constructor<?> firebaseTokenImplPayloadConstructor = firebaseTokenImplPayloadClass.getDeclaredConstructors()[0];
		firebaseTokenImplPayloadConstructor.setAccessible(true);
		Object firebaseTokenImplPayload = firebaseTokenImplPayloadConstructor.newInstance();
		
		Class<?> jsonWebTokenPayloadClass = Class.forName("com.google.api.client.json.webtoken.JsonWebToken$Payload");
		Method jsonWebTokenPayloadSetSubjectMethod = jsonWebTokenPayloadClass.getMethod("setSubject", String.class);
		jsonWebTokenPayloadSetSubjectMethod.setAccessible(true);
		jsonWebTokenPayloadSetSubjectMethod.invoke(firebaseTokenImplPayload, this.uuid);
		
		Field firebaseTokenImplPayloadFieldName = firebaseTokenImplPayloadClass.getDeclaredField("name");
		firebaseTokenImplPayloadFieldName.setAccessible(true);
		firebaseTokenImplPayloadFieldName.set(firebaseTokenImplPayload, this.name);
		
		Field firebaseTokenImplPayloadFieldPicture = firebaseTokenImplPayloadClass.getDeclaredField("picture");
		firebaseTokenImplPayloadFieldPicture.setAccessible(true);
		firebaseTokenImplPayloadFieldPicture.set(firebaseTokenImplPayload, this.picture);
		
		Field firebaseTokenImplPayloadFieldEmail = firebaseTokenImplPayloadClass.getDeclaredField("email");
		firebaseTokenImplPayloadFieldEmail.setAccessible(true);
		firebaseTokenImplPayloadFieldEmail.set(firebaseTokenImplPayload, this.email);
		
		Class<?> firebaseTokenImplClass = Class.forName("com.google.firebase.auth.FirebaseToken$FirebaseTokenImpl");
		Constructor<?> firebaseTokenImplConstructor = firebaseTokenImplClass.getDeclaredConstructors()[0];
		firebaseTokenImplConstructor.setAccessible(true);
		Object firebaseTokenImpl = firebaseTokenImplConstructor.newInstance(new com.google.api.client.json.webtoken.JsonWebSignature.Header(), firebaseTokenImplPayload, new byte[] {}, new byte[] {});
		
		Constructor<?> firebaseTokenConstructor = FirebaseToken.class.getDeclaredConstructors()[0];
		firebaseTokenConstructor.setAccessible(true);
		return (FirebaseToken) firebaseTokenConstructor.newInstance(firebaseTokenImpl);
	}
	
}
