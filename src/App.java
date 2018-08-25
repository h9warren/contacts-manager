import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;




public class App {
	
	static String directory = "data";
	static String filename = "info.txt";
	static Path dataDirectory = Paths.get(directory);
	static Path dataFile = Paths.get(directory, filename);
	static List<String> data;
	static List<Contact> contactList;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException {
		try	{
			fileExistsCheck();
			logic(choice());
		} catch (IOException e) {
			System.out.print(e);
		}
	// offer options 1. show all 2. add 3. delete 4. search
	}

	public static void fileExistsCheck() throws IOException {
		if (Files.notExists(dataDirectory)) {
			Files.createDirectories(dataDirectory);
		}
		if (Files.notExists(dataFile)) {
			Files.createFile(dataFile);
		}
	}
	public static void promptUserWithChoices() {
		String prompt = 
		"1. View contacts.\n" +
		"2. Add a new contact.\n" +
		"3. Search a contact by name.\n" +
		"4. Delete an existing contact.\n" +
		"5. Exit.\n" +
		"Enter an option (1, 2, 3, 4 or 5): "; 
		System.out.print(prompt);
	}
	public static int choice() {
		promptUserWithChoices();	
		int choice = sc.nextInt();
		sc.nextLine();
		return choice;
	}
	public static void logic(int choice) {

		if (choice == 1) {
			//show all
			displayAllContacts(getAllContacts());
		} else if (choice == 2) {
			//add
			addContact(newContact());
		} else if (choice == 3) {
			//search
			System.out.print("Contact Search\n");
			System.out.println("_____________________\n");
			displaySearchResults(searchContacts(), searchTerm());
		} else if (choice == 4) {
			//delete
			System.out.print("Delete Contact\n");
			System.out.println("_____________________\n");
			deleteContact(searchContacts(), searchTerm());
		} else if (choice == 5) {
			System.out.println("bye");
			sc.close();
		}
	}
	
	public static List<Contact> getAllContacts () {
		try {
			data = Files.readAllLines(dataFile);
			contactList = new ArrayList<Contact>();
			
			for (String row : data) {
				List<String> contactStringSplit = new ArrayList<String>(Arrays.asList(row.split(" ")));
				Contact person = new Contact(contactStringSplit.get(0), contactStringSplit.get(1), contactStringSplit.get(2));
				contactList.add(person);
				}
			return contactList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	
	public static void saveFile () {
		List<String> contactsListToWrite = new ArrayList<>();
		for (Contact entry : contactList) {	
			contactsListToWrite.add(entry.getFirst() + " " + entry.getLast() +  " " + entry.getNumber());
		}
		try {
			Files.write(dataFile, contactsListToWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void displayAllContacts(List<Contact> contactList) {
		System.out.println("\n\n\n\n\n");
		System.out.println("Name | Phone number");
		System.out.println("---------------");
		for (Contact entry : contactList) {
				String output = 
					entry.getFirst() + " " +
					entry.getLast() + " | " +
					entry.getNumber();
				System.out.println(output);
		}
		logic(choice());
	}
	public static Contact newContact() {
		String first;
		String last;
		String phone;
		System.out.println("\nnew contact info");
		System.out.println("________________");
		System.out.print("first: ");
		first = sc.next();
		System.out.print("last: ");
		last = sc.next();
		System.out.print("phone: ");
		phone = sc.next();
		Contact newcontact = new Contact(first, last, phone);
		sc.nextLine();
		return newcontact;	
	}
	public static void addContact(Contact newcontact) {
		getAllContacts ();
		contactList.add(newcontact);
		saveFile();
		System.out.println("\n" + newcontact.getFirst() + " " + newcontact.getLast() + " added!");
		logic(choice());
	}
	public static String searchTerm() {
		System.out.print("contact name: ");
		String searchterm = sc.next().toLowerCase();
		searchterm.trim();
		sc.nextLine();
		return searchterm;
	}
	public static void displaySearchResults(List<Contact>allContacts,String searchterm) {
		int i = 0;
		System.out.print("\n");
		for (Contact contact : allContacts) {
			if ((contact.getFirst().toLowerCase().equals(searchterm)) || (contact.getLast().toLowerCase().equals(searchterm))) {
				i++;
				System.out.println(i + ". " +
					contact.getFirst() + " " +
					contact.getLast() + " | " +
					contact.getNumber());
			}
		}
		System.out.print("\nMATCHING ENTRIES: " + i + "\n");
		System.out.println("_____________________\n");		
		searchAgain();
	}
	public static void deleteContact(List<Contact>allContacts,String searchterm) {
		int g = 0;
		System.out.print("\n");
		for (Contact contact : allContacts) {
			g++;
			if ((contact.getFirst().toLowerCase().equals(searchterm)) || (contact.getLast().toLowerCase().equals(searchterm))) {
				System.out.println(g + ". " +
					contact.getFirst() + " " +
					contact.getLast() + " | " +
					contact.getNumber());
			}
		}
		System.out.print("\nwhich " + searchterm + " to delete?\n");
		System.out.println("_____________________\n");	
		String del = pickContactToDelete();
		int index = Integer.parseInt(del) - 1;
		deleteContactAt(index, allContacts);
		deleteAgain();
	}
	public static void deleteContactAt(int index, List<Contact> allContacts) {
		allContacts.remove(index);
		contactList = allContacts;
		saveFile();
	}
	public static List<Contact> searchContacts() {
		List<Contact> allContacts = getAllContacts();	
		return allContacts;
	}
	public static String pickContactToDelete() {
		String answer = sc.nextLine(); 
		answer.trim();
		return answer;
	}
	public static void searchAgain() {
		System.out.print("SEARCH AGAIN? (y\\n) ");
		String answer = sc.nextLine(); 
		answer.trim();
		if (answer.equals("y") || answer.equals("yes")) {
			displaySearchResults(searchContacts(), searchTerm());
		} else {
			logic(choice());
		}	
	}
	public static void deleteAgain() {
		System.out.print("DELETE ANOTHER CONTACT? (y\\n) ");
		String answer = sc.nextLine(); 
		answer.trim();
		if (answer.equals("y") || answer.equals("yes")) {
			deleteContact(searchContacts(), searchTerm());
		} else {
			logic(choice());
		}	
	}
}
