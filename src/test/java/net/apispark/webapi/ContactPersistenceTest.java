package net.apispark.webapi;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import net.apispark.webapi.db.ContactPersistence;
import net.apispark.webapi.representation.Contact;
import net.apispark.webapi.representation.Gender;

public class ContactPersistenceTest {
	@Test
	public void TryContactId(){
		Contact c = new Contact(null, "Micael", "Mbagira", "svg-1", Gender.MALE);
		c=ContactPersistence.INSTANCE.addContact(c);
		Assert.assertNotNull(c.getId());
		ContactPersistence.INSTANCE.deleteContact(c.getId());
	}
	@Test
	public void GetContactWithId(){
		Contact c = new Contact(null, "Micaelle", "Mbagira", "svg-6", Gender.FEMALE);
		Contact updatedcontact=ContactPersistence.INSTANCE.addContact(c);
	
		Contact d=ContactPersistence.INSTANCE.getContact(c.getId());
		assertEquals(c.getId(), d.getId());
		
	}
}
